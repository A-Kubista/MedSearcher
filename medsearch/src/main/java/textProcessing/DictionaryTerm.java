package textProcessing;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class DictionaryTerm extends Term implements Serializable{
    private List<String> treeIndexes;
    private List<Term> synonyms;

    public static final int MAX_LENGTH = 10;

    public DictionaryTerm(String name){
        super(name);
        this.treeIndexes = new ArrayList<>();
        this.synonyms = new ArrayList<>();
        this.addSynonym(this.name);
    }

    public void addTreeIndex(String index){
        treeIndexes.add(index);
    }

    public void addSynonym(Term term){
        synonyms.add(term);
    }

    public void addSynonym(String string){
        synonyms.add(new Term(string));
    }

    @Override
    public boolean equals(Object obj){
        //System.out.println("\t\tDictionaryTerm.equals: \n\t\t\t["+obj.getClass()+"]"+obj.toString()+"\n\t\t\t["+this.getClass()+"]"+this.toString());
        if(obj instanceof Term){
            return synonyms.contains(obj);
        }
        return super.equals(obj);
    }

    public String toString(){
        String res = name+"\n\tIndex tree ("+treeIndexes.size()+")\n";
        for (String s: treeIndexes) {
            res = res + "\t\t "+s+"\n";
        }
        res = res+"\tSynonyms ("+synonyms.size()+")\n";
        for (Term s: synonyms) {
            res = res + "\t\t "+s+"\n";
        }
        return res;
    }

    public static void addToMap(Map<String,DictionaryTerm> map, DictionaryTerm term){
        for(Term synonym: term.getSynonyms()){
            map.put(synonym.getNormalizedName(),term);
        }
    }
}
