package com.quantity.measurement.model;

import com.quantity.measurement.enums.WeightUnit;

public class QuantityWeight {

    private final Quantity<WeightUnit> quantity;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null)
            throw new NullPointerException("Unit should not be null");

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
        Quantity<WeightUnit> result = quantity.convertTo(targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityWeight)) return false;
        return this.quantity.equals(((QuantityWeight) o).quantity);
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}