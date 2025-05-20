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

    /**
     * Connect to the Server
     */
    public boolean connectToSrv();
}
