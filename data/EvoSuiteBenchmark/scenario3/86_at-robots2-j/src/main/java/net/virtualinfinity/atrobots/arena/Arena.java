package net.virtualinfinity.atrobots.arena;

import net.virtualinfinity.atrobots.ArenaObjectVisitor;
import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.CollidableArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.DamageInflicter;
import net.virtualinfinity.atrobots.measures.Duration;
import net.virtualinfinity.atrobots.radio.RadioDispatcher;
import java.util.*;

/**
 * The Arena is the virtual world within which the simulation occurs.
 *
 * @author Daniel Pitts
 */
public class Arena {

    /**
     * Get the number of robots still active in the arena.
     *
     * @return the number of robots still active in the arena.
     */
    public int countActiveRobots();
}
