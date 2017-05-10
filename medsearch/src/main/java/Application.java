import index.IndexController;
import util.Filters;
import util.Path;
import util.ViewUtil;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        // Configure Spark
        port(9999);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Set up before-filters (called before each get/post)
       before("*",Filters.addTrailingSlashes);


        // Set up routes
        get("/", IndexController.serveIndexPage);
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.INDEX + "/:a", IndexController.serveIndexPage);
        get("*", ViewUtil.notFound);

    }
}