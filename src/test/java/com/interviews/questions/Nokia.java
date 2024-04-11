package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Nokia {

    /**
     * https://www.geeksforgeeks.org/find-the-ordering-of-tasks-from-given-dependencies/
     *
     * https://www.geeksforgeeks.org/find-whether-it-is-possible-to-finish-all-tasks-or-not-from-given-dependencies/
     *
     * https://stackoverflow.com/questions/18314250/optimized-algorithm-to-schedule-tasks-with-dependency/18327459
     */

    @Test
    public void returnIfAllStrStartWithSame() {
        Assert.assertFalse(isAllStrStartWithSame(new String[]{"aaa", "aaa", "aab", "aba", "bb"}));
        Assert.assertTrue(isAllStrStartWithSame(new String[]{"aaa", "aaa", "aab", "aba", "ab"}));
    }

    private boolean isAllStrStartWithSame(String[] arr) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : arr) {
            String s1 = s.split("")[0];
            map.merge(s1, 1, Integer::sum);
        }

        if (map.size() <= 1)
            return true;
        return false;
    }
}
