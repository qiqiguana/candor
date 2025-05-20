package net.virtualinfinity.atrobots.hardware.missiles;

import net.virtualinfinity.atrobots.ArenaObjectVisitor;
import net.virtualinfinity.atrobots.arena.LinearDamageFunction;
import net.virtualinfinity.atrobots.arena.Position;
import net.virtualinfinity.atrobots.arena.Speed;
import net.virtualinfinity.atrobots.arena.TangibleArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.CollidableArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.DamageInflicter;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.Duration;
import net.virtualinfinity.atrobots.measures.Vector;
import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;
import net.virtualinfinity.atrobots.snapshots.MissileSnapshot;

/**
 * An explosive projectile arena object.
 *
 * @author Daniel Pitts
 */
public class Missile extends CollidableArenaObject {
    private final DamageInflicter damageInflicter;
    private final double power;
    private final boolean overburn;
    private Duration age = Duration.ZERO_CYCLE;

    public Missile(DamageInflicter damageInflicter, Position position, AbsoluteAngle angle, double power, boolean overburn) {
        this.damageInflicter = damageInflicter;
        this.power = power;
        this.position.copyFrom(position);
        this.heading.setAngle(angle);
        this.overburn = overburn;
        getSpeed().setDistanceOverTime((32) * (power), Duration.ONE_CYCLE);
    }

    protected ArenaObjectSnapshot createSpecificSnapshot() {
        return new MissileSnapshot(overburn, age);
    }

    /**
     * Get the speed of this missile.
     *
     * @return the speed
     */
    public Speed getSpeed() {
        return speed;
    }


    /**
     * See if this robot collides with this missile.
     *
     * @param arenaObject the robot to check collision against.
     */
    @Override
    public void checkCollision(TangibleArenaObject arenaObject) {
        if (arenaObject == this.damageInflicter || isDead()) {
            return;
        }
        final Vector collisionPoint = getCollisionPoint(arenaObject);
        if (collisionPoint != null) {
            if (collisionPoint.minus(arenaObject.getPosition().getVector()).getMagnitudeSquared() < 196) {
                position.copyFrom(new Position(collisionPoint));
                explode();
            }
        }
    }

    private Vector getCollisionPoint(ArenaObject arenaObject) {
        return arenaObject.getPosition().getVector().perpendicularIntersectionFrom(getPosition().getVector(), heading.getAngle(), speed.times(Duration.ONE_CYCLE));
    }


    private void explode() {
        getArena().explosion(this.damageInflicter, new LinearDamageFunction(position, power, 14));
        die();
    }

    @Override
    public void update(Duration duration) {
        super.update(duration);
        age = age.plus(duration);
        if (!isDead()) {
            checkWallCollision();
        }
    }

    private void checkWallCollision() {
        if (isOutsideArena()) {
            position.move(getWallIntersectionDelta());
            explode();
        }
    }

    private Vector getWallIntersectionDelta() {
        final double x = -position.getX();
        final double y = -position.getY();
        final Vector vector = heading.getAngle().toUnitVector();
        if (position.getX() <= 0) {
            return vector.times(x / (vector.getX()));
        }
        if (position.getX() >= 1000) {
            return vector.times((1000 + x) / (vector.getX()));
        }
        if (position.getY() <= 0) {
            return vector.times(y / (vector.getY()));
        }
        if (position.getY() >= 1000) {
            return vector.times((1000 + y) / (vector.getY()));
        }
        throw new IllegalStateException("Should be outside arena.");

    }

    private boolean isOutsideArena() {
        return position.getX() < 0 || position.getX() > 1000
                || position.getY() < 0 || position.getY() > 1000;
    }

    public void accept(ArenaObjectVisitor arenaObjectVisitor) {
        arenaObjectVisitor.visit(this);
    }
}
