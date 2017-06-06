package textProcessing;

import article.ArticleSaxHandler;
import ch.obermuhlner.jhuge.collection.HugeArrayList;
import lombok.Data;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class Dictionary implements Serializable{
    private HugeArrayList<DictionaryTerm> terms ;

    public Dictionary(int i){
        terms = new HugeArrayList.Builder<DictionaryTerm>().build();
    }

    public Dictionary(){
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

        return dic;
    }
}
