package com.car.comparator;

import java.util.Comparator;

import com.car.Car;

/**
 * Compare cars by the remaining fuel and name in ascendant order.
 */
public class CarComparatorByRemainingFuelName implements Comparator<Car> {

	@Override
	public int compare(Car arg0, Car arg1) {
		int result = Double.compare(arg0.getActualFuel(), arg1.getActualFuel());

		if (result == 0) {
			result = arg0.getName().compareTo(arg1.getName());
		}

		return result;
	}

}
