package util;

import lombok.Getter;

/**
 * Created by wilek on 2017-04-15.
 */
public class Path {
    public static class Web {
        @Getter public static final String BASE_URL = "http://localhost:4567";
        @Getter public static final String INDEX = "/index/";
    }

    public static class Template {
        public final static String INDEX = "layout.ftl";
        public static final String NOT_FOUND = "notfound.ftl";
    }
}
