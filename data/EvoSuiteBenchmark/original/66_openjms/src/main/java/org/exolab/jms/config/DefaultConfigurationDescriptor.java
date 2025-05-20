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

import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.validators.*;

/**
 * Class DefaultConfigurationDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class DefaultConfigurationDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private java.lang.String nsPrefix;

    /**
     * Field nsURI
     */
    private java.lang.String nsURI;

    /**
     * Field xmlName
     */
    private java.lang.String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public DefaultConfigurationDescriptor() {
        super();
        nsURI = "http://openjms.exolab.org/configuration";
        xmlName = "DefaultConfiguration";
        
        //-- set grouping compositor
        setCompositorAsSequence();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.xml.XMLFieldHandler              handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors
        
        //-- initialize element descriptors
        
        //-- _serverConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.ServerConfiguration.class, "_serverConfiguration", "ServerConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getServerConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setServerConfiguration( (org.exolab.jms.config.ServerConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.ServerConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _serverConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _connectors
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.Connectors.class, "_connectors", "Connectors", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getConnectors();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setConnectors( (org.exolab.jms.config.Connectors) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.Connectors();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _connectors
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _tcpConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.TcpConfiguration.class, "_tcpConfiguration", "TcpConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getTcpConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setTcpConfiguration( (org.exolab.jms.config.TcpConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.TcpConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _tcpConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _tcpsConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.TcpsConfiguration.class, "_tcpsConfiguration", "TcpsConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getTcpsConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setTcpsConfiguration( (org.exolab.jms.config.TcpsConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.TcpsConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _tcpsConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _rmiConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.RmiConfiguration.class, "_rmiConfiguration", "RmiConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getRmiConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setRmiConfiguration( (org.exolab.jms.config.RmiConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.RmiConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _rmiConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _httpConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.HttpConfiguration.class, "_httpConfiguration", "HttpConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getHttpConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setHttpConfiguration( (org.exolab.jms.config.HttpConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.HttpConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _httpConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _httpsConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.HttpsConfiguration.class, "_httpsConfiguration", "HttpsConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getHttpsConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setHttpsConfiguration( (org.exolab.jms.config.HttpsConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.HttpsConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _httpsConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _messageManagerConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.MessageManagerConfiguration.class, "_messageManagerConfiguration", "MessageManagerConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getMessageManagerConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setMessageManagerConfiguration( (org.exolab.jms.config.MessageManagerConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.MessageManagerConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _messageManagerConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _schedulerConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.SchedulerConfiguration.class, "_schedulerConfiguration", "SchedulerConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getSchedulerConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setSchedulerConfiguration( (org.exolab.jms.config.SchedulerConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.SchedulerConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _schedulerConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _garbageCollectionConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.GarbageCollectionConfiguration.class, "_garbageCollectionConfiguration", "GarbageCollectionConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getGarbageCollectionConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setGarbageCollectionConfiguration( (org.exolab.jms.config.GarbageCollectionConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.GarbageCollectionConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _garbageCollectionConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _loggerConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.LoggerConfiguration.class, "_loggerConfiguration", "LoggerConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getLoggerConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setLoggerConfiguration( (org.exolab.jms.config.LoggerConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.LoggerConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _loggerConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _securityConfiguration
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.exolab.jms.config.SecurityConfiguration.class, "_securityConfiguration", "SecurityConfiguration", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                DefaultConfiguration target = (DefaultConfiguration) object;
                return target.getSecurityConfiguration();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    DefaultConfiguration target = (DefaultConfiguration) object;
                    target.setSecurityConfiguration( (org.exolab.jms.config.SecurityConfiguration) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new org.exolab.jms.config.SecurityConfiguration();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _securityConfiguration
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    } //-- org.exolab.jms.config.DefaultConfigurationDescriptor()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method getAccessMode
     */
    public org.exolab.castor.mapping.AccessMode getAccessMode()
    {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode() 

    /**
     * Method getExtends
     */
    public org.exolab.castor.mapping.ClassDescriptor getExtends()
    {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends() 

    /**
     * Method getIdentity
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity()
    {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity() 

    /**
     * Method getJavaClass
     */
    public java.lang.Class getJavaClass()
    {
        return org.exolab.jms.config.DefaultConfiguration.class;
    } //-- java.lang.Class getJavaClass() 

    /**
     * Method getNameSpacePrefix
     */
    public java.lang.String getNameSpacePrefix()
    {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix() 

    /**
     * Method getNameSpaceURI
     */
    public java.lang.String getNameSpaceURI()
    {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI() 

    /**
     * Method getValidator
     */
    public org.exolab.castor.xml.TypeValidator getValidator()
    {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator() 

    /**
     * Method getXMLName
     */
    public java.lang.String getXMLName()
    {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
