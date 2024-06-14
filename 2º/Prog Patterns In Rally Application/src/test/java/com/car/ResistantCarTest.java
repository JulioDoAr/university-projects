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

class ResistantCarTest {

	static CircuitFactory circuitFactory;
	static CarFactory carFactory;
	static PilotFactory pilotFactory;

	@BeforeAll
	public static void beforeAll() {
		circuitFactory = new CircuitFactoryImpl();
		carFactory = new CarFactoryImpl();
		pilotFactory = new PilotFactoryImpl();
	}

	@Test
	void test_constructor() {
		Car car = carFactory.seatTarraco();

		assertEquals("Seat Tarraco", car.getName());
		assertEquals(Speed.TORTUGA, car.getSpeed());
		assertEquals(Fuel.GENEROSO, car.getFuel());
		assertEquals(Fuel.GENEROSO.getValue(), car.getActualFuel());
	}

//	<piloto:McRae> <tipo: PilotoEstrella> <dest: 0.93> <conc: CONCENTRADO(110.0)> <descalificado:false> con 
//	<coche: Seat Tarraco> <tipo: CocheResistente> <vel_teó: TORTUGA(200.0)> <comb: GENEROSO(460.0)(actual:460.0)>> <reserva: 100.0>
//	+++ Con estas condiciones es capaz de correr a 177.14 km/hora +++
//	+++ McRae termina la carrera en 96.53 minutos +++
//	+++ El combustible del Seat Tarraco tras la carrera es 363.47 +++

	@Test
	void test_calculateRealSpeed() {
		Car car = carFactory.seatTarraco();
		Pilot p = pilotFactory.mcRae();
		Circuit c = circuitFactory.australia();

		assertEquals(177.14, car.calculateRealSpeed(p, c));
	}

	@Test
	void test_compite() {
		Car car = carFactory.seatTarraco();
		Pilot p = pilotFactory.mcRae();
		Circuit c = circuitFactory.australia();

		double usedFuel;
		// Damos 5 vueltas para agotar combustible
		for (int i = 0; i < 5; i++) {
			usedFuel = car.drive(p, c);
			assertEquals(96.53, usedFuel);
			car.reduceFuel(usedFuel);
		}
		// En la sexta vuelta nos quedamos sin combustible
		assertEquals(-1, car.drive(p, c));
	}
}
