package com.e2e;

import org.junit.jupiter.api.Test;

import com.car.CarFactory;
import com.car.CarFactoryImpl;
import com.car.comparator.CarComparatorByRemainingFuelName;
import com.circuit.CircuitFactory;
import com.circuit.CircuitFactoryImpl;
import com.circuit.comparator.CircuitComparatorByDistance;
import com.logger.FileOutputOperation;
import com.logger.Logger;
import com.organization.Organization;
import com.pilot.PilotFactory;
import com.pilot.PilotFactoryImpl;
import com.pilot.comparator.PilotComparatorByPointsDexterityName;
import com.team.Team;
import com.team.comparator.TeamComparatorByTotalPointsTotalRacesName;

class CampeonatoCompletoTest {

	@Test
	void test() {
		Organization.getInstance().reset();
		Logger.getInstance().reset();
		Logger log = Logger.getInstance();
//		log.addOutputOperation(new ConsoleOutputOperation());
		log.addOutputOperation(new FileOutputOperation("src/test/resources/output/SalidaDatosCampeonatoCompleto.txt"));
		log.startWrite();
		log.write(
				"*********************************************************************************************************");
		log.write(
				"*****************ESTA SIMULACI�N CONCLUYE NORMALMENTE COMPLET�NDOSE TODAS LAS CARRERAS*******************");
		log.write(
				"*********************************************************************************************************\n");
		initData();
		log.finishWrite();
	}

	private void initData() {

		// organizador debe ser una instancia �nica con la siguiente configuraci�n:
		Organization organization = Organization.getInstance();
		// LimiteAbandonos=3, LimitePilotos=2,
		organization.setDropoutsLimit(3);
		organization.setPilotsLimit(2);

		// Circuitos ordenados de forma descendente de acuerdo a su distancia
		organization.setCircuitComparator(new CircuitComparatorByDistance().reversed());

		// creamos y a�adimos los circuitos del campeonato:
		// Crear circuito portugal con nombre:�Portugal" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// adem�s, acciones necesarias para que portugal sea un circuito con Gravilla y
		// Nocturno
		// a�adir circuito portugal a circuitos de la organizaci�n
		CircuitFactory circuitFactory = new CircuitFactoryImpl();
		organization.addCircuit(circuitFactory.portugal());

		// Crear circuito cerdena con nombre:�Cerde�a" - complejidad:ALTA -
		// distancia:CORTA);
		// adem�s, acciones necesarias para que cerdena sea un circuito con:
		// Gravilla y Mojado
		// a�adir circuito cerdena a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.cerdena());

		// Crear circuito australia con nombre:�Australia" - complejidad:BAJA -
		// distancia:LARGA);
		// adem�s, acciones necesarias para que australia sea un circuito con:
		// Gravilla
		// a�adir circuito australia a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.australia());

		// Crear circuito corcega con nombre:�C�rcega" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// adem�s, acciones necesarias para que corcega sea un circuito con:
		// Nocturno y Gravilla
		// a�adir circuito corcega a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.corcega());

		// Crear circuito finlandia con nombre:�Finlandia" - complejidad:ALTA -
		// distancia:CORTA);
		// adem�s, acciones necesarias para que finlandia sea un circuito con:
		// Nocturno, Fr�o y Mojado
		// a�adir circuito finlandia a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.finlandia());

		// Crear circuito alemania con nombre:�Alemania" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// adem�s, acciones necesarias para que alemania sea un circuito con:
		// Mojado
		// a�adir circuito alemania a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.alemania());

		// Crear circuito chile con nombre:�Chile" - complejidad:ALTA -
		// distancia:CORTA);
		// adem�s, acciones necesarias para que chile sea un circuito con:
		// Gravilla
		// a�adir circuito chile a circuitos de la organizaci�n
		organization.addCircuit(circuitFactory.chile());

		// creamos e inscribimos a las escuder�as que participar�n en el campeonato:
		organization.setTeamComparator(new TeamComparatorByTotalPointsTotalRacesName().reversed());
		// Crear escuderia peugeot con nombre:"Peugeot"
		// ordenaci�nPilotos: ASCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaci�nCoches: ASCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team peugeot = new Team("Peugeot", new PilotComparatorByPointsDexterityName(),
				new CarComparatorByRemainingFuelName());
		// peugeot se inscribe en campeonato
		peugeot.subscribe();

