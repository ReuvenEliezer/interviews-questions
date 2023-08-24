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
    public void test() throws InterruptedException {
        /**
         *
         */

        receive("www.google.com");
        receive("www.google.com");
        receive("www.google.com");
        assertThat(getMostFrequentSorted(2)).size().isEqualTo(1);
        assertThat(getMostFrequentSorted(1)).size().isEqualTo(1);
        assertThat(getMostFrequentSorted(1)).containsExactly("www.google.com");
        receive("www.mc.com");
        assertThat(getMostFrequentSorted(2)).size().isEqualTo(2);
        assertThat(getMostFrequentSorted(1)).containsExactly("www.google.com");
        receive("www.mc.com");
        receive("www.mc.com");
        receive("www.mc.com");
        assertThat(getMostFrequentSorted(1)).containsExactly("www.mc.com");

        Thread.sleep(PERIOD_TIME_DURATION.plusSeconds(1).toMillis());
        receive("www.google.com");
        assertThat(getMostFrequentSorted(1)).containsExactly("www.google.com");
    }

    private final Map<String, Integer> webUrlToCountMap = new HashMap<>();
    private final Map<Integer, Set<String>> countToWebUrlMap = new TreeMap<>(Collections.reverseOrder()); // in order to return the result of getMostFrequentSorted in o(1)
    private final Queue<WebUrlData> webUrlDataQueue = new ArrayDeque<>();
    private static final Duration PERIOD_TIME_DURATION = Duration.ofSeconds(2);


    public void receive(String url) {
        WebUrlData webUrlData = new WebUrlData(url, LocalDateTime.now());
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

    public List<String> getMostFrequentSorted(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(String.format("k=%s must be a positive value", k));
        }
        //clean old messages
        LocalDateTime now = LocalDateTime.now();
        while (!webUrlDataQueue.isEmpty() && webUrlDataQueue.peek().currentTime.plus(PERIOD_TIME_DURATION).isBefore(now)) { //is olden
            //remove from queue
            WebUrlData webUrlData = webUrlDataQueue.remove();


            //reduce the counter & increase the prev counter
            Integer totalCount = webUrlToCountMap.get(webUrlData.url);
            Set<String> urls = countToWebUrlMap.get(totalCount);
            if (urls.size() <= 1) {
                countToWebUrlMap.remove(totalCount);
            } else {
                urls.remove(webUrlData.url);
            }
            countToWebUrlMap.computeIfAbsent(totalCount - 1, key -> new HashSet<>()).add(webUrlData.url); //increase the prev counter


            Integer countValue = webUrlToCountMap.merge(webUrlData.url, -1, Integer::sum);
            if (countValue == 0) {
                webUrlToCountMap.remove(webUrlData.url);
            }

        }

        // return results by ordering

        return countToWebUrlMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .limit(k)
                .collect(Collectors.toCollection(() -> new ArrayList<>(k)));
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

    record WebUrlData(String url, LocalDateTime currentTime) {
    }
}
