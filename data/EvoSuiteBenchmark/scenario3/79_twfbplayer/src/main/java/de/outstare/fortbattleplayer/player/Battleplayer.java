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
    public int numberOfRounds();
}
