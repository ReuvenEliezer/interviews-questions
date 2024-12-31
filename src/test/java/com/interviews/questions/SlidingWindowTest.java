package com.interviews.questions;

import com.google.common.collect.Sets;
import com.interviews.questions.ranges.EdgeTimeValue;
import com.interviews.questions.ranges.PeriodTimeResult;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SlidingWindowTest {

    /**
     * נתון רשימה של תוכניות טלוויזיה עם זמן התחלה וסיום לכל תוכנית.
     * יש למצוא את פרק הזמן בו ישנן הכי הרבה תוכניות ולהחזיר מהן ואת משך הזמן
     */


    @Test
    public void findMaxProgramAtPeriodTimeTest() {
        List<Program> programs = new ArrayList<>();

        programs.add(new Program("program 1", LocalDateTime.of(2023, 8, 13, 0, 0),
                LocalDateTime.of(2023, 8, 13, 0, 25)));
        programs.add(new Program("program 2", LocalDateTime.of(2023, 8, 13, 0, 10),
                LocalDateTime.of(2023, 8, 13, 0, 25)));
        programs.add(new Program("program 3", LocalDateTime.of(2023, 8, 13, 0, 20),
                LocalDateTime.of(2023, 8, 13, 0, 30)));


        List<ProgramResult> maxProgramsAtPeriodTime = findMaxProgramAtPeriodTime(programs);
        System.out.println(maxProgramsAtPeriodTime);
        assertThat(maxProgramsAtPeriodTime).hasSize(1);
        ProgramResult programResult = new ProgramResult(
                LocalDateTime.of(2023, 8, 13, 0, 20),
                LocalDateTime.of(2023, 8, 13, 0, 24),
                Sets.newHashSet("program 1", "program 2", "program 3"));
        assertThat(maxProgramsAtPeriodTime).containsExactly(programResult);

        List<ProgramResult> maxProgramsAtPeriodTime2 = findMaxProgramAtPeriodTime2(programs);
        assertThat(maxProgramsAtPeriodTime2).containsExactly(new ProgramResult(
                LocalDateTime.of(2023, 8, 13, 0, 20),
                LocalDateTime.of(2023, 8, 13, 0, 25),
                Sets.newHashSet("program 1", "program 2", "program 3")));
        System.out.println(maxProgramsAtPeriodTime2);

        programs.add(new Program("program 6", LocalDateTime.of(2023, 8, 13, 1, 20),
                LocalDateTime.of(2023, 8, 13, 1, 30)));
        programs.add(new Program("program 4", LocalDateTime.of(2023, 8, 13, 1, 0),
                LocalDateTime.of(2023, 8, 13, 1, 25)));
        programs.add(new Program("program 5", LocalDateTime.of(2023, 8, 13, 1, 10),
                LocalDateTime.of(2023, 8, 13, 1, 25)));


        List<ProgramResult> maxProgramsAtPeriodTime1 = findMaxProgramAtPeriodTime(programs);
        System.out.println(maxProgramsAtPeriodTime1);
        assertThat(maxProgramsAtPeriodTime1).hasSize(2);
        ProgramResult programResult1 = new ProgramResult(
                LocalDateTime.of(2023, 8, 13, 1, 20),
                LocalDateTime.of(2023, 8, 13, 1, 24),
                Sets.newHashSet("program 4", "program 5", "program 6"));
        assertThat(maxProgramsAtPeriodTime1)
                .containsExactlyInAnyOrder(programResult, programResult1);

        List<ProgramResult> maxProgramsAtPeriodTime22 = findMaxProgramAtPeriodTime2(programs);
        assertThat(maxProgramsAtPeriodTime22)
                .containsExactlyInAnyOrder(
                        new ProgramResult(
                                LocalDateTime.of(2023, 8, 13, 0, 20),
                                LocalDateTime.of(2023, 8, 13, 0, 25),
                                Sets.newHashSet("program 1", "program 2", "program 3")),
                        new ProgramResult(
                                LocalDateTime.of(2023, 8, 13, 1, 20),
                                LocalDateTime.of(2023, 8, 13, 1, 25),
                                Sets.newHashSet("program 4", "program 5", "program 6")));
        System.out.println(maxProgramsAtPeriodTime22);
    }

    public static List<ProgramResult> findMaxProgramAtPeriodTime2(List<Program> programs) {
        List<Event> eventsSorted = programs.stream()
                .map(program ->
                        List.of(
                                new Event(program.startTime, EventType.START, program.name),
                                new Event(program.endTime, EventType.END, program.name))
                )
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Event::time).thenComparing(event -> event.type == EventType.END ? 0 : 1))
                .toList();

        int maxCount = 0;
        Set<String> activePrograms = new HashSet<>();
        List<ProgramResult> results = new ArrayList<>();
        LocalDateTime startTime = null;
        LocalDateTime endTime;

        for (Event event : eventsSorted) {
            if (event.type == EventType.START) {
                startTime = event.time;
                activePrograms.add(event.programName);
            } else {
                endTime = event.time;
                if (maxCount <= activePrograms.size()) {
                    results.add(new ProgramResult(startTime, endTime, new HashSet<>(activePrograms)));
                    maxCount = activePrograms.size();
                }
                activePrograms.remove(event.programName);
            }
        }
        return results;
    }


    enum EventType {
        START,
        END
    }

    record Event(LocalDateTime time, EventType type, String programName) {

    }

    private List<ProgramResult> findMaxProgramAtPeriodTime(List<Program> programs) {
        Map<LocalDateTime, Set<String>> timeInMinutesToProgramNamesMap = new TreeMap<>();

        for (Program program : programs) {
            for (LocalDateTime time = program.startTime; time.isBefore(program.endTime); time = time.plusMinutes(1)) {
                timeInMinutesToProgramNamesMap.merge(time, Sets.newHashSet(program.name), (existingNames, newNames) -> {
                    existingNames.addAll(newNames);
                    return existingNames;
                });
            }
        }

        int max = getMaxProgramAtPeriod(timeInMinutesToProgramNamesMap);

        //filter all max programs in a period time
        List<Map.Entry<LocalDateTime, Set<String>>> results = timeInMinutesToProgramNamesMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == max)
                .toList();


        for (Map.Entry<LocalDateTime, Set<String>> entry : results) {
            System.out.println(entry);
        }


        //grouping by program names
        Map<Set<String>, List<Map.Entry<LocalDateTime, Set<String>>>> groupedByProgramNames = results.stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.toList()));

        //build a result with start and end period time
        List<ProgramResult> programResults = new ArrayList<>();
        groupedByProgramNames.forEach((key, value) -> {
            programResults.add(new ProgramResult(value.get(0).getKey(), value.get(value.size() - 1).getKey(), key));
        });

        return programResults;
    }

    private static int getMaxProgramAtPeriod(Map<LocalDateTime, Set<String>> timeInMinutesToProgramNamesMap) {
        //        Map.Entry<LocalDateTime, Set<String>> max = Collections.max(timeInMinutesToProgramNamesMap.entrySet(), Comparator.comparingInt(entry -> entry.getValue().size()));
//        Map.Entry<LocalDateTime, Set<String>> max = Collections.max(timeInMinutesToProgramNamesMap.entrySet(), Comparator.comparing(entry -> entry.getValue().size()));
        return timeInMinutesToProgramNamesMap.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue().size() > entry2.getValue().size() ? 1 : -1)
                .get()
                .getValue()
                .size();
    }

    record ProgramResult(LocalDateTime start, LocalDateTime end, Set<String> programNames) {
    }

    record Program(String name, LocalDateTime startTime, LocalDateTime endTime) {
    }


    /**
     * בכביש  בו נוסעות מכוניות -מותקנת מצלמה המזהה את המספר ומוסיפה את השעה והרכב למאגר
     * נדרש להציג את תחילת פרק הזמן שבו עברו מס' המכוניות הגדול ביותר
     * <p>
     * (אפשר לבנות מפה לכל מקטע ולהחזיק
     * הכל בזיכרון)
     * אך זה תופס הרבה זיכרון.
     * צריך לשמור בכל פעם רק את ה period ואז לבדוק את הפריוד הבא - רק אם המקס' רכבים בו גדול יותר - לדרוס את המפה. וכן הלאה.
     */
    @Test
    public void carTest() {
        List<CarTime> carTimeList = new ArrayList<>();
        carTimeList.add(new CarTime(0, "12345678"));
        carTimeList.add(new CarTime(1, "12345678"));
        carTimeList.add(new CarTime(3, "12345678"));
        carTimeList.add(new CarTime(93, "12345678"));
        carTimeList.add(new CarTime(94, "12345678"));
        carTimeList.add(new CarTime(97, "12345678"));
        carTimeList.add(new CarTime(123, "12345678"));
        int timeInterval = 60;
        long startTimeStampThatPassedMaxCar = findMaxPassedCarsInPeriodTimeInterval(carTimeList, timeInterval);
        System.out.printf("the period time with highest moving cars is from %s until %s%n", startTimeStampThatPassedMaxCar, startTimeStampThatPassedMaxCar + timeInterval);
    }

    @Test
    public void carOptimisticMemoryTest() {
        List<CarDateTime> carTimeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        carTimeList.add(new CarDateTime(now, "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(1), "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(3), "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(93), "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(94), "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(97), "12345678"));
        carTimeList.add(new CarDateTime(now.plusSeconds(123), "12345678"));
        long startTimeStampThatPassedMaxCarOptimisticMemory = findMaxPassedCarsInPeriodTimeIntervalOptimisticMemory(carTimeList, Duration.ofMinutes(1));
    }

    private long findMaxPassedCarsInPeriodTimeIntervalOptimisticMemory(List<CarDateTime> carTimeList, Duration timeInterval) {
        carTimeList.sort(Comparator.comparing(CarDateTime::movingTime));
        System.out.println(carTimeList);
        List<EdgeTimeValue> triplets = new ArrayList<>();
        for (CarDateTime carDateTime : carTimeList) {
            LocalDateTime start = carDateTime.movingTime();
            LocalDateTime end = start.plus(timeInterval);
            EdgeTimeValue startEdgeTimeValue = new EdgeTimeValue(start, 1, false);
            triplets.add(startEdgeTimeValue);
            EdgeTimeValue endEdgeTimeValue = new EdgeTimeValue(end, 1, true);
            triplets.add(endEdgeTimeValue);
        }
        triplets.sort(Comparator.comparing(EdgeTimeValue::getTime).thenComparing(EdgeTimeValue::isEndTime));
        System.out.println(triplets);
        long max = 0;
        PeriodTimeResult periodTimeResult = null;
        List<Integer> currentAccumulationS = new ArrayList<>();
        for (int i = 0; i < triplets.size(); i++) {
            EdgeTimeValue triplet = triplets.get(i);

            LocalDateTime timeTagN;

            if (triplet.isEndTime()) {
                currentAccumulationS.remove(triplet.getValue().get(0));
            } else {
                currentAccumulationS.add(triplet.getValue().get(0));
            }
            timeTagN = triplet.getTime();

            LocalDateTime timeTagM;

            if (i + 1 < triplets.size()) {
                EdgeTimeValue nextTriplet = triplets.get(i + 1);
                timeTagM = nextTriplet.getTime();
            } else {
                timeTagM = triplet.getTime();
            }

            /**
             *
             This answer doesn't take account of gaps (gaps should not appear in output), so I refined it: * If e=false, add a to S. If e=true, take away a from S. * Define n'=n if e=false or n'=n+1 if e=true * Define m'=m-1 if f=false or m'=m if f=true * If n' <= m' and (e and not f) = false, output (n',m',S), otherwise output nothing. – silentman.it
             */
            if (!timeTagN.isAfter(timeTagM) && i + 1 < triplets.size() && ((triplet.isEndTime() && !triplets.get(i + 1).isEndTime()) == false)) {
                periodTimeResult = new PeriodTimeResult(timeTagN, timeTagM, currentAccumulationS);
                System.out.println(periodTimeResult);
                if (currentAccumulationS.stream().mapToInt(Integer::intValue).sum() > max) {
                    max = currentAccumulationS.stream().mapToInt(Integer::intValue).sum();
                }
            }
        }
        if (periodTimeResult != null)
            System.out.println(String.format("the period time with highest moving cars (%s) is from %s until %s", max, periodTimeResult.getStart(), periodTimeResult.getEnd()));
        return max;
    }

    //מחזיר את הנקודה שבה מחחיל הזמן ממנו ועד סוף הtimeinterval עברו הכי הרבה מכוניות
    private long findMaxPassedCarsInPeriodTimeInterval(List<CarTime> carTimeList, int timeInterval) {
        carTimeList.sort(Comparator.comparing(CarTime::timeStamp));
        CarTime first = carTimeList.get(0);
        CarTime last = carTimeList.get(carTimeList.size() - 1);
        Map<Long, Integer> totalCarsToStartPeriodTimeMap = new HashMap<>(); //for each timeInterval period
        for (long i = first.timeStamp(); i < last.timeStamp(); i++) {
            totalCarsToStartPeriodTimeMap.put(i, 0);
        }
        for (CarTime carTime : carTimeList) {
            long currentCarTime = carTime.timeStamp;
            for (Map.Entry<Long, Integer> snapshot : totalCarsToStartPeriodTimeMap.entrySet()) {
                //current time is between period time interval
                if (currentCarTime >= snapshot.getKey().longValue() && currentCarTime - timeInterval <= snapshot.getKey().longValue())
                    snapshot.setValue(snapshot.getValue() + 1);
            }
        }
        return Collections.max(totalCarsToStartPeriodTimeMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    record CarDateTime(LocalDateTime movingTime, String carNumber) {
    }

    record CarTime(long timeStamp, String carNumber) {
    }

    @Test
    public void returnMostFrequencySortedInSlidingWindowTest() throws InterruptedException {
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
        Duration slidingWindowDuration = Duration.ofSeconds(1);

        SlidingWindowCache<String> stringSlidingWindowCache = new SlidingWindowCache<>(slidingWindowDuration);
        stringSlidingWindowCache.receive("www.google.com");
        Thread.sleep(slidingWindowDuration.plusMillis(1).toMillis());

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
