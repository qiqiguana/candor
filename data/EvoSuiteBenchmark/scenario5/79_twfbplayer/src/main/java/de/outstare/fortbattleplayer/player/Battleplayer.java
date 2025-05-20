package de.outstare.fortbattleplayer.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import de.outstare.fortbattleplayer.model.Combatant;

/**
 * A {@link Battleplayer} controls the battle. It is a puppet player controlling
 * the behavior of the {@link Combatant}s.
 *
 * @author daniel
 */
public class Battleplayer implements Player {

    /**
     * @return the number of rounds this player shows
     */
    public int numberOfRounds() {
        return plan.numberOfRounds();
    }
}

/**
 * A plan which determines each step of the battle. A battle is divided into
 * {@link Round}s.
 *
 * @author daniel
 */
public class Battleplan {

    /**
     * @return the number of rounds this plan has
     */
    public int numberOfRounds();
}
