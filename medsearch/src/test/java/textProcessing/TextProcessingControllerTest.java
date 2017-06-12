package textProcessing;

import article.ArticleContainer;
import article.ArticleController;
import article.ArticleModel;
import spark.Request;

import java.util.ArrayList;
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
        controller.processQuery("migraine treatment");

        TextProcessingController.sort(TextProcessingConstants.SortingType.SORT_BY_DMI, controller.getSortedArticles());

        List<ArticleContainer> sortedArticles = controller.getSortedArticles();

        for(ArticleContainer as: sortedArticles){
            System.out.println(as);
        }

        List<Double> weights = new ArrayList<>();
        weights.add(0.5);
        weights.add(1.0);

        TextProcessingController.changeWeights(weights,controller.getQuery(),sortedArticles,controller.getDataContainer().getDictionary());

        System.out.println("\n\n\n\n");
        for(ArticleContainer as: sortedArticles){
            System.out.println(as);
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