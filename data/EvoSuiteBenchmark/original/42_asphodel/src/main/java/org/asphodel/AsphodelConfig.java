package org.asphodel;

import java.util.Properties;

/**
 * @author : Sun Wenju
 *         Date: Apr 23, 2008 9:51:41 AM
 */
public class AsphodelConfig {
    private static Properties properties;

/*
    private AsphodelConfig(Properties props) {
        this.properties = props;

    }
    private static AsphodelConfig instance;
    public AsphodelConfig getInstance(Properties props) {
        if (instance == null) {
            instance = new AsphodelConfig(props);
        }
        return instance;
    }
*/

    public static void populateConfig(Properties props)
    {
        properties =props;
    }

    public static String getRepositoryHousePath() {
        return getConfig("ftr.repository.house");
    }

    public static String getDefaultRepository()
    {
        return getConfig("ftr.repository.default");
    }

    private static String getConfig(String key) {
        return properties.getProperty(key);
    }
}
