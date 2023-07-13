package com.team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.car.Car;
import com.common.OrderedList;
import com.logger.Logger;
import com.organization.Organization;
import com.pilot.Pilot;
import com.utils.Messages;

/**
 * The Team that will compete in a championship. It can have pilots and cars and
 * assign cars to pilot.
 */
public class Team {

	/** The log. */
	Logger log = Logger.getInstance();

	/** The name. */
	private String name;

	/** The pilots. */
	private OrderedList<Pilot> pilots;

	/** The cars. */
	private OrderedList<Car> cars;

	/**
	 * Instantiates a new team.
	 *
	 * @param name             the name
	 * @param pilotsComparator the pilots comparator
	 * @param carsComparator   the cars comparator
	 */
	public Team(String name, Comparator<Pilot> pilotsComparator, Comparator<Car> carsComparator) {
		this.name = name;
		this.pilots = new OrderedList<Pilot>(pilotsComparator);
		this.cars = new OrderedList<Car>(carsComparator);
	}

	/**
	 * Adds a car.
	 *
	 * @param car the car
	 */
	public void addCar(Car car) {
		if (car != null)
			cars.add(car);
	}

	/**
	 * Adds a pilot.
	 *
	 * @param pilot the pilot
	 */
	public void addPilot(Pilot pilot) {
		if (pilot != null) {
			pilots.add(pilot);
			pilot.setTeam(this);
		}
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
	 * Gets the pilots.
	 *
	 * @return the pilots
	 */
	public List<Pilot> getPilots() {
		return pilots;
	}

	/**
	 * Gets a determined number of pilots. Depends of the pilotComparator
	 *
	 * @param num the num
	 * @return the pilots
	 */
	public List<Pilot> getPilots(int num) {

		List<Pilot> list = new ArrayList<Pilot>();
		Iterator<Pilot> iterator = pilots.iterator();

		while (list.size() < num && iterator.hasNext()) {
			Pilot pilot = iterator.next();
			if (!pilot.isDisqualified() && pilot.canDrive()) {
				list.add(pilot);
			}
		}

		return list;
	}

	/**
	 * Gets the cars.
	 *
	 * @return the cars
	 */
	public List<Car> getCars() {
		return cars;
	}

	@Override
	public String toString() {

		String pilotsInfo = "";
		for (Pilot pilot : pilots) {
			pilotsInfo += "\n" + pilot.toString();
		}

		String carInfo = "";
		for (Car car : getCars()) {
			carInfo += "\n" + car.toString();
		}

		String text = "%%% " + name.toUpperCase() + " %%%";
		text += pilotsInfo;
		text += carInfo;

		return text;
	}

	/**
	 * Gets the total points.
	 *
	 * @return the total points
	 */
	public int getTotalPoints() {
		int totalPoints = 0;

		for (Pilot pilot : pilots) {
			if (!pilot.isDisqualified())
				totalPoints += pilot.calculatePoints();
		}

		return totalPoints;
	}

	/**
	 * Checks if is disqualified.
	 *
	 * @return true, if is disqualified
	 */
	public boolean isDisqualified() {
		for (Pilot pilot : pilots) {
			if (!pilot.isDisqualified())
				return false;
		}
		return true;
	}

	/**
	 * Checks for disqualified pilots.
	 *
	 * @return true, if successful
	 */
	public boolean hasDisqualifiedPilots() {
		for (Pilot pilot : pilots) {
			if (pilot.isDisqualified())
				return true;
		}
		return false;
	}

	/**
	 * Look for disqualified pilots and returns it.
	 * 
	 * @return an array of disqualified pilots. Empty array if has not disqualified
	 *         pilots.
	 */
	public ArrayList<Pilot> getDisqualifiedPilots() {
		ArrayList<Pilot> disqualifiedPilots = new ArrayList<Pilot>();

		for (Pilot pilot : pilots) {
			if (pilot.isDisqualified())
				disqualifiedPilots.add(pilot);
		}

		return disqualifiedPilots;
	}

	/**
	 * Look for qualified pilots and returns it.
	 * 
	 * @return an array of qualified pilots. Empty array if has not qualified
	 *         pilots.
	 */
	public ArrayList<Pilot> getQualifiedPilots() {
		ArrayList<Pilot> qualifiedPilots = new ArrayList<Pilot>();

		for (Pilot pilot : pilots) {
			if (!pilot.isDisqualified())
				qualifiedPilots.add(pilot);
		}

		return qualifiedPilots;
	}

	/**
	 * Subscribe the team to the championship.
	 */
	public void subscribe() {
		Organization.getInstance().inscribeTeam(this);
	}

	/**
	 * Assign each car with the correspondent pilot based in the order selected by
	 * the team.
	 *
	 * @param pilotsLimit the pilots limit
	 */
	public void assignCarsToPilots(int pilotsLimit) {
		int index = 0;

		pilots.sort();
		cars.sort();

		ArrayList<Pilot> undisqualifiedPilots = getUndisqualifiedPilots();
		Iterator<Car> auxCars = getFueledCars().iterator();

		for (Pilot pilot : undisqualifiedPilots) {
			pilot.resetCar();
			if (index < pilotsLimit) {
				if (auxCars.hasNext()) {
					pilot.setCar(auxCars.next());
				} else {
					log.write(Messages.PILOT_WITHOUT_CAR, pilot.getName(), pilot.getTeam().getName());
				}
				index++;
			}
		}
	}

	private ArrayList<Pilot> getUndisqualifiedPilots() {
		ArrayList<Pilot> undisqualifiedPilots = new ArrayList<Pilot>();

		for (Pilot pilot : pilots) {
			if (!pilot.isDisqualified())
				undisqualifiedPilots.add(pilot);
		}

		return undisqualifiedPilots;
	}

	private ArrayList<Car> getFueledCars() {
		ArrayList<Car> fueledCars = new ArrayList<Car>();

		for (Car car : cars) {
			if (car.hasFuel())
				fueledCars.add(car);
		}

		return fueledCars;
	}

	/**
	 * Gets the finished races.
	 *
	 * @return the finished races
	 */
	public int getFinishedRaces() {
		int total = 0;
		for (Pilot pilot : pilots) {
			total += pilot.getFinishedRaces();
		}
		return total;
	}
}
