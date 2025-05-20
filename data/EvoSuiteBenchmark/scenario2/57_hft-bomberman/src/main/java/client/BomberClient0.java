package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import javax.swing.ImageIcon;
import messages.Message;
import messages.global.InfoRequestMsg;
import messages.global.JoinSessionMsg;
import messages.global.MapInfo;
import messages.global.SessionDetailsMsg;
import messages.round.ClientQuitRunningSessionMsg;
import org.apache.log4j.Logger;
import client.gui.StartFrame;
import client.network.ClientMsgReceiver;
import client.network.ClientMsgSender;

/**
 * This is central client class. It is responsible for globally scoped messages
 * and the creation of sessions.
 *
 * @author andi
 */
public class BomberClient extends Observable {

    /**
     * Returns the singleton.
     *
     * @return The only BomberClient instance.
     */
    public static BomberClient getInstance() {
        if (instance == null) {
            logger.info("Creating BomberClient singleton instance");
            instance = new BomberClient("client");
        }
        return instance;
    }
}
