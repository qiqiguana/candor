/**
 * Database.java: Manages all database access using JDBC
 */

package bible.util;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Manages all database access using JDBC
 *
 * @version $Id: Database.java,v 1.1.1.1 2001/02/10 05:46:08 jstauffe Exp $
 * @author  Umesh Berry
 */
public class Database {
    private Database() {
        connectionBroker = new DbConnectionBroker();
        String logDir = connectionBroker.getLog();
        if(logDir != null) {
            log = new Logger(logDir);
            log.log(connectionBroker.toString());
            log.log("Start \tCreation Time \tIndex \tCreate Time String\tQuery");
            log.log("End \tCreation Time \tElapsed Milliseconds");
        }
    }

    public static DbResult Query(String query) throws SQLException {
        return DefaultDatabase.query(query, true);
    }

    private DbResult query(String query) throws SQLException {
        return query(query, true);
    }

    private DbResult query(String query, boolean recursive) throws SQLException {

        String PREFIX = "select ";
        if(query.trim().substring(0, PREFIX.length()).equalsIgnoreCase(PREFIX)) {
            DbConnectionAttributes connectionAttributes = getConnectionAttributes();
            connectionAttributes.init(query);
            logStart(connectionAttributes);
            try {
                return new DbResult(connectionAttributes, connectionAttributes.executeQuery(), connectionBroker);
            } catch(SQLException e) {
                e.printStackTrace();
                if((e.getMessage() != null) && (e.getMessage().indexOf(CONNECTION_ERROR) >= 0)) {
                    connectionBroker.disconnectAll();
                    if(recursive) {//Try one more time
                        return query(query, false);
                    }
                } else {
                    connectionBroker.release(connectionAttributes);
                }
                throw new SQLException("Query=" + query + "\r\n" + Util.ToString(e));
            } finally {
                logEnd(connectionAttributes);
            }
        } else {
            throw new SQLException("Query doesn't start with '" + PREFIX + "', but '" + query.substring(0, PREFIX.length()) + "' :" + query);
        }
    }

    public static int Update(String update) throws SQLException {
        return DefaultDatabase.update(update);
    }

    private int update(String update) throws SQLException {
        int rowsChanged = 0;
        DbConnectionAttributes connectionAttributes = getConnectionAttributes();
        connectionAttributes.init(update);
        logStart(connectionAttributes);
        try {
            rowsChanged = connectionAttributes.executeUpdate();
            connectionBroker.release(connectionAttributes);
            return rowsChanged;
        } catch(SQLException e) {
            if((e.getMessage() != null) && (e.getMessage().indexOf(CONNECTION_ERROR) >= 0)) {
                connectionBroker.disconnectAll();
            } else {
                connectionBroker.release(connectionAttributes);
            }
            throw e;
        } finally {
            logEnd(connectionAttributes);
            connectionAttributes.reset();
        }
    }

    public static int Insert(String insert) throws SQLException {
        String[] inserts = new String[1];
        inserts[0] = insert;
        return DefaultDatabase.insert(inserts, true)[0];
    }

    /**
     * Useful for passing in multiple inserts to do in batches
     * @author James Stauffer
     */
    public static int[] BatchInsert(String[] inserts, int countPerBatch) throws SQLException {
        int [] ids = new int[inserts.length];
        int [] batchIds;
        String[] insertBatch = new String[countPerBatch];
        int fullBatches = inserts.length / countPerBatch;
        for(int index = 0; index < fullBatches; index++) {
            System.arraycopy(inserts, index * countPerBatch, insertBatch, 0, countPerBatch);//Copy inserts in batch to separate array
            batchIds = DefaultDatabase.insert(insertBatch, false);
            System.arraycopy(batchIds, 0, ids, index * countPerBatch, countPerBatch);//Copy ids back
        }

        int lastBatchSize = inserts.length % countPerBatch;
        if(lastBatchSize > 0) {
            insertBatch = new String[lastBatchSize];
            System.arraycopy(inserts, inserts.length - lastBatchSize, insertBatch, 0, insertBatch.length);//Copy inserts in batch to separate array
            batchIds = DefaultDatabase.insert(insertBatch, false);
            System.arraycopy(batchIds, 0, ids, inserts.length - lastBatchSize, lastBatchSize);//Copy ids back
        }
        return ids;
    }

