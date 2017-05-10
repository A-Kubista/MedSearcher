package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import twitter4j.*;
import util.*;
import spark.*;
import java.util.*;



/**
 * Created by wilek on 2017-04-15.
 */



public class IndexController {
    public static final int TWEET_STARTING_COUNT = 20;

    /**
     *  Integration with Twitter Api
     *  tries to get filter and chanel for query
     */
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        model.put("templateName","search_result.ftl");

        return ViewUtil.render(request, model, Path.Template.INDEX);
    };


}
