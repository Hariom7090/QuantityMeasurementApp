package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new NullPointerException("Unit shouldn't be null");
        if (Double.isNaN(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> toConvert(U targetUnit) {
        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(converted, targetUnit);
    }

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });

        private final DoubleBinaryOperator op;

        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }

        double apply(double a, double b) {
            return op.applyAsDouble(a, b);
        }
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private void validate(Quantity<U> other, U target, boolean requireTarget) {
        if (other == null) {
            throw new NullPointerException("Quantity must not be null");
        }

        if (requireTarget && target == null) {
            throw new NullPointerException("Target unit must not be null");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Different measurement types");
        }
    }

    private double base(U unit, double value) {
        return unit.convertToBaseUnit(value);
    }

    private double operate(Quantity<U> other, ArithmeticOperation op) {
        double a = base(this.unit, this.value);
        double b = base(other.unit, other.value);
        return op.apply(a, b);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        this.unit.validateOperationSupport("ADD");

        double resultBase = operate(other, ArithmeticOperation.ADD);
        double converted = targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        this.unit.validateOperationSupport("SUBTRACT");

        double resultBase = operate(other, ArithmeticOperation.SUBTRACT);
        double converted = targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(converted, targetUnit);
    }

    public double divide(Quantity<U> other) {
        validate(other, null, false);
        this.unit.validateOperationSupport("DIVIDE");
        return operate(other, ArithmeticOperation.DIVIDE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);

        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(base / EPSILON));
    }
}