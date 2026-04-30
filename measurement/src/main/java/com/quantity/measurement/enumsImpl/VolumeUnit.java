package com.quantity.measurement.enumsImpl;

import com.quantity.measurement.enums.IMeasurable;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double toLitreFactor;

    VolumeUnit(double toLitreFactor) {
        this.toLitreFactor = toLitreFactor;
    }

    @Override
    public double getConversionFactor() {
        return toLitreFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        validate(value);
        return value * toLitreFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        validate(value);
        return value / toLitreFactor;
    }

    private void validate(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}