package com.car;

/**
 * Car Factory.
 */
public interface CarFactory {

	/**
	 * Citroen C 5.
	 *
	 * @return the car
	 */
	Car citroenC5();

	/**
	 * Citroen C 4.
	 *
	 * @return the car
	 */
	Car citroenC4();

	/**
	 * Citroen C 3.
	 *
	 * @return the car
	 */
	Car citroenC3();

	/**
	 * Seat tarraco.
	 *
	 * @return the car
	 */
	Car seatTarraco();

	/**
	 * Seat ateca.
	 *
	 * @return the car
	 */
	Car seatAteca();

	/**
	 * Seat arona.
	 *
	 * @return the car
	 */
	Car seatArona();

	/**
	 * Peugeot 5008.
	 *
	 * @return the car
	 */
	Car peugeot5008();

	/**
	 * Peugeot 3008.
	 *
	 * @return the car
	 */
	Car peugeot3008();

	/**
	 * Peugeot 2008.
	 *
	 * @return the car
	 */
	Car peugeot2008();
}
