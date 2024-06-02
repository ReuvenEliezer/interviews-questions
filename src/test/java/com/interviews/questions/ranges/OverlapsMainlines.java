package com.interviews.questions.ranges;

import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class OverlapsMainlines {

    /**
     * https://stackoverflow.com/questions/17543091/intersecting-overlapping-intervals-in-java
     * <p>
     * I have an input set of date ranges that may overlap. Instead of combining these overlapping date ranges, I want to create new date ranges with adjusted dates, e.g.:
     * <p>
     * |----------10---------–|
     * *********|--10-|
     * *************|-----10----–|
     * should end up in:
     * <p>
     * |-------|---|-|--------|---|
     * |--10---|20|30|---20---|-10|
     */

    //Split overlapping and add missing intervals --- https://stackoverflow.com/questions/44811291/split-overlapping-and-add-missing-intervals
    @Test
    public void intersectingOverlappingIntervals2() {
        /**
         * Split overlapping and add missing intervals
         * https://stackoverflow.com/questions/628837/how-to-divide-a-set-of-overlapping-ranges-into-non-overlapping-ranges
         * https://stackoverflow.com/questions/5963847/is-there-possibility-of-sum-of-arraylist-without-looping
         */

    }

    /**
     * https://stackoverflow.com/questions/24744601/intervals-to-non-overlapping-subintervals
     * //     * @param originals
     *
     * @return
     */
//    private static List<StartEndShift> determineSubIntervals(final StartEndShift[] originals) {
//        System.out.println("Originals: " + Arrays.toString(originals));
//        final Set<Integer> endpoints = extractOrderedUniqueEndpoints(originals);
//        final List<StartEndShift> candidates = createConsecutiveIntervals(endpoints);
//        final List<StartEndShift> subIntervals = removeDisjunct(candidates, originals);
//        System.out.println("Sub intervals: " + subIntervals);
//        return subIntervals;
//    }
//
//    private static Set<Integer> extractOrderedUniqueEndpoints(final StartEndShift[] intervals) {
//        final Set<Integer> endpoints = new TreeSet<>();
//        for (final StartEndShift interval : intervals) {
//            endpoints.add(interval.getStart());
//            endpoints.add(interval.getEnd());
//        }
//        return endpoints;
//    }
//
//    private static List<StartEndShift> createConsecutiveIntervals(final Set<Integer> endpoints) {
//        final List<StartEndShift> intervals = new ArrayList<StartEndShift>();
//        final Iterator<Integer> it = endpoints.iterator();
//        Integer start = null;
//        while (it.hasNext()) {
//            final Integer end = it.next();
//            if (start != null) {
//                final StartEndShift candidate = new StartEndShift(start, end);
//                intervals.add(candidate);
//            }
//            start = end;
//        }
//        return intervals;
//    }
//
//    private static List<StartEndShift> removeDisjunct(final List<StartEndShift> candidates, final StartEndShift[] intervals) {
//        final Iterator<StartEndShift> it = candidates.iterator();
//        while (it.hasNext()) {
//            final StartEndShift candidate = it.next();
//            if (isDisjunct(candidate, intervals)) {
//                it.remove();
//            }
//        }
//        return candidates;
//    }
//
//    private static boolean isDisjunct(final StartEndShift candidate, final StartEndShift[] intervals) {
//        final int a = candidate.getStart();
//        final int b = candidate.getEnd();
//        for (final StartEndShift interval : intervals) {
//            final int x = interval.getStart();
//            final int y = interval.getEnd();
//            if ((b > x) && (y > a)) {
//                return false;
//            }
//        }
//        return true;
//    }
    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges() {
        /**
         * https://softwareengineering.stackexchange.com/questions/363091/split-overlapping-ranges-into-all-unique-ranges?newreg=942197cefec54b70857c0715cb29f4f1#comment829992_363096
         */
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
//        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(5), 1);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(10), 1);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(7), start.plusMinutes(12), 2);
        StartEndShift ml3 = new StartEndShift(start.plusMinutes(9), start.plusMinutes(15), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2, ml3));

        Assert.assertEquals(5, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(7), 1), result.get(0));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(7), start.plusMinutes(9), 3), result.get(1));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(9), start.plusMinutes(10), 6), result.get(2));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(10), start.plusMinutes(12), 5), result.get(3));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(12), start.plusMinutes(15), 3), result.get(4));
    }

    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges1() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(15), 3);
        StartEndShift ml2 = new StartEndShift(start, start.plusMinutes(1), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2));
        Assert.assertEquals(2, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(1), 6), result.get(0));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(1), start.plusMinutes(15), 3), result.get(1));

    }

    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges2() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(15), 3);
        StartEndShift ml2 = new StartEndShift(start, start.plusMinutes(15), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2));
        Assert.assertEquals(1, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(15), 6), result.get(0));
    }

    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges3() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(5), 3);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(1), start.plusMinutes(6), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2));
        Assert.assertEquals(3, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(1), 3), result.get(0));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(1), start.plusMinutes(5), 6), result.get(1));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(5), start.plusMinutes(6), 3), result.get(2));

    }

    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges4() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(5), 3);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(5), start.plusMinutes(6), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2));
        Assert.assertEquals(2, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(5), 3), result.get(0));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(5), start.plusMinutes(6), 3), result.get(1));
    }

    @Test
    public void splitOverlappingRangesIntoAllUniqueRanges5() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(5), 3);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(6), start.plusMinutes(7), 3);

        List<StartEndShift> result = splitOverlappingRangesIntoAllUniqueRanges(Arrays.asList(ml1, ml2));
        Assert.assertEquals(2, result.size());

        Assert.assertEquals(new StartEndShift(start, start.plusMinutes(5), 3), result.get(0));
        Assert.assertEquals(new StartEndShift(start.plusMinutes(6), start.plusMinutes(7), 3), result.get(1));
    }



    @Test
    public void mergeOverlapping() {
        /**
         * https://stackoverflow.com/questions/31670849/merge-overlapping-intervals
         */
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = start.plusMinutes(15);
//        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(5), 1);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(10), 1);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(7), start.plusMinutes(12), 2);
        StartEndShift ml3 = new StartEndShift(start.plusMinutes(9), end, 3);
        StartEndShift ml4 = new StartEndShift(end.plusMinutes(9), end.plusHours(1), 3);

        List<StartEndShift> result = mergeOverlapping(Arrays.asList(ml1, ml2, ml3, ml4));
    }

    @Test
    public void removeAll() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(1);
        integerList.remove(1);
    }

    @Test
    public void intersectingOverlappingIntervals() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = start.plusMinutes(25);
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(22), 10);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(9), start.plusMinutes(13), 10);
        StartEndShift ml3 = new StartEndShift(start.plusMinutes(12), end, 10);
