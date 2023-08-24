import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ProofPointTest {


    @Test
    public void test() {
        /**
         * Input: arr[] = {2, 8, 7, 1, 5};
         * Output: 2 1 5
         */
        int[] arr = {2, 8, 7, 1, 5};

        assertThat(filterOut2BiggestNumbers(arr)).isEqualTo(new int[]{2, 1, 5});
        assertThat(filterOut2BiggestNumbersUsingMap(arr)).containsExactlyInAnyOrder(2, 1, 5);
        assertThat(filterOut2BiggestNumbersUsingMap(arr)).containsExactlyInAnyOrder(2, 1, 5);


        assertThat(filterOut2BiggestNumbers(new int[]{2, 7, 8})).isEqualTo(new int[]{2});
        assertThat(filterOut2BiggestNumbers(new int[]{2, 8, 7})).isEqualTo(new int[]{2});
        assertThat(filterOut2BiggestNumbers(new int[]{7, 8, 2})).isEqualTo(new int[]{2});
        assertThat(filterOut2BiggestNumbers(new int[]{8, 7, 2})).isEqualTo(new int[]{2});


        Arrays.sort(arr);
        int[] result = Arrays.stream(arr).limit(arr.length - 2).toArray();
        assertThat(result).containsExactlyInAnyOrder(new int[]{2, 1, 5});

    }

    @Test
    public void isPalindromeNumber() {
        boolean palindrome = isPalindrome(121);
    }

    private boolean isPalindrome(int x) {
        char[] charArray = Integer.toString(x).toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            char leftDigit = charArray[i];
            char rightDigit = charArray[charArray.length - 1 - i];
            if (leftDigit != rightDigit) {
                return false;
            }
        }
        return true;
    }

    private int[] filterOut2BiggestNumbers(int[] arr) {
        if (arr.length <= 2) {
            return new int[]{};
        }

        Integer max1 = null; // max1 > max2
        Integer max2 = null;
        int index = 0;
        int[] result = new int[arr.length - 2];

        for (int value : arr) {
            if (max1 == null) {
                max1 = value;
            } else if (max2 == null) {
                if (value > max1) {
                    max2 = max1;
                    max1 = value;
                } else {
                    max2 = value;
                }
            } else if (value > max1) {
                result[index] = max2;
                max2 = max1;
                max1 = value;
                index++;
            } else if (value > max2) {
                result[index] = max2;
                max2 = value;
                index++;
            } else {
                result[index] = value;
                index++;
            }
        }
        return result;
    }

    private int[] filterOut2BiggestNumbersUsingMap(int[] arr) {
        if (arr.length <= 2) {
            return new int[]{};
        }

        Map<Integer, Integer> valueToIndexMap = new TreeMap<>();
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            valueToIndexMap.put(value, i);
        }

        return valueToIndexMap.keySet().stream().limit(arr.length - 2).mapToInt(value -> value).toArray();
    }


    @Test
    public void getSumOfKeysByCounterByO1Test() {
        KeyCounter keyCounter = new KeyCounterImpl();

        keyCounter.deleteKey("b");

        keyCounter.addKey("a");
        keyCounter.addKey("a");
        keyCounter.addKey("b");
        keyCounter.addKey("c");

        long keysByCounter = keyCounter.getSumOfKeysByCounter(1);
        assertThat(keysByCounter).isEqualTo(2);

        keysByCounter = keyCounter.getSumOfKeysByCounter(2);
        assertThat(keysByCounter).isEqualTo(2);

        keyCounter.deleteKey("b");
        keysByCounter = keyCounter.getSumOfKeysByCounter(1);
        assertThat(keysByCounter).isEqualTo(1);

        keysByCounter = keyCounter.getSumOfKeysByCounter(2);
        assertThat(keysByCounter).isEqualTo(2);

        keyCounter.addKey("c");
        assertThat(keyCounter.getSumOfKeysByCounter(2)).isEqualTo(4);

        keyCounter.deleteKey("a");
        assertThat(keyCounter.getSumOfKeysByCounter(1)).isEqualTo(0);
        assertThat(keyCounter.getSumOfKeysByCounter(2)).isEqualTo(2);
    }

    public interface KeyCounter {
        void addKey(String key);

        void deleteKey(String key); //reduce the counter value if exists

        long getSumOfKeysByCounter(int counter); // by o(1)
    }

    static class KeyCounterImpl implements ProofPointTest.KeyCounter {
        private static final Object lock = new Object();
        private final Map<Integer, Integer> counterToNumOfKeysMap = new HashMap<>();

        private final Map<String, Integer> keyToCounterMap = new HashMap<>();

        @Override
        public void addKey(String key) {
            synchronized (lock) {
                final Integer value = keyToCounterMap.merge(key, 1, Integer::sum);

                //reduce the prev value counter
                final Integer prevValue = counterToNumOfKeysMap.get(value - 1);
                if (prevValue != null) {
                    counterToNumOfKeysMap.put(value - 1, prevValue - 1);
                }
                //add the new value counter
                counterToNumOfKeysMap.merge(value, 1 + (prevValue == null ? 0 : prevValue), Integer::sum);
            }
        }

        @Override
        public void deleteKey(String key) {
            synchronized (lock) {
                if (keyToCounterMap.containsKey(key)) {
                    final Integer value = keyToCounterMap.merge(key, -1, Integer::sum);

                    //increase prev value counter
                    final Integer prevValue = counterToNumOfKeysMap.get(value + 1);
                    if (prevValue != null) {
                        counterToNumOfKeysMap.put(value + 1, prevValue - (value + 1));
                    }
                }
            }
        }

        @Override
        public long getSumOfKeysByCounter(int counter) {
            synchronized (lock) {
                return counterToNumOfKeysMap.getOrDefault(counter, 0);
//            return keyToCounterMap.values().stream()
//                    .filter(e -> e.equals(counter))
//                    .count();
            }
        }
    }

    @Test
    public void test1() {
        B b = new B();
    }

    class B extends A {
        String b;

        B() {
            System.out.println("b instance");
        }
    }

    class A {
        String a;

        A() {
            System.out.println("a instance");
        }


    }


}
