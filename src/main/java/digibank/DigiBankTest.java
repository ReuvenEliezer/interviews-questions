package digibank;

import org.junit.Assert;
import org.junit.Test;

public class DigiBankTest {


    /**
     * Char |   Allowed followers  |  Can the char be at the end of the word?
     * a	|   a,b,d             |  true
     * b	|   a,f               |  False
     * c	|   b	              |  true
     */


    @Test
    public void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        DigiBankService digiBankService = new DigiBankService();
        digiBankService.init();
        Assert.assertTrue(digiBankService.isValid("ad"));
        Assert.assertFalse(digiBankService.isValid("ah"));
        Assert.assertFalse(digiBankService.isValid("bab"));
        Assert.assertTrue(digiBankService.isValid("bf"));
        Assert.assertTrue(digiBankService.isValid("bfd"));
        Assert.assertFalse(digiBankService.isValid("bfb"));
    }


}
