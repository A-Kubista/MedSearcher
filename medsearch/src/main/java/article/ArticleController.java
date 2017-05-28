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

    public static final int ARTICLE_COUNT_BASE = 20;
    public ArticleController() {

    }


    public ArrayList<ArticleModel> getPorcessedArticles(Request request) {
        int article_count = ARTICLE_COUNT_BASE;
        ArrayList empList = request.session().attribute("article_list");
        if (empList == null) {
            empList = new ArrayList<>();
        }

        Object last_count = request.queryParams("article_count");
        if(last_count != null)
            article_count = Integer.parseInt((String)last_count);
        if(article_count <= empList.size()){
            empList = new ArrayList<ArticleModel> (empList.subList(0,article_count));
        }else {

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try {
                SAXParser saxParser = saxParserFactory.newSAXParser();
                ArticleSaxHandler handler = new ArticleSaxHandler(article_count, empList.size());
                saxParser.parse(new File("./src/main/resources/pubmed_result.xml"), handler);
                //Get Employees list
                empList.addAll(handler.getEmpList());
                request.session().attribute("article_list", empList);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }

        }
        return empList;
    }
}
