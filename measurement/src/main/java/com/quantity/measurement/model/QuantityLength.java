package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final Quantity<LengthUnit> quantity;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null)
            throw new NullPointerException("Unit should not be null");

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
        Quantity<LengthUnit> result = quantity.convertTo(targetUnit);
        return new QuantityLength(result.getValue(), result.getUnit());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityLength)) return false;
        return this.quantity.equals(((QuantityLength) o).quantity);
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}