    /**
     * @author James Stauffer
     */
    public static int Insert(String insert, boolean returnID) throws SQLException {
        String[] inserts = new String[1];
        inserts[0] = insert;
        return DefaultDatabase.insert(inserts, returnID)[0];
    }

    private int insert(String insert) throws SQLException {
        String[] inserts = new String[1];
        inserts[0] = insert;
        return insert(inserts, true)[0];
    }

    /**
     * Creates inserts for the results of the select.
     * @param insert Something like "insert into web_complete_Document (form_number, transaction_status, web_doc_uid)"
     * @param select Something like "select form_number, transaction_status, 4321 from web_complete_Document where web_doc_uid = 1234"
     * @author James Stauffer
     */
    private int[] selectInsert(String insert, String select) throws SQLException {
        DbResult result = null;
        try {
            Vector inserts = new Vector();
            Vector values;
            result = query(select);
            ResultSetMetaData metaData = result.getMetaData();
            int columns = metaData.getColumnCount();
            while(result.next()) {
                values = new Vector();
                for(int col = 1; col <= columns; col++) {
                    Object obj = result.getObject(col);
                    if(obj instanceof String) {
                        values.addElement("'" + obj + "'");
                    } else {
                        values.addElement(obj.toString());
                    }
                }
                inserts.addElement(insert + " values(" + Util.ToString(values, false, ", ") + ")");
            }
            DbResult.Close(result);
            if(inserts.size() > 0) {
                return insert(Util.ToStringArray(inserts), true);
            } else {// nothing returned by the select
                return new int[0];
            }
        } finally {
            DbResult.Close(result);
        }
    }

    /**
     * @param returnID false means that no extra work(db call) will be done to find the id
     * @author James Stauffer(Modified)
     */
    private int[] insert(String inserts[], boolean returnID) throws SQLException {
        DbConnectionAttributes connectionAttributes = getConnectionAttributes();
        connectionAttributes.init(Util.ToString(inserts, false, " "));
        logStart(connectionAttributes);
        try {
            int[] id = new int[inserts.length];
            connectionAttributes.executeUpdate();

            if(returnID) {//To get the last identity value, use the @@IDENTITY global variable.
                ResultSet rs = connectionAttributes.executeQuery("select @@identity id");
                rs.next();
                id[0] = rs.getInt("id");
                rs.close();
            }

            connectionBroker.release(connectionAttributes);
            return id;
        } catch(SQLException e) {
            if((e.getMessage() != null) && (e.getMessage().indexOf(CONNECTION_ERROR) >= 0)) {
                connectionBroker.disconnectAll();
            } else {
                connectionBroker.release(connectionAttributes);
            }
            throw e;
        } finally {
            logEnd(connectionAttributes);
            connectionAttributes.reset();
        }
    }

    /***
     * Creates a prepared statement using the connection managed by the database manager
     *
     * @author Michael Lee
     * @see <a href="http://java.sun.com/docs/books/tutorial/jdbc/basics/prepared.html">JDBC Prepared Statements Tutorial</a>
     */
    private PreparedStatement prepareStatement(String query) throws SQLException {
        DbConnectionAttributes connectionAttributes = getConnectionAttributes();
        connectionAttributes.init(query);
        logStart(connectionAttributes);
        try {
            return connectionAttributes.prepareStatement();
        } finally {
            logEnd(connectionAttributes);
        }
    }

    public static PreparedStatement PrepareStatement(String query) throws SQLException {
        return DefaultDatabase.prepareStatement(query);
    }

