package Strategies;

import Models.Match;
import Models.Team;

public class DoubleRoundRobin {

    private final Team[] teams;
    private final int n;

    public DoubleRoundRobin(Team[] teams) {
        this.teams = teams.clone();   // Don't modify original array
        this.n = teams.length;
    }

    public Match[] generateMatches() {

        Match[] matches = new Match[n * (n - 1)];

        Team[] rotation = teams.clone();

        int matchId = 0;

        int rounds = n - 1;

        for (int round = 0; round < rounds; round++) {

            for (int i = 0; i < n / 2; i++) {

                Team home = rotation[i];
                Team away = rotation[n - 1 - i];

                // First leg
                matches[matchId] =
                        new Match(
                                matchId,
                                home,
                                away,
                                home.getHomeVenue()
                        );
                matchId++;

                // Second leg
                matches[matchId] =
                        new Match(
                                matchId,
                                away,
                                home,
                                away.getHomeVenue()
                        );
                matchId++;
            }

            rotate(rotation);
        }

        return matches;
    }

    /**
     * Standard circle-method rotation.
     * First team remains fixed.
     */
    private void rotate(Team[] arr) {

        Team last = arr[n - 1];

        for (int i = n - 1; i >= 2; i--) {
            arr[i] = arr[i - 1];
        }

        arr[1] = last;
    }
}