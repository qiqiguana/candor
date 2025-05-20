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

    private ObjectOutputStream out;

    private Socket server;

    private String serverName = "193.196.141.182";

    private int serverPort = 6666;

    /**
     * sends a message
     *
     * @param msg
     */
    public void sendMsg(Message msg);

    /**
     * Connect to the Server
     */
    public boolean connectToSrv();

    /**
     * Connect to the server, and send the message
     *
     * @param stop
     */
    public void callingStopServer(StopServer stop);

    /**
     * @param args
     */
    public static void main(String[] args);
}
