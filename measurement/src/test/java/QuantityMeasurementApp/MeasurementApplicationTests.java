
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
}

