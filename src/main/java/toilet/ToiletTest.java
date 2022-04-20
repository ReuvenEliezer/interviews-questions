package toilet;

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
        ToiletService toiletService = new ToiletService(new ToiletRepositoryImpl());
        Toilet nearestLocation = toiletService.calcNearestLocation(new Location(34.5, 34));
        Toilet nearestLocation2 = toiletService.calcNearestLocation(new Location(35, 35));
        ToiletService toiletService2 = new ToiletService(new ToiletRepositoryMockImpl(new Toilet(new Location(33d, 34d))));
    }

    @Test
    public void mockTest() {
        ToiletService toiletMockToiletList = new ToiletService(new ToiletRepositoryMockImpl(List.of(new Toilet(new Location(33d, 34d)), new Toilet(new Location(33d, 34d)))));
        Assert.assertNotNull(toiletMockToiletList.calcNearestLocation(new Location(35, 35)));

        ToiletService toiletMockOneToilet = new ToiletService(new ToiletRepositoryMockImpl(new Toilet(new Location(33d, 34d))));
        Assert.assertNotNull(toiletMockOneToilet.calcNearestLocation(new Location(35, 35)));

        ToiletService toiletMockEmptyToilets = new ToiletService(new ToiletRepositoryMockImpl(new ArrayList<>()));
        Assert.assertNull(toiletMockEmptyToilets.calcNearestLocation(new Location(35, 35)));
    }

}
