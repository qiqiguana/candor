/*
 * ClientSettingBean.java
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
public class AdminSettingBean {
    private String serverURL;
    private String serverPort;
    private String adminEmail;
    private static final String ADMIN_PROP_FILE_NAME = "/config/admin.properties";
    String path;
    /** Creates a new instance of AdminSettingBean */
    public AdminSettingBean(String path1) {
        path=path1+ADMIN_PROP_FILE_NAME;
        Properties config = new Properties();
        try {
            System.out.println("path="+path);
            config.load(new FileInputStream(path));
            if(config!=null){
                serverURL=config.getProperty("serverURL");
                serverPort=config.getProperty("serverPort");
                adminEmail=config.getProperty("adminEmail");
                System.out.println("setting loaded!");
            }else{
                System.out.println("[AdminSettingBean] Error loading property file\n");
            }
        } catch (Exception e) {
            System.out.println("[AdminSettingBean] Error loading property file\n"+e);
            e.printStackTrace();
        }
    }
    public void updateSettings(){
        File tempFile=new File(path);
        BufferedWriter out=null;
        try{
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
            out.write("# Property file used to set parameters for Instant Chat Admin Client");out.newLine();
            out.write("serverURL="+serverURL);out.newLine();
            out.write("serverPort="+serverPort);out.newLine();
            out.write("adminEmail="+adminEmail);out.newLine();
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
     * @return the adminEmail
     */
    public String getAdminEmail() {
        return adminEmail;
    }

    /**
     * @param adminEmail the adminEmail to set
     */
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
