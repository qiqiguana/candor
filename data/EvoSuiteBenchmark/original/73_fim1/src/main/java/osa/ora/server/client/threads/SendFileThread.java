/*
 * SendFileThread.java
 *
 * Created on November 1, 2009, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JOptionPane;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.IConstant;
import osa.ora.server.client.*;
import osa.ora.server.client.ui.ChatWindowPanel;
import osa.ora.server.utils.FileEncrypter;

/**
 *
 * @author ooransa
 */
public class SendFileThread extends Thread {

    File file;
    ChatWindowPanel parentPanel;
    BinaryMessage bm;
    ChatClientApp chatApp;
    /** Creates a new instance of SendFileThread */
    public SendFileThread(File file,ChatWindowPanel parentPanel,BinaryMessage bm,ChatClientApp chatApp) {
        this.file=file;
        this.parentPanel=parentPanel;
        this.bm=bm;
        this.chatApp=chatApp;
    }

    public void run() {
        bm.setAction(IConstant.REQUEST);
        String result=chatApp.sendBinaryMessage(bm);
        if(result==null){
            bm.setAction(IConstant.DATA);
            if(chatApp.getSecurityMode()>0){
                bm.setDesc(chatApp.getStringEnc().decrypt(bm.getDesc()));
                bm.setTitle(chatApp.getStringEnc().decrypt(bm.getTitle()));
            }
            if(chatApp.getSecurityMode()==2){
                 String stamp=Calendar.getInstance().getTimeInMillis()+"";
                 stamp=stamp.substring(7);
                 String tempFile=chatApp.getPath()+"/"+stamp;
                 byte[] data=FileEncrypter.getInstance().fileEncrypt(file,new File(tempFile));
                 bm.setData(data);
            }else{
                FileInputStream fis=null;
                try{
                    fis=new FileInputStream(file);
                    int lenght=fis.available();
                    byte[] data=new byte[lenght];
                    fis.read(data);
                    bm.setData(data);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentPanel, "Error while loading this file:"+ex.getMessage(),"Error!",JOptionPane.OK_OPTION);
                    return;
                }finally {
                    try {
                        if(fis!=null) fis.close();
                    } catch (IOException ex) {
                    }
                }
            }
            if(bm.getData()!=null) {
                result=chatApp.sendBinaryMessage(bm);
                if(result!=null){
                    parentPanel.addTextChat(result);
                }else{
                    parentPanel.addTextChat("File ["+file.getName()+"] Uploaded To The Server Succesfully.");
                }
            }
        }else{
            parentPanel.addTextChat(result);
        }
    }


}
