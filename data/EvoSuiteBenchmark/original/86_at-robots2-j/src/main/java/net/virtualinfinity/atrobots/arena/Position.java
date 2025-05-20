package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.measures.Vector;

import java.util.Random;

/**
 * @author Daniel Pitts
 */
public class Position {
    private Vector vector;
    private Vector lastVector;
    private Odometer odometer;

    public Position() {
    }

    public Position(Vector vector) {
        this.vector = vector;
    }

    public double getX() {
        return vector.getX();
    }

    public double getY() {
        return vector.getY();
    }

    public void move(Vector delta) {
        if (odometer != null) {
            odometer.accumulate((delta.getMagnitude()));
        }
        lastVector = vector;
        vector = vector.plus(delta);
    }

    public void setOdometer(Odometer odometer) {
        this.odometer = odometer;
    }

    public void copyFrom(Position source) {
        vector = source.vector;
        lastVector = source.lastVector;
    }

    public Vector getVectorTo(Position position) {
        return Vector.createCartesian(getX() - (position.getX()), getY() - (position.getY()));
    }

    public Vector getVector() {
        return vector;
    }

    public static Position random(double lowerX, double lowerY, double higherX, double higherY) {
        final Position position = new Position();
        Random random = new Random();
        position.vector = Vector.createCartesian(
                (lowerX + random.nextDouble() * (higherX - lowerX)),
                (lowerY + random.nextDouble() * (higherY - lowerY)));
        position.lastVector = position.vector;
        return position;
    }
}
