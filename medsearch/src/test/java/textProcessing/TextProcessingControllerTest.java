package textProcessing;

import article.ArticleContainer;
import article.ArticleController;
import article.ArticleModel;
import spark.Request;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.Assert.*;

/**
 * Created by LU on 2017-06-03.
 */
public class TextProcessingControllerTest {
    @org.junit.Test
    public void test() throws Exception {

        TextProcessingController controller = new TextProcessingController(ArticleModel.testArticles(),Dictionary.testDictionary());
        //TextProcessingController controller = new TextProcessingController(articles,Dictionary.testDictionary());
        controller.processQuery("treatment migraine");

        for(ArticleContainer as: controller.getSortedArticles()){
            System.out.println(as);
            double TFtitle = Indexer.cosineSimilarity(as.getVectorTFTitle(),controller.getQuery().getVectorTFweighted());
            double TFcontent = Indexer.cosineSimilarity(as.getVectorTFContent(),controller.getQuery().getVectorTFweighted());
            double TFkeys = Indexer.cosineSimilarity(as.getVectorTFKeyWords(),controller.getQuery().getVectorTFweighted());
            System.out.println("Title: "+TFtitle+"\tContent: "+TFcontent+"\tKeywords: "+TFkeys);
            System.out.println("\n\n\n");
        }

        /*for(ArticleContainer as: controller.getSortedArticles()){
            for(Term t: controller.getDataContainer().getDictionary()){
                System.out.print(as.getArticle().getVectorTF().get(t)+"\t");
            }
            System.out.println();
        }*/

        /*System.out.println("\n"+controller.getQuery());
        for(Term t: controller.getDataContainer().getDictionary()){
            System.out.print(t+"\t");
            for(ArticleContainer a: controller.getSortedArticles()){
                System.out.print(a.getArticle().getVectorTF().get(t)+"\t");
            }
            System.out.print(controller.getQuery().getVectorTF().get(t)+"\n");
        }*/
        //System.out.println();
        //for(Term t: controller.getDataContainer().getDictionary()){
        //    if(controller.getQuery().getVectorTF().get(t)>0.0) System.out.println(t);
        //}

        //System.out.println("\n\n\n\n");
        //for(Term t: controller.getDataContainer().getDictionary()){
        //    System.out.println(t);
        //}

    }


}