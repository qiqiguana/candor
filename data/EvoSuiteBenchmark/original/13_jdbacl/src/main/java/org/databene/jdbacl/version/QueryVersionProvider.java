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

import java.sql.Connection;
import java.sql.SQLException;

import org.databene.commons.ConfigurationError;
import org.databene.commons.version.VersionNumber;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.VersionProvider;

/**
 * {@link VersionProvider} implementation which uses a SQL query to fetch the version number.<br/><br/>
 * Created: 17.10.2011 16:03:18
 * @since 0.6.12
 * @author Volker Bergmann
 */
public class QueryVersionProvider implements VersionProvider {
	
	private String query;
	
	public QueryVersionProvider() {
		this(null);
	}
	
	public QueryVersionProvider(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public VersionNumber getVersion(Connection connection) {
		if (query == null)
			throw new ConfigurationError("Query text undefined");
		try {
			Object result = DBUtil.queryAndSimplify(query, connection);
			if (result.getClass().isArray())
				throw new ConfigurationError(getClass() + " expects a single value as result of query " + query);
			return VersionNumber.valueOf(result.toString());
		} catch (SQLException e) {
			throw new RuntimeException("Error executing query " + query, e);
		}
	}

}
