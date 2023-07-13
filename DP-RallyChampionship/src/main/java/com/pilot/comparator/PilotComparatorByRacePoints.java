package com.pilot.comparator;

import java.util.Comparator;

import com.pilot.Pilot;

/**
 * Order the pilots by race points and name in ascendant order.
 */
public class PilotComparatorByRacePoints implements Comparator<Pilot> {

	/** The circuit name. */
	private String circuitName;

	/**
	 * Instantiates a new pilot comparator by race points.
	 *
	 * @param circuitName the circuit name
	 */
	public PilotComparatorByRacePoints(String circuitName) {
		this.circuitName = circuitName;
	}

	@Override
	public int compare(Pilot pilot1, Pilot pilot2) {
		int result = Boolean.compare(!pilot1.getResult(circuitName).isDropout(),
				!pilot2.getResult(circuitName).isDropout());
		if (result == 0) {
			result = Integer.compare(pilot1.getPoints(circuitName), pilot2.getPoints(circuitName));
		}
		if (result == 0)
			result = pilot1.getName().compareTo(pilot2.getName());
		return result;
	}

}
