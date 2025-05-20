package com.imsmart.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MProperties {

    private static final String INPUT_DETAILS_PROPERTIES = "properties/migrater.properties";
    public static final String DOC_CLASS = "doc_class";
    public static final String MAIL_FROM_ADDRESS = "mail_from_address";
    public static final String MAIL_TO_ADDRESS = "mail_to_address";
    public static final String MAIL_SUBJECT = "mail_subject";
    public static final String MAIL_BODY = "mail_body";
    public static final String SMTP_HOST = "smtp_host";
    public static final String THREAD_COUNT = "thread_count";
    public static final String LOG_DIR = "log_dir";
    public static final String REPORT_DIR = "report_dir";
    public static final String IMAGE_DIR = "image_dir";
    public static final String PLUG_IN_CLASS_DIR = "plug_in_class_dir";
    public static final String CMS_CLASS = "cms_class";
    public static final String METADATA_FILE_TYPE = "metadata_file_type";
    public static final String METADATA_SEPERATOR = "metadata_seperator";
    public static final String METADATA_FILE = "metadata_file";
    public static final String METADATA_LIST = "metadata_list";
    public static final String CMS_USER_NAME = "cms_user_name";
    public static final String CMS_USER_PASSWORD = "cms_user_password";
    private static MProperties mProperties = null;
    public static final String MERGED_DIR = "merged_dir";
    public static final String OPERATION="operation";
    Properties prop = new Properties();

    private MProperties() {
        try {
            prop.load(new FileInputStream(INPUT_DETAILS_PROPERTIES));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MProperties getInstance() {
        if (mProperties == null) {
            mProperties = new MProperties();
        }
        return mProperties;
    }

    public String getPropertyValue(String property) {
        String value = prop.getProperty(property);
        return value;
    }
}
