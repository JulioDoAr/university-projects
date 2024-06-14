package com.pilot;

/**
 * The Enum Focus.
 */
public enum Focus {

	/** Despistado. */
	DESPISTADO(90),
	/** Normal. */
	NORMAL(100),
	/** TConcentrado. */
	CONCENTRADO(110),
	/** Zen. */
	ZEN(120);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new focus.
	 *
	 * @param value the value
	 */
	private Focus(int value) {
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

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("%s(%.2f)", this.name(), this.getValue());
	}
}
