package macaw.system;

public class StartupOptions {

    // ==========================================
    private boolean useDemo;

    private String databaseName;

    private String dbDriverName;

    private String dbUser;

    private String dbPassword;

    private String server;

    private String port;

    private int maximumNumberOfConnections;

    // ==========================================
    public StartupOptions() {
    }

    // ==========================================
    public void processCommandLineArguments(String[] arguments);

    public String getDatabaseURI();

    /**
     * @return the dbUser
     */
    public String getDbUser();

    /**
     * @param dbUser the dbUser to set
     */
    public void setDbUser(String dbUser);

    /**
     * @return the dbPassword
     */
    public String getDbPassword();

    /**
     * @param dbPassword the dbPassword to set
     */
    public void setDbPassword(String dbPassword);

    /**
     * @return the server
     */
    public String getServer();

    /**
     * @param server the server to set
     */
    public void setServer(String server);

    /**
     * @return the port
     */
    public String getPort();

    /**
     * @param port the port to set
     */
    public void setPort(String port);

    /**
     * @return the useDemo
     */
    public boolean useDemo();

    /**
     * @param useDemo the useDemo to set
     */
    public void setUseDemo(boolean useDemo);

    /**
     * @return the databaseName
     */
    public String getDatabaseName();

    /**
     * @param databaseName the databaseName to set
     */
    public void setDatabaseName(String databaseName);

    /**
     * @return the dbDriverName
     */
    public String getDbDriverName();

    /**
     * @param dbDriverName the dbDriverName to set
     */
    public void setDbDriverName(String dbDriverName);

    /**
     * @return the maximumNumberOfConnections
     */
    public int getMaximumNumberOfConnections();

    /**
     * @param maximumNumberOfConnections the maximumNumberOfConnections to set
     */
    public void setMaximumNumberOfConnections(int maximumNumberOfConnections);
}
