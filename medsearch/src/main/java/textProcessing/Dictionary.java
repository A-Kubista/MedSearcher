package textProcessing;

import lombok.Data;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class Dictionary implements Serializable {
    private Map<String, DictionaryTerm> terms;

    public Dictionary(int i) {

        terms = new HashMap<>();
    }

    public Dictionary() {
        DictionarySaxHandler handler = new DictionarySaxHandler();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File("./src/main/resources/mesh2017.xml"), handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        terms = handler.getEmpList();
    }

    public void addTerm(DictionaryTerm term){
        DictionaryTerm.addToMap(terms,term);
    }

    public DictionaryTerm findTerm(Term searchingTerm){
        return terms.getOrDefault(searchingTerm.getNormalizedName(),null);
    }

    public int getSize(){
        return terms.size();
    }

    public String toString(){
        String res = "";
        for(Map.Entry<String,DictionaryTerm> term: this.terms.entrySet()){
            res = res + term.getKey()+": " +term.getValue()+ "\n";
        }
        return res;
    }

    public List<DictionaryTerm> findChildren(String termNumber){
        List<DictionaryTerm> res = new ArrayList<>();

        for(Map.Entry<String,DictionaryTerm> term: this.terms.entrySet()){
            for(String number: term.getValue().getTreeIndexes()){
                if(number.matches("^"+termNumber+"\\.[0-9]+$") && !res.contains(term.getValue())) res.add(term.getValue());
            }
        }

        return res;
    }

    public DictionaryTerm findParent(String termNumber){
        String searchingNumber = termNumber.replaceAll("\\.[0-9]+$","");
        for(Map.Entry<String,DictionaryTerm> term: this.terms.entrySet()){
            for(String number: term.getValue().getTreeIndexes()){
                if(number.equals(searchingNumber)) return term.getValue();
            }
        }
        return null;
    }

    public static Dictionary testDictionary(){
        Dictionary dic = new Dictionary(1);
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

        term = new DictionaryTerm("Vitamin A");
        term.addSynonym("A Vitamin");
        term.addTreeIndex("G11.15.9");
        dic.addTerm(term);

        return dic;
    }

    public void printDictionary(){
        List<DictionaryTerm> res = new ArrayList<>();
        for(Map.Entry<String,DictionaryTerm> element: terms.entrySet()){
            if(!res.contains(element.getValue())){
            //    System.out.println(element.getValue());
                res.add(element.getValue());
            }
        }
    }
}
