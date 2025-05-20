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

    public int checkForWinner() {
        if (players.size() < 2) {
            // 0 means it's a tie (all remaining players got
            int winnerId = 0;
            // killed by the same bomb)
            if (players.size() == 1) {
                // only the winner is left
                winnerId = players.get(0).getId();
            }
            return winnerId;
        } else {
            // game still running
            return -1;
        }
    }
}
