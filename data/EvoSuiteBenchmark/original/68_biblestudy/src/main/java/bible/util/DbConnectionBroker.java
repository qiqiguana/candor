package bible.util;

import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * Manages all database access using JDBC
 *
 * @author  Prabhakar Babu
 */
public class DbConnectionBroker {
    public DbConnectionBroker() {
        options = new Options();
        driver = loadDriver();

        min = options.getMin();
        if(min >= options.getMax()) {
            max = min;
        } else {
            max = options.getMax();
        }
        connectionAttributesArray = new DbConnectionAttributes[max];
        for(int index = 0;index < max; index++) {
            connectionAttributesArray[index] = new DbConnectionAttributes(index);
        }
        for(int index = 0; index < min ; index++) {
            try {
                connectionAttributesArray[index].connect(DriverManager.getConnection(options.getURL(), options.getUsername(), options.getPassword()));
            } catch(SQLException e) {
                e.printStackTrace();
                connectionAttributesArray[index].release();
            }
        }
        for(int index = min; index < max; index++) {
            connectionAttributesArray[index].release();
        }
    }

    public synchronized DbConnectionAttributes getConnectionAttributes() throws SQLException {
        //Needs revisit to make it efficient
        for(int index = 0; index < min; index++) {
            if(connectionAttributesArray[index].isFree()) {
                if(connectionAttributesArray[index].isClosed()) {
                    connectionAttributesArray[index].connect(DriverManager.getConnection(options.getURL(), options.getUsername(), options.getPassword()));
                }
                connectionAttributesArray[index].use(index);
                maxConnections++;
                if(maxReached < maxConnections) {
                    maxReached = maxConnections;
                }
                return connectionAttributesArray[index];
            }
        }

        for(int index = min; index < max;index++) {
            if(connectionAttributesArray[index].isNotUsed()) {
                connectionAttributesArray[index].connect(DriverManager.getConnection(options.getURL(), options.getUsername(), options.getPassword()));
                connectionAttributesArray[index].use(index);
                maxConnections++;
                if(maxReached < maxConnections) {
                    maxReached = maxConnections;
                }
                return connectionAttributesArray[index];
            }
        }

        //Ran out of connections
        System.err.println("All Database Connections used");
        for(int index = 0; index < max; index++) {//Print out current used connections.
            System.err.println(connectionAttributesArray[index]);
        }
        throw new SQLException("Database server is busy. The database can have (min = " + min + " , max = " + max + ") connections.");
    }

    /**
     * @author James Stauffer
     */
    protected synchronized void disconnectAll() {
System.out.println("DbConnectionBroker.disconnectAll()");
        for(int index = 0; index < min; index++) {
            if(connectionAttributesArray[index].isFree()) {
                disconnect(connectionAttributesArray[index]);
            } else if(connectionAttributesArray[index].isInUse()) {
System.out.println("DbConnectionBroker.disconnectAll() Freeing in use connection at " + index);
                connectionAttributesArray[index].drop();
                connectionAttributesArray[index] = new DbConnectionAttributes(index);
            }

        }
    }

    /**
     * @author James Stauffer
     */
    protected synchronized void disconnect(DbConnectionAttributes connectionAttributes) {
        if((connectionAttributes.getIndex() < 0) || (connectionAttributesArray[connectionAttributes.getIndex()] == null)) {// Connection may have been cut off because all of the connections were disconnected
System.out.println("ConnectionBroker.disconnect() freeing orphan: " + connectionAttributes);
            try {
                connectionAttributes.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connectionAttributesArray[connectionAttributes.getIndex()].close();
            } catch(SQLException e) {
                System.err.println("ConnectionBroker.release: " + connectionAttributesArray[connectionAttributes.getIndex()].toString());
                e.printStackTrace();
            } finally {
                connectionAttributesArray[connectionAttributes.getIndex()].release();
                maxConnections--;
            }
        }
    }

