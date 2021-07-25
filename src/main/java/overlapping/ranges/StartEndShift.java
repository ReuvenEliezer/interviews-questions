package overlapping.ranges;

import java.time.LocalDateTime;
import java.util.Objects;

public class StartEndShift {
    private LocalDateTime start, end;
    private int capacity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartEndShift that = (StartEndShift) o;
        return capacity == that.capacity && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, capacity);
    }

    @Override
    public String toString() {
        return "StartEndShift{" +
                "start=" + start +
                ", end=" + end +
                ", capacity=" + capacity +
                '}';
    }
}