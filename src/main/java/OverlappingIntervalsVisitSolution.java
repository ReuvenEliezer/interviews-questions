import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OverlappingIntervalsVisitSolution {

    //https://stackoverflow.com/questions/19155454/find-the-time-period-with-the-maximum-number-of-overlapping-intervals
    public SolutionResult calculateMaxPeopleAtOnce(List<Visit> visitList) {
        if (visitList == null || visitList.isEmpty())
            return new SolutionResult(null, null, 0);
        if (visitList.size() == 1)
            return new SolutionResult(visitList.get(0).getEntryTime(), visitList.get(0).getExitTime(), 1);

        List<TimeEntry> timeEntryList = new ArrayList<>();
        for (Visit visit : visitList) {
            timeEntryList.add(new TimeEntry(visit.getEntryTime(), true));
            timeEntryList.add(new TimeEntry(visit.getExitTime(), false));
        }

        timeEntryList.sort(Comparator.comparing(TimeEntry::getTime).thenComparing(TimeEntry::isEntry));


        long count = 0;
        Boolean lastStart = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        long lastBestCount = 0;
        for (TimeEntry timeEntry : timeEntryList) {
            if (timeEntry.isEntry()) {
                count++;
                if (count > lastBestCount) {
                    lastBestCount = count;
                    lastStart = true;
                    start = timeEntry.getTime();
                }
            } else {
                if (lastStart != null) {
                    end = timeEntry.getTime();
                }
                count--;
                lastStart = null;
            }
        }


        System.out.println("start :" + start + " end: " + end + " lastBestCount: " + lastBestCount);

        return new SolutionResult(start, end, lastBestCount);
    }

    private class SolutionResult {
        LocalDateTime startDate;
        LocalDateTime endDate;
        long maxVisitors;

        public SolutionResult(LocalDateTime startDate, LocalDateTime endDate, long maxVisitors) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.maxVisitors = maxVisitors;
        }
    }

    private class TimeEntry {
        private LocalDateTime time;
        private boolean isEntry;

        public boolean isEntry() {
            return isEntry;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public TimeEntry(LocalDateTime time, boolean isEntry) {
            this.time = time;
            this.isEntry = isEntry;
        }
    }

    @Test
    public void visitSolutionTest1() {
        Visit visit5 = new Visit(LocalDateTime.of(1959, 1, 1, 0, 0, 0),
                LocalDateTime.of(1959, 1, 1, 0, 0, 0));
        Visit visit1 = new Visit(LocalDateTime.of(1990, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        Visit visit2 = new Visit(LocalDateTime.of(1995, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        Visit visit3 = new Visit(LocalDateTime.of(2010, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        Visit visit4 = new Visit(LocalDateTime.of(1992, 1, 1, 0, 0, 0),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0));

        OverlappingIntervalsVisitSolution overlappingIntervalsVisitSolution = new OverlappingIntervalsVisitSolution();

        SolutionResult result = overlappingIntervalsVisitSolution.calculateMaxPeopleAtOnce(Stream.of(visit1, visit2, visit3, visit4, visit5).collect(Collectors.toList()));
        Assert.assertEquals(3, result.maxVisitors);
        Assert.assertEquals(visit2.getEntryTime(), result.startDate);
        Assert.assertEquals(visit4.getExitTime(), result.endDate);
    }

    @Test
    public void visitSolutionTest2() {
        Visit visit5 = new Visit(LocalDateTime.of(2012, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        Visit visit6 = new Visit(LocalDateTime.of(2012, 1, 1, 0, 0, 0),
                LocalDateTime.of(2014, 1, 1, 0, 0, 0));
        Visit visit7 = new Visit(LocalDateTime.of(2012, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        Visit visit1 = new Visit(LocalDateTime.of(1990, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        Visit visit2 = new Visit(LocalDateTime.of(1995, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        Visit visit3 = new Visit(LocalDateTime.of(2010, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        Visit visit4 = new Visit(LocalDateTime.of(1992, 1, 1, 0, 0, 0),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0));

        OverlappingIntervalsVisitSolution overlappingIntervalsVisitSolution = new OverlappingIntervalsVisitSolution();

        SolutionResult result = overlappingIntervalsVisitSolution.calculateMaxPeopleAtOnce(Stream.of(visit1, visit2, visit3, visit4
                , visit5, visit6, visit7
        ).collect(Collectors.toList()));
        Assert.assertEquals(5, result.maxVisitors);
        Assert.assertEquals(visit5.getEntryTime(), result.startDate);
        Assert.assertEquals(visit7.getExitTime(), result.endDate);
    }

    private class Visit {
        private LocalDateTime entryTime;
        private LocalDateTime ExitTime;

        public Visit(LocalDateTime entryTime, LocalDateTime ExitTime) {
            this.entryTime = entryTime;
            this.ExitTime = ExitTime;
        }

        public LocalDateTime getEntryTime() {
            return entryTime;
        }

        public LocalDateTime getExitTime() {
            return ExitTime;
        }
    }

}