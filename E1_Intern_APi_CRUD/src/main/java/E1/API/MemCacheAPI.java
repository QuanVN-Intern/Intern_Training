/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E1.API;

import E1.Cache.MemCache;
import E1.Service.MemCacheService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Tuple;

/**
 *
 * @author quant
 */
public class MemCacheAPI {

    private MemCache memCache;
    private MySQLPool client;

    public MemCacheAPI(MemCache memCache, MySQLPool client) {
        this.memCache = memCache;
        this.client = client;
    }
//    public static class CategoryDao{
//        public Categories categories(Row row) {
//            return new Categories(
//                    row.getInteger("id"),
//                    row.getString("name"),
//                    row.getString("url"),
//                    row.getString("locale"),
//                    row.getInteger("order"),
//                    row.getInteger("isDisplayed"),
//                    row.getString("packageCount"),
//                    row.getLong("createdDate")
//            );
//        }
//    }
//
//    public static class PackageDao{
//        public Packages packages(Row row) {
//            return new Packages(
//                    row.getInteger("id"),
//                    row.getString("name"),
//                    row.getString("creatorName"),
//                    row.getInteger("stickerCount"),
//                    row.getInteger("addWhatsApp"),
//                    row.getInteger("addTelegram"),
//                    row.getString("categoryIds"),
//                    row.getInteger("viewCount"),
//                    row.getLong("createdDate"),
//                    row.getInteger("isDisplayed"),
//                    row.getString("locale"),
//                    row.getInteger("order"),
//                    row.getInteger("isPremium"),
//                    row.getInteger("isAnimated")
//            );
//        }
//    }
//
//    public static class StickerDao{
//        public Stickers stickers(Row row) {
//            return new Stickers(
//                    row.getInteger("id"),
//                    row.getString("url"),
//                    row.getInteger("packageId"),
//                    row.getInteger("order"),
//                    row.getInteger("viewCount"),
//                    row.getLong("createdDate"),
//                    row.getString("emojis"),
//                    row.getInteger("isPremium")
//            );
//        }
//    }

    public void route(Router router) {
        //Categories
        router.get("/memcache/categories/:id").handler(this::handleGetCategories);
        router.post("/memcache/categories").handler(this::handlePostCategories);
        router.put("/memcache/categories/:id").handler(this::handleUpdateCategories);
        router.delete("/memcache/categories/:id").handler(this::handleDeleteCategories);

        //Packages
        router.get("/memcache/packages/:id").handler(this::handleGetPackages);
        router.post("/memcache/packages").handler(this::handlePostPackages);
        router.put("/memcache/packages/:id").handler(this::handleUpdatePackages);
        router.delete("/memcache/packages/:id").handler(this::handleDeletePackages);

        //Stickers
        router.get("/memcache/stickers/:id").handler(this::handleGetStickers);
        router.post("/memcache/stickers").handler(this::handlePostStickers);
        router.put("/memcache/stickers/:id").handler(this::handleUpdateStickers);
        router.delete("/memcache/stickers/:id").handler(this::handleDeleteStickers);
    }

    //Categories
    private void handleGetCategories(RoutingContext context) {
        String categoriesId = context.pathParam("id");
        String key = "categories:" + categoriesId;
        memCache.getJson(key).onSuccess(cached -> {
            if (cached != null) {
                context.response().putHeader("content-type", "application/json").end(cached.toString());
            } else {
                client.preparedQuery("select * from categories where id = ?")
                        .execute(Tuple.of(Integer.parseInt(categoriesId)), res -> {
                            if (res.succeeded() && res.result().size() > 0) {
                                JsonObject category = res.result().iterator().next().toJson();
                                memCache.setJson(key, category.encode(), 60);
                                context.response().putHeader("content-type", "application/json").end(category.encode());
                            } else {
                                context.response().putHeader("content-type", "application/json").setStatusCode(404).end("Category not found");
                            }
                        });
            }
        }).onFailure(err -> {
            context.response().setStatusCode(500).end("Error retrieving data from Memcached" + err.getMessage());
        });
    }

