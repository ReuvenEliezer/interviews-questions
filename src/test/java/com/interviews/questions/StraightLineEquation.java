package com.interviews.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class StraightLineEquation implements Serializable{

    private double x;
    private double x1;
    private double x2;
    private double y;
    private double y1;
    private double y2;

    public StraightLineEquation(double x1, double x2, double y1, double y2, double x) {
        this.x = x;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public double getX() {
        return x;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public StraightLineEquation() {
    }

    public double getY() {
        double a = (y1 - y2) / (x1 - x2);
        double b = (2 * x1 * y2 + x1 * y1 + x2 * y1) / (2 * (x2 - x1));
        y = a * x + b;
        return y;
    }

    @Override
    public String toString() {
        return "StraightLineEquation{" +
                "x=" + x +
                ", x1=" + x1 +
                ", x2=" + x2 +
                ", y=" + y +
                ", y1=" + y1 +
                ", y2=" + y2 +
                '}';
    }
}
