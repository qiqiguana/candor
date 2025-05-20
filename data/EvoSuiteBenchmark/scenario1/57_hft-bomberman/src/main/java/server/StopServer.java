package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import messages.Message;
import messages.global.StopServerMsg;

/**
 * Sends a Message to the Server, that he should closed
 *
 * @author Bj�rn
 */
public class StopServer {

    public boolean connectToSrv() {
        try {
            //System.out.println("Connecting...");
            System.out.println("Establishing a connection ");
            server = new Socket(serverName, serverPort);
            out = new ObjectOutputStream(server.getOutputStream());
            System.out.println("connected to " + serverName);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("Connection failed");
            return false;
        }
    }
}
