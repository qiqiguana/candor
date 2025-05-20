package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import messages.Message;
import messages.global.GlobalServerMsg;
import messages.global.SessionDetailsMsg;
import messages.global.SessionListMsg;
import org.apache.log4j.Logger;
import org.hsqldb.Server;
import org.hsqldb.jdbc.jdbcDataSource;
import common.Constants;

/**
 * This is the main server class. It is responsible for accepting connections
 * from clients and starting new games/session.
 *
 * @author Steffen, Bj�rn, Daniel
 */
public class BomberServer {

    /**
     * Static factory method.
     *
     * @return The BomberServer Singleton
     */
    public static BomberServer getInstance() {
        if (bomberServer == null) {
            bomberServer = new BomberServer();
        }
        return bomberServer;
    }
}
