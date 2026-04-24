package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;
import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new NullPointerException("Unit shouldn't be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // ✅ Conversion
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(converted, targetUnit);
    }

    // ✅ Alias for your test cases
    public Quantity<U> toConvert(U targetUnit) {
        return convertTo(targetUnit);
    }

    // ✅ Add
    public Quantity<U> add(Quantity<U> other) {
        if (other == null)
            throw new NullPointerException("Other quantity cannot be null");

        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new NullPointerException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.getUnit().convertToBaseUnit(other.getValue());

        double sumInBase = thisInBase + otherInBase;

        double result = targetUnit.convertFromBaseUnit(sumInBase);

        return new Quantity<>(result, targetUnit);
    }

    // ✅ Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.getUnit().convertToBaseUnit(other.getValue());

        return Math.abs(thisInBase - otherInBase) < EPSILON;
    }

    // ✅ HashCode
    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(base / EPSILON));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}