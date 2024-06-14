package com.organization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.circuit.Circuit;
import com.common.OrderedList;
import com.logger.Logger;
import com.pilot.Pilot;
import com.pilot.RaceResult;
import com.pilot.comparator.PilotComparatorByRacePoints;
import com.pilot.comparator.PilotComparatorByRaceTime;
import com.pilot.comparator.PilotComparatorByTotalPointsFinishedRacesName;
import com.pilot.comparator.PilotComparatorToAssignPoints;
import com.team.Team;
import com.team.comparator.TeamComparatorByTotalPointsTotalRacesName;
import com.utils.Messages;

/**
 * The Class Organization.
 */
public class Organization {

	/** The log. */
	private Logger log = Logger.getInstance();

	/** The instance. */
	private static Organization instance = null;

	/** The dropout limit. */
	private int dropoutsLimit;

	/** The pilots limit. */
	private int pilotsLimit;

	/** The circuits. */
	private OrderedList<Circuit> circuits;

	/** The teams. */
	private OrderedList<Team> teams;

	/** The pilots to compete. */
	private List<Pilot> pilotsToCompete;

	/**
	 * Instantiates a new organization.
	 */
	private Organization() {
		this.dropoutsLimit = 0;
		this.pilotsLimit = 0;
		this.circuits = new OrderedList<Circuit>();
		this.teams = new OrderedList<Team>();
		this.pilotsToCompete = new OrderedList<Pilot>(new PilotComparatorByTotalPointsFinishedRacesName());
	}

	/**
	 * Gets the single instance of Organization.
	 *
	 * @return single instance of Organization
	 */
	public static Organization getInstance() {
		if (instance == null)
			instance = new Organization();
		return instance;
	}

	/**
	 * <b>Only for testing purposes.</b> Resets all data introduced.
	 */
	public static void reset() {
		instance = null;
	}

	/**
	 * Inscribe team.
	 *
	 * @param team the team
	 */
	public void inscribeTeam(Team team) {
		teams.add(team);
	}

	/**
	 * Start championship.
	 */
	public void startChampionship() {

		boolean championshipContinues = true;
		printCircuits();
		printTeams();

		int raceIndex = 1;
		for (Circuit circuit : circuits) {
			if (championshipContinues) {
				printRace(circuit, raceIndex);
				assignCarsToPilots();
				fillPilotsToCompete();
				if (canChampionshipContinue()) {
					printRaceInfo(circuit, pilotsToCompete, raceIndex);
					startRace(circuit, pilotsToCompete);
					assignPoints(circuit.getName());
					printRaceResults(circuit, pilotsToCompete);
					raceIndex++;
				} else {
					championshipContinues = false;
					printChampionshipDoesntContinue();
				}
			}
		}
		finishChampionship();
	}

	/**
	 * Assign cars to pilots.
	 */
	private void assignCarsToPilots() {
		for (Team team : teams) {
			team.assignCarsToPilots(pilotsLimit);
		}
	}

	private void printChampionshipDoesntContinue() {
		log.write(Messages.EXCLAMATION_92);
		log.write(Messages.STOPPING_CHAMPIONSHIP);
		log.write(Messages.EXCLAMATION_92);
	}

	/**
	 * Assign points.
	 *
	 * @param circuitName the circuit name
	 */
	private void assignPoints(String circuitName) {

		Set<Pilot> pilotsByRacePuntuation = new TreeSet<Pilot>(
				new PilotComparatorToAssignPoints(circuitName).reversed());

		pilotsByRacePuntuation.addAll(pilotsToCompete);

		int points = 10;
		for (Pilot pilot : pilotsByRacePuntuation) {
			if (!pilot.getResult(circuitName).isDropout()) {
				pilot.addPoints(circuitName, points);
				if (points > 2)
					points -= 2;
			} else {
				pilot.addPoints(circuitName, 0);
			}
		}
	}

	/**
	 * Start race.
	 *
	 * @param circuit         the circuit
	 * @param pilotsToCompite the pilots to compite
	 */
	private void startRace(Circuit circuit, List<Pilot> pilotsToCompite) {
		log.write(Messages.PLUSES);
		log.write(Messages.START_RACE_IN, circuit.getName());
		log.write(Messages.PLUSES);

		int pilotIndex = 1;
		int pilotCount = pilotsToCompite.size();

		for (Pilot pilot : pilotsToCompite) {

			// Shows individual information.
			log.write(Messages.PILOT_INDEX_OF, pilotIndex, pilotCount);
			log.write(pilot.toString());
			log.write(pilot.getCar().toString());

			// Send pilot to compite.
			pilot.drive(circuit);
			log.write(Messages.AT);
			// Pilot undisqualified check.
			pilot.updateIsDisqualified(dropoutsLimit);

			if (pilot.isDisqualified()) {
				log.write(Messages.AT);
				log.write(Messages.DIQUALIFIED_DROPOUTS_LIMIT, pilot.getName(), dropoutsLimit);
				log.write(Messages.AT);
				log.write("");
			}

			pilotIndex++;
		}
	}

