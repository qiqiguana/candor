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
     * Tries to connect to the server.
     *
     * @throws RuntimeException Thrown when the connection attempt was not successful.
     */
    public boolean connectToSrv() {
        try {
            //System.out.println("Connecting...");
            logger.info("Establishing a connection ");
            server = new Socket(serverName, serverPort);
            msgSender = new ClientMsgSender(server);
            msgSender.start();
            msgReceiver = new ClientMsgReceiver(server, BomberClient.this);
            msgReceiver.start();
            msgSender.sendMsg(new InfoRequestMsg(InfoRequestMsg.GET_SESSION_LIST));
            logger.info("connected to " + serverName);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.info("Connection failed");
            return false;
        }
    }
}
