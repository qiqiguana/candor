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

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.depend.Dependent;
import org.databene.commons.iterator.TableRowIterator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents a database table.<br/><br/>
 * Created: 06.01.2007 08:58:49
 * @author Volker Bergmann
 */
public interface DBTable extends ContainerComponent, CompositeDBObject<DBTableComponent>, Dependent<DBTable>, MultiColumnObject {

    String getName();
    String getDoc();
    DBCatalog getCatalog();
    DBSchema getSchema();

    List<DBColumn> getColumns();
    DBColumn[] getColumns(String[] columnNames);
    DBColumn getColumn(String columnName);
	void addColumn(DBColumn column);
    
    List<DBIndex> getIndexes();
    DBIndex getIndex(String indexName);
	void addIndex(DBIndex dbIndex);

    DBPrimaryKeyConstraint getPrimaryKeyConstraint();
    void setPrimaryKey(DBPrimaryKeyConstraint dbPrimaryKeyConstraint);
	String[] getPKColumnNames();

	Set<DBUniqueConstraint> getUniqueConstraints(boolean includePK);
	DBUniqueConstraint getUniqueConstraint(String name);
	void addUniqueConstraint(DBUniqueConstraint uniqueConstraint);

	List<DBCheckConstraint> getCheckConstraints();
	void addCheckConstraint(DBCheckConstraint checkConstraint);

	Set<DBForeignKeyConstraint> getForeignKeyConstraints();
	DBForeignKeyConstraint getForeignKeyConstraint(String[] columnNames);
	void addForeignKey(DBForeignKeyConstraint dbForeignKeyConstraint);

	Collection<DBTable> getReferrers();
	void addReferrer(DBTable table);
	
	long getRowCount(Connection connection);
    DBRow queryByPK(Object pk, Connection connection) throws SQLException;
	DBRowIterator allRows(Connection connection) throws SQLException;
    DBRowIterator queryRowsByCellValues(String[] columnNames, Object[] values, Connection connection) throws SQLException;
    DBRowIterator queryRows(String whereClause, Connection connection) throws SQLException;
    HeavyweightIterator<Object> queryPKs(Connection connection);
	TableRowIterator query(String query, Connection connection);
    
}
