/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.Service;

import E1.API.MemCacheAPI;
import E1.Cache.MemCache;
import E1.Service.MemCacheService;
import E1.db.DBContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import static io.vertx.core.Vertx.vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLPool;

/**
 *
 * @author quant
 */
public class MemCacheService extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
//        MySQLConnectOptions mysqlConnectOptions = new MySQLConnectOptions()
//                .setPort(3306)
//                .setDatabase("utf8stickit")
//                .setUser("root")
//                .setPassword("quant-1234");
//        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        final MySQLPool pool = DBContext.getClient(vertx);
        MemCache memCache = new MemCache(vertx);
        Router memCacheRouter = Router.router(vertx);
        memCacheRouter.route().handler(BodyHandler.create());
        MemCacheAPI memCacheAPI = new MemCacheAPI(memCache, pool);
        memCacheAPI.route(memCacheRouter);
        vertx.createHttpServer().requestHandler(memCacheRouter).listen(8888, httpServerAsyncResult -> {
            if (httpServerAsyncResult.succeeded()) {
                System.out.println("MemCache API started on port 8888");
                startPromise.complete();
            } else {
                startPromise.fail(httpServerAsyncResult.cause());
            }
        });
    }
}
