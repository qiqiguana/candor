package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.computer.Resettable;

/**
 * @author Daniel Pitts
 */
public class Odometer implements Resettable {
    private double distance = (0);

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void accumulate(double distance) {
        this.distance += distance;
    }

    public void reset() {
        distance = (0);
    }

    public double getDistance() {
        return distance;
    }
}
