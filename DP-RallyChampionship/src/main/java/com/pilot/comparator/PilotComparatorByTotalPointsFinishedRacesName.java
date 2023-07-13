package com.pilot.comparator;

import java.util.Comparator;

import com.pilot.Pilot;

/**
 * Order the pilots by total points, finished races and name in ascendant order.
 */
public class PilotComparatorByTotalPointsFinishedRacesName implements Comparator<Pilot> {

	@Override
	public int compare(Pilot pilot1, Pilot pilot2) {
		int result = Integer.compare(pilot1.calculatePoints(), pilot2.calculatePoints());
		if (result == 0)
			result = Integer.compare(pilot1.getFinishedRaces(), pilot2.getFinishedRaces());
		if (result == 0)
			result = pilot1.getName().compareTo(pilot2.getName());
		return result;
	}

}
