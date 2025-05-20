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

package org.databene.jdbacl.dialect;

import org.databene.jdbacl.DatabaseDialect;

/**
 * {@link DatabaseDialect} implementation for the MySQL database.<br/><br/>
 * Created: 24.06.2011 07:29:20
 * @since 0.6.9
 * @author Volker Bergmann
 */
public class MySQLDialect extends DatabaseDialect {

	private static final String DATE_PATTERN = "''yyyy-MM-dd''";
	private static final String TIME_PATTERN = "''HH:mm:ss''";

	public MySQLDialect() {
	    super("mysql", false, false, DATE_PATTERN, TIME_PATTERN);
	}

	@Override
	public boolean isDefaultCatalog(String catalog, String user) {
		// MySQL does not have a default catalog, so jdbacl has to retrieve it from Connection.getCatalog()
		return false;
	}

	@Override
	public boolean isDefaultSchema(String schema, String user) {
		return false; // MySQL does not make use of schemas at all
	}

	@Override
	public boolean isDeterministicPKName(String pkName) {
		return true; // MySQL always creates deterministic names
	}

	@Override
	public boolean isDeterministicUKName(String ukName) {
		return true; // MySQL always creates deterministic names
	}

	@Override
	public boolean isDeterministicFKName(String fkName) {
		return true; // MySQL always creates deterministic names
	}

	@Override
	public boolean isDeterministicIndexName(String indexName) {
		return true; // MySQL always creates deterministic names
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
