package com.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.car.comparator.CarComparatorByRemainingFuelName;
import com.circuit.Circuit;
import com.circuit.CircuitFactory;
import com.circuit.CircuitFactoryImpl;
import com.pilot.Pilot;
import com.pilot.PilotFactory;
import com.pilot.PilotFactoryImpl;

class CarTest {

	private final static String SERIALIZATION = "<coche: Prueba> <tipo: Car> <vel_teó: NORMAL(220.0)> <comb: NORMAL(440.0)(actual: 440,00)>";

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
		Car car = carFactory.citroenC3();

		assertEquals("Citröen C3", car.getName());
		assertEquals(Speed.RAPIDA, car.getSpeed());
		assertEquals(Fuel.ESCASO, car.getFuel());
		assertEquals(Fuel.ESCASO.getValue(), car.getActualFuel());
	}

	@Test
	void test_calculateRealSpeed() {
		Car car = carFactory.citroenC3();
		Pilot p = pilotFactory.auriol();
		Circuit c = circuitFactory.australia();

		assertEquals(170.86, car.calculateRealSpeed(p, c));
	}

	@Test
	void test_compite() {
		Car car = carFactory.citroenC3();
		Pilot p = pilotFactory.makinen();
		Circuit c = circuitFactory.alemania();

		double usedFuel;
		// Damos 3 vueltas para agotar combustible
		for (int i = 0; i < 3; i++) {
			usedFuel = car.drive(p, c);
			assertEquals(87.81, usedFuel);
			car.reduceFuel(usedFuel);
		}
		// En la sexta vuelta nos quedamos sin combustible
		assertEquals(-1.24, car.drive(p, c));
	}

	@Test
	void test_toString() {
		Car car = new Car("Prueba", Speed.NORMAL, Fuel.NORMAL);
		assertEquals(SERIALIZATION, car.toString());
	}

	@Test
	void test_comparator() {
		CarComparatorByRemainingFuelName comparatorEqual = new CarComparatorByRemainingFuelName();
		assertEquals(0, comparatorEqual.compare(carFactory.citroenC3(), carFactory.citroenC3()));

		CarComparatorByRemainingFuelName comparatorGreater = new CarComparatorByRemainingFuelName();
		assertEquals(1, comparatorGreater.compare(carFactory.seatAteca(), carFactory.seatArona()));

		CarComparatorByRemainingFuelName comparatorLower = new CarComparatorByRemainingFuelName();
		assertEquals(-1, comparatorLower.compare(carFactory.seatArona(), carFactory.seatAteca()));
	}

	@Test
	void test_equals() {
		assertFalse(carFactory.citroenC3().equals(carFactory.citroenC4()));
		assertTrue(carFactory.citroenC3().equals(carFactory.citroenC3()));
	}

}
