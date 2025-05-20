/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.jdbacl.model.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.bean.HashCodeBuilder;
import org.databene.commons.iterator.TableRowIterator;
import org.databene.jdbacl.model.DBCatalog;
import org.databene.jdbacl.model.DBCheckConstraint;
import org.databene.jdbacl.model.DBColumn;
import org.databene.jdbacl.model.CompositeDBObject;
import org.databene.jdbacl.model.DBForeignKeyConstraint;
import org.databene.jdbacl.model.DBIndex;
import org.databene.jdbacl.model.DBObject;
import org.databene.jdbacl.model.DBPrimaryKeyConstraint;
import org.databene.jdbacl.model.DBRow;
import org.databene.jdbacl.model.DBRowIterator;
import org.databene.jdbacl.model.DBSchema;
import org.databene.jdbacl.model.DBTable;
import org.databene.jdbacl.model.DBTableComponent;
import org.databene.jdbacl.model.DBUniqueConstraint;
import org.databene.jdbacl.model.DefaultDBTable;

/**
 * Table representation that supports lazy loading of its column and constraint info.<br/><br/>
 * Created: 18.08.2010 10:56:11
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class LazyTable implements DBTable {

	private static final long serialVersionUID = 188548842580766795L;
	
	private String name;
	private String doc;
	private JDBCDBImporter importer;
	private DBSchema schema;
	
	private DefaultDBTable realTable;
	
	public LazyTable(JDBCDBImporter importer, DBSchema schema, String tableName, String doc) {
	    this.importer = importer;
	    this.schema = schema;
	    this.name = tableName;
	    this.doc = doc;
        if (schema != null)
        	schema.addTable(this);
    }
	
	
	
	// implementation of the meta data part of the 'DBTable' interface -------------------------------------------------
	
	public String getObjectType() {
		return "table";
	}

	public List<DBTableComponent> getComponents() {
		return getRealTable().getComponents();
	}

	public CompositeDBObject<?> getOwner() {
		return getRealTable().getOwner();
	}

	public void setOwner(CompositeDBObject<?> owner) {
		getRealTable().setOwner(owner);
	}
	
	public void setPrimaryKey(DBPrimaryKeyConstraint pk) {
		getRealTable().setPrimaryKey(pk);
	}

	public void addUniqueConstraint(DBUniqueConstraint uk) {
		getRealTable().addUniqueConstraint(uk);
	}

	public void addForeignKey(DBForeignKeyConstraint fk) {
		getRealTable().addForeignKey(fk);
	}

	public void addIndex(DBIndex index) {
		getRealTable().addIndex(index);
	}

	public DBRowIterator queryRows(String whereClause, Connection connection)
			throws SQLException {
		return getRealTable().queryRows(whereClause, connection);
	}

	public HeavyweightIterator<Object> queryPKs(Connection connection) {
		return getRealTable().queryPKs(connection);
	}

	public TableRowIterator query(String query, Connection connection) {
		return getRealTable().query(query, connection);
	}

	public DBRow queryByPK(Object pk, Connection connection) throws SQLException {
		return getRealTable().queryByPK(pk, connection);
	}

	public DBCatalog getCatalog() {
		return schema.getCatalog();
    }

	public DBSchema getSchema() {
		return schema;
    }

	public String getName() {
	    return name;
    }

	public String getDoc() {
	    return doc;
    }

	public String[] getColumnNames() {
		return getRealTable().getColumnNames();
	}

	public DBColumn getColumn(String columnName) {
	    return getRealTable().getColumn(columnName);
    }

	public List<DBColumn> getColumns() {
	    return getRealTable().getColumns();
    }

	public void addColumn(DBColumn column) {
		getRealTable().addColumn(column);
	}

	public DBColumn[] getColumns(String[] columnNames) {
	    return getRealTable().getColumns(columnNames);
    }

	public Set<DBForeignKeyConstraint> getForeignKeyConstraints() {
	    return getRealTable().getForeignKeyConstraints();
    }

	public DBForeignKeyConstraint getForeignKeyConstraint(String[] columnNames) {
	    return getRealTable().getForeignKeyConstraint(columnNames);
	}

	public List<DBCheckConstraint> getCheckConstraints() {
		return getRealTable().getCheckConstraints();
	}

	public void addCheckConstraint(DBCheckConstraint checkConstraint) {
		getRealTable().addCheckConstraint(checkConstraint);
	}

	public DBIndex getIndex(String indexName) {
	    return getRealTable().getIndex(indexName);
    }

	public List<DBIndex> getIndexes() {
	    return getRealTable().getIndexes();
    }

	public String[] getPKColumnNames() {
	    return getRealTable().getPKColumnNames();
    }

	public DBPrimaryKeyConstraint getPrimaryKeyConstraint() {
	    return getRealTable().getPrimaryKeyConstraint();
    }

	public long getRowCount(Connection connection) {
	    return getRealTable().getRowCount(connection);
    }

	public Set<DBUniqueConstraint> getUniqueConstraints(boolean includePK) {
	    return getRealTable().getUniqueConstraints(includePK);
    }

	public DBUniqueConstraint getUniqueConstraint(String name) {
	    return getRealTable().getUniqueConstraint(name);
    }

	public Collection<DBTable> getReferrers() {
	    return getRealTable().getReferrers();
    }
	
	public void addReferrer(DBTable table) {
	    getRealTable().addReferrer(table);
	}

	public DBRowIterator queryRowsByCellValues(String[] columnNames, Object[] values, Connection connection)
            throws SQLException {
	    return getRealTable().queryRowsByCellValues(columnNames, values, connection);
    }

	public DBRow queryByPK(Object[] idParts, Connection connection) throws SQLException {
	    return getRealTable().queryByPK(idParts, connection);
    }

	public DBRowIterator allRows(Connection connection) throws SQLException {
	    return getRealTable().allRows(connection);
    }
	

	
	// implementation of the 'Dependent' part of the 'DBTable' interface -----------------------------------------------
	
	public int countProviders() {
	    return getRealTable().countProviders();
    }

	public DBTable getProvider(int index) {
	    return getRealTable().getProvider(index);
    }

	public boolean requiresProvider(int index) {
	    return getRealTable().requiresProvider(index);
    }
	
	public boolean isIdentical(DBObject other) {
		return getRealTable().isIdentical(other);
	}
	
	
		
	// access to the wrapped object ------------------------------------------------------------------------------------

	public DefaultDBTable getRealTable() {
		if (realTable == null)
			realTable = importer.importTable(getCatalog(), schema, name, doc);
	    return realTable;
    }

	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return name;
	}

	@Override
    public int hashCode() {
	    return HashCodeBuilder.hashCode(schema, name);
    }

	@Override
    public boolean equals(Object other) {
	    if (this == other)
		    return true;
	    if (other == null || !(other instanceof DBTable))
		    return false;
	    DBTable that = (DBTable) other;
	    if (!NullSafeComparator.equals(this.schema, that.getSchema()))
	    	return false;
	    return NullSafeComparator.equals(this.name, that.getName());
    }

}
