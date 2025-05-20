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
 * This element defines items specific to the OpenJMS server.
 *  By default, OpenJMS uses no security.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class SecurityConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Enables/disables security.
     *  
     */
    private boolean _securityEnabled = false;

    /**
     * keeps track of state for field: _securityEnabled
     */
    private boolean _has_securityEnabled;


      //----------------/
     //- Constructors -/
    //----------------/

    public SecurityConfiguration() {
        super();
    } //-- org.exolab.jms.config.SecurityConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteSecurityEnabled
     */
    public void deleteSecurityEnabled()
    {
        this._has_securityEnabled= false;
    } //-- void deleteSecurityEnabled() 

    /**
     * Returns the value of field 'securityEnabled'. The field
     * 'securityEnabled' has the following description:
     * Enables/disables security.
     *  
     * 
     * @return the value of field 'securityEnabled'.
     */
    public boolean getSecurityEnabled()
    {
        return this._securityEnabled;
    } //-- boolean getSecurityEnabled() 

    /**
     * Method hasSecurityEnabled
     */
    public boolean hasSecurityEnabled()
    {
        return this._has_securityEnabled;
    } //-- boolean hasSecurityEnabled() 

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
     * Sets the value of field 'securityEnabled'. The field
     * 'securityEnabled' has the following description:
     * Enables/disables security.
     *  
     * 
     * @param securityEnabled the value of field 'securityEnabled'.
     */
    public void setSecurityEnabled(boolean securityEnabled)
    {
        this._securityEnabled = securityEnabled;
        this._has_securityEnabled = true;
    } //-- void setSecurityEnabled(boolean) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.SecurityConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.SecurityConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.SecurityConfiguration.class, reader);
    } //-- org.exolab.jms.config.SecurityConfiguration unmarshal(java.io.Reader) 

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
