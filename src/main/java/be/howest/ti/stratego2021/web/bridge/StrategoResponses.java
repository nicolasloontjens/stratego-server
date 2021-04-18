package be.howest.ti.stratego2021.web.bridge;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


/**
 * The StrategoResponses class is responsible for translating the result of the controller into
 * JSON responses with an appropriate HTTP code.
 */
public class StrategoResponses {

    private StrategoResponses() { /* utility class */ }

    private static void sendJsonResponse(RoutingContext ctx, int statusCode, Object response) {
        ctx.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setStatusCode(statusCode)
                .end(Json.encodePrettily(response));
    }

    public static void sendGetDemoResponse(RoutingContext ctx) {
        sendJsonResponse(ctx, 200, new JsonObject()
        .put("msg", "hello world"));
    }

    public static void sendPostDemoResponse(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendStrategoVersions(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendStrategoVersion(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendMove(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendMoves(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendGameState(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendJoinedGameInfo(RoutingContext ctx) {
        sendNotYetImplementedResponse(ctx);
    }

    public static void sendFailure(RoutingContext ctx, int code, String message) {
        sendJsonResponse(ctx, code, new JsonObject()
                .put("failure", code)
                .put("cause", message));
    }

    // TODO skeleton: this method should not be used (and thus removed) in the final version.
    private static void sendNotYetImplementedResponse(RoutingContext ctx) {
        sendJsonResponse(ctx, 500, new JsonObject()
                .put("message", "Not Yet Implemented (NYI)")
        );
    }
}