	/**
	 * Prints the circuits info.
	 */
	private void printCircuits() {
		log.write(Messages.VERTICAL_BARS);
		log.write(Messages.CIRCUITS_IN_CHAMPIONSHIP);
		log.write(Messages.VERTICAL_BARS);
		for (Circuit circuit : circuits) {
			log.write(circuit.toString());
		}
		log.write(Messages.VERTICAL_BARS);
		log.write("");
	}

	/**
	 * Prints the teams info.
	 */
	private void printTeams() {
		log.write(Messages.PERCENTUAL_43);
		log.write(Messages.TEAMS_OF_CHAMPIONSHIP);
		log.write(Messages.PERCENTUAL_43);
		for (Team team : teams) {
			log.write(Messages.PERCENTUAL_105);
			log.write(team.toString());
			log.write(Messages.PERCENTUAL_105);
		}
		log.write("");
	}

	/**
	 * Prints the race results.
	 *
	 * @param circuit         the circuit
	 * @param pilotsToCompite the pilots to compite
	 */
	private void printRaceResults(Circuit circuit, List<Pilot> pilotsToCompite) {

		log.write(Messages.PLUSES);
		log.write(Messages.FINAL_CLASSIFICATION_OF_RACE, circuit.getName());
		log.write(Messages.PLUSES);

		Set<Pilot> pilotsByRacePuntuation = new TreeSet<Pilot>(
				new PilotComparatorByRacePoints(circuit.getName()).reversed());
		pilotsByRacePuntuation.addAll(pilotsToCompite);

		Set<Pilot> qualifiedPilots = getAllQualifiedPilots(circuit.getName(), pilotsToCompite);
		Set<Pilot> disqualifiedPilots = getAllDisqualifiedPilots(circuit.getName(), pilotsToCompite);

		int index = 1;
		for (Pilot pilot : qualifiedPilots) {
			log.write(Messages.POSITION_TIME_POINTS, index, pilot.getName(), pilot.getTime(circuit.getName()),
					pilot.getPoints(circuit.getName()));
			index++;
		}

		for (Pilot pilot : disqualifiedPilots) {
			if (pilot.isDisqualified()) {
				log.write(Messages.POSITION_TIME_POINTS_DISQUALIFIED, pilot.getName(), pilot.getTime(circuit.getName()),
						pilot.getPoints(circuit.getName()));
			} else if (pilot.isDisqualified(circuit.getName()))
				log.write(Messages.ABANDONE_TIME_POINTS, pilot.getName(), pilot.getTime(circuit.getName()),
						pilot.getPoints(circuit.getName()));
		}
		log.write("");
	}

	/**
	 * Get all the qualified pilots in a certain circuit
	 * 
	 * @param circuitName The name of the circuit
	 * @param pilots      List of pilots to filter
	 * @return Return a set ordered by race time
	 */
	private Set<Pilot> getAllQualifiedPilots(String circuitName, List<Pilot> pilots) {
		Set<Pilot> set = new TreeSet<Pilot>(new PilotComparatorByRaceTime(circuitName));
		set.addAll(getAllPilotsByQualification(circuitName, pilots, false));
		return set;
	}

	/**
	 * Get all the disqualified pilots in a certain circuit
	 * 
	 * @param circuitName The name of the circuit
	 * @param pilots      List of pilots to filter
	 * @return Return a set ordered by reversed race time
	 */
	private Set<Pilot> getAllDisqualifiedPilots(String circuitName, List<Pilot> pilots) {
		Set<Pilot> set = new TreeSet<Pilot>(new PilotComparatorByRaceTime(circuitName));
		set.addAll(getAllPilotsByQualification(circuitName, pilots, true));
		return set;
	}

	/**
	 * Get all pilots that are disqualified or not in a circuit
	 * 
	 * @param circuitName    The circuit name
	 * @param pilots         List of pilots where search
	 * @param isDisqualified Flag that select all disqualified or not pilots.
	 *                       <b>true</b> get all the disqualified pilots,
	 *                       <b>false</b> if not.
	 * @return
	 */
	private List<Pilot> getAllPilotsByQualification(String circuitName, List<Pilot> pilots, boolean isDisqualified) {
		List<Pilot> list = new ArrayList<Pilot>();
		for (Pilot pilot : pilots) {
			if (pilot.isDisqualified(circuitName) == isDisqualified)
				list.add(pilot);
		}
		return list;
	}

	private void printRace(Circuit circuit, int index) {
		log.write(Messages.ASTERISKS_104);
		log.write(Messages.RACE_INFO, index, circuit.toString());
		log.write(Messages.ASTERISKS_104);
	}

