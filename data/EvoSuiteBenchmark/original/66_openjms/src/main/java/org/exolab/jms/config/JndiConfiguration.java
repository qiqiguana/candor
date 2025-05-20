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
import java.util.ArrayList;
import java.util.Enumeration;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * This element defines the properties used when constructing an 
 *  InitialContext. Users only need to specify this if an external
 * JNDI
 *  provider is being used.
 *  Typically the java.naming.factory.initial property must be
 * supplied,
 *  with the fully qualified name of the factory class that will
 * create
 *  an initial context.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class JndiConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * A property name and value that is used to create an 
     *  InitialContext.
     *  
     */
    private java.util.ArrayList _propertyList;


      //----------------/
     //- Constructors -/
    //----------------/

    public JndiConfiguration() {
        super();
        _propertyList = new ArrayList();
    } //-- org.exolab.jms.config.JndiConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addProperty
     * 
     * @param vProperty
     */
    public void addProperty(Property vProperty)
        throws java.lang.IndexOutOfBoundsException
    {
        _propertyList.add(vProperty);
    } //-- void addProperty(Property) 

    /**
     * Method addProperty
     * 
     * @param index
     * @param vProperty
     */
    public void addProperty(int index, Property vProperty)
        throws java.lang.IndexOutOfBoundsException
    {
        _propertyList.add(index, vProperty);
    } //-- void addProperty(int, Property) 

    /**
     * Method clearProperty
     */
    public void clearProperty()
    {
        _propertyList.clear();
    } //-- void clearProperty() 

    /**
     * Method enumerateProperty
     */
    public java.util.Enumeration enumerateProperty()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_propertyList.iterator());
    } //-- java.util.Enumeration enumerateProperty() 

    /**
     * Method getProperty
     * 
     * @param index
     */
    public Property getProperty(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _propertyList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Property) _propertyList.get(index);
    } //-- Property getProperty(int) 

    /**
     * Method getProperty
     */
    public Property[] getProperty()
    {
        int size = _propertyList.size();
        Property[] mArray = new Property[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Property) _propertyList.get(index);
        }
        return mArray;
    } //-- Property[] getProperty() 

    /**
     * Method getPropertyCount
     */
    public int getPropertyCount()
    {
        return _propertyList.size();
    } //-- int getPropertyCount() 

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
     * Method removeProperty
     * 
     * @param vProperty
     */
    public boolean removeProperty(Property vProperty)
    {
        boolean removed = _propertyList.remove(vProperty);
        return removed;
    } //-- boolean removeProperty(Property) 

    /**
     * Method setProperty
     * 
     * @param index
     * @param vProperty
     */
    public void setProperty(int index, Property vProperty)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _propertyList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _propertyList.set(index, vProperty);
    } //-- void setProperty(int, Property) 

    /**
     * Method setProperty
     * 
     * @param propertyArray
     */
    public void setProperty(Property[] propertyArray)
    {
        //-- copy array
        _propertyList.clear();
        for (int i = 0; i < propertyArray.length; i++) {
            _propertyList.add(propertyArray[i]);
        }
    } //-- void setProperty(Property) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.JndiConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.JndiConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.JndiConfiguration.class, reader);
    } //-- org.exolab.jms.config.JndiConfiguration unmarshal(java.io.Reader) 

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
