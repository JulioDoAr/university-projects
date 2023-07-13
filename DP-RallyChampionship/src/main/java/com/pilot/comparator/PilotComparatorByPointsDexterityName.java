package com.pilot.comparator;

import java.util.Comparator;

import com.pilot.Pilot;

/**
 * Order the pilots by dexterity points and name in ascendant order.
 */
public class PilotComparatorByPointsDexterityName implements Comparator<Pilot> {

	@Override
	public int compare(Pilot pilot1, Pilot pilot2) {
		int result = 0;

		if (pilot1 == null && pilot2 == null) {
			result = 0;
		} else if (pilot1 == null && pilot2 != null) {
			result = 1;
		} else if (pilot1 != null && pilot2 == null) {
			result = -1;
		} else {
			result = Integer.compare(pilot1.calculatePoints(), pilot2.calculatePoints());
			if (result == 0) {
				result = Double.compare(pilot1.calculateDexterity(), pilot2.calculateDexterity());
				if (result == 0) {
					result = pilot1.getName().compareTo(pilot2.getName());
				}
			}
		}

		return result;
	}
}
