package com.car;

import com.circuit.Circuit;
import com.logger.Logger;
import com.pilot.Pilot;
import com.utils.Messages;
import com.utils.Utils;

/**
 * The car.
 */
public class Car {

	/** The log. */
	protected final Logger log = Logger.getInstance();

	/** The name. */
	private String name;

	/** The speed. */
	private Speed speed;

	/** The fuel. */
	private Fuel fuel;

	/** The actual fuel. */
	private double actualFuel;

	/**
	 * Instantiates a new car.
	 *
	 * @param name  The name.
	 * @param speed The speed.
	 * @param fuel  The fuel.
	 */
	public Car(String name, Speed speed, Fuel fuel) {
		this.name = name;
		this.speed = speed;
		this.fuel = fuel;
		this.actualFuel = fuel.getValue();
	}

	/**
	 * Calculate the speed of the car driven by a pilot in a determined circuit.
	 *
	 * @param driver  The driver of the car.
	 * @param circuit The circuit to go.
	 * @return The calculated speed of the car.
	 */
	public double calculateRealSpeed(Pilot driver, Circuit circuit) {
		double realSpeed = Utils
				.round(speed.getValue() * driver.calculateDexterity() / circuit.getCalculatedComplexity());
		log.write(Messages.SPEED, realSpeed);
		return realSpeed;
	}

	/**
	 * Calculate the time that the car needs to complete a circuit.
	 *
	 * @param circuit The circuit to go.
	 * @param speed   The speed.
	 * @return The calculated amount of minutes that the car need to complete the
	 *         circuit.
	 */
	public double calculateTimeNeeded(Circuit circuit, double speed) {
		return Utils.round((circuit.getCalculatedDistance() / speed) * 60);
	}

	/**
	 * Drive across a circuit and returns the time needed, checking the fuel.
	 *
	 * @param driver  The driver of the car.
	 * @param circuit The circuit to go.
	 * @return The amount of minutes that the car needs to finish the circuit.
	 *         Return -1 if it has not enough fuel.
	 */
	public double drive(Pilot driver, Circuit circuit) {
		double time = -1;

		// Calculamos la velocidad del coche
		double speed = calculateRealSpeed(driver, circuit);
		// Calculamos el tiempo necesario en completar la carrera
		double neededTime = calculateTimeNeeded(circuit, speed);

		if (neededTime < actualFuel) {
			time = neededTime;
		} else {
			log.write(Messages.NO_ENOUGH_FUEL_MESSAGE, this.name, neededTime - actualFuel);
			log.write(Messages.NO_FUEL_TIME_MESSAGE, actualFuel);
			reduceFuel(neededTime);
			time = actualFuel;
		}

		return Utils.round(time);
	}

	/**
	 * Reduce the fuel of the car
	 * 
	 * @param amount The amount to reduce
	 */
	public void reduceFuel(double amount) {
		actualFuel -= amount;
	}

	/**
	 * Sets the name.
	 *
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the speed.
	 *
	 * @param speed The new speed.
	 */
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	/**
	 * Sets the fuel.
	 *
	 * @param fuel The new fuel.
	 */
	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}

	/**
	 * Gets the name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the actual fuel.
	 *
	 * @return The actual fuel.
	 */
	public double getActualFuel() {
		return actualFuel;
	}

	/**
	 * Gets the speed.
	 *
	 * @return The speed.
	 */
	public Speed getSpeed() {
		return speed;
	}

	/**
	 * Gets the fuel.
	 *
	 * @return The fuel.
	 */
	public Fuel getFuel() {
		return fuel;
	}

	/**
	 * Sets the actual fuel.
	 *
	 * @param actualfuel The new actual fuel.
	 */
	public void setActualFuel(double actualfuel) {
		this.actualFuel = actualfuel;
	}

	/**
	 * To string.
	 *
	 * @return The string.
	 */
	@Override
	public String toString() {
		return String.format(Messages.CAR_SERIALIZATION, getName(), getClass().getSimpleName(), getSpeed().name(),
				getSpeed().getValue(), getFuel().name(), getFuel().getValue(), getActualFuel());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Car))
			return false;

		Car car = (Car) obj;

		if (this.name == null ? car.getName() != null : !this.name.equals(car.getName()))
			return false;
		if (this.speed == null ? car.getSpeed() != null : !this.speed.equals(car.getSpeed()))
			return false;
		if (this.fuel == null ? car.getFuel() != null : !this.fuel.equals(car.getFuel()))
			return false;

		return true;
	}

	/**
	 * Checks for fuel.
	 *
	 * @return true, if successful.
	 */
	public boolean hasFuel() {
		return actualFuel > 0;
	}

}
