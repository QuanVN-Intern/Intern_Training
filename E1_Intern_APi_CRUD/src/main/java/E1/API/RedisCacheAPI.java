/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.API;

import E1.Cache.RedisCache;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

/**
 *
 * @author quant
 */
public class RedisCacheAPI {

    private RedisCache redisCache;
    private MySQLPool client;

    public RedisCacheAPI(RedisCache redisCache, MySQLPool client) {
        this.redisCache = redisCache;
        this.client = client;
    }

    public void mountRoutes(RedisCache redisCache, Router router, Pool db) {
        // categories
        router.get("/api/categories/:id").handler(context -> getCategories(context, db, redisCache));
        router.post("/api/categories").handler(context -> createCategory(context, db));
        router.put("/api/categories/:id").handler(context -> updateCategory(context, db, redisCache));
        router.delete("/api/categories/:id").handler(context -> deleteCategory(context, db, redisCache));

        // package
        router.get("/api/packages/:id").handler(context -> getPackages(context, db, redisCache));
        router.post("/api/packages").handler(context -> createPackage(context, db));
        router.put("/api/packages/:id").handler(context -> updatePackage(context, db, redisCache));
        router.delete("/api/packages/:id").handler(context -> deletePackage(context, db, redisCache));

        //Stiker
        router.get("/api/stickers/:id").handler(context -> getStickers(context, db, redisCache));
        router.post("/api/stickers").handler(context -> createSticker(context, db));
        router.put("/api/stickers/:id").handler(context -> updateSticker(context, db, redisCache));
        router.delete("/api/stickers/:id").handler(context -> deleteSticker(context, db, redisCache));
    }

    //Get Categories done
    private static void getCategories(RoutingContext context, Pool db, RedisCache redisCache) {
        String id = context.pathParam("id");
        String key = "categories:" + id;
        redisCache.getJson(key).onSuccess(cacheJson -> {
            if (cacheJson != null) {
                context.response().putHeader("content-type", "application/json").end(cacheJson.encode());
            } else {
                db.preparedQuery("SELECT * FROM categories where id = ?")
                        .execute(Tuple.of(Integer.parseInt(id)), res -> {
                            if (res.succeeded() && res.result().size() > 0) {
                                Row row = res.result().iterator().next();
                                JsonObject category = new JsonObject()
                                        .put("id", row.getInteger("id"))
                                        .put("name", row.getString("name"))
                                        .put("url", row.getString("url"))
                                        .put("locale", row.getString("locale"))
                                        .put("order", row.getInteger("order"))
                                        .put("isDisplayed", row.getBoolean("isDisplayed"))
                                        .put("packageCount", row.getString("packageCount"))
                                        .put("createdDate", row.getLong("createdDate"));

                                redisCache.setJson(key, category, 300);
                                context.response().putHeader("content-type", "application/json").end(category.encode());
                            } else {
                                context.response().setStatusCode(404).end("Category not found");
                            }
                        });
            }
        });
    }

