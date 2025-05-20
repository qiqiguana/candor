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

package org.databene.jdbacl.dialect;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.databene.commons.ArrayBuilder;
import org.databene.jdbacl.DBUtil;
import org.databene.jdbacl.DatabaseDialect;
import org.databene.jdbacl.model.DBSequence;

/**
 * {@link DatabaseDialect} implementation for the H2 database.
 * See <a href="http://www.h2database.com/html/grammar.html">H2 SQL grammar</a><br/><br/>
 * Created: 28.03.2010 07:54:19
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class H2Dialect extends DatabaseDialect {
    
	private static final String DATE_PATTERN = "''yyyy-MM-dd''";
	private static final String TIME_PATTERN = "''HH:mm:ss''";

	Pattern randomPKNamePattern = Pattern.compile("CONSTRAINT_\\w+");
	Pattern randomUKNamePattern = Pattern.compile("CONSTRAINT_INDEX_\\w+");
	Pattern randomFKNamePattern = Pattern.compile("CONSTRAINT_\\w+");
	Pattern randomIndexNamePattern = Pattern.compile("CONSTRAINT_INDEX_\\w+|PRIMARY_KEY_\\w+");

    public H2Dialect() {
	    super("h2", false, true, DATE_PATTERN, TIME_PATTERN);
    }

	@Override
    public boolean isDefaultCatalog(String catalog, String user) {
	    return (catalog == null);
    }

	@Override
    public boolean isDefaultSchema(String schema, String user) {
	    return "PUBLIC".equalsIgnoreCase(schema);
    }

	@Override
	public boolean isSequenceBoundarySupported() {
		return false;
	}
	
	@Override
    public DBSequence[] querySequences(Connection connection) throws SQLException {
        String query = "select SEQUENCE_CATALOG, SEQUENCE_SCHEMA, SEQUENCE_NAME, CURRENT_VALUE, INCREMENT, CACHE from information_schema.sequences";
        // TODO v0.7 restrict to catalog and schema, see http://www.h2database.com/html/grammar.html
        ArrayBuilder<DBSequence> builder = new ArrayBuilder<DBSequence>(DBSequence.class);
        ResultSet resultSet = DBUtil.executeQuery(query, connection);
        while (resultSet.next()) {
        	String name = resultSet.getString("SEQUENCE_NAME");
        	DBSequence sequence = new DBSequence(name, null);
        	sequence.setStart(new BigInteger(resultSet.getString("CURRENT_VALUE")));
        	sequence.setLastNumber(sequence.getStart());
        	sequence.setIncrement(new BigInteger(resultSet.getString("INCREMENT")));
        	sequence.setCache(resultSet.getLong("CACHE"));
        	builder.add(sequence);
        }
		return builder.toArray();
	}

	@Override
    public String renderFetchSequenceValue(String sequenceName) {
        return "select next value for " + sequenceName;
    }

	@Override
	public void setNextSequenceValue(String sequenceName, long value, Connection connection) throws SQLException {
	    DBUtil.executeUpdate(setSequenceValue(sequenceName, value), connection);
	}

	public String setSequenceValue(String sequenceName, long value) {
	    return "alter sequence " + sequenceName + " restart with " + value;
    }
	
	@Override
	public String renderDropSequence(String name) {
		return "drop sequence " + name;
	}

	@Override
	public boolean isDeterministicPKName(String pkName) {
		return !randomPKNamePattern.matcher(pkName).matches();
	}

	@Override
	public boolean isDeterministicUKName(String ukName) {
		return !randomUKNamePattern.matcher(ukName).matches();
	}

	@Override
	public boolean isDeterministicFKName(String fkName) {
		return !randomFKNamePattern.matcher(fkName).matches();
	}

	@Override
	public boolean isDeterministicIndexName(String indexName) {
		return !randomIndexNamePattern.matcher(indexName).matches();
	}

	@Override
	public boolean supportsRegex() {
		return true;
	}
	
	@Override
	public String regexQuery(String expression, boolean not, String regex) {
		return expression + (not ? " NOT" : "") + " REGEXP '" + regex + "'";
	}

}
