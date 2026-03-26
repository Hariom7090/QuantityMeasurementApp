package QuantityMeasurementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeasurementApplication {

	static class Feet{
		private final double value;
		public Feet(double value){
			this.value = value;
		}
		public boolean equals(Object obj){
			if(this == obj) return  true;
			if(obj == null || getClass() != obj.getClass()){
				return  false;
			}
			Feet other = (Feet)obj;
			return Double . compare(this.value, other.value) ==0;
		}
	}

	public static void main(String[] args) {


		SpringApplication.run(MeasurementApplication.class, args);

		Feet f1 = new Feet(34.5);
		Feet f2 = new Feet(34.5);
		System.out.print("Answer: ");
		System.out.println(f1.equals(f2));
	}


}