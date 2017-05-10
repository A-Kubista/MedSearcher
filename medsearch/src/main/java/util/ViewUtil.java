package util;

import org.eclipse.jetty.http.*;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.modelAndView;

/**
 * Created by wilek on 2017-04-15.
 */
public class ViewUtil {


    /**
     * @param request
     * @param model
     * @param templatePath
     * @return
     * returns fullfiled html template
     */
    public static String render(Request request, Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(modelAndView(model, templatePath));
    }


    /**
     *  returns not found template
     */
    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };


}
