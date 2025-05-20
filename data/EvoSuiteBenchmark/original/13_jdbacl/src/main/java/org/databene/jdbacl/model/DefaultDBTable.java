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

package org.databene.jdbacl.model;

import org.databene.commons.ArrayFormat;
import org.databene.commons.Assert;
import org.databene.commons.CollectionUtil;
import org.databene.commons.HeavyweightIterator;
import org.databene.commons.NameUtil;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.ObjectNotFoundException;
import org.databene.commons.OrderedSet;
import org.databene.commons.StringUtil;
import org.databene.jdbacl.ArrayResultSetIterator;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.QueryIterator;
import org.databene.jdbacl.ResultSetConverter;
import org.databene.jdbacl.SQLUtil;
import org.databene.commons.bean.HashCodeBuilder;
import org.databene.commons.collection.OrderedNameMap;
import org.databene.commons.depend.Dependent;
import org.databene.commons.iterator.ConvertingIterator;
import org.databene.commons.iterator.TableRowIterator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a database table.<br/><br/>
 * Created: 06.01.2007 08:58:49
 * @author Volker Bergmann
 */
public class DefaultDBTable extends AbstractCompositeDBObject<DBTableComponent> implements DBTable, Dependent<DBTable> {

    private static final long serialVersionUID = 6829370969378083211L;
    private static final String[] EMPTY_ARRAY = new String[0];

    OrderedNameMap<DBColumn> columns;
    private DBPrimaryKeyConstraint pk;
    private OrderedSet<DBUniqueConstraint> uniqueConstraints;
    private OrderedSet<DBForeignKeyConstraint> foreignKeyConstraints;
    private OrderedNameMap<DBIndex> indexes;
    private Set<DBTable> referrers;
	private List<DBCheckConstraint> checkConstraints;

    // constructors ----------------------------------------------------------------------------------------------------

    public DefaultDBTable() {
        this(null);
    }

    public DefaultDBTable(String name) {
        this(name, null);
    }

