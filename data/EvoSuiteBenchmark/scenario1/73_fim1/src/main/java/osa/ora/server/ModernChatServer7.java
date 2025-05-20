package osa.ora.server;

import java.net.URISyntaxException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import osa.ora.server.bd.UsersBD;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.ServerSettingBean;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;
import osa.ora.server.client.ClientInterface;
import osa.ora.server.threads.SendKickOffByLoginMessageThread;
import osa.ora.server.threads.SendKickOffMessageThread;
import osa.ora.server.threads.SendRefreshContactThread;
import osa.ora.server.threads.SendUserUpdatedStatusThread;
import osa.ora.server.utils.StringEncoder64;
import osa.ora.server.utils.StringEncrypter;

/**
 * @author ooransa
 * Class implements 2 interfaces:
 * 1.Runnable for ping users thread : that ping users periodically to check if they are still connected or not.
 * 2.ServerInterface that extends 2 other interfaces : Admin Interface and Client Interface , both used
 * As the RMI view of the server for the connected client.
 */
public class ModernChatServer implements ServerInterface, Runnable {

    public ResultBean sendSecureBinaryMessage(BinaryMessage msg) throws RemoteException {
        ClientInterface cf = connectedClients.get(msg.getToUserId());
        if (cf == null) {
            return new ResultBean(false, IConstant.OFFLINE, null);
        } else {
            try {
                //online message, send it..
                if (msg.getAction() == IConstant.REQUEST) {
                    getLogger().log(Level.FINE, "Request send");
                    return cf.receiveSecureBinaryMessageRequest(msg);
                } else {
                    getLogger().log(Level.FINE, "Online BinaryMessage size=" + msg.getData().length + " From=" + msg.getFromUserId() + " To=" + msg.getToUserId());
                    cf.receiveSecureBinaryMessageLoad(msg);
                    return new ResultBean(true, IConstant.SUCCESS, null);
                }
            } catch (RemoteException ex) {
                try {
                    getLogger().log(Level.FINE, "Exception happen, will logoff this user");
                    connectedClients.remove(msg.getToUserId());
                    connectedClientsIPs.remove(msg.getToUserId());
                    offlineStatus(msg.getToUserId());
                } catch (RemoteException ex1) {
                }
                return new ResultBean(false, IConstant.EXCEPTION, ex.getMessage());
            }
        }
    }
}
