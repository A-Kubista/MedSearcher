package textProcessing;

import article.ArticleContainer;
import article.ArticleModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class TextProcessingController {
    private DataContainer dataContainer;
    private List<ArticleContainer> sortedArticles;
    private Query query;

    public TextProcessingController(List<ArticleModel> articles, Dictionary dictionary){
        dataContainer = new DataContainer(dictionary, articles);
        sortedArticles = new ArrayList<>();
    }

    public void processQuery(String queryString){
        query = new Query(queryString,dataContainer.getMeshDictionary(),dataContainer.getDictionary(),dataContainer.getVectorIDF());
        prepareRanking();
    }

    private void prepareRanking(){
        sortedArticles.clear();
        List<ArticleModel> articles = this.getArticleList();

        for(ArticleModel article: articles){
            ArticleContainer conrainer = new ArticleContainer(article,query);
            sortedArticles.add(conrainer);
        }

        ArticleContainer.sortArticleContainers(ArticleContainer.SORT_BY_IDF,sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setIDFnumber(i+1);

        ArticleContainer.sortArticleContainers(ArticleContainer.SORT_BY_TF,sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setTFnumber(i+1);

    }

    public List<ArticleModel> getArticleList(){
        return dataContainer.getArticles();
    }
}
