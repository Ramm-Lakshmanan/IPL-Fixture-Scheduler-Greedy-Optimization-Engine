import Models.Calendar;
import Models.Match;
import Models.Team;
import Models.Venue;
import Scheduler.Scheduler;
import Strategies.DoubleRoundRobin;
import Utils.DistanceMatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_DAYS = 70;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of teams: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Team[] teams = new Team[n];

        for (int i = 0; i < n; i++) {

            System.out.print("Team Name: ");
            String teamName = scanner.nextLine();

            System.out.print("Home Venue: ");
            String venueName = scanner.nextLine();

            teams[i] = new Team(
                    teamName,
                    new Venue(i, venueName)
            );
        }

        DistanceMatrix dm = new DistanceMatrix(teams);

        dm.inputDistances();

        int[][] distanceMatrix = dm.getDistanceMatrix();

        DoubleRoundRobin generator =
                new DoubleRoundRobin(teams);

        Match[] matches =
                generator.generateMatches();

        System.out.print("Tournament Start Date (dd/mm/yyyy): ");

        String[] parts = scanner.nextLine().split("/");

        Date start =
                new Date(
                        Integer.parseInt(parts[2]) - 1900,
                        Integer.parseInt(parts[1]) - 1,
                        Integer.parseInt(parts[0])
                );

        java.util.Calendar calendar =
                java.util.Calendar.getInstance();

        calendar.setTime(start);

        List<Calendar> fixtures = new ArrayList<>();

        boolean[] scheduled = new boolean[matches.length];

        int dayNumber = 1;

        while (allMatchesScheduled(scheduled) && dayNumber <= MAX_DAYS) {

            int slots = (dayNumber % 7 == 0) ? 2 : 1;

            Calendar day =
                    new Calendar(
                            dayNumber,
                            calendar.getTime(),
                            slots
                    );

            fixtures.add(day);

            Scheduler scheduler =
                    new Scheduler(
                            day,
                            matches,
                            scheduled,
                            distanceMatrix
                    );

            scheduler.generateSchedule();

            calendar.add(
                    java.util.Calendar.DAY_OF_MONTH,
                    1
            );

            dayNumber++;
        }

        System.out.println(
                "\n================ IPL FIXTURE ================\n"
        );

        int totalMatches = 0;

        for (Calendar day : fixtures) {

            System.out.println(
                    "Day "
                            + day.getDay()
                            + " ("
                            + day.getMatchDate()
                            + ")"
            );

            boolean hasMatch = false;

            for (Match match : day.getMatches()) {

                if (match == null)
                    continue;

                hasMatch = true;
                totalMatches++;

                System.out.println(
                        match.getHomeTeam().getTeamName()
                                + " vs "
                                + match.getAwayTeam().getTeamName()
                                + " @ "
                                + match.getVenue().getVenueName()
                );
            }

            if (!hasMatch) {
                System.out.println("---- Rest Day ----");
            }

            System.out.println();
        }

        System.out.println("--------------------------------");

        System.out.println(
                "Total Matches Scheduled : " + totalMatches
        );

        System.out.println(
                "Total Days Used : " + fixtures.size()
        );

        if (allMatchesScheduled(scheduled)) {

            int remaining = 0;

            for (boolean value : scheduled) {
                if (!value)
                    remaining++;
            }

            System.out.println();
            System.out.println(
                    "WARNING: Only "
                            + totalMatches
                            + " out of "
                            + matches.length
                            + " matches could be scheduled."
            );

            System.out.println(
                    "Remaining Matches : " + remaining
            );

            System.out.println(
                    "The constraints are too restrictive to complete the schedule."
            );
        }

        System.out.println(
                "\n========= Team Travel =========\n"
        );

        for (Team team : teams) {

            System.out.printf(
                    "%-20s : %d km%n",
                    team.getTeamName(),
                    team.getTotalTravel()
            );
        }

        scanner.close();
    }

    private static boolean allMatchesScheduled(boolean[] scheduled) {

        for (boolean value : scheduled) {
            if (!value)
                return true;
        }

        return false;
    }
}