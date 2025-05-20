package net.virtualinfinity.atrobots.game;

import net.virtualinfinity.atrobots.arena.FrameBuilder;
import net.virtualinfinity.atrobots.arena.RoundState;
import net.virtualinfinity.atrobots.arena.SimulationObserver;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.robot.FinalRobotScore;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.robot.RobotScore;
import net.virtualinfinity.atrobots.robot.RobotScoreKeeper;
import java.util.*;

/**
 * This class coordinates rounds, entrants, and the simulation frame buffer.
 *
 * @author Daniel Pitts
 */
public class Game implements RoundListener {

    private RoundState roundState;

    private Round round;

    private int roundNumber = 0;

    private int totalRounds;

    private int maxProcessorSpeed = 5;

    private final FrameBuilder frameBuffer;

    private final List<RobotFactory> entrants = Collections.synchronizedList(new ArrayList<RobotFactory>());

    private int nextEntrantId;

    private final Map<RobotFactory, RobotScoreKeeper> scoreKeepers = new IdentityHashMap<RobotFactory, RobotScoreKeeper>();

    public Game(int totalRounds) {
    }

    public Game(int totalRounds, FrameBuilder frameBuffer) {
    }

    /**
     * Get the current round.
     *
     * @return the current round.
     */
    public synchronized Round getRound();

    /**
     * Get the total number of rounds.
     *
     * @return the total number of rounds.
     */
    public synchronized int getTotalRounds();

    /**
     * Start the next round. This ends the current round.
     */
    public synchronized void nextRound();

    private void gameOver();

    /**
     * Create a robot for the given entrant.
     *
     * @param entrant the entrant
     * @param id      the id for the robot.
     * @return the robot.
     */
    protected Robot createRobotFor(RobotFactory entrant, int id);

    private RobotScoreKeeper getScoreKeeper(RobotFactory entrant);

    private RobotScore getFinalRobotScore(RobotFactory entrant);

    public GameResult getFinalResults();

    /**
     * Add an observer.
     *
     * @param observer the observer to add.
     */
    public synchronized void addSimulationObserver(SimulationObserver observer);

    /**
     * Remove an observer.
     *
     * @param observer the observer to remove.
     */
    public synchronized void removeSimulationObserver(SimulationObserver observer);

    /**
     * Add an entrant for the next round.
     *
     * @param entrant the entrant
     */
    public synchronized void addEntrant(RobotFactory entrant);

    /**
     * Execute one step in the simulation.
     */
    public synchronized boolean stepRound();

    public void roundOver();

    public int getMaxProcessorSpeed();

    public void setMaxProcessorSpeed(int maxProcessorSpeed);
}
