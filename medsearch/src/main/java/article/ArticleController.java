package article;

import ch.obermuhlner.jhuge.collection.HugeArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import spark.Request;
import textProcessing.Dictionary;

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

    public static final int ARTICLE_COUNT_BASE = 50;

    public ArticleController() {

    }

    public HugeArrayList<ArticleModel> parseXmls(){
        HugeArrayList<ArticleModel> parsedXmls = new HugeArrayList.Builder<ArticleModel>().build();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        final File folder = new File("./src/main/resources/articles");
            for (final File fileEntry : folder.listFiles()) {
                try {
                    SAXParser saxParser = saxParserFactory.newSAXParser();
                    ArticleSaxHandler handler = new ArticleSaxHandler();
                    saxParser.parse(fileEntry, handler);
                    //Get Employees list
                    parsedXmls.addAll(handler.getEmpList());

                } catch (ParserConfigurationException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            }
            return parsedXmls;
    }

    public HugeArrayList<ArticleModel> getProcessedArticles(Request request) {
        int article_count = ARTICLE_COUNT_BASE;

        HugeArrayList<ArticleModel> empList = request.session().attribute("article_list");
        if (empList == null) {
            empList = parseXmls();
            request.session().attribute("article_list", empList);
       }

 /*
        Object last_count = request.queryParams("article_count");
        if(last_count != null)
            article_count = Integer.parseInt((String)last_count);
        if(article_count <= empList.size()){
            empList = new ArrayList<ArticleModel> (empList.subList(0,article_count));
        }else {
            request.session().attribute("article_list", empList);
        }
 */
        return empList;
    }
}
