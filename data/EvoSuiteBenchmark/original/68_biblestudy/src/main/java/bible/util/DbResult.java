package bible.util;

import java.sql.*;

/**
 * Models the result for a SQL query
 *
 * @author  Umesh Berry
 */
public class DbResult
{
    public DbResult(DbConnectionAttributes connectionAttributes, ResultSet resultSet, DbConnectionBroker connectionBroker)
    {
        this.connectionAttributes = connectionAttributes;
        this.resultSet = resultSet;
        this.connectionBroker = connectionBroker;
        this.currentRow = 0;
    }

    public boolean next() throws SQLException {
        try {
            currentRow++;
            return resultSet.next();
        } catch(SQLException e) {
            throw getException(e, null);
        }
    }

    public boolean getBoolean(String columnName) throws SQLException {
        try {
            return resultSet.getBoolean(columnName);
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    public double getDouble(String columnName) throws SQLException {
        try {
            return resultSet.getDouble(columnName);
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    public int getInt(String columnName) throws SQLException {
        try {
            return resultSet.getInt(columnName);
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    public int getInt(int columnNumber) throws SQLException {
        try {
            return resultSet.getInt(columnNumber);
        } catch(SQLException e) {
            throw getException(e, String.valueOf(columnNumber));
        }
    }

    public String getString(String columnName) throws SQLException {
        try {
            String result = resultSet.getString(columnName);
            if (result == null) {
                result = "";
            }
            return result.trim();
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    /**
     * @author James Stauffer
     */
    public Object getObject(int columnNumber) throws SQLException {
        try {
            Object result = resultSet.getObject(columnNumber);
            if (result == null) {
                result = "";
            }
            return result;
        } catch(SQLException e) {
            throw getException(e, String.valueOf(columnNumber));
        }
    }

    /**
     * @author James Stauffer
     */
    public Object getObject(String columnName) throws SQLException {
        try {
            Object result = resultSet.getObject(columnName);
            if (result == null) {
                result = "";
            }
            return result;
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    public Timestamp getTimestamp(String columnName) throws SQLException {
        try {
            return resultSet.getTimestamp(columnName);
        } catch(SQLException e) {
            throw getException(e, columnName);
        }
    }

    /**
     * @author James Stauffer
     */
    public ResultSetMetaData getMetaData() {
      try {
          return resultSet.getMetaData();
      } catch(SQLException e) {
          return null;
      }
    }

    /**
     * @author James Stauffer
     */
    public String getQuery() {
        return connectionAttributes.getQuery();
    }

    /**
     * @author James Stauffer
     */
    public SQLWarning getWarnings() {
      try {
          return resultSet.getWarnings();
      } catch(SQLException e) {
          return null;
      }
    }

    public void close() throws SQLException {
        resultSet.close();
        connectionBroker.release(connectionAttributes);
    }


    /**
     * Allows closing even if null.
     * @author James Stauffer
     */
    public static void Close(DbResult result) {
        if(result != null) {
            try {
                result.close();
            } catch(SQLException se) {
            }
        }
    }

    public String toString() {
        return getClass().getName() + ":[currentRow=" + currentRow 
            + ", connectionAttributes=" + connectionAttributes 
            + ", resultSet=" + resultSet
            + "]";
    }

    public String toShortString() {
        return getClass().getName() + ":[" + currentRow 
            + " query=" + connectionAttributes.toShortString()
            + "]";
    }

    private int currentRow;// counter of current row for debugging purposes
    private DbConnectionAttributes connectionAttributes;
    private ResultSet resultSet;
    private DbConnectionBroker connectionBroker;

    private SQLException getException(SQLException e, String column) throws SQLException{
        close();
        return new SQLException(column + ": " + toShortString() + "\r\n" + Util.ToString(e));
    }
}

