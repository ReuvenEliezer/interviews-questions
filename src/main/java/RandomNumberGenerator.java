import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

public class RandomNumberGenerator implements IRandomNumberGenerator {

    //https://leetcode.com/problems/insert-delete-getrandom-o1/submissions/
    private HashMap<Integer, Integer> valueToIndexHashMap = new HashMap<>();
    private HashMap<Integer, Integer> indexToValueHashMap = new HashMap<>();
    private Random random = new Random();

    @Override
    public boolean insert(int value) {
        if (valueToIndexHashMap.containsKey(value))
            return false;
        valueToIndexHashMap.put(value, valueToIndexHashMap.size());
        indexToValueHashMap.put(indexToValueHashMap.size(), value);
        return true;
    }

    @Override
    public boolean remove(int value) {
        if (!valueToIndexHashMap.containsKey(value))
            return false;
        Integer index = valueToIndexHashMap.get(value);
        Integer moveValue = indexToValueHashMap.get(indexToValueHashMap.size() - 1);

        valueToIndexHashMap.put(moveValue, index);
        indexToValueHashMap.put(index, moveValue);
        indexToValueHashMap.remove(indexToValueHashMap.size() - 1);
        valueToIndexHashMap.remove(value);

        return true;
    }

    @Override
    public int getRandom() {
        return indexToValueHashMap.get(random.nextInt(indexToValueHashMap.size()));
    }

    @Test
    public void test1() {
        RandomNumberGenerator numberGenerator = new RandomNumberGenerator();
        Assert.assertEquals(true, numberGenerator.insert(10));
        Assert.assertEquals(10, numberGenerator.getRandom());
        Assert.assertEquals(true, numberGenerator.insert(11));
        Assert.assertEquals(true, numberGenerator.insert(12));
        Assert.assertEquals(true, numberGenerator.insert(13));

        Assert.assertEquals(true, numberGenerator.remove(10));

        int random = numberGenerator.getRandom();
        System.out.println(random);
    }

    @Test
    public void test2() {
        RandomNumberGenerator numberGenerator = new RandomNumberGenerator();
        Assert.assertEquals(false, numberGenerator.remove(0));
        Assert.assertEquals(false, numberGenerator.remove(0));

        Assert.assertEquals(true, numberGenerator.insert(0));
        Assert.assertEquals(0, numberGenerator.getRandom());
        Assert.assertEquals(true, numberGenerator.remove(0));
        Assert.assertEquals(true, numberGenerator.insert(0));


        int random = numberGenerator.getRandom();
        System.out.println(random);
    }


    @Test
    public void test3() {
        RandomNumberGenerator numberGenerator = new RandomNumberGenerator();
        Assert.assertEquals(true, numberGenerator.insert(1));
        Assert.assertEquals(false, numberGenerator.remove(2));
        Assert.assertEquals(true, numberGenerator.insert(2));
        int random1 = numberGenerator.getRandom();
        Assert.assertEquals(true, numberGenerator.remove(1));
        Assert.assertEquals(false, numberGenerator.insert(2));
        Assert.assertEquals(2, numberGenerator.getRandom());


        int random = numberGenerator.getRandom();
        System.out.println(random);
    }
}


