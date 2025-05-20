/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.databene.jdbacl.version;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.databene.commons.version.VersionNumber;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.dialect.HSQLUtil;
import org.junit.Test;

/**
 * Tests the {@link QueryVersionProvider}.<br/><br/>
 * Created: 17.10.2011 16:31:16
 * @since 0.6.12
 * @author Volker Bergmann
 */
public class QueryVersionProviderTest {

	@Test
	public void test() throws Exception {
		Connection connection = HSQLUtil.connectInMemoryDB(getClass().getName());
		DBUtil.executeQuery("create table version_table ( version_number varchar(10) )", connection);
		try {
			DBUtil.executeQuery("insert into version_table values ('1.2.3')", connection);
			QueryVersionProvider provider = new QueryVersionProvider();
			provider.setQuery("select version_number from version_table");
			VersionNumber version = provider.getVersion(connection);
			assertEquals(VersionNumber.valueOf("1.2.3"), version);
		} finally {
			DBUtil.executeQuery("drop table version_table", connection);
		}
	}
	
}
