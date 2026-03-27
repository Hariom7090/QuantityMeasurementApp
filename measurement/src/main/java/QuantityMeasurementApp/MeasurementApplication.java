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
	static class Inches{
		private final double value;
		public Inches(double value){
			this.value = value;
		}
		public boolean equals(Object obj){
			if(this == obj) return  true;
			if(obj == null || getClass() != obj.getClass()){
				return  false;
			}
			Inches other = (Inches)obj;
			return Double . compare(this.value, other.value) ==0;
		}
	}

	public static void main(String[] args) {


		SpringApplication.run(MeasurementApplication.class, args);

		Feet f1 = new Feet(34.5);
		Feet f2 = new Feet(34.5);
		System.out.print(" Feet Answer: ");
		System.out.println(f1.equals(f2));
		Inches in1 = new Inches(34.5);
		Inches in2 = new Inches(34.5);
		System.out.print(" Inches Answer: ");
		System.out.println(in1.equals(in2));
	}


}