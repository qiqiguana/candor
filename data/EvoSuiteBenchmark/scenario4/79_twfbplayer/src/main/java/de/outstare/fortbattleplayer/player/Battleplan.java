package de.outstare.fortbattleplayer.player;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * A plan which determines each step of the battle. A battle is divided into
 * {@link Round}s.
 *
 * @author daniel
 */
public class Battleplan {

    private static final transient Logger LOG = Logger.getLogger(Battleplan.class.getName());

    private final SortedMap<Integer, Round> rounds = new TreeMap<Integer, Round>();

    /**
     * Create a new plan with the given rounds
     *
     * @param roundsOfPlan
     */
    public Battleplan(final Collection<Round> roundsOfPlan) {
    }

    /**
     * @param roundsOfPlan
     */
    private void mapRoundsToNumbers(final Collection<Round> roundsOfPlan);

    /**
     * @return the number of rounds this plan has
     */
    public int numberOfRounds();

    /**
     * executes the given {@link Round} of this plan
     *
     * @param roundNo
     *            0 <= roundNo <= numberOfRounds()
     * @param config
     */
    public void executeRound(final int roundNo, final PlayerConfiguration config);

    /**
     * @param roundNo
     * @return
     * @throws IllegalArgumentException
     */
    private Round getRound(final int roundNo) throws IllegalArgumentException;

    /**
     * @param no
     * @return <code>true</code> if this plan contains a round with the given
     *         number
     */
    public boolean hasRoundNo(final int no);

    /**
     * @param roundNo
     */
    public void resetToRound(final int roundNo);

    /**
     * @param roundNo
     * @return <code>true</code> if more rounds follow after the given round
     *         number.
     */
    public boolean hasMoreRounds(final int roundNo);

    /**
     * @require hasMoreRounds(currentRoundNo)
     * @param currentRoundNo
     * @return the number of the following round
     */
    public int getNextRound(final int currentRoundNo);

    /**
     * @param currentRoundNo
     * @return all rounds that follow after the given round number
     */
    private SortedMap<Integer, Round> getRemainingRounds(final int currentRoundNo);
}