	/**
	 * Print race info of a concrete circuit.
	 *
	 * @param circuit         the circuit
	 * @param pilotsToCompite the pilots to compite
	 * @param index           the index
	 */
	private void printRaceInfo(Circuit circuit, List<Pilot> pilotsToCompite, int index) {

		// Pilots without cars.
		for (Pilot pilot : pilotsToCompite) {
			if (!pilot.canDrive())
				log.write(
						"¡¡¡ %s NO ES ENVIADO A LA CARRERA porque su escudería(%s) no tiene más coches con combustible disponibles !!!",
						pilot.getName(), pilot.getTeam().getName());
		}

		log.write(Messages.ASTERISKS_104);
		log.write(Messages.PILOTS_TO_COMPITE_IN, circuit.getName());
		log.write(Messages.ASTERISKS_104);

		for (Pilot pilot : pilotsToCompite) {
			if (pilot.canDrive())
				log.write(pilot.toString());
		}
	}

	/**
	 * Fill pilots to compete.
	 */
	private void fillPilotsToCompete() {
		pilotsToCompete.clear();
		for (Team team : teams) {
			pilotsToCompete.addAll(team.getPilots(pilotsLimit));
		}
	}

	/**
	 * Check if the championship can continue. Check if all the pilots have been
	 * disqualified.
	 *
	 * @return true, if successful
	 */
	private boolean canChampionshipContinue() {

		int notDesqualifiedPilots = 0;
		for (Pilot pilot : pilotsToCompete) {
			if (!pilot.isDisqualified())
				notDesqualifiedPilots++;
		}

		return notDesqualifiedPilots > 1;
	}

	/**
	 * Finish championship. Prints the final classification of pilots and teams.
	 */
	private void finishChampionship() {
		log.write(Messages.ASTERISK_52);
		log.write(Messages.END_OF_CHAMPIONSHIP);

		/** Pilot clasification */
		printFinalClasificationPilots();

		/** Printing disqualified Pilots */
		Set<Pilot> disqualifiedPilots = getDisqualifiedPilots();
		if (!disqualifiedPilots.isEmpty())
			printDisqualifiedPilots(disqualifiedPilots);

		/** Team clasification */
		printFinalClasificationTeams();

		/** Printing disqualified Teams */
		if (areDisqualifiedTeams())
			printDisqualifiedTeams();
	}

	/**
	 * Gets all the disqualified pilots
	 * 
	 * @return Set of disqualified Pilots.
	 */
	private Set<Pilot> getDisqualifiedPilots() {
		Set<Pilot> disqualifiedPilots = new TreeSet<Pilot>(
				new PilotComparatorByTotalPointsFinishedRacesName().reversed());

		for (Team team : teams) {
			disqualifiedPilots.addAll(team.getDisqualifiedPilots());
		}

		return disqualifiedPilots;
	}

	/**
	 * Gets all the qualified pilots
	 * 
	 * @return Set of qualified Pilots.
	 */
	private Set<Pilot> getQualifiedPilots() {
		Set<Pilot> qualifiedPilots = new TreeSet<Pilot>(new PilotComparatorByTotalPointsFinishedRacesName().reversed());

		for (Team team : teams) {
			qualifiedPilots.addAll(team.getQualifiedPilots());
		}

		return qualifiedPilots;
	}

	/**
	 * Prints the final classification of pilots.
	 */
	private void printFinalClasificationPilots() {

		log.write(Messages.ASTERISK_52);
		log.write(Messages.CLASIFICACION_OF_PILOTS);
		log.write(Messages.ASTERISK_52);

		Set<Pilot> pilotsByRacePuntuation = new TreeSet<Pilot>(
				new PilotComparatorByTotalPointsFinishedRacesName().reversed());
		pilotsByRacePuntuation.addAll(getQualifiedPilots());

		int pilotIndex = 1;
		for (Pilot pilot : pilotsByRacePuntuation) {
			if (!pilot.isDisqualified()) {
				log.write(Messages.POSITION_OF_PILOT, pilotIndex, pilot.getName(), pilot.calculatePoints());

				for (Circuit circuit : circuits) {
					RaceResult raceResult = pilot.getResult(circuit.getName());

					if (raceResult != null)
						log.write(Messages.RACE_POINTS_NAME, circuit.getName(), raceResult.getPoints(),
								raceResult.getTime());
				}
				log.write("");

				pilotIndex++;
			}
		}
		if (pilotIndex == 1) {
			log.write(Messages.EXCLAMATION_UP_100);
			log.write(Messages.NO_PILOTS_CLASSIFIED);
			log.write(Messages.EXCLAMATION_DOWN_100);
		}

	}

