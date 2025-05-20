package com.imsmart.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.imsmart.cms.MContentManager;
import com.imsmart.cms.MContentManagerFileNet;
import com.imsmart.misc.MDate;
import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;
import com.imsmart.report.MReport;
import com.imsmart.report.MReportCSV;

import java.util.HashMap;

public class MImageMigrator extends Thread {

    private static int threadCount = 0;
    private static List fileList;
    private static int fileIndex = 0;
    private static File dirToMigrate;
    private static File processedDir;
    private static File filesList[];
    private static boolean mutex = false;
    private HashMap metadataMap;
    boolean flagCalc;
    private MLog logger = MLog.getInstance();

    public MImageMigrator(HashMap metadataMap) {
        this.metadataMap = metadataMap;
        initialize();
    }

    public MImageMigrator(String name) {
        super(name);
        initialize();

    }

    private void initialize() {
        threadCount++;
        String migratedDir = dirToMigrate.getAbsolutePath() + "/migrated";
        processedDir = new File(migratedDir);
        processedDir.mkdir();

    }

    @Override
    public void run() {
        logger.info("Starting new thread - " + getName());
        while (fileIndex < filesList.length) {
            File fileToMigrate = getNextFileToMigrate();
            if (fileToMigrate.isFile()) {
                //logger.info(getName() + " is migrating file - " + fileToMigrate.getAbsoluteFile().getName());

                // Call Content Manager
                boolean success = true;
                try {
                    MProperties properties = MProperties.getInstance();
                    MContentManager contenManager = (MContentManagerFileNet) getContentManager(properties.getPropertyValue(
                            MProperties.CMS_CLASS));

                    contenManager.connect(properties.getPropertyValue(MProperties.CMS_USER_NAME), properties.getPropertyValue(MProperties.CMS_USER_PASSWORD));
                    contenManager.setMetadataMap(this.metadataMap);
                    //boolean status = contenManager.checkInContent(fileToMigrate);
                    if (contenManager.checkInContent(fileToMigrate)) {
                        success = fileToMigrate.renameTo(new File(processedDir, fileToMigrate.getName()));
                    }
                } catch (Exception ex) {
                    StackTraceElement[] element = ex.getStackTrace();
                    String stackTrace = "";
                    
                    for (int i = 0; i < element.length; i++) {
                        stackTrace = "\n\r" + element[i].getFileName() + " " + element[i].getMethodName() + " " + element[i].getLineNumber();
                    }
                    logger.error("Error uploading file: " + fileToMigrate.getAbsoluteFile().getName() + "\n" + ex.getMessage() + stackTrace);
                    success = false;
                    ex.printStackTrace();
                }

                //logger.info(getName() + " migrated file - " + fileToMigrate.getAbsoluteFile().getName());
                try {
                    addToReport(fileToMigrate.getAbsoluteFile().getName(),
                            MDate.now(MDate.MM_DD_YYYY), MDate.now(MDate.TIME_ONLY),
                            Boolean.toString(success));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());

                }
            }
            justWaitAMin();
        }
        logger.info("End of thread - " + getName());
    }

    private void justWaitAMin() {
        try {
            sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private synchronized File getNextFileToMigrate() {
        File file = filesList[fileIndex];
        fileIndex++;
        return file;
    }

    public static void setDirectoryToMigrate(File dir) {
        // File dir = new File(dirName);
        if (fileList == null) {
            dirToMigrate = dir;
            filesList = dir.listFiles();
        }
    }

    private void addToReport(String name, String dateMigrated,
            String timeMigrated, String status) throws Exception {
        MReport report = new MReportCSV();
        List columns = new ArrayList();
        columns.add(name);
        columns.add(dateMigrated);
        columns.add(timeMigrated);
        columns.add(status);
        report.addLine(columns);
    }

    private MContentManager getContentManager(String contentManagerClass) {
        MContentManager cManager = null;
        try {
            ClassLoader parentLoader = MContentManager.class.getClassLoader();
            String cmsClassDir = MProperties.getInstance().getPropertyValue(
                    MProperties.PLUG_IN_CLASS_DIR);
            if (cmsClassDir.endsWith(".jar")) {
                cmsClassDir = "jar:file://" + cmsClassDir + "!/";
            }
            File classesDir = new File(cmsClassDir);

            URLClassLoader loader1 = new URLClassLoader(new URL[]{classesDir.toURI().toURL()}, parentLoader);
            Class cls1 = loader1.loadClass(contentManagerClass);
            cManager = (MContentManager) cls1.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cManager;
    }
}
