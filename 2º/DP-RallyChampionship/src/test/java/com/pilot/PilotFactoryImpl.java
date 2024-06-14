package com.pilot;

/**
 * The implementation of PilotFactory.
 */
public class PilotFactoryImpl implements PilotFactory {

	/**
	 * Instantiates a new pilot factory impl.
	 */
	public PilotFactoryImpl() {
	};

	@Override
	public Pilot loeb() {
		return new ExperimentedPilot("Loeb", Focus.NORMAL);
	}

	@Override
	public Pilot makinen() {
		return new StarPilot("Makinen", Focus.ZEN);
	}

	@Override
	public Pilot auriol() {
		return new NoobPilot("Auriol", Focus.NORMAL);
	}

	@Override
	public Pilot ogier() {
		return new ExperimentedPilot("Ogier", Focus.NORMAL);
	}

	@Override
	public Pilot mcRae() {
		return new StarPilot("McRae", Focus.CONCENTRADO);
	}

	@Override
	public Pilot blomquist() {
		return new NoobPilot("Blomquist", Focus.DESPISTADO);
	}

	@Override
	public Pilot kankunnen() {
		return new ExperimentedPilot("Kankunnen", Focus.CONCENTRADO);
	}

	@Override
	public Pilot sainz() {
		return new StarPilot("Sainz", Focus.ZEN);
	}

	@Override
	public Pilot sordo() {
		return new NoobPilot("Sordo", Focus.DESPISTADO);
	}
}
