package textProcessing;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilek on 2017-06-12.
 */
@Data
public class CategoryTree {
    public Term title;
    public List<Tree> trees;

    public CategoryTree(){
        trees = new ArrayList<>();
    }

    public void addTree(Tree newTree){
        trees.add(newTree);
    }

}
