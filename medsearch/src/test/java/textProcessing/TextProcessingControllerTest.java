package textProcessing;

import article.ArticleContainer;
import article.ArticleModel;

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
        controller.processQuery("treatment");
        //controller.processQuery("has");

        for(ArticleContainer as: controller.getSortedArticles()){
            System.out.println(as);
            System.out.println("\n\n\n");
        }

        for(ArticleContainer as: controller.getSortedArticles()){
            for(Term t: controller.getDataContainer().getDictionary()){
                System.out.print(as.getArticle().getVectorTF().get(t)+"\t");
            }
            System.out.println();
        }
        System.out.println("\n"+controller.getQuery());
        for(Term t: controller.getDataContainer().getDictionary()){
            System.out.print(controller.getQuery().getVectorTF().get(t)+"\t");
        }
        //System.out.println();
        //for(Term t: controller.getDataContainer().getDictionary()){
        //    if(controller.getQuery().getVectorTF().get(t)>0.0) System.out.println(t);
        //}

        System.out.println("\n\n\n\n");
        for(Term t: controller.getDataContainer().getDictionary()){
            System.out.println(t);
        }

    }


}