	/**
	 * Prints the disqualified pilots.
	 */
	private void printDisqualifiedPilots(Set<Pilot> disqualifiedPilots) {

		log.write(Messages.ASTERISK_52);
		log.write(Messages.CLASIFICACION_OF_DISQUALIFIED_PILOTS);
		log.write(Messages.ASTERISK_52);

		for (Pilot disqualifiedPilot : disqualifiedPilots) {
			log.write(Messages.DISQUALIFIED_PILOT_ANULATED_POINTS, disqualifiedPilot.getName(),
					disqualifiedPilot.calculatePoints());
			for (Circuit circuit : circuits) {

				RaceResult raceResult = disqualifiedPilot.getResult(circuit.getName());
				if (raceResult == null)
					continue;
				else
					log.write(Messages.RACE_POINTS_TIME, circuit.getName(), raceResult.getPoints(),
							raceResult.getTime());
			}
			log.write("");
		}
		log.write("");
	}

	/**
	 * Prints the final classification of teams.
	 */
	private void printFinalClasificationTeams() {
		log.write(Messages.ASTERISK_52);
		log.write(Messages.CLASIFICACION_OF_TEAMS);
		log.write(Messages.ASTERISK_52);

		teams.sort(new TeamComparatorByTotalPointsTotalRacesName().reversed());
		int teamIndex = 1;
		for (Team team : teams) {

			if (!team.isDisqualified()) {
				log.write(Messages.POSITION_TEAM_POINTS, teamIndex, team.getName(), team.getTotalPoints());
				log.write(Messages.PERCENTUAL_105);
				log.write(team.toString());

				teamIndex++;

				log.write(Messages.PERCENTUAL_105);
			}
		}
		if (teamIndex == 1) {
			log.write(Messages.EXCLAMATION_UP_100);
			log.write(Messages.NO_TEAMS_CLASSIFIED);
			log.write(Messages.EXCLAMATION_DOWN_100);
		}
	}

	/**
	 * Prints the disqualified teams.
	 */
	private void printDisqualifiedTeams() {

		log.write(Messages.ASTERISK_52);
		log.write(Messages.CLASIFICACION_OF_DISQUALIFIED_TEAMS);
		log.write(Messages.ASTERISK_52);

		ArrayList<Team> disqualifiedTeams = new ArrayList<Team>();
		for (Team team : teams) {
			if (team.isDisqualified())
				disqualifiedTeams.add(team);
		}
		disqualifiedTeams.sort(new TeamComparatorByTotalPointsTotalRacesName());

		for (Team disqualifiedTeam : disqualifiedTeams) {
			log.write(Messages.DISQUALIFIED_TEAM, disqualifiedTeam.getName(), disqualifiedTeam.getTotalPoints());
			log.write(Messages.PERCENTUAL_105);
			log.write(disqualifiedTeam.toString());
			log.write(Messages.PERCENTUAL_105);
		}
	}

	/**
	 * Check if at least one pilot is disqualified.
	 *
	 * @return true, if successful
	 */
	private boolean areDisqualifiedPilots() {
		for (Team team : teams) {
			if (team.hasDisqualifiedPilots())
				return true;
		}
		return false;
	}

	/**
	 * Check if at least one team is disqualified. One team is disqualified if all
	 * its pilots have been disqualified.
	 *
	 * @return true, if successful
	 */
	private boolean areDisqualifiedTeams() {
		for (Team team : teams) {
			if (team.isDisqualified())
				return true;
		}
		return false;
	}

	/**
	 * Add a circuit to the list of circuits.
	 *
	 * @param circuit Circuit to add
	 * @return <b>true</b> if the circuit was successfully added, <b>false</b> if
	 *         not
	 */
	public void addCircuit(Circuit circuit) {
		circuits.add(circuit);
	}

	/**
	 * Sets the circuit comparator.
	 *
	 * @param comparator the new circuit comparator
	 */
	public void setCircuitComparator(Comparator<Circuit> comparator) {
		circuits.setComparator(comparator);
	}

	/**
	 * Sets the team comparator.
	 *
	 * @param comparator the new team comparator
	 */
	public void setTeamComparator(Comparator<Team> comparator) {
		teams.setComparator(comparator);
	}

	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Sets the dropout limit.
	 *
	 * @param dropoutsLimit the new dropouts limit
	 */
	public void setDropoutsLimit(int dropoutsLimit) {
		this.dropoutsLimit = dropoutsLimit;
	}

	/**
	 * Sets the pilots limit.
	 *
	 * @param pilotsLimit the new pilots limit
	 */
	public void setPilotsLimit(int pilotsLimit) {
		this.pilotsLimit = pilotsLimit;
	}

	/**
	 * Gets the circuits.
	 *
	 * @return the circuits
	 */
	public List<Circuit> getCircuits() {
		return circuits;
	}

}
