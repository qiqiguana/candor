package de.outstare.fortbattleplayer.player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import de.outstare.fortbattleplayer.Configuration;

/**
 * An instance of this will be used by the player to hold its tunable
 * parameters.
 *
 * @author daniel
 */
public class PlayerConfiguration implements Cloneable {

    /**
     * @return <code>true</code> if shooting lines should be displayed
     */
    public boolean showShootline();
}
