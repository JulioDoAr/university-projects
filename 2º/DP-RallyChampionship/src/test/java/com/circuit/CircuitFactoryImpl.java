package com.circuit;

/**
 * The implementation of {@link CircuitFactory}
 * 
 */
public class CircuitFactoryImpl implements CircuitFactory {

	/**
	 * Instantiates a new circuit factory impl.
	 */
	public CircuitFactoryImpl() {

	}

	@Override
	public Circuit australia() {
		return new ComplicationGravel(new CircuitData("Australia", Complexity.BAJA, Distance.LARGA));
	}

	@Override
	public Circuit portugal() {
		return new ComplicationNight(
				new ComplicationGravel(new CircuitData("Portugal", Complexity.MEDIA, Distance.INTERMEDIA)));
	}

	@Override
	public Circuit cerdena() {
		return new ComplicationWet(new ComplicationGravel(new CircuitData("Cerdeña", Complexity.ALTA, Distance.CORTA)));
	}

	@Override
	public Circuit corcega() {
		return new ComplicationGravel(
				new ComplicationNight(new CircuitData("Córcega", Complexity.MEDIA, Distance.INTERMEDIA)));
	}

	@Override
	public Circuit finlandia() {
		return new ComplicationWet(new ComplicationCold(
				new ComplicationNight(new CircuitData("Finlandia", Complexity.ALTA, Distance.CORTA))));
	}

	@Override
	public Circuit alemania() {
		return new ComplicationWet(new CircuitData("Alemania", Complexity.MEDIA, Distance.INTERMEDIA));
	}

	@Override
	public Circuit chile() {
		return new ComplicationGravel(new CircuitData("Chile", Complexity.ALTA, Distance.CORTA));
	}
}
