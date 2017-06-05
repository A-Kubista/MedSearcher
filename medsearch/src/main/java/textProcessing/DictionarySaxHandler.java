package textProcessing;

import article.ArticleModel;
import ch.obermuhlner.jhuge.collection.HugeArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilek on 2017-05-27.
 */


public class DictionarySaxHandler extends DefaultHandler {

        //List to hold Employees object
        private HugeArrayList<DictionaryTerm> empList = new HugeArrayList.Builder<DictionaryTerm>().build();
        private DictionaryTerm tmp = null;


    public DictionarySaxHandler() {
    }



    //getter method for employee list
        public HugeArrayList<DictionaryTerm> getEmpList() {
            return empList;
        }
        boolean name = false;
        boolean tree_number = false;
        boolean term = false;
        boolean term_string = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            if (qName.equalsIgnoreCase("DescriptorName")) {
                name = true;
            }else if (qName.equalsIgnoreCase("TreeNumber")) {
                //set boolean values for fields, will be used in setting Employee variables
                tree_number = true;
            } else if (qName.equalsIgnoreCase("Term")) {
                term = true;
            } else if (qName.equalsIgnoreCase("String") && term) {
                term_string = true;
                term = false;
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName){
            if (qName.equalsIgnoreCase("DescriptorRecord")) {
                empList.add(tmp);
            }
        }

        @Override
        public void characters(char ch[], int start, int length){
                if (name) {
                    tmp = new DictionaryTerm( new String(ch, start, length));
                } else if (tree_number) {
                    tmp.addTreeIndex(new String(ch, start, length));
                    tree_number = false;
                } else if (term_string) {
                   tmp.addSynonym(new String(ch, start, length));
                    term_string = false;
                }
        }
}
