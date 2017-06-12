package textProcessing;

import lombok.Data;

import java.util.*;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class Query {
    private String queryString;
    private List<Term> indexedQuery;
    private Map<Term,Double> weights;
    private Dictionary MESHDictionary;
    private Map<Term,Double> vectorTF;
    private Map<Term,Double> vectorIDF;
    private Map<Term,Double> vectorTFweighted;
    private Map<Term,Double> vectorIDFweighted;

    private List<CategoryTree> termsTrees;

    public Query(String queryString, Dictionary MESHDictionary, SortedSet<Term> dictionary, Map<Term,Double> idfs){
        this.queryString = queryString;
        this.indexedQuery = Indexer.indexText(queryString,MESHDictionary);
        this.MESHDictionary = MESHDictionary;

        vectorTF = Indexer.createTFVector(dictionary,indexedQuery);
        vectorIDF = Indexer.timesVectors(dictionary,idfs,vectorTF);

        weights = new HashMap<>();
        for(Term t: indexedQuery){
            weights.put(t,1.0);
        }

        countWeightedVectors(dictionary);
        findMESHparentsAndChildrens();

    }

    public String toString(){
        String result = "";
        for(Term t: indexedQuery){
            result = result+t+", ";
        }
        result = result+"\n";
        for(CategoryTree ct: this.termsTrees){
            result = result + ct.getTitle()+":\n";
            for(Tree t: ct.getTrees()){
                result = result +"\t"+t.getParent()+"\n\t\t"+t.getMainTerm()+"\n";
                for(Term child: t.getChildren()){
                    result = result +"\t\t\t"+ child +"\n";
                }
            }
        }

        return result;
    }

    private void countWeightedVectors(SortedSet<Term> dictionary){
        vectorIDFweighted = Indexer.timesVectors(dictionary,vectorIDF,weights);
        vectorTFweighted = Indexer.timesVectors(dictionary,vectorTF,weights);
    }

    public void changeWeights(SortedSet<Term> dictionary, Map<Term,Double> weights){
        this.weights = weights;
        countWeightedVectors(dictionary);
    }

    private void findMESHparentsAndChildrens(){
        this.termsTrees = new ArrayList<>();
        for(Term term: indexedQuery){
            if(term instanceof DictionaryTerm){
                DictionaryTerm dTerm = (DictionaryTerm) term;
                CategoryTree categoryTree = new CategoryTree();
                categoryTree.setTitle(dTerm);
                for(String number: dTerm.getTreeIndexes()){
                    Tree tmpTree = new Tree();
                    tmpTree.setMainTerm(dTerm);
                    tmpTree.setParent(MESHDictionary.findParent(number));
                    tmpTree.setChildren(MESHDictionary.findChildren(number));
                    categoryTree.addTree(tmpTree);
                }
                this.termsTrees.add(categoryTree);
            }
        }
    }

}
