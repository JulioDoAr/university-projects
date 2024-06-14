package com.car;

/**
 * The Enum Speed.
 */
public enum Speed {

	/** Tortuga. */
	TORTUGA(200),
	/** Lenta. */
	LENTA(210),
	/** Normal. */
	NORMAL(220),
	/** Rapida. */
	RAPIDA(230),
	/** Guepardo. */
	GUEPARDO(240);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new speed.
	 *
	 * @param value the value
	 */
	private Speed(int value) {
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
