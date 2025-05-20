package client;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import org.apache.log4j.Logger;
import client.controller.LocalControl;
import client.gui.StartFrame;
import client.network.ForwardingObserver;
import client.view.BombView;
import client.view.GameCanvas;
import client.view.GameView;
import client.view.GfxFactory;
import client.view.OffGamePainter;
import client.view.PlayerView;
import client.view.PowerUpView;
import client.view.StatusView;
import client.view.TileView;
import common.Bomb;
import common.Constants;
import common.GameModel;
import common.GameObject;
import common.GameRound;
import common.Map;
import common.Player;
import common.PowerUp;
import common.Tile;

/**
 * Represents a game on the client side. This class serves as a connection
 * between the model, view and game loop.
 *
 * @author Andi, Bj�rn
 */
public class ClientGameRound extends GameRound implements Observer {

    /**
     * Searches and returns a GameObject by its id.
     *
     * @param id The id of the GameObject that should be returned.
     * @return The matching GameObject or null if the id is unknown.
     */
    public GameObject getGameObjectById(int id);
}
