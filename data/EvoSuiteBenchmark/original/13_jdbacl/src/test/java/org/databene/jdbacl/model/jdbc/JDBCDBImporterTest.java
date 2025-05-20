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

package org.databene.jdbacl.model.jdbc;

import java.sql.Connection;
import java.util.List;

import org.databene.commons.ErrorHandler;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.dialect.HSQLUtil;
import org.databene.jdbacl.model.DBIndex;
import org.databene.jdbacl.model.DBNonUniqueIndex;
import org.databene.jdbacl.model.DBSchema;
import org.databene.jdbacl.model.DBTable;
import org.databene.jdbacl.model.DBUniqueIndex;
import org.databene.jdbacl.model.Database;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link JDBCDBImporter}.<br/><br/>
 * Created at 03.05.2008 08:59:20
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class JDBCDBImporterTest {
	
	@Test
	public void testImportDatabase_HSQL() throws Exception {
		// prepare database
		Connection connection = HSQLUtil.connectInMemoryDB(getClass().getSimpleName());
		DBUtil.runScript("org/databene/jdbacl/model/jdbc/create_tables.hsql.sql", "ISO-8859-1", connection, true, new ErrorHandler(getClass()));
		// run importer
		JDBCDBImporter importer = new JDBCDBImporter(connection, "sa", "public");
		Database db = importer.importDatabase();
		// check schema
		DBSchema schema = db.getCatalog(null).getSchema("public");
		assertNotNull(schema);
		// check tables
		assertEquals(1, schema.getTables().size());
		DBTable table = schema.getTable("T1");
		// check indexes
		List<DBIndex> indexes = table.getIndexes();
		assertEquals(3, indexes.size());
		for (DBIndex index : indexes) {
			if (index instanceof DBNonUniqueIndex) {
				// non-unique nickname index
				assertEquals(1, index.getColumnNames().length);
				assertTrue("NICKNAME".equalsIgnoreCase(index.getColumnNames()[0]));
			} else if (index instanceof DBUniqueIndex) {
				if (index.getColumnNames().length == 1) {
					// PK index
					assertTrue("ID".equalsIgnoreCase(index.getColumnNames()[0]));
				} else {
					// unique composite index (namespace,name)
					assertEquals(2, index.getColumnNames().length);
					assertTrue("NAMESPACE".equalsIgnoreCase(index.getColumnNames()[0]));
					assertTrue("NAME".equalsIgnoreCase(index.getColumnNames()[1]));
				}
			} else
				fail("Unexpected index type: " + index.getClass() + '(' + index + ')');
		}
	}

}
