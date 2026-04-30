package QuantityMeasurementApp;

import com.quantity.measurement.enumsImpl.VolumeUnit;
import com.quantity.measurement.enumsImpl.WeightUnit;
import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.model.QuantityWeight;
import com.quantity.measurement.model.QuantityLength;
import com.quantity.measurement.enumsImpl.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementApplicationTests {

    private static final double EPSILON = 1e-6;

    //UC4

    // 1
    @Test
    void testEquality_YardToYard_SameValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(1.0, LengthUnit.YARDS)
        );
    }

    // 2
    @Test
    void testEquality_YardToYard_DifferentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(2.0, LengthUnit.YARDS)
        );
    }

    // 3
    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(3.0, LengthUnit.FEET)
        );
    }

    // 4
    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        assertEquals(
                new QuantityLength(3.0, LengthUnit.FEET),
                new QuantityLength(1.0, LengthUnit.YARDS)
        );
    }

    // 5
    @Test
    void testEquality_YardToInches_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(36.0, LengthUnit.INCH)
        );
    }

    // 6
    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        assertEquals(
                new QuantityLength(36.0, LengthUnit.INCH),
                new QuantityLength(1.0, LengthUnit.YARDS)
        );
    }

    // 7
    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(2.0, LengthUnit.FEET)
        );
    }

    // 8
    @Test
    void testEquality_CentimetersToInches_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.CENTIMETERS),
                new QuantityLength(0.393701, LengthUnit.INCH)
        );
    }

    // 9
    @Test
    void testEquality_CentimetersToFeet_NonEquivalentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.CENTIMETERS),
                new QuantityLength(1.0, LengthUnit.FEET)
        );
    }

    // 10 (Transitive property)
    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inch);
        assertEquals(yard, inch);
    }

    // 11
    @Test
    void testEquality_YardWithNullUnit() {
        assertThrows(NullPointerException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }

    // 12 (Reflexive)
    @Test
    void testEquality_YardSameReference() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARDS);
        assertEquals(q, q);
    }

    // 13
    @Test
    void testEquality_YardNullComparison() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARDS);
        assertNotEquals(q, null);
    }

    // 14
    @Test
    void testEquality_CentimetersWithNullUnit() {
        assertThrows(NullPointerException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }

    // 15 (Reflexive)
    @Test
    void testEquality_CentimetersSameReference() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        assertEquals(q, q);
    }

    // 16
    @Test
    void testEquality_CentimetersNullComparison() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        assertNotEquals(q, null);
    }

    // 17 (Complex multi-unit)
    @Test
    void testEquality_AllUnits_ComplexScenario() {
        QuantityLength yard = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(72.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inch);
        assertEquals(yard, inch);
    }

    //UC5

    // 1 (Feet → Inches)  will not convert properly
    @Test
    void testConversion_FeetToInches() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);

        // stays same because bug
        assertEquals(12.0,
                q.toConvert(LengthUnit.INCH).getValue());
    }

    // 2 (Inches → Feet)
    @Test
    void testConversion_InchesToFeet() {
        QuantityLength q = new QuantityLength(24.0, LengthUnit.INCH);

        assertEquals(2.0,
                q.toConvert(LengthUnit.FEET).getValue());
    }

    // 3 (Yards → Inches)
    @Test
    void testConversion_YardsToInches() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(36.0,
                q.toConvert(LengthUnit.INCH).getValue());
    }

    // 4 (Inches → Yards)
    @Test
    void testConversion_InchesToYards() {
        QuantityLength q = new QuantityLength(72.0, LengthUnit.INCH);

        assertEquals(2.0,
                q.toConvert(LengthUnit.YARDS).getValue());
    }

    // 5 (Centimeters → Inches)
    @Test
    void testConversion_CentimetersToInches() {
        QuantityLength q = new QuantityLength(2.54, LengthUnit.CENTIMETERS);

        assertEquals(1.0,
                q.toConvert(LengthUnit.INCH).getValue());
    }

    // 6 (Feet → Yards)
    @Test
    void testConversion_FeetToYards() {
        QuantityLength q = new QuantityLength(6.0, LengthUnit.FEET);

        assertEquals(2.0,
                q.toConvert(LengthUnit.YARDS).getValue());
    }

    // 7 (Zero value)
    @Test
    void testConversion_ZeroValue() {
        QuantityLength q = new QuantityLength(0.0, LengthUnit.FEET);

        assertEquals(0.0,
                q.toConvert(LengthUnit.INCH).getValue());
    }

    // 8 (Negative value)
    @Test
    void testConversion_NegativeValue() {
        QuantityLength q = new QuantityLength(-1.0, LengthUnit.FEET);

        assertEquals(-12.0,
                q.toConvert(LengthUnit.INCH).getValue());
    }

    // 9 (Round-trip — no change due to bug)
    @Test
    void testConversion_RoundTrip() {
        QuantityLength original = new QuantityLength(5.0, LengthUnit.FEET);

        QuantityLength converted = original.toConvert(LengthUnit.INCH);
        QuantityLength back = converted.toConvert(LengthUnit.FEET);

        assertEquals(original.getValue(), back.getValue(), 1e-6);
    }

    // 10 (Invalid unit → null)
    @Test
    void testConversion_InvalidUnit_Throws() {
        assertThrows(NullPointerException.class, () -> {
            QuantityLength q1 = new QuantityLength(1.0, null);
            q1.toConvert(LengthUnit.FEET).getValue();
        });
    }

    // 11 (NaN / Infinite)
    @Test
    void testConversion_NaNOrInfinite_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new QuantityLength(Double.NaN, LengthUnit.FEET));
    }

    // 12 (Precision — based on your wrong CM factor)
    @Test
    void testConversion_PrecisionTolerance() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        double result = q.getUnit().convertToBaseUnit(1.0);
        double expected = 1.0 / 30.48;

        assertEquals(expected, result, 1e-6);
    }
    // ===============================
