/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.Cache;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author quant
 */
public class MemCache {

    private final MemcachedClient memcachedClient;
    private final Vertx vertx;

    //constructor
    public MemCache(Vertx vertx) {
        this.vertx = vertx;
        try {
            memcachedClient = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to Memcached server" + e.getMessage());
        }
    }

    public Future<Object> getJson(String key) {
        Promise<Object> promise = Promise.promise();
        vertx.executeBlocking(
                p -> {
                    try {
                        Object value = memcachedClient.get(key);
                        p.complete(value);
                    } catch (Exception e) {
                        p.fail(e);
                    }
                }, promise);
        return promise.future();
    }

    public Future<Void> setJson(String key, String value, int ttlSeconds) {

        Promise<Void> promise = Promise.promise();
        vertx.executeBlocking(
                p -> {
                    try {
                        memcachedClient.set(key, ttlSeconds, value);
                        p.complete();
                    } catch (Exception e) {
                        p.fail(e);
                    }
                }, promise);
        return promise.future();
    }

    public Future<Void> delete(String key) {
        Promise<Void> promise = Promise.promise();
        vertx.executeBlocking(
                p -> {
                    try {
                        memcachedClient.delete(key);
                        p.complete();
                    } catch (Exception e) {
                        p.fail(e);
                    }
                }, promise);
        return promise.future();
    }
}
