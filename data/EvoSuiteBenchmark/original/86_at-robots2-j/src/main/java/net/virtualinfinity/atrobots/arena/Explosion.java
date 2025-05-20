package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.ArenaObjectVisitor;
import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.measures.Duration;
import net.virtualinfinity.atrobots.measures.Vector;
import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;
import net.virtualinfinity.atrobots.snapshots.ExplosionSnapshot;

/**
 * Represents an explosion in the arena.
 *
 * @author Daniel Pitts
 */
public class Explosion extends ArenaObject {
    private Duration age = Duration.ZERO_CYCLE;
    private final double radius;

    /**
     * Create an explosing at the given
     *
     * @param center the center of the explosion.
     * @param radius the radius of the explosion.
     */
    public Explosion(Vector center, double radius) {
        super(center);
        this.radius = radius;
    }

    protected ArenaObjectSnapshot createSpecificSnapshot() {
        return new ExplosionSnapshot(radius, age);
    }

    @Override
    public void update(Duration duration) {
        if (!isDead()) {
            age = age.plus(duration);
            if (age.getCycles() > radius) {
                age = Duration.fromCycles((int) Math.rint(radius));
                die();
            }
        }
    }

    public void accept(ArenaObjectVisitor arenaObjectVisitor) {
        arenaObjectVisitor.visit(this);
    }
}
