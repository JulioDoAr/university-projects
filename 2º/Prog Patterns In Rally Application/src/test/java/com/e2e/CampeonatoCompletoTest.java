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
				"*****************ESTA SIMULACIÓN CONCLUYE NORMALMENTE COMPLETÁNDOSE TODAS LAS CARRERAS*******************");
		log.write(
				"*********************************************************************************************************\n");
		initData();
		log.finishWrite();
	}

	private void initData() {

		// organizador debe ser una instancia única con la siguiente configuración:
		Organization organization = Organization.getInstance();
		// LimiteAbandonos=3, LimitePilotos=2,
		organization.setDropoutsLimit(3);
		organization.setPilotsLimit(2);

		// Circuitos ordenados de forma descendente de acuerdo a su distancia
		organization.setCircuitComparator(new CircuitComparatorByDistance().reversed());

		// creamos y añadimos los circuitos del campeonato:
		// Crear circuito portugal con nombre:”Portugal" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// además, acciones necesarias para que portugal sea un circuito con Gravilla y
		// Nocturno
		// añadir circuito portugal a circuitos de la organización
		CircuitFactory circuitFactory = new CircuitFactoryImpl();
		organization.addCircuit(circuitFactory.portugal());

		// Crear circuito cerdena con nombre:”Cerdeña" - complejidad:ALTA -
		// distancia:CORTA);
		// además, acciones necesarias para que cerdena sea un circuito con:
		// Gravilla y Mojado
		// añadir circuito cerdena a circuitos de la organización
		organization.addCircuit(circuitFactory.cerdena());

		// Crear circuito australia con nombre:”Australia" - complejidad:BAJA -
		// distancia:LARGA);
		// además, acciones necesarias para que australia sea un circuito con:
		// Gravilla
		// añadir circuito australia a circuitos de la organización
		organization.addCircuit(circuitFactory.australia());

		// Crear circuito corcega con nombre:”Córcega" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// además, acciones necesarias para que corcega sea un circuito con:
		// Nocturno y Gravilla
		// añadir circuito corcega a circuitos de la organización
		organization.addCircuit(circuitFactory.corcega());

		// Crear circuito finlandia con nombre:”Finlandia" - complejidad:ALTA -
		// distancia:CORTA);
		// además, acciones necesarias para que finlandia sea un circuito con:
		// Nocturno, Frío y Mojado
		// añadir circuito finlandia a circuitos de la organización
		organization.addCircuit(circuitFactory.finlandia());

		// Crear circuito alemania con nombre:”Alemania" - complejidad:MEDIA -
		// distancia:INTERMEDIA);
		// además, acciones necesarias para que alemania sea un circuito con:
		// Mojado
		// añadir circuito alemania a circuitos de la organización
		organization.addCircuit(circuitFactory.alemania());

		// Crear circuito chile con nombre:”Chile" - complejidad:ALTA -
		// distancia:CORTA);
		// además, acciones necesarias para que chile sea un circuito con:
		// Gravilla
		// añadir circuito chile a circuitos de la organización
		organization.addCircuit(circuitFactory.chile());

		// creamos e inscribimos a las escuderías que participarán en el campeonato:
		organization.setTeamComparator(new TeamComparatorByTotalPointsTotalRacesName().reversed());
		// Crear escuderia peugeot con nombre:"Peugeot"
		// ordenaciónPilotos: ASCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaciónCoches: ASCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team peugeot = new Team("Peugeot", new PilotComparatorByPointsDexterityName(),
				new CarComparatorByRemainingFuelName());
		// peugeot se inscribe en campeonato
		peugeot.subscribe();

		// escudería citroen
		// Crear escuderia citroen con nombre:"Citroen"
		// ordenaciónPilotos: DESCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaciónCoches: DESCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team citroen = new Team("Citroen", new PilotComparatorByPointsDexterityName().reversed(),
				new CarComparatorByRemainingFuelName().reversed());
		// citroen se inscribe en campeonato
		citroen.subscribe();

		// escudería seat
		// Crear escuderia seat con nombre:"Seat"
		// ordenaciónPilotos: ASCENDENTE por Puntos del Piloto, en caso de empate por
		// Destreza, en caso de empate por nombre
		// ordenaciónCoches: ASCENDENTE por Combustible restante del Coche , en caso de
		// empate por nombre);
		Team seat = new Team("Seat", new PilotComparatorByPointsDexterityName(),
				new CarComparatorByRemainingFuelName());
		// seat se inscribe en campeonato
		seat.subscribe();

		// creamos los pilotos y los coches de cada escudería

		// coches y pilotos de citroen
		// añadir a citroen un CocheResistente(nombre:"Citröen C5" - velocidad:RAPIDA -
		// combustible:ELEFANTE);
		CarFactory carFactory = new CarFactoryImpl();
		PilotFactory pilotFactory = new PilotFactoryImpl();
		citroen.addCar(carFactory.citroenC5());
		// añadir a citroen un CocheRapido(nombre:"Citröen C4" - velocidad:RAPIDA -
		// combustible:ESCASO);
		citroen.addCar(carFactory.citroenC4());
		// añadir a citroen un Coche(nombre:"Citröen C3" - velocidad:RAPIDA -
		// combustible:ESCASO);
		citroen.addCar(carFactory.citroenC3());
		// añadir a citroen un PilotoExperimentado(nombre:"Loeb" - concentración:
		// NORMAL));
		citroen.addPilot(pilotFactory.loeb());
		// añadir a citroen un PilotoEstrella(nombre:"Makinen" - concentración: ZEN));
		citroen.addPilot(pilotFactory.makinen());
		// añadir a citroen un PilotoNovato(nombre:"Auriol" - concentración: NORMAL));
		citroen.addPilot(pilotFactory.auriol());

		// coches y pilotos de seat
		// añadir a seat un CocheResistente(nombre:"Seat Tarraco" - velocidad:TORTUGA -
		// combustible:GENEROSO);
		seat.addCar(carFactory.seatTarraco());
		// añadir a seat un CocheRapido(nombre:"Seat Ateca" - velocidad:GUEPARDO -
		// combustible:GENEROSO);
		seat.addCar(carFactory.seatAteca());
		// añadir a seat un Coche(nombre:"Seat Arona" - velocidad:RAPIDA -
		// combustible:ESCASO);
		seat.addCar(carFactory.seatArona());
		// añadir a seat un PilotoExperimentado(nombre:"Ogier" - concentración:
		// NORMAL));
		seat.addPilot(pilotFactory.ogier());
		// añadir a seat un PilotoEstrella(nombre:"McRae" - concentración:
		// CONCENTRADO));
		seat.addPilot(pilotFactory.mcRae());
		// añadir a seat un PilotoNovato(nombre:"Blomquist" - concentración:
		// DESPISTADO));
		seat.addPilot(pilotFactory.blomquist());

		// coches y pilotos de peugeot
		// añadir a peugeot un CocheResistente(nombre:"Peugeot 5008" - velocidad:LENTA -
		// combustible:GENEROSO);
		peugeot.addCar(carFactory.peugeot5008());
		// añadir a peugeot un CocheRapido(nombre:"Peugeot 3008" - velocidad:GUEPARDO -
		// combustible:NORMAL);
		peugeot.addCar(carFactory.peugeot3008());
		// añadir a peugeot un Coche(nombre:"Peugeot 2008" - velocidad:NORMAL -
		// combustible:ESCASO);
		peugeot.addCar(carFactory.peugeot2008());
		// añadir a peugeot un PilotoExperimentado(nombre:"Kankunnen" - concentración:
		// CONCENTRADO));
		peugeot.addPilot(pilotFactory.kankunnen());
		// añadir a peugeot un PilotoEstrella(nombre:"Sainz" - concentración: ZEN ));
		peugeot.addPilot(pilotFactory.sainz());
		// añadir a peugeot un PilotoNovato(nombre:"Sordo" - concentración:
		// DESPISTADO));
		peugeot.addPilot(pilotFactory.sordo());

		organization.startChampionship();
	}
}
