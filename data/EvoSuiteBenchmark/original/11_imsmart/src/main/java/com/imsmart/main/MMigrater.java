package com.imsmart.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.imsmart.misc.MDate;
import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;
import com.imsmart.parser.MParser;
import com.imsmart.parser.MParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MMigrater {

    private HashMap metadataMap;
    private MLog logger;

    public static void main(String args[]) {
        MProperties properties = MProperties.getInstance();

        if ("MERGE".equalsIgnoreCase(args[0])) {
            MMergeTIFF.startMerge();
        } else if ("MIGRATE".equalsIgnoreCase(args[0])) {
            String imageDir = properties.getPropertyValue(MProperties.IMAGE_DIR);

            MMigrater migrater = new MMigrater();
            MImageMigrator.setDirectoryToMigrate(new File(imageDir));
            migrater.initializeLogger();
            migrater.initializeAttributesMap();

            migrater.startImageMigrationThread();
        }

    }

    public MMigrater() {
        logger = MLog.getInstance();
        logger.info("Migration process started.");
    }

    private void initializeAttributesMap() {
        logger.info("Reading Metadata!");
        MProperties properties = MProperties.getInstance();
        MParser parser = MParserFactory.getParser();
        metadataMap = parser.parseAsMap(properties.getPropertyValue(MProperties.METADATA_FILE));

        String metadata = properties.getPropertyValue(MProperties.METADATA_LIST);
        List metadataList = new ArrayList();
        StringTokenizer t = new StringTokenizer(metadata, ",");



        while (t.hasMoreElements()) {
            String value = (String) t.nextElement();
            metadataList.add(value);

        }
        metadataMap.put(MProperties.METADATA_LIST, metadataList);
        logger.info("Loading Metada Done!!");
    }

    private void startImageMigrationThread() {
        logger.info("Starting Migration");
        MProperties properties = MProperties.getInstance();
        String strThreadCount = properties.getPropertyValue(MProperties.THREAD_COUNT);

        int threadCount = Integer.parseInt(strThreadCount);

        for (int i = 0;
                i < threadCount; i++) {
            MImageMigrator imageMigrationThread = new MImageMigrator(metadataMap);
            imageMigrationThread.start();
        }

    }

    private void initializeLogger() {
        MProperties properties = MProperties.getInstance();
        String logDir = properties.getPropertyValue(MProperties.LOG_DIR);
        logger.info(logDir);
        try {
            // Create an appending file handler
            boolean append = true;
            String logFileName = "migration_" + MDate.now(MDate.DATE_ONLY);
            FileHandler handler = new FileHandler(logDir + "/" + logFileName,
                    append);

            // Add to the desired logger
            Logger logger = Logger.getLogger("com.mycompany");
            logger.addHandler(handler);
        } catch (IOException e) {
        }
    }
}
