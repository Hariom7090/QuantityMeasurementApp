package QuantityMeasurementApp;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementApplicationTests {

	private final double EPSILON = 1e-6;

	@Test
	void testEquality_YardToYard_SameValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToYard_DifferentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.YARDS);
		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_FeetToYard_EquivalentValue() {

		QuantityLength q1 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_InchesToYard_EquivalentValue() {

		QuantityLength q1 = new QuantityLength(36.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_CentimetersToInches_EquivalentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_CentimetersToFeet_NonEquivalentValue() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {

		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}

	@Test
	void testEquality_YardWithNullUnit() {

		assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);

		});
	}

	@Test
	void testEquality_YardSameReference() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q1));
	}

	@Test
	void testEquality_YardNullComparison() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertFalse(q1.equals(null));
	}

	@Test
	void testEquality_CentimetersWithNullUnit() {

		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0, null);
		});
	}

	@Test
	void testEquality_CentimetersSameReference() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		assertTrue(q1.equals(q1));
	}

	@Test
	void testEquality_CentimetersNullComparison() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		assertFalse(q1.equals(null));
	}

	@Test
	void testEquality_AllUnits_ComplexScenario() {

		QuantityLength yard = new QuantityLength(2.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(72.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}



	@Test
	void testConversion_FeetToInches() {

		double result = QuantityLength.convert(1.0,LengthUnit.FEET, LengthUnit.INCH);
		assertEquals(12.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToFeet() {

		double result = QuantityLength.convert(24.0, LengthUnit.INCH, LengthUnit.FEET);
		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testConversion_YardsToFeet() {

		double result = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.FEET);
		assertEquals(3.0, result, EPSILON);
	}

	@Test
	void testConversion_FeetToYards() {

		double result = QuantityLength.convert(6.0, LengthUnit.FEET, LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testConversion_YardsToInches() {

		double result = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCH);
		assertEquals(36.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToYards() {

		double result = QuantityLength.convert(72.0, LengthUnit.INCH, LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testConversion_CentimetersToInches() {

		double result = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCH);
		assertEquals(1.0, result, 0.0001);
	}

	@Test
	void testConversion_InchesToCentimeters() {

		double result = QuantityLength.convert(1.0, LengthUnit.INCH, LengthUnit.CENTIMETERS);
		assertEquals(2.54, result, 0.0001);
	}

	@Test
	void testConversion_SameUnit() {

		double result = QuantityLength.convert(5.0, LengthUnit.FEET, LengthUnit.FEET);
		assertEquals(5.0, result, EPSILON);
	}

	@Test
	void testConversion_ZeroValue() {

		double result = QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH);
		assertEquals(0.0, result, EPSILON);
	}

	@Test
	void testConversion_NegativeValue() {

		double result = QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH);
		assertEquals(-12.0, result, EPSILON);
	}

	@Test
	void testConversion_InvalidUnit() {

		assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, null, LengthUnit.FEET));
	}

	// ===============================
// UC6 TEST CASES (12)
// ===============================

	@Test
	void testAddition_SameUnit_FeetPlusFeet() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

		QuantityLength result = q1.add(q2);
		assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {

		QuantityLength q1 = new QuantityLength(6.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(6.0, LengthUnit.INCH);

		QuantityLength result = q1.add(q2);
		assertEquals(new QuantityLength(12.0, LengthUnit.INCH), result);
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {

		QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

		QuantityLength result = feet.add(inch);
		assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {

		QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

		QuantityLength result = inch.add(feet);
		assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {

		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength result = yard.add(feet);
		assertEquals(new QuantityLength(2.0, LengthUnit.YARDS), result);
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {

		QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
		QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);

		QuantityLength result = cm.add(inch);
		assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS), result);
	}

	@Test
	void testAddition_Commutativity() {

		QuantityLength A = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength B = new QuantityLength(12.0, LengthUnit.INCH);

		QuantityLength result1 = A.add(B);
		QuantityLength result2 = B.add(A);

		assertTrue(result1.equals(result2));
	}

	@Test
	void testAddition_WithZero() {

		QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
		QuantityLength zero = new QuantityLength(0.0, LengthUnit.INCH);

		QuantityLength result = q1.add(zero);
		assertEquals(new QuantityLength(5.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_NegativeValues() {

		QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);

		QuantityLength result = q1.add(q2);
		assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_NullSecondOperand() {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.add(null));
	}

	@Test
	void testAddition_LargeValues() {

		QuantityLength q1 = new QuantityLength(1e6, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1e6, LengthUnit.FEET);

		QuantityLength result = q1.add(q2);
		assertEquals(new QuantityLength(2e6, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_SmallValues() {

		QuantityLength q1 = new QuantityLength(0.001, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(0.002, LengthUnit.FEET);

		QuantityLength result = q1.add(q2);
		assertEquals(new QuantityLength(0.003, LengthUnit.FEET), result);
	}
}