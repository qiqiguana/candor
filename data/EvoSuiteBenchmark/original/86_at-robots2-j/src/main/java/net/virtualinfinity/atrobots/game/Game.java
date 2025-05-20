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
        this(totalRounds, new FrameBuilder());
    }

    public Game(int totalRounds, FrameBuilder frameBuffer) {
        this.totalRounds = totalRounds;
        this.frameBuffer = frameBuffer;
    }

    /**
     * Get the current round.
     *
     * @return the current round.
     */
    public synchronized Round getRound() {
        return round;
    }

    /**
     * Get the total number of rounds.
     *
     * @return the total number of rounds.
     */
    public synchronized int getTotalRounds() {
        return totalRounds;
    }

    /**
     * Start the next round. This ends the current round.
     */
    public synchronized void nextRound() {
        if (round != null) {
            round.finalizeRound();
        }

        if (roundNumber < getTotalRounds()) {
            roundState = new StandardRoundState(totalRounds, ++roundNumber);
            round = new Round(frameBuffer);
            round.addRoundListener(this);
            for (int i = 0, entrantsSize = entrants.size(); i < entrantsSize; i++) {
                round.getArena().addRobot(createRobotFor(entrants.get(i), i));
            }
            round.getArena().buildFrame();
        } else {
            round = null;
            gameOver();
        }
    }

    private void gameOver() {

    }

    /**
     * Create a robot for the given entrant.
     *
     * @param entrant the entrant
     * @param id      the id for the robot.
     * @return the robot.
     */
    protected Robot createRobotFor(RobotFactory entrant, int id) {
        return entrant.createRobot(roundState, getMaxProcessorSpeed(), getScoreKeeper(entrant), round.getArena(), id);
    }

    private RobotScoreKeeper getScoreKeeper(RobotFactory entrant) {
        RobotScoreKeeper robotScoreKeeper = scoreKeepers.get(entrant);
        if (robotScoreKeeper == null) {
            robotScoreKeeper = new RobotScoreKeeper();
            scoreKeepers.put(entrant, robotScoreKeeper);
        }
        return robotScoreKeeper;
    }

    private RobotScore getFinalRobotScore(RobotFactory entrant) {
        final RobotScoreKeeper robotScoreKeeper = scoreKeepers.get(entrant);
        if (robotScoreKeeper == null) {
            throw new IllegalArgumentException("Entrant did not participate in this game, so you can't get its final score.");
        }
        return FinalRobotScore.copyOf(getScoreKeeper(entrant));
    }

    public GameResult getFinalResults() {
        List<RobotGameResult> results = new ArrayList<RobotGameResult>();
        for (Map.Entry<RobotFactory, RobotScoreKeeper> robots : scoreKeepers.entrySet()) {
            results.add(new RobotGameResult(robots.getKey(), FinalRobotScore.copyOf(robots.getValue())));
        }
        Collections.sort(results);
        return new GameResult(results);
    }

    /**
     * Add an observer.
     *
     * @param observer the observer to add.
     */
    public synchronized void addSimulationObserver(SimulationObserver observer) {
        frameBuffer.addSimulationObserver(observer);
    }

    /**
     * Remove an observer.
     *
     * @param observer the observer to remove.
     */
    public synchronized void removeSimulationObserver(SimulationObserver observer) {
        frameBuffer.removeSimulationObserver(observer);
    }

    /**
     * Add an entrant for the next round.
     *
     * @param entrant the entrant
     */
    public synchronized void addEntrant(RobotFactory entrant) {
        entrants.add(entrant);
    }

    /**
     * Execute one step in the simulation.
     */
    public synchronized boolean stepRound() {
        if (round == null) {
            return false;
        }
        getRound().step();
        return round != null;
    }

    public void roundOver() {
        nextRound();
    }

    public int getMaxProcessorSpeed() {
        return maxProcessorSpeed;
    }

    public void setMaxProcessorSpeed(int maxProcessorSpeed) {
        this.maxProcessorSpeed = maxProcessorSpeed;
    }

}
