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
 * This element defines items specific to the message manager.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class MessageManagerConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The maximum size of a destination cache before
     * non-persistent 
     *  messages are discarded.
     *  
     */
    private int _destinationCacheSize;

    /**
     * keeps track of state for field: _destinationCacheSize
     */
    private boolean _has_destinationCacheSize;


      //----------------/
     //- Constructors -/
    //----------------/

    public MessageManagerConfiguration() {
        super();
    } //-- org.exolab.jms.config.MessageManagerConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'destinationCacheSize'. The field
     * 'destinationCacheSize' has the following description: The
     * maximum size of a destination cache before non-persistent 
     *  messages are discarded.
     *  
     * 
     * @return the value of field 'destinationCacheSize'.
     */
    public int getDestinationCacheSize()
    {
        return this._destinationCacheSize;
    } //-- int getDestinationCacheSize() 

    /**
     * Method hasDestinationCacheSize
     */
    public boolean hasDestinationCacheSize()
    {
        return this._has_destinationCacheSize;
    } //-- boolean hasDestinationCacheSize() 

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
     * Sets the value of field 'destinationCacheSize'. The field
     * 'destinationCacheSize' has the following description: The
     * maximum size of a destination cache before non-persistent 
     *  messages are discarded.
     *  
     * 
     * @param destinationCacheSize the value of field
     * 'destinationCacheSize'.
     */
    public void setDestinationCacheSize(int destinationCacheSize)
    {
        this._destinationCacheSize = destinationCacheSize;
        this._has_destinationCacheSize = true;
    } //-- void setDestinationCacheSize(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.MessageManagerConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.MessageManagerConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.MessageManagerConfiguration.class, reader);
    } //-- org.exolab.jms.config.MessageManagerConfiguration unmarshal(java.io.Reader) 

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
