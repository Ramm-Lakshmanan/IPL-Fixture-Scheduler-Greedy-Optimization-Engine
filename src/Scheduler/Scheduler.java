package Scheduler;

import Models.Calendar;
import Models.Match;
import Models.Team;

public class Scheduler {

    private final Match[] matches;
    private final Calendar day;
    private final boolean[] scheduled;
    private final int[][] distanceMatrix;

    public Scheduler(
            Calendar day,
            Match[] matches,
            boolean[] scheduled,
            int[][] distanceMatrix) {

        this.day = day;
        this.matches = matches;
        this.scheduled = scheduled;
        this.distanceMatrix = distanceMatrix;
    }

    public void generateSchedule() {

        for (int slot = 0; slot < day.getSlots(); slot++) {

            CostCalculator calculator =
                    new CostCalculator(
                            matches,
                            scheduled,
                            day,
                            distanceMatrix
                    );

            Match match = calculator.getMinCost();

            // Leave this slot empty if no valid match exists.
            if (match == null) {
                continue;
            }

            day.addMatch(slot, match);

            scheduled[match.getMatchId()] = true;

            updateTeams(match);

            match.getVenue().bookDay(day.getDay());
        }
    }

    /**
     * Updates all scheduling information for both teams.
     */
    private void updateTeams(Match match) {

        Team home = match.getHomeTeam();
        Team away = match.getAwayTeam();

        // Travel
        home.addTravel(
                distanceMatrix[
                        home.getLastVenue().getVenueId()
                        ][
                        match.getVenue().getVenueId()
                        ]
        );

        away.addTravel(
                distanceMatrix[
                        away.getLastVenue().getVenueId()
                        ][
                        match.getVenue().getVenueId()
                        ]
        );

        // Last played day
        home.setLastPlayedDay(day.getDay());
        away.setLastPlayedDay(day.getDay());

        // Last venue
        home.setLastVenue(match.getVenue());
        away.setLastVenue(match.getVenue());

        // Home/Away streaks
        home.setHomeStreak(home.getHomeStreak() + 1);
        home.setAwayStreak(0);

        away.setAwayStreak(away.getAwayStreak() + 1);
        away.setHomeStreak(0);
    }
}