    public synchronized void release(DbConnectionAttributes connectionAttributes) {
        if((connectionAttributes.getIndex() < 0) || (connectionAttributesArray[connectionAttributes.getIndex()] == null)) {// Connection may have been cut off because all of the connections were disconnected
            disconnect(connectionAttributes);
        } else if(connectionAttributes.getIndex() < min) {
            connectionAttributesArray[connectionAttributes.getIndex()].free();
            maxConnections--;
        } else {
            disconnect(connectionAttributes);
        }
    }

    public DbConnectionAttributes[] getConnectionAttributesArray() {
        return connectionAttributesArray;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getMaxReached() {
        return maxReached;
    }

    public String getURL() {
        return options.getURL();
    }

    public String getLog() {
        return options.getLog();
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean verbose) {
        return getClass().getName() + "[ options=" + options
            + ", maxConnections=" + maxConnections
            + ", maxReached=" + maxReached
            + ", lastTime=" + lastTime
            + (verbose ? (" Open: " + Util.ToString(getOpenConnections())) : "")
            + "]";
    }

    private Options options;
    private Driver driver;

    public Vector getOpenConnections() {
        Vector open = new Vector();
        for(int index = 0; index < max;index++) {
            if(connectionAttributesArray[index].isInUse()) {
                open.addElement(connectionAttributesArray[index]);
            }
        }
        return open;
    }

    private Driver loadDriver() {
        Driver c = null;
        String driverName = options.getDriverName();
        try {
            c = (Driver)Class.forName(driverName).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(InstantiationException e) {
            e.printStackTrace();
        }

        return c;
    }

    private DbConnectionAttributes connectionAttributesArray[];
    private int min;
    private int max;
    private int maxConnections = 0;
    private int maxReached = 0;
    private long lastTime = 0;

}

class Options {
    public String toString() {
        return getClass().getName() + "[ url=" + url
            + ", username=" + username
            + ", range=" + min + "-" + max
            + ", driverName=" + driverName
            + (quiet ? ", quiet" : "")
            + "]";
    }

    protected Options() {
        propertiesFile = new File(DEFAULT_PROPERTIES_FILE);
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getMin() {
        return min;
    }

    protected int getMax() {
        return max;
    }

    protected String getURL() {
        return url;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected String getDriverName() {
        return driverName;
    }

    protected String getLog() {
        return log;
    }

    private static final String DEFAULT_PROPERTIES_FILE = "bible/properties/Database.properties";
    private static final String URL_KEY = "URL";
    private static final String USERNAME_KEY = "Username";
    private static final String PASSWORD_KEY = "Password";
    private static final String MIN_KEY = "Min";
    private static final String MAX_KEY = "Max";
    private static final String DRIVER_NAME = "Driver";
    private static final String QUIET = "Quiet";
    private static final String LOG = "Log";
    private File   propertiesFile;
    private String url = "jdbc:weblogic:mssqlserver4:_db@localhost:1433";
    private String username = "sa";
    private String password = "";
    private int min = 10;
    private int max = 30;
    private String driverName = "weblogic.jdbc.mssqlserver4.Driver";
    private boolean quiet = false;
    private String log = null;// Suggested: Database/

    private void load() throws IOException {
        if (propertiesFile.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(propertiesFile);
            properties.load(fis);

            url          = properties.getProperty(URL_KEY);
            username     = properties.getProperty(USERNAME_KEY);
            password     = properties.getProperty(PASSWORD_KEY);
            String szMin = properties.getProperty(MIN_KEY);
            String szMax = properties.getProperty(MAX_KEY);
            driverName   = properties.getProperty(DRIVER_NAME);

            try {
                min = Integer.parseInt(szMin);
                max = Integer.parseInt(szMax);
            } catch(NumberFormatException e) {
                System.out.println("\n\nPlease check " + propertiesFile + "\n\n");
                e.printStackTrace();
            }

            quiet = properties.getProperty(QUIET) != null;
            log   = properties.getProperty(LOG);
            if((log != null) && (log.length() == 0)) {
                log = null;
            }
            fis.close();
        } else {
            System.out.println("DbConnectionBroker.Options.load() unable to access " + propertiesFile);
        }
        if(!quiet) {
            System.out.println("DbConnectionBroker to "+ url);
        }
    }
}

