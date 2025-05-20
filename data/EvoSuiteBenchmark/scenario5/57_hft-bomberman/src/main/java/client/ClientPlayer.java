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

    /**
     * Returns weather the player can bowl bombs
     *
     * @return boolean can bowl bombs
     */
    public boolean canBowlBombs() {
        return bowlBombs;
    }
}
