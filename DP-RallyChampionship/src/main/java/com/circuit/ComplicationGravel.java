package com.circuit;

import com.utils.Utils;

/**
 * Gravel complication.
 */
public class ComplicationGravel extends CircuitDecorator {

	public ComplicationGravel(Circuit circuit) {
		super(circuit);
	}

	@Override
	public double getCalculatedComplexity() {
		return Utils.round(circuit.getCalculatedComplexity() * 1.05);
	}

	@Override
	public double getCalculatedDistance() {
		return Utils.round(circuit.getCalculatedDistance() * 0.95);
	}

	@Override
	public String getConditions() {
		return circuit.getConditions() + " Gravilla";
	}
}
