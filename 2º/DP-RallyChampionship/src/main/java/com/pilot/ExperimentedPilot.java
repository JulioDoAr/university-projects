package com.pilot;

import com.utils.Utils;

/**
 * The Class ExperimentedPilot.
 */
public class ExperimentedPilot extends Pilot {

	/**
	 * Instantiates a new experimented pilot.
	 *
	 * @param name  the name
	 * @param focus the focus
	 */
	public ExperimentedPilot(String name, Focus focus) {
		super(name, focus);
	}

	@Override
	public double calculateDexterity() {
		// destreza = ((concentración del piloto + 3) / 130) * 1.03;
		return Utils.round(((this.getFocus().getValue() + 3) / 130) * 1.03f);
	}

}
