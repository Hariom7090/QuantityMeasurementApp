package com.quantity.measurement.enums;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(1.0 / 2.20462);

    private final double toKilogramFactor;

    WeightUnit(double toKilogramFactor) {
        this.toKilogramFactor = toKilogramFactor;
    }

    @Override
    public double getConversionFactor() {
        return toKilogramFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        validate(value);
        return value * toKilogramFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        validate(value);
        return value / toKilogramFactor;
    }

    private void validate(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}