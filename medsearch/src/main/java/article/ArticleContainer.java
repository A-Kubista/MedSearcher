package article;

import lombok.Data;
import textProcessing.Indexer;
import textProcessing.Query;
import textProcessing.TextProcessingConstants;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class ArticleContainer {
    private ArticleModel article;
    private double IDF = 0.0;
    private double TF = 0.0;
    private double DMI = 0.0;
    private double LTI = 0.0;
    private int IDFnumber = 0;
    private int TFnumber = 0;
    private int DMInumber = 0;
    private int LTInumber = 0;

    public ArticleContainer(ArticleModel article, Query query){
        this.article = article;
        IDF = Indexer.cosineSimilarity(article.getVectorIDF(),query.getVectorIDFweighted());
        TF = Indexer.cosineSimilarity(article.getVectorTF(),query.getVectorTFweighted());
        DMI = Indexer.cosineSimilarity(article.getVectorDMI(),query.getVectorTFweighted());
        LTI = Indexer.countLTI(article.getVectorTFKeyWords(),article.getVectorTFContent(),article.getVectorTFTitle(),query.getVectorTF());
    }

    public String toString(){
        return "TF: "+TF+" ("+TFnumber+")\t\tIDF:"+IDF+" ("+IDFnumber+")\tDMI: "+
                DMI+" ("+DMInumber+")\tLTI: "+LTI+" ("+LTInumber+")\n" +article;
    }

    public static void sortArticleContainers(TextProcessingConstants.SortingType typeOfSorting, List<ArticleContainer> articles){
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
