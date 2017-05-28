package article;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import spark.Request;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wilek on 2017-05-15.
 */
public class ArticleController {

    public ArticleController() {

    }


    public ArrayList<ArticleModel> getPorcessedArticles(Request request) {
        ArrayList<ArticleModel> empList = request.session().attribute("article_list");
        int article_count = 20;
        Object last_count = request.queryParams("article_count");
        if(last_count != null)
            article_count += Integer.parseInt((String)last_count);
        if( empList == null) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try {
                SAXParser saxParser = saxParserFactory.newSAXParser();
                ArticleSaxHandler handler = new ArticleSaxHandler(article_count);
                saxParser.parse(new File("./src/main/resources/pubmed_result.xml"), handler);
                //Get Employees list
                System.out.println("zaczynam");
                empList = handler.getEmpList();
                request.session().attribute("article_list", empList);
                System.out.println("skonczylem");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }


        return empList;
    }
}
