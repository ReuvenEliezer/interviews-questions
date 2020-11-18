//package overideiIterator;
//
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
///**
// * // This is the interface that allows for creating nested lists.
// * // You should not implement it, or speculate about its implementation
// * public interface NestedInteger {
// *
// *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
// *     public boolean isInteger();
// *
// *     // @return the single integer that this NestedInteger holds, if it holds a single integer
// *     // Return null if this NestedInteger holds a nested list
// *     public Integer getInteger();
// *
// *     // @return the nested list that this NestedInteger holds, if it holds a nested list
// *     // Return null if this NestedInteger holds a single integer
// *     public List<NestedInteger> getList();
// * }
// */
//public class NestedIterator implements Iterator<Integer> {
//
//    public NestedIterator(List<NestedInteger> nestedList) {
//
//    }
//
//    @Override
//    public Integer next() {
//
//    }
//
//    @Override
//    public boolean hasNext() {
//
//    }
//
//    @Test
//    public void test() {
////        [[1,1],2,[1,1]]
//        List<Integer> mainList = new ArrayList<>();
//
//        List<Integer> integerList = Arrays.asList(1,1);
//        mainList.addAll(integerList);
//        mainList.add(2);
//        mainList.addAll(integerList);
//
//        EvenIterator evenIterator = new EvenIterator(integerList);
//        while (evenIterator.hasNext()) {
//            System.out.println(evenIterator.next());
//        }
//    }
//}
//
///**
// * Your NestedIterator object will be instantiated and called as such:
// * NestedIterator i = new NestedIterator(nestedList);
// * while (i.hasNext()) v[f()] = i.next();
// */