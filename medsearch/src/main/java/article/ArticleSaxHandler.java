package article;

import ch.obermuhlner.jhuge.collection.HugeArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by wilek on 2017-05-27.
 */


public class ArticleSaxHandler extends DefaultHandler {

        //List to hold Employees object
        private HugeArrayList<ArticleModel> empList = new HugeArrayList.Builder<ArticleModel>().build();
        private ArticleModel emp = null;
        private int article_count;
        private int counter = 0;
        private int start_index =0;


    public ArticleSaxHandler(int article_count,int start_index) {
        this.article_count = article_count;
        this.start_index = start_index;
    }

    public ArticleSaxHandler() {
        this.start_index = 0;
        this.article_count = 99999;
    }


    //getter method for employee list
        public HugeArrayList<ArticleModel> getEmpList() {
            return empList;
        }

        boolean title = false;
        boolean last_name = false;
        boolean date = false;
        boolean content = false;
        boolean keyword =false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            if (qName.equalsIgnoreCase("PubmedArticle")) {
                 if(counter >= start_index  && counter < article_count) {
                    //create a new Employee and put it in Map
                    String id = attributes.getValue("id");
                    //initialize Employee object and set id attribute
                    emp = new ArticleModel();
                    emp.setId("" + counter);
                    //   emp.setId(Integer.parseInt(id));
                    //initialize list
                }
            }else {
                if(counter >= start_index  && counter < article_count) {
                    if (qName.equalsIgnoreCase("ArticleTitle")) {
                        //set boolean values for fields, will be used in setting Employee variables
                        title = true;
                    } else if (qName.equalsIgnoreCase("LastName")) {
                        last_name = true;
                    } else if (qName.equalsIgnoreCase("DateCreated")) {
                        date = true;
                    } else if (qName.equalsIgnoreCase("AbstractText")) {
                        content = true;
                    }else if (qName.equalsIgnoreCase("Keyword")) {
                        keyword = true;
                    }
                }
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName){
                if (qName.equalsIgnoreCase("PubmedArticle")) {
                    if(counter >= start_index && counter < article_count) {
                        //add Employee object to list
                        empList.add(emp);
                    }
                    counter++;
                } else if(counter >= start_index && counter < article_count) {
                    if (qName.equalsIgnoreCase("DateCreated")) {
                        date = false;
                    }else if (qName.equalsIgnoreCase("LastName")) {
                        last_name = false;
                    }
                    else if (qName.equalsIgnoreCase("AbstractText")) {
                        content = false;
                    }
                    else if (qName.equalsIgnoreCase("Keyword")) {
                        keyword = false;
                    }
                }
        }

        @Override
        public void characters(char ch[], int start, int length){
            if(counter >= start_index && counter < article_count) {
                if (title) {
                    //age element, set Employee age
                    emp.setTitle(new String(ch, start, length));
                    title = false;
                } else if (last_name) {
                    emp.setAuthor(emp.getAuthor(this) +" "+ new String(ch, start, length));
                    last_name = false;
                } else if (date) {
                    String tmp = new String(ch, start, length);
                    if (!(tmp.isEmpty() || tmp.equals("null")))
                        emp.setDate(emp.getDate(this)  + tmp);
                } else if (content) {
                    emp.setContent(emp.getContent() + "<p>     " + new String(ch, start, length) + "</p>");
                    content = false;
                }
                else if (keyword) {
                    emp.getKeyWords().add(new String(ch, start, length));
                    keyword = false;
                }
            }

        }
}
