package Scheduler;

import Models.Calendar;
import Models.Match;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CostCalculator {

    private final Match[] matches;
    private final boolean[] scheduled;
    private final Calendar day;
    private final int[][] distanceMatrix;
    private final ConstraintValidator validator;

    public CostCalculator(
            Match[] matches,
            boolean[] scheduled,
            Calendar day,
            int[][] distanceMatrix) {

        this.matches = matches;
        this.scheduled = scheduled;
        this.day = day;
        this.distanceMatrix = distanceMatrix;
        this.validator = new ConstraintValidator(matches, scheduled);
    }

    public Match getMinCost() {

        List<Match> validMatches = validator.getCandidateMatches(day);

        if (validMatches.isEmpty()) {
            return null;
        }

        PriorityQueue<MatchCost> pq =
                new PriorityQueue<>(Comparator.comparingInt(mc -> mc.cost));

        for (Match match : validMatches) {
            pq.offer(new MatchCost(match, calculateCost(match)));
        }

        return pq.poll().match;
    }

    private int calculateCost(Match match) {

        int cost = 0;

        cost += distanceMatrix[
                match.getHomeTeam()
                        .getLastVenue()
                        .getVenueId()
                ][
                match.getVenue()
                        .getVenueId()
                ];

        cost += distanceMatrix[
                match.getAwayTeam()
                        .getLastVenue()
                        .getVenueId()
                ][
                match.getVenue()
                        .getVenueId()
                ];

        int homeRest =
                day.getDay() - match.getHomeTeam().getLastPlayedDay();

        int awayRest =
                day.getDay() - match.getAwayTeam().getLastPlayedDay();

        // Prefer teams that have waited longer.
        cost -= (homeRest + awayRest);

        // Soft penalties instead of hard constraints

        if (match.getHomeTeam().getHomeStreak() >= 3)
            cost += 10000;

        if (match.getAwayTeam().getAwayStreak() >= 3)
            cost += 10000;

        return cost;
    }

    private static class MatchCost {

        final Match match;
        final int cost;

        MatchCost(Match match, int cost) {
            this.match = match;
            this.cost = cost;
        }
    }
}