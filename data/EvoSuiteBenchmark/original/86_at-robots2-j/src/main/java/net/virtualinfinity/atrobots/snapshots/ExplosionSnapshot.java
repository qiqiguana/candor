package net.virtualinfinity.atrobots.snapshots;


import net.virtualinfinity.atrobots.measures.Duration;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author Daniel Pitts
 */
public class ExplosionSnapshot extends ArenaObjectSnapshot {
    private final double radius;
    private final Duration age;

    public ExplosionSnapshot(double radius, Duration age) {
        this.radius = radius;
        this.age = age;
    }

    public Ellipse2D.Double getExplosionShape() {
        final Ellipse2D.Double circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(getPositionVector().toPoint2D(), new Point2D.Double(getPositionVector().getX() + getDegradedRadius(), getPositionVector().getY() + getDegradedRadius()));
        return circle;
    }

    private double getDegradedRadius() {
        return getRadius() - getAge().getCycles();
    }

    public void visit(SnapshotVisitor visitor) {
        visitor.acceptExplosion(this);
    }

    public double getRadius() {
        return radius;
    }

    public Duration getAge() {
        return age;
    }
}
