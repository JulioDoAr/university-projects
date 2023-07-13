package com.circuit;

import com.utils.Messages;
import com.utils.Utils;

/**
 * The CircuitDecorator. Part of the Decorator pattern. To be a canonical
 * Decorator pattern, this class implements Circuit, but to evade code
 * duplication, we could do inherit from CircuitData.
 */
public abstract class CircuitDecorator implements Circuit {

	/** The circuit. */
	protected Circuit circuit;

	/**
	 * Instantiates a new circuit decorator.
	 *
	 * @param circuit the circuit
	 */
	public CircuitDecorator(Circuit circuit) {
		this.circuit = circuit;
	}

	@Override
	public double getCalculatedComplexity() {
		return Utils.round(circuit.getCalculatedComplexity());
	}

	@Override
	public double getCalculatedDistance() {
		return Utils.round(circuit.getCalculatedDistance());
	}

	@Override
	public String toString() {

		String text = String.format(Messages.CIRCUIT_SERIALIZATION, circuit.getName(), getConditions(),
				circuit.getComplexity().name(), circuit.getComplexity().getValue(), getCalculatedComplexity(),
				circuit.getDistance().name(), circuit.getDistance().getValue(), getCalculatedDistance());

		return text;
	}

	@Override
	public String getName() {
		return circuit.getName();
	}

	@Override
	public Complexity getComplexity() {
		return circuit.getComplexity();
	}

	@Override
	public Distance getDistance() {
		return circuit.getDistance();
	}

	@Override
	public String getConditions() {
		return "";
	}

	/**
	 * Gets the circuit.
	 *
	 * @return the circuit
	 */
	public Circuit getCircuit() {
		return circuit;
	}

}
