package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.StrategoController;
import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;

import be.howest.ti.stratego2021.web.StrategoWebController;
import be.howest.ti.stratego2021.web.exceptions.ForbiddenAccessException;
import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;
import be.howest.ti.stratego2021.web.tokens.PlainTextTokens;
import be.howest.ti.stratego2021.web.tokens.TokenManager;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.impl.HttpStatusException;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * In the StrategoBridge class you will find one handler-method per API operation.
 * The job of the "bridge" is to bridge between JSON (request and response) and Java (the controller).
 *
 * For each API operation you should get the required data from the `requestParameters`,
 * pass it to the controller and use its result to generate a response (`StrategoResponses`).
 */
public class StrategoBridge implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(StrategoBridge.class.getName());

    private final StrategoWebController controller;
    private final TokenManager tokenManager = new PlainTextTokens();

    public StrategoBridge(StrategoWebController controller) {
        this.controller = controller;
    }

    public StrategoBridge() {
        this(new StrategoController());
    }

    private void getDemo(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.getDemo();

        StrategoResponses.sendGetDemoResponse(ctx);
    }

    private void postDemo(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.postDemo();

        StrategoResponses.sendPostDemoResponse(ctx);
    }

    private void getStrategoVersions(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        String[] allVersions = controller.getStrategoVersions();

        StrategoResponses.sendStrategoVersions(ctx, allVersions);
    }

    private void getStrategoVersion(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.getStrategoVersion();

        StrategoResponses.sendStrategoVersion(ctx);
    }

    private void makeMove(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.makeMove();

        StrategoResponses.sendMove(ctx);
    }

    private void getMoves(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.getMoves();

        StrategoResponses.sendMoves(ctx);
    }

    private void joinGame(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.joinGame();

        StrategoResponses.sendJoinedGameInfo(ctx);

    }

    private void getGameState(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        controller.getGameState();

        StrategoResponses.sendGameState(ctx);
    }

    private void authorize(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        String authorizedGameId = requestParameters.getAuthorizedGameId();
        String authorizedPlayer = requestParameters.getAuthorizedPlayer();

        if ("invalid".equals(authorizedGameId) || "invalid".equals(authorizedPlayer)) {
            throw new ForbiddenAccessException();
        }

        ctx.next();
    }

    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("player_auth", BearerAuthHandler.create(this));


        LOGGER.log(Level.INFO, "Installing cors handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing failure handler for all operations");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));


        LOGGER.log(Level.INFO, "Installing dedicated handler for each operation");
        routerBuilder.operation("getDemo").handler(this::getDemo);

        routerBuilder.operation("postDemo")
                .handler(this::authorize)
                .handler(this::postDemo);

        routerBuilder.operation("getStrategoVersions").handler(this::getStrategoVersions);

        routerBuilder.operation("getStrategoVersion").handler(this::getStrategoVersion);

        routerBuilder.operation("joinGame").handler(this::joinGame);

        routerBuilder.operation("getGameState")
                .handler(this::authorize)
                .handler(this::getGameState);

        routerBuilder.operation("makeMove")
                .handler(this::authorize)
                .handler(this::makeMove);

        routerBuilder.operation("getMoves")
                .handler(this::authorize)
                .handler(this::getMoves);


        LOGGER.log(Level.INFO, "All handlers are installed, creating router.");
        return routerBuilder.createRouter();
    }

    /**
     * Whenever an exception is thrown during the execution of one of the
     * operation handlers, this method is invoked.
     *
     * The type of the exception is translated into an HTTP (error) code.
     */
    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code;
        String message = Objects.isNull(cause) ? null : cause.getMessage();

        if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof StrategoResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof StrategoGameRuleException) {
            code = 409;
        } else if (cause instanceof HttpStatusException) {
            code = ctx.statusCode();
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
            code = 500;
            message = "Something went wrong on the server side, check the logs for more information.";
        }

        StrategoResponses.sendFailure(ctx, code, message);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create();
    }

    /**
     * Extracts the player token from the request,
     * asks the token manger to convert it into a game-id and player name, and
     * makes it available for the StrategoRequestParameters.
     */
    @Override public void authenticate(JsonObject credentials, Handler<AsyncResult<User>> handler) {
        TokenCredentials tokenCredentials = credentials.mapTo(TokenCredentials.class);
        String token = tokenCredentials.getToken();

        try {
            handler.handle(Future.succeededFuture(
                    tokenManager.createUser(token)
            ));
        } catch (InvalidTokenException ex) {
            handler.handle(Future.failedFuture(ex));
        }
    }
}
