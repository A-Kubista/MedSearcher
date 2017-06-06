package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import article.ArticleController;
import article.ArticleModel;

import ch.obermuhlner.jhuge.collection.HugeArrayList;
import textProcessing.Dictionary;
import textProcessing.TextProcessingController;
import util.*;
import spark.*;



/**
 * Created by wilek on 2017-04-15.
 */



public class IndexController {
    public static final int ARTICLES_STARTING_COUNT = 20;

    /**
     *  Integration with Twitter Api
     *  tries to get filter and chanel for query
     */

    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String query = request.params("query");
        if(query == null)
            query = "acid";
        String filter = request.params("filter");
        int filter_int  = 0;
        if(filter != null){
            try{
                filter_int = Integer.parseInt(filter);
            }catch (Exception e){

            }
        }


        TextProcessingController mainController = prepareData(request);
        mainController.processQuery(query);
       // mainController.processQuery("migraine treatment");

        mainController.getSortedArticles();

        model.put("articles",mainController.getSortedArticles());
        model.put("query",mainController.getQuery().getQueryString());
        model.put("sort_text", filter);
        model.put("templateName","search_result.ftl");

        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    private static TextProcessingController prepareData(Request request){
        ArticleController articleController = new ArticleController();
        Dictionary MESHdictionary;
        HugeArrayList<ArticleModel> articleList = articleController.getProcessedArticles(request);
        MESHdictionary = request.session().attribute("dictionary");

        if(MESHdictionary == null) {
            //MESHdictionary = new Dictionary();
            //request.session().attribute("dictionary",MESHdictionary);
            MESHdictionary = Dictionary.testDictionary();
        }



        TextProcessingController textProcessingController = new TextProcessingController(articleList, MESHdictionary);

        return textProcessingController;
    }


}
