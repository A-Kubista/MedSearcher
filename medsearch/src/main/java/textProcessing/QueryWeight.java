package textProcessing;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wilek on 2017-06-12.
 */
@Data
public class QueryWeight implements Serializable{
    private String val1;
    private Double val2;
    private int id;

    public QueryWeight(String name, Double weigth,int id) {
        this.val1 = name;
        this.val2 = weigth;
        this.id = id;
    }
}
