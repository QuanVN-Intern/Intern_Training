/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package E1.Main;

import E1.Service.MemCacheService;
import E1.Service.RedisCacheService;
import io.vertx.core.Vertx;

/**
 *
 * @author quant
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MemCacheService());
        vertx.deployVerticle(new RedisCacheService());
    }
}
