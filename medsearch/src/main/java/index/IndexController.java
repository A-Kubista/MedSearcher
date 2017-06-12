package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.io.Console;
import java.util.*;

import article.ArticleContainer;
import article.ArticleController;
import article.ArticleModel;

import ch.obermuhlner.jhuge.collection.HugeArrayList;
import org.apache.lucene.search.Weight;
import textProcessing.*;
import textProcessing.Dictionary;
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
        }else {
            String filter = request.queryParams("filter");
            int filter_int = 0;
            if (filter != null) {
                try {
                    filter_int = Integer.parseInt(filter);
                    switch (filter_int) {
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
                        case 4:
                            filter = "Lucene";
                            break;
                    }
                } catch (Exception e) {

                }
            } else {
                filter = "TF";
            }


            TextProcessingController mainController = prepareData(request);
            //mainController.processQuery(query);
            mainController.processQuery(query);
            System.out.println("Przeprocesowano zapytanie...");

            ArrayList<QueryWeight> query_weights = new ArrayList<>();
            StringBuilder query_terms_builder = new StringBuilder();
            StringBuilder weights_builder = new StringBuilder();
                for (Term query_term : mainController.getQuery().getIndexedQuery()) {
                    QueryWeight query_weigth = new QueryWeight(query_term.getName(), mainController.getQuery().getWeights().get(query_term));
                    query_weights.add(query_weigth);

                    weights_builder.append("<p>");
                    weights_builder.append(query_term.getName());
                    weights_builder.append(mainController.getQuery().getWeights().get(query_term));
                    weights_builder.append("</p>");
                    query_terms_builder.append(" ");
                    query_terms_builder.append(query_term.getName());
                    query_terms_builder.append(",");
                }
            query_terms_builder.deleteCharAt(query_terms_builder.length() - 1);
            model.put("query",mainController.getQuery().getQueryString());
            model.put("sort_text", filter);
            model.put("search_terms",query_terms_builder.toString());
            model.put("ws",weights_builder.toString());
            model.put("roots",mainController.getQuery().getTermsTrees());
     //      model.put("qw",query_weights);
            model.put("articles",mainController.getSortedArticles());
        }


        model.put("is_query",is_query);
        model.put("templateName","search_result.ftl");

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
