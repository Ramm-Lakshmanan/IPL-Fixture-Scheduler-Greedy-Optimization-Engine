package Scheduler;

import Models.Calendar;
import Models.Match;
import Models.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConstraintValidator {

    private static final int MIN_REST_DAYS = 1;
    private static final int MAX_STREAK = 3;

    private final Match[] matches;
    private final boolean[] scheduled;

    public ConstraintValidator(Match[] matches, boolean[] scheduled) {
        this.matches = matches;
        this.scheduled = scheduled;
    }

    public List<Match> getCandidateMatches(Calendar day) {

        List<Match> candidates = new ArrayList<>();
        Set<Team> alreadyPlayingToday = new HashSet<>();

        // Collect teams already assigned on this day
        for (Match match : day.getMatches()) {

            if (match == null)
                continue;

            alreadyPlayingToday.add(match.getHomeTeam());
            alreadyPlayingToday.add(match.getAwayTeam());
        }

        for (int i = 0; i < matches.length; i++) {

            if (scheduled[i])
                continue;

            Match match = matches[i];

            if (match == null)
                continue;

            Team home = match.getHomeTeam();
            Team away = match.getAwayTeam();

            // Team already has a match today
            if (alreadyPlayingToday.contains(home) || alreadyPlayingToday.contains(away))
                continue;

            // Minimum rest period
            if (day.getDay() - home.getLastPlayedDay() < MIN_REST_DAYS)
                continue;

            if (day.getDay() - away.getLastPlayedDay() < MIN_REST_DAYS)
                continue;

            // Venue occupied
            if (!match.getVenue().isAvailable(day.getDay()))
                continue;

//            Home/Away streak limit
            if (home.getHomeStreak() >= MAX_STREAK)
                continue;

            if (away.getAwayStreak() >= MAX_STREAK)
                continue;

            candidates.add(match);
        }

        return candidates;
    }
}