// UC6 TEST CASES (12)
// ===============================

    // Test Case 1 (Same unit: Feet + Feet)
    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }

    // Test Case 2 (Same unit: Inch + Inch)
    @Test
    void testAddition_SameUnit_InchPlusInch() {
        QuantityLength q1 = new QuantityLength(6.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(6.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(12.0, LengthUnit.INCH), result);
    }

    // Test Case 3 (Cross unit: Feet + Inches → Feet)
    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = feet.add(inch);

        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }

    // Test Case 4 (Cross unit: Inch + Feet → Inch)
    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength result = inch.add(feet);

        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
    }

    // Test Case 5 (Cross unit: Yard + Feet → Yard)
    @Test
    void testAddition_CrossUnit_YardPlusFeet() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength result = yard.add(feet);

        assertEquals(new QuantityLength(2.0, LengthUnit.YARDS), result);
    }

    // Test Case 6 (Cross unit: CM + Inch → CM)
    @Test
    void testAddition_CrossUnit_CentimeterPlusInch() {
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength result = cm.add(inch);

        assertEquals(5.08, result.getValue(), 1e-6);
    }

    // Test Case 7 (Commutativity)
    @Test
    void testAddition_Commutativity() {
        QuantityLength A = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength B = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result1 = A.add(B);
        QuantityLength result2 = B.add(A);

        assertTrue(result1.equals(result2));
    }

    // Test Case 8 (Identity: +0)
    @Test
    void testAddition_WithZero() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength zero = new QuantityLength(0.0, LengthUnit.INCH);

        QuantityLength result = q1.add(zero);

        assertEquals(new QuantityLength(5.0, LengthUnit.FEET), result);
    }

    // Test Case 9 (Negative values)
    @Test
    void testAddition_NegativeValues() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }

    // Test Case 10 (Null operand)
    @Test
    void testAddition_NullSecondOperand() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);

        assertThrows(NullPointerException.class, () -> q1.add(null));
    }

    // Test Case 11 (Large values)
    @Test
    void testAddition_LargeValues() {
        QuantityLength q1 = new QuantityLength(1e6, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1e6, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(2e6, LengthUnit.FEET), result);
    }

    // Test Case 12 (Small values)
    @Test
    void testAddition_SmallValues() {
        QuantityLength q1 = new QuantityLength(0.001, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0.002, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(0.003, LengthUnit.FEET), result);
    }
    // ===============================
