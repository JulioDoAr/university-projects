package com.car;

import com.circuit.Circuit;
import com.pilot.Pilot;
import com.utils.Messages;
import com.utils.Utils;

/**
 * The resistant car.
 */
public class ResistantCar extends Car {

	/** The auxiliar fuel. */
	private int auxiliarFuel = 100;

	/**
	 * Instantiates a new resistant car.
	 *
	 * @param name  the name
	 * @param speed the speed
	 * @param fuel  the fuel
	 */
	public ResistantCar(String name, Speed speed, Fuel fuel) {
		super(name, speed, fuel);
	}

	@Override
	public double drive(Pilot driver, Circuit circuit) {
		double time = -1;

		// Calculamos la velocidad del coche
		double speed = calculateRealSpeed(driver, circuit);
		// Calculamos el tiempo necesario en completar la carrera
		double neededTime = calculateTimeNeeded(circuit, speed);

		addFuelIfNeccesary(neededTime);

		if (neededTime < getActualFuel()) {
			time = neededTime;
		} else {
			log.write(Messages.NO_ENOUGH_FUEL_MESSAGE, getName(), neededTime - getActualFuel());
			log.write(Messages.NO_FUEL_TIME_MESSAGE, getActualFuel());
			reduceFuel(neededTime);
		}
		return Utils.round(time);
	}

	/**
	 * Adds the fuel if neccesary.
	 *
	 * @param neededTime the needed time
	 */
	private void addFuelIfNeccesary(double neededTime) {
		if (auxiliarFuel != 0 && neededTime > getActualFuel()) {
			setActualFuel(getActualFuel() + auxiliarFuel);
			auxiliarFuel = 0;
		}
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String serialization = super.toString();
		serialization += String.format(Messages.RESISTANT_CAR_SERIALIZATION, auxiliarFuel);
		return serialization;
	}
}
