/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import REST.TestRest;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.net.http.HttpResponse.BodyHandler;
import org.zandero.rest.RestRouter;
/*
 *
 * @author quant
 */

public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        try {
            RestRouter.register(router, new TestRest());
        } catch (Exception e) {
            System.err.println("Failed to register REST endpoints: " + e.getMessage());
        }

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080, result -> {
                if (result.succeeded()) {
                    System.out.println("Server started on port 8080");
                } else {
                    System.err.println("Server failed to start: " + result.cause());
                }
            });
    }
} 