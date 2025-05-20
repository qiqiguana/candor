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
 * This element defines items specific to the TCPS connector.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class TcpsConfiguration extends org.exolab.jms.config.TcpConfigurationType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The path to the keystore.
     *  
     */
    private java.lang.String _keyStore = "${openjms.home}/config/openjms.keystore";

    /**
     * The keystore password.
     *  
     */
    private java.lang.String _keyStorePassword = "openjms";

    /**
     * The keystore type. Supported values are "JKS" and "PKCS12".
     *  
     */
    private java.lang.String _keyStoreType = "JKS";

    /**
     * The path to the truststore. If unset, this defaults to the
     *  keystore path.
     *  
     */
    private java.lang.String _trustStore;

    /**
     * The truststore password. If unset, this defaults to the
     * value
     *  of keystorePassword.
     *  
     */
    private java.lang.String _trustStorePassword;

    /**
     * The truststore type. Supported values are "JKS" and
     * "PKCS12".
     *  
     */
    private java.lang.String _trustStoreType = "JKS";

    /**
     * Determines if connections which are accepted must include
     * client
     *  authentication. By default, clients do not need to provide
     *  authentication information.
     *  
     */
    private boolean _needClientAuth = false;

    /**
     * keeps track of state for field: _needClientAuth
     */
    private boolean _has_needClientAuth;


      //----------------/
     //- Constructors -/
    //----------------/

    public TcpsConfiguration() {
        super();
        setKeyStore("${openjms.home}/config/openjms.keystore");
        setKeyStorePassword("openjms");
        setKeyStoreType("JKS");
        setTrustStoreType("JKS");
    } //-- org.exolab.jms.config.TcpsConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteNeedClientAuth
     */
    public void deleteNeedClientAuth()
    {
        this._has_needClientAuth= false;
    } //-- void deleteNeedClientAuth() 

    /**
     * Returns the value of field 'keyStore'. The field 'keyStore'
     * has the following description: The path to the keystore.
     *  
     * 
     * @return the value of field 'keyStore'.
     */
    public java.lang.String getKeyStore()
    {
        return this._keyStore;
    } //-- java.lang.String getKeyStore() 

    /**
     * Returns the value of field 'keyStorePassword'. The field
     * 'keyStorePassword' has the following description: The
     * keystore password.
     *  
     * 
     * @return the value of field 'keyStorePassword'.
     */
    public java.lang.String getKeyStorePassword()
    {
        return this._keyStorePassword;
    } //-- java.lang.String getKeyStorePassword() 

    /**
     * Returns the value of field 'keyStoreType'. The field
     * 'keyStoreType' has the following description: The keystore
     * type. Supported values are "JKS" and "PKCS12".
     *  
     * 
     * @return the value of field 'keyStoreType'.
     */
    public java.lang.String getKeyStoreType()
    {
        return this._keyStoreType;
    } //-- java.lang.String getKeyStoreType() 

    /**
     * Returns the value of field 'needClientAuth'. The field
     * 'needClientAuth' has the following description: Determines
     * if connections which are accepted must include client
     *  authentication. By default, clients do not need to provide
     *  authentication information.
     *  
     * 
     * @return the value of field 'needClientAuth'.
     */
    public boolean getNeedClientAuth()
    {
        return this._needClientAuth;
    } //-- boolean getNeedClientAuth() 

    /**
     * Returns the value of field 'trustStore'. The field
     * 'trustStore' has the following description: The path to the
     * truststore. If unset, this defaults to the
     *  keystore path.
     *  
     * 
     * @return the value of field 'trustStore'.
     */
    public java.lang.String getTrustStore()
    {
        return this._trustStore;
    } //-- java.lang.String getTrustStore() 

    /**
     * Returns the value of field 'trustStorePassword'. The field
     * 'trustStorePassword' has the following description: The
     * truststore password. If unset, this defaults to the value
     *  of keystorePassword.
     *  
     * 
     * @return the value of field 'trustStorePassword'.
     */
    public java.lang.String getTrustStorePassword()
    {
        return this._trustStorePassword;
    } //-- java.lang.String getTrustStorePassword() 

    /**
     * Returns the value of field 'trustStoreType'. The field
     * 'trustStoreType' has the following description: The
     * truststore type. Supported values are "JKS" and "PKCS12".
     *  
     * 
     * @return the value of field 'trustStoreType'.
     */
    public java.lang.String getTrustStoreType()
    {
        return this._trustStoreType;
    } //-- java.lang.String getTrustStoreType() 

    /**
     * Method hasNeedClientAuth
     */
    public boolean hasNeedClientAuth()
    {
        return this._has_needClientAuth;
    } //-- boolean hasNeedClientAuth() 

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
     * Sets the value of field 'keyStore'. The field 'keyStore' has
     * the following description: The path to the keystore.
     *  
     * 
     * @param keyStore the value of field 'keyStore'.
     */
    public void setKeyStore(java.lang.String keyStore)
    {
        this._keyStore = keyStore;
    } //-- void setKeyStore(java.lang.String) 

    /**
     * Sets the value of field 'keyStorePassword'. The field
     * 'keyStorePassword' has the following description: The
     * keystore password.
     *  
     * 
     * @param keyStorePassword the value of field 'keyStorePassword'
     */
    public void setKeyStorePassword(java.lang.String keyStorePassword)
    {
        this._keyStorePassword = keyStorePassword;
    } //-- void setKeyStorePassword(java.lang.String) 

    /**
     * Sets the value of field 'keyStoreType'. The field
     * 'keyStoreType' has the following description: The keystore
     * type. Supported values are "JKS" and "PKCS12".
     *  
     * 
     * @param keyStoreType the value of field 'keyStoreType'.
     */
    public void setKeyStoreType(java.lang.String keyStoreType)
    {
        this._keyStoreType = keyStoreType;
    } //-- void setKeyStoreType(java.lang.String) 

    /**
     * Sets the value of field 'needClientAuth'. The field
     * 'needClientAuth' has the following description: Determines
     * if connections which are accepted must include client
     *  authentication. By default, clients do not need to provide
     *  authentication information.
     *  
     * 
     * @param needClientAuth the value of field 'needClientAuth'.
     */
    public void setNeedClientAuth(boolean needClientAuth)
    {
        this._needClientAuth = needClientAuth;
        this._has_needClientAuth = true;
    } //-- void setNeedClientAuth(boolean) 

    /**
     * Sets the value of field 'trustStore'. The field 'trustStore'
     * has the following description: The path to the truststore.
     * If unset, this defaults to the
     *  keystore path.
     *  
     * 
     * @param trustStore the value of field 'trustStore'.
     */
    public void setTrustStore(java.lang.String trustStore)
    {
        this._trustStore = trustStore;
    } //-- void setTrustStore(java.lang.String) 

    /**
     * Sets the value of field 'trustStorePassword'. The field
     * 'trustStorePassword' has the following description: The
     * truststore password. If unset, this defaults to the value
     *  of keystorePassword.
     *  
     * 
     * @param trustStorePassword the value of field
     * 'trustStorePassword'.
     */
    public void setTrustStorePassword(java.lang.String trustStorePassword)
    {
        this._trustStorePassword = trustStorePassword;
    } //-- void setTrustStorePassword(java.lang.String) 

    /**
     * Sets the value of field 'trustStoreType'. The field
     * 'trustStoreType' has the following description: The
     * truststore type. Supported values are "JKS" and "PKCS12".
     *  
     * 
     * @param trustStoreType the value of field 'trustStoreType'.
     */
    public void setTrustStoreType(java.lang.String trustStoreType)
    {
        this._trustStoreType = trustStoreType;
    } //-- void setTrustStoreType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.TcpsConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.TcpsConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.TcpsConfiguration.class, reader);
    } //-- org.exolab.jms.config.TcpsConfiguration unmarshal(java.io.Reader) 

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
