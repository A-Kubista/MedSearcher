package textProcessing;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Terms;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
public class Indexer {


    private static int TERM_MAX_LENGTH = 10;

    public static List<Term> indexText(String text, Dictionary dictionary){
        List<Term> res = new ArrayList<>();
        if(text==null || text.equals("")) return res;

        List<String> tokens = new ArrayList<>();
        try {
            tokens = stemming(tokenizeAndStopping(text));
        }catch (Exception ex){
            System.exit(999);
        }


        for(int len=TERM_MAX_LENGTH;len>0;len--){
            for(int i=0;i<=tokens.size()-len;i++){
                List<String> sublist = tokens.subList(i,i+len);
                Term testTerm = new Term(concatStrings(sublist), false);
                DictionaryTerm dTerm = dictionary.findTerm(testTerm);
                if(dTerm!=null){
                    res.add(dTerm);
                    for(int count=len;count>0;count--) tokens.remove(i);
                    i--;
                }
            }
        }

        res.addAll(stringsToTerms(tokens));

        return res;
    }

    private static List<Term> stringsToTerms(List<String> list){
        List<Term> res = new ArrayList<>();
        for (String s: list){
            res.add(new Term(s,false));
        }
        return res;
    }

    private static List<String> tokenizeAndStopping(String text) throws IOException{
        List<String> result = new ArrayList<>();
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(text));
        CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);

        tokenStream.reset();
        while(tokenStream.incrementToken()) {
            result.add(term.toString());
        }
        return result;
    }

    private static List<String> stemming(List<String> words){
        List<String> result = new ArrayList<>();
        EnglishStemmer english = new EnglishStemmer();
        for(String word: words){
            english.setCurrent(word);
            english.stem();
            String term = english.getCurrent();
            result.add(term);
        }
        return result;
    }

    public static String normalizeTerm(String s){
        String res = "";
        try {
            res = concatStrings(stemming(tokenizeAndStopping(s)));
        }catch(Exception ex){
            System.exit(999);
        }
        return res;
    }

    private static String concatStrings(List<String> list){
        String res = "";
        for (String word: list) {
            if(!res.equals("")) res = res +" ";
            res = res + word;
        }
        return res;
    }

    private static List<String> stemming(Set<String> words, boolean duplicates){
        List<String> result = new ArrayList<>();
        EnglishStemmer english = new EnglishStemmer();
        for(String word: words){
            english.setCurrent(word);
            english.stem();
            String term = english.getCurrent();
            if(duplicates || !result.contains(term))result.add(term);
        }

        for(String s: result){
            System.out.print("["+s+"] ");
        }
        System.out.print("\n");

        return result;
    }

    public static Map<Term,Double> createTFVector(SortedSet<Term> dictionary, List<Term> index){
        Map<Term,Double> res = createVector(dictionary);

        for(Term t: index){
            res.computeIfPresent(t,(k,v)->v+1.0);
        }
        return res;
    }

    public static Map<Term,Double> timesVectors(SortedSet<Term> dictionary, Map<Term,Double> vector1, Map<Term,Double> vector2){
        Map<Term,Double> res = createVector(dictionary);
        Set<Term> keys = vector1.keySet();
        for(Term k: keys){
            res.put(k,(vector1.get(k)==null ? 0.0 : vector1.get(k))*(vector2.get(k)==null ? 0.0 : vector2.get(k)));
        }
        return res;
    }

    public static Map<Term,Double> createVector(SortedSet<Term> dictionary){
        Map<Term,Double> res = new HashMap(dictionary.size());

        for(Term t: dictionary){
            res.put(t,0.0);
        }
        return res;
    }

    public static Map<Term,Double> sumVectors(Map<Term,Double> vector1, Map<Term,Double> vector2){
        Map<Term,Double> res = new HashMap<>();
        Set<Term> terms = vector1.keySet();
        for(Term t: terms){
            res.put(t,vector1.get(t)+vector2.get(t));
        }
        return res;
    }

    public static double cosineSimilarity(Map<Term,Double> vector1, Map<Term,Double> vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        Set<Term> terms = vector1.keySet();
        for(Term t: terms){
            dotProduct += vector1.get(t) * vector2.get(t);
            norm1 += Math.pow(vector1.get(t), 2);
            norm2 += Math.pow(vector2.get(t), 2);
        }

        double res = dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));

        return (Double.isNaN(res))? 0.0 : res;
    }

    public static double countLTI(Map<Term,Double> keyWords, Map<Term,Double> content, Map<Term,Double> title, Map<Term,Double> query){
        double LTI, TFtitle, TFcontent, TFkeyWords;

        TFtitle = Indexer.cosineSimilarity(title,query);
        TFcontent = Indexer.cosineSimilarity(content,query);
        TFkeyWords = Indexer.cosineSimilarity(keyWords,query);

        LTI = ((TFtitle>TFkeyWords)?TFtitle:TFkeyWords)*TextProcessingConstants.CONTENT_SIGNIFICANCE;
        LTI = LTI + (1.0-LTI)*TFcontent;

        return LTI;
    }

}
