package textProcessing;

import article.ArticleModel;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.Assert.*;

/**
 * Created by LU on 2017-06-02.
 */
public class DataContainerTest {
    @org.junit.Test
    public void test() throws Exception {
        DataContainer container = new DataContainer(Dictionary.testDictionary(),ArticleModel.testArticles());

        List<ArticleModel> as = container.getAllArticles();
        System.out.println("ARTICLES:");
        for(ArticleModel a: as){
            System.out.println(a+"\n\n");
        }
        System.out.println("\n\nDICTIONARY:");
        SortedSet<Term> d = container.getDictionary();
        for(Term q: d){
            System.out.println(q);
        }

        //SortedSet<Term> d = container.getDictionary();
        //Map<Term,Double> tf = container.getVectorTF();
        //Map<Term,Double> idf = container.getVectorIDF();
        //for(Term q: d){
        //    System.out.println(q+"\t"+tf.get(q)+"\t\t"+idf.get(q));
        //}
    }

}