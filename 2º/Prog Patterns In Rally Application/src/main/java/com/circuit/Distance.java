package com.circuit;

/**
 * The Enum Distance.
 */
public enum Distance {

	/** Corta. */
	CORTA(250),
	/** Media. */
	INTERMEDIA(275),
	/** Larga. */
	LARGA(300);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new distance.
	 *
	 * @param value the value
	 */
	private Distance(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