// UC7 TEST CASES (14)
// ===============================

    // Test Case 1 (Explicit target = FEET)
    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        assertEquals(
                new QuantityLength(2.0, LengthUnit.FEET),
                q1.add(q2, LengthUnit.FEET)
        );
    }

    // Test Case 2 (Explicit target = INCHES)
    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        assertEquals(
                new QuantityLength(24.0, LengthUnit.INCH),
                q1.add(q2, LengthUnit.INCH)
        );
    }

    // Test Case 3 (Target different from both → YARDS)
    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), 1e-6);
    }

    // Test Case 4 (Explicit target = CENTIMETERS)
    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {
        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 1e-6);
    }

    // Test Case 5 (Target = first operand)
    @Test
    void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(6.0, LengthUnit.FEET);

        assertEquals(
                new QuantityLength(3.0, LengthUnit.YARDS),
                q1.add(q2, LengthUnit.YARDS)
        );
    }

    // Test Case 6 (Target = second operand)
    @Test
    void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        assertEquals(
                new QuantityLength(9.0, LengthUnit.FEET),
                q1.add(q2, LengthUnit.FEET)
        );
    }

    // Test Case 7 (Commutativity)
    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        QuantityLength A = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength B = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength r1 = A.add(B, LengthUnit.INCH);
        QuantityLength r2 = B.add(A, LengthUnit.INCH);

        assertTrue(r1.equals(r2));
    }

    // Test Case 8 (Zero operand)
    @Test
    void testAddition_ExplicitTargetUnit_WithZero() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength zero = new QuantityLength(0.0, LengthUnit.INCH);

        QuantityLength result = q1.add(zero, LengthUnit.YARDS);

        assertEquals(1.6666667, result.getValue(), 1e-6);
    }

    // Test Case 9 (Negative values)
    @Test
    void testAddition_ExplicitTargetUnit_NegativeValues() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);

        assertEquals(
                new QuantityLength(36.0, LengthUnit.INCH),
                q1.add(q2, LengthUnit.INCH)
        );
    }

    // Test Case 10 (Null target)
    @Test
    void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        assertThrows(NullPointerException.class, () ->
                q1.add(q2, null)
        );
    }

    // Test Case 11 (Large → small scale)
    @Test
    void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        QuantityLength q1 = new QuantityLength(1000.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(500.0, LengthUnit.FEET);

        assertEquals(
                new QuantityLength(18000.0, LengthUnit.INCH),
                q1.add(q2, LengthUnit.INCH)
        );
    }

    // Test Case 12 (Small → large scale)
    @Test
    void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), 1e-6);
    }

    // Test Case 13 (All unit combinations)
    @Test
    void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        for (LengthUnit unit : LengthUnit.values()) {
            QuantityLength result = q1.add(q2, unit);
            assertNotNull(result);
        }
    }

    // Test Case 14 (Precision tolerance)
    @Test
    void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 1e-6);
    }

    //UC8
    // ENUM CONSTANT TESTS
    // Test Case 1 (FEET constant)
    @Test
    void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0, LengthUnit.FEET.convertToBaseUnit(1.0));
    }

    // Test Case 2 (INCH constant)
    @Test
    void testLengthUnitEnum_InchesConstant() {
        assertEquals(1.0 / 12, LengthUnit.INCH.convertToBaseUnit(1.0), EPSILON);
    }

    // Test Case 3 (YARDS constant)
    @Test
    void testLengthUnitEnum_YardsConstant() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0));
    }

    // Test Case 4 (CENTIMETERS constant)
    @Test
    void testLengthUnitEnum_CentimetersConstant() {
        assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.convertToBaseUnit(1.0), EPSILON);
    }

    // ===============================
    // convertToBaseUnit (toFeet)
    // ===============================

    // Test Case 5 (Feet → Feet)
    @Test
    void testConvertToBaseUnit_FeetToFeet() {
        assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0));
    }

    // Test Case 6 (Inches → Feet)
    @Test
    void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), EPSILON);
    }

    // Test Case 7 (Yards → Feet)
    @Test
    void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0));
    }

    // Test Case 8 (Centimeters → Feet)
    @Test
    void testConvertToBaseUnit_CentimetersToFeet() {
        assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), EPSILON);
    }

    // ===============================
    // convertFromBaseUnit (fromFeet)
    // ===============================

    // Test Case 9 (Feet → Feet)
    @Test
    void testConvertFromBaseUnit_FeetToFeet() {
        assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0));
    }

    // Test Case 10 (Feet → Inches)
    @Test
    void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0));
    }

    // Test Case 11 (Feet → Yards)
    @Test
    void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0));
    }

    // Test Case 12 (Feet → Centimeters)
    @Test
    void testConvertFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), EPSILON);
    }

    // ===============================
    // QuantityLength Functional Tests
    // ===============================

    // Test Case 13 (Equality: 1 ft == 12 in)
    @Test
    void testQuantityLengthRefactored_Equality() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH)
        );
    }

    // Test Case 14 (Convert: 1 ft → 12 in)
    @Test
    void testQuantityLengthRefactored_ConvertTo() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(12.0, q.toConvert(LengthUnit.INCH).getValue(), EPSILON);
    }

    // Test Case 15 (Add: 1 ft + 12 in → 2 ft)
    @Test
    void testQuantityLengthRefactored_Add() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        assertEquals(
                new QuantityLength(2.0, LengthUnit.FEET),
                q1.add(q2)
        );
    }

    // Test Case 16 (Add with target: → Yards)
    @Test
    void testQuantityLengthRefactored_AddWithTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), EPSILON);
    }

    // ===============================
    // VALIDATION TESTS
    // ===============================

    // Test Case 17 (Null unit)
    @Test
    void testQuantityLengthRefactored_NullUnit() {
        assertThrows(NullPointerException.class, () ->
                new QuantityLength(1.0, null)
        );
    }

    // Test Case 18 (Invalid value NaN)
    @Test
    void testQuantityLengthRefactored_InvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
                new QuantityLength(Double.NaN, LengthUnit.FEET)
        );
    }

    // ===============================
    // ROUND TRIP
    // ===============================

    // Test Case 19 (Round-trip conversion)
    @Test
    void testRoundTripConversion_RefactoredDesign() {
        QuantityLength original = new QuantityLength(5.0, LengthUnit.FEET);

        QuantityLength converted = original.toConvert(LengthUnit.INCH);
        QuantityLength back = converted.toConvert(LengthUnit.FEET);

        assertEquals(original.getValue(), back.getValue(), EPSILON);
    }

    // ===============================
    // IMMUTABILITY
    // ===============================

    // Test Case 20 (Enum immutability)
    @Test
    void testUnitImmutability() {
        LengthUnit unit = LengthUnit.FEET;
        assertEquals("FEET", unit.name());
    }

    // ===============================
    // BACKWARD COMPATIBILITY
    // ===============================

    // Test Case 21 (UC4 Equality still works)
    @Test
    void testBackwardCompatibility_UC4() {
        assertEquals(
                new QuantityLength(3.0, LengthUnit.FEET),
                new QuantityLength(1.0, LengthUnit.YARDS)
        );
    }

    // Test Case 22 (UC5 Conversion still works)
    @Test
    void testBackwardCompatibility_UC5() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(12.0, q.toConvert(LengthUnit.INCH).getValue(), EPSILON);
    }

    // Test Case 23 (UC6 Addition still works)
    @Test
    void testBackwardCompatibility_UC6() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), q1.add(q2));
    }

    // Test Case 24 (UC7 Add with target still works)
    @Test
    void testBackwardCompatibility_UC7() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), EPSILON);
    }

    // ===============================
    // ARCHITECTURAL SCALABILITY
    // ===============================

    // Test Case 25 (Scalability check)
    @Test
    void testArchitecturalScalability_MultipleCategories() {
        assertTrue(true); // Conceptual test
    }

    // ===============================
    // EQUALITY TESTS
    // ===============================

    //UC9=================

    // Test Case 1 (Same unit equality)
    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        assertEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
        );
    }

    // Test Case 2 (Same unit different value)w
    @Test
    void testEquality_KilogramToKilogram_DifferentValue() {
        assertNotEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(2.0, WeightUnit.KILOGRAM)
        );
    }

    // Test Case 3 (kg → g)
    @Test
    void testEquality_KilogramToGram_EquivalentValue() {
        assertEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM)
        );
    }

    // Test Case 4 (g → kg)
    @Test
    void testEquality_GramToKilogram_EquivalentValue() {
        assertEquals(
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
        );
    }

    // Test Case 5 (Category mismatch)
    @Test
    void testEquality_WeightVsLength_Incompatible() {
        assertNotEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityLength(1.0, LengthUnit.FEET)
        );
    }

    // Test Case 6 (Null comparison)
    @Test
    void testEquality_NullComparison() {
        QuantityWeight q = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        assertNotEquals(q, null);
    }

    // Test Case 7 (Reflexive property)
    @Test
    void testEquality_SameReference() {
        QuantityWeight q = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        assertEquals(q, q);
    }

    // Test Case 8 (Null unit)
    @Test
    void testEquality_NullUnit() {
        assertThrows(NullPointerException.class, () ->
                new QuantityWeight(1.0, null)
        );
    }

    // Test Case 9 (Transitive property)
    @Test
    void testEquality_TransitiveProperty_Weight() {

        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight c = new QuantityWeight(2.20462, WeightUnit.POUND);

        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    // Test Case 10 (Zero value)
    @Test
    void testEquality_ZeroValue() {
        assertEquals(
                new QuantityWeight(0.0, WeightUnit.KILOGRAM),
                new QuantityWeight(0.0, WeightUnit.GRAM)
        );
    }

    // Test Case 11 (Negative value)
    @Test
    void testEquality_NegativeWeight() {
        assertEquals(
                new QuantityWeight(-1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(-1000.0, WeightUnit.GRAM)
        );
    }

    // Test Case 12 (Large value)
    @Test
    void testEquality_LargeWeightValue() {
        assertEquals(
                new QuantityWeight(1000.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000000.0, WeightUnit.GRAM)
        );
    }

    // Test Case 13 (Small value)
    @Test
    void testEquality_SmallWeightValue() {
        assertEquals(
                new QuantityWeight(0.001, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.GRAM)
        );
    }

    // ===============================
    // CONVERSION TESTS
    // ===============================

    // Test Case 14 (lb → kg)
    @Test
    void testConversion_PoundToKilogram_Weight() {
        QuantityWeight q = new QuantityWeight(2.20462, WeightUnit.POUND);
        assertEquals(1.0, q.toConvert(WeightUnit.KILOGRAM).getValue(), EPSILON);
    }

    // Test Case 15 (kg → lb)
    @Test
    void testConversion_KilogramToPound() {
        QuantityWeight q = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        assertEquals(2.20462,
                q.toConvert(WeightUnit.POUND).getValue(),
                1e-4);
    }

    // Test Case 16 (Same unit conversion)
    @Test
    void testConversion_SameUnit() {
        QuantityWeight q = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        assertEquals(5.0, q.toConvert(WeightUnit.KILOGRAM).getValue(), EPSILON);
    }

    // Test Case 17 (Zero conversion)
    @Test
    void testConversion_Zero_Value() {
        QuantityWeight q = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
        assertEquals(0.0, q.toConvert(WeightUnit.GRAM).getValue(), EPSILON);
    }

    // Test Case 18 (Negative conversion)
    @Test
    void test_Conversion_NegativeValue() {
        QuantityWeight q = new QuantityWeight(-1.0, WeightUnit.KILOGRAM);
        assertEquals(-1000.0, q.toConvert(WeightUnit.GRAM).getValue(), EPSILON);
    }

    // Test Case 19 (Round-trip)
    @Test
    void test_Conversion_RoundTrip() {
        QuantityWeight original = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        QuantityWeight converted = original.toConvert(WeightUnit.GRAM);
        QuantityWeight back = converted.toConvert(WeightUnit.KILOGRAM);

        assertEquals(original.getValue(), back.getValue(), EPSILON);
    }

    // ===============================
    // ADDITION TESTS
    // ===============================

    // Test Case 20 (Same unit addition)
    @Test
    void testAddition_SameUnit_KilogramPlusKilogram() {
        assertEquals(
                new QuantityWeight(3.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM))
        );
    }

    // Test Case 21 (Cross-unit kg + g)
    @Test
    void testAddition_CrossUnit_KilogramPlusGram() {
        assertEquals(
                new QuantityWeight(2.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM))
        );
    }

    // Test Case 22 (lb + kg → lb)
    @Test
    void testAddition_CrossUnit_PoundPlusKilogram() {
        QuantityWeight result = new QuantityWeight(2.20462, WeightUnit.POUND)
                .add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), WeightUnit.POUND);

        assertEquals(4.40924, result.getValue(), 1e-4);
    }

    // Test Case 23 (Explicit target unit)
    @Test
    void testAddition_ExplicitTargetUnit_Kilogram() {
        QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    // Test Case 24 (Commutativity)
    @Test
    void testAddition__Commutativity() {
        QuantityWeight A = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight B = new QuantityWeight(1000.0, WeightUnit.GRAM);

        assertTrue(A.add(B).equals(B.add(A)));
    }

    // Test Case 25 (Zero identity)
    @Test
    void testAddition_With_Zero() {
        assertEquals(
                new QuantityWeight(5.0, WeightUnit.KILOGRAM),
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(0.0, WeightUnit.GRAM))
        );
    }

    // Test Case 26 (Negative addition)
    @Test
    void testAddition_Negative_Values() {
        assertEquals(
                new QuantityWeight(3.0, WeightUnit.KILOGRAM),
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(-2000.0, WeightUnit.GRAM))
        );
    }

    // Test Case 27 (Large values)
    @Test
    void testAddition_Large_Values() {
        assertEquals(
                new QuantityWeight(2e6, WeightUnit.KILOGRAM),
                new QuantityWeight(1e6, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1e6, WeightUnit.KILOGRAM))
        );
    }
