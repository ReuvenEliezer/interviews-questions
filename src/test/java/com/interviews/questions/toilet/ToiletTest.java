package com.interviews.questions.toilet;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class ToiletTest {



    /**
     * user > request with current location.
     * server >
     */
    @Test
    public void test() {
        com.interviews.questions.toilet.ToiletService toiletService = new com.interviews.questions.toilet.ToiletService(new ToiletRepositoryImpl());
        com.interviews.questions.toilet.Toilet nearestLocation = toiletService.calcNearestLocation(new com.interviews.questions.toilet.Location(34.5, 34));
        com.interviews.questions.toilet.Toilet nearestLocation2 = toiletService.calcNearestLocation(new com.interviews.questions.toilet.Location(35, 35));
        com.interviews.questions.toilet.ToiletService toiletService2 = new com.interviews.questions.toilet.ToiletService(new com.interviews.questions.toilet.ToiletRepositoryMockImpl(new com.interviews.questions.toilet.Toilet(new com.interviews.questions.toilet.Location(33d, 34d))));
    }

    @Test
    public void mockTest() {
        com.interviews.questions.toilet.ToiletService toiletMockToiletList = new com.interviews.questions.toilet.ToiletService(new com.interviews.questions.toilet.ToiletRepositoryMockImpl(List.of(new com.interviews.questions.toilet.Toilet(new com.interviews.questions.toilet.Location(33d, 34d)), new com.interviews.questions.toilet.Toilet(new com.interviews.questions.toilet.Location(33d, 34d)))));
        Assert.assertNotNull(toiletMockToiletList.calcNearestLocation(new com.interviews.questions.toilet.Location(35, 35)));

        com.interviews.questions.toilet.ToiletService toiletMockOneToilet = new com.interviews.questions.toilet.ToiletService(new com.interviews.questions.toilet.ToiletRepositoryMockImpl(new Toilet(new com.interviews.questions.toilet.Location(33d, 34d))));
        Assert.assertNotNull(toiletMockOneToilet.calcNearestLocation(new com.interviews.questions.toilet.Location(35, 35)));

        com.interviews.questions.toilet.ToiletService toiletMockEmptyToilets = new ToiletService(new ToiletRepositoryMockImpl(new ArrayList<>()));
        Assert.assertNull(toiletMockEmptyToilets.calcNearestLocation(new Location(35, 35)));
    }

}
