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
 * Class DatabaseConfiguration.
 * 
 * @version $Revision$ $Date$
 */
public class DatabaseConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     */
    private org.exolab.jms.config.RdbmsDatabaseConfiguration _rdbmsDatabaseConfiguration;


      //----------------/
     //- Constructors -/
    //----------------/

    public DatabaseConfiguration() {
        super();
    } //-- org.exolab.jms.config.DatabaseConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'rdbmsDatabaseConfiguration'.
     * 
     * @return the value of field 'rdbmsDatabaseConfiguration'.
     */
    public org.exolab.jms.config.RdbmsDatabaseConfiguration getRdbmsDatabaseConfiguration()
    {
        return this._rdbmsDatabaseConfiguration;
    } //-- org.exolab.jms.config.RdbmsDatabaseConfiguration getRdbmsDatabaseConfiguration() 

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
     * Sets the value of field 'rdbmsDatabaseConfiguration'.
     * 
     * @param rdbmsDatabaseConfiguration the value of field
     * 'rdbmsDatabaseConfiguration'.
     */
    public void setRdbmsDatabaseConfiguration(org.exolab.jms.config.RdbmsDatabaseConfiguration rdbmsDatabaseConfiguration)
    {
        this._rdbmsDatabaseConfiguration = rdbmsDatabaseConfiguration;
    } //-- void setRdbmsDatabaseConfiguration(org.exolab.jms.config.RdbmsDatabaseConfiguration) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.DatabaseConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.DatabaseConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.DatabaseConfiguration.class, reader);
    } //-- org.exolab.jms.config.DatabaseConfiguration unmarshal(java.io.Reader) 

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
