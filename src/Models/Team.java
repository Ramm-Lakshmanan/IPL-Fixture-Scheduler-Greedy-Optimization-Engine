package Models;

public class Team {

    private String teamName;
    private Venue homeVenue;

    // Scheduling state
    private int lastPlayedDay;
    private Venue lastVenue;

    private int homeStreak;
    private int awayStreak;

    private int totalTravel;

    public Team(String teamName, Venue homeVenue) {
        this.teamName = teamName;
        this.homeVenue = homeVenue;

        lastPlayedDay = -100;
        lastVenue = homeVenue;

        homeStreak = 0;
        awayStreak = 0;

        totalTravel = 0;
    }

    public String getTeamName() {
        return teamName;
    }

    public Venue getHomeVenue() {
        return homeVenue;
    }

    public int getLastPlayedDay() {
        return lastPlayedDay;
    }

    public void setLastPlayedDay(int lastPlayedDay) {
        this.lastPlayedDay = lastPlayedDay;
    }

    public Venue getLastVenue() {
        return lastVenue;
    }

    public void setLastVenue(Venue lastVenue) {
        this.lastVenue = lastVenue;
    }

    public int getHomeStreak() {
        return homeStreak;
    }

    public void setHomeStreak(int homeStreak) {
        this.homeStreak = homeStreak;
    }

    public int getAwayStreak() {
        return awayStreak;
    }

    public void setAwayStreak(int awayStreak) {
        this.awayStreak = awayStreak;
    }

    public int getTotalTravel() {
        return totalTravel;
    }

    public void addTravel(int distance) {
        totalTravel += distance;
    }
}