package Models;

import java.util.Objects;

public class Match {

    private final int matchId;
    private final Team homeTeam;
    private final Team awayTeam;
    private final Venue venue;

    public Match(int matchId, Team homeTeam, Team awayTeam, Venue venue) {

        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null.");
        }

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("A team cannot play against itself.");
        }

        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null.");
        }

        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = venue;
    }

    public int getMatchId() {
        return matchId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Venue getVenue() {
        return venue;
    }

    public boolean involvesTeam(Team team) {
        return homeTeam.equals(team) || awayTeam.equals(team);
    }

    @Override
    public String toString() {
        return String.format(
                "Match %02d : %s vs %s (%s)",
                matchId,
                homeTeam.getTeamName(),
                awayTeam.getTeamName(),
                venue.getVenueName()
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Match))
            return false;

        Match other = (Match) obj;

        return matchId == other.matchId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId);
    }
}