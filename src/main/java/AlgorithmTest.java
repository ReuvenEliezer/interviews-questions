import org.junit.Test;

public class AlgorithmTest {

    @Test
    public void aVoid() {
        int[] ints = {7, 1, 5, 3, 6, 4};
        int[] clone = ints.clone();
        clone[0]=11;
//        int[] ints= {1,2,3,4,5};
//        int[] ints= {7,6,4,3,1};
        int result = maxProfit(ints);
    }

    public int maxProfit(int[] prices) {
        int first = prices[0];
        int sumProfit=0;
//        Map<Integer, List<Integer>> integerListMap =  new HashMap<>();
        for (int i = 1; i < prices.length; i++) {
            if (prices[i]>first){
                sumProfit+=prices[i]-first;
            }
            int price = prices[i];

        }
        return -1;
    }
}
