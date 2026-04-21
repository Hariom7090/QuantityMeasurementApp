package com.quantity.measurement.model;
import com.quantity.measurement.enums.WeightUnit;

public class QuantityWeight {
    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double baseValue = unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new QuantityWeight(convertedValue, targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        double sumInBase = this.unit.convertToBaseUnit(this.value) +
                other.unit.convertToBaseUnit(other.value);
        double finalValue = targetUnit.convertFromBaseUnit(sumInBase);
        return new QuantityWeight(finalValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight that = (QuantityWeight) obj;


        double v1 = this.unit.convertToBaseUnit(this.value);
        double v2 = that.unit.convertToBaseUnit(that.value);


        return Math.abs(v1 - v2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(Math.round(unit.convertToBaseUnit(value) * 1000000.0));
    }
//    @Override
//    public String toString() {
//        return String.format("%s %s", value, unit);
//    }
}