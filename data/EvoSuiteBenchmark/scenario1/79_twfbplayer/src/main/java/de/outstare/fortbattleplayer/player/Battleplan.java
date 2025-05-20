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

    public int numberOfRounds() {
        return rounds.size();
    }
}
