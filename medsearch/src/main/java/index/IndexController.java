package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import article.ArticleController;
import article.ArticleModel;

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

            //TODO: jeśli pierwsze zapytanie, to należy wczytać dane (wszytkie dane, które wystarczy pobrać i przetworzyć raz (artykuły, słownik MESH, indeksowanie dokumentów itp.)
            //TODO: mainController powinien zostać zapisane w sesji
            TextProcessingController mainController = prepareData(request);

            //TODO: przekazać do zapisanego w sesji kontrolera aktualnie przetwarzane zapytania
            mainController.processQuery("Migrain Tension-Type Headache");

            model.put("articles",mainController.getArticleList());

        model.put("templateName","search_result.ftl");

        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    //TODO: Przebudować articleController.getPorcessedArticles tak, by nie wymagała requesta
    private static TextProcessingController prepareData(Request request){

        //TODO: Kontroler artykułów chyba może być statyczny
        ArticleController articleController = new ArticleController();
        List<ArticleModel> articleList = articleController.getPorcessedArticles(request);

        //TODO: wczytanie słownika MESH z pliku mesh2017.xml - sposób budowania słownika przedstawiony został w Dictionary.testDictionary()
        //Dictionary MESHdictionary = new Dictionary();
        Dictionary MESHdictionary = Dictionary.testDictionary();

        TextProcessingController textProcessingController = new TextProcessingController(articleList, MESHdictionary);

        return textProcessingController;
    }


}
