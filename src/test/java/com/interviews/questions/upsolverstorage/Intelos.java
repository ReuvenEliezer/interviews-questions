package com.interviews.questions.upsolverstorage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class Intelos {

    @Test
    void intelosTest() {

        int[] original = new int[]{1, 4, 3, 2};
        int[] desired = new int[]{1, 2, 4, 3};
        System.out.println(minPieces(original, desired));

//        FireDragon fireDragon = new FireDragon();
//        System.out.println(fireDragon instanceof Reptile);
    }

    public static int minPieces(int[] original, int[] desired) {
        Map<Integer, Integer> valueToIndexDesiredMap = new HashMap<>(desired.length);
        for (int index = 0, desiredLength = desired.length; index < desiredLength; index++) {
            int value = desired[index];
            valueToIndexDesiredMap.put(value,index);
        }
        int result = 0;
        for (int index = 0, originalLength = original.length; index < originalLength; index++) {
            int originalValue = original[index];
            int desiredIndex = valueToIndexDesiredMap.get(originalValue);
            if (index != desiredIndex){
                result++;
            }
        }

        return result;
    }

//    public static int minPieces(int[] original, int[] desired) {
////        Map<Integer, Integer> IndexToValueDesiredMap = new HashMap<>(desired.length);
////        for (int index = 0, desiredLength = desired.length; index < desiredLength; index++) {
////            int value = desired[index];
////            IndexToValueDesiredMap.put(index, value);
////        }
//        int result = 0;
//        for (int index = 0, originalLength = original.length; index < originalLength; index++) {
//            int originalValue = original[index];
//            int desiredValue = desired[index];
//            if (originalValue != desiredValue){
//                result++;
//            }
//        }
//
//        return result;
//    }

    interface Reptile {
        ReptileEgg lay();
    }

    class FireDragon implements Reptile {
        public FireDragon() {
        }

        @Override
        public ReptileEgg lay() {
            return null;
        }
    }

    class ReptileEgg {
        public ReptileEgg(Callable<Reptile> createReptile) {
            throw new UnsupportedOperationException("Waiting to be implemented.");
        }

        public Reptile hatch() throws Exception {
            throw new UnsupportedOperationException("Waiting to be implemented.");
        }

    }
}
