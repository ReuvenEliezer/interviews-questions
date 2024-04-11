package com.interviews.questions;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class ForeScout {

    private Duration timeInterval = Duration.ofMinutes(1);

    @Test
    public void foreScoutTest() {
        isShouldLock(1,true);
        isShouldLock(1,true);

        /**
         * should to return result as below:
         * your task is to return indicator if we should be locked the user :
         * if login password incorrect for specific user 3 times consecutive (or more) in the TimePeriod - we need to lock the user
         */
    }

    private Map<Integer, LinkedBlockingDeque<LocalDateTime>> userIdToFailureLoginMap = new ConcurrentHashMap<>();
    private int maxFailed = 3;

    private boolean isShouldLock(int userId, boolean correctPassword) {
        if (correctPassword) {
            userIdToFailureLoginMap.remove(userId);
            return true;
        }

        LinkedBlockingDeque<LocalDateTime> failedLoginTimeList = userIdToFailureLoginMap.computeIfAbsent(userId, q -> new LinkedBlockingDeque<>());
        LocalDateTime now = LocalDateTime.now();
        boolean result;
        if (failedLoginTimeList.size() >= maxFailed) {
            Iterator<LocalDateTime> iterator = failedLoginTimeList.iterator();
            while (iterator.hasNext() && (failedLoginTimeList.getFirst().plus(timeInterval).isBefore(now) || failedLoginTimeList.size() > 3)) {
                iterator.remove();
            }
            if (failedLoginTimeList.getFirst().plus(timeInterval).isAfter(now)) {
                result = false;
            } else {
                result = true;
            }
        } else {
            result = true;
        }
        failedLoginTimeList.add(now);

        return result;
    }
    //TODO create new scheduled thread that moving over all map and clear the ols keys that last value is olds then current time+timeInterval;

}
