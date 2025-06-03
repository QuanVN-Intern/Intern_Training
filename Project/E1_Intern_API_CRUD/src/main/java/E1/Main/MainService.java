/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.Main;
//import com.example.api.MemCacheApi;
//import com.example.api.RedisCacheApi;
//import com.example.cache.MemCacheService;
//import com.example.cache.RedisCacheService;
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Promise;
//import io.vertx.core.http.HttpServer;
//import io.vertx.ext.web.Router;
//import io.vertx.ext.web.handler.BodyHandler;
//import io.vertx.mysqlclient.MySQLConnectOptions;
//import io.vertx.mysqlclient.MySQLPool;
//import io.vertx.redis.client.Redis;
//import io.vertx.redis.client.RedisAPI;
//import io.vertx.redis.client.RedisOptions;
//import io.vertx.sqlclient.PoolOptions;
//
//public class MainService extends AbstractVerticle {
//    @Override
//    public void start(Promise<Void> startPromise) {
//        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
//                .setPort(3306)
//                .setDatabase("utf8stickit")
//                .setUser("root")
//                .setPassword("1234");
//
//        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
//        final MySQLPool pool = MySQLPool.pool(vertx, connectOptions, poolOptions);
//
//        RedisCacheService redisCacheService = new RedisCacheService(vertx);
//        //Config Redis Cache
//        RedisOptions redisOptions = new RedisOptions()
//                .setConnectionString("redis://localhost:6379"); // address Redis server
//        Redis redis = Redis.createClient(vertx, redisOptions);
//        redis.connect(onConnect -> {
//            if (onConnect.succeeded()) {
//                RedisAPI redisAPI = RedisAPI.api(onConnect.result());
//                System.out.println("Connected to Redis server");
//                // Create router
//                Router router = Router.router(vertx);
//                router.route().handler(BodyHandler.create());
//
//                router.get("/ping").handler(context -> {
//                    context.response().putHeader("content-type", "text/plain").end("pong");
//                });
//                RedisCacheApi redisCacheApi = new RedisCacheApi(redisCacheService, pool);
//                redisCacheApi.mountRoutes(redisCacheService, router, pool);
//                HttpServer server = vertx.createHttpServer();
//                server.requestHandler(router).listen(8080, http -> {
//                    if (http.succeeded()) {
//                        System.out.println("Redis Cache API started on port 8080");
//                        startPromise.complete();
//                    } else {
//                        startPromise.fail(http.cause());
//                    }
//                });
//            } else {
//                startPromise.fail(onConnect.cause());
//            }
//        });
//
//        // Redis Cache
//        MemCacheService memCacheService = new MemCacheService(vertx);
//        Router memCacheRouter = Router.router(vertx);
//        memCacheRouter.route().handler(BodyHandler.create());
//        MemCacheApi memCacheApi = new MemCacheApi(memCacheService, pool);
//
//        memCacheApi.route(memCacheRouter);
//        vertx.createHttpServer()
//                .requestHandler(memCacheRouter)
//                .listen(8888, http -> {
//                    if (http.succeeded()) {
//                        System.out.println("MemCache API server started on port 8888");
//                    } else {
//                        System.err.println("Failed to start MemCache API server: " + http.cause());
//                    }
//                });
//    }
//}

