package Models;

import java.util.Arrays;
import java.util.Date;

public class Calendar {

    private final int tournamentDay;
    private final Date matchDate;
    private final int slots;
    private final Match[] matches;

    public Calendar(int tournamentDay, Date matchDate, int slots) {
        this.tournamentDay = tournamentDay;
        this.matchDate = matchDate;
        this.slots = slots;
        this.matches = new Match[slots];
    }

    public int getDay() {
        return tournamentDay;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public int getSlots() {
        return slots;
    }

    public Match[] getMatches() {
        return matches;
    }

    /**
     * Adds a match to the specified slot.
     *
     * @return true if successfully added.
     */
    public boolean addMatch(int slot, Match match) {

        if (slot < 0 || slot >= slots)
            return false;

        if (matches[slot] != null)
            return false;

        matches[slot] = match;
        return true;
    }

    /**
     * Returns true if the given slot has no match.
     */
    public boolean isSlotAvailable(int slot) {

        if (slot < 0 || slot >= slots)
            return false;

        return matches[slot] == null;
    }

    /**
     * Returns number of scheduled matches.
     */
    public int getScheduledMatches() {

        int count = 0;

        for (Match match : matches) {
            if (match != null)
                count++;
        }

        return count;
    }

    /**
     * Returns true if all slots are occupied.
     */
    public boolean isFull() {
        return getScheduledMatches() == slots;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n========== Day ")
                .append(tournamentDay)
                .append(" ==========\n");

        for (int i = 0; i < slots; i++) {

            sb.append("Slot ")
                    .append(i + 1)
                    .append(" : ");

            if (matches[i] == null) {
                sb.append("No Match");
            } else {
                sb.append(matches[i]);
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}