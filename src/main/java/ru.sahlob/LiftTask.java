package ru.sahlob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiftTask {

    private int floor, timeUp, timeDown, timeLift;

    public static void main(String[] args) {
        int floor = 6;
        int timeUp = 20;
        int timeDown = 10;
        int timeLift = 5;
        String result = new LiftTask().getMinTime(floor, timeUp, timeDown, timeLift);
        System.out.println(result);
    }

    /**
     * Method returns the minimum time for all people to be in their seats.
     *
     * First, using the floorElevator() method,
     * we find the floor where the Elevator should ideally stop.
     * The floor value must be equal.
     *
     * If the floor value is an integer, we calculate the max time.
     * If the floor value is not an integer, then we will remove the minimum value from the two nearest integer floors.
     * @param floor number of floors
     * @param timeUp Time it takes people to walk up.
     * @param timeDown Time it takes people to walk down.
     * @param timeLift Time it takes people to take lift.
     */
    private String getMinTime(int floor, int timeUp, int timeDown, int timeLift) {
        this.floor = floor;
        this.timeUp = timeUp;
        this.timeDown = timeDown;
        this.timeLift = timeLift;

        int resultTime;
        double floorElevator = floorElevator();

        if (0 == (floorElevator % 1)) {
            resultTime = timeUp * footFloor((int) floorElevator);
        } else {
            List<Integer> results = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                int x = (int) floorElevator + i;
                int y = footFloor(x);
                results.add(getMaxValue(y, x));
            }
            resultTime = Collections.min(results);
        }


        return getTimeInMinutesAndSeconds(resultTime);
    }

    /**
     * In an ideal situation, the time to take people to the extreme points is equal.
     *   Extreme points are defined as:
     * - the maximum floor that people reached on foot without using the Elevator.
     * - ascent to the maximum floor using the Elevator and stairs.
     * - take the Elevator, then descend to the desired floor.
     * x - the floor to which the Elevator ascends.
     * y - the maximum floor that people reached on foot without using the Elevator.
     *
     * therefore:
     * timeUp * y = timeLift * x + timeDown * (x - y - 1) = timeLift * x + timeUp * (floor - x)
     *
     * from here we get a system of two equations with two unknowns:
     * y = ((timeLift + timeDown) * x) / (timeUp + timeDown)
     * y = (timeLift * x + timeUp * (floor - x)) / timeUp
     *
     *simplifying the equation, get:
     * x = (floor * timeUp * (timeDown + timeUp)) / (timeUp ^ 2 + 2 * timeUp * timeDown - timeDown * timeLift)
     */
    private double floorElevator() {
        return (double) (floor * timeUp * (timeDown + timeUp))
               / (Math.pow(timeUp, 2) + 2 * timeUp * timeDown - timeDown * timeLift);
    }

    /**
     * Calculating floor that people walk up.
     * @param floorElevator floor that the Elevator goes up.
     */
    private int footFloor(int floorElevator) {
        return ((timeLift + timeDown) * floorElevator) / (timeUp + timeDown);
    }

    /**
     * Calculating the maximum time in seconds that people need to get to their floor.
     * @param y floor that people walk up.
     * @param x floor that the Elevator goes up.
     */
    private int getMaxValue(int y, int x) {
        return Collections.max(Arrays.asList(
                timeUp * y,
                timeLift * x + timeDown * (x - y - 1),
                timeLift * x + timeUp * (floor - x)));
    }


    /**
     * Converts seconds to minutes and seconds.
     * @param secondsTime time in seconds.
     */
    private String getTimeInMinutesAndSeconds(int secondsTime) {
        String resultTime = "";
        int min = secondsTime / 60;
        int sec = secondsTime - (min * 60);

        if (min != 0) {
            resultTime += min + " min";
        }
        if (sec != 0) {
            if (min != 0) {
                resultTime += ", ";
            }
            resultTime += sec + " sec.";
        }
        return resultTime;
    }
}
