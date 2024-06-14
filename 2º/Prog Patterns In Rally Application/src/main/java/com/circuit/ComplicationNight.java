package com.circuit;

import com.utils.Utils;

/**
 * Night complication.
 */
public class ComplicationNight extends CircuitDecorator {

	public ComplicationNight(Circuit circuit) {
		super(circuit);
	}

	@Override
	public double getCalculatedComplexity() {
		return Utils.round(circuit.getCalculatedComplexity() * 1.2);
	}

	@Override
	public double getCalculatedDistance() {
		return Utils.round(circuit.getCalculatedDistance() * 0.8);
	}

	@Override
	public String getConditions() {
		return circuit.getConditions() + " Nocturno";
	}
}
