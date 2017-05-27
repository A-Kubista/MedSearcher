package article;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by wilek on 2017-05-27.
 */
public class ArticleSaxHandler extends DefaultHandler {

        //List to hold Employees object
        private ArrayList<ArticleModel> empList = new ArrayList<>();
        private ArticleModel emp = null;


        //getter method for employee list
        public ArrayList<ArticleModel> getEmpList() {
            return empList;
        }

        boolean title = false;
        boolean last_name = false;
        boolean date = false;
        boolean content = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            if (qName.equalsIgnoreCase("PubmedArticle")) {
                //create a new Employee and put it in Map
                String id = attributes.getValue("id");
                //initialize Employee object and set id attribute
                emp = new ArticleModel();
             //   emp.setId(Integer.parseInt(id));
                //initialize list
            } else if (qName.equalsIgnoreCase("ArticleTitle")) {
                //set boolean values for fields, will be used in setting Employee variables
                    title = true;
            } else if (qName.equalsIgnoreCase("LastName")) {
                    last_name = true;
            }else if (qName.equalsIgnoreCase("DateCreated")) {
                    date = true;
            }else if (qName.equalsIgnoreCase("AbstractText")) {
                    content = true;
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName){
            if (qName.equalsIgnoreCase("PubmedArticle")) {
                //add Employee object to list
                empList.add(emp);
            }else if (qName.equalsIgnoreCase("DateCreated")) {
                    date = false;
            }
        }

        @Override
        public void characters(char ch[], int start, int length){

           if (title) {
                //age element, set Employee age
                emp.setArticle_name(new String(ch, start, length));
                   title = false;
            } else if (last_name) {
                emp.setAuthor(new String(ch, start, length));
                last_name = false;
            }
           else if (date) {
                   String tmp = new String(ch, start, length);
                   if(!(tmp.isEmpty() || tmp ==  null || tmp.equals("null")))
                          emp.setDate(emp.getDate() + "-" +tmp );
           }else if (content) {
                 emp.setArticle_content(emp.getArticle_content() + "-" + new String(ch, start, length));
           }


        }
}
