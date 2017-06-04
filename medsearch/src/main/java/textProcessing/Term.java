package textProcessing;

import lombok.Data;

/**
 * Created by LU on 2017-06-02.
 */
@Data
public class Term implements Comparable{
    protected String name;
    protected String normalizedName;

    public Term(String name){
        this.name = name;
        normalizedName = normalize(name);
    }

    public static String normalize(String string){
        String result = string.toLowerCase();
        result = result.replace('-',' ');
        result = result.replace('.',' ');
        result = result.replace(',',' ');
        result = result.replaceAll("\\s+"," ");

        return result;
    }

    @Override
    public String toString() {
        return name+"\t("+this.normalizedName+")";
    }

    @Override
    public boolean equals(Object obj) {
        //System.out.println("\t\tTerm.equals: \n\t\t\t["+obj.getClass()+"]"+obj.toString()+"\n\t\t\t["+this.getClass()+"]"+this.toString());
        if(obj instanceof DictionaryTerm) {
            return obj.equals(this);
        }
        if(obj instanceof Term){
            return this.normalizedName.equals(((Term) obj).getNormalizedName());
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode(){
        int hash = 7;
        for(int i=0;i<this.getNormalizedName().length();i++){
            hash = hash*3+this.getNormalizedName().charAt(i);
        }
        return hash;
    }

    @Override
    public int compareTo(Object obj){
        Term t = (Term) obj;
        return this.name.compareTo(t.getName());
    }

}
