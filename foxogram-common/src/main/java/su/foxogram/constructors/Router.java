package su.foxogram.constructors;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class Router {
    private final io.vertx.ext.web.Router router;

    public Router(io.vertx.ext.web.Router router) {
        this.router = router;

        router.route().handler(CorsHandler.create("*"));
        router.route().handler(BodyHandler.create());
    }

    public void addHandler(Handler<RoutingContext> Handler) {
        router.post().handler(Handler);
    }
}
