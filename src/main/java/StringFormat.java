import org.junit.Test;

public class StringFormat {

    private static final String CSV_SPLITTER = ",";
    private static final String DOT_URL_SITE = ".";

    @Test
    public void test(){
        String s = "sss, s,55,";
        boolean isValid = s.matches(String.format(".*%1$s.*%2$s.*%2$s.*", DOT_URL_SITE, CSV_SPLITTER));

    }
}
