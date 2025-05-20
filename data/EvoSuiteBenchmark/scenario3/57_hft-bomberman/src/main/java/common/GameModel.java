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

    /**
     * Checks if the game is over and if yes returns the gameObjectId of the
     * winner.
     *
     * @return -1 if game still running, 0 in case of a tie, gameObjectId of the
     *         winner in all other cases
     */
    public int checkForWinner();
}