    // Create Category done
    private static void createCategory(RoutingContext context, Pool db) {
        JsonObject category = context.body().asJsonObject();

        String name = category.getString("name");
        String url = category.getString("url");
        String locale = category.getString("locale");
        int order = category.getInteger("order", 999);
        boolean isDisplayed = category.getBoolean("isDisplayed", true);
        String packageCount = category.getString("packageCount", "0");
        Long createdDate = category.getLong("createdDate", System.currentTimeMillis());

        db.preparedQuery("INSERT INTO categories (name, url, locale, `order`, isDisplayed, packageCount, createdDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(name, url, locale, order, isDisplayed, packageCount, createdDate), ar -> {
                    if (ar.succeeded()) {
                        context.response().setStatusCode(201).end("Category created");
                    } else {
                        context.response().setStatusCode(400).end("Failed to create category: " + ar.cause().getMessage());
                    }
                });
    }

    // Update Category done
    private static void updateCategory(RoutingContext context, Pool db, RedisCache redisCache) {
        String categoryId = context.pathParam("id");
        String key = "category:" + categoryId;

        JsonObject category = context.body().asJsonObject();
        String name = category.getString("name");
        String url = category.getString("url");
        String locale = category.getString("locale");
        int order = category.getInteger("order", 999);
        boolean isDisplayed = category.getBoolean("isDisplayed", true);
        String packageCount = category.getString("packageCount", "0");
        Long createdDate = category.getLong("createdDate", System.currentTimeMillis());

        db.preparedQuery("UPDATE categories SET name = ?, url = ?, locale = ?, `order` = ?, isDisplayed = ?, packageCount = ?, createdDate = ? WHERE id = ?")
                .execute(Tuple.of(name, url, locale, order, isDisplayed, packageCount, createdDate, Integer.parseInt(categoryId)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        JsonObject updated = new JsonObject()
                                .put("id", Integer.parseInt(categoryId))
                                .put("name", name)
                                .put("url", url)
                                .put("locale", locale)
                                .put("order", order)
                                .put("isDisplayed", isDisplayed)
                                .put("packageCount", packageCount)
                                .put("createdDate", createdDate);

                        redisCache.setJson(key, updated, 300);
                        context.response().end("Category updated");
                    } else {
                        context.response().setStatusCode(400).end("Category not found or changed");
                    }
                });
    }

    // Delete Category done
    private static void deleteCategory(RoutingContext context, Pool db, RedisCache redisCache) {
        String categoryId = context.pathParam("id");
        String key = "category:" + categoryId;
        db.preparedQuery("DELETE From categories where id = ?")
                .execute(Tuple.of(Integer.parseInt(categoryId)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        redisCache.delete(key);
                        context.response().end("Category deleted");
                    } else {
                        context.response().setStatusCode(404).end("Category not found");
                    }
                });
    }

    // Get Packages done
    private static void getPackages(RoutingContext context, Pool db, RedisCache redisCache) {
        String packageId = context.pathParam("id");
        String key = "packages:" + packageId;
        redisCache.getJson(key).onSuccess(cacheJson -> {
            if (cacheJson != null) {
                context.response().putHeader("content-type", "application/json").end(cacheJson.encode());
            } else {
                db.preparedQuery("SELECT * FROM packages WHERE id = ?").execute(Tuple.of(Integer.valueOf(packageId)), ar -> {
                    if (ar.succeeded() && ar.result().size() > 0) {
                        Row row = ar.result().iterator().next();
                        JsonObject packageJson = new JsonObject()
                                .put("id", row.getInteger("id"))
                                .put("name", row.getString("name"))
                                .put("creatorName", row.getString("creatorName"))
                                .put("stickerCount", row.getInteger("stickerCount"))
                                .put("addWhatsApp", row.getInteger("addWhatsapp"))
                                .put("addTelegram", row.getInteger("addTelegram"))
                                .put("viewCount", row.getInteger("viewCount"))
                                .put("categoryIds", row.getString("categoryIds"))
                                .put("isDisplayed", row.getInteger("isDisplayed"))
                                .put("createdDate", row.getLong("createdDate"))
                                .put("locale", row.getString("locale"))
                                .put("order", row.getInteger("order"))
                                .put("isPremium", row.getInteger("isPremium") == 0)
                                .put("isAnimated", row.getInteger("isAnimated") == 0);

                        redisCache.setJson(key, packageJson, 300);
                        context.response().putHeader("content-type", "application/json").end(packageJson.encode());
                    } else {
                        context.response().setStatusCode(404).end("Package not found");
                    }
                });
            }
        });
    }

    // Create Package done
    private static void createPackage(RoutingContext context, Pool db) {

        JsonObject packageJson = context.body().asJsonObject();

        String name = packageJson.getString("name");
        String creatorName = packageJson.getString("creatorName");
        int stickerCount = packageJson.getInteger("stickerCount", 0);
        int addWhatsApp = packageJson.getInteger("addWhatsApp", 0);
        int addTelegram = packageJson.getInteger("addTelegram", 0);
        int viewCount = packageJson.getInteger("viewCount", 0);
        String categoryIds = packageJson.getString("CategoryIds");
        int isDisplayed = packageJson.getInteger("isDisplayed", 0);
        Long createdDate = packageJson.getLong("createdDate", System.currentTimeMillis());
        String locale = packageJson.getString("locale");
        int order = packageJson.getInteger("order", 999);
        boolean isPremium = packageJson.getBoolean("isPremium", false);
        boolean isAnimated = packageJson.getBoolean("isAnimated", false);

        db.preparedQuery("INSERT INTO packages (name, creatorName, stickerCount, addWhatsApp, addTelegram,"
                + " viewCount, categoryIds, isDisplayed, createdDate, locale, `order`, isPremium, isAnimated) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(name, creatorName, stickerCount, addWhatsApp, addTelegram,
                        viewCount, categoryIds, isDisplayed, createdDate, locale, order, isPremium, isAnimated), ar -> {
                            if (ar.succeeded()) {
                                context.response().setStatusCode(201).end("Package created");
                            } else {
                                context.response().setStatusCode(400).end("Failed to create package: " + ar.cause().getMessage());
                            }
                        });
    }

    // Update Package done
    private static void updatePackage(RoutingContext context, Pool db, RedisCache redisCache) {
        String packageId = context.pathParam("id");
        String key = "packages:" + packageId;

        JsonObject body = context.body().asJsonObject();
        String name = body.getString("name");
        String creatorName = body.getString("creatorName");
        int stickerCount = body.getInteger("stickerCount", 0);
        int addWhatsApp = body.getInteger("addWhatsApp", 0);
        int addTelegram = body.getInteger("addTelegram", 0);
        int viewCount = body.getInteger("viewCount");
        String categoryIds = body.getString("CategoryIds");
        int isDisplayed = body.getInteger("isDisplayed", 0);
        Long createdDate = body.getLong("CreatedDate", System.currentTimeMillis());
        String locale = body.getString("locale");
        int order = body.getInteger("order", 999);
        boolean isPremium = body.getBoolean("isPremium");
        boolean isAnimated = body.getBoolean("isAnimated");

        db.preparedQuery("UPDATE packages SET name = ?, creatorName = ?, stickerCount = ?, addWhatsApp = ?, addTelegram = ?, viewCount = ?, CategoryIds = ?,"
                + " isDisplayed = ?, CreatedDate = ?, locale = ?, `order` = ?, isPremium = ?, isAnimated = ? WHERE id = ?")
                .execute(Tuple.of(name, creatorName, stickerCount, addWhatsApp, addTelegram, viewCount, categoryIds, isDisplayed, createdDate,
                        locale, order, isPremium, isAnimated, Integer.parseInt(packageId)), ar -> {
                    if (ar.succeeded()) {
                        if (ar.result().rowCount() > 0) {
                            JsonObject updated = new JsonObject()
                                    .put("id", Integer.parseInt(packageId))
                                    .put("name", name)
                                    .put("creatorName", creatorName)
                                    .put("stickerCount", stickerCount)
                                    .put("addWhatsApp", addWhatsApp)
                                    .put("addTelegram", addTelegram)
                                    .put("viewCount", viewCount)
                                    .put("CategoryIds", categoryIds)
                                    .put("isDisplayed", isDisplayed)
                                    .put("CreatedDate", createdDate)
                                    .put("locale", locale)
                                    .put("order", order)
                                    .put("isPremium", isPremium)
                                    .put("isAnimated", isAnimated);

                            redisCache.setJson(key, updated, 300);
                            context.response().end("Package updated");
                        } else {
                            context.response().setStatusCode(404).end("Package not found");
                        }
                    } else {
                        context.response().setStatusCode(500).end("Failed to update package: " + ar.cause().getMessage());
                    }
                });
    }

    // Delete Package done
    private static void deletePackage(RoutingContext context, Pool db, RedisCache redisCache) {
        String packageId = context.pathParam("id");
        String key = "packages:" + packageId;
        db.preparedQuery("DELETE FROM packages WHERE id = ?")
                .execute(Tuple.of(Integer.parseInt(packageId)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        redisCache.delete(key);
                        context.response().end("Package deleted");
                    } else {
                        context.response().setStatusCode(400).end("Package not found");
                    }
                });
    }

    //Get stikers
    private static void getStickers(RoutingContext context, Pool db, RedisCache redisCache) {
        String stickerId = context.pathParam("id");
        String key = "sticker:" + stickerId;
        redisCache.getJson(key).onSuccess(cacheJson -> {
            if (cacheJson != null) {
                context.response().putHeader("content-type", "application/json").end(cacheJson.encode());
            } else {
                db.preparedQuery("SELECT * FROM stickers where id = ?")
                        .execute(Tuple.of(stickerId), res -> {
                            if (res.succeeded() && res.result().size() > 0) {
                                Row row = res.result().iterator().next();
                                JsonObject sticker = new JsonObject()
                                        .put("id", row.getInteger("id"))
                                        .put("url", row.getString("url"))
                                        .put("packageId", row.getInteger("packageId"))
                                        .put("locale", row.getString("locale"))
                                        .put("order", row.getInteger("order"))
                                        .put("viewCount", row.getInteger("viewCount"))
                                        .put("createdDate", row.getLong("createdDate"))
                                        .put("emojis", row.getString("emojis"))
                                        .put("isPremium", row.getBoolean("isPremium"));
                                redisCache.setJson(key, sticker, 300);
                                context.response().putHeader("content-type", "application/json").end(sticker.encode());
                            } else {
                                context.response().setStatusCode(400).end("Sticker not found");
                            }
                        });
            }
        });
    }

    // Create Sticker
    private static void createSticker(RoutingContext context, Pool db) {

        JsonObject sticker = context.body().asJsonObject();
        String url = sticker.getString("url");
        int packageId = sticker.getInteger("packageId");
        String locale = sticker.getString("locale");
        int order = sticker.getInteger("order", 999);
        int viewCount = sticker.getInteger("viewCount", 0);
        Long createdDate = sticker.getLong("createdDate", System.currentTimeMillis());
        String emojis = sticker.getString("emojis");
        boolean isPremium = sticker.getBoolean("isPremium", false);

        db.preparedQuery("INSERT INTO stickers (url, packageId, locale, `order`, viewCount, createdDate, emojis, isPremium) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(url, packageId, locale, order, viewCount, createdDate, emojis, isPremium), ar -> {
                    if (ar.succeeded()) {
                        context.response().setStatusCode(201).end("Sticker created");
                    } else {
                        ar.cause().printStackTrace();  // thêm dòng này để debug lỗi
                        context.response().setStatusCode(400).end("Failed to create sticker");
                    }
                });
    }

    // Update Sticker
    private static void updateSticker(RoutingContext context, Pool db, RedisCache redisCache) {
        String stickerId = context.pathParam("id");
        String key = "sticker:" + stickerId;
        redisCache.getJson(key).onSuccess(cacheJson -> {
            JsonObject sticker = new JsonObject();
            String url = sticker.getString("url");
            String packageId = sticker.getString("packageId");
            String locale = sticker.getString("locale");
            int order = sticker.getInteger("order", 999);
            int viewCount = sticker.getInteger("viewCount", 0);
            Long createdDate = sticker.getLong("createdDate", System.currentTimeMillis());
            String emojis = sticker.getString("emojis");
            boolean isPremium = sticker.getBoolean("isPremium", false);

            db.preparedQuery("UPDATE stickers SET url = ?, packageId= ?, locale = ?, "
                    + "`order`=?, viewCount = ?, createdDate = ?, emojis = ?, isPremium = ? WHERE id = ?")
                    .execute(Tuple.of(url, packageId, locale, order, viewCount, createdDate, emojis, isPremium, Integer.parseInt(stickerId)),
                            ar -> {
                                if (ar.succeeded() && ar.result().rowCount() > 0) {
                                    JsonObject updated = new JsonObject()
                                            .put("id", Integer.parseInt(stickerId))
                                            .put("url", url)
                                            .put("packageId", packageId)
                                            .put("locale", locale)
                                            .put("order", order)
                                            .put("viewCount", viewCount)
                                            .put("createdDate", createdDate)
                                            .put("emojis", emojis)
                                            .put("isPremium", isPremium);
                                    redisCache.setJson(key, updated, 300);
                                    context.response().end("Sticker updated");
                                } else {
                                    context.response().setStatusCode(400).end("Sticker not found");
                                }
                            });
        });
    }

    // Update Sticker
    private static void deleteSticker(RoutingContext context, Pool db, RedisCache redisCacheService) {
        String stickerId = context.pathParam("id");
        String key = "sticker:" + stickerId;

        db.preparedQuery("DELETE FROM stickers WHERE id = ?")
                .execute(Tuple.of(Integer.parseInt(stickerId)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        redisCacheService.delete(key);
                        context.response().end("Sticker deleted");
                    } else {
                        context.response().setStatusCode(404).end("Sticker not found");
                    }
                });
    }
}
