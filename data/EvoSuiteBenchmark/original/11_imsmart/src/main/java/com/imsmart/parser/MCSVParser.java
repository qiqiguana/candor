package com.imsmart.parser;

import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.StringTokenizer;

public class MCSVParser implements MParser {

    private String dataSeparator;
    private MLog logger = MLog.getInstance();

    public MCSVParser() {
        // if nothing is set then its the default Separator
        dataSeparator = ",";
    }

    public void setSeparator(String separator) {
        //this.dataSeparator = separator;
    }

    public List parseAsList(String fileNameWithPath) {
        List parsedList = new ArrayList();
        BufferedReader reader = null;

        File csvFile = new File(fileNameWithPath);

        try {
            String line = "";

            reader = new BufferedReader(new FileReader(csvFile));
            while ((line = reader.readLine()) != null) {

                List row = new ArrayList();

                StringTokenizer t = new StringTokenizer(line, "|");
                while (t.hasMoreElements()) {
                    String value = (String) t.nextElement();
                    row.add(value);

                }

                parsedList.add(row);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return parsedList;
    }

    public HashMap parseAsMap(String fileNameWithPath) {
        MProperties properties = MProperties.getInstance();
        HashMap parsedMap = new HashMap();
        BufferedReader reader = null;

        File csvFile = new File(fileNameWithPath);

        try {
            String line = "";

            reader = new BufferedReader(new FileReader(csvFile));
            while ((line = reader.readLine()) != null) {

                List row = new ArrayList();

                String spliter = properties.getPropertyValue(MProperties.METADATA_SEPERATOR);
                StringTokenizer t = new StringTokenizer(line, spliter);
                while (t.hasMoreElements()) {
                    String value = (String) t.nextElement();
                    row.add(value);

                }
                String primaryIndex = (String) row.get(0);

                parsedMap.put(primaryIndex, row);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return parsedMap;
    }
}
