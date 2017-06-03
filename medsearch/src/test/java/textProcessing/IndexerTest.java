package textProcessing;

import java.util.List;

/**
 * Created by LU on 2017-06-02.
 */
public class IndexerTest {
    @org.junit.Test
    public void indexText() throws Exception {
        Dictionary dic = Dictionary.testDictionary();

        System.out.println(dic);

        List<Term> res = Indexer.indexText("Vitamin D Deficiency in Patients With Chronic Tension-Type Headache: A Case-Control Study.", dic);
        for (Term q: res) {
            System.out.println(q);
        }
    }

}