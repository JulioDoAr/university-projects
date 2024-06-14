package com.circuit;

import com.utils.Utils;

/**
 * Wet complication.
 */
public class ComplicationWet extends CircuitDecorator {

	public ComplicationWet(Circuit circuit) {
		super(circuit);
	}

	@Override
	public double getCalculatedComplexity() {
		return Utils.round(circuit.getCalculatedComplexity() * 1.15);
	}

	@Override
	public double getCalculatedDistance() {
		return Utils.round(circuit.getCalculatedDistance() * 0.85);
	}

	@Override
	public String getConditions() {
		return circuit.getConditions() + " Mojado";
	}
}
