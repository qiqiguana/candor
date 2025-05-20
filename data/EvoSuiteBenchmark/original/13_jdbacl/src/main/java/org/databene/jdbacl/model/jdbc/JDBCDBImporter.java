/*
 * (c) Copyright 2006-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.jdbacl.model.jdbc;

import org.databene.commons.ConnectFailedException;
import org.databene.commons.ErrorHandler;
import org.databene.commons.Escalator;
import org.databene.commons.Filter;
import org.databene.commons.ImportFailedException;
import org.databene.commons.LoggerEscalator;
import org.databene.commons.ObjectNotFoundException;
import org.databene.commons.ProgrammerError;
import org.databene.commons.StringUtil;
import org.databene.commons.Level;
import org.databene.commons.collection.OrderedNameMap;
import org.databene.commons.version.VersionNumber;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.DatabaseDialect;
import org.databene.jdbacl.DatabaseDialectManager;
import org.databene.jdbacl.JDBCConnectData;
import org.databene.jdbacl.dialect.OracleDialect;
import org.databene.jdbacl.model.DBSequence;
import org.databene.jdbacl.model.FKChangeRule;
import org.databene.jdbacl.model.DBCatalog;
import org.databene.jdbacl.model.DBCheckConstraint;
import org.databene.jdbacl.model.DBColumn;
import org.databene.jdbacl.model.DBDataType;
import org.databene.jdbacl.model.DBForeignKeyConstraint;
import org.databene.jdbacl.model.DBMetaDataImporter;
import org.databene.jdbacl.model.DBIndex;
import org.databene.jdbacl.model.DBNonUniqueIndex;
import org.databene.jdbacl.model.DBPrimaryKeyConstraint;
import org.databene.jdbacl.model.DBSchema;
import org.databene.jdbacl.model.DBTable;
import org.databene.jdbacl.model.DBUniqueConstraint;
import org.databene.jdbacl.model.DBUniqueIndex;
import org.databene.jdbacl.model.Database;
import org.databene.jdbacl.model.DefaultDBColumn;
import org.databene.jdbacl.model.DefaultDBTable;
import org.databene.jdbacl.model.DefaultDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Imports a DB model via JDBC.<br/><br/>
 * Created: 06.01.2007 19:16:45
 * @author Volker Bergmann
 */
