package com.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.car.CarFactory;
import com.car.CarFactoryImpl;
import com.car.comparator.CarComparatorByRemainingFuelName;
import com.organization.Organization;
import com.pilot.Pilot;
import com.pilot.PilotFactory;
import com.pilot.PilotFactoryImpl;
import com.pilot.comparator.PilotComparatorByPointsDexterityName;

class TeamTest {

	Team team;
	static CarFactory carFactory;
	static PilotFactory pilotFactory;

	@BeforeAll
	public static void beforeAll() {
		carFactory = new CarFactoryImpl();
		pilotFactory = new PilotFactoryImpl();
	}

	@BeforeEach
	void setUp() throws Exception {
		Organization.reset();
		team = new Team("test", new PilotComparatorByPointsDexterityName(), new CarComparatorByRemainingFuelName());
	}

	@Test
	void test_constructor() {
		assertNotNull(team);
		assertEquals("test", team.getName());
		assertEquals(true, team.getCars().isEmpty());
		assertEquals(true, team.getPilots().isEmpty());
	}

	@Test
	void test_addCar() {
		team.addCar(carFactory.citroenC3());
		team.addCar(carFactory.citroenC4());
		team.addCar(carFactory.citroenC5());
		team.addCar(carFactory.peugeot2008());
		team.addCar(carFactory.peugeot3008());
		team.addCar(carFactory.peugeot5008());
		team.addCar(carFactory.seatArona());
		team.addCar(carFactory.seatAteca());
		team.addCar(carFactory.seatTarraco());

		assertEquals(9, team.getCars().size());
	}

	@Test
	void test_addCar_null() {
		team.addCar(null);
		assertEquals(0, team.getCars().size());
	}

	@Test
	void test_addPilot() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		team.addPilot(pilotFactory.loeb());
		team.addPilot(pilotFactory.makinen());
		team.addPilot(pilotFactory.mcRae());
		team.addPilot(pilotFactory.ogier());
		team.addPilot(pilotFactory.sainz());
		team.addPilot(pilotFactory.sordo());

		assertEquals(9, team.getPilots().size());
	}

	@Test
	void test_addPilot_null() {
		team.addPilot(null);
		assertEquals(0, team.getPilots().size());
	}

	@Test
	void test_subscribe() {
		team.subscribe();
		assertEquals(1, Organization.getInstance().getTeams().size());
	}

	@Test
	void test_getPilots_number() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		team.addCar(carFactory.citroenC3());
		team.addCar(carFactory.citroenC4());
		team.addCar(carFactory.citroenC5());
		team.assignCarsToPilots(2);

		List<Pilot> pilots = team.getPilots(2);
		assertEquals(2, pilots.size());
	}

	@Test
	void test_getPilots_emptyPilots() {
		List<Pilot> pilots = team.getPilots(2);
		assertTrue(pilots.isEmpty());
	}

	@Test
	void test_getPilots_cero() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		List<Pilot> pilots = team.getPilots(0);
		assertTrue(pilots.isEmpty());
	}

	@Test
	void test_getPilots_moreRequestedThanHave() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		team.addCar(carFactory.citroenC3());
		team.addCar(carFactory.citroenC4());
		team.addCar(carFactory.citroenC5());
		team.assignCarsToPilots(5);

		List<Pilot> pilots = team.getPilots(1);
		assertEquals(1, pilots.size());
	}

	@Test
	void test_asigCarsToPilots() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		team.addCar(carFactory.citroenC3());
		team.addCar(carFactory.peugeot3008());
		team.addCar(carFactory.citroenC4());
		team.assignCarsToPilots(3);
		for (Pilot pilot : team.getPilots()) {
			assertNotNull(pilot.getCar());
		}
	}

	@Test
	void test_asigCarsToPilots_oneWithoutCar() {
		team.addPilot(pilotFactory.auriol());
		team.addPilot(pilotFactory.blomquist());
		team.addPilot(pilotFactory.kankunnen());
		team.addPilot(pilotFactory.makinen());
		team.addCar(carFactory.citroenC3());
		team.addCar(carFactory.citroenC4());
		team.addCar(carFactory.citroenC5());
		team.assignCarsToPilots(3);
		Iterator<Pilot> it = team.getPilots().iterator();

		assertNotNull(it.next().getCar());
		assertNotNull(it.next().getCar());
		assertNotNull(it.next().getCar());
		assertNull(it.next().getCar());
	}

	@Test
	void test_AssignCarsToPilots() {
		Team peugeot = new Team("Peugeot", new PilotComparatorByPointsDexterityName().reversed(),
				new CarComparatorByRemainingFuelName().reversed());
		peugeot.addCar(carFactory.peugeot5008());
		peugeot.addCar(carFactory.peugeot3008());
		peugeot.addCar(carFactory.peugeot2008());
		peugeot.addPilot(pilotFactory.kankunnen());
		peugeot.addPilot(pilotFactory.sainz());
		peugeot.addPilot(pilotFactory.sordo());

		peugeot.assignCarsToPilots(2);

		for (Pilot pilot : peugeot.getPilots()) {
			if (pilot.getName().equals("Kankunnen"))
				assertEquals("Peugeot 3008", pilot.getCar().getName());
			else if (pilot.getName().equals("Sainz"))
				assertEquals("Peugeot 5008", pilot.getCar().getName());
			else if (pilot.getName().equals("Sordo"))
				assertNull(pilot.getCar());
		}

		Team seat = new Team("Seat", new PilotComparatorByPointsDexterityName().reversed(),
				new CarComparatorByRemainingFuelName().reversed());

		seat.addCar(carFactory.seatTarraco());
		seat.addCar(carFactory.seatAteca());
		seat.addCar(carFactory.seatArona());
		seat.addPilot(pilotFactory.ogier());
		seat.addPilot(pilotFactory.mcRae());
		seat.addPilot(pilotFactory.blomquist());
		seat.assignCarsToPilots(2);
		for (Pilot pilot : peugeot.getPilots()) {
			if (pilot.getName().equals("Ogier"))
				assertEquals("Seat Ateca", pilot.getCar().getName());
			else if (pilot.getName().equals("McRae"))
				assertEquals("Seat Tarraco", pilot.getCar().getName());
			else if (pilot.getName().equals("Blomquist"))
				assertNull(pilot.getCar());
		}
	}

}
