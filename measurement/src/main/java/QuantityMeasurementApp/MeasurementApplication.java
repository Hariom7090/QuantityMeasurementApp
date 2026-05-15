package QuantityMeasurementApp;


import com.app.quantitymeasurement.enumsImpl.LengthUnit;
import com.app.quantitymeasurement.model.QuantityLength;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeasurementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementApplication.class,args);

		double value1 = 1.0;
		LengthUnit unit1 = LengthUnit.FEET;

		double value2 = 12.0;
		LengthUnit unit2 = LengthUnit.INCH;

		QuantityLength quantityLength = new QuantityLength(value1,unit1);
		QuantityLength quantityLength1 = new QuantityLength(value2,unit2);


	}
}