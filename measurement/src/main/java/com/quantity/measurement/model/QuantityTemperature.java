package com.quantity.measurement.model;

import com.quantity.measurement.enumsImpl.TemperatureUnit;

public class QuantityTemperature {

    private final Quantity<TemperatureUnit> quantity;

    public QuantityTemperature(double value, TemperatureUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public TemperatureUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityTemperature toConvert(TemperatureUnit targetUnit) {
        Quantity<TemperatureUnit> result = quantity.toConvert(targetUnit);
        return new QuantityTemperature(result.getValue(), result.getUnit());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityTemperature)) {
            return false;
        }
        return this.quantity.equals(((QuantityTemperature) o).quantity);
    }
}