package index;

/**
 * Created by wilek on 2017-05-10.
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import article.ArticleController;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import twitter4j.*;
import util.*;
import spark.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File("./src/main/resources/pubmed_result.xml");
            Document doc = builder.parse(file);
           NodeList articles =  doc.getElementsByTagName("PubmedArticle");

            ArticleController articleController = new ArticleController();
            model.put("articles",articleController.getPorcessedArticles(articles));
            // Do something with the document here.
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        }


        model.put("templateName","search_result.ftl");

        return ViewUtil.render(request, model, Path.Template.INDEX);
    };


}
