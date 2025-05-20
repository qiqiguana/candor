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

    public LoginBean signInAsAdmin(String emailAddr, String password) throws RemoteException {
        //authenticate User
        //DB authentication....
        if (emailAddr != null && password != null) {
            String email = StringEncoder64.decodeStringUTF8(emailAddr);
            User user = null;
            String orignalPass = passwordEnc.decrypt(passwords.get(1000));
            password = StringEncrypter.getInstance(orignalPass).decrypt(password);
            if (email.equalsIgnoreCase(getAdminUser().getEmail()) && password != null && password.equals(orignalPass)) {
                user = getAdminUser();
            }
            if (user != null) {
                getLogger().log(Level.FINE, "User " + email + " has logged in.");
                authToken = "FIM" + Calendar.getInstance().getTimeInMillis();
                LoginBean loginBean = new LoginBean();
                loginBean.setUser(adminUser);
                loginBean.setAuthToken(authToken);
                String tokenUsed = StringEncrypter.getInstance(orignalPass).encrypt(secToken);
                loginBean.setSecureToken(tokenUsed);
                return loginBean;
            } else {
                getLogger().log(Level.FINE, "User " + email + " is not valid!");
                return null;
            }
        } else {
            getLogger().log(Level.WARNING, "Credentials is null!");
            return null;
        }
    }
}
