package com.pilot;

import com.utils.Utils;

/**
 * The Class StarPilot.
 */
public class StarPilot extends Pilot {

	/**
	 * Instantiates a new star pilot.
	 *
	 * @param name  the name
	 * @param focus the focus
	 */
	public StarPilot(String name, Focus focus) {
		super(name, focus);
	}

	@Override
	public double calculateDexterity() {
		// destreza = (((concentración del piloto + 6) / 140) * 1.06) + 0.05;
		return Utils.round((((this.getFocus().getValue() + 6) / 140) * 1.06d) + 0.05d);
	}

}
