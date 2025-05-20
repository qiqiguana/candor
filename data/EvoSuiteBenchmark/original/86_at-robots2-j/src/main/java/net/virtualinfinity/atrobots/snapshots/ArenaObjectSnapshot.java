package net.virtualinfinity.atrobots.snapshots;

import net.virtualinfinity.atrobots.measures.Vector;

/**
 * @author Daniel Pitts
 */
public abstract class ArenaObjectSnapshot {
    private Vector positionVector;
    private Vector velocityVector;
    private boolean dead;

    public void setPositionVector(Vector positionVector) {
        this.positionVector = positionVector;
    }

    public void setVelocityVector(Vector velocityVector) {
        this.velocityVector = velocityVector;
    }

    public abstract void visit(SnapshotVisitor visitor);

    public double getY() {
        return getPositionVector().getY();
    }

    public double getX() {
        return getPositionVector().getX();
    }

    public double getVelocityX() {
        return getVelocityVector().getX();
    }

    public double getVelocityY() {
        return getVelocityVector().getY();
    }

    public Vector getPositionVector() {
        return positionVector;
    }

    public Vector getVelocityVector() {
        return velocityVector;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
