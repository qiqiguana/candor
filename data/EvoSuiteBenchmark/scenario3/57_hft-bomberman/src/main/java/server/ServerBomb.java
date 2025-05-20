package server;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Vector;
import messages.round.BombExplodedMsg;
import org.apache.log4j.Logger;
import common.Bomb;
import common.Constants;
import common.GameObject;
import common.Player;
import common.PowerUp;
import common.Tile;
import db.DBException;
import db.DBGameUser;
import db.DBServiceFactory;

/**
 * The Bomb Object of the Server. This Bomb ticks some times and then exploded.
 * Then it detects the player and the tile hits.
 *
 * @author Bj�rn, Steffen, Daniel
 */
public class ServerBomb extends Bomb {

    /**
     * handles the tick of the bomb
     *
     * @return
     */
    public boolean tick();
}
