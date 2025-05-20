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
 * Class Server.
 * 
 * @version $Revision$ $Date$
 */
public class Server implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scheme
     */
    private org.exolab.jms.config.types.SchemeType _scheme;

    /**
     * Field _proxyClass
     */
    private java.lang.String _proxyClass;

    /**
     * Field _implementationClass
     */
    private java.lang.String _implementationClass;


      //----------------/
     //- Constructors -/
    //----------------/

    public Server() {
        super();
    } //-- org.exolab.jms.config.Server()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'implementationClass'.
     * 
     * @return the value of field 'implementationClass'.
     */
    public java.lang.String getImplementationClass()
    {
        return this._implementationClass;
    } //-- java.lang.String getImplementationClass() 

    /**
     * Returns the value of field 'proxyClass'.
     * 
     * @return the value of field 'proxyClass'.
     */
    public java.lang.String getProxyClass()
    {
        return this._proxyClass;
    } //-- java.lang.String getProxyClass() 

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
     * Sets the value of field 'implementationClass'.
     * 
     * @param implementationClass the value of field
     * 'implementationClass'.
     */
    public void setImplementationClass(java.lang.String implementationClass)
    {
        this._implementationClass = implementationClass;
    } //-- void setImplementationClass(java.lang.String) 

    /**
     * Sets the value of field 'proxyClass'.
     * 
     * @param proxyClass the value of field 'proxyClass'.
     */
    public void setProxyClass(java.lang.String proxyClass)
    {
        this._proxyClass = proxyClass;
    } //-- void setProxyClass(java.lang.String) 

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
    public static org.exolab.jms.config.Server unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.Server) Unmarshaller.unmarshal(org.exolab.jms.config.Server.class, reader);
    } //-- org.exolab.jms.config.Server unmarshal(java.io.Reader) 

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
