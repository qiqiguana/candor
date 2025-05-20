/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.exolab.jms.config;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * 
 * 
 * @version $Revision$ $Date$
 */
public class RdbmsDatabaseConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The connection manager, which pools connections to the
     * underlying
     *  database.
     *  
     */
    private java.lang.String _clazz = "org.exolab.jms.persistence.DBCPConnectionManager";

    /**
     * The JDBC driver, which must be XA compliant, used to access
     * the 
     *  database.
     *  
     */
    private java.lang.String _driver;

    /**
     * The JDBC url.
     *  
     */
    private java.lang.String _url;

    /**
     * The database user.
     *  
     */
    private java.lang.String _user;

    /**
     * The user password.
     *  
     */
    private java.lang.String _password;

    /**
     * The maximum no. of active connections that can be allocated
     * from
     *  the connection pool, or zero for no limit.
     *  
     */
    private int _maxActive = 10;

    /**
     * keeps track of state for field: _maxActive
     */
    private boolean _has_maxActive;

    /**
     * The maximum no. of connections that can sit idle in the
     * connection
     *  pool, before connections are evicted.
     *  
     */
    private int _maxIdle = 10;

    /**
     * keeps track of state for field: _maxIdle
     */
    private boolean _has_maxIdle;

    /**
     * Specifies the minimum time that a connection may remain idle
     *  before it may be evicted. 
     *  Use "0" to disable eviction of idle connections.
     *  
     */
    private int _minIdleTime = 0;

    /**
     * keeps track of state for field: _minIdleTime
     */
    private boolean _has_minIdleTime;

    /**
     * Specifies the interval, in seconds, between checking idle
     *  connections. Idle connections are removed after
     * 'minIdleTime'
     *  seconds, or if 'testQuery' is specified and the query
     * fails.
     *  
     */
    private int _evictionInterval;

    /**
     * keeps track of state for field: _evictionInterval
     */
    private boolean _has_evictionInterval;

    /**
     * Specifies an SQL query to validate connections. This query
     *  should return at least one row, and be fast to execute.
     *  
     */
    private java.lang.String _testQuery;

    /**
     * If true, each connection is tested before being used, using
     *  'testQuery'.
     *  If a connection fails, it is discarded, and another
     * connection
     *  allocated. This ensures a higher reliability, at the cost
     * of
     *  performance.
     *  
     */
    private boolean _testBeforeUse = false;

    /**
     * keeps track of state for field: _testBeforeUse
     */
    private boolean _has_testBeforeUse;

    /**
     * Determines whether or not to batch SQL requests. This will
     *  only be useful in certain scenarios (i.e small to medium
     * message
     *  size). This can be controlled with the batchSize parameter.
     *  
     */
    private boolean _batch = false;

    /**
     * keeps track of state for field: _batch
     */
    private boolean _has_batch;

    /**
     * The number of SQL requests to batch before committing them
     * to 
     *  the database.
     *  
     */
    private int _batchSize = 10;

    /**
     * keeps track of state for field: _batchSize
     */
    private boolean _has_batchSize;


      //----------------/
     //- Constructors -/
    //----------------/

    public RdbmsDatabaseConfiguration() {
        super();
        setClazz("org.exolab.jms.persistence.DBCPConnectionManager");
    } //-- org.exolab.jms.config.RdbmsDatabaseConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteBatch
     */
    public void deleteBatch()
    {
        this._has_batch= false;
    } //-- void deleteBatch() 

    /**
     * Method deleteBatchSize
     */
    public void deleteBatchSize()
    {
        this._has_batchSize= false;
    } //-- void deleteBatchSize() 

    /**
     * Method deleteEvictionInterval
     */
    public void deleteEvictionInterval()
    {
        this._has_evictionInterval= false;
    } //-- void deleteEvictionInterval() 

    /**
     * Method deleteMaxActive
     */
    public void deleteMaxActive()
    {
        this._has_maxActive= false;
    } //-- void deleteMaxActive() 

    /**
     * Method deleteMaxIdle
     */
    public void deleteMaxIdle()
    {
        this._has_maxIdle= false;
    } //-- void deleteMaxIdle() 

    /**
     * Method deleteMinIdleTime
     */
    public void deleteMinIdleTime()
    {
        this._has_minIdleTime= false;
    } //-- void deleteMinIdleTime() 

    /**
     * Method deleteTestBeforeUse
     */
    public void deleteTestBeforeUse()
    {
        this._has_testBeforeUse= false;
    } //-- void deleteTestBeforeUse() 

    /**
     * Returns the value of field 'batch'. The field 'batch' has
     * the following description: Determines whether or not to
     * batch SQL requests. This will
     *  only be useful in certain scenarios (i.e small to medium
     * message
     *  size). This can be controlled with the batchSize parameter.
     *  
     * 
     * @return the value of field 'batch'.
     */
    public boolean getBatch()
    {
        return this._batch;
    } //-- boolean getBatch() 

    /**
     * Returns the value of field 'batchSize'. The field
     * 'batchSize' has the following description: The number of SQL
     * requests to batch before committing them to 
     *  the database.
     *  
     * 
     * @return the value of field 'batchSize'.
     */
    public int getBatchSize()
    {
        return this._batchSize;
    } //-- int getBatchSize() 

    /**
     * Returns the value of field 'clazz'. The field 'clazz' has
     * the following description: The connection manager, which
     * pools connections to the underlying
     *  database.
     *  
     * 
     * @return the value of field 'clazz'.
     */
    public java.lang.String getClazz()
    {
        return this._clazz;
    } //-- java.lang.String getClazz() 

    /**
     * Returns the value of field 'driver'. The field 'driver' has
     * the following description: The JDBC driver, which must be XA
     * compliant, used to access the 
     *  database.
     *  
     * 
     * @return the value of field 'driver'.
     */
    public java.lang.String getDriver()
    {
        return this._driver;
    } //-- java.lang.String getDriver() 

    /**
     * Returns the value of field 'evictionInterval'. The field
     * 'evictionInterval' has the following description: Specifies
     * the interval, in seconds, between checking idle
     *  connections. Idle connections are removed after
     * 'minIdleTime'
     *  seconds, or if 'testQuery' is specified and the query
     * fails.
     *  
     * 
     * @return the value of field 'evictionInterval'.
     */
    public int getEvictionInterval()
    {
        return this._evictionInterval;
    } //-- int getEvictionInterval() 

    /**
     * Returns the value of field 'maxActive'. The field
     * 'maxActive' has the following description: The maximum no.
     * of active connections that can be allocated from
     *  the connection pool, or zero for no limit.
     *  
     * 
     * @return the value of field 'maxActive'.
     */
    public int getMaxActive()
    {
        return this._maxActive;
    } //-- int getMaxActive() 

    /**
     * Returns the value of field 'maxIdle'. The field 'maxIdle'
     * has the following description: The maximum no. of
     * connections that can sit idle in the connection
     *  pool, before connections are evicted.
     *  
     * 
     * @return the value of field 'maxIdle'.
     */
    public int getMaxIdle()
    {
        return this._maxIdle;
    } //-- int getMaxIdle() 

    /**
     * Returns the value of field 'minIdleTime'. The field
     * 'minIdleTime' has the following description: Specifies the
     * minimum time that a connection may remain idle
     *  before it may be evicted. 
     *  Use "0" to disable eviction of idle connections.
     *  
     * 
     * @return the value of field 'minIdleTime'.
     */
    public int getMinIdleTime()
    {
        return this._minIdleTime;
    } //-- int getMinIdleTime() 

    /**
     * Returns the value of field 'password'. The field 'password'
     * has the following description: The user password.
     *  
     * 
     * @return the value of field 'password'.
     */
    public java.lang.String getPassword()
    {
        return this._password;
    } //-- java.lang.String getPassword() 

    /**
     * Returns the value of field 'testBeforeUse'. The field
     * 'testBeforeUse' has the following description: If true, each
     * connection is tested before being used, using
     *  'testQuery'.
     *  If a connection fails, it is discarded, and another
     * connection
     *  allocated. This ensures a higher reliability, at the cost
     * of
     *  performance.
     *  
     * 
     * @return the value of field 'testBeforeUse'.
     */
    public boolean getTestBeforeUse()
    {
        return this._testBeforeUse;
    } //-- boolean getTestBeforeUse() 

    /**
     * Returns the value of field 'testQuery'. The field
     * 'testQuery' has the following description: Specifies an SQL
     * query to validate connections. This query
     *  should return at least one row, and be fast to execute.
     *  
     * 
     * @return the value of field 'testQuery'.
     */
    public java.lang.String getTestQuery()
    {
        return this._testQuery;
    } //-- java.lang.String getTestQuery() 

    /**
     * Returns the value of field 'url'. The field 'url' has the
     * following description: The JDBC url.
     *  
     * 
     * @return the value of field 'url'.
     */
    public java.lang.String getUrl()
    {
        return this._url;
    } //-- java.lang.String getUrl() 

    /**
     * Returns the value of field 'user'. The field 'user' has the
     * following description: The database user.
     *  
     * 
     * @return the value of field 'user'.
     */
    public java.lang.String getUser()
    {
        return this._user;
    } //-- java.lang.String getUser() 

    /**
     * Method hasBatch
     */
    public boolean hasBatch()
    {
        return this._has_batch;
    } //-- boolean hasBatch() 

    /**
     * Method hasBatchSize
     */
    public boolean hasBatchSize()
    {
        return this._has_batchSize;
    } //-- boolean hasBatchSize() 

    /**
     * Method hasEvictionInterval
     */
    public boolean hasEvictionInterval()
    {
        return this._has_evictionInterval;
    } //-- boolean hasEvictionInterval() 

    /**
     * Method hasMaxActive
     */
    public boolean hasMaxActive()
    {
        return this._has_maxActive;
    } //-- boolean hasMaxActive() 

    /**
     * Method hasMaxIdle
     */
    public boolean hasMaxIdle()
    {
        return this._has_maxIdle;
    } //-- boolean hasMaxIdle() 

    /**
     * Method hasMinIdleTime
     */
    public boolean hasMinIdleTime()
    {
        return this._has_minIdleTime;
    } //-- boolean hasMinIdleTime() 

    /**
     * Method hasTestBeforeUse
     */
    public boolean hasTestBeforeUse()
    {
        return this._has_testBeforeUse;
    } //-- boolean hasTestBeforeUse() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'batch'. The field 'batch' has the
     * following description: Determines whether or not to batch
     * SQL requests. This will
     *  only be useful in certain scenarios (i.e small to medium
     * message
     *  size). This can be controlled with the batchSize parameter.
     *  
     * 
     * @param batch the value of field 'batch'.
     */
    public void setBatch(boolean batch)
    {
        this._batch = batch;
        this._has_batch = true;
    } //-- void setBatch(boolean) 

    /**
     * Sets the value of field 'batchSize'. The field 'batchSize'
     * has the following description: The number of SQL requests to
     * batch before committing them to 
     *  the database.
     *  
     * 
     * @param batchSize the value of field 'batchSize'.
     */
    public void setBatchSize(int batchSize)
    {
        this._batchSize = batchSize;
        this._has_batchSize = true;
    } //-- void setBatchSize(int) 

    /**
     * Sets the value of field 'clazz'. The field 'clazz' has the
     * following description: The connection manager, which pools
     * connections to the underlying
     *  database.
     *  
     * 
     * @param clazz the value of field 'clazz'.
     */
    public void setClazz(java.lang.String clazz)
    {
        this._clazz = clazz;
    } //-- void setClazz(java.lang.String) 

    /**
     * Sets the value of field 'driver'. The field 'driver' has the
     * following description: The JDBC driver, which must be XA
     * compliant, used to access the 
     *  database.
     *  
     * 
     * @param driver the value of field 'driver'.
     */
    public void setDriver(java.lang.String driver)
    {
        this._driver = driver;
    } //-- void setDriver(java.lang.String) 

    /**
     * Sets the value of field 'evictionInterval'. The field
     * 'evictionInterval' has the following description: Specifies
     * the interval, in seconds, between checking idle
     *  connections. Idle connections are removed after
     * 'minIdleTime'
     *  seconds, or if 'testQuery' is specified and the query
     * fails.
     *  
     * 
     * @param evictionInterval the value of field 'evictionInterval'
     */
    public void setEvictionInterval(int evictionInterval)
    {
        this._evictionInterval = evictionInterval;
        this._has_evictionInterval = true;
    } //-- void setEvictionInterval(int) 

    /**
     * Sets the value of field 'maxActive'. The field 'maxActive'
     * has the following description: The maximum no. of active
     * connections that can be allocated from
     *  the connection pool, or zero for no limit.
     *  
     * 
     * @param maxActive the value of field 'maxActive'.
     */
    public void setMaxActive(int maxActive)
    {
        this._maxActive = maxActive;
        this._has_maxActive = true;
    } //-- void setMaxActive(int) 

    /**
     * Sets the value of field 'maxIdle'. The field 'maxIdle' has
     * the following description: The maximum no. of connections
     * that can sit idle in the connection
     *  pool, before connections are evicted.
     *  
     * 
     * @param maxIdle the value of field 'maxIdle'.
     */
    public void setMaxIdle(int maxIdle)
    {
        this._maxIdle = maxIdle;
        this._has_maxIdle = true;
    } //-- void setMaxIdle(int) 

    /**
     * Sets the value of field 'minIdleTime'. The field
     * 'minIdleTime' has the following description: Specifies the
     * minimum time that a connection may remain idle
     *  before it may be evicted. 
     *  Use "0" to disable eviction of idle connections.
     *  
     * 
     * @param minIdleTime the value of field 'minIdleTime'.
     */
    public void setMinIdleTime(int minIdleTime)
    {
        this._minIdleTime = minIdleTime;
        this._has_minIdleTime = true;
    } //-- void setMinIdleTime(int) 

    /**
     * Sets the value of field 'password'. The field 'password' has
     * the following description: The user password.
     *  
     * 
     * @param password the value of field 'password'.
     */
    public void setPassword(java.lang.String password)
    {
        this._password = password;
    } //-- void setPassword(java.lang.String) 

    /**
     * Sets the value of field 'testBeforeUse'. The field
     * 'testBeforeUse' has the following description: If true, each
     * connection is tested before being used, using
     *  'testQuery'.
     *  If a connection fails, it is discarded, and another
     * connection
     *  allocated. This ensures a higher reliability, at the cost
     * of
     *  performance.
     *  
     * 
     * @param testBeforeUse the value of field 'testBeforeUse'.
     */
    public void setTestBeforeUse(boolean testBeforeUse)
    {
        this._testBeforeUse = testBeforeUse;
        this._has_testBeforeUse = true;
    } //-- void setTestBeforeUse(boolean) 

    /**
     * Sets the value of field 'testQuery'. The field 'testQuery'
     * has the following description: Specifies an SQL query to
     * validate connections. This query
     *  should return at least one row, and be fast to execute.
     *  
     * 
     * @param testQuery the value of field 'testQuery'.
     */
    public void setTestQuery(java.lang.String testQuery)
    {
        this._testQuery = testQuery;
    } //-- void setTestQuery(java.lang.String) 

    /**
     * Sets the value of field 'url'. The field 'url' has the
     * following description: The JDBC url.
     *  
     * 
     * @param url the value of field 'url'.
     */
    public void setUrl(java.lang.String url)
    {
        this._url = url;
    } //-- void setUrl(java.lang.String) 

    /**
     * Sets the value of field 'user'. The field 'user' has the
     * following description: The database user.
     *  
     * 
     * @param user the value of field 'user'.
     */
    public void setUser(java.lang.String user)
    {
        this._user = user;
    } //-- void setUser(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.RdbmsDatabaseConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.RdbmsDatabaseConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.RdbmsDatabaseConfiguration.class, reader);
    } //-- org.exolab.jms.config.RdbmsDatabaseConfiguration unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