//        StartEndShift ml4 = new StartEndShift(start.plusMinutes(50), end.plusDays(10), 10);
        List<StartEndShift> result1 = calcOverlapping(Arrays.asList(ml1, ml2, ml3));
        List<StartEndShift> result2 = calcOverlapping1(Arrays.asList(ml1, ml2, ml3));

//        List<StartEndShift> result = calcOverlappingMl(Arrays.asList(ml1, ml2, ml3,ml4));
        List<StartEndShift> result = calcOverlappingMl(Arrays.asList(ml1, ml2, ml3));

        Assert.assertEquals(5, result.size());
        Assert.assertEquals(10, result.get(0).getCapacity());
        Assert.assertEquals(20, result.get(1).getCapacity());
        Assert.assertEquals(30, result.get(2).getCapacity());
        Assert.assertEquals(20, result.get(3).getCapacity());
        Assert.assertEquals(10, result.get(4).getCapacity());
        Assert.assertEquals(start, result.get(0).getStart());
        Assert.assertEquals(end, result.get(result.size() - 1).getEnd());
    }

    private List<StartEndShift> splitOverlappingRangesIntoAllUniqueRanges(List<StartEndShift> list) {
        /**
         *  https://softwareengineering.stackexchange.com/questions/363091/split-overlapping-ranges-into-all-unique-ranges?newreg=942197cefec54b70857c0715cb29f4f1#comment829992_363096
         */
        List<EdgeTimeValue> edgeTimeValues = new ArrayList<>();
        for (StartEndShift shift : list) {
            LocalDateTime start = shift.getStart();
            LocalDateTime end = shift.getEnd();
            int capacity = shift.getCapacity();
            EdgeTimeValue startEdgeTimeValue = new EdgeTimeValue(start, capacity, false);
            EdgeTimeValue endEdgeTimeValue = new EdgeTimeValue(end, capacity, true);
            edgeTimeValues.addAll(Arrays.asList(startEdgeTimeValue, endEdgeTimeValue));
        }
        Collections.sort(edgeTimeValues, Comparator.comparing(EdgeTimeValue::getTime).thenComparing(EdgeTimeValue::isEndTime));
        System.out.println(edgeTimeValues);
        List<PeriodTimeResult> resultList = new ArrayList<>();
        List<StartEndShift> shiftResultList = new ArrayList<>();
        List<Integer> currentS = new ArrayList<>();
        for (int i = 0; i < edgeTimeValues.size(); i++) {
            EdgeTimeValue triplet = edgeTimeValues.get(i);

            LocalDateTime timeTagN;
//            if (triplet.isEndTime()) {
//                currentS.remove(triplet.getValue().get(0));
//                timeTagN = triplet.getTime();//.plusNanos(1);
//            } else {
//                currentS.add(triplet.getValue().get(0));
//                timeTagN = triplet.getTime();
//            }

            if (triplet.isEndTime()) {
                currentS.remove(triplet.getValue().get(0));
            } else {
                currentS.add(triplet.getValue().get(0));
            }
            timeTagN = triplet.getTime();

            LocalDateTime timeTagM;

            if (i + 1 < edgeTimeValues.size()) {
                EdgeTimeValue next = edgeTimeValues.get(i + 1);
//                if (nextTriplet.isEndTime()) {
//                    timeTagM = nextTriplet.getTime();
//                } else {
//                    timeTagM = nextTriplet.getTime();//.minusNanos(1);
//                }
                timeTagM = next.getTime();
            } else {
                timeTagM = triplet.getTime();
            }

            /**
             *
             This answer doesn't take account of gaps (gaps should not appear in output), so I refined it: * If e=false, add a to S. If e=true, take away a from S. * Define n'=n if e=false or n'=n+1 if e=true * Define m'=m-1 if f=false or m'=m if f=true * If n' <= m' and (e and not f) = false, output (n',m',S), otherwise output nothing. – silentman.it
             */
//            if (!timeTagN.isAfter(timeTagM) && i + 1 < edgeTimeValues.size() && ((triplet.isEndTime && !edgeTimeValues.get(i + 1).isEndTime) == false)) {
            if (!timeTagN.isEqual(timeTagM)
                    && !timeTagN.isAfter(timeTagM)
                    && i + 1 < edgeTimeValues.size()
                    && ((triplet.isEndTime && !edgeTimeValues.get(i + 1).isEndTime) == false)) {

                PeriodTimeResult tripletsResult = new PeriodTimeResult(timeTagN, timeTagM, currentS);
//                System.out.println(tripletsResult);
                StartEndShift result = new StartEndShift(timeTagN, timeTagM, currentS.stream().mapToInt(Integer::intValue).sum());
                System.out.println(result);
                shiftResultList.add(result);
                resultList.add(tripletsResult);
            }
        }
        return shiftResultList;
    }

    private List<StartEndShift> mergeOverlapping(List<StartEndShift> shifts) {
        //https://stackoverflow.com/questions/31670849/merge-overlapping-intervals
        if (shifts == null || shifts.isEmpty() || shifts.size() == 1)
            return shifts;

        Collections.sort(shifts, Comparator.comparing(StartEndShift::getStart).thenComparing(StartEndShift::getEnd));

        StartEndShift first = shifts.get(0);
        LocalDateTime start = first.getStart();
        LocalDateTime end = first.getEnd();

        ArrayList<StartEndShift> result = new ArrayList<>();

        for (int i = 1; i < shifts.size(); i++) {
            StartEndShift current = shifts.get(i);
            if (!current.getStart().isAfter(end)) {
                long endMillis = Math.max(current.getEnd().toInstant(ZoneOffset.UTC).toEpochMilli(), end.toInstant(ZoneOffset.UTC).toEpochMilli());
                end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endMillis), ZoneOffset.UTC);
            } else {
                result.add(new StartEndShift(start, end));
                start = current.getStart();
                end = current.getEnd();
            }
        }
        result.add(new StartEndShift(start, end));
        return result;
    }

    private List<StartEndShift> calcOverlapping(List<StartEndShift> startEndShifts) {

        List<LocalDateTime> localDateTimeList = new ArrayList<>(startEndShifts.size() * 2);
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getStart()).toList());
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getEnd()).toList());
        Collections.sort(localDateTimeList);

        List<StartEndShift> rangesOutput = new ArrayList<>();

        for (int i = 1; i < localDateTimeList.size(); i++) {
            LocalDateTime start = localDateTimeList.get(i - 1); // Subtract one for silly index counting.
            LocalDateTime stop = localDateTimeList.get(i); // Subtract one for silly index counting. Or use ( i ) instead.
            if (!start.equals(stop)) {  // If not equal, proceed. (If equal, ignore and move on to next loop.)
                StartEndShift range = new StartEndShift(start, stop);
                rangesOutput.add(range);
            }
        }
        return rangesOutput;
    }

    private List<StartEndShift> calcOverlapping1(List<StartEndShift> startEndShifts) {

        List<LocalDateTime> localDateTimeList = new ArrayList<>(startEndShifts.size() * 2);
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getStart()).toList());
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getEnd()).toList());
        Collections.sort(localDateTimeList);

        List<StartEndShift> rangesOutput = new ArrayList<>();

        for (int i = 1; i < localDateTimeList.size(); i++) {
            LocalDateTime start = localDateTimeList.get(i - 1); // Subtract one for silly index counting.
            LocalDateTime stop = localDateTimeList.get(i); // Subtract one for silly index counting. Or use ( i ) instead.
            if (!start.equals(stop)) {  // If not equal, proceed. (If equal, ignore and move on to next loop.)
                StartEndShift range = new StartEndShift(start, stop);
                rangesOutput.add(range);
            }
        }
        return rangesOutput;
    }

    private List<StartEndShift> calcOverlappingMl(List<StartEndShift> startEndShifts) {
        Comparator<StartEndShift> startShiftComparator = Comparator.comparing(StartEndShift::getStart);
        Comparator<StartEndShift> endShiftComparator = Comparator.comparing(StartEndShift::getEnd);
        Collections.sort(startEndShifts, startShiftComparator.thenComparing(endShiftComparator));

        Map<LocalDateTime, Integer> specificMinuteTimeToCapacityMap = new TreeMap<>();

        LocalDateTime start = startEndShifts.get(0).getStart();
        LocalDateTime end = startEndShifts.get(startEndShifts.size() - 1).getEnd();
        LocalDateTime currentTime = start;
        while (currentTime.isBefore(end)) {
            specificMinuteTimeToCapacityMap.put(currentTime, 0);
            for (StartEndShift startEndShift : startEndShifts) {
                if (!startEndShift.getStart().isAfter(currentTime) && !startEndShift.getEnd().isBefore(currentTime)) {
                    specificMinuteTimeToCapacityMap.put(currentTime, specificMinuteTimeToCapacityMap.get(currentTime) + startEndShift.getCapacity());
                }
            }
            currentTime = currentTime.plusMinutes(1);
        }

        return reduceInterval(specificMinuteTimeToCapacityMap, end);
    }

    private List<StartEndShift> reduceInterval(Map<LocalDateTime, Integer> specificMinuteTimeToCapacityMap, LocalDateTime endTime) {
        List<StartEndShift> result = new ArrayList<>();
        Integer capacityValue = null;
        for (Map.Entry<LocalDateTime, Integer> entry : specificMinuteTimeToCapacityMap.entrySet()) {
            if (!entry.getValue().equals(capacityValue)) {
                capacityValue = entry.getValue();
                StartEndShift startEndShift = new StartEndShift(entry.getKey(), null, capacityValue);
                setEndDate(result, entry.getKey());
                result.add(startEndShift);
            }
        }

        setEndDate(result, endTime);
        return result;
    }

    private void setEndDate(List<StartEndShift> result, LocalDateTime key) {
        if (!result.isEmpty()) {
            StartEndShift prevEndShift = result.get(result.size() - 1);
            prevEndShift.setEnd(key);
        }
    }

}
