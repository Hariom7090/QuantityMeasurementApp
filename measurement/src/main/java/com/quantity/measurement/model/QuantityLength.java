package com.quantity.measurement.model;
import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private final double EPSILON=1e-6;

    public QuantityLength(double value, LengthUnit unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }
    public double toFeet() {

        return unit.toFeet(value);
    }

    public double toConvert(LengthUnit targetUnit) {

        return convert(this.value,this.unit,targetUnit);
    }

    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (sourceUnit == null || targetUnit == null) {
            throw new IllegalArgumentException("Source/Target unit cannot be null");
        }
        if (Double.isNaN(value) ||Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        double valueInFeet = sourceUnit.toFeet(value);
        return targetUnit.fromFeet(valueInFeet);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;
        QuantityLength other = (QuantityLength) obj;
        double thisInFeet = this.toFeet();
        double otherInFeet = other.toFeet();
        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
    }

    @Override
    public String toString() {
        return value + " " + unit.name();
    }

    private double toBaseUnit() {
        return unit.toFeet(value);
    }

    public QuantityLength add(QuantityLength other,LengthUnit targetUnit) {

        if (other == null || targetUnit==null) {
            throw new IllegalArgumentException("Second quantity and targetUnit  must not be null");

        }
        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        double thisInFeet = this.toBaseUnit();
        double otherInFeet = other.toBaseUnit();

        double sumInFeet = thisInFeet + otherInFeet;
        double resultValue = targetUnit.fromFeet(sumInFeet);
        return new QuantityLength(resultValue, targetUnit);

    }
    public QuantityLength add(QuantityLength other) {

        return add(other,this.unit);
    }
}