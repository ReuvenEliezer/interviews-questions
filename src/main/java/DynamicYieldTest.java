import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class DynamicYieldTest {

    @Test
    public void slidingWindowTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        stringSlidingWindowCache.receive("www.google.com");
        stringSlidingWindowCache.receive("www.google.com");
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(2)).size().isEqualTo(1);
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(1)).size().isEqualTo(1);
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(1)).containsExactly("www.google.com");
        stringSlidingWindowCache.receive("www.mc.com");
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(2)).size().isEqualTo(2);
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(1)).containsExactly("www.google.com");
        stringSlidingWindowCache.receive("www.mc.com");
        stringSlidingWindowCache.receive("www.mc.com");
        stringSlidingWindowCache.receive("www.mc.com");
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(1)).containsExactly("www.mc.com");

        Thread.sleep(slidingWindowDuration.plusSeconds(1).toMillis());
        stringSlidingWindowCache.receive("www.google.com");
        assertThat(stringSlidingWindowCache.getMostFrequentSorted(1)).containsExactly("www.google.com");
    }

    @Test
    public void singleUrlTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(1);
        assertThat(mostFrequent).containsExactly("www.google.com");
    }

    @Test
    public void multipleUrlsTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        stringSlidingWindowCache.receive("www.mc.com");
        stringSlidingWindowCache.receive("www.google.com");
        stringSlidingWindowCache.receive("www.mc.com");

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(2);
        assertThat(mostFrequent).containsExactly("www.mc.com", "www.google.com");
    }

    @Test
    public void noUrlsTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(1);
        assertThat(mostFrequent).isEmpty();
    }

    @Test
    public void outdatedUrlsTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        Thread.sleep(slidingWindowDuration.plusSeconds(1).toMillis()); // Wait for the sliding window to expire

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(1);
        assertThat(mostFrequent).isEmpty();
    }

    @Test
    public void invalidUrlSizeTest() {
        Duration slidingWindowDuration = Duration.ofSeconds(2);
        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);

        assertThatThrownBy(() -> stringSlidingWindowCache.getMostFrequentSorted(0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> stringSlidingWindowCache.getMostFrequentSorted(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void slidingWindowExpirationTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        Thread.sleep(slidingWindowDuration.plusSeconds(3).toMillis());

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(1);
        assertThat(mostFrequent).isEmpty();
    }

    @Test
    public void multipleUrlsExpireTest() throws InterruptedException {
        Duration slidingWindowDuration = Duration.ofSeconds(2);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        stringSlidingWindowCache.receive("www.mc.com");
        Thread.sleep(slidingWindowDuration.plusSeconds(1).toMillis());

        List<String> mostFrequent = stringSlidingWindowCache.getMostFrequentSorted(2);
        assertThat(mostFrequent).isEmpty();
    }

    static class SlidingWindowCache<T> {
        private final Duration periodTimeDuration;
        private final Map<T, Integer> webUrlToCountMap = new HashMap<>();
        private final Map<Integer, Set<T>> countToWebUrlMap = new TreeMap<>(Collections.reverseOrder()); // in order to return the result of getMostFrequentSorted in o(1)
        private final Queue<WebUrlData<T>> webUrlDataQueue = new ArrayDeque<>();

        public SlidingWindowCache(Duration periodTimeDuration) {
            this.periodTimeDuration = periodTimeDuration;
        }

        record WebUrlData<T>(T url, LocalDateTime currentTime) {
        }

        /**
         * This method processes incoming URLs
         *
         * @param url
         */
        public void receive(T url) {
            WebUrlData<T> webUrlData = new WebUrlData<>(url, LocalDateTime.now());
            webUrlDataQueue.add(webUrlData);

            Integer prevCount = webUrlToCountMap.get(url);
            Integer mergeResult = webUrlToCountMap.merge(url, 1, Integer::sum);

            //add webUrl to the update counter key
            countToWebUrlMap.computeIfAbsent(mergeResult, key -> new HashSet<>()).add(url);

            //reduce the webUrl from the prevCount in the countToWebUrlMap
            if (prevCount != null) {
                if (countToWebUrlMap.get(prevCount).size() > 1) {
                    //remove the webUrl from the value list
                    countToWebUrlMap.get(prevCount).remove(url);
                } else {
                    //remove index
                    countToWebUrlMap.remove(prevCount);
                }
            }
//        countToWebUrlMap.computeIfAbsent(prevCount, k -> new HashSet<>()).remove(url);
//        countToWebUrlMap.computeIfAbsent(merge, k -> new HashSet<>()).add(url);
        }

        /**
         * : This method retrieves the k most frequent URLs, sorted in descending order by frequency in a sliding window time
         */
        public List<T> getMostFrequentSorted(int urlSize) {
            if (urlSize <= 0) {
                throw new IllegalArgumentException(String.format("k=%s must be a positive value", urlSize));
            }
            //clean old messages
            LocalDateTime now = LocalDateTime.now();
            while (!webUrlDataQueue.isEmpty() && webUrlDataQueue.peek().currentTime.plus(periodTimeDuration).isBefore(now)) { //is olden
                cleanOldMessages();
            }

            // return results by ordering
            return countToWebUrlMap.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .limit(urlSize)
                    .collect(Collectors.toCollection(() -> new ArrayList<>(urlSize)));
//        List<String> result = new ArrayList<>(k);
//        for (Map.Entry<Integer, Set<String>> entry : countToWebUrlMap.entrySet()) {
//            for (String webUrl :  entry.getValue()) {
//                result.add(webUrl);
//                if (result.size() == k) {
//                    return result;
//                }
//            }
//        }
//        return result;
        }

        private void cleanOldMessages() {
            //remove from queue
            WebUrlData<T> webUrlData = webUrlDataQueue.remove();

            //reduce the counter & increase the prev counter
            Integer totalCount = webUrlToCountMap.get(webUrlData.url);
            Set<T> urls = countToWebUrlMap.get(totalCount);
            if (urls.size() <= 1) {
                countToWebUrlMap.remove(totalCount);
            } else {
                urls.remove(webUrlData.url);
            }
            if (totalCount - 1 > 0) { //don't add value to a zero counter
                countToWebUrlMap.computeIfAbsent(totalCount - 1, key -> new HashSet<>()).add(webUrlData.url); //increase the prev counter
            }

            //reduce counter of webUrlToCountMap
            Integer countValue = webUrlToCountMap.merge(webUrlData.url, -1, Integer::sum);
            if (countValue == 0) {
                webUrlToCountMap.remove(webUrlData.url);
            }
        }
    }

}
