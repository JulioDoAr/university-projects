package com.pilot;

import com.utils.Utils;

/**
 * The Class NoobPilot.
 */
public class NoobPilot extends Pilot {

	/**
	 * Instantiates a new noob pilot.
	 *
	 * @param name  the name
	 * @param focus the focus
	 */
	public NoobPilot(String name, Focus focus) {
		super(name, focus);
	}

	@Override
	public double calculateDexterity() {
		// destreza = ((concentración del piloto * 0.97) / 120 ) - 0.03;
		return Utils.round(((this.getFocus().getValue() * 0.97f) / 120) - 0.03f);
	}

}
