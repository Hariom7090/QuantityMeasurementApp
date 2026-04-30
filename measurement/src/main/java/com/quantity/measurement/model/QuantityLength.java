package com.quantity.measurement.model;

import com.quantity.measurement.enumsImpl.LengthUnit;

public class QuantityLength {
    private final Quantity<LengthUnit> quantity;

    public QuantityLength(double value, LengthUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public LengthUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityLength add(QuantityLength other) {
        Quantity<LengthUnit> result = quantity.add(other.quantity);
        return new QuantityLength(result.getValue(), result.getUnit());
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        Quantity<LengthUnit> result = quantity.add(other.quantity, targetUnit);
        return new QuantityLength(result.getValue(), result.getUnit());
    }

    public QuantityLength toConvert(LengthUnit targetUnit) {
        Quantity<LengthUnit> result = quantity.toConvert(targetUnit);
        return new QuantityLength(result.getValue(), result.getUnit());
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof QuantityLength))  return false;
        return this.quantity.equals(((QuantityLength) o).quantity);
    }
}
//
//    private static final double EPSILON = 1e-6;
//
//    private final double value;
//    private final LengthUnit unit;
//
//    // Constructor
//    public QuantityLength(double value, LengthUnit unit) {
//        if (unit == null) {
//            throw new IllegalArgumentException("Unit should not be null");
//        }
//
//        if (!Double.isFinite(value)) {
//            throw new IllegalArgumentException("Invalid value");
//        }
//
//        this.value = value;
//        this.unit = unit;
//    }
//
//    // Getters
//    public double getValue() {
//        return value;
//    }
//
//    public LengthUnit getUnit() {
//        return unit;
//    }
//
//    // ========================
//    // ADD with target unit (UC7)
//    // ========================
//    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
//
//        if (other == null || targetUnit == null) {
//            throw new IllegalArgumentException("Other quantity and target unit must not be null");
//        }
//
//        if (!Double.isFinite(other.value)) {
//            throw new IllegalArgumentException("Invalid value in other quantity");
//        }
//
//        double thisInFeet = this.unit.convertToBaseUnit(this.value);
//        double otherInFeet = other.unit.convertToBaseUnit(other.value);
//
//        double sumInFeet = thisInFeet + otherInFeet;
//
//        double result = targetUnit.convertFromBaseUnit(sumInFeet);
//
//        return new QuantityLength(result, targetUnit);
//    }
//
//    // ========================
//    // ADD (UC6)
//    // ========================
//    public QuantityLength add(QuantityLength other) {
//        return add(other, this.unit);
//    }
//
//    // ========================
//    // CONVERT (UC5 / UC8)
//    // Original -> Feet(Base) -> TargetUnit
//    // ========================
//    public QuantityLength toConvert(LengthUnit targetUnit) {
//        if (targetUnit == null) {
//            throw new IllegalArgumentException("target Unit should not null");
//        }
//
//        double thisInFeet = unit.convertToBaseUnit(this.value);
//        double targetValue = targetUnit.convertFromBaseUnit(thisInFeet);
//
//        return new QuantityLength(targetValue, targetUnit);
//    }
//
//    // ========================
//    // EQUALS (UC4)
//    // ========================
//    @Override
//    public boolean equals(Object obj) {
//
//        if (this == obj) return true;
//
//        if (obj == null || getClass() != obj.getClass()) return false;
//
//        QuantityLength other = (QuantityLength) obj;
//
//        double thisInFeet = this.unit.convertToBaseUnit(this.value);
//        double otherInFeet = other.unit.convertToBaseUnit(other.value);
//
//        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
//    }
//}