    /**
     * Excecutes a prepared statement and returns a DbResult.  It assumes that any parameters have been properly
     * filled into the prepared statement.
     *
     * @author Michael Lee
     * @see <a href="http://java.sun.com/docs/books/tutorial/jdbc/basics/prepared.html">JDBC Prepared Statements Tutorial</a>
     * @param recursive true if there are some conditions where if the query fails we should retry the query.
     */
    private DbResult preparedStatement(PreparedStatement statement, boolean recursive) throws SQLException {
        DbConnectionAttributes connectionAttributes = getConnectionAttributes();
        connectionAttributes.init(statement.toString());
        logStart(connectionAttributes);
        try {

            try {
               return new DbResult(connectionAttributes, statement.executeQuery(), connectionBroker);
            } catch(SQLException e) {
                e.printStackTrace();
                if((e.getMessage() != null) && (e.getMessage().indexOf(CONNECTION_ERROR) >= 0)) {
                    connectionBroker.disconnectAll();
                    if(recursive) {//Try one more time
                       return preparedStatement(statement, false);
                    }
                } else {
                    connectionBroker.release(connectionAttributes);
                }
                throw e;
            }
        } finally {
            logEnd(connectionAttributes);
        }
    }

    /**
     * Excecutes a prepared statement and returns a DbResult.  It assumes that any parameters have been properly
     * filled into the prepared statement.
     *
     * @author Michael Lee
     * @see <a href="http://java.sun.com/docs/books/tutorial/jdbc/basics/prepared.html">JDBC Prepared Statements Tutorial</a>
     */
    private DbResult preparedStatement(PreparedStatement statement) throws SQLException {
       return preparedStatement(statement, true);
    }

    public static DbResult PreparedStatement(PreparedStatement statement) throws SQLException {
       return DefaultDatabase.preparedStatement(statement, true);
    }


    public static String Escape(String s) {
        int index = s.indexOf(ESCAPE_CHAR);
        if (index != -1) {
            s = s.substring(0, index) + ESCAPE_CHAR + ESCAPE_CHAR + Escape(s.substring(index+1));
        }
        return s;
    }

    public static DbConnectionBroker GetConnectionBroker() {
        return DefaultDatabase.connectionBroker;
    }

    public static String GetTableName(String insertQuery) {
        StringTokenizer st = new StringTokenizer(insertQuery, "( ");
        String dummy = st.nextToken();
        dummy = st.nextToken();
        return st.nextToken();
    }

    public String toString() {
        return getClass().getName() + "[ connectionBroker=" + connectionBroker
            + "]";
    }

    public static DbConnectionAttributes GetConnectionAttributes() throws SQLException {
        return DefaultDatabase.connectionBroker.getConnectionAttributes();
    }

    private DbConnectionAttributes getConnectionAttributes() throws SQLException {
        return connectionBroker.getConnectionAttributes();
    }

    private void logStart(DbConnectionAttributes ca) throws SQLException {
        if(log != null) {
            log.log("Start \t" + ca.getCreationTime() + " \t" + ca.getIndex()
                + " \t" + Logger.FormatDate(ca.getCreationTime()) + "\t" + ca.getQuery());
        }
    }

    private void logEnd(DbConnectionAttributes ca) throws SQLException {
        if(log != null) {
            log.log("End \t" + ca.getCreationTime() + " \t" + (System.currentTimeMillis() - ca.getCreationTime()));
        }
    }

    private DbConnectionBroker connectionBroker;
    private Logger log;
    private static Database DefaultDatabase;
    private static final char ESCAPE_CHAR = '\'';
    private static final String CONNECTION_ERROR = "Connection reset by peer";

    static {
    	try {
    		DefaultDatabase = new Database();
    	} catch(Throwable e) {
    			e.printStackTrace(System.out);
    	}
    }

}

