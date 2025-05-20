package net.virtualinfinity.atrobots.arena;

import net.virtualinfinity.atrobots.ArenaObjectVisitor;
import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.CollidableArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.DamageInflicter;
import net.virtualinfinity.atrobots.measures.Duration;
import net.virtualinfinity.atrobots.radio.RadioDispatcher;

import java.util.*;

/**
 * The Arena is the virtual world within which the simulation occurs.
 *
 * @author Daniel Pitts
 */
public class Arena {
    private final List<TangibleArenaObject> activeRobots = new LinkedList<TangibleArenaObject>();
    private final List<TangibleArenaObject> allRobots = new LinkedList<TangibleArenaObject>();
    private final List<CollidableArenaObject> collidables = new LinkedList<CollidableArenaObject>();
    private final Collection<ArenaObject> intangibles = new LinkedList<ArenaObject>();
    private final RoundTimer roundTimer = new RoundTimer();

    @SuppressWarnings({"unchecked"})
    final Collection<Collection<? extends ArenaObject>> allActiveObjects = new ArrayList<Collection<? extends ArenaObject>>(
            Arrays.asList(collidables, activeRobots, intangibles)
    );

    @SuppressWarnings({"unchecked"})
    final Collection<Collection<? extends ArenaObject>> allFramedObjects = new ArrayList<Collection<? extends ArenaObject>>(
            Arrays.asList(collidables, intangibles, allRobots)
    );

    @SuppressWarnings({"unchecked"})
    final Collection<Collection<? extends CollidableArenaObject>> allCollidable = new ArrayList<Collection<? extends CollidableArenaObject>>(
            Arrays.asList(collidables, activeRobots)
    );


    private final RadioDispatcher radioDispatcher = new RadioDispatcher();
    private final FrameBuilder frameBuilder;
    private boolean roundOver;

    public Arena() {
        this(null);
    }

    public Arena(FrameBuilder frameBuilder) {
        this.frameBuilder = frameBuilder;
        if (frameBuilder != null) {

        }
    }


    /**
     * Get the number of robots still active in the arena.
     *
     * @return the number of robots still active in the arena.
     */
    public int countActiveRobots() {
        return activeRobots.size();
    }

    private void connectArena(ArenaObject object) {
        object.setArena(this);
    }

    /**
     * Get the radio dispatcher for this arena.
     *
     * @return the radio dispatcher for this arena.
     */
    public RadioDispatcher getRadioDispatcher() {
        return radioDispatcher;
    }

    /**
     * Simulate a certain amount of time elapsing.
     */
    public void simulate() {
        updateSimulation();
        buildFrame();
        roundTimer.increment(Duration.ONE_CYCLE);
    }

    /**
     * Prepare a snapshot of the current arena state in the {@link FrameBuilder}.
     */
    public void buildFrame() {
        if (frameBuilder != null) {
            frameBuilder.beginFrame(roundOver);
            for (Collection<? extends ArenaObject> objectCollection : allFramedObjects) {
                for (ArenaObject object : objectCollection) {
                    frameBuilder.addObject(object.getSnapshot());
                }
            }
            frameBuilder.endFrame();
        }
    }

    private void updateSimulation() {
        for (Collection<? extends ArenaObject> objectCollection : allActiveObjects) {
            for (ArenaObject object : objectCollection) {
                object.update(Duration.ONE_CYCLE);
            }
        }
        checkCollissions();
        removeDead();
    }

    private void removeDead() {
        for (Collection<? extends ArenaObject> objectCollection : allActiveObjects) {
            for (Iterator<? extends ArenaObject> it = objectCollection.iterator(); it.hasNext(); ) {
                if (it.next().isDead()) {
                    it.remove();
                }
            }
        }
    }

    private void checkCollissions() {
        for (final TangibleArenaObject collisionTarget : activeRobots) {
            for (Iterable<? extends CollidableArenaObject> toCheckAgainst : allCollidable) {
                for (CollidableArenaObject collidable : toCheckAgainst) {
                    if (collidable == collisionTarget) {
                        break;
                    }
                    collidable.checkCollision(collisionTarget);
                }
            }
        }
    }

    /**
     * Add a robot to the arena at a random location.
     *
     * @param robot the robot to add to this arena.
     */
    public void addRobot(TangibleArenaObject robot) {
        robot.getPosition().copyFrom(Position.random(0.0, 0.0, 1000.0, 1000.0));
        connectArena(robot);
        activeRobots.add(robot);
        allRobots.add(robot);
    }

    public void addCollidable(CollidableArenaObject arenaObject) {
        connectArena(arenaObject);
        collidables.add(arenaObject);
    }

    /**
     * Cause an explosion.
     *
     * @param cause             the robot which gets credit for any damage done.
     * @param explosionFunction the damage explosion function.
     */
    public void explosion(DamageInflicter cause, ExplosionFunction explosionFunction) {
        addIntangible(new Explosion(explosionFunction.getCenter(), explosionFunction.getRadius()));
        for (TangibleArenaObject robot : activeRobots) {
            explosionFunction.inflictDamage(cause, robot);
        }
    }

    public void determineWinners() {
        if (!activeRobots.isEmpty()) {
            if (activeRobots.size() == 1) {
                for (TangibleArenaObject robot : activeRobots) {
                    robot.winRound();
                }
            } else {
                for (TangibleArenaObject robot : activeRobots) {
                    robot.tieRound();
                }
            }
        } else {
            for (TangibleArenaObject robot : allRobots) {
                robot.tieRound();
            }
        }
    }

    public void endRound() {
        roundOver = true;
        determineWinners();
        buildFrame();
    }


    public boolean isOnlyOneRobotAlive() {
        return countActiveRobots() == 1;
    }

    public void addIntangible(ArenaObject object) {
        if (frameBuilder != null) {
            intangibles.add(object);
        }
    }

    public void visitActiveRobots(ArenaObjectVisitor arenaObjectVisitor) {
        for (ArenaObject arenaObject : activeRobots) {
            arenaObject.accept(arenaObjectVisitor);
        }
    }

    public RoundTimer getRoundTimer() {
        return roundTimer;
    }
}
