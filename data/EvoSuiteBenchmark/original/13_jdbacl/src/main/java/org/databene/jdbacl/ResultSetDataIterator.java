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

package org.databene.jdbacl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.databene.commons.IOUtil;
import org.databene.commons.StringUtil;
import org.databene.webdecs.DataContainer;
import org.databene.webdecs.DataIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides {@link DataIterator}-style access to a {@link ResultSet}.<br/><br/>
 * Created: 03.08.2011 19:24:35
 * @since 0.6.10
 * @author Volker Bergmann
 */
public class ResultSetDataIterator implements DataIterator<ResultSet> {

    private ResultSet resultSet;
    private String[] columnLabels;

    private String query;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public ResultSetDataIterator(ResultSet resultSet) {
        this(resultSet, "");
    }

    public ResultSetDataIterator(ResultSet resultSet, String query) {
    	if (resultSet == null)
    		throw new IllegalArgumentException("resultSet is null");
        this.resultSet = resultSet;
        if (StringUtil.isEmpty(query))
        	throw new IllegalArgumentException("query is empty");
        this.query = query;
    }

    // interface -------------------------------------------------------------------------------------------------------

    public String[] getColumnLabels() {
    	if (columnLabels == null) {
    		try {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int n = metaData.getColumnCount();
	            columnLabels = new String[n];
	            for (int i = 0; i < n; i++)
	            	columnLabels[i] = metaData.getColumnLabel(i + 1);
            } catch (SQLException e) {
	            throw new RuntimeException("Error querying column meta data", e);
            }
    	}
    	return columnLabels;
    }
    
	public Class<ResultSet> getType() {
		return ResultSet.class;
	}

	public DataContainer<ResultSet> next(DataContainer<ResultSet> container) {
        LOGGER.debug("next() called on {}", this);
        if (resultSet == null)
        	return null;
		try {
			if (resultSet.next()) {
				return container.setData(resultSet);
			} else {
				IOUtil.close(this);
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    public synchronized void close() {
        LOGGER.debug("closing {}", this);
    	if (resultSet == null)
    		return;
    	DBUtil.closeResultSetAndStatement(resultSet);
    	resultSet = null;
    }
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + query + ']';
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultSetIterator.class);

}
