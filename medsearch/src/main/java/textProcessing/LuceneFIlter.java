package textProcessing;

import article.ArticleModel;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LU on 2017-06-05.
 */
public class LuceneFIlter {

    public static List<ArticleModel> filterArticles(String query, List<ArticleModel> articles){
        try {
            return letsFilterArticles(query, articles);
        }catch(Exception ex){
            System.out.println("NIE UDAŁO SIĘ WYFILTROWAĆ ARTYKUŁÓW");
        }
        return articles;
    }

    private static List<ArticleModel> letsFilterArticles(String query, List<ArticleModel> articles) throws IOException, ParseException{
        List<ArticleModel> res = new ArrayList<>();

        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);

        for(int i=0;i<articles.size();i++){
            addDoc(w,articles.get(i),i);
        }
        w.close();

        Query q = new QueryParser("article",analyzer).parse(query);

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, TextProcessingConstants.NUMBER_OF_ARTICLES);
        ScoreDoc[] hits = docs.scoreDocs;

        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            res.add(articles.get(Integer.parseInt(d.get("No"))));
            res.get(i).setLuceneScore(hits[i].score);
        }

        System.out.println("\n\n\n\n");

        return res;
    }

    private static void addDoc(IndexWriter w, ArticleModel article, int articleNo) throws IOException {
        Document doc = new Document();
        String keyWords = "";
        for(String k: article.getKeyWords()) keyWords = keyWords +" "+ k;
        String articleText = article.getTitle() + "\n" +article.getContent() + keyWords;
        doc.add(new TextField("article", articleText, Field.Store.YES));
        doc.add(new StringField("No", Integer.toString(articleNo), Field.Store.YES));
        w.addDocument(doc);
    }

}
