package common;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;
import messages.round.RoundTimeOverMsg;
import org.apache.log4j.Logger;

/**
 * @author Steffen, Andi, Bj�rn
 */
public class GameModel {

    protected HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();

    protected Vector<Player> players = new Vector<Player>();

    protected long time = Constants.time;

    protected static final Logger logger = Logger.getLogger(GameModel.class);

    public void update();

    /**
     * Checks if the game is over and if yes returns the gameObjectId of the
     * winner.
     *
     * @return -1 if game still running, 0 in case of a tie, gameObjectId of the
     *         winner in all other cases
     */
    public int checkForWinner();

    public void addGameObject(int id, GameObject newGameObject);

    /**
     * @param id
     * @return
     */
    public GameObject getGameObjectById(int id);

    public void snapToGrid(Bomb bomb);

    /**
     * Returns the actual time of this GameModel.
     *
     * @return long - actual time
     */
    public long getTime();

    /**
     * Sets the time of this GameModel.
     *
     * @param time
     *            the time, which should be set
     */
    public void setTime(long time);

    public Vector<Player> getPlayers();
}
