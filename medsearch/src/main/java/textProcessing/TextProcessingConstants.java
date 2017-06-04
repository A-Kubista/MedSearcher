package textProcessing;

/**
 * Created by LU on 2017-06-03.
 */
public class TextProcessingConstants {
    public static final double DICTIONARY_TERM_WEIGHT = 2.5;
    public static final double CONTENT_SIGNIFICANCE = 0.0;

    public enum SortingType {
        SORT_BY_TF,
        SORT_BY_IDF,
        SORT_BY_DMI,
        SORT_BY_LTI
    }
}
