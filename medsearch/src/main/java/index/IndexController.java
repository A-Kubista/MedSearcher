package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.util.*;

import article.ArticleContainer;
import article.ArticleController;
import article.ArticleModel;

import ch.obermuhlner.jhuge.collection.HugeArrayList;
import textProcessing.Dictionary;
import textProcessing.TextProcessingConstants;
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
        String query = request.queryParams("query");
        boolean is_query = true; // freemarker conditinal rendering cant check string
        if(query == null) {
            query = "";
            is_query = false;
        }
        String filter = request.queryParams("filter");
        int filter_int  = 0;
        if(filter != null){
            try{
                filter_int = Integer.parseInt(filter);
                switch(filter_int){
                    case 0:
                        filter = "TF";
                    break;
                    case 1:
                        filter = "DMI";
                    break;
                    case 2:
                        filter = "IDF";
                    break;
                    case 3:
                        filter = "LTI";
                        break;
                }
            }catch (Exception e){

            }
        }else{
            filter = "TF";
        }


        TextProcessingController mainController = prepareData(request);
        //mainController.processQuery(query);
        mainController.processQuery(query);
        System.out.println("Przeprocesowano zapytanie...");

        for(ArticleContainer ac: mainController.getSortedArticles()){
            System.out.println(ac);
        }

        model.put("articles",mainController.getSortedArticles());
        model.put("is_query",is_query);
        model.put("query",mainController.getQuery().getQueryString());
        model.put("sort_text", filter);
        model.put("templateName","search_result.ftl");
        System.out.println(mainController.getSortedArticles().get(0));
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    private static TextProcessingController prepareData(Request request){
        System.out.println("Start ");
        ArticleController articleController = new ArticleController();
        Dictionary MESHdictionary;
        List<ArticleModel> articleList = articleController.getProcessedArticles(request);
        //List<ArticleModel> articleList = ArticleModel.testArticles();
        System.out.println("Wczytano artykuły...");
        MESHdictionary = request.session().attribute("dictionary");

        if(MESHdictionary == null) {
            MESHdictionary = new Dictionary();
            //request.session().attribute("dictionary",MESHdictionary);
         //   MESHdictionary = Dictionary.testDictionary();
        }
        System.out.println("Wczytano słownik...");

        TextProcessingController textProcessingController = new TextProcessingController(articleList, MESHdictionary);
        System.out.println("Utworzono kontroler...");

        return textProcessingController;
    }


}
