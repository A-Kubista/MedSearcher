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
    private Query query;

    public TextProcessingController(List<ArticleModel> articles, Dictionary dictionary){
        dataContainer = new DataContainer(dictionary, articles);
        query = null;
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
     * @param newWeights mapa zawierająca Termy i nowe wagi
     * @param query procesowane zapytanie (pobrane z sesji)
     * @param sortedArticles lista artykułów (pobrana z sesji)
     * @param dictionary słownik (pobrany z sesji, można go otrzymać poprzez TextProcessingController.getDataContainer().getDictionary())
     */
    public void changeWeights(Map<Term,Double> newWeights, Query query, List<ArticleContainer> sortedArticles, SortedSet<Term> dictionary){
        query.changeWeights(dictionary,newWeights);
        DataContainer container = new DataContainer(sortedArticles);
        container.prepareRanking(query);
    }

    /**
     * Zmiana sposobu sortowania
     * @param sortingType nowy typ sortowania
     * @param sortedArticles lista ArticleContainer do posortowania (pobrana z sesji)
     */
    public static void sort(int sortingType, List<ArticleContainer> sortedArticles){
        ArticleContainer.sortArticleContainers(sortingType,sortedArticles);
    }

    /**
     * Zmiana sposobu sortowania
     * @param sortingType nowy typ sortowania
     */
    public void sort(int sortingType){
        ArticleContainer.sortArticleContainers(sortingType,this.getSortedArticles());
    }

    public List<ArticleContainer> getSortedArticles(){
        return dataContainer.getSortedArticles();
    }

    public void printDictionary(){
        for(Term t: this.dataContainer.getDictionary()){
            System.out.println(t);
        }
    }

}
