package com.circuit;

/**
 * The Class CircuitData.
 */
public class CircuitData implements Circuit {

	/**
	 * Instantiates a new circuit data.
	 *
	 * @param name       the name
	 * @param complexity the complexity
	 * @param distance   the distance
	 */
	public CircuitData(String name, Complexity complexity, Distance distance) {
		this.name = name;
		this.complexity = complexity;
		this.distance = distance;
	}

	/** The name. */
	private String name;

	/** The complexity. */
	private Complexity complexity;

	/** The distance. */
	private Distance distance;

	/**
	 * Gets the calculated distance.
	 *
	 * @return the calculated distance
	 */
	@Override
	public double getCalculatedDistance() {
		return distance.getValue();
	}

	/**
	 * Gets the calculated complexity.
	 *
	 * @return the calculated complexity
	 */
	@Override
	public double getCalculatedComplexity() {
		return complexity.getValue();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the complexity.
	 *
	 * @return the complexity
	 */
	public Complexity getComplexity() {
		return complexity;
	}

	/**
	 * Sets the complexity.
	 *
	 * @param complexity the new complexity
	 */
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance the new distance
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	/**
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	@Override
	public String getConditions() {
		return "";
	}

}
