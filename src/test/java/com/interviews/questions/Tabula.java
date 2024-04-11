package com.interviews.questions;

import org.junit.Test;

public class Tabula {

    @Test
    public void test() {
        //https://stackoverflow.com/questions/600293/how-to-check-if-a-number-is-a-power-of-2
        boolean isPower = isPowered(5);
        boolean isPower1 = isPowered(2);
        boolean isPower2 = isPowered(8);
        boolean isPower3 = isPowered(64);
        boolean isPower4 = isPowered(60);
        boolean isPower5 = isPowered(-1);
    }

    private boolean isPowered(int value) {
        return (value != 0) && ((value & (value - 1)) == 0);
//        if (value == 2)
//            return true;
//        if (value % 2 != 0)
//            return false;
//        return isPowered(value / 2);
    }
}