    public DefaultDBTable(String name, DBSchema schema) {
        super(name, "table", schema);
        if (schema != null)
        	schema.addTable(this);
        this.doc = null;
        this.columns = OrderedNameMap.createCaseInsensitiveMap();
        this.uniqueConstraints = new OrderedSet<DBUniqueConstraint>();
        this.foreignKeyConstraints = new OrderedSet<DBForeignKeyConstraint>();
        this.checkConstraints = new ArrayList<DBCheckConstraint>();
        this.indexes = OrderedNameMap.createCaseInsensitiveMap();
        this.referrers = new HashSet<DBTable>();
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

	public DBCatalog getCatalog() {
		return getSchema().getCatalog();
	}

    // schema operations -----------------------------------------------------------------------------------------------

	public DBSchema getSchema() {
        return (DBSchema) getOwner();
    }

    public void setSchema(DBSchema schema) {
        setOwner(schema);
    }

    // Primary Key operations ------------------------------------------------------------------------------------------

    public void setPrimaryKey(DBPrimaryKeyConstraint constraint) {
        this.pk = constraint;
    }

    public DBPrimaryKeyConstraint getPrimaryKeyConstraint() {
    	return pk;
    }

	public List<DBTableComponent> getComponents() {
		List<DBTableComponent> result = new ArrayList<DBTableComponent>();
		result.addAll(columns.values());
		if (pk != null)
			result.add(pk);
		result.addAll(uniqueConstraints);
		result.addAll(foreignKeyConstraints);
		result.addAll(indexes.values());
		return result;
	}

    // column operations -----------------------------------------------------------------------------------------------

	public String[] getColumnNames() {
		return CollectionUtil.toArray(NameUtil.getNames(columns.values()), String.class);
	}
	
    public List<DBColumn> getColumns() {
        return columns.values();
    }

    public DBColumn[] getColumns(String[] columnNames) {
        List<DBColumn> list = new ArrayList<DBColumn>(columnNames.length);
        for (String columnName : columnNames) {
            DBColumn column = getColumn(columnName);
            if (column == null)
                throw new IllegalArgumentException("Table '" + name + "' does not have a column '" + columnName + "'");
            list.add(column);
        }
        DBColumn[] array = new DBColumn[columnNames.length];
        return list.toArray(array);
    }

    public DBColumn getColumn(String columnName) {
        DBColumn column = columns.get(columnName);
        if (column == null)
            throw new ObjectNotFoundException("Column '" + columnName + 
                    "' not found in table '" + this.getName() + "'");
        return column;
    }

    public void addColumn(DBColumn column) {
        column.setTable(this);
        columns.put(column.getName(), column);
    }

    // index operations ------------------------------------------------------------------------------------------------

    public List<DBIndex> getIndexes() {
        return new ArrayList<DBIndex>(indexes.values());
    }

    public DBIndex getIndex(String indexName) {
        return indexes.get(indexName);
    }

    public void addIndex(DBIndex index) {
    	index.setTable(this);
        indexes.put(index.getName(), index);
    }

    public void removeIndex(DBIndex index) {
        indexes.remove(index.getName());
    }

    // uniqueConstraint operations -------------------------------------------------------------------------------------

    public Set<DBUniqueConstraint> getUniqueConstraints(boolean includePK) {
    	Set<DBUniqueConstraint> result = new HashSet<DBUniqueConstraint>(uniqueConstraints);
    	if (includePK)
    		result.add(pk);
    	return result;
    }

	public DBUniqueConstraint getUniqueConstraint(String[] columnNames) {
		if (pk != null && StringUtil.equalsIgnoreCase(columnNames, pk.getColumnNames()))
			return pk;
		for (DBUniqueConstraint constraint : uniqueConstraints)
			if (StringUtil.equalsIgnoreCase(columnNames, constraint.getColumnNames()))
				return constraint;
		return null;
	}

	public DBUniqueConstraint getUniqueConstraint(String name) {
		if (name.equalsIgnoreCase(pk.getName()))
			return pk;
		for (DBUniqueConstraint constraint : uniqueConstraints)
			if (name.equals(constraint.getName()))
				return constraint;
		return null;
	}

	public void addUniqueConstraint(DBUniqueConstraint uk) {
		uk.setTable(this);
		if (uk instanceof DBPrimaryKeyConstraint)
			setPrimaryKey((DBPrimaryKeyConstraint) uk);
		uniqueConstraints.add(uk);
    }

    public void removeUniqueConstraint(DBUniqueConstraint constraint) {
        uniqueConstraints.remove(constraint.getName());
    }

    // ForeignKeyConstraint operations ---------------------------------------------------------------------------------

    public Set<DBForeignKeyConstraint> getForeignKeyConstraints() {
        return new HashSet<DBForeignKeyConstraint>(foreignKeyConstraints);
    }

	public DBForeignKeyConstraint getForeignKeyConstraint(String[] columnNames) {
		for (DBForeignKeyConstraint fk : foreignKeyConstraints)
			if (StringUtil.equalsIgnoreCase(fk.getColumnNames(), columnNames))
				return fk;
		throw new ObjectNotFoundException("Table '" + name + "' has no foreign key " +
				"with the columns (" + ArrayFormat.format(columnNames) + ")");
	}

    public void addForeignKey(DBForeignKeyConstraint constraint) {
    	constraint.setTable(this);
        foreignKeyConstraints.add(constraint);
    }

    public void removeForeignKeyConstraint(DBForeignKeyConstraint constraint) {
        foreignKeyConstraints.remove(constraint);
    }

    // referrer operations ---------------------------------------------------------------------------------------------
    
    public Collection<DBTable> getReferrers() {
    	return new HashSet<DBTable>(referrers);
    }
    
	public void addReferrer(DBTable referrer) {
		referrers.add(referrer);
    }
    
	public String[] getPKColumnNames() {
		DBPrimaryKeyConstraint pk = getPrimaryKeyConstraint();
		return (pk != null ? pk.getColumnNames() : EMPTY_ARRAY);
	}

    // check constraint operations -------------------------------------------------------------------------------------

	public List<DBCheckConstraint> getCheckConstraints() {
		return new ArrayList<DBCheckConstraint>(checkConstraints);
	}

	public void addCheckConstraint(DBCheckConstraint checkConstraint) {
		checkConstraint.setTable(this);
		this.checkConstraints.add(checkConstraint);
	}

    // row operations --------------------------------------------------------------------------------------------------

    public DBRowIterator allRows(Connection connection) throws SQLException {
        return new DBRowIterator(this, connection, null);
    }
    
	public DBRowIterator queryRows(String whereClause, Connection connection) throws SQLException {
        return new DBRowIterator(this, connection, whereClause);
	}

	public long getRowCount(Connection connection) {
		return DBUtil.countRows(name, connection);
	}

	public DBRow queryByPK(Object pk, Connection connection) throws SQLException {
    	String[] pkColumnNames = getPrimaryKeyConstraint().getColumnNames();
    	if (pkColumnNames.length == 0)
    		throw new ObjectNotFoundException("Table " + name + " has no primary key");
    	Object[] pkComponents = (pk.getClass().isArray() ? (Object[]) pk : new Object[] { pk });
		String whereClause = SQLUtil.renderWhereClause(pkColumnNames, pkComponents);
        DBRowIterator iterator = new DBRowIterator(this, connection, whereClause);
        if (!iterator.hasNext())
        	throw new ObjectNotFoundException("No " + name + " row with id (" + pkComponents + ")");
		DBRow result = iterator.next();
		iterator.close();
		return result;
    }
    
    public DBRowIterator queryRowsByCellValues(String[] columns, Object[] values, Connection connection) throws SQLException {
		String whereClause = SQLUtil.renderWhereClause(columns, values);
        return new DBRowIterator(this, connection, whereClause);
    }
    
	public HeavyweightIterator<Object> queryPKs(Connection connection) {
		StringBuilder query = new StringBuilder("select ");
		query.append(ArrayFormat.format(getPKColumnNames()));
		query.append(" from ").append(name);
    	Iterator<ResultSet> rawIterator = new QueryIterator(query.toString(), connection, 100);
        ResultSetConverter<Object> converter = new ResultSetConverter<Object>(Object.class, true);
    	return new ConvertingIterator<ResultSet, Object>(rawIterator, converter);
	}

	public TableRowIterator query(String query, Connection connection) {
		Assert.notEmpty(query, "query");
		return new ArrayResultSetIterator(connection, query);
	}

	// implementation of the 'Dependent' interface ---------------------------------------------------------------------

    public int countProviders() {
        return getForeignKeyConstraints().size();
    }

    public DBTable getProvider(int index) {
        return foreignKeyConstraints.get(index).getRefereeTable();
    }

    public boolean requiresProvider(int index) {
        String firstFkColumnName = foreignKeyConstraints.get(index).getForeignKeyColumnNames()[0];
		return !getColumn(firstFkColumnName).isNullable();
    }

	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
    public int hashCode() {
		return HashCodeBuilder.hashCode(owner, name);
    }

    public boolean equals(Object other) {
	    if (this == other)
		    return true;
	    if (other == null || !(other instanceof DBTable))
		    return false;
	    DBTable that = (DBTable) other;
	    if (!NullSafeComparator.equals(this.owner, that.getSchema()))
	    	return false;
	    return NullSafeComparator.equals(this.name, that.getName());
    }

}
