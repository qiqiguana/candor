/*
 * ServerSettingBean.java
 *
 * Created on October 3, 2009, 3:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.beans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 *
 * @author ooransa
 */
public class ServerSettingBean {
    private String rootNode;
    private String defualtPassword;
    private String serverURL;
    private String serverPort;
    private String connectionType;
    private String logLevel;
    private String secureMode;
    private static final String SERVER_PROP_FILE_NAME = "/config/server.properties";
    String path;
    /** Creates a new instance of ServerSettingBean */
    public ServerSettingBean(String path1) {
        path=path1+SERVER_PROP_FILE_NAME;
        Properties config = new Properties();
        try {
            System.out.println("path="+path);
            config.load(new FileInputStream(path));
            if(config!=null){
                rootNode=config.getProperty("rootNode");
                serverURL=config.getProperty("serverURL");
                serverPort=config.getProperty("serverPort");
                connectionType=config.getProperty("connectionType");
                defualtPassword=config.getProperty("defualtTransaction");
                logLevel=config.getProperty("logLevel");
                secureMode=config.getProperty("secureMode");
                System.out.println("setting loaded!");
            }else{
                System.out.println("[ServerSettingBean] Error loading property file\n");
            }
        } catch (Exception e) {
            System.out.println("[ServerSettingBean] Error loading property file\n"+e);
            e.printStackTrace();
        }
    }
    public void updateSettings(){
        File tempFile=new File(path);
        BufferedWriter out=null;
        try{
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
            out.write("# Property file used to set parameters for Modern Chat Server");out.newLine();
            out.write("rootNode="+rootNode);out.newLine();
            out.write("serverURL="+serverURL);out.newLine();
            out.write("serverPort="+serverPort);out.newLine();
            out.write("defualtTransaction="+defualtPassword);out.newLine();
            out.write("logLevel="+logLevel);out.newLine();
            out.write("secureMode="+secureMode);out.newLine();
            out.flush();
            out.close();
        }catch(Exception e){
            //no thing for now
            e.printStackTrace();
        }finally{
            if(out!=null){
                try{
                    out.close();
                }catch(Exception e){
                    //e.printStackTrace();
                }
            }
        }
    }
    /**
     * @return the serverURL
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * @param serverURL the serverURL to set
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    /**
     * @return the serverPort
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the connectionType
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * @param connectionType the connectionType to set
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @return the defualtPassword
     */
    public String getDefualtPassword() {
        return defualtPassword;
    }

    /**
     * @param defualtPassword the defualtPassword to set
     */
    public void setDefualtPassword(String defualtPassword) {
        this.defualtPassword = defualtPassword;
    }

    /**
     * @return the logLevel
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * @param logLevel the logLevel to set
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * @return the secureMode
     */
    public String getSecureMode() {
        return secureMode;
    }

    /**
     * @param secureMode the secureMode to set
     */
    public void setSecureMode(String secureMode) {
        this.secureMode = secureMode;
    }

    /**
     * @return the rootNode
     */
    public String getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }
}