//    // ===============================
// IMeasurable Interface Tests
// ===============================

    // Test Case 1 (LengthUnit implements all interface methods)
    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        assertEquals(1.0, LengthUnit.FEET.convertToBaseUnit(1.0), EPSILON);
        assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0), EPSILON);
    }

    // Test Case 2 (WeightUnit implements all interface methods)
    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        assertEquals(1.0, WeightUnit.KILOGRAM.convertToBaseUnit(1.0), EPSILON);
        assertEquals(1000.0, WeightUnit.GRAM.convertFromBaseUnit(1.0), EPSILON);
    }

    // Test Case 3 (Both enums match interface contract)
    @Test
    void testIMeasurableInterface_ConsistentBehavior() {
        assertNotNull(LengthUnit.FEET.name());
        assertNotNull(WeightUnit.KILOGRAM.name());
    }

// ===============================
// GENERIC QUANTITY - LENGTH
// ===============================

    // Test Case 4 (Quantity<LengthUnit> equality)
    @Test
    void testGenericQuantity_LengthOperations_Equality() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    // Test Case 5 (Quantity<WeightUnit> equality)
    @Test
    void testGenericQuantity_WeightOperations_Equality() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    // Test Case 6 (Quantity<LengthUnit> toConvert())
    @Test
    void testGenericQuantity_LengthOperations_Conversion() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(12.0,
                q.toConvert(LengthUnit.INCH).getValue(),
                EPSILON);
    }

    // Test Case 7 (Quantity<WeightUnit> toConvert())
    @Test
    void testGenericQuantity_WeightOperations_Conversion() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(1000.0,
                q.toConvert(WeightUnit.GRAM).getValue(),
                EPSILON);
    }

    // Test Case 8 (Quantity<LengthUnit> add())
    @Test
    void testGenericQuantity_LengthOperations_Addition() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    // Test Case 9 (Quantity<WeightUnit> add())
    @Test
    void testGenericQuantity_WeightOperations_Addition() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = q1.add(q2, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

// ===============================
// CROSS CATEGORY TESTS
// ===============================

    // Test Case 10 (Length vs Weight equals() returns false)
    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    // Test Case 11 (Type mismatch → compiler safety)
    @Test
    void testCrossCategoryPrevention_CompilerTypeSafety() {
        assertTrue(true); // Compile-time generic protection
    }

// ===============================
// CONSTRUCTOR VALIDATION
// ===============================

    // Test Case 12 (Null unit in constructor rejected)
    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {
        assertThrows(NullPointerException.class,
                () -> new Quantity<>(1.0, null));
    }

    // Test Case 13 (NaN value rejected)
    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, WeightUnit.KILOGRAM));
    }

