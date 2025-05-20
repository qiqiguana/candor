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

    private static final transient Logger LOG = Logger.getLogger(SimpleCombatant.class.getName());

    private final int maxHealth;

    private final CombatantSide side;

    private final String name;

    private final String city;

    private final Set<CombatantObserver> observers = new HashSet<CombatantObserver>();

    private final Object stateChangeLock = new Object();

    private final CharacterClass charClass;

    private final Weapon weapon;

    private CombatantState state;

    private Combatant aimingAt = null;

    /**
     * Create a new {@link Combatant} at the given position and the given amount
     * of health.
     *
     * @param side
     *            not null
     * @param initialState
     *            not null
     * @param maxHealth
     *            > 0
     * @param name
     *            the name of the player of this Combatant
     * @param characterClass
     *            maybe null for old logs
     * @param weapon
     *            not null
     * @param city
     *            not null
     */
    public SimpleCombatant(final CombatantSide side, final CombatantState initialState, final int maxHealth, final String name, final CharacterClass characterClass, final Weapon weapon, final String city) {
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#getName()
     */
    public String getName();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#getWeapon()
     */
    public Weapon getWeapon();

    /**
     * internal method for graphical display
     *
     * @return the maximal health
     */
    public int _getMaxLP();

    /**
     * internal method for graphical display
     *
     * @return the current health
     */
    public int _getCurrentLP();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#move(de.outstare.fortbattleplayer.model.Area)
     */
    public void move(final Area target);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#shoot(de.outstare.fortbattleplayer.model.Area)
     */
    public void shoot(final int power);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#hit(int)
     */
    public boolean hit(final int damageAmount);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#aimAt(de.outstare.fortbattleplayer.model.Combatant)
     */
    public void aimAt(final Combatant target);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#setDestination(de.outstare.fortbattleplayer.model.Area)
     */
    public void setDestination(final Area destination);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#addObserver(de.outstare.fortbattleplayer.model.CombatantObserver)
     */
    public void addObserver(final CombatantObserver observer);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#removeObserver(de.outstare.fortbattleplayer.model.CombatantObserver)
     */
    public void removeObserver(final CombatantObserver observer);

    /**
     * notify all observers about the current position
     */
    protected void fireHasMoved();

    /**
     * notify all observers about the current position
     *
     * @param swappedWith
     */
    protected void fireHasSwapped(final Combatant swappedWith);

    /**
     * notify all observers about the current position
     */
    protected void fireNewTarget();

    /**
     * notify all observers that I shot at somebody.
     */
    protected void fireAimingAt();

    /**
     * notify all observers about a loss of health
     *
     * @param damage
     *            amount of healthpoints lost
     * @param healthBefore
     *            original health amount before it was decreased by
     *            <code>damage</code>
     */
    protected void fireWasHit(final int damage, final int healthBefore);

    /**
     * notify all observers about our dead
     */
    protected void fireIsDead();

    /**
     * notify all observers about our resurrection
     */
    protected void fireIsAlive();

    /**
     * notify all observers about changed online state
     *
     * @param changed
     *            if the value was changed or only set
     */
    protected void fireOnlineChange(final boolean changed);

    /**
     * notify all observers that i have done a critical shot
     *
     * @param damage
     *            the actual caused damage
     */
    protected void fireCriticalShot(final int damage);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#_getLocation()
     */
    public Area _getLocation();

    /**
     * @return the side
     */
    public CombatantSide getSide();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#_health()
     */
    public int _health();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#_maxHealth()
     */
    public int _maxHealth();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#_setState(de.outstare.fortbattleplayer.model.CombatantState)
     */
    public void _setState(final CombatantState newState);

    /**
     * @param position
     */
    private void moveAway(final Area position);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#_state()
     */
    public CombatantState _state();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#isOnline()
     */
    public boolean isOnline();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#setOnline()
     */
    public void setOnline(final boolean isOnline);

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#getCharacterClass()
     */
    public CharacterClass getCharacterClass();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#getSectorBonus()
     */
    public SectorBonus getSectorBonus();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#getCity()
     */
    public String getCity();

    /**
     * @see de.outstare.fortbattleplayer.model.Combatant#usesBayonet()
     */
    public boolean usesBayonet();

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode();

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj);
}
