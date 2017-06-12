package textProcessing;

import static org.junit.Assert.*;

/**
 * Created by LU on 2017-06-07.
 */
public class DictionaryTest {
    @org.junit.Test
    public void testDictionary(){
        System.out.print("start...");
        Dictionary dictionary = new Dictionary();
        System.out.print("done ("+dictionary.getSize()+")");
    }

}