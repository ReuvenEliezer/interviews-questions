package com.interviews.questions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FeedVisorTest {

    private final static Logger logger = LogManager.getLogger(FeedVisorTest.class);

    int periodTime10 = 10;
    int periodTime60 = 60;
    int maxRequestsTogetherInPeriodOf10Sec = 20;
    int maxRequestsTogetherInPeriodOf60Sec = 60;
    Duration periodTimeDuration10Sec = Duration.ofSeconds(10);
    Duration periodTimeDuration60Sec = Duration.ofSeconds(60);

    @Test
    public void test1() {
        //no more that 3 request in one sec
        //no more 20 requests in time period of 10 sec
        //no more 60 requests in time period of 60 sec
        List<Integer> integerList = Arrays.asList(1, 1, 1, 1, 2);
        int result = calcDroppedRequestsResult(integerList);
        Assert.assertEquals(1, result);
        List<Integer> integerList1 = Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 11, 11, 11, 11);
        result = calcDroppedRequestsResult(integerList1);
        Assert.assertEquals(7, result);
        List<Integer> integerList2 = Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 12, 12, 12);
        result = calcDroppedRequestsResult(integerList2);
        Assert.assertEquals(0, result);
    }
    @Test
    public void test2() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(now);
        localDateTimes.add(now);
        localDateTimes.add(now);
        int result = calcDroppedRequestsResultTime(localDateTimes);
        Assert.assertEquals(0, result);
        localDateTimes.add(now);
        result = calcDroppedRequestsResultTime(localDateTimes);
        Assert.assertEquals(1, result);
        localDateTimes.add(now.plusSeconds(1));
        localDateTimes.add(now.plusSeconds(1));
        localDateTimes.add(now.plusSeconds(1));
        localDateTimes.add(now.plusSeconds(2));
        localDateTimes.add(now.plusSeconds(2));
        localDateTimes.add(now.plusSeconds(2));
        localDateTimes.add(now.plusSeconds(3));
        localDateTimes.add(now.plusSeconds(3));
        localDateTimes.add(now.plusSeconds(3));
        localDateTimes.add(now.plusSeconds(4));
        localDateTimes.add(now.plusSeconds(4));
        localDateTimes.add(now.plusSeconds(4));
        localDateTimes.add(now.plusSeconds(5));
        localDateTimes.add(now.plusSeconds(5));
        localDateTimes.add(now.plusSeconds(5));
        localDateTimes.add(now.plusSeconds(6));
        localDateTimes.add(now.plusSeconds(6));
        localDateTimes.add(now.plusSeconds(6));
        localDateTimes.add(now.plusSeconds(6));
        localDateTimes.add(now.plusSeconds(10));
        localDateTimes.add(now.plusSeconds(10));
        localDateTimes.add(now.plusSeconds(10));
        localDateTimes.add(now.plusSeconds(10));
        result = calcDroppedRequestsResultTime(localDateTimes);
        Assert.assertEquals(7, result);
    }

    private int calcDroppedRequestsResultTime(List<LocalDateTime> localDateTimeList) {
        int droppedRequestsResult = 0;
        if (localDateTimeList == null || localDateTimeList.size() < 4) {
            return droppedRequestsResult;
        }
        Collections.sort(localDateTimeList);

        LocalDateTime one = localDateTimeList.get(0);
        LocalDateTime two = localDateTimeList.get(1);
        LocalDateTime three = localDateTimeList.get(2);
        for (int index = 3; index < localDateTimeList.size(); index++) {
            LocalDateTime current = localDateTimeList.get(index);
            if (current.equals(one) && current.equals(two) && current.equals(three)) {
                logger.info("got index: {} with value: {} already exist this value more that 3 times ", index, current);
                droppedRequestsResult++;
            } else if (index >= maxRequestsTogetherInPeriodOf10Sec) {
                LocalDateTime valueOfIndex20Distance = localDateTimeList.get(index - maxRequestsTogetherInPeriodOf10Sec);
                if (periodTimeDuration10Sec.compareTo(Duration.between(valueOfIndex20Distance, current)) == 1) {
                    logger.info("got index: {} with value: {}, the value in distance of {} indexes is: {}, this is more that allow request {} for period time of {}", index, current, maxRequestsTogetherInPeriodOf10Sec, valueOfIndex20Distance, maxRequestsTogetherInPeriodOf10Sec, periodTime10);
                    droppedRequestsResult++;
                } else if (index >= maxRequestsTogetherInPeriodOf60Sec) {
                    LocalDateTime valueOfIndex60Distance = localDateTimeList.get(index - maxRequestsTogetherInPeriodOf60Sec);
                    if (periodTimeDuration60Sec.compareTo(Duration.between(valueOfIndex60Distance, current)) == 1) {
                        logger.info("got index: {} with value: {}, the value in distance of {} indexes is: {}, this is more that allow request {} for period time of {}", index, current, maxRequestsTogetherInPeriodOf60Sec, valueOfIndex60Distance, maxRequestsTogetherInPeriodOf60Sec, periodTime60);
                        droppedRequestsResult++;
                    }
                }
            }
            one = two;
            two = three;
            three = current;
        }
        return droppedRequestsResult;
    }

    private int calcDroppedRequestsResult(List<Integer> integerList) {
        int droppedRequestsResult = 0;
        if (integerList == null || integerList.size() < 4) {
            return droppedRequestsResult;
        }
        Collections.sort(integerList);

        int one = integerList.get(0);
        int two = integerList.get(1);
        int three = integerList.get(2);
        for (int index = 3; index < integerList.size(); index++) {
            Integer current = integerList.get(index);
            if (current.equals(one) && current.equals(two) && current.equals(three)) {
                logger.info("got index: {} with value: {} already exist this value more that 3 times ", index, current);
                droppedRequestsResult++;
            } else if (index >= maxRequestsTogetherInPeriodOf10Sec) {
                Integer valueOfIndex20Distance = integerList.get(index - maxRequestsTogetherInPeriodOf10Sec);
                if (current - valueOfIndex20Distance < periodTime10) {
                    logger.info("got index: {} with value: {}, the value in distance of {} indexes is: {}, this is more that allow request {} for period time of {}", index, current, maxRequestsTogetherInPeriodOf10Sec, valueOfIndex20Distance, maxRequestsTogetherInPeriodOf10Sec, periodTime10);
                    droppedRequestsResult++;
                } else if (index >= maxRequestsTogetherInPeriodOf60Sec) {
                    Integer valueOfIndex60Distance = integerList.get(index - maxRequestsTogetherInPeriodOf60Sec);
                    if (current - valueOfIndex60Distance < periodTime60) {
                        logger.info("got index: {} with value: {}, the value in distance of {} indexes is: {}, this is more that allow request {} for period time of {}", index, current, maxRequestsTogetherInPeriodOf60Sec, valueOfIndex60Distance, maxRequestsTogetherInPeriodOf60Sec, periodTime60);
                        droppedRequestsResult++;
                    }
                }
            }
            one = two;
            two = three;
            three = current;
        }
        return droppedRequestsResult;
    }

    @Test
    public void test() {
//        List<Integer> integerList = Arrays.asList(21, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7);
        List<Integer> integerList = Arrays.asList(
                84,
                1,
                1,
                1,
                1,
                2,
                2,
                2,
                3,
                3,
                3,
                3,
                4,
                5,
                5,
                5,
                6,
                6,
                6,
                6,
                7,
                7,
                7,
                8,
                8,
                8,
                8,
                9,
                9,
                9,
                9,
                9,
                10,
                10,
                11,
                11,
                11,
                11,
                11,
                11,
                12,
                12,
                12,
                12,
                12,
                12,
                12,
                13,
                13,
                13,
                13,
                14,
                14,
                14,
                14,
                14,
                16,
                16,
                16,
                16,
                16,
                16,
                17,
                17,
                17,
                18,
                18,
                18,
                18,
                18,
                18,
                18,
                18,
                19,
                19,
                19,
                19,
                19,
                19,
                19,
                20,
                20,
                20,
                20,
                20);
        Assert.assertEquals(67,calcDroppedRequestsResult(integerList));
    }

}
