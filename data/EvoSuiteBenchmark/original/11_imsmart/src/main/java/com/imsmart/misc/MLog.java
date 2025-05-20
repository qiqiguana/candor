/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imsmart.misc;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author gowerdh
 */
public class MLog {

    private static Logger logger = Logger.getLogger("com.smart");
    
    private static MLog mLog = null;

    private MLog(){
        try {
            MProperties properties = MProperties.getInstance();
            String strLogDir = properties.getPropertyValue(MProperties.LOG_DIR);
            String logFileName = "migration_" + MDate.now(MDate.DATE_ONLY) + ".log";
            FileHandler handler = new FileHandler(strLogDir + "/" + logFileName, true);
            handler.setFormatter(new SimpleFormatter());
            // Add to the desired logger
            logger = Logger.getLogger("com.smart");
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static MLog getInstance() {
        if (mLog == null) {
            mLog = new MLog();
        }
        return mLog;
    }
   
    public void info(String info) {
        logger.log(Level.INFO,info);
    }
    public void warning(String warning) {
        logger.log(Level.WARNING,warning);
    }
    
    public void error(String exception) {
        logger.log(Level.SEVERE,exception);
    }
}
