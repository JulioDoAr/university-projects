package com.car;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.circuit.Circuit;
import com.circuit.CircuitFactory;
import com.circuit.CircuitFactoryImpl;
import com.pilot.Pilot;
import com.pilot.PilotFactory;
import com.pilot.PilotFactoryImpl;

class FastCarTest {

	static CarFactory carFactory;
	static CircuitFactory circuitFactory;
	static PilotFactory pilotFactory;

	@BeforeAll
	public static void beforeAll() {
		carFactory = new CarFactoryImpl();
		circuitFactory = new CircuitFactoryImpl();
		pilotFactory = new PilotFactoryImpl();
	}

	@Test
	void test_constructor() {
		Car car = carFactory.seatAteca();

		assertEquals("Seat Ateca", car.getName());
		assertEquals(Speed.GUEPARDO, car.getSpeed());
		assertEquals(Fuel.GENEROSO, car.getFuel());
		assertEquals(Fuel.GENEROSO.getValue(), car.getActualFuel());
	}

	@Test
	void test_calculateRealSpeed() {
		Car car = carFactory.seatAteca();
		Pilot p = pilotFactory.mcRae();
		Circuit c = circuitFactory.chile();

		assertEquals(169.52, car.calculateRealSpeed(p, c));
	}

	@Test
	void test_compite() {
		Car car = carFactory.seatAteca();
		Pilot p = pilotFactory.mcRae();
		Circuit c = circuitFactory.chile();

		double usedFuel;
		// 2 laps with full nitro
		for (int i = 0; i < 2; i++) {
			usedFuel = car.drive(p, c);
			assertEquals(84.06, usedFuel);
			car.reduceFuel(usedFuel);
		}
		// 1 lap with half nitro
		for (int i = 0; i < 1; i++) {
			usedFuel = car.drive(p, c);
			assertEquals(86.49, usedFuel);
			car.reduceFuel(usedFuel);
		}
		// 2 laps without nitro
		for (int i = 0; i < 2; i++) {
			usedFuel = car.drive(p, c);
			assertEquals(100.87, usedFuel);
			car.reduceFuel(usedFuel);
		}
		// 1 unfinished lap
		assertEquals(-97.22, car.drive(p, c));
	}

	private final String SERIALIZATION = "<coche: Prueba> <tipo: FastCar> <vel_teó: NORMAL(220.0)> <comb: NORMAL(440.0)(actual: 440,00)> <nitro_pendiente: 80,00>";

	@Test
	void test_toString() {
		FastCar car = new FastCar("Prueba", Speed.NORMAL, Fuel.NORMAL);
		assertEquals(SERIALIZATION, car.toString());
	}

}
