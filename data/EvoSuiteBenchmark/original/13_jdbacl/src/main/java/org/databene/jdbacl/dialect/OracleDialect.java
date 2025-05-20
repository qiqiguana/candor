/*
 * (c) Copyright 2008-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.jdbacl.dialect;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.databene.commons.ArrayBuilder;
import org.databene.commons.CollectionUtil;
import org.databene.commons.NameUtil;
import org.databene.commons.OrderedMap;
import org.databene.commons.StringUtil;
import org.databene.commons.converter.TimestampFormatter;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.DatabaseDialect;
import org.databene.jdbacl.model.DBCheckConstraint;
import org.databene.jdbacl.model.DBPackage;
import org.databene.jdbacl.model.DBProcedure;
import org.databene.jdbacl.model.DBSchema;
import org.databene.jdbacl.model.DBSequence;
import org.databene.jdbacl.model.DBTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements generic database concepts for Oracle.<br/><br/>
 * Created: 26.01.2008 07:05:28
 * @since 0.4.0
 * @author Volker Bergmann
 */
public class OracleDialect extends DatabaseDialect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OracleDialect.class);
    
	private static final String DATE_PATTERN = "'to_date('''yyyy-MM-dd HH:mm:ss''', ''yyyy-mm-dd HH24:mi:ss'')'";
	private static final String TIME_PATTERN = "'to_date('''HH:mm:ss''', ''HH24:mi:ss'')'";
    private static final String TIMESTAMP_MESSAGE = "to_timestamp(''{0}'', ''yyyy-mm-dd HH24:mi:ss.FF'')";
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSSSSSSS";
    private static final Pattern SIMPLE_NOT_NULL_CHECK = Pattern.compile("\"[A-Z0-9_]+\" IS NOT NULL");

	Pattern randomNamePattern = Pattern.compile("SYS_C\\d{8}");

	public OracleDialect() {
	    super("oracle", true, true, DATE_PATTERN, TIME_PATTERN);
    }

	@Override
	public boolean isDefaultCatalog(String catalog, String user) {
	    return (catalog == null);
	}
	
	@Override
	public boolean isDefaultSchema(String schema, String user) {
	    return user.equalsIgnoreCase(schema);
	}
	
	@Override
	public String renderCreateSequence(DBSequence sequence) {
		/* Oracle sequence syntax:
			CREATE SEQUENCE [myschema.]xyz 
			START WITH 1
			INCREMENT BY 1 
			MINVALUE 1 | NOMINVALUE
			MAXVALUE 999999999 | NOMAXVALUE
			CACHE 1 | NOCACHE
			CYCLE | NOCYCLE
			ORDER | NOORDER
		 */
		String result = super.renderCreateSequence(sequence);
		// apply cache settings
    	Long cache = sequence.getCache();
    	if (cache != null)
    		result += " CACHE " + cache;
		// if applicable, append ORDER. This is purely oracle
		Boolean order = sequence.isOrder();
		if (order != null)
			result += (order ? " ORDER" : "NOORDER");
		return result;
	}
	
	@Override
	protected String renderSequenceNameAndType(DBSequence sequence) {
		String schemaName = sequence.getSchemaName();
		return (schemaName != null ? "\"" + schemaName + "\"." : "") + '"' + sequence.getName() + '"';
	}
	
	@Override
    public String renderFetchSequenceValue(String sequenceName) {
        return "select " + sequenceName + ".nextval from dual";
    }
	
	@Override
    public String formatTimestamp(Timestamp value) {
		String renderedTimestamp = new TimestampFormatter(TIMESTAMP_PATTERN).format(value);
		return MessageFormat.format(TIMESTAMP_MESSAGE, renderedTimestamp);
    }

	@Override
	public DBSequence[] querySequences(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("select sequence_name, min_value, max_value, increment_by, " +
					"cycle_flag, order_flag, cache_size, last_number from user_sequences");
			try {
				ArrayBuilder<DBSequence> builder = new ArrayBuilder<DBSequence>(DBSequence.class);
				while (resultSet.next()) {
					DBSequence sequence = new DBSequence(resultSet.getString(1), null);
					sequence.setMinValue(new BigInteger(resultSet.getString(2)));
					sequence.setMaxValue(new BigInteger(resultSet.getString(3)));
					sequence.setIncrement(new BigInteger(resultSet.getString(4)));
					sequence.setCycle("Y".equals(resultSet.getString(5)));
					sequence.setOrder("Y".equals(resultSet.getString(6)));
					sequence.setCache(resultSet.getLong(7));
					sequence.setLastNumber(new BigInteger(resultSet.getString(8)));
					builder.add(sequence);
				}
				return builder.toArray();
			} finally {
				DBUtil.close(resultSet);
			}
		} finally {
			DBUtil.close(statement);
		}
	}
	
	public DBCheckConstraint[] queryCheckConstraints(Connection connection, String schemaName) throws SQLException {
		Statement statement = connection.createStatement();
		statement.setFetchSize(300);
		String query = "select owner, constraint_name, table_name, search_condition " +
				"from user_constraints where constraint_type = 'C'";
		if (schemaName != null)
			query += " and owner = '" + schemaName.toUpperCase() + "'";
		ResultSet resultSet = statement.executeQuery(query);
		ArrayBuilder<DBCheckConstraint> builder = new ArrayBuilder<DBCheckConstraint>(DBCheckConstraint.class);
		while (resultSet.next()) {
			String ownerName = resultSet.getString("owner");
			if (schemaName == null || StringUtil.equalsIgnoreCase(schemaName, ownerName)) {
				String constraintName = resultSet.getString("constraint_name");
				String tableName = resultSet.getString("table_name");
				String condition = resultSet.getString("search_condition");
				if (!SIMPLE_NOT_NULL_CHECK.matcher(condition).matches()) {
					try {
						DBCheckConstraint constraint = new DBCheckConstraint(
								constraintName, !isDeterministicCheckName(constraintName), tableName, condition);
						builder.add(constraint);
					} catch (Exception e) {
						LOGGER.error("Error parsing check constraint ", e);
					}
				}
			}
		}
		return builder.toArray();
	}

	public boolean isDeterministicCheckName(String checkName) {
		return !randomNamePattern.matcher(checkName).matches();
	}

	@Override
	public boolean isDeterministicPKName(String pkName) {
		return !randomNamePattern.matcher(pkName).matches();
	}

	@Override
	public boolean isDeterministicUKName(String ukName) {
		return !randomNamePattern.matcher(ukName).matches();
	}

	@Override
	public boolean isDeterministicFKName(String fkName) {
		return !randomNamePattern.matcher(fkName).matches();
	}

	@Override
	public boolean isDeterministicIndexName(String indexName) {
		return !randomNamePattern.matcher(indexName).matches();
	}

	@Override
	public boolean supportsRegex() {
		return true;
	}
	
	@Override
	public String regexQuery(String expression, boolean not, String regex) {
		return (not ? "NOT " : "") + "REGEXP_LIKE(" + expression + ", '" + regex + "')";
	}

	/*
	@Override
	public List<DBIndex> queryIndexes(DBSchema schema, Connection connection) throws SQLException {
		String query = "SELECT INDEX_NAME, INDEX_TYPE, TABLE_OWNER, TABLE_NAME, TABLE_TYPE, UNIQUENESS" +
				" FROM USER_INDEXES";
		if (schema != null)
			query += " WHERE TABLE_OWNER = '" + schema.getName().toUpperCase() + "'";
		List<Object[]> indexInfos = DBUtil.query(query, connection);
		OrderedMap<String, DBIndex> indexes = new OrderedMap<String, DBIndex>();
		for (int i = 0; i < indexInfos.size(); i++) {
			Object[] indexInfo = indexInfos.get(i);
			String ownerName = (String) indexInfo[2];
			if (schema == null || schema.getName().equals(ownerName)) {
				boolean unique = "UNIQUE".equalsIgnoreCase(indexInfo[5].toString());
				String name = (String) indexInfo[0];
				String tableName = (String) indexInfo[3];
				boolean deterministicName = isDeterministicIndexName(name);
				DBTable table = schema.getTable(tableName);
				DBIndex index;
				if (unique) {
					DBUniqueConstraint uk = table.getUniqueConstraint(name);
					index = new DBUniqueIndex(name, deterministicName, uk);
				} else {
					index = new DBNonUniqueIndex(name, deterministicName, table);
				}
				indexes.put(index.getName(), index);
				LOGGER.debug("Imported index {}", index);
			}
		}
		
		// query package procedures
		query = "SELECT INDEX_NAME, TABLE_NAME, COLUMN_NAME, COLUMN_POSITION FROM USER_IND_COLUMNS";
		if (schema != null)
			query += " AND OWNER = '" + schema.getName().toUpperCase() + "'";
		query += " ORDER BY INDEX_NAME, COLUMN_POSITION";
		List<Object[]> colInfos = DBUtil.query(query, connection);
		for (int i = 0; i < colInfos.size(); i++) {
			Object[] colInfo = colInfos.get(i);
			DBIndex index = indexes.get((String) colInfo[0]);
			String columnName = (String) colInfo[2];
			index.addColumnName(columnName);
			LOGGER.debug("Imported index column {}.{}", index.getName(), columnName);
		}		
		return indexes.values();
	}
	*/
	
	@Override
	public List<DBTrigger> queryTriggers(DBSchema schema, Connection connection) throws SQLException {
		String query = "SELECT OWNER, TRIGGER_NAME, TRIGGER_TYPE, TRIGGERING_EVENT, TABLE_OWNER, BASE_OBJECT_TYPE, " +
			"TABLE_NAME, COLUMN_NAME, REFERENCING_NAMES, WHEN_CLAUSE, STATUS, DESCRIPTION, ACTION_TYPE, " +
			"TRIGGER_BODY FROM SYS.ALL_TRIGGERS";
		if (schema != null)
			query += " WHERE OWNER = '" + schema.getName().toUpperCase() + "'";
		List<Object[]> triggerInfos = DBUtil.query(query, connection);
		List<DBTrigger> triggers = new ArrayList<DBTrigger>();
		for (int i = 0; i < triggerInfos.size(); i++) {
			Object[] triggerInfo = triggerInfos.get(i);
			DBTrigger trigger = new DBTrigger((String) triggerInfo[1], schema);
			trigger.setTriggerType((String) triggerInfo[2]);
			trigger.setTriggeringEvent((String) triggerInfo[3]);
			trigger.setTableOwner((String) triggerInfo[4]);
			trigger.setBaseObjectType((String) triggerInfo[5]);
			trigger.setTableName((String) triggerInfo[6]);
			trigger.setColumnName((String) triggerInfo[7]);
			trigger.setReferencingNames((String) triggerInfo[8]);
			trigger.setWhenClause((String) triggerInfo[9]);
			trigger.setStatus((String) triggerInfo[10]);
			trigger.setDescription((String) triggerInfo[11]);
			trigger.setActionType((String) triggerInfo[12]);
			trigger.setTriggerBody((String) triggerInfo[13]);
			triggers.add(trigger);
			LOGGER.debug("Imported trigger: {}", trigger.getName());
		}
		return triggers;
	}
	
	@Override
	public List<DBPackage> queryPackages(DBSchema schema, Connection connection) throws SQLException {
		
		// query packages
		String query = "SELECT OWNER, OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, OBJECT_TYPE, STATUS" +
				" FROM USER_OBJECTS WHERE UPPER(OBJECT_TYPE) = 'PACKAGE'";
		if (schema != null)
			query += " AND OWNER = '" + schema.getName().toUpperCase() + "'";
		List<Object[]> pkgInfos = DBUtil.query(query, connection);
		OrderedMap<String, DBPackage> packages = new OrderedMap<String, DBPackage>();
		for (int i = 0; i < pkgInfos.size(); i++) {
			Object[] pkgInfo = pkgInfos.get(i);
			String ownerName = (String) pkgInfo[0];
			if (schema == null || schema.getName().equals(ownerName)) {
				String name = (String) pkgInfo[1];
				DBPackage pkg = new DBPackage(name, schema);
				pkg.setSubObjectName((String) pkgInfo[2]);
				pkg.setObjectId(pkgInfo[3].toString());
				pkg.setObjectType((String) pkgInfo[4]);
				pkg.setStatus((String) pkgInfo[5]);
				packages.put(pkg.getName(), pkg);
				LOGGER.debug("Imported package {}", pkg);
			}
		}
		
		// query package procedures
		query = "SELECT OBJECT_NAME, PROCEDURE_NAME, OBJECT_ID, SUBPROGRAM_ID, OVERLOAD" +
			" FROM SYS.USER_PROCEDURES WHERE UPPER(OBJECT_TYPE) = 'PACKAGE'" +
			" AND PROCEDURE_NAME IS NOT NULL AND OBJECT_NAME IN (" + 
			CollectionUtil.formatCommaSeparatedList(NameUtil.getNames(packages.values()), '\'') + ")";
		List<Object[]> procInfos = DBUtil.query(query, connection);
		for (int i = 0; i < procInfos.size(); i++) {
			Object[] procInfo = procInfos.get(i);
			DBPackage owner = packages.get((String) procInfo[0]);
			String name = (String) procInfo[1];
			DBProcedure proc = new DBProcedure(name, owner);
			proc.setObjectId(procInfo[2].toString());
			proc.setSubProgramId(procInfo[3].toString());
			proc.setOverload((String) procInfo[4]);
			LOGGER.debug("Imported package procedure {}.{}", owner.getName(),  proc.getName());
		}		
		return packages.values();
	}
	
}