package textProcessing;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class Dictionary {
    private List<DictionaryTerm> terms;

    public Dictionary(){
        terms = new ArrayList<>();
    }

    public void addTerm(DictionaryTerm term){
        terms.add(term);
    }

    public DictionaryTerm findTerm(Term searchingTerm){
        for (DictionaryTerm term: terms) {
            if(term.equals(searchingTerm)) return term;
        }
        return null;
    }

    public int getSize(){
        return terms.size();
    }

    public String toString(){
        String res = "";
        for (DictionaryTerm term: terms) {
            res = res + term + "\n";
        }
        return res;
    }

    public List<DictionaryTerm> findChildren(String termNumber){
        List<DictionaryTerm> res = new ArrayList<>();

        for(DictionaryTerm term: this.terms){
            for(String number: term.getTreeIndexes()){
                if(number.matches("^"+termNumber+"\\.[0-9]+$")) res.add(term);
            }
        }

        return res;
    }

    public DictionaryTerm findParent(String termNumber){
        String searchingNumber = termNumber.replaceAll("\\.[0-9]+$","");
        for(DictionaryTerm term: this.terms){
            for(String number: term.getTreeIndexes()){
                if(number.equals(searchingNumber)) return term;
            }
        }
        return null;
    }

    public static Dictionary testDictionary(){
        Dictionary dic = new Dictionary();
        DictionaryTerm term;

        term = new DictionaryTerm("Vitamin D");
        term.addSynonym("D Vitamin");
        term.addTreeIndex("G11.15.8");
        dic.addTerm(term);

        term = new DictionaryTerm("Headache");
        term.addTreeIndex("G12");
        dic.addTerm(term);

        term = new DictionaryTerm("Migraine");
        term.addSynonym("Tension-type headache");
        term.addTreeIndex("G12.14");
        dic.addTerm(term);

        term = new DictionaryTerm("Controled study");
        term.addSynonym("Case-Control Study");
        term.addTreeIndex("G11.15");
        dic.addTerm(term);

        return dic;
    }
}