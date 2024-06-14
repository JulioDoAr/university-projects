package com.pilot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PilotTest {

	static PilotFactory pilotFactory;

	String noobToString = "<piloto:Blomquist> <tipo: NoobPilot> <dest: 0,70> <conc: DESPISTADO(90,00)> <descalificado:false>";
	Pilot noob;
	String experimentedToString = "<piloto:Ogier> <tipo: ExperimentedPilot> <dest: 0,82> <conc: NORMAL(100,00)> <descalificado:false>";
	Pilot experimented;
	String starToString = "<piloto:McRae> <tipo: StarPilot> <dest: 0,93> <conc: CONCENTRADO(110,00)> <descalificado:false>";
	Pilot star;

	@BeforeAll
	public static void beforeAll() {
		pilotFactory = new PilotFactoryImpl();
	}

	@BeforeEach
	void setUp() throws Exception {
		noob = pilotFactory.blomquist();
		experimented = pilotFactory.ogier();
		star = pilotFactory.mcRae();
	}

	@Test
	void test_constructor() {
		assertEquals(noob.getName(), "Blomquist");
		assertEquals(noob.getFocus(), Focus.DESPISTADO);
		assertEquals(noob.getCar(), null);
		assertTrue(noob.getResults().isEmpty());
		assertFalse(noob.isDisqualified());

		assertEquals(experimented.getName(), "Ogier");
		assertEquals(experimented.getFocus(), Focus.NORMAL);
		assertEquals(experimented.getCar(), null);
		assertTrue(experimented.getResults().isEmpty());
		assertFalse(experimented.isDisqualified());

		assertEquals(star.getName(), "McRae");
		assertEquals(star.getFocus(), Focus.CONCENTRADO);
		assertEquals(star.getCar(), null);
		assertTrue(star.getResults().isEmpty());
		assertFalse(star.isDisqualified());
	}

	@Test
	void test_toString() {
		assertEquals(noobToString, noob.toString());
		assertEquals(experimentedToString, experimented.toString());
		assertEquals(starToString, star.toString());
	}

	@Test
	void test_equals() {
		assertFalse(pilotFactory.auriol().equals(pilotFactory.blomquist()));
		assertTrue(pilotFactory.blomquist().equals(pilotFactory.blomquist()));
	}

	@Test
	void test_calculateDexterity() {
		assertEquals(0.7, noob.calculateDexterity());
		assertEquals(0.82, experimented.calculateDexterity());
		assertEquals(0.93, star.calculateDexterity());
	}

}
