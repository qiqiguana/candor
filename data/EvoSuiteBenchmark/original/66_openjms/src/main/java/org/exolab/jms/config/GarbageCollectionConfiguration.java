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
 * This element defines items specific to the garbage collection
 * service.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class GarbageCollectionConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This indicates how often the server will check the memory 
     *  utilization of the server. 
     *  It will check to ensure that the ratio of free memory to
     * total 
     *  memory doesn't fall below the lowWaterThreshold.
     *  The value is specified in seconds, and defaults to 30
     * seconds if
     *  not set.
     *  
     */
    private int _memoryCheckInterval = 30;

    /**
     * keeps track of state for field: _memoryCheckInterval
     */
    private boolean _has_memoryCheckInterval;

    /**
     * This is the ratio of free memory to total memory, specified
     * as a 
     *  percentage, which will trigger garbage collection.
     *  The default value of 20 indicates that when free memory
     * falls 
     *  below 20% of total memory (i.e. total VM memory) then
     * garbage 
     *  collection will be triggered. The range of valid values is
     *  10-50.
     *  
     */
    private int _lowWaterThreshold = 20;

    /**
     * keeps track of state for field: _lowWaterThreshold
     */
    private boolean _has_lowWaterThreshold;

    /**
     * This indicates how often the garbage collector will run to
     * remove
     *  processed messages from the cache. A value of zero will
     * disable
     *  this capability.
     *  The value is specified in seconds, and defaults to 600
     * seconds if
     *  a value is not set.
     *  
     */
    private int _garbageCollectionInterval = 600;

    /**
     * keeps track of state for field: _garbageCollectionInterval
     */
    private boolean _has_garbageCollectionInterval;

    /**
     * This is the priority assigned to the garbage collection
     * thread.
     *  It ranges from 1-10, and defaults to 5 if a value is not
     * set.
     *  
     */
    private int _garbageCollectionThreadPriority = 5;

    /**
     * keeps track of state for field:
     * _garbageCollectionThreadPriority
     */
    private boolean _has_garbageCollectionThreadPriority;


      //----------------/
     //- Constructors -/
    //----------------/

    public GarbageCollectionConfiguration() {
        super();
    } //-- org.exolab.jms.config.GarbageCollectionConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteGarbageCollectionInterval
     */
    public void deleteGarbageCollectionInterval()
    {
        this._has_garbageCollectionInterval= false;
    } //-- void deleteGarbageCollectionInterval() 

    /**
     * Method deleteGarbageCollectionThreadPriority
     */
    public void deleteGarbageCollectionThreadPriority()
    {
        this._has_garbageCollectionThreadPriority= false;
    } //-- void deleteGarbageCollectionThreadPriority() 

    /**
     * Method deleteLowWaterThreshold
     */
    public void deleteLowWaterThreshold()
    {
        this._has_lowWaterThreshold= false;
    } //-- void deleteLowWaterThreshold() 

    /**
     * Method deleteMemoryCheckInterval
     */
    public void deleteMemoryCheckInterval()
    {
        this._has_memoryCheckInterval= false;
    } //-- void deleteMemoryCheckInterval() 

    /**
     * Returns the value of field 'garbageCollectionInterval'. The
     * field 'garbageCollectionInterval' has the following
     * description: This indicates how often the garbage collector
     * will run to remove
     *  processed messages from the cache. A value of zero will
     * disable
     *  this capability.
     *  The value is specified in seconds, and defaults to 600
     * seconds if
     *  a value is not set.
     *  
     * 
     * @return the value of field 'garbageCollectionInterval'.
     */
    public int getGarbageCollectionInterval()
    {
        return this._garbageCollectionInterval;
    } //-- int getGarbageCollectionInterval() 

    /**
     * Returns the value of field
     * 'garbageCollectionThreadPriority'. The field
     * 'garbageCollectionThreadPriority' has the following
     * description: This is the priority assigned to the garbage
     * collection thread.
     *  It ranges from 1-10, and defaults to 5 if a value is not
     * set.
     *  
     * 
     * @return the value of field 'garbageCollectionThreadPriority'.
     */
    public int getGarbageCollectionThreadPriority()
    {
        return this._garbageCollectionThreadPriority;
    } //-- int getGarbageCollectionThreadPriority() 

    /**
     * Returns the value of field 'lowWaterThreshold'. The field
     * 'lowWaterThreshold' has the following description: This is
     * the ratio of free memory to total memory, specified as a 
     *  percentage, which will trigger garbage collection.
     *  The default value of 20 indicates that when free memory
     * falls 
     *  below 20% of total memory (i.e. total VM memory) then
     * garbage 
     *  collection will be triggered. The range of valid values is
     *  10-50.
     *  
     * 
     * @return the value of field 'lowWaterThreshold'.
     */
    public int getLowWaterThreshold()
    {
        return this._lowWaterThreshold;
    } //-- int getLowWaterThreshold() 

    /**
     * Returns the value of field 'memoryCheckInterval'. The field
     * 'memoryCheckInterval' has the following description: This
     * indicates how often the server will check the memory 
     *  utilization of the server. 
     *  It will check to ensure that the ratio of free memory to
     * total 
     *  memory doesn't fall below the lowWaterThreshold.
     *  The value is specified in seconds, and defaults to 30
     * seconds if
     *  not set.
     *  
     * 
     * @return the value of field 'memoryCheckInterval'.
     */
    public int getMemoryCheckInterval()
    {
        return this._memoryCheckInterval;
    } //-- int getMemoryCheckInterval() 

    /**
     * Method hasGarbageCollectionInterval
     */
    public boolean hasGarbageCollectionInterval()
    {
        return this._has_garbageCollectionInterval;
    } //-- boolean hasGarbageCollectionInterval() 

    /**
     * Method hasGarbageCollectionThreadPriority
     */
    public boolean hasGarbageCollectionThreadPriority()
    {
        return this._has_garbageCollectionThreadPriority;
    } //-- boolean hasGarbageCollectionThreadPriority() 

    /**
     * Method hasLowWaterThreshold
     */
    public boolean hasLowWaterThreshold()
    {
        return this._has_lowWaterThreshold;
    } //-- boolean hasLowWaterThreshold() 

    /**
     * Method hasMemoryCheckInterval
     */
    public boolean hasMemoryCheckInterval()
    {
        return this._has_memoryCheckInterval;
    } //-- boolean hasMemoryCheckInterval() 

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
     * Sets the value of field 'garbageCollectionInterval'. The
     * field 'garbageCollectionInterval' has the following
     * description: This indicates how often the garbage collector
     * will run to remove
     *  processed messages from the cache. A value of zero will
     * disable
     *  this capability.
     *  The value is specified in seconds, and defaults to 600
     * seconds if
     *  a value is not set.
     *  
     * 
     * @param garbageCollectionInterval the value of field
     * 'garbageCollectionInterval'.
     */
    public void setGarbageCollectionInterval(int garbageCollectionInterval)
    {
        this._garbageCollectionInterval = garbageCollectionInterval;
        this._has_garbageCollectionInterval = true;
    } //-- void setGarbageCollectionInterval(int) 

    /**
     * Sets the value of field 'garbageCollectionThreadPriority'.
     * The field 'garbageCollectionThreadPriority' has the
     * following description: This is the priority assigned to the
     * garbage collection thread.
     *  It ranges from 1-10, and defaults to 5 if a value is not
     * set.
     *  
     * 
     * @param garbageCollectionThreadPriority the value of field
     * 'garbageCollectionThreadPriority'.
     */
    public void setGarbageCollectionThreadPriority(int garbageCollectionThreadPriority)
    {
        this._garbageCollectionThreadPriority = garbageCollectionThreadPriority;
        this._has_garbageCollectionThreadPriority = true;
    } //-- void setGarbageCollectionThreadPriority(int) 

    /**
     * Sets the value of field 'lowWaterThreshold'. The field
     * 'lowWaterThreshold' has the following description: This is
     * the ratio of free memory to total memory, specified as a 
     *  percentage, which will trigger garbage collection.
     *  The default value of 20 indicates that when free memory
     * falls 
     *  below 20% of total memory (i.e. total VM memory) then
     * garbage 
     *  collection will be triggered. The range of valid values is
     *  10-50.
     *  
     * 
     * @param lowWaterThreshold the value of field
     * 'lowWaterThreshold'.
     */
    public void setLowWaterThreshold(int lowWaterThreshold)
    {
        this._lowWaterThreshold = lowWaterThreshold;
        this._has_lowWaterThreshold = true;
    } //-- void setLowWaterThreshold(int) 

    /**
     * Sets the value of field 'memoryCheckInterval'. The field
     * 'memoryCheckInterval' has the following description: This
     * indicates how often the server will check the memory 
     *  utilization of the server. 
     *  It will check to ensure that the ratio of free memory to
     * total 
     *  memory doesn't fall below the lowWaterThreshold.
     *  The value is specified in seconds, and defaults to 30
     * seconds if
     *  not set.
     *  
     * 
     * @param memoryCheckInterval the value of field
     * 'memoryCheckInterval'.
     */
    public void setMemoryCheckInterval(int memoryCheckInterval)
    {
        this._memoryCheckInterval = memoryCheckInterval;
        this._has_memoryCheckInterval = true;
    } //-- void setMemoryCheckInterval(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.GarbageCollectionConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.GarbageCollectionConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.GarbageCollectionConfiguration.class, reader);
    } //-- org.exolab.jms.config.GarbageCollectionConfiguration unmarshal(java.io.Reader) 

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
