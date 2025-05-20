package client;

import java.awt.Point;
import org.apache.log4j.Logger;
import sound.SoundPlayer;
import common.Actor;
import common.Constants;
import common.Player;

/**
 * The client specific implemetation of a Player. This implementation will
 * notify any number of registered observers about changes to their state.
 *
 * @author Andi, Bj�rn
 */
public class ClientPlayer extends Player {

    private boolean bowlBombs = false;

    private Logger logger = Logger.getLogger(ClientPlayer.class);

    private int bombsPlanted = 0;

    private int bombDiameter = Constants.BOMB_DIAMETER;

    private int maxBombs = Constants.MIN_BOMBS;

    private long lastPlant;

    /**
     * Creates a new ClientPlayer that starts on a fixed position.
     *
     * @param position
     *            The starting location for this player.
     */
    public ClientPlayer(Point position, int id) {
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see common.Actor#preUpdate()
	 */
    @Override
    protected void preUpdate();

    /**
     * Plants a bomb on the battlefield. The bomb will be located at the current
     * position. If the user can bowl bombs the bomb get a moveVector. This will
     * trigger a notification for the observers.
     */
    public void plantBomb();

    /*
	 * (non-Javadoc)
	 * 
	 * @see common.Player#die()
	 */
    @Override
    public void die();

    /**
     * Increases the maximum number of bombs this player can plant by one. If
     * the player can already plant MAX_BOMDS bombs, this method will do
     * nothing.
     */
    public void increaseMaxBombs();

    /**
     * Resets the maximum number of bombs this player can plant by one.
     */
    public void resetMaxBombs();

    /**
     * Notifies this player that a previously planted bomb has exploded. This
     * will decrease the count of currently planted bombs.
     */
    public void plantedBombExploded();

    /**
     * Increases the diameter of the bomb, that this player can plant.
     */
    public void increaseBombDiameter();

    /**
     * Resets the diameter of the bomb, that this player can plant.
     */
    public void resetBombDiameter();

    /**
     * Sets, that player can bowl bombs
     */
    public void setBowlBombs();

    /**
     * Sets, that player can't bowl bombs
     */
    public void resetBowlBombs();

    /**
     * Returns weather the player can bowl bombs
     *
     * @return boolean can bowl bombs
     */
    public boolean canBowlBombs();
}
