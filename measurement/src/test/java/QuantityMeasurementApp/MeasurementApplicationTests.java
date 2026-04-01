
package QuantityMeasurementApp;


import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class  MeasurementApplicationTests {

	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_InchToFeet_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertNotEquals(q1, q2);
	}

	@Test
	void testEquality_InchToInch_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCH);
		assertNotEquals(q1, q2);
	}

	@Test
	void testEquality_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	@Test
	void testEquality_NullUnit() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertNotEquals(q1, null);
	}

	@Test
	void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertEquals(q1, q1);
	}

	@Test
	void testEquality_NullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(null));
	}
	// ===============================
// UC4 TEST CASES (17 Total)
// ===============================

	@Test
	void testEquality_YardToYard_SameValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToYard_DifferentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
				new QuantityLength(2.0, LengthUnit.YARDS);

		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
				new QuantityLength(3.0, LengthUnit.FEET);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_FeetToYard_EquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength q2 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
				new QuantityLength(36.0, LengthUnit.INCH);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_InchesToYard_EquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(36.0, LengthUnit.INCH);

		QuantityLength q2 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
				new QuantityLength(2.0, LengthUnit.FEET);

		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_CentimetersToInches_EquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.CENTIMETERS);

		QuantityLength q2 =
				new QuantityLength(0.393701, LengthUnit.INCH);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_CentimetersToFeet_NonEquivalentValue() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.CENTIMETERS);

		QuantityLength q2 =
				new QuantityLength(1.0, LengthUnit.FEET);

		assertFalse(q1.equals(q2));
	}

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {

		QuantityLength yard =
				new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength feet =
				new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength inch =
				new QuantityLength(36.0, LengthUnit.INCH);

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

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

		assertTrue(q1.equals(q1));
	}

	@Test
	void testEquality_YardNullComparison() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.YARDS);

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

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.CENTIMETERS);

		assertTrue(q1.equals(q1));
	}

	@Test
	void testEquality_CentimetersNullComparison() {

		QuantityLength q1 =
				new QuantityLength(1.0, LengthUnit.CENTIMETERS);

		assertFalse(q1.equals(null));
	}

	@Test
	void testEquality_AllUnits_ComplexScenario() {

		QuantityLength yard =
				new QuantityLength(2.0, LengthUnit.YARDS);

		QuantityLength feet =
				new QuantityLength(6.0, LengthUnit.FEET);

		QuantityLength inch =
				new QuantityLength(72.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}
}