public final class JDBCDBImporter implements DBMetaDataImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCDBImporter.class);

    String url;
    String driver;
    String password;
    final String user;
    String catalogName;
    String schemaName;
    Pattern includeTables;
    Pattern excludeTables;
    boolean importingIndexes;
	boolean importingUKs = true;
	boolean importingSequences = false;
	boolean importingChecks = true;
	boolean importingTriggers = false;
	boolean importingPackages = false;
	boolean lazy = false;
	
    Connection _connection;
    DatabaseDialect dialect;
    String databaseProductName;

    Escalator escalator = new LoggerEscalator();
    ErrorHandler errorHandler;
    TableNameFilter tableNameFilter;

	DatabaseMetaData metaData;
	DefaultDatabase database;

    public JDBCDBImporter(String environment) {
        this(DBUtil.getConnectData(environment));
    }

    public JDBCDBImporter(JDBCConnectData cd) {
    	this(cd.url, cd.driver, cd.user, cd.password, cd.catalog, cd.schema);
	}

    public JDBCDBImporter(String url, String driver, String user, String password, String catalog, String schema) {
        this(url, driver, user, password, catalog, schema, ".*", true);
    }

    public JDBCDBImporter(String url, String driver, String user, String password, String catalog, String schema, 
    		String tablePattern, boolean importingIndexes) {
    	this._connection = null;
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.catalogName = catalog;
        this.schemaName = schema;
        this.includeTables = Pattern.compile(tablePattern != null ? tablePattern : ".*");
        this.importingIndexes = importingIndexes;
        this.errorHandler = new ErrorHandler(getClass().getName(), Level.error);
    }

    public JDBCDBImporter(Connection connection, String user, String schemaName) {
    	this._connection = connection;
        this.user = user;
        this.schemaName = schemaName;
        this.errorHandler = new ErrorHandler(getClass().getName(), Level.error);
        this.importingIndexes = true;
    }

    // properties ------------------------------------------------------------------------------------------------------
    
	/**
     * @return the productName
     */
    public String getDatabaseProductName() {
        return databaseProductName;
    }
    
    public void setFaultTolerant(boolean faultTolerant) {
    	this.errorHandler = new ErrorHandler(getClass().getName(), (faultTolerant ? Level.warn : Level.error));
    }

	public Connection getConnection() throws ConnectFailedException {
		if (this._connection == null)
			this._connection = DBUtil.connect(url, driver, user, password, true);
	    return this._connection;
    }

    @Deprecated
	public void setTablePattern(Pattern tablePattern) {
    	this.includeTables = tablePattern;
    }

	public void setIncludeTables(String includeTables) {
    	this.includeTables = (StringUtil.isEmpty(includeTables) ? null : Pattern.compile(includeTables));
    }

	public void setExcludeTables(String excludeTables) {
    	this.excludeTables = (StringUtil.isEmpty(excludeTables) ? null : Pattern.compile(excludeTables));
    }

	public boolean isImportingIndexes() {
    	return importingIndexes;
    }

	public void setImportingIndexes(boolean importingIndexes) {
    	this.importingIndexes = importingIndexes;
    }

	public void setImportingUKs(boolean importingUKs) {
		this.importingUKs  = importingUKs;
    }
	
	public void setImportingChecks(boolean importingChecks) {
		this.importingChecks = importingChecks;
	}
	
	public void setImportingSequences(boolean importingSequences) {
		this.importingSequences = importingSequences;
	}
	
	public void setImportingTriggers(boolean importingTriggers) {
		this.importingTriggers = importingTriggers;
	}

	public boolean isImportingPackages() {
		return importingPackages;
	}

	public void setImportingPackages(boolean importingPackages) {
		this.importingPackages = importingPackages;
	}

	public void setSchemaName(String schemaName) {
	    this.schemaName = schemaName;
    }
    
    public void setCatalogName(String catalogName) {
	    this.catalogName = catalogName;
    }
    
	public void setLazy(boolean lazy) {
    	this.lazy = lazy;
    }

	// functional interface --------------------------------------------------------------------------------------------
	
	public Database importDatabase() throws ConnectFailedException, ImportFailedException {
		if (!lazy)
			LOGGER.info("Importing database metadata. Be patient, this may take some time...");
        long startTime = System.currentTimeMillis();
        tableNameFilter = new TableNameFilter();
        try {
            metaData = getConnection().getMetaData();
            databaseProductName = metaData.getDatabaseProductName();
            VersionNumber productVersion = VersionNumber.valueOf(metaData.getDatabaseMajorVersion() + "." + 
            		metaData.getDatabaseMinorVersion());
            LOGGER.debug("Product: {} {}", databaseProductName, productVersion);
            dialect = DatabaseDialectManager.getDialectForProduct(databaseProductName, productVersion);
            if (isOracle()) // fix for Oracle varchar column size, see http://kr.forums.oracle.com/forums/thread.jspa?threadID=554236
            	DBUtil.executeUpdate("ALTER SESSION SET NLS_LENGTH_SEMANTICS=CHAR", getConnection());
            database = new DefaultDatabase(databaseProductName, databaseProductName, productVersion);
        	database.setImportDate(new Date());
        	database.setUser(user);
        	database.setTableInclusionPattern(includeTables != null ? includeTables.pattern() : null);
        	database.setTableExclusionPattern(excludeTables != null ? excludeTables.pattern() : null);
        	database.setImportedChecks(importingChecks);
        	database.setImportedUKs(importingUKs);
        	database.setImportedIndexes(importingIndexes);
        	database.setImportedSequences(importingSequences);
            importCatalogs();
            importSchemas();
            importTables();
            if (importingSequences && dialect.isSequenceSupported())
            	importSequences();
            if (!lazy) {
            	importColumns();
	            if (importingIndexes || importingUKs)
	                importIndexes();
            	importPrimaryKeys();
	            importImportedKeys();
	            if (importingChecks)
	            	importChecks();
            }
            if (importingTriggers)
            	importTriggers();
            if (importingPackages)
            	importPackages();
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Imported" + (lazy ? " core" : "") + " database metadata within " + duration + " ms.");
            return database;
        } catch (SQLException e) {
            throw new ImportFailedException(e);
        }
    }

	public void close() {
        DBUtil.close(_connection);
	}
	
	// private helper methods ------------------------------------------------------------------------------------------

	private void importCatalogs() throws SQLException, ConnectFailedException {
        LOGGER.debug("Importing catalogs");
        ResultSet catalogSet = metaData.getCatalogs();
        int catalogCount = 0;
        while (catalogSet.next()) {
            String foundCatalog = catalogSet.getString(1);
            LOGGER.debug("found catalog '" + foundCatalog + "'");
            if (StringUtil.equalsIgnoreCase(foundCatalog, this.catalogName) // this is the configured catalog
            		|| (StringUtil.isEmpty(this.catalogName) && ( // no catalog configured but...
            				dialect.isDefaultCatalog(foundCatalog, user) // ...the one found is the default for the database
            				|| foundCatalog.equalsIgnoreCase(getConnection().getCatalog()) // or for the connection
            		))) {
                this.catalogName = foundCatalog;
                database.addCatalog(new DBCatalog(foundCatalog));
                catalogCount++;
            }
        }
        if (catalogCount == 0)
            database.addCatalog(new DBCatalog(null));
        catalogSet.close();
    }

    private void importSchemas() throws SQLException {
        LOGGER.debug("Importing schemas");
        int schemaCount = 0;
        ResultSet schemaSet = metaData.getSchemas();
        while (schemaSet.next()) {
            String schemaName = schemaSet.getString(1);
            String catalogName = null;
            int columnCount = schemaSet.getMetaData().getColumnCount();
			if (columnCount >= 2)
            	catalogName = schemaSet.getString(2);
            if (schemaName.equalsIgnoreCase(this.schemaName) 
            		|| (this.schemaName == null && dialect.isDefaultSchema(schemaName, user))) {
	            LOGGER.debug("importing schema '{}'", schemaName);
	        	this.schemaName = schemaName; // take over capitalization used in the DB
	            DBSchema schema = new DBSchema(schemaName);
	            String catalogNameOfSchema = (columnCount >= 2 && catalogName != null ? catalogName : this.catalogName); // PostgreSQL and SQL Server do not necessarily tell you the catalog name
            	DBCatalog catalogOfSchema = database.getCatalog(catalogNameOfSchema);
            	if (catalogOfSchema != null)
            		catalogOfSchema.addSchema(schema);
            	else
            		throw new ObjectNotFoundException("Catalog not found: " + catalogOfSchema);
	            schemaCount++;
            } else
                LOGGER.debug("ignoring schema {}", schemaName);
        }
        if (schemaCount == 0) {
        	// add a default schema if none was reported (e.g. by MySQL)
        	DBCatalog catalogToUse = database.getCatalog(catalogName);
        	if (catalogToUse == null)
        		catalogToUse = database.getCatalogs().get(0);
       		catalogToUse.addSchema(new DBSchema(null));
        }
        schemaSet.close();
    }

	private void importTables() throws SQLException {
        LOGGER.info("Importing tables");
        ResultSet tableSet = metaData.getTables(catalogName, schemaName, null, new String[] { "TABLE", "VIEW" });
        List<DBTable> importedTables = new ArrayList<DBTable>();
        while (tableSet.next()) {

            // parsing ResultSet line
            String tCatalogName = tableSet.getString(1);
            String tSchemaName = tableSet.getString(2);
            String tableName = tableSet.getString(3);
            if (tableName.startsWith("BIN$")) {
                if (isOracle() && tableName.startsWith("BIN$"))
                    escalator.escalate("BIN$ table found (for improved performance " +
                                "execute 'PURGE RECYCLEBIN;')", this, tableName);
                continue;
            }
            if (!tableSupported(tableName)) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("ignoring table: " + tCatalogName + ", " + tSchemaName + ", " + tableName);
            	continue;
            }
            String tableType = tableSet.getString(4); // Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
            String tableRemarks = tableSet.getString(5);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("importing table: " + tCatalogName + ", " + tSchemaName + ", " + tableName + ", " + tableType + ", " + tableRemarks);

            DBCatalog catalog = database.getCatalog(tCatalogName);
			DBSchema schema;
            if (catalog != null) {
            	// that's the expected way
            	schema = catalog.getSchema(tSchemaName);
            } else {
            	// postgres returns no catalog info, so we need to search for the schema in the whole database
            	schema = database.getSchema(tSchemaName);
            	catalog = schema.getCatalog();
            }
			if (catalog != null && schema != null) {
				DBTable table = createTable(catalog, schema, tableName, tableRemarks, lazy);
	            importedTables.add(table);
			} else {
				LOGGER.warn("ignoring table " + tableName);
			}
        }
        if (!lazy)
        	for (DBTable table : importedTables)
        		importTableDetails((DefaultDBTable) table);
        tableSet.close();
    }

	DefaultDBTable importTable(DBCatalog catalog, DBSchema schema, String tableName, String remarks) {
		DefaultDBTable table = (DefaultDBTable) createTable(catalog, schema, tableName, remarks, false);
		importTableDetails(table);
        return table;
    }

	private void importTableDetails(DefaultDBTable table) {
	    importColumns(table.getCatalog(), table.getSchema().getName(), table.getName(), null, null);
	    importPrimaryKeys(table);
	    importImportedKeys(table);
	    importRefererTables(table);
    }

	private DBTable createTable(DBCatalog catalog, DBSchema schema, String tableName, String remarks, boolean lazy) {
	    DBTable table;
		if (lazy) {
			table = new LazyTable(this, schema, tableName, remarks);
		} else {
			DefaultDBTable dTable = new DefaultDBTable(tableName, schema);
			dTable.setDoc(remarks);
			table = dTable;
		}
	    return table;
    }

	private boolean isOracle() {
		return databaseProductName.toLowerCase().startsWith("oracle");
	}

    private void importColumns() {
		if (this.catalogName != null)
			importColumns(database.getCatalog(this.catalogName), this.schemaName, null, tableNameFilter, errorHandler);
		else
			for (DBCatalog catalog : database.getCatalogs()) {
				for (DBSchema schema : catalog.getSchemas())
					importColumns(catalog, schema.getName(), null, tableNameFilter, errorHandler);
			}
    }

    private void importColumns(DBCatalog catalog, String schemaName, String tablePattern, Filter<String> tableFilter, ErrorHandler errorHandler) {
        String catalogName = catalog.getName();
        String schemaPattern = (schemaName != null ? schemaName : (catalog.getSchemas().size() == 1 ? catalog.getSchemas().get(0).getName() : null));
        LOGGER.debug("Importing columns for " +
        		"catalog " + StringUtil.quoteIfNotNull(catalogName) + ", " +
        		"schemaPattern " + StringUtil.quoteIfNotNull(schemaName) + ", " +
        		"tablePattern '" + StringUtil.quoteIfNotNull(tablePattern) + "'");
        ResultSet columnSet = null;
        try {
        	columnSet = metaData.getColumns(catalogName, schemaPattern, tablePattern, null);
	        ResultSetMetaData setMetaData = columnSet.getMetaData();
	        if (setMetaData.getColumnCount() == 0)
	            return;
	        while (columnSet.next()) {
	            String colSchemaName = columnSet.getString(2);
	            String tableName = columnSet.getString(3);
	            String columnName = columnSet.getString(4);
	            if (tableName.startsWith("BIN$") || (tableFilter != null && !tableFilter.accept(tableName))) {
	            	if (LOGGER.isDebugEnabled())
	            		LOGGER.debug("ignoring column " + catalogName + "." + colSchemaName + "." + tableName + "." + columnName);
	                continue;
	            }
	            int sqlType = columnSet.getInt(5);
	            String columnType = columnSet.getString(6);
	            Integer columnSize = columnSet.getInt(7);
	            if (columnSize == 0) // happens with INTEGER values on HSQLDB
	            	columnSize = null;
	            int decimalDigits = columnSet.getInt(9);
	            boolean nullable = columnSet.getBoolean(11);
	            String comment = columnSet.getString(12);
	            String defaultValue = columnSet.getString(13);

	            // Bug fix 3075401: boolean value generation problem in postgresql 8.4
	            if (sqlType == Types.BIT && "bool".equals(columnType.toLowerCase()) && databaseProductName.toLowerCase().startsWith("postgres")) {
	            	sqlType = Types.BOOLEAN;
	            }
	            
	            if (LOGGER.isDebugEnabled())
	            	LOGGER.debug("found column: " + catalogName + ", " + colSchemaName + ", " + tableName + ", "
	                        + columnName + ", " + sqlType + ", " + columnType + ", " + columnSize + ", " + decimalDigits
	                        + ", " + nullable + ", " + comment + ", " + defaultValue);
	
	            DBTable table = catalog.getTable(tableName, false);
	            if (table == null) {
	            	LOGGER.debug("Ignoring column {}.{}", tableName, columnName);
	            	continue; // PostgreSQL returns the columns of indexes, too
	            }
	            DBSchema schema = catalog.getSchema(schemaName);
	            if (schema != null)
	                table = (DefaultDBTable) schema.getTable(tableName);

	            Integer fractionDigits = (decimalDigits > 0 ? decimalDigits : null);
	            DefaultDBColumn column = new DefaultDBColumn(columnName, table, DBDataType.getInstance(sqlType, columnType), columnSize, fractionDigits);
	            if (!StringUtil.isEmpty(comment))
	                column.setDoc(comment);
	            if (!StringUtil.isEmpty(defaultValue)) {
	                if (!column.getType().isAlpha())
	                    defaultValue = removeBrackets(defaultValue); // some driver adds brackets to number defaults
	                column.setDefaultValue(defaultValue.trim()); // oracle thin driver produces "1 "
	            }
	            if (!nullable)
	                column.setNullable(false);
	
	            // not used: importVersionColumnInfo(catalogName, table, metaData);
	        }
    	} catch (SQLException e) {
    		// possibly we try to access a catalog to which we do not have access rights
    		if (errorHandler == null)
    			errorHandler = new ErrorHandler(getClass());
    		errorHandler.handleError("Error in parsing columns of catalog " + catalog.getName(), e);
        } finally {
        	DBUtil.close(columnSet);
        }
    }

    private static String removeBrackets(String defaultValue) {
        if (StringUtil.isEmpty(defaultValue))
            return defaultValue;
        if (!defaultValue.startsWith("(") || !defaultValue.endsWith(")"))
            return defaultValue;
        return removeBrackets(defaultValue.substring(1, defaultValue.length() - 1));
    }

    
