package article;

import lombok.Data;
import textProcessing.*;
import textProcessing.Dictionary;

import java.util.*;

import static textProcessing.TextProcessingConstants.SortingType.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class ArticleContainer {
    private ArticleModel article;

    private Map<Term,Double> vectorTF;
    private Map<Term,Double> vectorTFTitle;
    private Map<Term,Double> vectorTFContent;
    private Map<Term,Double> vectorTFKeyWords;

    private Map<Term,Double> vectorIDF;
    private Map<Term,Double> vectorIDFTitle;
    private Map<Term,Double> vectorIDFContent;
    private Map<Term,Double> vectorIDFKeyWords;

    private Map<Term,Double> vectorDMI;

    private double IDF = 0.0;
    private double TF = 0.0;
    private double DMI = 0.0;
    private double LTI = 0.0;
    private int IDFnumber = 0;
    private int TFnumber = 0;
    private int DMInumber = 0;
    private int LTInumber = 0;

    public ArticleContainer(ArticleModel article, Dictionary dictionary){
        this.article = article;
        article.indexArticle(dictionary);
    }

    public void createTFVectors(SortedSet<Term> dictionary){
        this.vectorTFTitle = Indexer.createTFVector(dictionary,article.getIndexedTitle());
        this.vectorTFContent = Indexer.createTFVector(dictionary,article.getIndexedContent());
        this.vectorTFKeyWords = Indexer.createTFVector(dictionary,article.getIndexedKeyWords());

        this.vectorTF = Indexer.sumVectors(this.vectorTFTitle,this.vectorTFContent);
        this.vectorTF = Indexer.sumVectors(this.vectorTF,this.vectorTFKeyWords);
    }

    public void createDMIVector(){

        vectorDMI = new HashMap<>();

        Set<Term> terms = this.vectorTF.keySet();
        for(Term term: terms){
            if(term instanceof DictionaryTerm){
                vectorDMI.put(term,(new Double(vectorTF.get(term)))* TextProcessingConstants.DICTIONARY_TERM_WEIGHT);
            }
            else{
                vectorDMI.put(term,new Double(vectorTF.get(term)));
            }
        }
    }

    public void createIDFVectors(Map<Term,Double> idfs,SortedSet<Term> dictionary){

        this.vectorIDFTitle = Indexer.timesVectors(dictionary,idfs,vectorTFTitle);
        this.vectorIDFContent = Indexer.timesVectors(dictionary,idfs,vectorTFContent);
        this.vectorIDFKeyWords = Indexer.timesVectors(dictionary,idfs,vectorTFKeyWords);
        this.vectorIDF = Indexer.timesVectors(dictionary,idfs,vectorTF);
    }

    public void countMeasurements(Query query){
        TF = Indexer.cosineSimilarity(this.getVectorTF(),query.getVectorTFweighted());
        IDF = Indexer.cosineSimilarity(this.getVectorIDF(),query.getVectorIDFweighted());
        DMI = Indexer.cosineSimilarity(this.getVectorDMI(),query.getVectorTFweighted());
        LTI = Indexer.countLTI(this.getVectorTFKeyWords(),this.getVectorTFContent(),this.getVectorTFTitle(),query.getVectorTFweighted());
    }


    public String toString(){
        return "TF: "+TF+" ("+TFnumber+")\t\tIDF:"+IDF+" ("+IDFnumber+")\tDMI: "+
                DMI+" ("+DMInumber+")\tLTI: "+LTI+" ("+LTInumber+")\n" +article;
    }

    public static void sortArticleContainers(int typeOfSorting, List<ArticleContainer> articles){
        switch(typeOfSorting){
            case SORT_BY_TF:
                Collections.sort(articles, new Comparator() {

                    public int compare(Object o1, Object o2) {

                        Double x1 = new Double(((ArticleContainer) o1).getTF());
                        Double x2 = new Double(((ArticleContainer) o2).getTF());
                        int sComp = x1.compareTo(x2);

                        return -sComp;
                    }});
                break;
            case SORT_BY_IDF:
                Collections.sort(articles, new Comparator() {

                    public int compare(Object o1, Object o2) {

                        Double x1 = new Double(((ArticleContainer) o1).getIDF());
                        Double x2 = new Double(((ArticleContainer) o2).getIDF());
                        int sComp = x1.compareTo(x2);

                        return -sComp;
                    }});
                break;
            case SORT_BY_DMI:
                Collections.sort(articles, new Comparator() {

                    public int compare(Object o1, Object o2) {

                        Double x1 = new Double(((ArticleContainer) o1).getDMI());
                        Double x2 = new Double(((ArticleContainer) o2).getDMI());
                        int sComp = x1.compareTo(x2);

                        return -sComp;
                    }});
                break;
            case SORT_BY_LTI:
                Collections.sort(articles, new Comparator() {

                    public int compare(Object o1, Object o2) {

                        Double x1 = new Double(((ArticleContainer) o1).getLTI());
                        Double x2 = new Double(((ArticleContainer) o2).getLTI());
                        int sComp = x1.compareTo(x2);

                        return -sComp;
                    }});
                break;
            default: break;
        }
    }

}
