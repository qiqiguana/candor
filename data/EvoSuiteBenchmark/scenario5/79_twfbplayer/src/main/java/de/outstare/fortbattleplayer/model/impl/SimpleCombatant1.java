package de.outstare.fortbattleplayer.model.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;
import de.outstare.fortbattleplayer.model.SectorBonus;
import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.WeaponData;

/**
 * A Combatant with basic attributes (position, health)
 *
 * @author daniel
 */
public class SimpleCombatant implements Combatant {

    /**
     * internal method for graphical display
     *
     * @return the current health
     */
    public int _getCurrentLP() {
        return state.getHealth();
    }
}

/**
 * The state of a {@link Combatant}. An instance is immutable, so all modifying
 * methods return a new state.
 *
 * FIXME this class should not be public
 *
 * @author daniel
 */
public class CombatantState {

    /**
     * @return the health
     */
    public int getHealth();
}
