/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.Cache;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import java.util.Arrays;

/**
 *
 * @author quant
 */
public class RedisCache {

    private final Redis redis;
    private final RedisAPI redisAPI;

    public RedisCache(Vertx vertx) {
        RedisOptions options = new RedisOptions()
                .setConnectionString("redis://localhost:6379");
        this.redis = Redis.createClient(vertx, options);
        this.redisAPI = RedisAPI.api(redis);
    }

    public Future<JsonObject> getJson(String key) {
        Promise<JsonObject> promise = Promise.promise();
        redisAPI.get(key, res -> {
            if (res.succeeded() && res.result() != null) {
                promise.complete(new JsonObject(res.result().toString()));
            } else {
                promise.complete(null);
            }
        });
        return promise.future();
    }

    public Future<Void> setJson(String key, JsonObject data, int ttlSeconds) {
        Promise<Void> promise = Promise.promise();
//       redisAPI.set(Arrays.asList(key, data.encode()))
//               .onSuccess(  setRes -> {
//                   redisAPI.expire(Arrays.asList(key, String.valueOf(ttlSeconds)))
//                           .onSuccess(expireRes -> promise.complete())
//                           .onFailure(promise::fail);;
//               })
        redisAPI.set(Arrays.asList(key, data.encode(), "EX", String.valueOf(ttlSeconds)))
                .onSuccess(res -> promise.complete())
                .onFailure(err -> {
                    promise.fail(err);
                    err.printStackTrace();
                });
        return promise.future();
    }

    public Future<Void> delete(String key) {
        redisAPI.del(Arrays.asList(key), res -> {
        });
        return Future.succeededFuture();
    }
}
