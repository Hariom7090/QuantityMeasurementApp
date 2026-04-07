package com.quantity.measurement.enums;



public enum LengthUnit {

    FEET(12.0),

    INCH(1.0),

    YARDS(36.0),

    CENTIMETERS(0.393701);

    private final double toFeetFactor;

    LengthUnit(double toInchFactor) {
        this.toFeetFactor = toInchFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }

    public double fromFeet(double value){
        return value / toFeetFactor;

    }
}