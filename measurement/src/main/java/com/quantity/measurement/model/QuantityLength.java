package com.quantity.measurement.model;
import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final double EPSILON = 1e-6;
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if(unit==null) throw new IllegalArgumentException("unit should not be null");
        this.value = value;
        this.unit = unit;
    }

    public double toConvert(LengthUnit targetUnit){
        return convert(this.value,this.unit,targetUnit);
    }

    public static double convert(double value, LengthUnit sourceUnit,LengthUnit targetUnit){
        if(sourceUnit == null || targetUnit == null){
            throw  new IllegalArgumentException("unit should not be empty");
        }
        if(!Double.isFinite(value)){
            throw  new IllegalArgumentException("Invalid numeric value !");
        }
        double valueInFeet = sourceUnit.toFeet(value);
        return targetUnit.fromFeet(valueInFeet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength other = (QuantityLength) obj;
        double thisInFeet=this.unit.toFeet(this.value);
        double otherInFeet=other.unit.toFeet(other.value);
        return Math.abs(thisInFeet-otherInFeet) < EPSILON;
    }
}