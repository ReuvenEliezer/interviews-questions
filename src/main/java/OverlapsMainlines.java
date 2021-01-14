import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private List<StartEndShift> calcOverlapping(List<StartEndShift> startEndShifts) {

        List<LocalDateTime> localDateTimeList = new ArrayList<>(startEndShifts.size() * 2);
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getStart()).collect(Collectors.toList()));
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getEnd()).collect(Collectors.toList()));
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
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getStart()).collect(Collectors.toList()));
        localDateTimeList.addAll(startEndShifts.stream().map(startEndShift -> startEndShift.getEnd()).collect(Collectors.toList()));
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

    class StartEndShift {
        LocalDateTime start, end;
        int capacity;

        public StartEndShift(LocalDateTime start, LocalDateTime end, int capacity) {
            this.start = start;
            this.end = end;
            this.capacity = capacity;
        }

        public StartEndShift(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public int getCapacity() {
            return capacity;
        }
    }

}
