/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.db;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author quant
 */

public class DBContext {

    private static MySQLPool client;

    public static MySQLPool getClient(Vertx vertx) {
        if (client == null) {
            MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                    .setPort(3306)
                    .setHost("localhost")
                    .setDatabase("utf8stickit")
                    .setUser("root")
                    .setPassword("quant-1234")
                    .setCharset("utf8mb4");

//            PoolOptions poolOptions = new PoolOptions()
//                    .setMaxSize(5);
//            System.out.println("Creating MySQL client for vertx instance: " + v);
//            return MySQLPool.pool(v, connectOptions, poolOptions);
            PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
            client = MySQLPool.pool(vertx, connectOptions, poolOptions);

            // Test connection with a simple query
            client.query("select 1").execute(ar -> {
                if (ar.succeeded()) {
                    System.out.println("Successfully connected to MySQL database!");
                } else {
                    System.err.println("Failed to connect to MySQL: " + ar.cause().getMessage());
                }
            });
        }
        return client;
    }
}
