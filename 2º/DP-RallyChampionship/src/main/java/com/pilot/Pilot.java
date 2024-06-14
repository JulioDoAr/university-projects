package com.pilot;

import java.util.HashMap;
import java.util.Map;

import com.car.Car;
import com.circuit.Circuit;
import com.logger.Logger;
import com.team.Team;
import com.utils.Messages;

/**
 * The Class Pilot.
 */
public abstract class Pilot {

	/** The log. */
	private final Logger log = Logger.getInstance();

	/** The name. */
	private String name;

	/** The car. */
	private Car car;

	/** The focus. */
	private Focus focus;

	/** The results. */
	private Map<String, RaceResult> results;

	/** The disqualified. */
	private boolean disqualified;

	/** The team. */
	private Team team;

	/**
	 * Instantiates a new pilot.
	 *
	 * @param name  the name
	 * @param focus the focus
	 */
	protected Pilot(String name, Focus focus) {
		this.name = name;
		this.car = null;
		this.focus = focus;
		this.results = new HashMap<String, RaceResult>();
		this.disqualified = false;
	}

	/**
	 * Calculate dexterity.
	 *
	 * @return the double
	 */
	public abstract double calculateDexterity();

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
	 * Gets the car.
	 *
	 * @return the car
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * Sets the car.
	 *
	 * @param car the new car
	 */
	public void setCar(Car car) {
		this.car = car;
	}

	/**
	 * Set car to null.
	 *
	 */
	public void resetCar() {
		this.car = null;
	}

	/**
	 * Gets the focus.
	 *
	 * @return the focus
	 */
	public Focus getFocus() {
		return focus;
	}

	/**
	 * Sets the focus.
	 *
	 * @param focus the new focus
	 */
	public void setFocus(Focus focus) {
		this.focus = focus;
	}

	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public Map<String, RaceResult> getResults() {
		return results;
	}

	/**
	 * Gets the result.
	 *
	 * @param circuitName the circuit name
	 * @return the result
	 */
	public RaceResult getResult(String circuitName) {
		return results.get(circuitName);
	}

	/**
	 * Sets the results.
	 *
	 * @param results the results
	 */
	public void setResults(Map<String, RaceResult> results) {
		this.results = results;
	}

	/**
	 * Checks if is disqualified.
	 *
	 * @return true, if is disqualified
	 */
	public boolean isDisqualified() {
		return disqualified;
	}

	/**
	 * Update is disqualified.
	 *
	 * @param numberOfMaxiumsDropouts the number of maxiums dropouts
	 */
	public void updateIsDisqualified(int numberOfMaxiumsDropouts) {
		int dropouts = 0;
		for (RaceResult result : results.values()) {
			if (result.isDropout())
				dropouts++;
		}

		if (!disqualified) {
			disqualified = dropouts >= numberOfMaxiumsDropouts;
		}
	}

	/**
	 * Adds the points.
	 *
	 * @param circuit the circuit
	 * @param points  the points
	 */
	public void addPoints(String circuit, int points) {
		RaceResult result = results.get(circuit);
		result.setPoints(points);
	}

	/**
	 * Calculate the sum of points earned by the pilot in all races.
	 *
	 * @return the total number of points;
	 */
	public int calculatePoints() {
		int total = 0;
		for (RaceResult result : results.values()) {
			total += result.getPoints();
		}
		return total;
	}

	@Override
	public String toString() {
		String text = String.format(Messages.PILOT_SERIALIZATION, getName(), getClass().getSimpleName(),
				calculateDexterity(), getFocus().toString(), isDisqualified());
		return text;
	}

	/**
	 * Drive.
	 *
	 * @param circuit the circuit
	 */
	public void drive(Circuit circuit) {
		// Tiempo necesario para completar una vuelta
		double timeNeeded = car.drive(this, circuit);
		// Creamos datos de la carrera
		RaceResult result = new RaceResult();

		if (timeNeeded < 0) {
			// En caso de falta de fuel
			result.setDropout(true);
			result.setTime(timeNeeded);
		} else if (!canStayFocused(timeNeeded)) {
			// En caso de perdida del conocimiento
			log.write(Messages.DROPOUT_MESSAGE, this.name, timeNeeded - focus.getValue());
			log.write(Messages.ABANDONE_RACE_MESSAGE, focus.getValue());
			car.reduceFuel(focus.getValue());
			result.setDropout(true);
			result.setTime(focus.getValue() - timeNeeded);
		} else {
			// En caso de todo ok
			result.setTime(timeNeeded);
			car.reduceFuel(timeNeeded);
			log.write(Messages.RACE_TIME, this.name, timeNeeded);
		}

		log.write(Messages.FUEL_BEFORE_RACE, car.getName(), car.getActualFuel());

		results.put(circuit.getName(), result);
	}

	/**
	 * Check if the pilot can stay focused a certain amount of time.
	 *
	 * @param time The time that the pilot need to be concentrated
	 * @return If the pilot can be concentrated all the time or not
	 */
	private boolean canStayFocused(double time) {
		return this.focus.getValue() > time;
	}

	/**
	 * Count the times that the pilot has leave a race.
	 * 
	 * @return the number of dropouts
	 */
	public int countDropuots() {
		int dropouts = 0;
		for (RaceResult result : results.values()) {
			if (result.isDropout())
				dropouts++;
		}
		return dropouts;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pilot))
			return false;

		Pilot pilot = (Pilot) obj;

		if (this.name == null ? pilot.getName() != null : !this.name.equals(pilot.getName()))
			return false;
		if (this.focus == null ? pilot.getFocus() != null : !this.focus.equals(pilot.getFocus()))
			return false;

		return true;
	}

	/**
	 * Checks if is disqualified.
	 *
	 * @param race the race
	 * @return true, if is disqualified
	 */
	public boolean isDisqualified(String race) {
		return results.get(race).isDropout();
	}

	/**
	 * Gets the time.
	 *
	 * @param race the race
	 * @return the time
	 */
	public double getTime(String race) {
		return results.get(race).getTime();
	}

	/**
	 * Calculate the number of points that the pilot have in a determined race.
	 *
	 * @param circuitName Name of the circuit
	 * @return The number of points earned in that race
	 */
	public int getPoints(String circuitName) {
		return results.get(circuitName).getPoints();
	}

	/**
	 * Check if the pilot has an assigned car and it has fuel.
	 *
	 * @return true, if successful
	 */
	public boolean canDrive() {
		return car != null && car.hasFuel();
	}

	/**
	 * Gets the finished races.
	 *
	 * @return the finished races
	 */
	public int getFinishedRaces() {
		int total = 0;
		for (RaceResult result : results.values()) {
			if (!result.isDropout())
				total++;
		}
		return total;
	}

	/**
	 * Sets the team
	 * 
	 * @param team the team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}
}
