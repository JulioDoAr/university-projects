package com.circuit.comparator;

import java.util.Comparator;

import com.circuit.Circuit;

/**
 * Compare two circuits by its calculated complexity in descendant order.
 */
public class CircuitComparatorByComplexity implements Comparator<Circuit> {

	@Override
	public int compare(Circuit arg0, Circuit arg1) {
		int result = Double.compare(arg0.getCalculatedComplexity(), arg1.getCalculatedComplexity());
		if (result == 0)
			result = arg0.getName().compareTo(arg1.getName());
		return result;
	}

}
