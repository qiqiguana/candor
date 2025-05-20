package net.virtualinfinity.atrobots.arena;

/**
 * Keeps record or the current round
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface RoundState {

    /**
     * Get the total number of rounds in this "game".
     *
     * @return the total number of rounds
     */
    int getTotalRounds();

    /**
     * Get the current round, 1 based
     *
     * @return The current round number.
     */
    int getRoundNumber();
}
