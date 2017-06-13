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

            List<Double> weigths  = getWeigthsFromRequest(request);

            TextProcessingController mainController = prepareData(request);
            mainController.processQuery(query);
            System.out.println("Przeprocesowano zapytanie...");

            List<QueryWeight> query_weights = new ArrayList<>();
            StringBuilder query_terms_builder = new StringBuilder();
          //  StringBuilder weights_builder = new StringBuilder();
            int id = 0;
            double weigth = 0;
                for (Term query_term : mainController.getQuery().getIndexedQuery()) {
                    if(weigths.size() > id)
                        weigth = weigths.get(id);
                    else
                        weigth =  mainController.getQuery().getWeights().get(query_term);

                    QueryWeight query_weight = new QueryWeight(query_term.getName(),weigth,id);
                    query_weights.add(query_weight);

                    query_terms_builder.append(" ");
                    query_terms_builder.append(query_term.getName());
                    query_terms_builder.append(",");
                    id++;
                }

            query_terms_builder.deleteCharAt(query_terms_builder.length() - 1);

            if(weigths.size() > 0 ){
                TextProcessingController.changeWeights(weigths,mainController.getQuery(),mainController.getSortedArticles(),mainController.getDataContainer().getDictionary());
            }


            model.put("query",mainController.getQuery().getQueryString());
            model.put("sort_text", filter);
            model.put("search_terms",query_terms_builder.toString());
            model.put("factors",query_weights);
            model.put("roots",mainController.getQuery().getTermsTrees());
            model.put("articles",mainController.getSortedArticles());

            for(ArticleContainer ac: mainController.getSortedArticles()){
                System.out.println(ac+"\n\n");
            }

        }


        model.put("is_query",is_query);
        model.put("templateName","search_result.ftl");

        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    private static List<Double> getWeigthsFromRequest(Request request) {

        // brzydkie zalozenie ze wczyta je w odpowiendiej kolejnosci

        List<Double> result = new ArrayList<>();
        for (String param:request.queryParams()) {
         if(param.startsWith("factor_")){
             result.add(Double.parseDouble(request.queryParams(param)));
         }
        }
        return result;
    }

    private static TextProcessingController prepareData(Request request){
        System.out.println("Start ");
        ArticleController articleController = new ArticleController();

        List<ArticleModel> articleList;
        Dictionary MESHdictionary;

        articleList = request.session().attribute("allArticles");
        if(articleList==null){
            articleList = articleController.getProcessedArticles(request);
            System.out.println("Wczytano "+articleList.size() +" artykuły...");
            request.session().attribute("allArticles",articleList);
            System.out.println("Zserializowano artykuły...");
        }
        else System.out.println("Pobrano z sesji "+articleList.size() +" artykuły...");

        MESHdictionary = request.session().attribute("dictionary");
        if(MESHdictionary == null) {
            MESHdictionary = new Dictionary();
            System.out.println("Wczytano słownik ("+MESHdictionary.getTerms().size()+" kluczy)...");
            request.session().attribute("dictionary",MESHdictionary);
            System.out.println("Zserializowano słownik...");
        }
        else System.out.println("Pobrano z sesji słownik ("+MESHdictionary.getTerms().size()+" kluczy)...");

        TextProcessingController textProcessingController = new TextProcessingController(articleList, MESHdictionary);
        System.out.println("Utworzono kontroler...");

        return textProcessingController;
    }


}
