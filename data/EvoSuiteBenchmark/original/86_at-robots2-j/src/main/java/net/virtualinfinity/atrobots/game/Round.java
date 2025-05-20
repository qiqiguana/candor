package net.virtualinfinity.atrobots.game;

import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.arena.FrameBuilder;
import net.virtualinfinity.atrobots.arena.RoundTimer;
import net.virtualinfinity.atrobots.measures.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Document
 *
 * @author Daniel Pitts
 */
public class Round {
    private Duration roundEnd = Duration.fromCycles(10);
    private Duration maxCycles = Duration.fromCycles(25000);
    private final Arena arena;
    private final RoundTimer roundTimer;
    private final List<RoundListener> roundListeners = new ArrayList<RoundListener>();
    private boolean roundOver;

    public Round(FrameBuilder frameBuffer) {
        arena = new Arena(frameBuffer);
        roundTimer = arena.getRoundTimer();
    }

    public void addRoundListener(RoundListener roundListener) {
        roundListeners.add(roundListener);
    }

    public Arena getArena() {
        return arena;
    }

    public boolean step() {
        if (!roundOver) {
            arena.simulate();
            checkEndCondition();
            if (roundOver) {
                for (RoundListener roundListener : roundListeners) {
                    roundListener.roundOver();
                }
            }
        }
        return roundOver;

    }

    private void checkEndCondition() {
        if (arena.isOnlyOneRobotAlive()) {
            roundEnd = roundEnd.minus(Duration.ONE_CYCLE);
            if (roundEnd.getCycles() == 0) {
                roundOver = true;
            }
        }

        if (isArenaEmpty() || isRoundExpired()) {
            roundOver = true;
        }
    }

    private boolean isRoundExpired() {
        return roundTimer.getTime().compareTo(maxCycles) > 0;
    }

    private boolean isArenaEmpty() {
        return arena.countActiveRobots() == 0;
    }


    public void finalizeRound() {
        arena.endRound();
    }
}
