package overlapping.ranges;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EdgeTimeValue {
    LocalDateTime time;
    List<Integer> value = new ArrayList<>();
    boolean isEndTime;

    public EdgeTimeValue(LocalDateTime time, int value, boolean isEndTime) {
        this.time = time;
        this.value.add(value);
        this.isEndTime = isEndTime;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<Integer> getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value.add(value);
    }

    public boolean isEndTime() {
        return isEndTime;
    }

    public void setEndTime(boolean endTime) {
        isEndTime = endTime;
    }

    @Override
    public String toString() {
        return "Triplets{" +
                "time=" + time +
                ", value=" + value +
                ", isEndTime=" + isEndTime +
                '}';
    }
}
