package com.circuit;

import com.utils.Utils;

/**
 * Cold complication.
 */
public class ComplicationCold extends CircuitDecorator {

	/**
	 * Instantiates a new complication cold.
	 *
	 * @param circuit the circuit
	 */
	public ComplicationCold(Circuit circuit) {
		super(circuit);
	}

	@Override
	public double getCalculatedComplexity() {
		return Utils.round(circuit.getCalculatedComplexity() * 1.1);
	}

	@Override
	public double getCalculatedDistance() {
		return Utils.round(circuit.getCalculatedDistance() * 0.9);
	}

	@Override
	public String getConditions() {
		return circuit.getConditions() + " Frio";
	}
}
