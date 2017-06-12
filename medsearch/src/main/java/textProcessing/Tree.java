package textProcessing;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LU on 2017-06-12.
 */
@Data
public class Tree {
    private DictionaryTerm mainTerm;
    private DictionaryTerm parent;
    private List<DictionaryTerm> children;

    public Tree(){
        children = new ArrayList<>();
    }

    public void addChild(DictionaryTerm child){
        children.add(child);
    }
}
