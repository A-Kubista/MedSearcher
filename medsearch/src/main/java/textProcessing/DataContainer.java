package textProcessing;

import article.ArticleContainer;
import article.ArticleModel;
import lombok.Data;

import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class DataContainer {
    private Dictionary meshDictionary;
    private List<ArticleModel> allArticles;


    private SortedSet<Term> dictionary;
    private List<ArticleModel> filteredArticles;
    private Map<Term,Double> vectorTF;
    private Map<Term,Double> vectorIDF;

    private List<ArticleContainer> sortedArticles;

    private int sortingType = 0;


    public DataContainer(Dictionary meshDictionary, List<ArticleModel> articles){
        this.allArticles = articles;
        this.meshDictionary = meshDictionary;

        this.dictionary = new TreeSet<>();
        this.filteredArticles = new ArrayList<>();
        this.vectorTF = new HashMap<>();
        this.vectorIDF = new HashMap<>();
        this.sortedArticles = new ArrayList<>();
    }

    public void prepareDataForQuery(String queryString){
        this.resetFilter();
        this.filterArticles(queryString);

        this.indexArticles();
        this.prepareDictionary();

        this.prepareVectors();
    }

    public void prepareRanking(Query query){
        for(ArticleContainer ac: this.sortedArticles){
            ac.countMeasurements(query);
        }

        /*

        ArticleContainer.sortArticleContainers(TextProcessingConstants.SortingType.SORT_BY_LTI, sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setLTInumber(i+1);

        ArticleContainer.sortArticleContainers(TextProcessingConstants.SortingType.SORT_BY_DMI, sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setDMInumber(i+1);

        ArticleContainer.sortArticleContainers(TextProcessingConstants.SortingType.SORT_BY_IDF, sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setIDFnumber(i+1);

        ArticleContainer.sortArticleContainers(TextProcessingConstants.SortingType.SORT_BY_TF, sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setTFnumber(i+1);

        */

        ArticleContainer.sortArticleContainers(sortingType, sortedArticles);
        for(int i = 0;i<sortedArticles.size();i++) sortedArticles.get(i).setLTInumber(i+1);

     //   sortingType = 0;

    }

    private void indexArticles(){
        for(ArticleModel a: filteredArticles){
            ArticleContainer ac = new ArticleContainer(a,meshDictionary);
            sortedArticles.add(ac);
        }
    }

    private void resetFilter(){
        dictionary.clear();
        filteredArticles.clear();
        vectorTF.clear();
        vectorIDF.clear();
        sortedArticles.clear();
    }

    private void filterArticles(String query){
        this.filteredArticles = LuceneFIlter.filterArticles(query,allArticles);
    }

    private void prepareDictionary(){
        dictionary.clear();

        for (ArticleModel article: filteredArticles) {
            List<Term> terms = article.getIndexedAll();
            for(Term qt: terms){
                dictionary.add(qt);
            }
        }
    }

    private void prepareVectors(){

        for (ArticleContainer article: sortedArticles) {
            article.createTFVectors(dictionary);
            article.createDMIVector();
        }

        this.vectorTF = Indexer.createVector(dictionary);
        this.vectorIDF = Indexer.createVector(dictionary);

        for (ArticleContainer article: sortedArticles) {
            Map<Term,Double> terms = article.getVectorTF();
            for(Term t: dictionary){
                Double number = terms.get(t);
                if(number.doubleValue()>0.0){
                    vectorTF.computeIfPresent(t,(k,v)->v+number);
                    vectorIDF.computeIfPresent(t,(k,v)->v+1.0);
                }
            }
        }

        for(Term t: dictionary){
            vectorIDF.computeIfPresent(t,(k,v)->Math.log(v==0.0 ? 1.0 : ((double) dictionary.size())/v));
        }

        for (ArticleContainer article: sortedArticles) {
            article.createIDFVectors(this.vectorIDF, this.dictionary);
        }
    }
}
