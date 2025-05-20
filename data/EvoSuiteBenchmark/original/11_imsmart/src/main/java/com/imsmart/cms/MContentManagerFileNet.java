package com.imsmart.cms;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.momed.cms.filenet.session.CEConnectionManager;
import com.momed.cms.filenet.session.CESession;
import com.momed.cms.filenet.util.CEImport;
import com.momed.cms.filenet.util.CEProperties;
import com.imsmart.misc.MDate;
import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;
import java.util.Date;
import java.util.Map;

public class MContentManagerFileNet implements MContentManager {

    private CESession session;
    private CEConnectionManager manager;
    private Map metadataMap;
    private MLog logger = MLog.getInstance();
    CEProperties properties = CEProperties.getInstance();

    @Override
    public void setMetadataMap(Map metadataMap) {
        this.metadataMap = metadataMap;
    }

    @Override
    public boolean checkInContent(File content) throws Exception {


        CEImport ceImport = new CEImport(manager);
        String docNameWithExtension = content.getAbsoluteFile().getName();
        if (!docNameWithExtension.equalsIgnoreCase("Thumbs.db")) {
            int dotIndex = docNameWithExtension.indexOf(".");
            String docFirstName = docNameWithExtension.substring(0, dotIndex);
            //String docClassRegionCode = docFirstName.substring(0, 2);

            //String docClass = properties.getPropertyValue(docClassRegionCode);
            //logger.info("Document Name: "+docFirstName+" docClassRegionCode: "+docClassRegionCode+" Doc Class: "+docClass);

            List metadataValueList = (List) metadataMap.get(docFirstName);
            List metadataList = (List) metadataMap.get(MProperties.METADATA_LIST);

            //Getting doc class
            String plexusDocClass = (String) metadataValueList.get(1);
            String docClass = getDocClass(plexusDocClass);
            String checkInFolder = properties.getPropertyValue(docClass);

            HashMap map = new HashMap();
            map.put("DocumentTitle", docFirstName);
            map.put("Primary_Index", docFirstName);
            //map.put((String)metadataList.get(0), docNameWithExtension);
            for (int i = 0; i < metadataValueList.size(); i++) {
                String metadataWithType = (String) metadataList.get(i); //Created_On|DATE|MM/DD/yyyy
                
                String dataType = metadataWithType.substring(metadataWithType.indexOf("|") + 1, metadataWithType.length());//DATE|MM/DD/yyyy
                
                //Checks whether format is specified for this datatype. useful specially for Date
                String format = "";
                if (dataType.indexOf("|") != -1) {
                    format = dataType.substring(dataType.indexOf("|") + 1, dataType.length());//MM/DD/yyyy
                    dataType = dataType.substring(0, dataType.indexOf("|"));//DATE
                }

                String metadataName = metadataWithType.substring(0, metadataWithType.indexOf("|"));
                String value = (String) metadataValueList.get(i);

                if (value != null || !value.equals("")) {
                    if ("STRING".equalsIgnoreCase(dataType)) {
                        map.put(metadataName, value);
                    } else if ("DATE".equalsIgnoreCase(dataType)) {
                        //if (value.indexOf("/") != -1) {
                        Date dateValue = MDate.parseDate(value, format);
                        map.put(metadataName, dateValue);
                    //} else {
                    //    Date dateValue = MDate.parseDate(value, MDate.YYYYMMDD);
                    //    map.put(metadataName, dateValue);
                    //}
                    } else if ("BOOLEAN".equalsIgnoreCase(dataType)) {
                        if (metadataName.equalsIgnoreCase("Red_Filter")) {
                            if (value.indexOf("NOVL") != -1) {
                                map.put(metadataName, new Boolean(false));
                            } else {
                                map.put(metadataName, new Boolean(true));
                            }
                        }
                    }
                }
            }
            ceImport.uploadDocument(content, docNameWithExtension, docClass,
                    checkInFolder, map, properties.getPropertyValue(CEProperties.MIME_TYPE));
        }

        return true;
    }

    private String getDocClass(String plexusDocClass) {

        String subName;
        int i = plexusDocClass.indexOf("NOVL");
        if (i != -1) {
            subName = plexusDocClass.substring(2, i);
        } else {
            subName = plexusDocClass.substring(2);
        }

        String docClassName = properties.getPropertyValue(subName);

        return docClassName;
    }

    @Override
    public void connect(String userName, String password) {
        manager = CEConnectionManager.getConnectionManager(userName, password);
        session =
                new CESession(manager);
    }

    @Override
    public boolean createFolder(String parentFolder, String folder) {
        return false;
    }
}
