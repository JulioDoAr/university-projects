package com.circuit;

/**
 * The Enum Complexity.
 */
public enum Complexity {

	/** Baja. */
	BAJA(1.0d),
	/** Media. */
	MEDIA(1.25d),
	/** Alta. */
	ALTA(1.5d);

	/** The value. */
	private final double value;

	/**
	 * Instantiates a new complexity.
	 *
	 * @param value the value
	 */
	private Complexity(double value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

}
