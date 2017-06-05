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
        dataContainer.prepareDataForQuery(queryString);
        query = new Query(queryString,dataContainer.getMeshDictionary(),dataContainer.getDictionary(),dataContainer.getVectorIDF());
        dataContainer.prepareRanking(query);
    }

    /**
     * Zmiana wag termów w zapytania
     * @param weights mapa zawierająca Termy i nowe wagi
     */
    public void changeWeights(Map<Term,Double> weights){
        query.changeWeights(dataContainer.getDictionary(),weights);
        dataContainer.prepareRanking(query);
    }

    /**
     * Zmiana sposobu sortowania
     * @param sortingType nowy typ sortowania
     */
    public void sort(int  sortingType){
        if(dataContainer.getSortingType()!=sortingType){
            ArticleContainer.sortArticleContainers(sortingType,sortedArticles);
            dataContainer.setSortingType(sortingType);
        }
    }

    public List<ArticleContainer> getSortedArticles(){
        return dataContainer.getSortedArticles();
    }

}
