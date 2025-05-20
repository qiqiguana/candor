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
 * This element defines an administered topic, and any durable
 *  subscribers.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class AdministeredTopic implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The name of the topic. This must be unique.
     *  
     */
    private java.lang.String _name;

    /**
     * This defines a durable subscriber to an administered topic
     *  
     */
    private java.util.ArrayList _subscriberList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AdministeredTopic() {
        super();
        _subscriberList = new ArrayList();
    } //-- org.exolab.jms.config.AdministeredTopic()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSubscriber
     * 
     * @param vSubscriber
     */
    public void addSubscriber(Subscriber vSubscriber)
        throws java.lang.IndexOutOfBoundsException
    {
        _subscriberList.add(vSubscriber);
    } //-- void addSubscriber(Subscriber) 

    /**
     * Method addSubscriber
     * 
     * @param index
     * @param vSubscriber
     */
    public void addSubscriber(int index, Subscriber vSubscriber)
        throws java.lang.IndexOutOfBoundsException
    {
        _subscriberList.add(index, vSubscriber);
    } //-- void addSubscriber(int, Subscriber) 

    /**
     * Method clearSubscriber
     */
    public void clearSubscriber()
    {
        _subscriberList.clear();
    } //-- void clearSubscriber() 

    /**
     * Method enumerateSubscriber
     */
    public java.util.Enumeration enumerateSubscriber()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_subscriberList.iterator());
    } //-- java.util.Enumeration enumerateSubscriber() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The name of the topic. This must be
     * unique.
     *  
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getSubscriber
     * 
     * @param index
     */
    public Subscriber getSubscriber(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subscriberList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Subscriber) _subscriberList.get(index);
    } //-- Subscriber getSubscriber(int) 

    /**
     * Method getSubscriber
     */
    public Subscriber[] getSubscriber()
    {
        int size = _subscriberList.size();
        Subscriber[] mArray = new Subscriber[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Subscriber) _subscriberList.get(index);
        }
        return mArray;
    } //-- Subscriber[] getSubscriber() 

    /**
     * Method getSubscriberCount
     */
    public int getSubscriberCount()
    {
        return _subscriberList.size();
    } //-- int getSubscriberCount() 

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
     * Method removeSubscriber
     * 
     * @param vSubscriber
     */
    public boolean removeSubscriber(Subscriber vSubscriber)
    {
        boolean removed = _subscriberList.remove(vSubscriber);
        return removed;
    } //-- boolean removeSubscriber(Subscriber) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The name of the topic. This must be
     * unique.
     *  
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Method setSubscriber
     * 
     * @param index
     * @param vSubscriber
     */
    public void setSubscriber(int index, Subscriber vSubscriber)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subscriberList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _subscriberList.set(index, vSubscriber);
    } //-- void setSubscriber(int, Subscriber) 

    /**
     * Method setSubscriber
     * 
     * @param subscriberArray
     */
    public void setSubscriber(Subscriber[] subscriberArray)
    {
        //-- copy array
        _subscriberList.clear();
        for (int i = 0; i < subscriberArray.length; i++) {
            _subscriberList.add(subscriberArray[i]);
        }
    } //-- void setSubscriber(Subscriber) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.AdministeredTopic unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.AdministeredTopic) Unmarshaller.unmarshal(org.exolab.jms.config.AdministeredTopic.class, reader);
    } //-- org.exolab.jms.config.AdministeredTopic unmarshal(java.io.Reader) 

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
