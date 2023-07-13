package com.pilot.comparator;

import java.util.Comparator;

import com.pilot.Pilot;

/**
 * Order the pilots by race time and name in ascendant order.
 */
public class PilotComparatorByRaceTime implements Comparator<Pilot> {

	/** The circuit name. */
	private String circuitName;

	/**
	 * Instantiates a new pilot comparator by race points.
	 *
	 * @param circuitName the circuit name
	 */
	public PilotComparatorByRaceTime(String circuitName) {
		this.circuitName = circuitName;
	}

	@Override
	public int compare(Pilot pilot1, Pilot pilot2) {
		int result = Double.compare(pilot1.getTime(circuitName), pilot2.getTime(circuitName));
		if (result == 0)
			result = pilot1.getName().compareTo(pilot2.getName());
		return result;
	}
}
