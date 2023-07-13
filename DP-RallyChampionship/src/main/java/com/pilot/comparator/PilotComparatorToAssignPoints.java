package com.pilot.comparator;

import java.util.Comparator;

import com.pilot.Pilot;

/**
 * Order the pilots by the points earned in a determined race and name in
 * ascendant order.
 */
public class PilotComparatorToAssignPoints implements Comparator<Pilot> {

	/** The circuit name. */
	private String circuitName;

	/**
	 * Instantiates a new pilot comparator to assign points.
	 *
	 * @param circuitName the circuit name
	 */
	public PilotComparatorToAssignPoints(String circuitName) {
		this.circuitName = circuitName;
	}

	@Override
	public int compare(Pilot p1, Pilot p2) {
		int result = Boolean.compare(!p1.getResult(circuitName).isDropout(), !p2.getResult(circuitName).isDropout());
		if (result == 0) {
			result = Double.compare(p2.getResult(circuitName).getTime(), p1.getResult(circuitName).getTime());
		}
		if (result == 0)
			result = p1.getName().compareTo(p2.getName());
		return result;
	}

}
