import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
        StartEndShift ml1 = new StartEndShift(start, start.plusMinutes(22), 10);
        StartEndShift ml2 = new StartEndShift(start.plusMinutes(9), start.plusMinutes(13), 10);
        StartEndShift ml3 = new StartEndShift(start.plusMinutes(13), start.plusMinutes(25), 10);
        List<StartEndShift> startEndShifts = Arrays.asList(ml1, ml2, ml3);

        Comparator<StartEndShift> startShiftComparator = Comparator.comparing(StartEndShift::getStart);
        Comparator<StartEndShift> endShiftComparator = Comparator.comparing(StartEndShift::getEnd);

        Collections.sort(startEndShifts, startShiftComparator.thenComparing(endShiftComparator));

        Map<LocalDateTime, Integer> specificMinuteTimeToCapacityMap = new TreeMap<>();

        LocalDateTime startShift = startEndShifts.get(0).getStart();
        LocalDateTime endShift = startEndShifts.get(startEndShifts.size() - 1).getEnd();
        LocalDateTime currentTime = startShift;
        while (currentTime.isBefore(endShift)) {
            specificMinuteTimeToCapacityMap.put(currentTime, 0);
            for (StartEndShift startEndShift : startEndShifts) {
                if (!startEndShift.getStart().isAfter(currentTime) && !startEndShift.getEnd().isBefore(currentTime)) {
                    specificMinuteTimeToCapacityMap.put(currentTime, specificMinuteTimeToCapacityMap.get(currentTime) + startEndShift.getCapacity());
                }
            }
            currentTime = currentTime.plusMinutes(1);
        }

        List<StartEndShift> result = new ArrayList<>();
        Integer capacityValue = null;
        for (Map.Entry<LocalDateTime, Integer> entry : specificMinuteTimeToCapacityMap.entrySet()) {
            if (capacityValue == null || !entry.getValue().equals(capacityValue)) {
                capacityValue = entry.getValue();
                StartEndShift startEndShift = new StartEndShift(entry.getKey(), null, capacityValue);
                if (!result.isEmpty()) {
                    StartEndShift prevEndShift = result.get(result.size() - 1);
                    prevEndShift.setEnd(entry.getKey());
                }
                result.add(startEndShift);
            }
        }

        if (!result.isEmpty()) {
            StartEndShift lastEndShift = result.get(result.size() - 1);
            lastEndShift.setEnd(endShift);
        }

        Assert.assertEquals(5, result.size());
        Assert.assertEquals(10, result.get(0).getCapacity());
        Assert.assertEquals(20, result.get(1).getCapacity());
        Assert.assertEquals(30, result.get(2).getCapacity());
        Assert.assertEquals(20, result.get(3).getCapacity());
        Assert.assertEquals(10, result.get(4).getCapacity());
        Assert.assertEquals(startShift, result.get(0).getStart());
        Assert.assertEquals(endShift, result.get(result.size()-1).getEnd());
    }

    class StartEndShift {
        LocalDateTime start, end;
        int capacity;

        public StartEndShift(LocalDateTime start, LocalDateTime end, int capacity) {
            this.start = start;
            this.end = end;
            this.capacity = capacity;
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
