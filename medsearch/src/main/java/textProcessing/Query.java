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

    private Map<Term,List<DictionaryTerm>> parents;
    private Map<Term,List<List<DictionaryTerm>>> children;

    public Query(String queryString, Dictionary MESHDictionary, SortedSet<Term> dictionary, Map<Term,Double> idfs){
        this.queryString = queryString;
        this.indexedQuery = Indexer.indexText(queryString,MESHDictionary);
        this.MESHDictionary = MESHDictionary;

        vectorTF = Indexer.createTFVector(dictionary,indexedQuery);
        vectorIDF = Indexer.timesVectors(dictionary,idfs,vectorTF);

        System.out.println("TUDAJ");
        for(Term t: dictionary){
            System.out.print(vectorTF.get(t)+"\t");
        }

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
        parents = new HashMap<>();
        children = new HashMap<>();
        for(Term term: indexedQuery){
            if(term instanceof DictionaryTerm){
                List<DictionaryTerm> termParents = new ArrayList();
                List<List<DictionaryTerm>> termChildrens = new ArrayList<>();
                DictionaryTerm dTerm = (DictionaryTerm) term;
                for(String number: dTerm.getTreeIndexes()){
                    termParents.add(MESHDictionary.findParent(number));
                    termChildrens.add(MESHDictionary.findChildren(number));
                }
                parents.put(term,termParents);
                children.put(term,termChildrens);
            }
        }
    }

}
