package com.interviews.questions.ranges;

import java.time.LocalDateTime;
import java.util.List;

public class PeriodTimeResult {
    LocalDateTime start, end;
    List<Integer> values;

    public PeriodTimeResult(LocalDateTime start, LocalDateTime end, List<Integer> values) {
        this.start = start;
        this.end = end;
        this.values = values;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "PeriodTimeResult{" +
                "start=" + start +
                ", end=" + end +
                ", values=" + values +
                '}';
    }
}
