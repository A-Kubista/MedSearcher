package textProcessing;

import article.ArticleContainer;
import article.ArticleModel;

/**
 * Created by LU on 2017-06-07.
 */
public class IwMTest {

    @org.junit.Test
    public void IwMTest() throws Exception {
        TextProcessingController controller = new TextProcessingController(ArticleModel.testArticlesIwM(),new Dictionary(2));
        controller.processQuery("informatyka, medycyna");

        System.out.println("Article number: "+controller.getDataContainer().getAllArticles().size()+"\n");
        System.out.println("Article number: "+controller.getDataContainer().getFilteredArticles().size()+"\n");
        System.out.println("Article number: "+controller.getSortedArticles().size()+"\n");

        System.out.println("QUERY: informatyka, medycyna");
        for(ArticleContainer as: controller.getSortedArticles()){
            System.out.println(as);
            //System.out.println(as.getVectorTF());
            //System.out.println(as.getVectorIDF());
        }

        controller.processQuery("informatyka, medycyna, komputer");

        System.out.println("\n\nQUERY: informatyka, medycyna, komputer");
        for(ArticleContainer as: controller.getSortedArticles()){
            System.out.println(as);
        }

        //System.out.println("\n\n\n");
        //System.out.println(controller.getDataContainer().getVectorTF()+"\n");
        //System.out.println(controller.getDataContainer().getVectorIDF()+"\n");
    }
}
