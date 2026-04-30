package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;
import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double EPSILON = 1e-6;
    private final double value;
    private final U unit;

    // Constructor
    public Quantity(double value, U unit) {
        if (unit == null)
            throw new NullPointerException("Unit shouldn't be null");
        if (Double.isNaN(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // Getters
    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // Conversion
    public Quantity<U> toConvert(U targetUnit) {
        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(converted, targetUnit);
    }

    // Add
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null || targetUnit == null)
            throw new NullPointerException("Second quantity & targetUnit must not be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cannot operate on different measurement categories");

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double sumBase = thisBase + otherBase;
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new Quantity<>(result, targetUnit);
    }
    // Subtraction
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        if (other == null || targetUnit == null)
            throw new NullPointerException(
                    "Quantity and Target Unit must not be empty");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException(
                    "Cannot operate on different measurement categories");

        double thisBase = this.unit.convertToBaseUnit(this.getValue());
        double otherBase = other.unit.convertToBaseUnit(other.getValue());

        double baseSubtraction = thisBase - otherBase;
        double result = targetUnit.convertFromBaseUnit(baseSubtraction);

        return new Quantity<>(result, targetUnit);
    }

    // Divide
    public double divide(Quantity<U> other) {

        if (other == null)
            throw new NullPointerException(
                    "Quantity and Target Unit must not be empty");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException(
                    "Cannot operate on different measurement categories");

        double thisBase = this.unit.convertToBaseUnit(this.getValue());
        double otherBase = other.unit.convertToBaseUnit(other.getValue());

        if (Math.abs(otherBase) < EPSILON)
            throw new ArithmeticException("Can not divisible by 0 or less");

        return thisBase / otherBase;
    }


    // Equality Check
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double thisInFeet =
                this.unit.convertToBaseUnit(this.value);

        double otherInFeet =
                other.unit.convertToBaseUnit(other.getValue());

        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(base / EPSILON));
    }
}