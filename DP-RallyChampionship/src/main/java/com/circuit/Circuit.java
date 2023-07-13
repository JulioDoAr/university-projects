package com.circuit;

/**
 * The Interface Circuit.
 */
public interface Circuit {

	/**
	 * Gets the calculated distance.
	 *
	 * @return the calculated distance
	 */
	double getCalculatedDistance();

	/**
	 * Gets the calculated complexity.
	 *
	 * @return the calculated complexity
	 */
	double getCalculatedComplexity();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the complexity.
	 *
	 * @return the complexity
	 */
	Complexity getComplexity();

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	Distance getDistance();

	/**
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	String getConditions();

	@Override
	String toString();
}
