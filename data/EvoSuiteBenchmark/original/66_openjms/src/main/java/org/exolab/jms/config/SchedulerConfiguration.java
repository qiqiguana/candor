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
 * This element defines items specific to the scheduler.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class SchedulerConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The maximum number of worker threads that the scheduler
     * uses.
     *  
     */
    private int _maxThreads;

    /**
     * keeps track of state for field: _maxThreads
     */
    private boolean _has_maxThreads;


      //----------------/
     //- Constructors -/
    //----------------/

    public SchedulerConfiguration() {
        super();
    } //-- org.exolab.jms.config.SchedulerConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'maxThreads'. The field
     * 'maxThreads' has the following description: The maximum
     * number of worker threads that the scheduler uses.
     *  
     * 
     * @return the value of field 'maxThreads'.
     */
    public int getMaxThreads()
    {
        return this._maxThreads;
    } //-- int getMaxThreads() 

    /**
     * Method hasMaxThreads
     */
    public boolean hasMaxThreads()
    {
        return this._has_maxThreads;
    } //-- boolean hasMaxThreads() 

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
     * Sets the value of field 'maxThreads'. The field 'maxThreads'
     * has the following description: The maximum number of worker
     * threads that the scheduler uses.
     *  
     * 
     * @param maxThreads the value of field 'maxThreads'.
     */
    public void setMaxThreads(int maxThreads)
    {
        this._maxThreads = maxThreads;
        this._has_maxThreads = true;
    } //-- void setMaxThreads(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.SchedulerConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.SchedulerConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.SchedulerConfiguration.class, reader);
    } //-- org.exolab.jms.config.SchedulerConfiguration unmarshal(java.io.Reader) 

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
