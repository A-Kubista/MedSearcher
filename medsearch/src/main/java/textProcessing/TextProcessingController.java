package textProcessing;

import article.ArticleContainer;
import article.ArticleModel;
import lombok.Data;

import java.util.*;

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

    /**
     * Zmiana zapytania do procesowania
     * @param queryString nowe zapytania
     */
    public void processQuery(String queryString){
        query = new Query(queryString,dataContainer.getMeshDictionary(),dataContainer.getDictionary(),dataContainer.getVectorIDF());
        prepareRanking();
    }

    /**
     * Zmiana wag termów w zapytania
     * @param weights mapa zawierająca Termy i nowe wagi
     */
    public void changeWeights(Map<Term,Double> weights){
        query.changeWeights(dataContainer.getDictionary(),weights);
        this.prepareRanking();
    }

    /**
     * Zmiana sposobu sortowania
     * @param sortingType nowy typ sortowania
     */
    public void sort(TextProcessingConstants.SortingType sortingType){
        ArticleContainer.sortArticleContainers(sortingType,sortedArticles);
    }

    private void prepareRanking(){
        sortedArticles.clear();
        List<ArticleModel> articles = this.getArticleList();

        for(ArticleModel article: articles){
            ArticleContainer conrainer = new ArticleContainer(article,query);
            sortedArticles.add(conrainer);
        }

        sort(TextProcessingConstants.SortingType.SORT_BY_LTI);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setLTInumber(i+1);

        sort(TextProcessingConstants.SortingType.SORT_BY_DMI);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setDMInumber(i+1);

        sort(TextProcessingConstants.SortingType.SORT_BY_IDF);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setIDFnumber(i+1);

        sort(TextProcessingConstants.SortingType.SORT_BY_TF);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setTFnumber(i+1);

    }

    public List<ArticleModel> getArticleList(){
        return dataContainer.getArticles();
    }
}
