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

    @SuppressWarnings({ "unchecked" })
    final Collection<Collection<? extends ArenaObject>> allActiveObjects = new ArrayList<Collection<? extends ArenaObject>>(Arrays.asList(collidables, activeRobots, intangibles));

    @SuppressWarnings({ "unchecked" })
    final Collection<Collection<? extends ArenaObject>> allFramedObjects = new ArrayList<Collection<? extends ArenaObject>>(Arrays.asList(collidables, intangibles, allRobots));

    @SuppressWarnings({ "unchecked" })
    final Collection<Collection<? extends CollidableArenaObject>> allCollidable = new ArrayList<Collection<? extends CollidableArenaObject>>(Arrays.asList(collidables, activeRobots));

    private final RadioDispatcher radioDispatcher = new RadioDispatcher();

    private final FrameBuilder frameBuilder;

    private boolean roundOver;

    public Arena() {
    }

    public Arena(FrameBuilder frameBuilder) {
    }

    /**
     * Get the number of robots still active in the arena.
     *
     * @return the number of robots still active in the arena.
     */
    public int countActiveRobots();

    private void connectArena(ArenaObject object);

    /**
     * Get the radio dispatcher for this arena.
     *
     * @return the radio dispatcher for this arena.
     */
    public RadioDispatcher getRadioDispatcher();

    /**
     * Simulate a certain amount of time elapsing.
     */
    public void simulate();

    /**
     * Prepare a snapshot of the current arena state in the {@link FrameBuilder}.
     */
    public void buildFrame();

    private void updateSimulation();

    private void removeDead();

    private void checkCollissions();

    /**
     * Add a robot to the arena at a random location.
     *
     * @param robot the robot to add to this arena.
     */
    public void addRobot(TangibleArenaObject robot);

    public void addCollidable(CollidableArenaObject arenaObject);

    /**
     * Cause an explosion.
     *
     * @param cause             the robot which gets credit for any damage done.
     * @param explosionFunction the damage explosion function.
     */
    public void explosion(DamageInflicter cause, ExplosionFunction explosionFunction);

    public void determineWinners();

    public void endRound();

    public boolean isOnlyOneRobotAlive();

    public void addIntangible(ArenaObject object);

    public void visitActiveRobots(ArenaObjectVisitor arenaObjectVisitor);

    public RoundTimer getRoundTimer();
}
