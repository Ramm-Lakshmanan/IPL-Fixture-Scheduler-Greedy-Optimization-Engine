package Utils;

import Models.Team;
import Models.Venue;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DistanceMatrix {

    private final Team[] teams;
    private final Map<String, Integer> distanceMap;

    public DistanceMatrix(Team[] teams) {
        this.teams = teams;
        this.distanceMap = new HashMap<>();
    }

    private String getKey(Venue v1, Venue v2) {
        return v1.getVenueName() + "#" + v2.getVenueName();
    }

    public void inputDistances() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n========== DISTANCE MATRIX ==========\n");
        System.out.println("Enter distances only once.");
        System.out.println();

        for (int i = 0; i < teams.length; i++) {

            for (int j = i; j < teams.length; j++) {

                Venue v1 = teams[i].getHomeVenue();
                Venue v2 = teams[j].getHomeVenue();

                if (i == j) {

                    distanceMap.put(getKey(v1, v2), 0);

                } else {

                    System.out.print(v1.getVenueName() + " <-> "
                            + v2.getVenueName() + " : ");

                    int d = sc.nextInt();

                    distanceMap.put(getKey(v1, v2), d);
                    distanceMap.put(getKey(v2, v1), d);
                }
            }
        }
    }

    public int getDistance(Venue from, Venue to) {

        if (from == null || to == null)
            return 0;

        if (from.getVenueName().equals(to.getVenueName()))
            return 0;

        Integer d = distanceMap.get(getKey(from, to));

        return d == null ? 0 : d;
    }

    public void printMatrix() {

        System.out.println("\n========== DISTANCE MATRIX ==========\n");

        for (Team t1 : teams) {

            for (Team t2 : teams) {

                System.out.printf("%5d ",
                        getDistance(
                                t1.getHomeVenue(),
                                t2.getHomeVenue()
                        )
                );
            }

            System.out.println();
        }
    }
    public int[][] getDistanceMatrix() {

        int n = teams.length;

        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                matrix[i][j] = getDistance(
                        teams[i].getHomeVenue(),
                        teams[j].getHomeVenue()
                );
            }
        }

        return matrix;
    }
}