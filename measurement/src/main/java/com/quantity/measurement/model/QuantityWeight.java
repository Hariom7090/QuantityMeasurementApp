package com.quantity.measurement.model;

import com.quantity.measurement.enumsImpl.WeightUnit;

public class QuantityWeight {

    private final Quantity<WeightUnit> quantity;

    public QuantityWeight(double value, WeightUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public WeightUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityWeight add(QuantityWeight other) {
        Quantity<WeightUnit> result = quantity.add(other.quantity);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        Quantity<WeightUnit> result = quantity.add(other.quantity, targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public QuantityWeight toConvert(WeightUnit targetUnit) {
        Quantity<WeightUnit> result = quantity.toConvert(targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityWeight)) {return false;
        }
        return this.quantity.equals(((QuantityWeight) o).quantity);
    }


//    public Quantity<IMeasurable> convertTo(WeightUnit weightUnit) {
//    }
}

//    private static final double EPSILON = 1e-6;
//
//    private final double value;
//    private final Quantity<WeightUnit> unit;
//
//    // Constructor
//    public QuantityWeight(double value,  Quantity<WeightUnit> unit) {
//        if (unit == null) {
//            throw new IllegalArgumentException("Unit should not be null");
//        }
//        if (!Double.isFinite(value)) {
//            throw new IllegalArgumentException("Invalid value");
//        }
//        this.value = value;
//        this.unit = unit;
//    }
//
//    // Getters
//    public double getValue() {
//        return value;
//    }
//
//    public WeightUnit getUnit() {
//        return unit;
//    }
//
//    // ========================
//    // CONVERT (UC9)
//    // Original -> Kilogram (Base) -> TargetUnit
//    // ========================
//    public QuantityWeight convertTo(WeightUnit targetUnit) {
//        if (targetUnit == null) {
//            throw new IllegalArgumentException("Target unit should not be null");
//        }
//        double inKilogram = this.unit.convertToBaseUnit(this.value);
//        double targetValue = targetUnit.convertFromBaseUnit(inKilogram);
//        return new QuantityWeight(targetValue, targetUnit);
//    }
//
//    // ========================
//    // ADD with explicit target unit (UC9 — mirrors UC7)
//    // ========================
//    public Quantity<T> add(Quantity<T> other, Quantity<WeightUnit> targetUnit) {
//        if (other == null || targetUnit == null) {
//            throw new IllegalArgumentException("Other quantity and target unit must not be null");
//        }
//        if (!Double.isFinite(other.getValue())) {
//            throw new IllegalArgumentException("Invalid value in other quantity");
//        }
//        double sumInKg = this.unit.convertToBaseUnit(this.value)
//                + other.getUnit().convertToBaseUnit(other.getValue());
//        double result = targetUnit.convertFromBaseUnit(sumInKg);
//        return new Quantity<T>(result, targetUnit);
//    }
//
//    // ========================
//    // ADD with implicit target unit (UC9 — mirrors UC6)
//    // Result expressed in the unit of the first operand
//    // ========================
//    public QuantityWeight add(QuantityWeight other) {
//        return add(other, this.unit);
//    }
//
//    // ========================
//    // EQUALS (UC9)
//    // Cross-category safety: rejects any non-QuantityWeight object
//    // ========================
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//
//        QuantityWeight other = (QuantityWeight) obj;
//
//        double thisInKg  = this.unit.convertToBaseUnit(this.value);
//        double otherInKg = other.unit.convertToBaseUnit(other.value);
//
//        return Math.abs(thisInKg - otherInKg) < EPSILON;
//    }
//
//    @Override
//    public int hashCode() {
//        // Normalise to base unit so equal objects produce the same hash
//        return Double.hashCode(Math.round(this.unit.convertToBaseUnit(this.value) / EPSILON) * EPSILON);
//    }
//
//    @Override
//    public String toString() {
//        return "QuantityWeight{value=" + value + ", unit=" + unit + "}";
//    }
//}