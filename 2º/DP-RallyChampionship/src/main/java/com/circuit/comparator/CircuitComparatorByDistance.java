package com.circuit.comparator;

import java.util.Comparator;

import com.circuit.Circuit;

/**
 * Compare two circuits by its calculated distance in ascendant order.
 */
public class CircuitComparatorByDistance implements Comparator<Circuit> {

	@Override
	public int compare(Circuit arg0, Circuit arg1) {
		int result = Double.compare(arg0.getCalculatedDistance(), arg1.getCalculatedDistance());
		if (result == 0)
			result = arg0.getName().compareTo(arg1.getName());
		return result;
	}

}
