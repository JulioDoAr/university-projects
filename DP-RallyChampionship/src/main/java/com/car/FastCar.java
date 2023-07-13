package com.car;

import com.circuit.Circuit;
import com.logger.Logger;
import com.pilot.Pilot;
import com.utils.Messages;
import com.utils.Utils;

/**
 * The fast car.
 */
public class FastCar extends Car {

	/** The log. */
	private final Logger log = Logger.getInstance();

	/** The nitro. */
	private double nitro;

	/**
	 * Instantiates a new fast car.
	 *
	 * @param name  the name
	 * @param speed the speed
	 * @param fuel  the fuel
	 */
	public FastCar(String name, Speed speed, Fuel fuel) {
		super(name, speed, fuel);
		nitro = 80;
	}

	@Override
	public double calculateRealSpeed(Pilot driver, Circuit circuit) {
		double realSpeed = super.calculateRealSpeed(driver, circuit);
		double maxiumBonusSpeed = realSpeed * 0.2f;

		double usedNitro = 0;
		if (nitro <= maxiumBonusSpeed && nitro > 0) {
			usedNitro = nitro;
		} else if (nitro > maxiumBonusSpeed) {
			usedNitro = maxiumBonusSpeed;
		}

		realSpeed += usedNitro;
		nitro -= usedNitro;

		if (usedNitro != 0)
			log.write(Messages.FAST_CAR_SPEED, getName(), usedNitro, realSpeed, nitro);

		return Utils.round(realSpeed);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String serialization = super.toString();
		serialization += String.format(Messages.FAST_CAR_SERIALIZATION, nitro);
		return serialization;
	}
}
