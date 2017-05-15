package article;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by wilek on 2017-05-15.
 */
public class ArticleController {
    NodeList articles;

    public ArticleController() {

    }

    public ArticleController(NodeList articles) {
        this.articles = articles;
    }

    public ArrayList<ArticleModel> getPorcessedArticles(NodeList articles) {
        ArrayList<ArticleModel> result = new ArrayList<>();
        ArticleModel tmpArticle = new ArticleModel();
        for( int i  = 0 ; i < 40 ; i ++){
            Element article = (Element) articles.item(i);
            Node date =  article.getElementsByTagName("DateCreated").item(0);
            StringBuilder date_builder = new StringBuilder();
            NodeList dated_children = date.getChildNodes();
            for(int j = 0 ; j < dated_children.getLength(); j++ ){
                date_builder.append(dated_children.item(j).getTextContent()).append(" ");
            }
            tmpArticle.setDate(date_builder.toString());
            tmpArticle.setArticle_name(article.getElementsByTagName("ArticleTitle").item(0).getTextContent());
            tmpArticle.setArticle_author(article.getElementsByTagName("LastName").item(0).getTextContent());
            result.add(tmpArticle);
        }
        return result;
    }
}
