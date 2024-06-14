package com.team.comparator;

import java.util.Comparator;

import com.team.Team;

/**
 * Order the teams by total points, total races finished and name in ascendant
 * order.
 */
public class TeamComparatorByTotalPointsTotalRacesName implements Comparator<Team> {

	@Override
	public int compare(Team team1, Team team2) {
		int comparation = Integer.compare(team1.getTotalPoints(), team2.getTotalPoints());
		if (comparation == 0)
			comparation = Integer.compare(team1.getFinishedRaces(), team2.getFinishedRaces());
		if (comparation == 0)
			comparation = team1.getName().compareTo(team2.getName());
		return comparation;
	}
}
