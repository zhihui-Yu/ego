package test;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		System.out.println(list.toString());
	}
}
class Car{
	private String wheelsName;
	private String carName;
/*	@Override
	public String toString() {
		return "car [wheelsName=" + wheelsName + ", carName=" + carName + "]";
	}*/
	public Car(String wheelsName, String carName) {
		super();
		this.wheelsName = wheelsName;
		this.carName = carName;
	}
	public Car() {
		super();
	}
	public String getWheelsName() {
		return wheelsName;
	}
	public void setWheelsName(String wheelsName) {
		this.wheelsName = wheelsName;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	
}