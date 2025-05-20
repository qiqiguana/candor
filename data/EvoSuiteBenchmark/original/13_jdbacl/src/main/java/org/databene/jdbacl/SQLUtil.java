/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

package org.databene.jdbacl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.databene.commons.ArrayFormat;
import org.databene.commons.CollectionUtil;
import org.databene.commons.StringUtil;
import org.databene.jdbacl.model.DBCheckConstraint;
import org.databene.jdbacl.model.DBColumn;
import org.databene.jdbacl.model.DBConstraint;
import org.databene.jdbacl.model.DBForeignKeyConstraint;
import org.databene.jdbacl.model.DBNotNullConstraint;
import org.databene.jdbacl.model.DBObject;
import org.databene.jdbacl.model.DBPrimaryKeyConstraint;
import org.databene.jdbacl.model.DBTable;
import org.databene.jdbacl.model.DBUniqueConstraint;

/**
 * Provides utility methods for creating SQL queries and commands.<br/><br/>
 * Created: 01.09.2010 09:38:46
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class SQLUtil {
	
	private static final Set<String> NO_SIZE_TYPES = CollectionUtil.toSet(
			"DATE", "BLOB", "CLOB", "NCLOB");

	private static final Set<String> DDL_STATEMENTS = CollectionUtil.toSet(
			"create table", "alter table", "drop table",
			"create unique index", "drop index", "alter index",
			"rename",
			"create materialized view", "alter materialized view", "drop materialized view" 
			);
	
	private static final Set<String> DML_STATEMENTS = CollectionUtil.toSet(
			"insert", "update", "delete", "truncate", "select into");
	
	private static final Set<String> PROCEDURE_CALLS = CollectionUtil.toSet(
			"execute", "exec", "call");
	
	public static Object[] parseColumnTypeAndSize(String spec) {
		int lparen = spec.indexOf('(');
		if (lparen < 0)
			return new Object[] { spec };
		String type = spec.substring(0, lparen);
		int rparen = spec.indexOf(')', lparen);
		if (rparen < 0)
			throw new RuntimeException("Illegal column type format: " + spec);
		String[] sizeAndFractionDigits = spec.substring(lparen + 1, rparen).split(",");
		if (sizeAndFractionDigits.length == 1)
			return new Object[] { type, Integer.parseInt(sizeAndFractionDigits[0].trim()) };
		else 
			return new Object[] { type, Integer.parseInt(sizeAndFractionDigits[0].trim()), 
				Integer.parseInt(sizeAndFractionDigits[1].trim()) };
	}
	
	public static void renderCreateTable(DBTable table, 
			boolean includeForeignKeys, NameSpec nameSpec, PrintWriter out) {
		// create table <name> (
		out.print("create table ");
		out.print(table.getName());
		out.print(" (");
		// columns
		List<DBColumn> columns = table.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			if (i > 0)
				out.print(',');
			out.println();
			out.print('\t');
			out.print(renderColumn(columns.get(i)));
		}
		// primary key
		DBPrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
		if (pk != null) {
			out.println(",");
			out.print('\t');
			out.print(pkSpec(pk, nameSpec));
		}
		// unique keys
		Set<DBUniqueConstraint> uks = table.getUniqueConstraints(false);
		for (DBUniqueConstraint uk : uks) {
			out.println(",");
			out.print('\t');
			out.print(ukSpec(uk, nameSpec));
		}
		// unique keys
		if (includeForeignKeys) {
			Set<DBForeignKeyConstraint> fks = table.getForeignKeyConstraints();
			for (DBForeignKeyConstraint fk : fks) {
				out.println(",");
				out.print('\t');
				out.print(fkSpec(fk, nameSpec));
			}
		}
		// checks
		List<DBCheckConstraint> checks = table.getCheckConstraints();
		for (DBCheckConstraint check : checks) {
			out.println(",");
			out.print('\t');
			out.print(checkSpec(check, nameSpec));
		}
		out.println();
		out.print(")");
	}
	
	public static void renderAddForeignKey(DBForeignKeyConstraint fk, NameSpec nameSpec, PrintWriter printer) {
		printer.println("ALTER TABLE " + fk.getTable().getName() + " ADD ");
		printer.print('\t');
		printer.print(SQLUtil.fkSpec(fk, nameSpec));
    }

    public static String renderColumnNames(DBColumn[] columns) {
        StringBuilder builder = new StringBuilder(columns[0].getName());
        for (int i = 1; i < columns.length; i++)
            builder.append(", ").append(columns[i].getName());
        return builder.toString();
    }

    public static String renderColumnNames(List<DBColumn> columns) {
        StringBuilder builder = new StringBuilder(columns.get(0).getName());
        for (int i = 1; i < columns.size(); i++)
            builder.append(", ").append(columns.get(i).getName());
        return builder.toString();
    }

	public static String renderColumnNames(String[] columnNames) {
		return '(' + ArrayFormat.format(columnNames) + ')';
	}

	public static String renderColumn(DBColumn column) {
		StringBuilder builder = new StringBuilder();
		
	    // column name
	    builder.append(column.getName());
	    
	    // column type & size
		builder.append(' ');
		renderColumnTypeWithSize(column, builder);
	    
	    // default
	    if (column.getDefaultValue() != null)
	    	builder.append(" DEFAULT " + column.getDefaultValue());
	    
	    // nullability
	    if (!column.isNullable())
	    	builder.append(" NOT");
    	builder.append(" NULL");
	    
	    return builder.toString();
    }

	public static String renderColumnTypeWithSize(DBColumn column) {
	    StringBuilder builder = new StringBuilder();
		renderColumnTypeWithSize(column, builder);
		return builder.toString();
    }
	
	public static void renderColumnTypeWithSize(DBColumn column, StringBuilder builder) {
	    String typeName = column.getType().getName();
	    builder.append(typeName);
	    if (column.getSize() != null && !NO_SIZE_TYPES.contains(typeName)) {
	    	builder.append("(" + column.getSize());
	    	if (column.getFractionDigits() != null)
	    		builder.append("," + column.getFractionDigits());
	    	builder.append(")");
	    }
    }
	
	public static String substituteMarkers(String sql, String marker, Object substitution) {
		return sql.replace(marker, renderValue(substitution));
    }

    public static String renderQuery(DBTable table, String[] columnNames, Object[] values) {
		StringBuilder builder = new StringBuilder("SELECT * FROM ").append(table.getName());
		builder.append(" WHERE ").append(renderWhereClause(columnNames, values));
		return builder.toString();
    }
    
    public static String renderWhereClause(String[] columnNames, Object[] values) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < columnNames.length; i++) {
			if (i > 0)
				builder.append(" AND ");
			builder.append(columnNames[i]).append(" = ").append(renderValue(values[i]));
		}
		return builder.toString();
    }
    
    public static String renderValue(Object value) {
	    if (value instanceof String || value instanceof Character)
	    	return "'" + value + "'";
	    else
	    	return String.valueOf(value);
    }
    
	public static Boolean mutatesDataOrStructure(String sql) {
		sql = normalizeSQL(sql);
		// ALTER SESSION does not change data or structure
		if (sql.trim().startsWith("alter session"))
			return false;

		// check if structure is changed...
		if (Boolean.TRUE.equals(mutatesStructure(sql)))
			return true;
		else if (isQuery(sql))
			return false;
		else if (isDML(sql))
			return true;
		return null;
    }

	public static Boolean mutatesStructure(String sql) {
	    if (isDDL(sql))
	   		return true;
	    if (isProcedureCall(sql))
    		return false;
	    return false;
    }
	
	public static boolean isDDL(String sql) {
		sql = normalizeSQL(sql);
	    for (String ddl : DDL_STATEMENTS)
	    	if (sql.startsWith(ddl))
	    		return true;
	    return false;
	}
	
	public static boolean isDML(String sql) {
		sql = normalizeSQL(sql);
	    for (String ddl : DML_STATEMENTS)
	    	if (sql.startsWith(ddl))
	    		return true;
	    return false;
	}
	
	public static boolean isProcedureCall(String sql) {
		sql = normalizeSQL(sql);
	    for (String call : PROCEDURE_CALLS)
	    	if (sql.startsWith(call))
	    		return true;
	    return false;
	}

	public static boolean isQuery(String sql) {
		sql = normalizeSQL(sql);
		// anything else than SELECT must be a mutation...
	    if (!sql.startsWith("select"))
	    	return false;
	    // ... but a 'select' statement might be a 'select into'
	    StringTokenizer t = new StringTokenizer(sql);
	    while (t.hasMoreTokens())
	    	if ("into".equals(t.nextToken()))
	    		return false;
	    // it is a plain select statement
	    return true;
    }

	private static String normalizeSQL(String sql) {
		sql = StringUtil.normalizeSpace(sql.trim().toLowerCase());
		while (sql.contains("/*"))
			sql = StringUtil.removeSection(sql, "/*", "*/").trim();
		return sql;
	}

	public static String constraintSpec(DBConstraint constraint, NameSpec nameSpec) {
		if (constraint instanceof DBPrimaryKeyConstraint)
			return pkSpec((DBPrimaryKeyConstraint) constraint, nameSpec);
		else if (constraint instanceof DBUniqueConstraint)
			return ukSpec((DBUniqueConstraint) constraint, nameSpec);
		else if (constraint instanceof DBForeignKeyConstraint)
			return fkSpec((DBForeignKeyConstraint) constraint, nameSpec);
		else if (constraint instanceof DBNotNullConstraint)
			return notNullSpec((DBNotNullConstraint) constraint);
		else if (constraint instanceof DBCheckConstraint)
			return checkSpec((DBCheckConstraint) constraint, nameSpec);
		else
			throw new UnsupportedOperationException("Unknown constraint type: " + 
					constraint.getClass());
	}
	
	private static String checkSpec(DBCheckConstraint check, NameSpec nameSpec) {
		StringBuilder builder = createConstraintSpecBuilder(check, nameSpec);
		builder.append("CHECK ").append(check.getConditionText());
		return builder.toString();
	}

	private static String notNullSpec(DBNotNullConstraint constraint) {
		return constraint.getColumnNames()[0] + " NOT NULL";
	}

	public static String pkSpec(DBPrimaryKeyConstraint pk, NameSpec nameSpec) {
		StringBuilder builder = createConstraintSpecBuilder(pk, nameSpec);
		builder.append("PRIMARY KEY ").append(renderColumnNames(pk.getColumnNames()));
		return builder.toString();
	}
	
	public static String ukSpec(DBUniqueConstraint uk, NameSpec nameSpec) {
		StringBuilder builder = createConstraintSpecBuilder(uk, nameSpec);
		builder.append("UNIQUE ").append(renderColumnNames(uk.getColumnNames()));
		return builder.toString();
    }

	public static String fkSpec(DBForeignKeyConstraint fk, NameSpec nameSpec) {
		StringBuilder builder = createConstraintSpecBuilder(fk, nameSpec);
		builder.append("FOREIGN KEY ").append(renderColumnNames(fk.getColumnNames()));
		builder.append(" REFERENCES ").append(fk.getRefereeTable()).append(renderColumnNames(fk.getRefereeColumnNames()));
		return builder.toString();
	}

	protected static StringBuilder createConstraintSpecBuilder(DBConstraint constraint, NameSpec nameSpec) {
		StringBuilder builder = new StringBuilder();
		return appendConstraintName(constraint, builder, nameSpec);
	}
	
	public static String leftJoin(String alias1, String[] columns1, 
			String table2, String alias2, String[] columns2) {
		return join("left", alias1, columns1, table2, alias2, columns2);
	}

	public static String innerJoin(String alias1, String[] columns1, 
			String table2, String alias2, String[] columns2) {
		return join("inner", alias1, columns1, table2, alias2, columns2);
	}

	public static String join(String type, String refererAlias, String[] refererColumns, 
			String refereeTable, String refereeAlias, String[] refereeColumns) {
		StringBuilder builder = new StringBuilder();
		if (!StringUtil.isEmpty(type) && !"inner".equalsIgnoreCase(type))
			builder.append(type).append(" join ");
		builder.append(refereeTable).append(" as ").append(refereeAlias).append(" on "); 
		for (int i = 0; i < refererColumns.length; i++) {
			if (i > 0)
				builder.append(" and ");
			builder.append(refererAlias).append('.').append(refererColumns[i]);
			builder.append(" = ").append(refereeAlias).append('.').append(refereeColumns[i]);
		}
		return builder.toString();
	}

	public static StringBuilder addRequiredCondition(String condition, StringBuilder builder) {
		if (builder.length() > 0)
			builder.append(" and ");
		return builder.append(condition);
	}

	public static StringBuilder addOptionalCondition(String condition, StringBuilder builder) {
		if (builder.length() > 0)
			builder.append(" or ");
		return builder.append(condition);
	}

	public static String ownerDotComponent(DBObject object) {
		return (object.getOwner() != null ? object.getOwner() + "." : "") + object.getName();
	}

	public static StringBuilder appendConstraintName(DBConstraint constraint, StringBuilder builder, NameSpec nameSpec) {
		if (constraint.getName() != null && (nameSpec == NameSpec.ALWAYS || (nameSpec == NameSpec.IF_REPRODUCIBLE && constraint.isNameDeterministic())))
			builder.append("CONSTRAINT " + quoteNameIfNecessary(constraint.getName()) + ' ');
		return builder;
	}
	
	public static void appendConstraintName(DBConstraint constraint, StringBuilder builder) {
		if (constraint.getName() != null)
			builder.append("CONSTRAINT " + quoteNameIfNecessary(constraint.getName()) + ' ');
	}
	
	public static String constraintName(DBConstraint constraint) {
		return (constraint.getName() != null ? 
				"CONSTRAINT " + quoteNameIfNecessary(constraint.getName()) + ' ' : 
				"");
	}
	
	public static String typeAndName(DBObject dbObject) {
		if (dbObject == null)
			return null;
		String name = dbObject.getName();
		if (name == null && dbObject instanceof DBConstraint)
			name = "constraint";
		return dbObject.getObjectType() + ' ' + name;
	}

	public static String removeComments(String sql) {
		String result = sql;
		String tmp;
		do {
			tmp = result;
			result = StringUtil.removeSection(tmp, "/*", "*/");
		} while (!result.equals(tmp));
		return result;
	}

	public static String normalize(String sql, boolean removeComments) {
		if (removeComments)
			sql = sql.replace("--", "//");
		StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(sql));
		tokenizer.resetSyntax();
		tokenizer.wordChars('A', 'Z');
		tokenizer.wordChars('a', 'z');
		tokenizer.wordChars('0', '9');
		tokenizer.wordChars('_', '_');
		tokenizer.whitespaceChars(' ', ' ');
		tokenizer.whitespaceChars('\n', '\n');
		tokenizer.whitespaceChars('\r', '\r');
		tokenizer.whitespaceChars('\t', '\t');
		tokenizer.quoteChar('\'');
		tokenizer.quoteChar('"');
		if (removeComments) {
			tokenizer.slashStarComments(true);
			tokenizer.slashSlashComments(true);
		}
		StringBuilder builder = new StringBuilder();
		int lastTtype = StreamTokenizer.TT_EOF;
		try {
			while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
				int ttype = tokenizer.ttype;
				if (builder.length() > 0 // insert space if this is not the beginning of the string 
						&& ttype != ')' && ttype != ',' && lastTtype != '(' // no space for brackets and lists
						&& lastTtype != '.' && ttype != '.' // no space around '.' 
						&& !(lastTtype == '/' && ttype =='*') // preserve /* if it has not been filtered out 
						&& !(lastTtype == '-' && ttype =='-') // preserve -- if it has not been filtered out
						&& !(lastTtype == '*' && ttype =='/')) // preserve */ if it has not been filtered out
					builder.append(' ');
				switch (ttype) {
					case StreamTokenizer.TT_WORD: builder.append(tokenizer.sval); break;
					case StreamTokenizer.TT_NUMBER: builder.append(renderNumber(tokenizer)); break;
					case '"': builder.append('"').append(tokenizer.sval).append('"'); break;
					case '\'': builder.append('\'').append(tokenizer.sval).append('\''); break;
					default: builder.append((char) ttype);
				}				
				lastTtype = ttype;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return builder.toString();
	}

	protected static String renderNumber(StreamTokenizer tokenizer) {
		double value = tokenizer.nval;
		if (Math.floor(value) == value)
			return renderLong((long) value);
		else
			return renderDouble(value);
	}

	public static String renderColumnListWithTableName(String table, String... columns) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(table).append('.').append(columns[i]);
		}
		return builder.toString();
	}

	// private helpers -------------------------------------------------------------------------------------------------
	
	private static String renderLong(long value) {
		if (value > 0)
			return String.valueOf(value);
		else
			return "- " + String.valueOf(Math.abs(value));
	}

	private static String renderDouble(double value) {
		if (value > 0)
			return String.valueOf(value);
		else
			return "- " + String.valueOf(Math.abs(value));
	}

	private static String quoteNameIfNecessary(String name) {
		return (name != null && name.indexOf(' ') >= 0 ? '"' + name + '"' : name);
    }

}
