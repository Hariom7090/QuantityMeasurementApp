package com.quantity.measurement.enums;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCH(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double getConversionFactor() {
        return toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        validate(value);
        return value * toFeetFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        validate(value);
        return value / toFeetFactor;
    }

    private void validate(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}