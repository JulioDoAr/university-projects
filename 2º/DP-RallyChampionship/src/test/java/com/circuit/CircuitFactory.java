package com.circuit;

/**
 * A factory for creating Circuit objects.
 */
public interface CircuitFactory {

	/**
	 * Australia.
	 *
	 * @return the circuit
	 */
	Circuit australia();

	/**
	 * Portugal.
	 *
	 * @return the circuit
	 */
	Circuit portugal();

	/**
	 * Cerdena.
	 *
	 * @return the circuit
	 */
	Circuit cerdena();

	/**
	 * Corcega.
	 *
	 * @return the circuit
	 */
	Circuit corcega();

	/**
	 * Finlandia.
	 *
	 * @return the circuit
	 */
	Circuit finlandia();

	/**
	 * Alemania.
	 *
	 * @return the circuit
	 */
	Circuit alemania();

	/**
	 * Chile.
	 *
	 * @return the circuit
	 */
	Circuit chile();
}
