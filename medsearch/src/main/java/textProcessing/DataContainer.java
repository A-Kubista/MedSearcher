package textProcessing;

import article.ArticleModel;
import lombok.Data;

import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class DataContainer {
    private Dictionary meshDictionary;
    private SortedSet<Term> dictionary;
    private List<ArticleModel> articles;
    private Map<Term,Double> vectorTF;
    private Map<Term,Double> vectorIDF;


    public DataContainer(Dictionary meshDictionary, List<ArticleModel> articles){
        this.articles = articles;
        this.meshDictionary = meshDictionary;
        this.dictionary = new TreeSet<>();

        for(ArticleModel a: articles) a.indexArticle(meshDictionary);

        prepareDictionary();
        prepareVectors();
    }

    private void prepareDictionary(){
        dictionary.clear();

        for (ArticleModel article: articles) {
            List<Term> terms = article.getAllTerms();
            for(Term qt: terms){
                dictionary.add(qt);
            }
        }
    }

    private void prepareVectors(){
        dictionary.clear();

        for (ArticleModel article: articles) {
            List<Term> terms = article.getAllTerms();
            for(Term qt: terms){
                dictionary.add(qt);
            }
        }

        for (ArticleModel article: articles) {
            article.createTFVectors(dictionary);
        }

        this.vectorTF = Indexer.createVector(dictionary);
        this.vectorIDF = Indexer.createVector(dictionary);

        for (ArticleModel article: articles) {
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
            //System.out.println(t+"\t\t"+vectorTF.get(t)+"\t\t"+vectorIDF.get(t));
            vectorIDF.computeIfPresent(t,(k,v)->Math.log(v==0.0 ? 1.0 : ((double) dictionary.size())/v));
        }

        for (ArticleModel article: articles) {
            article.createIDFVectors(this.vectorIDF, this.dictionary);
        }
    }
}
