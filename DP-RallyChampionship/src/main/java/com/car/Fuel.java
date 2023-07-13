package com.car;

/**
 * The Enum Fuel.
 */
public enum Fuel {

	/** Escaso. */
	ESCASO(350),
	/** Normal. */
	NORMAL(440),
	/** Generoso. */
	GENEROSO(460),
	/** Tortuga. */
	ELEFANTE(480);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new fuel.
	 *
	 * @param value the value
	 */
	private Fuel(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

}
