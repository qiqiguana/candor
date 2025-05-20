/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.persistence;


/**
 * All concrete {@link DBConnectionManager} instances can extend this class.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:jima@intalio.com">Jim Alateras</a>
 * @see         org.exolab.jms.persistence.DBConnectionManager
 */
public abstract class AbstractConnectionManager
    implements DBConnectionManager {

    /**
     * The user name
     */
    private String _user;
    
    /**
     * The user's password
     */
    private String _password;
    
    /**
     * The JDBC driver class name
     */
    private String _driver;

    /**
     * The JDBC url
     */
    private String _url;

    /**
     * The maximum no. of active connections
     */
    private int _maxActive;

    /**
     * The maximum no. of idle connections
     */
    private int _maxIdle;

    /**
     * The minimum time that a connection may remain idle before it may be 
     * evicted, in seconds
     */
    private long _minIdleTime;

    /**
     * The interval between checking idle connections for eviction, in seconds
     */
    private long _evictionInterval;

    /**
     * The SQL query to validate connections
     */
    private String _testQuery;

    /**
     * Determines if connections should be tested before use,
     * using {@link #_testQuery}
     */
    private boolean _testBeforeUse = false;


    /**
     * Sets the user name that is used to obtain the connection
     *
     * @param name the user name
     */
    public void setUser(String name) {
        _user = name;
    }

    /**
     * Returns the user's name
     *
     * @return the user's name
     */
    public String getUser() {
        return _user;
    }

    /**
     * Sets the user's password that is used to access the database
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        _password = password;
    }

    /**
     * Returns the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Sets the JDBC driver class name
     *
     * @param driver the JDBC driver class name
     */
    public void setDriver(String driver) {
        _driver = driver;
    }

    /**
     * Returns the JDBC driver class name
     *
     * @return the JDBC driver class name
     */
    public String getDriver() {
        return _driver;
    }

    /**
     * Sets the URL to the database
     *
     * @param url the JDBC URL
     */
    public void setURL(String url) {
        _url = url;
    }

    /**
     * Returns the JDBC URL
     *
     * @return the JDBC URL
     */
    public String getURL() {
        return _url;
    }

    /**
     * Sets the maximum number of active connections that can be allocated from
     * this pool at the same time, or zero for no limit.
     *
     * @param active the maximum number of active connections
     */
    public void setMaxActive(int active) {
        _maxActive = active;
    }

    /**
     * Returns the maximum number of active connections that can be allocated 
     * from this pool at the same time. 
     *
     * @return the maximum number of active connections
     */
    public int getMaxActive() {
        return _maxActive;
    }

    /**
     * Sets the maximum number of connections that can sit idle in the 
     * connection pool, before connections are evicted.
     *
     * @param idle the maximum number of idle connections
     */
    public void setMaxIdle(int idle) {
        _maxIdle = idle;
    }

    /**
     * Returns the maximum number of connections that can sit idle in the 
     * connection pool, before connections are evicted.
     *
     * @return the maximum number of idle connections
     */
    public int getMaxIdle() {
        return _maxIdle;
    }

    /**
     * Sets the minimum time that a connection may remain idle
     * before it may be evicted, or zero for no eviction.
     *
     * @param time the idle time, in seconds
     */
    public void setMinIdleTime(long time) {
        _minIdleTime = time;
    }

    /**
     * Returns the minimum time that a connection may remain idle
     * before it may be evicted
     *
     * @return the minimum idle time, in seconds
     */
    public long getMinIdleTime() {
        return _minIdleTime;
    }

    /**
     * Sets the interval between checking idle connections for eviction.
     * Idle connections are removed after {@link #setMinIdleTime} seconds, 
     * or if {@ link #testQuery} is specified, and the query fails.
     *
     * @param interval the eviction interval, in seconds
     */
    public void setEvictionInterval(long interval) {
        _evictionInterval = interval;
    }

    /**
     * Returns the interval between checking idle connections for eviction.
     *
     * @return the eviction interval, in seconds
     */
    public long getEvictionInterval() {
        return _evictionInterval;
    }

    /**
     * Specifies an SQL query to validate connections. This query
     * should return at least one row, and be fast to execute.
     *
     * @param query the test query
     */
    public void setTestQuery(String query) {
        _testQuery = query;
    }

    /**
     * Returns the SQL query to validate connections.
     *
     * @return the test query
     */
    public String getTestQuery() {
        return _testQuery;
    }

    /**
     * Determines if connections should be tested before use.
     * If true, each connection is tested before being returned.
     * If a connection fails, it is discarded, and another connection
     * allocated. This ensures a higher reliability, at the cost of
     * performance.
     *
     * @param test if <code>true</code>, each connection is tested use.
     */
    public void setTestBeforeUse(boolean test) {
        _testBeforeUse = test;
    }

    /**
     * Returns if connections should be tested before use.
     *
     * @return <code>true</code> if each connection should be tested before 
     * being used.
     */
    public boolean getTestBeforeUse() {
        return _testBeforeUse;
    }

} //-- AbstractConnectionManager
