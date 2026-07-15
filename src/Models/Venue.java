package Models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Venue {

    private final int venueId;
    private final String venueName;
    private final Set<Integer> bookedDays;

    public Venue(int venueId, String venueName) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.bookedDays = new HashSet<>();
    }

    public int getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public boolean isAvailable(int day) {
        return !bookedDays.contains(day);
    }

    public void bookDay(int day) {
        bookedDays.add(day);
    }

    public Set<Integer> getBookedDays() {
        return Collections.unmodifiableSet(bookedDays);
    }

    @Override
    public String toString() {
        return venueName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Venue))
            return false;

        Venue other = (Venue) obj;
        return venueId == other.venueId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(venueId);
    }
}