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
 * This element is used to bootstrap the creation of administered
 * topics 
 *  and queues. It also allows the client to register durable
 * subscribers 
 *  for administered topics.
 *  
 *  OpenJMS also supports topic hierarchy and wildcard
 * subscriptions. 
 *  A topic hierarchy is defined as a sequence of names separated
 * by a '.' 
 *  (i.e a.b.c).
 *  Users can subscribe to multiple topics using '*' and the '**'.
 * A '*' 
 *  will wildcard one level in the hierarchy and a '**' will
 * wildcard all 
 *  subsequent levels (i.e a.*, a.b.** or **). 
 *  
 * 
 * @version $Revision$ $Date$
 */
public class AdministeredDestinations implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This element defines an administered topic, and any durable
     *  subscribers.
     *  
     */
    private java.util.ArrayList _administeredTopicList;

    /**
     * This element defines an administered queue.
     *  
     */
    private java.util.ArrayList _administeredQueueList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AdministeredDestinations() {
        super();
        _administeredTopicList = new ArrayList();
        _administeredQueueList = new ArrayList();
    } //-- org.exolab.jms.config.AdministeredDestinations()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAdministeredQueue
     * 
     * @param vAdministeredQueue
     */
    public void addAdministeredQueue(AdministeredQueue vAdministeredQueue)
        throws java.lang.IndexOutOfBoundsException
    {
        _administeredQueueList.add(vAdministeredQueue);
    } //-- void addAdministeredQueue(AdministeredQueue) 

    /**
     * Method addAdministeredQueue
     * 
     * @param index
     * @param vAdministeredQueue
     */
    public void addAdministeredQueue(int index, AdministeredQueue vAdministeredQueue)
        throws java.lang.IndexOutOfBoundsException
    {
        _administeredQueueList.add(index, vAdministeredQueue);
    } //-- void addAdministeredQueue(int, AdministeredQueue) 

    /**
     * Method addAdministeredTopic
     * 
     * @param vAdministeredTopic
     */
    public void addAdministeredTopic(AdministeredTopic vAdministeredTopic)
        throws java.lang.IndexOutOfBoundsException
    {
        _administeredTopicList.add(vAdministeredTopic);
    } //-- void addAdministeredTopic(AdministeredTopic) 

    /**
     * Method addAdministeredTopic
     * 
     * @param index
     * @param vAdministeredTopic
     */
    public void addAdministeredTopic(int index, AdministeredTopic vAdministeredTopic)
        throws java.lang.IndexOutOfBoundsException
    {
        _administeredTopicList.add(index, vAdministeredTopic);
    } //-- void addAdministeredTopic(int, AdministeredTopic) 

    /**
     * Method clearAdministeredQueue
     */
    public void clearAdministeredQueue()
    {
        _administeredQueueList.clear();
    } //-- void clearAdministeredQueue() 

    /**
     * Method clearAdministeredTopic
     */
    public void clearAdministeredTopic()
    {
        _administeredTopicList.clear();
    } //-- void clearAdministeredTopic() 

    /**
     * Method enumerateAdministeredQueue
     */
    public java.util.Enumeration enumerateAdministeredQueue()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_administeredQueueList.iterator());
    } //-- java.util.Enumeration enumerateAdministeredQueue() 

    /**
     * Method enumerateAdministeredTopic
     */
    public java.util.Enumeration enumerateAdministeredTopic()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_administeredTopicList.iterator());
    } //-- java.util.Enumeration enumerateAdministeredTopic() 

    /**
     * Method getAdministeredQueue
     * 
     * @param index
     */
    public AdministeredQueue getAdministeredQueue(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _administeredQueueList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (AdministeredQueue) _administeredQueueList.get(index);
    } //-- AdministeredQueue getAdministeredQueue(int) 

    /**
     * Method getAdministeredQueue
     */
    public AdministeredQueue[] getAdministeredQueue()
    {
        int size = _administeredQueueList.size();
        AdministeredQueue[] mArray = new AdministeredQueue[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (AdministeredQueue) _administeredQueueList.get(index);
        }
        return mArray;
    } //-- AdministeredQueue[] getAdministeredQueue() 

    /**
     * Method getAdministeredQueueCount
     */
    public int getAdministeredQueueCount()
    {
        return _administeredQueueList.size();
    } //-- int getAdministeredQueueCount() 

    /**
     * Method getAdministeredTopic
     * 
     * @param index
     */
    public AdministeredTopic getAdministeredTopic(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _administeredTopicList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (AdministeredTopic) _administeredTopicList.get(index);
    } //-- AdministeredTopic getAdministeredTopic(int) 

    /**
     * Method getAdministeredTopic
     */
    public AdministeredTopic[] getAdministeredTopic()
    {
        int size = _administeredTopicList.size();
        AdministeredTopic[] mArray = new AdministeredTopic[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (AdministeredTopic) _administeredTopicList.get(index);
        }
        return mArray;
    } //-- AdministeredTopic[] getAdministeredTopic() 

    /**
     * Method getAdministeredTopicCount
     */
    public int getAdministeredTopicCount()
    {
        return _administeredTopicList.size();
    } //-- int getAdministeredTopicCount() 

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
     * Method removeAdministeredQueue
     * 
     * @param vAdministeredQueue
     */
    public boolean removeAdministeredQueue(AdministeredQueue vAdministeredQueue)
    {
        boolean removed = _administeredQueueList.remove(vAdministeredQueue);
        return removed;
    } //-- boolean removeAdministeredQueue(AdministeredQueue) 

    /**
     * Method removeAdministeredTopic
     * 
     * @param vAdministeredTopic
     */
    public boolean removeAdministeredTopic(AdministeredTopic vAdministeredTopic)
    {
        boolean removed = _administeredTopicList.remove(vAdministeredTopic);
        return removed;
    } //-- boolean removeAdministeredTopic(AdministeredTopic) 

    /**
     * Method setAdministeredQueue
     * 
     * @param index
     * @param vAdministeredQueue
     */
    public void setAdministeredQueue(int index, AdministeredQueue vAdministeredQueue)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _administeredQueueList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _administeredQueueList.set(index, vAdministeredQueue);
    } //-- void setAdministeredQueue(int, AdministeredQueue) 

    /**
     * Method setAdministeredQueue
     * 
     * @param administeredQueueArray
     */
    public void setAdministeredQueue(AdministeredQueue[] administeredQueueArray)
    {
        //-- copy array
        _administeredQueueList.clear();
        for (int i = 0; i < administeredQueueArray.length; i++) {
            _administeredQueueList.add(administeredQueueArray[i]);
        }
    } //-- void setAdministeredQueue(AdministeredQueue) 

    /**
     * Method setAdministeredTopic
     * 
     * @param index
     * @param vAdministeredTopic
     */
    public void setAdministeredTopic(int index, AdministeredTopic vAdministeredTopic)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _administeredTopicList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _administeredTopicList.set(index, vAdministeredTopic);
    } //-- void setAdministeredTopic(int, AdministeredTopic) 

    /**
     * Method setAdministeredTopic
     * 
     * @param administeredTopicArray
     */
    public void setAdministeredTopic(AdministeredTopic[] administeredTopicArray)
    {
        //-- copy array
        _administeredTopicList.clear();
        for (int i = 0; i < administeredTopicArray.length; i++) {
            _administeredTopicList.add(administeredTopicArray[i]);
        }
    } //-- void setAdministeredTopic(AdministeredTopic) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.AdministeredDestinations unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.AdministeredDestinations) Unmarshaller.unmarshal(org.exolab.jms.config.AdministeredDestinations.class, reader);
    } //-- org.exolab.jms.config.AdministeredDestinations unmarshal(java.io.Reader) 

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