/*
    private void importVersionColumnInfo(DBCatalog catalogName, DBTable table, DatabaseMetaData metaData) throws SQLException {
        ResultSet versionColumnSet = metaData.getVersionColumns(catalogName.getName(), null, table.getName());
//        DBUtil.print(versionColumnSet);
        while (versionColumnSet.next()) {
            // short scope = versionColumnSet.getString(1);
            String columnName = versionColumnSet.getString(2);
            //int dataType = versionColumnSet.getInt(3);
            //String typeName = versionColumnSet.getString(4);
            //int columnSize = versionColumnSet.getInt(5);
            //int bufferLength = versionColumnSet.getInt(6);
            //short decimalDigits = versionColumnSet.getShort(7);
            //short pseudoColumn = versionColumnSet.getShort(8);
            DBColumn column = table.getColumn(columnName);
            column.setVersionColumn(true);
        }
    }
*/
    private void importPrimaryKeys() {
        int count = 0;
        DBSchema schema = database.getCatalog(catalogName).getSchema(schemaName);
        if (schema != null)
            for (DBTable table : schema.getTables()) {
                if (!tableSupported(table.getName()))
                	continue;
                importPrimaryKeys((DefaultDBTable) table);
                count++;
            }
        if (count > 0)
            return;
        DBCatalog catalog = database.getCatalog(catalogName);
        if (catalog != null)
            for (DBTable table : catalog.getTables()) {
                if (!tableSupported(table.getName()))
                	continue;
                importPrimaryKeys((DefaultDBTable) table);
            }
    }

    private void importPrimaryKeys(DefaultDBTable table) {
        LOGGER.debug("Importing primary keys for table " + table);
        ResultSet pkset = null;
        try {
	        pkset = metaData.getPrimaryKeys(catalogName, schemaName, table.getName());
	        TreeMap<Short, String> pkComponents = new TreeMap<Short, String>();
	        String pkName = null;
	        while (pkset.next()) {
	        	String tableName = pkset.getString(3);
	            if (!tableName.equals(table.getName())) // Bug fix for Firebird: 
	            	continue;							// When querying X, it returns the pks of XY too
	
	            String columnName = pkset.getString(4);
	            short keySeq = pkset.getShort(5);
	            pkComponents.put(keySeq, columnName);
	            pkName = pkset.getString(6);
	            if (LOGGER.isDebugEnabled())
	                LOGGER.debug("found pk column " + columnName + ", " + keySeq + ", " + pkName);
	        }
	        if (pkComponents.size() > 0) {
		        String[] columnNames = pkComponents.values().toArray(new String[pkComponents.size()]);
		        DBPrimaryKeyConstraint constraint = new DBPrimaryKeyConstraint(table, pkName, dialect.isDeterministicPKName(pkName), columnNames);
		        table.setPrimaryKey(constraint);
		        for (String columnName : columnNames) {
		        	DBColumn column = table.getColumn(columnName);
		            column.addUkConstraint(constraint);
		        }
	        }
        } catch (SQLException e) {
        	errorHandler.handleError("Error importing primary key of table " + table.getName());
        } finally {
        	DBUtil.close(pkset);
        }
    }

    private void importIndexes() {
        for (DBCatalog catalog : database.getCatalogs())
       		importIndexes(catalog, metaData);
    }

	private void importIndexes(DBCatalog catalog, DatabaseMetaData metaData) {
	    for (DBSchema schema : catalog.getSchemas()) {
	        LOGGER.debug("Importing indexes for schema '" + schema.getName() + "'");
	        OrderedNameMap<DBIndexInfo> indexes = new OrderedNameMap<DBIndexInfo>();
	        ResultSet indexSet = null;
	        try {
		        indexSet = metaData.getIndexInfo(catalog.getName(), schema.getName(), null, !importingIndexes, false);
		        //DBUtil.print(indexSet);
		        while (indexSet.next()) {
		            String indexName = null;
		            try {
		            	String tableName = indexSet.getString(3);
		    	    	if (!tableSupported(tableName))
		    	    		continue;
		                boolean unique = !indexSet.getBoolean(4);
		                String indexCatalogName = indexSet.getString(5);
		                indexName = indexSet.getString(6);
		                short indexType = indexSet.getShort(7);
		                // not used: 
		                // tableIndexStatistic - this identifies table statistics that are returned in conjuction with a table's index descriptions
		                // tableIndexClustered - this is a clustered index
		                // tableIndexHashed - this is a hashed index
		                // tableIndexOther - this is some other style of index
		                //
		                short ordinalPosition = indexSet.getShort(8);
		                if (ordinalPosition == 0)
		                    continue;
		                String columnName = indexSet.getString(9);
		                String ascOrDesc = indexSet.getString(10);
		                Boolean ascending = (ascOrDesc != null ? ascOrDesc.charAt(0) == 'A' : null);
		                int cardinality = indexSet.getInt(11);
		                int pages = indexSet.getInt(12);
		                String filterCondition = indexSet.getString(13);
		                if (LOGGER.isDebugEnabled())
		                    LOGGER.debug("found " + (unique ? "unique index " : "index ") + indexName + ", " 
		                            + indexCatalogName + ", " + indexType + ", " 
		                            + ordinalPosition + ", " + columnName + ", " + ascOrDesc + ", " 
		                            + cardinality + ", " + pages + ", " + filterCondition);
		                DBIndexInfo index = indexes.get(indexName);
		                if (index == null) {
		                    index = new DBIndexInfo(indexName, tableName, indexType, indexCatalogName, unique,
		                        ordinalPosition, columnName,
		                        ascending, cardinality, pages, filterCondition);
		                    indexes.put(indexName, index);
		                } else {
		                    index.addColumn(ordinalPosition, columnName);
		                }
		            } catch (Exception e) {
		            	LOGGER.error("Error parsing indexes: ", e);
		            }
		        }
	        } catch (SQLException e) {
        		// possibly we try to query a catalog to which we do not have access rights
        		errorHandler.handleError("Error parsing index data of schema " + schema.getName(), e);
			} finally {
	        	DBUtil.close(indexSet);
	        }
	        for (DBIndexInfo indexInfo : indexes.values()) {
                DBIndex index = null;
	            try {
	            	DBTable table = schema.getTable(indexInfo.tableName);
                    boolean deterministicName = dialect.isDeterministicIndexName(indexInfo.name);
	                if (indexInfo.unique) {
	                	DBPrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
	                	boolean isPK = (pk != null && StringUtil.equalsIgnoreCase(indexInfo.columnNames, pk.getColumnNames()));
	                	DBUniqueConstraint constraint;
	                	if (isPK) {
	                		constraint = pk;
	                	} else {
	                		constraint = new DBUniqueConstraint(table, indexInfo.name, dialect.isDeterministicUKName(indexInfo.name), indexInfo.columnNames);
		                    ((DefaultDBTable) table).addUniqueConstraint(constraint);
	                	}
						index = new DBUniqueIndex(indexInfo.name, deterministicName, constraint);
		                ((DefaultDBTable) table).addIndex(index);
	                } else {
	                    index = new DBNonUniqueIndex(indexInfo.name, deterministicName, table, indexInfo.columnNames);
		                ((DefaultDBTable) table).addIndex(index);
	                }
	            } catch (ObjectNotFoundException e) {
	                LOGGER.error("Error parsing index: " + index, e);
	            }
	        }
	    }
    }

	/*
	private void importIndexes(DBCatalog catalog, DatabaseMetaData metaData) {
	    for (DBTable table : catalog.getTables()) {
	    	if (!tableSupported(table.getName()))
	    		continue;
	        LOGGER.debug("Importing indexes for table '" + table.getName() + "'");
	        OrderedNameMap<DBIndexInfo> tableIndexes = new OrderedNameMap<DBIndexInfo>();
	        ResultSet indexSet = null;
	        try {
		        indexSet = metaData.getIndexInfo(catalog.getName(), table.getSchema().getName(), table.getName(), !importingIndexes, false);
		        //DBUtil.print(indexSet);
		        while (indexSet.next()) {
		            String indexName = null;
		            try {
		                boolean unique = !indexSet.getBoolean(4);
		                String indexCatalogName = indexSet.getString(5);
		                indexName = indexSet.getString(6);
		                short indexType = indexSet.getShort(7);
		                // not used: 
		                // tableIndexStatistic - this identifies table statistics that are returned in conjuction with a table's index descriptions
		                // tableIndexClustered - this is a clustered index
		                // tableIndexHashed - this is a hashed index
		                // tableIndexOther - this is some other style of index
		                //
		                short ordinalPosition = indexSet.getShort(8);
		                if (ordinalPosition == 0)
		                    continue;
		                String columnName = indexSet.getString(9);
		                String ascOrDesc = indexSet.getString(10);
		                Boolean ascending = (ascOrDesc != null ? ascOrDesc.charAt(0) == 'A' : null);
		                int cardinality = indexSet.getInt(11);
		                int pages = indexSet.getInt(12);
		                String filterCondition = indexSet.getString(13);
		                if (LOGGER.isDebugEnabled())
		                    LOGGER.debug("found " + (unique ? "unique index " : "index ") + indexName + ", " 
		                            + indexCatalogName + ", " + indexType + ", " 
		                            + ordinalPosition + ", " + columnName + ", " + ascOrDesc + ", " 
		                            + cardinality + ", " + pages + ", " + filterCondition);
		                DBIndexInfo index = tableIndexes.get(indexName);
		                if (index == null) {
		                    index = new DBIndexInfo(indexName, indexType, indexCatalogName, unique,
		                        ordinalPosition, columnName,
		                        ascending, cardinality, pages, filterCondition);
		                    tableIndexes.put(indexName, index);
		                } else {
		                    index.addColumn(ordinalPosition, columnName);
		                }
		            } catch (Exception e) {
		            	LOGGER.error("Error parsing indexes: ", e);
		            }
		        }
	        } catch (SQLException e) {
        		// possibly we try to query a catalog to which we do not have access rights
        		errorHandler.handleError("Error parsing index data of table " + table.getName(), e);
			} finally {
	        	DBUtil.close(indexSet);
	        }
	        for (DBIndexInfo indexInfo : tableIndexes.values()) {
                DBIndex index = null;
	            try {
                    boolean deterministicName = dialect.isDeterministicIndexName(indexInfo.name);
	                if (indexInfo.unique) {
	                	DBPrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
	                	boolean isPK = (pk != null && StringUtil.equalsIgnoreCase(indexInfo.columnNames, pk.getColumnNames()));
	                	DBUniqueConstraint constraint;
	                	if (isPK) {
	                		constraint = pk;
	                	} else {
	                		constraint = new DBUniqueConstraint(table, indexInfo.name, dialect.isDeterministicUKName(indexInfo.name), indexInfo.columnNames);
		                    ((DefaultDBTable) table).addUniqueConstraint(constraint);
	                	}
						index = new DBUniqueIndex(indexInfo.name, deterministicName, constraint);
		                ((DefaultDBTable) table).addIndex(index);
	                } else {
	                    index = new DBNonUniqueIndex(indexInfo.name, deterministicName, table, indexInfo.columnNames);
		                ((DefaultDBTable) table).addIndex(index);
	                }
	            } catch (ObjectNotFoundException e) {
	                LOGGER.error("Error parsing index: " + index, e);
	            }
	        }
	    }
    }
    */
    

    private void importImportedKeys() {
        LOGGER.info("Importing imported keys");
        int count = 0;
        for (DBCatalog catalog : database.getCatalogs())
	        for (DBSchema schema : catalog.getSchemas())
	            for (DBTable table : schema.getTables()) {
	                if (!tableSupported(table.getName()))
	                	continue;
	                importImportedKeys((DefaultDBTable) table);
	                count++;
	            }
    }

    private void importChecks() throws SQLException, ConnectFailedException {
        LOGGER.info("Importing checks");
        int count = 0;
        if (dialect instanceof OracleDialect) {
	        for (DBCatalog catalog : database.getCatalogs())
		        for (DBSchema schema : catalog.getSchemas()) {
		        	OracleDialect oraDialect = (OracleDialect) dialect;
					DBCheckConstraint[] newChecks = oraDialect.queryCheckConstraints(getConnection(), schema.getName());
					for (DBCheckConstraint newCheck : newChecks) {
		                if (!tableSupported(newCheck.getTableName()))
		                	continue;
		                schema.getTable(newCheck.getTableName()).addCheckConstraint(newCheck);
					}
	                count++;
	            }
        }
    }

    private boolean tableSupported(String tableName) {
	    return tableNameFilter.accept(tableName);
    }

    private void importImportedKeys(DefaultDBTable table) {
        LOGGER.debug("Importing imported keys for table {}", table.getName());
        DBCatalog catalog = table.getCatalog();
        DBSchema schema = table.getSchema();
        String catalogName = (catalog != null ? catalog.getName() : null);
        String tableName = table.getName();
        String schemaName = (schema != null ? schema.getName() : null);
        ResultSet resultSet = null;
        try {
	        resultSet = metaData.getImportedKeys(catalogName, schemaName, tableName);
	        List<ImportedKey> keyList = new ArrayList<ImportedKey>();
	        Map<String, ImportedKey> keysByName = OrderedNameMap.createCaseIgnorantMap();
	        ImportedKey recent = null;
	        while (resultSet.next()) {
	            ImportedKey cursor = ImportedKey.parse(resultSet, catalog, schema, table);
	            if (cursor == null) 
	            	continue;
	            if (cursor.key_seq == 1) {
	            	if (cursor.fk_name != null)
	            		keysByName.put(cursor.fk_name, cursor);
            		keyList.add(cursor);
	            } else {
	            	// additional column for a composite FK with columns defined before
		            if (cursor.fk_name != null)
		            	keysByName.get(cursor.fk_name).addForeignKeyColumn(cursor.fkcolumn_name, cursor.pkcolumn_name);
		            else // some systems may not report an fk constraint name
		            	recent.addForeignKeyColumn(cursor.fkcolumn_name, cursor.pkcolumn_name);
	            }
	            recent = cursor;
	        }
	        // build DBForeignKeyConstraint objects from the gathered information
	        for (ImportedKey key : keyList) {
	            int n = key.getForeignKeyColumnNames().size();
	            String[] columnNames = new String[n];
	            String[] refereeColumnNames = new String[n];
				for (int i = 0; i < n; i++) {
	                columnNames[i] = key.getForeignKeyColumnNames().get(i); 
	                refereeColumnNames[i] = key.getRefereeColumnNames().get(i);
				}
	            DBForeignKeyConstraint foreignKeyConstraint = new DBForeignKeyConstraint(
	            		key.fk_name, dialect.isDeterministicFKName(key.fk_name), table, columnNames, key.getPkTable(), refereeColumnNames);
	            foreignKeyConstraint.setUpdateRule(parseRule(key.update_rule));
	            foreignKeyConstraint.setDeleteRule(parseRule(key.delete_rule));
	            if (LOGGER.isDebugEnabled())
	            	LOGGER.debug("Imported foreign key {}", foreignKeyConstraint);
	        }
        } catch (SQLException e) {
        	errorHandler.handleError("Error importing foreign key constraints", e);
        } finally {
	        DBUtil.close(resultSet);
        }
     }

	private FKChangeRule parseRule(short rule) {
		switch (rule) {
			case DatabaseMetaData.importedKeyNoAction:   return FKChangeRule.NO_ACTION;
			case DatabaseMetaData.importedKeyCascade:    return FKChangeRule.CASCADE;
			case DatabaseMetaData.importedKeySetNull:    return FKChangeRule.SET_NULL;
			case DatabaseMetaData.importedKeySetDefault: return FKChangeRule.SET_DEFAULT;
			case DatabaseMetaData.importedKeyRestrict:   return FKChangeRule.NO_ACTION;
			default: throw new ProgrammerError("Not a supported rule: " + rule);
		}
	}

	private void importRefererTables(DefaultDBTable table) {
        LOGGER.debug("Importing exported keys for table {}", table.getName());
        DBCatalog catalog = table.getCatalog();
        DBSchema schema = table.getSchema();
        String catalogName = (catalog != null ? catalog.getName() : null);
        String tableName = table.getName();
        String schemaName = (schema != null ? schema.getName() : null);
        ResultSet resultSet = null;
        try {
	        resultSet = metaData.getExportedKeys(catalogName, schemaName, tableName);
	        while (resultSet.next()) {
	            String fktable_cat = resultSet.getString(5);
	            String fktable_schem = resultSet.getString(6);
	            String fktable_name = resultSet.getString(7);
	            if (tableSupported(fktable_name)) {
		            DBTable referrer = database.getTable(fktable_name);
		            if (LOGGER.isDebugEnabled())
		            	LOGGER.debug("Imported referrer: " + referrer);
		            table.addReferrer(referrer);
	            }
	        }
        } catch (SQLException e) {
        	errorHandler.handleError("Error importing foreign key constraints", e);
        } finally {
	        DBUtil.close(resultSet);
        }
     }

    class TableNameFilter implements Filter<String> {
		public boolean accept(String tableName) {
			if (tableName.contains("$") || (excludeTables != null && excludeTables.matcher(tableName).matches()))
				return false;
		    return (includeTables == null || includeTables.matcher(tableName).matches());

        }
    }
    
	private void importSequences() {
		try {
			if (dialect.isSequenceSupported()) {
				DBSequence[] sequences = dialect.querySequences(getConnection());
				for (DBSequence sequence : sequences) {
					DBCatalog catalog = database.getCatalog(sequence.getCatalogName());
					if (catalog != null) {
						DBSchema schema = catalog.getSchema(sequence.getSchemaName());
						if (schema != null)
							schema.addSequence(sequence);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error importing sequences", e);
		}
	}

	private void importTriggers() throws SQLException {
		for (DBCatalog catalog : database.getCatalogs())
			for (DBSchema schema : catalog.getSchemas())
				importTriggers(schema);
	}

	private void importTriggers(DBSchema schema) throws SQLException {
		dialect.queryTriggers(schema, _connection);
	}

	private void importPackages() throws SQLException {
		for (DBCatalog catalog : database.getCatalogs())
			for (DBSchema schema : catalog.getSchemas())
				importPackages(schema);
	}

	private void importPackages(DBSchema schema) throws SQLException {
		dialect.queryPackages(schema, _connection);
	}


    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
	public String toString() {
	    return getClass().getSimpleName();
	}
	
}
