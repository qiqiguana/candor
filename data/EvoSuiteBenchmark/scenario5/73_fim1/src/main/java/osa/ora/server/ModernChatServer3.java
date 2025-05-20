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

    /**
     * change user password
     *
     * @param email : user email
     * @param oldPass : current password
     * @param newPass : new password
     * @return ResultBean with either true or false
     * @throws RemoteException
     */
    public ResultBean changePassword(String email, String oldPass, String newPass) throws RemoteException {
        email = StringEncoder64.decodeStringUTF8(email);
        oldPass = StringEncrypter.getInstance(secToken).decrypt(oldPass);
        String newOldPass = StringEncrypter.getInstance(oldPass).encrypt(oldPass);
        User user = authenticateUser(email, newOldPass);
        if (user == null) {
            return new ResultBean(false, IConstant.ERROR, "Invalid Password!");
        }
        oldPass = passwordEnc.encrypt(oldPass);
        newPass = StringEncrypter.getInstance(secToken).decrypt(newPass);
        newPass = passwordEnc.encrypt(newPass);
        if (userBD.updatePassword(user.getId(), oldPass, newPass)) {
            passwords.put(user.getId(), newPass);
            return new ResultBean(true, IConstant.SUCCESS, null);
        }
        return new ResultBean(false, IConstant.ERROR, "Error During Applying New Password!");
    }

    /**
     * private method to authenticate the user and return its full detailed bean.
     * @param emailAddr : email of the user
     * @param password  : password of the user.
     * @return User or Null according to the authentication results.
     */
    private User authenticateUser(String emailAddr, String password);
}

public class StringEncoder64 {

    public static String decodeStringUTF8(String data);
}

/**
 * @author ooransa
 */
public class UsersBD implements IBO {

    public boolean updatePassword(int userId, String oldPass, String newPass);
}

/**
 * @author ooransa
 */
public class User implements Serializable {

    public int getId();
}

public class StringEncrypter {

    /**
     * Takes a encrypted String as an argument, decrypts and returns the
     * decrypted String.
     *
     * @param str Encrypted String to be decrypted
     * @return <code>String</code> Decrypted version of the provided String
     */
    public String decrypt(String str);

    /**
     * Singleton
     */
    public static StringEncrypter getInstance(String initKey);

    /**
     * Takes a single String as an argument and returns an Encrypted version
     * of that String.
     * @param str String to be encrypted
     * @return <code>String</code> Encrypted version of the provided String
     */
    public String encrypt(String str);
}
