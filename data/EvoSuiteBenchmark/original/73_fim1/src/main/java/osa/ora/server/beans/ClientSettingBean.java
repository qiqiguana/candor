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
import osa.ora.server.utils.StringEncrypter;

/**
 *
 * @author ooransa
 */
public class ClientSettingBean {
    private String defaultStatus;
    private String userEmail;
    private String sourceFontSize;
    private String sourceFontStyle;
    private String sourceFontColor;
    private String sourceFontName;
    private String destFontSize;
    private String destFontStyle;
    private String destFontColor;
    private String destFontName;
    private String serverURL;
    private String serverPort;
    private String soundType;
    private String rememberPass;
    private String password;
    private String onlineOnly;
    private String usersOnly;
    private static final String CLIENT_PROP_FILE_NAME = "/config/client.properties";
    private StringEncrypter stringEnc=StringEncrypter.getInstance("FIS1234");
    String path;
    /** Creates a new instance of ClientSettingBean */
    public ClientSettingBean(String path1) {
        path=path1+CLIENT_PROP_FILE_NAME;
        Properties config = new Properties();
        try {
            System.out.println("path="+path);
            config.load(new FileInputStream(path));
            if(config!=null){
                defaultStatus=config.getProperty("defaultStatus");
                userEmail=config.getProperty("userEmail");
                sourceFontName=config.getProperty("sourceFontName");
                sourceFontSize=config.getProperty("sourceFontSize");
                sourceFontStyle=config.getProperty("sourceFontStyle");
                sourceFontColor=config.getProperty("sourceFontColor");
                destFontName=config.getProperty("destFontName");
                destFontSize=config.getProperty("destFontSize");
                destFontStyle=config.getProperty("destFontStyle");
                destFontColor=config.getProperty("destFontColor");
                serverURL=config.getProperty("serverURL");
                serverPort=config.getProperty("serverPort");
                soundType=config.getProperty("soundType");
                rememberPass=config.getProperty("rememberPass");
                onlineOnly=config.getProperty("onlineOnly");
                usersOnly=config.getProperty("usersOnly");
                if("1".equalsIgnoreCase(rememberPass)){
                    password=stringEnc.decrypt(config.getProperty("trans"));
                }
                System.out.println("setting loaded!");
            }else{
                System.out.println("[ClientSettingBean] Error loading property file\n");
            }
        } catch (Exception e) {
            System.out.println("[ClientSettingBean] Error loading property file\n"+e);
            e.printStackTrace();
        }
    }
    public void updateSettings(){
        File tempFile=new File(path);
        BufferedWriter out=null;
        try{
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
            out.write("# Property file used to set parameters for Instant Chat Client");out.newLine();
            out.write("defaultStatus="+defaultStatus);out.newLine();
            out.write("userEmail="+userEmail);out.newLine();
            out.write("sourceFontSize="+getSourceFontSize());out.newLine();
            out.write("sourceFontStyle="+sourceFontStyle);out.newLine();
            out.write("sourceFontColor="+sourceFontColor);out.newLine();
            out.write("sourceFontName="+getSourceFontName());out.newLine();
            out.write("destFontSize="+getDestFontSize());out.newLine();
            out.write("destFontStyle="+getDestFontStyle());out.newLine();
            out.write("destFontColor="+getDestFontColor());out.newLine();
            out.write("destFontName="+getDestFontName());out.newLine();
            out.write("serverURL="+serverURL);out.newLine();
            out.write("serverPort="+serverPort);out.newLine();
            out.write("soundType="+soundType);out.newLine();
            out.write("rememberPass="+rememberPass);out.newLine();
            out.write("onlineOnly="+getOnlineOnly());out.newLine();
            out.write("usersOnly="+getUsersOnly());out.newLine();
            if("1".equalsIgnoreCase(rememberPass)){
                out.write("trans="+stringEnc.encrypt(password));out.newLine();
            }
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
     * @return the defaultStatus
     */
    public String getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * @param defaultStatus the defaultStatus to set
     */
    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
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
     * @return the sourceFontSize
     */
    public String getSourcefontSize() {
        return getSourceFontSize();
    }

    /**
     * @param sourceFontSize the sourceFontSize to set
     */
    public void setSourcefontSize(String sourcefontSize) {
        this.setSourceFontSize(sourcefontSize);
    }

    /**
     * @return the sourceFontStyle
     */
    public String getSourceFontStyle() {
        return sourceFontStyle;
    }

    /**
     * @param sourceFontStyle the sourceFontStyle to set
     */
    public void setSourceFontStyle(String sourceFontStyle) {
        this.sourceFontStyle = sourceFontStyle;
    }

    /**
     * @return the sourceFontColor
     */
    public String getSourceFontColor() {
        return sourceFontColor;
    }

    /**
     * @param sourceFontColor the sourceFontColor to set
     */
    public void setSourceFontColor(String sourceFontColor) {
        this.sourceFontColor = sourceFontColor;
    }

    /**
     * @return the soundType
     */
    public String getSoundType() {
        return soundType;
    }

    /**
     * @param soundType the soundType to set
     */
    public void setSoundType(String soundType) {
        this.soundType = soundType;
    }

    /**
     * @return the sourceFontSize
     */
    public String getSourceFontSize() {
        return sourceFontSize;
    }

    /**
     * @param sourceFontSize the sourceFontSize to set
     */
    public void setSourceFontSize(String sourceFontSize) {
        this.sourceFontSize = sourceFontSize;
    }

    /**
     * @return the sourceFontName
     */
    public String getSourceFontName() {
        return sourceFontName;
    }

    /**
     * @param sourceFontName the sourceFontName to set
     */
    public void setSourceFontName(String sourceFontName) {
        this.sourceFontName = sourceFontName;
    }

    /**
     * @return the destFontSize
     */
    public String getDestFontSize() {
        return destFontSize;
    }

    /**
     * @param destFontSize the destFontSize to set
     */
    public void setDestFontSize(String destFontSize) {
        this.destFontSize = destFontSize;
    }

    /**
     * @return the destFontStyle
     */
    public String getDestFontStyle() {
        return destFontStyle;
    }

    /**
     * @param destFontStyle the destFontStyle to set
     */
    public void setDestFontStyle(String destFontStyle) {
        this.destFontStyle = destFontStyle;
    }

    /**
     * @return the destFontColor
     */
    public String getDestFontColor() {
        return destFontColor;
    }

    /**
     * @param destFontColor the destFontColor to set
     */
    public void setDestFontColor(String destFontColor) {
        this.destFontColor = destFontColor;
    }

    /**
     * @return the destFontName
     */
    public String getDestFontName() {
        return destFontName;
    }

    /**
     * @param destFontName the destFontName to set
     */
    public void setDestFontName(String destFontName) {
        this.destFontName = destFontName;
    }

    /**
     * @return the rememberPass
     */
    public String getRememberPass() {
        return rememberPass;
    }

    /**
     * @param rememberPass the rememberPass to set
     */
    public void setRememberPass(String rememberPass) {
        this.rememberPass = rememberPass;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the onlineOnly
     */
    public String getOnlineOnly() {
        return onlineOnly;
    }

    /**
     * @param onlineOnly the onlineOnly to set
     */
    public void setOnlineOnly(String onlineOnly) {
        this.onlineOnly = onlineOnly;
    }

    /**
     * @return the usersOnly
     */
    public String getUsersOnly() {
        return usersOnly;
    }

    /**
     * @param usersOnly the usersOnly to set
     */
    public void setUsersOnly(String usersOnly) {
        this.usersOnly = usersOnly;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
