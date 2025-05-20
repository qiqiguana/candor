package net.virtualinfinity.atrobots.arenaobjects;

import net.virtualinfinity.atrobots.ArenaObjectVisitor;
import net.virtualinfinity.atrobots.arena.*;
import net.virtualinfinity.atrobots.measures.Duration;
import net.virtualinfinity.atrobots.measures.Vector;
import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;

/**
 * Abstract base class for objects which can appear in the {@link net.virtualinfinity.atrobots.arena.Arena}.
 *
 * @author Daniel Pitts
 */
public abstract class ArenaObject {
    protected final Heading heading = new Heading();
    protected final Speed speed = new Speed();
    protected final Velocity velocity = new Velocity(heading, speed);
    protected final Position position;
    private Arena arena;
    private boolean dead;

    protected ArenaObject() {
        position = new Position();
    }

    public ArenaObject(Vector location) {
        position = new Position(location);
    }

    /**
     * Update the position based on velocity.
     *
     * @param duration the amount of time to simulate movement for.
     */
    public final void simulateMovement(Duration duration) {
        position.move(velocity.times(duration));
    }

    /**
     * Get the position.
     *
     * @return the position.
     */
    public final Position getPosition() {
        return position;
    }

    /**
     * Check if this ArenaObject is dead.
     *
     * @return true if this ArenaObject is dead
     */
    public final boolean isDead() {
        return dead;
    }


    /**
     * Mark this ArenaObject as dead.
     */
    protected final void die() {
        this.dead = true;
    }

    public void update(Duration duration) {
        simulateMovement(duration);
    }

    /**
     * Get a snapshot of the current state of this ArenaObject.
     *
     * @return the current snapshot.
     * @see net.virtualinfinity.atrobots.arena.FrameBuilder
     * @see Arena#buildFrame()
     */
    public final ArenaObjectSnapshot getSnapshot() {
        final ArenaObjectSnapshot objectSnapshot = createSpecificSnapshot();
        objectSnapshot.setPositionVector(position.getVector());
        objectSnapshot.setVelocityVector(velocity.times(Duration.ONE_CYCLE));
        objectSnapshot.setDead(isDead());
        return objectSnapshot;
    }

    /**
     * Create the snapshot with type-specific information.
     *
     * @return a type-specific snapshot.
     */
    protected abstract ArenaObjectSnapshot createSpecificSnapshot();

    /**
     * Get the arena that this object is in.
     *
     * @return the arena.
     */
    public final Arena getArena() {
        return arena;
    }

    /**
     * Set the arena that this object lives in.
     *
     * @param arena the arena.
     */
    public final void setArena(Arena arena) {
        this.arena = arena;
        arenaConnected(arena);
    }

    protected void arenaConnected(Arena arena) {
    }

    public abstract void accept(ArenaObjectVisitor arenaObjectVisitor);
}
