package QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class MeasurementApplicationTests {


	@Test
	void testEquality_SameValue() {
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(12.2);
		MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(12.2);
		assertEquals(f1, f2);
	}

	@Test
	void testEquality_DifferentValue() {
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(12.2);
		MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(13.2);
		assertNotEquals(f1, f2);
	}

	@Test
	void testEquality_NullComparison() {
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(12.2);
		//  assertNotNull(f1);
		assertFalse(f1.equals(null));
	}

	@Test
	void testEquality_NonNumericInput() {
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(12.2);
		String name = "NR";
		assertFalse(f1.equals(name));
	}

	@Test
	void testEquality_SameReference() {
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(10.1);
		assertTrue(f1.equals(f1));
	}
}