		// escuder�a citroen
		// Crear escuderia citroen con nombre:"Citroen"
		// ordenaci�nPilotos: DESCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaci�nCoches: DESCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team citroen = new Team("Citroen", new PilotComparatorByPointsDexterityName().reversed(),
				new CarComparatorByRemainingFuelName().reversed());
		// citroen se inscribe en campeonato
		citroen.subscribe();

		// escuder�a seat
		// Crear escuderia seat con nombre:"Seat"
		// ordenaci�nPilotos: ASCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaci�nCoches: ASCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team seat = new Team("Seat", new PilotComparatorByPointsDexterityName(),
				new CarComparatorByRemainingFuelName());
		// seat se inscribe en campeonato
		seat.subscribe();

		// creamos los pilotos y los coches de cada escuder�a

		// coches y pilotos de citroen
		// a�adir a citroen un CocheResistente(nombre:"Citr�en C5" - velocidad:RAPIDA -
		// combustible:ELEFANTE);
		CarFactory carFactory = new CarFactoryImpl();
		PilotFactory pilotFactory = new PilotFactoryImpl();
		citroen.addCar(carFactory.citroenC5());
		// a�adir a citroen un CocheRapido(nombre:"Citr�en C4" - velocidad:RAPIDA -
		// combustible:ESCASO);
		citroen.addCar(carFactory.citroenC4());
		// a�adir a citroen un Coche(nombre:"Citr�en C3" - velocidad:RAPIDA -
		// combustible:ESCASO);
		citroen.addCar(carFactory.citroenC3());
		// a�adir a citroen un PilotoExperimentado(nombre:"Loeb" - concentraci�n:
		// NORMAL));
		citroen.addPilot(pilotFactory.loeb());
		// a�adir a citroen un PilotoEstrella(nombre:"Makinen" - concentraci�n: ZEN));
		citroen.addPilot(pilotFactory.makinen());
		// a�adir a citroen un PilotoNovato(nombre:"Auriol" - concentraci�n: NORMAL));
		citroen.addPilot(pilotFactory.auriol());

		// coches y pilotos de seat
		// a�adir a seat un CocheResistente(nombre:"Seat Tarraco" - velocidad:TORTUGA -
		// combustible:GENEROSO);
		seat.addCar(carFactory.seatTarraco());
		// a�adir a seat un CocheRapido(nombre:"Seat Ateca" - velocidad:GUEPARDO -
		// combustible:GENEROSO);
		seat.addCar(carFactory.seatAteca());
		// a�adir a seat un Coche(nombre:"Seat Arona" - velocidad:RAPIDA -
		// combustible:ESCASO);
		seat.addCar(carFactory.seatArona());
		// a�adir a seat un PilotoExperimentado(nombre:"Ogier" - concentraci�n:
		// NORMAL));
		seat.addPilot(pilotFactory.ogier());
		// a�adir a seat un PilotoEstrella(nombre:"McRae" - concentraci�n:
		// CONCENTRADO));
		seat.addPilot(pilotFactory.mcRae());
		// a�adir a seat un PilotoNovato(nombre:"Blomquist" - concentraci�n:
		// DESPISTADO));
		seat.addPilot(pilotFactory.blomquist());

		// coches y pilotos de peugeot
		// a�adir a peugeot un CocheResistente(nombre:"Peugeot 5008" - velocidad:LENTA -
		// combustible:GENEROSO);
		peugeot.addCar(carFactory.peugeot5008());
		// a�adir a peugeot un CocheRapido(nombre:"Peugeot 3008" - velocidad:GUEPARDO -
		// combustible:NORMAL);
		peugeot.addCar(carFactory.peugeot3008());
		// a�adir a peugeot un Coche(nombre:"Peugeot 2008" - velocidad:NORMAL -
		// combustible:ESCASO);
		peugeot.addCar(carFactory.peugeot2008());
		// a�adir a peugeot un PilotoExperimentado(nombre:"Kankunnen" - concentraci�n:
		// CONCENTRADO));
		peugeot.addPilot(pilotFactory.kankunnen());
		// a�adir a peugeot un PilotoEstrella(nombre:"Sainz" - concentraci�n: ZEN ));
		peugeot.addPilot(pilotFactory.sainz());
		// a�adir a peugeot un PilotoNovato(nombre:"Sordo" - concentraci�n:
		// DESPISTADO));
		peugeot.addPilot(pilotFactory.sordo());

		organization.startChampionship();
	}
}
