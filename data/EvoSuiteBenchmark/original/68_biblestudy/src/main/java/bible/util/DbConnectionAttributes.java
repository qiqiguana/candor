package bible.util;

import java.sql.*;
import java.util.Date;

public class DbConnectionAttributes {
    private static final int INUSE = 1;
    private static final int FREE = 2;
    private static final int NOTUSED = 3;

    protected DbConnectionAttributes(int index) {
        this.index = index;
        status = FREE;
    }

    protected void connect(Connection connection) throws SQLException {
        this.connection = connection;
        if(connection == null) {
            (new NullPointerException("connection - " + toString())).printStackTrace();
        }
        this.statement = connection.createStatement();
        this.status = FREE;
    }

    protected void close() throws SQLException {
        if(statement != null) {
            statement.close();
            statement = null;
        }
        if(connection != null) {
            connection.close();
            connection = null;
        }
    }

    protected void release() {
        status = NOTUSED;
        creationTime = -1;
    }

    protected void free() {
        status = FREE;
    }

    /**
     * Used to drop a connection whe it is in use.
     * @see ConnectionBroker#disconnectAll
     * @author James Stauffer
     */
    protected void drop() {
        index = -1;
    }

    protected void use(int index) {
        creationTime = System.currentTimeMillis();;
        status = INUSE;
        this.index = index;
    }

    protected void init(String query) {
        this.query = query;
        this.e = new Exception();
    }

    protected void reset() {
        this.query = "";
        this.e = null;
    }

    /**
     * Should only be used by the maintanence SQL queries in DatabaseManager
     * @see DatabaseManager#executeInsert
     * @author James Stauffer
     */
    protected ResultSet executeQuery(String identityQuery) throws SQLException {
        return statement.executeQuery(identityQuery);
    }

    /**
     * Should only be used by the maintanence SQL queries in DatabaseManager
     * @see DatabaseManager#getInsertID
     * @author James Stauffer
     */
    protected void executeUpdate(String identityUpdate) throws SQLException {
        statement.executeUpdate(identityUpdate);
    }

    protected ResultSet executeQuery() throws SQLException {
        return statement.executeQuery(query);
    }

    protected int executeUpdate() throws SQLException {
        return statement.executeUpdate(query);
    }

    protected PreparedStatement prepareStatement() throws SQLException {
        return connection.prepareStatement(query);
    }

    protected int getIndex() {
        return index;
    }

    protected long getCreationTime() {
        return creationTime;
    }

    public String getCreationDate() {
        return (new Date(creationTime)).toString();
    }

    public String getConnection() {
        return "" + connection;
    }

    public String getStatement() {
        return "" + statement;
    }

    public String getException() {
        if(e == null) {
            return null;
        } else {
            return Util.ToString(e);
        }
    }

    public String getQuery() {
        return query;
    }

    public boolean isFree() {
        return status == FREE;
    }

    public boolean isInUse() {
        return status == INUSE;
    }

    public boolean isNotUsed() {
        return status == NOTUSED;
    }
    protected boolean isClosed() throws SQLException {
        return (connection == null) || connection.isClosed();
    }

    /**
     * @author James Stauffer
     */
    public String toString() {
        return getClass().getName() + ":[index=" + index
            + ", status=" + getStatusString()
            + ", query=" + ((query != null) ? "\"" + query + "\"" : query)
            + ", creationDate=" + new Date(creationTime)
            + ", e=" + Util.ToString(e)
            + "]";
    }

    /**
     * @author James Stauffer
     */
    public String toShortString() {
        return getClass().getName() + ":[" + index
            + " " + ((query != null) ? query : "")
            + "]";
    }

    /**
     * @author James Stauffer
     */
    public String toLogString() {
        return index + " " + status + "  " + Logger.FormatDate(creationTime) + "\t" + query;
    }

    /**
     * @author James Stauffer
     */
    public String getStatusString() {
        switch(status) {
            case INUSE:    return "InUse (Connected)";
            case FREE:     return "Free (Connected-Not Used)";
            case NOTUSED:  return "NotUsed (Not Connected)";
            default:       return "NOT KNOWN";
        }

    }

    private int status;
    private Connection connection;
    private Statement statement;
    private int index;
    private long creationTime = -1;
    private String query = null;
    private Exception e = null;
}