    private void handlePostCategories(RoutingContext context) {
        JsonObject body = context.body().asJsonObject();
        if (body == null) {
            context.response().setStatusCode(400).end("Invalid JSON");
            return;
        }
        String name = body.getString("name");
        String url = body.getString("url");
        String locale = body.getString("locale");
        int order = body.getInteger("order", 999);
        int isDisplayed = body.getInteger("isDisplayed", 1);
        String packageCount = body.getString("packageCount", "0");
        Long createdDate = body.getLong("createdDate", System.currentTimeMillis());

        client.preparedQuery("INSERT INTO categories (name, url, locale, `order`, isDisplayed, packageCount, createdDate)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(name, url, locale, order, isDisplayed, packageCount, createdDate), ar -> {
                    if (ar.succeeded()) {
                        int id = Math.toIntExact(ar.result().property(MySQLClient.LAST_INSERTED_ID));
                        JsonObject category = new JsonObject()
                                .put("id", id)
                                .put("name", name)
                                .put("url", url)
                                .put("locale", locale)
                                .put("order", order)
                                .put("isDisplayed", isDisplayed)
                                .put("packageCount", packageCount)
                                .put("createdDate", createdDate);
                        memCache.setJson("categories:" + id, category.encode(), 60);
                        context.response().setStatusCode(201).end(category.encode());
                    } else {
                        ar.cause().printStackTrace();
                        context.response().setStatusCode(500).end("Error creating category" + ar.cause().getMessage());
                    }
                });
    }

    private void handleUpdateCategories(RoutingContext context) {
        String id = context.pathParam("id");
        String key = "categories:" + id;
        JsonObject body = context.getBodyAsJson();
        if (body == null) {
            context.response().setStatusCode(400).end("Invalid JSON");
            return;
        }
        try {
            String name = body.getString("name");
            String url = body.getString("url");
            String locale = body.getString("locale");
            int order = body.getInteger("order", 999);
            int isDisplayed = body.getInteger("isDisplayed", 1);
            String packageCount = body.getString("packageCount", "0");
            Long createdDate = body.getLong("createdDate", System.currentTimeMillis());

            client.preparedQuery("UPDATE categories SET name = ?, url = ?, locale = ?, `order` = ?, "
                    + "isDisplayed = ?, packageCount = ?, createdDate = ? WHERE id = ?")
                    .execute(Tuple.of(name, url, locale, order, isDisplayed, packageCount, createdDate,
                            Integer.parseInt(id)), ar -> {
                        if (ar.succeeded() && ar.result().rowCount() > 0) {
                            JsonObject updatedCategory = new JsonObject()
                                    .put("id", id)
                                    .put("name", name)
                                    .put("url", url)
                                    .put("locale", locale)
                                    .put("order", order)
                                    .put("isDisplayed", isDisplayed)
                                    .put("packageCount", packageCount)
                                    .put("createdDate", createdDate);
                            memCache.setJson(key, updatedCategory.encode(), 60);
                            context.response().putHeader("content-type", "application/json").end(updatedCategory.encode());
                        } else {
                            context.response().setStatusCode(404).end("Category not found");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            context.response().setStatusCode(500).end("Error updating category" + e.getMessage());
        }
    }

    private void handleDeleteCategories(RoutingContext context) {
        String categoriesId = context.pathParam("id");
        String key = "categories:" + categoriesId;

        client.preparedQuery("DELETE FROM categories WHERE id = ?")
                .execute(Tuple.of(Integer.parseInt(categoriesId)), res -> {
                    if (res.succeeded() && res.result().rowCount() > 0) {
                        memCache.delete(key);
                        context.response().end("Category deleted");
                    } else {
                        context.response().setStatusCode(500).end("Category not found");
                    }
                });
    }

    //Packages
    private void handleGetPackages(RoutingContext context) {
        String packageId = context.pathParam("id");
        String key = "packages:" + packageId;

        memCache.getJson(key).onSuccess(cached -> {
            if (cached != null) {
                context.response().putHeader("content-type", "application/json").end(cached.toString());
            } else {
                client.preparedQuery("select * from packages where id = ?")
                        .execute(Tuple.of(Integer.parseInt(packageId)), ar -> {
                            if (ar.succeeded() && ar.result().size() > 0) {
                                JsonObject packages = ar.result().iterator().next().toJson();
                                memCache.setJson(key, packages.encode(), 60);
                                context.response().putHeader("content-type", "application/json").end(packages.encode());
                            } else {
                                context.response().setStatusCode(404).end("Error retrieving data from Memcached");
                            }
                        });
            }
        }).onFailure(err -> {
            context.response().setStatusCode(500).end("Error retrieving data from Memcached" + err.getMessage());
        });
    }

    private void handlePostPackages(RoutingContext context) {
        JsonObject body = context.body().asJsonObject();
        if (body == null) {
            context.response().setStatusCode(400).end("Invalid JSON");
            return;
        }
        String name = body.getString("name");
        String creatorName = body.getString("creatorName");
        int stickerCount = body.getInteger("stickerCount", 0);
        int addWhatsApp = body.getInteger("addWhatsApp", 0);
        int addTelegram = body.getInteger("addTelegram", 0);
        int viewCount = body.getInteger("viewCount", 0);
        String categoryIds = body.getString("categoryIds");
        int isDisplayed = body.getInteger("isDisplayed", 0);
        Long createdDate = body.getLong("createdDate", System.currentTimeMillis());
        String locale = body.getString("locale");
        int order = body.getInteger("order", 999);
        int isPremium = body.getInteger("isPremium", 0);
        int isAnimated = body.getInteger("isAnimated", 0);

        client.preparedQuery("INSERT INTO packages (name, creatorName, stickerCount, addWhatsApp, addTelegram, viewCount, categoryIds, isDisplayed,"
                + " createdDate, locale, `order`, isPremium, isAnimated)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(name, creatorName, stickerCount, addWhatsApp, addTelegram, viewCount, categoryIds, isDisplayed,
                        createdDate, locale, order, isPremium, isAnimated), ar -> {
                            if (ar.succeeded()) {
                                int id = Math.toIntExact(ar.result().property(MySQLClient.LAST_INSERTED_ID));
                                JsonObject packages = new JsonObject()
                                        .put("name", name)
                                        .put("creatorName", creatorName)
                                        .put("stickerCount", stickerCount)
                                        .put("addWhatsApp", addWhatsApp)
                                        .put("addTelegram", addTelegram)
                                        .put("viewCount", viewCount)
                                        .put("categoryIds", categoryIds)
                                        .put("isDisplayed", isDisplayed)
                                        .put("createdDate", createdDate)
                                        .put("locale", locale)
                                        .put("order", order)
                                        .put("isPremium", isPremium)
                                        .put("isAnimated", isAnimated);
                                memCache.setJson("packages: " + id, packages.encode(), 60);
                                context.response().setStatusCode(201).end(packages.encode());
                            } else {
                                ar.cause().printStackTrace();
                                context.response().setStatusCode(500).end("Error updating category" + ar.cause().getMessage());
                            }
                        });
    }

    private void handleUpdatePackages(RoutingContext context) {
        String id = context.pathParam("id");
        String key = "packages:" + id;
        JsonObject body = context.body().asJsonObject();
        try {
            String name = body.getString("name");
            String creatorName = body.getString("creatorName");
            int stickerCount = body.getInteger("stickerCount", 0);
            int addWhatsApp = body.getInteger("addWhatsApp", 0);
            int addTelegram = body.getInteger("addTelegram", 0);
            int viewCount = body.getInteger("viewCount", 0);
            String categoryIds = body.getString("categoryIds");
            int isDisplayed = body.getInteger("isDisplayed", 0);
            Long createdDate = body.getLong("createdDate", System.currentTimeMillis());
            String locale = body.getString("locale");
            int order = body.getInteger("order", 999);
            int isPremium = body.getInteger("isPremium", 0);
            int isAnimated = body.getInteger("isAnimated", 0);

            client.preparedQuery("UPDATE packages SET name = ?, creatorName = ?, stickerCount = ?, addWhatsApp = ?,"
                    + " addTelegram = ?, viewCount = ?, categoryIds = ?, isDisplayed = ?,"
                    + " createdDate = ?, locale = ?, `order` = ?, isPremium = ?, isAnimated = ? WHERE id = ?")
                    .execute(Tuple.of(name, creatorName, stickerCount, addWhatsApp, addTelegram, viewCount, categoryIds, isDisplayed,
                            createdDate, locale, order, isPremium, isAnimated, Integer.parseInt(id)), ar -> {
                        if (ar.succeeded() && ar.result().rowCount() > 0) {
                            JsonObject updatePackages = new JsonObject()
                                    .put("id", id)
                                    .put("name", name)
                                    .put("creatorName", creatorName)
                                    .put("stickerCount", stickerCount)
                                    .put("addWhatsApp", addWhatsApp)
                                    .put("addTelegram", addTelegram)
                                    .put("viewCount", viewCount)
                                    .put("categoryIds", categoryIds)
                                    .put("isDisplayed", isDisplayed)
                                    .put("createdDate", createdDate)
                                    .put("locale", locale)
                                    .put("order", order)
                                    .put("isPremium", isPremium)
                                    .put("isAnimated", isAnimated);
                            memCache.setJson(key, updatePackages.encode(), 60);
                            context.response().putHeader("content-type", "application/json").end(updatePackages.encode());
                        } else {
                            ar.cause().printStackTrace();
                            context.response().setStatusCode(404).end("Package not found");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            context.response().setStatusCode(500).end("Error updating package" + e.getMessage());
        }
    }

    private void handleDeletePackages(RoutingContext context) {
        String id = context.pathParam("id");
        String key = "packages:" + id;
        client.preparedQuery("DELETE FROM packages WHERE id = ?")
                .execute(Tuple.of(Integer.parseInt(id)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        memCache.delete(key);
                        context.response().end("Package deleted");
                    } else {
                        context.response().setStatusCode(500).end("Package not found");
                    }
                });
    }

    //Stikers
    private void handleGetStickers(RoutingContext context) {
        String stikerId = context.pathParam("id");
        String key = "stickers:" + stikerId;

        memCache.getJson(key).onSuccess(cached -> {
            if (cached != null) {
                context.response().setStatusCode(200).end(cached.toString());
            } else {
                client.preparedQuery("SELECT * FROM stickers WHERE id = ?")
                        .execute(Tuple.of(stikerId), ar -> {
                            if (ar.succeeded() && ar.result().size() > 0) {
                                JsonObject stickers = ar.result().iterator().next().toJson();
                                memCache.setJson(key, stickers.encode(), 60);
                                context.response().putHeader("content-type", "application/json").end(stickers.encode());
                            } else {
                                context.response().setStatusCode(400).end("Error getting stickers");
                            }
                        });
            }
        }).onFailure(err -> {
            context.response().setStatusCode(500).end("Error retrieving data from Memcached");
        });
    }

    private void handlePostStickers(RoutingContext context) {
        JsonObject stikers = context.body().asJsonObject();
        if (stikers == null) {
            context.response().setStatusCode(400).end("Invalid JSON");
            return;
        }
        String url = stikers.getString("url");
        int packageId = stikers.getInteger("packageId");
        String locale = stikers.getString("locale");
        int order = stikers.getInteger("order", 999);
        int viewCount = stikers.getInteger("viewCount", 0);
        Long createdDate = stikers.getLong("createdDate");
        String emojis = stikers.getString("emojis", "[]");
        int isPremium = stikers.getInteger("isPremium", 0);

        client.preparedQuery("INSERT INTO stickers (url, packageId, locale, `order`, viewCount, createdDate, emojis, isPremium) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .execute(Tuple.of(url, packageId, locale, order, viewCount, createdDate, emojis, isPremium), ar -> {
                    if (ar.succeeded()) {
                        int id = Math.toIntExact(ar.result().property(MySQLClient.LAST_INSERTED_ID));
                        JsonObject stickers = new JsonObject()
                                .put("id", id)
                                .put("url", url)
                                .put("packageId", packageId)
                                .put("locale", locale)
                                .put("order", order)
                                .put("viewCount", viewCount)
                                .put("createdDate", createdDate)
                                .put("emojis", emojis)
                                .put("isPremium", isPremium);
                        memCache.setJson("stickers:" + id, stickers.encode(), 60);
                        context.response().setStatusCode(201).end(stickers.encode());
                    } else {
                        ar.cause().printStackTrace();
                        context.response().setStatusCode(500).end("Error creating sticker" + ar.cause().getMessage());
                    }
                });
    }

    private void handleUpdateStickers(RoutingContext context) {
        String stickerId = context.pathParam("id");
        String key = "Stikers:" + stickerId;
        JsonObject stickers = context.body().asJsonObject();
        if (stickers == null) {
            context.response().setStatusCode(400).end("Invalid Stickers");
            return;
        }

        try {
            String url = stickers.getString("url");
            int packageId = stickers.getInteger("packageId");
            String locale = stickers.getString("locale");
            int order = stickers.getInteger("order", 999);
            int viewCount = stickers.getInteger("viewCount", 0);
            Long createdDate = stickers.getLong("createdDate");
            String emojis = stickers.getString("emojis", "[]");
            int isPremium = stickers.getInteger("isPremium", 0);

            client.preparedQuery("UPDATE stickers SET url = ?, packageId = ?, locale = ?, `order` = ?, viewCount = ?,"
                    + "createdDate = ?, emojis = ?, isPremium = ? WHERE id = ?")
                    .execute(Tuple.of(url, packageId, locale, order, viewCount, createdDate, emojis, isPremium, Integer.parseInt(stickerId)), ar -> {
                        if (ar.succeeded() && ar.result().rowCount() > 0) {
                            JsonObject updatedStickers = new JsonObject()
                                    .put("id", stickerId)
                                    .put("url", url)
                                    .put("packageId", packageId)
                                    .put("locale", locale)
                                    .put("order", order)
                                    .put("viewCount", viewCount)
                                    .put("createdDate", createdDate)
                                    .put("emojis", emojis)
                                    .put("isPremium", isPremium);
                            memCache.setJson(key, updatedStickers.encode(), 60);
//                            context.response().setStatusCode(200).putHeader("").end(updatedStickers.encode());
                            context.response().putHeader("content-type", "application/json").end(updatedStickers.encode());
                        } else {
                            context.response().setStatusCode(404).end("Sticker not found");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            context.response().setStatusCode(500).end("Error updating sticker" + e.getMessage());
        }
    }

    private void handleDeleteStickers(RoutingContext context) {
        String stickerId = context.pathParam("id");
        String key = "stickers:" + stickerId;
        client.preparedQuery("DELETE FROM stickers WHERE id = ?")
                .execute(Tuple.of(Integer.parseInt(stickerId)), ar -> {
                    if (ar.succeeded() && ar.result().rowCount() > 0) {
                        memCache.delete(key);
                        context.response().putHeader("content-type", "application/json").end("Sticker deleted");
                    } else if (ar.succeeded()) {
                        context.response().setStatusCode(404).end("Stikers not found");
                    } else {
                        context.response().setStatusCode(500).end("Error deleting sticker");
                    }
                });
    }

}
