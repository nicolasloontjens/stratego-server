package be.howest.ti.stratego2021.web.bridge;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;

import java.util.List;

/**
 * The StrategoRequestParameters class is responsible for translating information that is part of the
 * request into Java.
 */
public class StrategoRequestParameters {

    public static StrategoRequestParameters from(RoutingContext ctx) {
        return new StrategoRequestParameters(ctx);
    }

    private final RequestParameters params;
    private final User user;

    private StrategoRequestParameters(RoutingContext ctx) {
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
        this.user = ctx.user();
    }

    public String getAuthorizedGameId() {
        return user.get("gameId");
    }
    public String getAuthorizedPlayer() {
        return user.get("player");
    }

    public String getVersion(){
        return params.pathParameter("version").getString();
    }
    public List<List<String>> getStartConfiguration(){
        JoinGamePostBody body = params.body().getJsonObject().mapTo(JoinGamePostBody.class);
        return body.getStartConfiguration();
    }

}
