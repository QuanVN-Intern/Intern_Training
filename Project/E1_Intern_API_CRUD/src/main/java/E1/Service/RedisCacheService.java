/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.Service;

import E1.API.MemCacheAPI;
import E1.API.RedisCacheAPI;
import E1.Cache.RedisCache;
import E1.db.DBContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;

/**
 *
 * @author quant
 */
public class RedisCacheService extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
//        MySQLConnectOptions mysqlConnectOptions = new MySQLConnectOptions()
//                .setPort(3306)
//                .setDatabase("utf8stickit")
//                .setUser("root")
//                .setPassword("quant-1234");
//
//        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        final MySQLPool pool = DBContext.getClient(vertx);

        RedisCache redisCache = new RedisCache(vertx);
        RedisOptions redisOptions = new RedisOptions()
                .setConnectionString("redis://localhost:6379/"); // address Redis server
        Redis redis = Redis.createClient(vertx, redisOptions);
        redis.connect(onConnect -> {
            if (onConnect.succeeded()) {
                // RedisAPI redisAPI = RedisAPI.api(onConnect.result());
                System.out.println("Connected to Redis server");
                // Create router
                Router router = Router.router(vertx);
                router.route().handler(io.vertx.ext.web.handler.BodyHandler.create());
                router.get("/ping").handler(context -> {
                    context.response().putHeader("content-type", "text/plain").end("pong");
                });
                RedisCacheAPI redisCacheAPI = new RedisCacheAPI(redisCache, pool);
                redisCacheAPI.mountRoutes(redisCache, router, pool);
                HttpServer server = vertx.createHttpServer();
                server.requestHandler(router).listen(8080, http -> {
                    if (http.succeeded()) {
                        System.out.println("Redis Cache API started on port 8080");
                        startPromise.complete();
                    } else {
                        startPromise.fail(http.cause());
                    }
                });
            } else {
                System.out.println("Failed to connect to Redis server: " + onConnect.cause());
                startPromise.fail(onConnect.cause());
            }
        });
    }
}
