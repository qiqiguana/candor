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
import org.exolab.jms.config.types.SchemeType;
import org.xml.sax.ContentHandler;

/**
 * This specifies the type of communication to use, between clients
 *  and the server.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Connector implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scheme
     */
    private org.exolab.jms.config.types.SchemeType _scheme;

    /**
     * Connection factories are bound in JNDI by the OpenJMS
     * server. This
     *  element defines the type and binding of factories.
     *  
     */
    private org.exolab.jms.config.ConnectionFactories _connectionFactories;


      //----------------/
     //- Constructors -/
    //----------------/

    public Connector() {
        super();
    } //-- org.exolab.jms.config.Connector()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'connectionFactories'. The field
     * 'connectionFactories' has the following description:
     * Connection factories are bound in JNDI by the OpenJMS
     * server. This
     *  element defines the type and binding of factories.
     *  
     * 
     * @return the value of field 'connectionFactories'.
     */
    public org.exolab.jms.config.ConnectionFactories getConnectionFactories()
    {
        return this._connectionFactories;
    } //-- org.exolab.jms.config.ConnectionFactories getConnectionFactories() 

    /**
     * Returns the value of field 'scheme'.
     * 
     * @return the value of field 'scheme'.
     */
    public org.exolab.jms.config.types.SchemeType getScheme()
    {
        return this._scheme;
    } //-- org.exolab.jms.config.types.SchemeType getScheme() 

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
     * Sets the value of field 'connectionFactories'. The field
     * 'connectionFactories' has the following description:
     * Connection factories are bound in JNDI by the OpenJMS
     * server. This
     *  element defines the type and binding of factories.
     *  
     * 
     * @param connectionFactories the value of field
     * 'connectionFactories'.
     */
    public void setConnectionFactories(org.exolab.jms.config.ConnectionFactories connectionFactories)
    {
        this._connectionFactories = connectionFactories;
    } //-- void setConnectionFactories(org.exolab.jms.config.ConnectionFactories) 

    /**
     * Sets the value of field 'scheme'.
     * 
     * @param scheme the value of field 'scheme'.
     */
    public void setScheme(org.exolab.jms.config.types.SchemeType scheme)
    {
        this._scheme = scheme;
    } //-- void setScheme(org.exolab.jms.config.types.SchemeType) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.Connector unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.Connector) Unmarshaller.unmarshal(org.exolab.jms.config.Connector.class, reader);
    } //-- org.exolab.jms.config.Connector unmarshal(java.io.Reader) 

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