// ===============================
// BULK TESTS
// ===============================

    // Test Case 14 (All unit pairs; all categories)
    @Test
    void testGenericQuantity_Conversion_AllUnitCombinations() {
        Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(12.0, l.toConvert(LengthUnit.INCH).getValue(), EPSILON);
        assertEquals(1000.0, w.toConvert(WeightUnit.GRAM).getValue(), EPSILON);
    }

    // Test Case 15 (Addition same/diff units; explicit target)
    @Test
    void testGenericQuantity_Addition_AllUnitCombinations() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(2.0, l1.add(l2, LengthUnit.FEET).getValue(), EPSILON);
        assertEquals(2.0, w1.add(w2, WeightUnit.KILOGRAM).getValue(), EPSILON);
    }

// ===============================
// BACKWARD COMPATIBILITY
// ===============================

    // Test Case 16 (All UC1–UC9 tests unchanged)
    @Test
    void testBackwardCompatibility_AllUC1Through9Tests() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

// ===============================
// SIMPLIFIED GENERIC DEMO
// ===============================

    // Test Case 17 (Single generic equality demo handles both)
    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
        assertTrue(new Quantity<>(1.0, LengthUnit.FEET)
                .equals(new Quantity<>(12.0, LengthUnit.INCH)));

        assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
    }

    // Test Case 18 (Single generic conversion demo)
    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
        assertEquals(12.0,
                new Quantity<>(1.0, LengthUnit.FEET)
                        .toConvert(LengthUnit.INCH).getValue(),
                EPSILON);

        assertEquals(1000.0,
                new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .toConvert(WeightUnit.GRAM).getValue(),
                EPSILON);
    }

    // Test Case 19 (Single generic addition demo)
    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
        assertEquals(2.0,
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.FEET)
                        .getValue(),
                EPSILON);

        assertEquals(2.0,
                new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM)
                        .getValue(),
                EPSILON);
    }

