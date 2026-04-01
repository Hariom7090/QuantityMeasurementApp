package com.quantity.measurement.enums;



public enum LengthUnit {

    FEET(12.0),

    INCH(1.0),

    YARDS(36.0),

    CENTIMETERS(0.393701);

    private final double toInchFactor;

    LengthUnit(double toInchFactor) {
        this.toInchFactor = toInchFactor;
    }

    public double toFeet(double value) {
        return value * toInchFactor;
    }
}