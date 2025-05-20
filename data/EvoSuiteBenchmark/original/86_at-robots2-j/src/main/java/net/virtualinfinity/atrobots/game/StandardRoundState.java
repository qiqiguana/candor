package net.virtualinfinity.atrobots.game;

import net.virtualinfinity.atrobots.arena.RoundState;

/**
 * A simple implementation of RoundState.
 */
public class StandardRoundState implements RoundState {
    private final int totalRounds;
    private final int roundNumber;

    /**
     * @param totalRounds the total number of rounds
     * @param roundNumber the current round number.
     */
    public StandardRoundState(int totalRounds, int roundNumber) {
        this.totalRounds = totalRounds;
        this.roundNumber = roundNumber;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
