/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.server;

import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import osa.ora.server.beans.ServerSettingBean;

/**
 *
 * @author ooransa
 */
public class StopServer {

    private ServerAdminInterface serverAdminInterface;
    private static String path = "";
    private ServerSettingBean adminSettingBean;
    /**
     * main method for admin
     * @param args
     */
    public static void main(String[] args) {
        try {
            StopServer adminApp = new StopServer();
            adminApp.stop();
            System.out.println("Stop signal send successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in Stopping the Server!");
        }
    }

    public StopServer() throws RemoteException {
        try {
            path = StopServer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        path = path.substring(0, path.lastIndexOf('/') + 1);
        adminSettingBean = new ServerSettingBean(path);
    }

    public void initServer() throws Exception {
        //lookup for the server
        Registry reg = null;
        reg = LocateRegistry.getRegistry(adminSettingBean.getServerURL(), Integer.parseInt(adminSettingBean.getServerPort()));
        System.out.println("trying to connect to the FIM server .....");
        try {
            serverAdminInterface = (ServerAdminInterface) reg.lookup("ModernChatServer");
            System.out.println("connected to the FIM server .....");
        } catch (RemoteException ex) {
            //ex.printStackTrace();
            throw new Exception("Can't find this FIM Server!");
        } catch (NotBoundException ex) {
            //ex.printStackTrace();
            throw new Exception("Server invalid IP/Port or FIM Server not running!");
        }
    }

    public void stop() throws Exception {
        if (serverAdminInterface == null) {
            initServer();
        }
        try {
            serverAdminInterface.ping();
            System.out.println("Server is running..., stopping FIM server...");
        } catch (RemoteException ex) {
            ex.printStackTrace();
            throw new Exception("Error During stopping the FIM Server!");
        }
        try {
            serverAdminInterface.shutdownServer("Command Line Stop","FIM");            
        } catch (Throwable ex) {
        }
        JOptionPane.showMessageDialog(null, "Stop Command Send To The FIM Server!");
    }
}