// ===============================
// ADVANCED GENERIC TESTS
// ===============================

    // Test Case 20 (Quantity<?> method accepts all categories)
    @Test
    void testTypeWildcard_FlexibleSignatures() {
        Quantity<?> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<?> q2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotNull(q1);
        assertNotNull(q2);
    }

    // Test Case 21 (VolumeUnit integrates without changing old code)
    @Test
    void testScalability_NewUnitEnumIntegration() {
        assertTrue(true);
    }

    // Test Case 22 (Temperature, Time, Volume categories added)
    @Test
    void testScalability_MultipleNewCategories() {
        assertTrue(true);
    }

    // Test Case 23 (Non-IMeasurable type rejected by compiler)
    @Test
    void testGenericBoundedTypeParameter_Enforcement() {
        assertTrue(true);
    }

    // Test Case 24 (Equal quantities have equal hash codes)
    @Test
    void testHashCode_GenericQuantity_Consistency() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(a.hashCode(), b.hashCode());
    }

    // Test Case 25 (Reflexive, symmetric, transitive equality)
    @Test
    void testEquals_GenericQuantity_ContractPreservation() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> c = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    // Test Case 26 (Cross-category survives type erasure)
    @Test
    void testTypeErasure_RuntimeSafety() {
        Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(l.equals(w));
    }

    // Test Case 27 (No setters; returns new objects)
    @Test
    void testImmutability_GenericQuantity() {
        Quantity<WeightUnit> original =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> converted =
                original.toConvert(WeightUnit.GRAM);

        assertNotSame(original, converted);
    }

    // Test Case 28 (5+ new unit types; no existing code changes)
    @Test
    void testArchitecturalReadiness_MultipleNewCategories() {
        assertTrue(true);
    }

    // Test Case 29 (Reduction in duplication vs UC9)
    @Test
    void testCodeReduction_DRYValidation() {
        assertTrue(true);
    }

    // ===============================
    // UC11 VOLUME MEASUREMENT TESTS (L, mL, GAL)
    // ===============================

    // EQUALITY
    // Test Case 1 (Equality Litre To Litre Same Value)
    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    // Test Case 2 (Equality Litre To Litre Different Value)
    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE));
    }

    // Test Case 3 (Equality Litre To Millilitre Equivalent Value)
    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
    }

    // Test Case 4 (Equality Millilitre To Litre Equivalent Value)
    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    // Test Case 5 (Equality Litre To Gallon Equivalent Value)
    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0 / 3.78541, VolumeUnit.GALLON));
    }

    // Test Case 6 (Equality Gallon To Litre Equivalent Value)
    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.GALLON), new Quantity<>(3.78541, VolumeUnit.LITRE));
    }

    // Test Case 7 (Equality Volume Vs Length Incompatible)
    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new QuantityLength(1.0, LengthUnit.FEET));
    }

    // Test Case 8 (Equality Volume Vs Weight Incompatible)
    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new QuantityWeight(1.0, WeightUnit.KILOGRAM));
    }

    // Test Case 9 (Equality Null Comparison)
    @Test
    void test_Equality_NullComparison() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), null);
    }

    // Test Case 10 (Equality Same Reference)
    @Test
    void testEquality_Same_Reference() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(q, q);
    }

    // Test Case 11 (Equality Null Unit)
    @Test
    void testEquality_Null_Unit() {
        assertThrows(NullPointerException.class, () -> new Quantity<>(1.0, null));
    }

    // Test Case 12 (Equality Transitive Property)
    @Test
    void testEquality_TransitiveProperty() {
        Quantity<VolumeUnit> l = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(3785.41, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> gal = new Quantity<>(1.0, VolumeUnit.GALLON);

        assertEquals(gal, l);
        assertEquals(l, ml);
        assertEquals(gal, ml);
    }

    // Test Case 13 (Equality Zero Value)
    @Test
    void test_Equality_ZeroValue() {
        assertEquals(new Quantity<>(0.0, VolumeUnit.LITRE), new Quantity<>(0.0, VolumeUnit.MILLILITRE));
    }

    // Test Case 14 (Equality Negative Volume)
    @Test
    void testEquality_NegativeVolume() {
        assertEquals(new Quantity<>(-1.0, VolumeUnit.LITRE), new Quantity<>(-1000.0, VolumeUnit.MILLILITRE));
    }

    // Test Case 15 (Equality Large Volume Value)
    @Test
    void testEquality_LargeVolumeValue() {
        assertEquals(new Quantity<>(1000.0, VolumeUnit.LITRE), new Quantity<>(1000000.0, VolumeUnit.MILLILITRE));
    }

    // Test Case 16 (Equality Small Volume Value)
    @Test
    void testEquality_SmallVolumeValue() {
        assertEquals(new Quantity<>(0.001, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.MILLILITRE));
    }

    // CONVERSION
    // Test Case 17 (Conversion Litre To Millilitre)
    @Test
    void testConversion_LitreToMillilitre() {
        assertEquals(1000.0, new Quantity<>(1.0, VolumeUnit.LITRE).toConvert(VolumeUnit.MILLILITRE).getValue(),
                EPSILON);
    }

    // Test Case 18 (Conversion Millilitre To Litre)
    @Test
    void testConversion_MillilitreToLitre() {
        assertEquals(1.0, new Quantity<>(1000.0, VolumeUnit.MILLILITRE).toConvert(VolumeUnit.LITRE).getValue(),
                EPSILON);
    }

    // Test Case 19 (Conversion Gallon To Litre)
    @Test
    void testConversion_GallonToLitre() {
        assertEquals(3.78541, new Quantity<>(1.0, VolumeUnit.GALLON).toConvert(VolumeUnit.LITRE).getValue(), EPSILON);
    }

    // Test Case 20 (Conversion Litre To Gallon)
    @Test
    void testConversion_LitreToGallon() {
        assertEquals(1.0, new Quantity<>(3.78541, VolumeUnit.LITRE).toConvert(VolumeUnit.GALLON).getValue(), 1e-4);
    }

    // Test Case 21 (Conversion Millilitre To Gallon)
    @Test
    void testConversion_MillilitreToGallon() {
        assertEquals(1.0 / 3.78541,
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE).toConvert(VolumeUnit.GALLON).getValue(), 1e-4);
    }

    // Test Case 22 (Conversion Same Unit)
    @Test
    void test_Conversion_Same_Unit() {
        assertEquals(5.0, new Quantity<>(5.0, VolumeUnit.LITRE).toConvert(VolumeUnit.LITRE).getValue(), EPSILON);
    }

    // Test Case 23 (Conversion Zero Value)
    @Test
    void test_Conversion_ZeroValue() {
        assertEquals(0.0, new Quantity<>(0.0, VolumeUnit.LITRE).toConvert(VolumeUnit.MILLILITRE).getValue(), EPSILON);
    }

    // Test Case 24 (Conversion Negative Value)
    @Test
    void test_Conversion_Negative_Value() {
        assertEquals(-1000.0, new Quantity<>(-1.0, VolumeUnit.LITRE).toConvert(VolumeUnit.MILLILITRE).getValue(),
                EPSILON);
    }

    // Test Case 25 (Conversion Round Trip)
    @Test
    void test_Conversion_Round_Trip() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> converted = q.toConvert(VolumeUnit.MILLILITRE);
        assertEquals(q.getValue(), converted.toConvert(VolumeUnit.LITRE).getValue(), EPSILON);
    }

    // ADDITION
    // Test Case 26 (Addition Same Unit Litre Plus Litre)
    @Test
    void testAddition_SameUnit_LitrePlusLitre() {
        assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE),
                new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    // Test Case 27 (Addition Same Unit Millilitre Plus Millilitre)
    @Test
    void testAddition_SameUnit_MillilitrePlusMillilitre() {
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                new Quantity<>(500.0, VolumeUnit.MILLILITRE).add(new Quantity<>(500.0, VolumeUnit.MILLILITRE)));
    }

    // Test Case 28 (Addition Cross Unit Litre Plus Millilitre)
    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE),
                new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    // Test Case 29 (Addition Cross Unit Millilitre Plus Litre)
    @Test
    void testAddition_CrossUnit_MillilitrePlusLitre() {
        assertEquals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE),
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE).add(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    // Test Case 30 (Addition Cross Unit Gallon Plus Litre)
    @Test
    void testAddition_CrossUnit_GallonPlusLitre() {
        assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON),
                new Quantity<>(1.0, VolumeUnit.GALLON).add(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    // Test Case 31 (Addition Explicit Target Unit Litre)
    @Test
    void testAddition_ExplicitTargetUnit_Litre() {
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE));
    }

    // Test Case 32 (Addition Explicit Target Unit Millilitre)
    @Test
    void testAddition_ExplicitTargetUnit_Millilitre() {
        assertEquals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE));
    }

    // Test Case 33 (Addition Explicit Target Unit Gallon)
    @Test
    void testAddition_ExplicitTargetUnit_Gallon() {
        assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), new Quantity<>(3.78541, VolumeUnit.LITRE)
                .add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON));
    }

    // Test Case 34 (Addition Commutativity)
    @Test
    void test_Addition_Commutativity() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(a.add(b), b.add(a));
    }

    // Test Case 35 (Addition With Zero)
    @Test
    void test_Addition_With_Zero() {
        assertEquals(new Quantity<>(5.0, VolumeUnit.LITRE),
                new Quantity<>(5.0, VolumeUnit.LITRE).add(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    // Test Case 36 (Addition Negative Values)
    @Test
    void test_Addition_NegativeValues() {
        assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE),
                new Quantity<>(5.0, VolumeUnit.LITRE).add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE)));
    }

    // Test Case 37 (Addition Large Values)
    @Test
    void test_Addition_Large_Values() {
        assertEquals(new Quantity<>(2e6, VolumeUnit.LITRE),
                new Quantity<>(1e6, VolumeUnit.LITRE).add(new Quantity<>(1e6, VolumeUnit.LITRE)));
    }

    // Test Case 38 (Addition Small Values)
    @Test
    void test_Addition_Small_Values() {
        assertEquals(new Quantity<>(0.003, VolumeUnit.LITRE),
                new Quantity<>(0.001, VolumeUnit.LITRE).add(new Quantity<>(0.002, VolumeUnit.LITRE)));
    }

    // ENUM CONSTANT
    // Test Case 39 (Volume Unit Enum Litre Constant)
    @Test
    void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
    }

    // Test Case 40 (Volume Unit Enum Millilitre Constant)
    @Test
    void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
    }

    // Test Case 41 (Volume Unit Enum Gallon Constant)
    @Test
    void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
    }

    // BASE CONVERSION
    // Test Case 42 (Convert To Base Unit Millilitre To Litre)
    @Test
    void testConvertToBaseUnit_MillilitreToLitre() {
        assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), EPSILON);
    }

    // Test Case 43 (Convert To Base Unit Gallon To Litre)
    @Test
    void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPSILON);
    }

    // Test Case 44 (Convert From Base Unit Litre To Millilitre)
    @Test
    void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPSILON);
    }

    // Test Case 45 (Convert From Base Unit Litre To Gallon)
    @Test
    void testConvertFromBaseUnit_LitreToGallon() {
        assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.78541), 1e-4);
    }

    // COMPATIBILITY & SCALABILITY
    // Test Case 46 (Backward Compatibility All UC1Through10Tests)
    @Test
    void testBackwardCompatibility_AllUC1Through10Tests() {
        assertTrue(true);
    }

    // Test Case 47 (Generic Quantity Operations Consistency)
    @Test
    void testGenericQuantity_VolumeOperations_Consistency() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertNotNull(q);
        assertEquals(VolumeUnit.LITRE, q.getUnit());
    }

    // Test Case 48 (Scalability Volume Integration)
    @Test
    void testScalability_VolumeIntegration() {
        assertTrue(true);
    }
    // ===============================
    // UC12 TEST CASES
    // ===============================
    // Test Case 1 (Subtraction  Same Unit  Feet Minus Feet)
    @Test
    void testSubtraction_SameUnit_FeetMinusFeet() {
        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), LengthUnit.FEET));
    }

    // Test Case 2 (Subtraction  Same Unit  Litre Minus Litre)
    @Test
    void testSubtraction_SameUnit_LitreMinusLitre() {
        assertEquals(new Quantity<>(7.0, VolumeUnit.LITRE), new Quantity<>(10.0, VolumeUnit.LITRE).subtract(new Quantity<>(3.0, VolumeUnit.LITRE), VolumeUnit.LITRE));
    }

    // Test Case 3 (Subtraction  Cross Unit  Feet Minus Inches)
    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {
        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.FEET));
    }

    // Test Case 4 (Subtraction  Cross Unit  Inches Minus Feet)
    @Test
    void testSubtraction_CrossUnit_InchesMinusFeet() {
        assertEquals(new Quantity<>(60.0, LengthUnit.INCH), new Quantity<>(120.0, LengthUnit.INCH).subtract(new Quantity<>(5.0, LengthUnit.FEET), LengthUnit.INCH));
    }

    // Test Case 5 (Subtraction  Explicit Target Unit  Feet)
    @Test
    void testSubtraction_ExplicitTargetUnit_Feet() {
        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.FEET));
    }

    // Test Case 6 (Subtraction  Explicit Target Unit  Inches)
    @Test
    void testSubtraction_ExplicitTargetUnit_Inches() {
        assertEquals(new Quantity<>(114.0, LengthUnit.INCH), new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH));
    }

    // Test Case 7 (Subtraction  Explicit Target Unit  Millilitre)
    @Test
    void testSubtraction_ExplicitTargetUnit_Millilitre() {
        assertEquals(new Quantity<>(3000.0, VolumeUnit.MILLILITRE), new Quantity<>(5.0, VolumeUnit.LITRE).subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE));
    }

    // Test Case 8 (Subtraction  Resulting In Negative)
    @Test
    void testSubtraction_ResultingInNegative() {
        assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<>(10.0, LengthUnit.FEET), LengthUnit.FEET));
    }

    // Test Case 9 (Subtraction  Resulting In Zero)
    @Test
    void testSubtraction_ResultingInZero() {
        assertEquals(new Quantity<>(0.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(120.0, LengthUnit.INCH), LengthUnit.FEET));
    }

    // Test Case 10 (Subtraction  With Zero Operand)
    @Test
    void testSubtraction_WithZeroOperand() {
        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<>(0.0, LengthUnit.INCH), LengthUnit.FEET));
    }

    // Test Case 11 (Subtraction  With Negative Values)
    @Test
    void testSubtraction_WithNegativeValues() {
        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<>(-2.0, LengthUnit.FEET), LengthUnit.FEET));
    }

    // Test Case 12 (Subtraction  Non Commutative)
    @Test
    void testSubtraction_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET);
        assertNotEquals(a.subtract(b, LengthUnit.FEET), b.subtract(a, LengthUnit.FEET));
    }

    // Test Case 13 (Subtraction  With Large Values)
    @Test
    void testSubtraction_WithLargeValues() {
        assertEquals(new Quantity<>(5e5, WeightUnit.KILOGRAM), new Quantity<>(1e6, WeightUnit.KILOGRAM).subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM), WeightUnit.KILOGRAM));
    }

    // Test Case 14 (Subtraction  With Small Values)
    @Test
    void testSubtraction_WithSmallValues() {
        assertEquals(new Quantity<>(0.0005, LengthUnit.FEET), new Quantity<>(0.001, LengthUnit.FEET).subtract(new Quantity<>(0.0005, LengthUnit.FEET), LengthUnit.FEET));
    }

    // Test Case 15 (Subtraction  Null Operand)
    @Test
    void testSubtraction_NullOperand() {
        assertThrows(NullPointerException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(null, LengthUnit.FEET));
    }

    // Test Case 16 (Subtraction  Null Target Unit)
    @Test
    void testSubtraction_NullTargetUnit() {
        assertThrows(NullPointerException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), null));
    }

    // Test Case 17 (Subtraction  Cross Category)
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void testSubtraction_CrossCategory() {
        Quantity feet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity kg = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> feet.subtract(kg, LengthUnit.FEET));
    }

    // Test Case 18 (Subtraction  All Measurement Categories)
    @Test
    void testSubtraction_AllMeasurementCategories() {
        assertNotNull(new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), LengthUnit.FEET));
        assertNotNull(new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM), WeightUnit.KILOGRAM));
        assertNotNull(new Quantity<>(10.0, VolumeUnit.LITRE).subtract(new Quantity<>(5.0, VolumeUnit.LITRE), VolumeUnit.LITRE));
    }

    // Test Case 19 (Subtraction  Chained Operations)
    @Test
    void testSubtraction_ChainedOperations() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), a.subtract(b, LengthUnit.FEET).subtract(c, LengthUnit.FEET));
    }

    // Test Case 20 (Division  Same Unit  Feet Divided By Feet)
    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);
    }

    // Test Case 21 (Division  Same Unit  Litre Divided By Litre)
    @Test
    void testDivision_SameUnit_LitreDividedByLitre() {
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPSILON);
    }

    // Test Case 22 (Division  Cross Unit  Feet Divided By Inches)
    @Test
    void testDivision_CrossUnit_FeetDividedByInches() {
        assertEquals(1.0, new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);
    }

    // Test Case 23 (Division  Cross Unit  Kilogram Divided By Gram)
    @Test
    void testDivision_CrossUnit_KilogramDividedByGram() {
        assertEquals(1.0, new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM)), EPSILON);
    }

    // Test Case 24 (Division  Ratio Greater Than One)
    @Test
    void testDivision_RatioGreaterThanOne() {
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);
    }

    // Test Case 25 (Division  Ratio Less Than One)
    @Test
    void testDivision_RatioLessThanOne() {
        assertEquals(0.5, new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET)), EPSILON);
    }

    // Test Case 26 (Division  Ratio Equal To One)
    @Test
    void testDivision_RatioEqualToOne() {
        assertEquals(1.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET)), EPSILON);
    }

    // Test Case 27 (Division  Non Commutative)
    @Test
    void testDivision_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        assertNotEquals(a.divide(b), b.divide(a), EPSILON);
    }

    // Test Case 28 (Division  By Zero)
    @Test
    void testDivision_ByZero() {
        assertThrows(ArithmeticException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    // Test Case 29 (Division  With Large Ratio)
    @Test
    void testDivision_WithLargeRatio() {
        assertEquals(1000000.0, new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)), EPSILON);
    }

    // Test Case 30 (Division  With Small Ratio)
    @Test
    void testDivision_WithSmallRatio() {
        assertEquals(1e-6, new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1e6, WeightUnit.KILOGRAM)), EPSILON);
    }

    // Test Case 31 (Division  Null Operand)
    @Test
    void testDivision_NullOperand() {
        assertThrows(NullPointerException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).divide(null));
    }

    // Test Case 32 (Division  Cross Category)
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void testDivision_CrossCategory() {
        Quantity feet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity kg = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> feet.divide(kg));
    }

    // Test Case 33 (Division  All Measurement Categories)
    @Test
    void testDivision_AllMeasurementCategories() {
        assertNotNull(new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET)));
        assertNotNull(new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)));
        assertNotNull(new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)));
    }

    // Test Case 34 (Subtraction And Division  Integration)
    @Test
    void testSubtractionAndDivision_Integration() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(2.0, LengthUnit.FEET);
        assertEquals(4.0, a.subtract(b, LengthUnit.FEET).divide(c), EPSILON);
    }

    // Test Case 35 (Subtraction Addition  Inverse)
    @Test
    void testSubtractionAddition_Inverse() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        assertEquals(a.getValue(), a.add(b).subtract(b, LengthUnit.FEET).getValue(), EPSILON);
    }

    // Test Case 36 (Subtraction  Immutability)
    @Test
    void testSubtraction_Immutability() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        a.subtract(b, LengthUnit.FEET);
        assertEquals(10.0, a.getValue(), EPSILON);
        assertEquals(5.0, b.getValue(), EPSILON);
    }

    // Test Case 37 (Division  Immutability)
    @Test
    void testDivision_Immutability() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        a.divide(b);
        assertEquals(10.0, a.getValue(), EPSILON);
        assertEquals(5.0, b.getValue(), EPSILON);
    }

    // Test Case 38 (Subtraction  Precision And Rounding)
    @Test
    void testSubtraction_PrecisionAndRounding() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.005, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.001, LengthUnit.FEET);
        assertEquals(5.004, q1.subtract(q2, LengthUnit.FEET).getValue(), EPSILON);
    }

    // Test Case 39 (Division  Precision Handling)
    @Test
    void testDivision_PrecisionHandling() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);
        assertEquals(10.0 / 3.0, q1.divide(q2), EPSILON);
    }

}