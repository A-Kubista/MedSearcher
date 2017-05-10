package util;

/**
 * Created by wilek on 2017-04-15.
 */

import spark.Filter;
import spark.Request;
import spark.Response;


/**
 * Filters for requests
 */

public class Filters {
    /**
     *  makes sure that routes are in correct form
     */
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };



}