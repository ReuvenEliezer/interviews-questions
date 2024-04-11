package com.interviews.questions;

import java.time.LocalDateTime;
import java.util.Date;

public class Tick {

    private int counter;
    private boolean isWaterMeter;
    private boolean isfertilizerMeter;

    private LocalDateTime localDateTime;

    public Tick(int counter, LocalDateTime localDateTime) {
        this.counter = counter;
        this.localDateTime = localDateTime;
    }

    public boolean isWaterMeter() {
        return isWaterMeter;
    }

    public void setWaterMeter(boolean waterMeter) {
        isWaterMeter = waterMeter;
    }

    public boolean isIsfertilizerMeter() {
        return isfertilizerMeter;
    }

    public void setIsfertilizerMeter(boolean isfertilizerMeter) {
        this.isfertilizerMeter = isfertilizerMeter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
