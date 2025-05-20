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
 * Connection factories are bound in JNDI by the OpenJMS server.
 * This
 *  element defines the type and binding of factories.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class ConnectionFactories implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _connectionFactoryList
     */
    private java.util.ArrayList _connectionFactoryList;

    /**
     * Field _queueConnectionFactoryList
     */
    private java.util.ArrayList _queueConnectionFactoryList;

    /**
     * Field _topicConnectionFactoryList
     */
    private java.util.ArrayList _topicConnectionFactoryList;

    /**
     * Field _XAConnectionFactoryList
     */
    private java.util.ArrayList _XAConnectionFactoryList;

    /**
     * Field _XAQueueConnectionFactoryList
     */
    private java.util.ArrayList _XAQueueConnectionFactoryList;

    /**
     * Field _XATopicConnectionFactoryList
     */
    private java.util.ArrayList _XATopicConnectionFactoryList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConnectionFactories() {
        super();
        _connectionFactoryList = new ArrayList();
        _queueConnectionFactoryList = new ArrayList();
        _topicConnectionFactoryList = new ArrayList();
        _XAConnectionFactoryList = new ArrayList();
        _XAQueueConnectionFactoryList = new ArrayList();
        _XATopicConnectionFactoryList = new ArrayList();
    } //-- org.exolab.jms.config.ConnectionFactories()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addConnectionFactory
     * 
     * @param vConnectionFactory
     */
    public void addConnectionFactory(ConnectionFactory vConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectionFactoryList.add(vConnectionFactory);
    } //-- void addConnectionFactory(ConnectionFactory) 

    /**
     * Method addConnectionFactory
     * 
     * @param index
     * @param vConnectionFactory
     */
    public void addConnectionFactory(int index, ConnectionFactory vConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectionFactoryList.add(index, vConnectionFactory);
    } //-- void addConnectionFactory(int, ConnectionFactory) 

    /**
     * Method addQueueConnectionFactory
     * 
     * @param vQueueConnectionFactory
     */
    public void addQueueConnectionFactory(QueueConnectionFactory vQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _queueConnectionFactoryList.add(vQueueConnectionFactory);
    } //-- void addQueueConnectionFactory(QueueConnectionFactory) 

    /**
     * Method addQueueConnectionFactory
     * 
     * @param index
     * @param vQueueConnectionFactory
     */
    public void addQueueConnectionFactory(int index, QueueConnectionFactory vQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _queueConnectionFactoryList.add(index, vQueueConnectionFactory);
    } //-- void addQueueConnectionFactory(int, QueueConnectionFactory) 

    /**
     * Method addTopicConnectionFactory
     * 
     * @param vTopicConnectionFactory
     */
    public void addTopicConnectionFactory(TopicConnectionFactory vTopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _topicConnectionFactoryList.add(vTopicConnectionFactory);
    } //-- void addTopicConnectionFactory(TopicConnectionFactory) 

    /**
     * Method addTopicConnectionFactory
     * 
     * @param index
     * @param vTopicConnectionFactory
     */
    public void addTopicConnectionFactory(int index, TopicConnectionFactory vTopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _topicConnectionFactoryList.add(index, vTopicConnectionFactory);
    } //-- void addTopicConnectionFactory(int, TopicConnectionFactory) 

    /**
     * Method addXAConnectionFactory
     * 
     * @param vXAConnectionFactory
     */
    public void addXAConnectionFactory(XAConnectionFactory vXAConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XAConnectionFactoryList.add(vXAConnectionFactory);
    } //-- void addXAConnectionFactory(XAConnectionFactory) 

    /**
     * Method addXAConnectionFactory
     * 
     * @param index
     * @param vXAConnectionFactory
     */
    public void addXAConnectionFactory(int index, XAConnectionFactory vXAConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XAConnectionFactoryList.add(index, vXAConnectionFactory);
    } //-- void addXAConnectionFactory(int, XAConnectionFactory) 

    /**
     * Method addXAQueueConnectionFactory
     * 
     * @param vXAQueueConnectionFactory
     */
    public void addXAQueueConnectionFactory(XAQueueConnectionFactory vXAQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XAQueueConnectionFactoryList.add(vXAQueueConnectionFactory);
    } //-- void addXAQueueConnectionFactory(XAQueueConnectionFactory) 

    /**
     * Method addXAQueueConnectionFactory
     * 
     * @param index
     * @param vXAQueueConnectionFactory
     */
    public void addXAQueueConnectionFactory(int index, XAQueueConnectionFactory vXAQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XAQueueConnectionFactoryList.add(index, vXAQueueConnectionFactory);
    } //-- void addXAQueueConnectionFactory(int, XAQueueConnectionFactory) 

    /**
     * Method addXATopicConnectionFactory
     * 
     * @param vXATopicConnectionFactory
     */
    public void addXATopicConnectionFactory(XATopicConnectionFactory vXATopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XATopicConnectionFactoryList.add(vXATopicConnectionFactory);
    } //-- void addXATopicConnectionFactory(XATopicConnectionFactory) 

    /**
     * Method addXATopicConnectionFactory
     * 
     * @param index
     * @param vXATopicConnectionFactory
     */
    public void addXATopicConnectionFactory(int index, XATopicConnectionFactory vXATopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        _XATopicConnectionFactoryList.add(index, vXATopicConnectionFactory);
    } //-- void addXATopicConnectionFactory(int, XATopicConnectionFactory) 

    /**
     * Method clearConnectionFactory
     */
    public void clearConnectionFactory()
    {
        _connectionFactoryList.clear();
    } //-- void clearConnectionFactory() 

    /**
     * Method clearQueueConnectionFactory
     */
    public void clearQueueConnectionFactory()
    {
        _queueConnectionFactoryList.clear();
    } //-- void clearQueueConnectionFactory() 

    /**
     * Method clearTopicConnectionFactory
     */
    public void clearTopicConnectionFactory()
    {
        _topicConnectionFactoryList.clear();
    } //-- void clearTopicConnectionFactory() 

    /**
     * Method clearXAConnectionFactory
     */
    public void clearXAConnectionFactory()
    {
        _XAConnectionFactoryList.clear();
    } //-- void clearXAConnectionFactory() 

    /**
     * Method clearXAQueueConnectionFactory
     */
    public void clearXAQueueConnectionFactory()
    {
        _XAQueueConnectionFactoryList.clear();
    } //-- void clearXAQueueConnectionFactory() 

    /**
     * Method clearXATopicConnectionFactory
     */
    public void clearXATopicConnectionFactory()
    {
        _XATopicConnectionFactoryList.clear();
    } //-- void clearXATopicConnectionFactory() 

    /**
     * Method enumerateConnectionFactory
     */
    public java.util.Enumeration enumerateConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_connectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateConnectionFactory() 

    /**
     * Method enumerateQueueConnectionFactory
     */
    public java.util.Enumeration enumerateQueueConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_queueConnectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateQueueConnectionFactory() 

    /**
     * Method enumerateTopicConnectionFactory
     */
    public java.util.Enumeration enumerateTopicConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_topicConnectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateTopicConnectionFactory() 

    /**
     * Method enumerateXAConnectionFactory
     */
    public java.util.Enumeration enumerateXAConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_XAConnectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateXAConnectionFactory() 

    /**
     * Method enumerateXAQueueConnectionFactory
     */
    public java.util.Enumeration enumerateXAQueueConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_XAQueueConnectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateXAQueueConnectionFactory() 

    /**
     * Method enumerateXATopicConnectionFactory
     */
    public java.util.Enumeration enumerateXATopicConnectionFactory()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_XATopicConnectionFactoryList.iterator());
    } //-- java.util.Enumeration enumerateXATopicConnectionFactory() 

    /**
     * Method getConnectionFactory
     * 
     * @param index
     */
    public ConnectionFactory getConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (ConnectionFactory) _connectionFactoryList.get(index);
    } //-- ConnectionFactory getConnectionFactory(int) 

    /**
     * Method getConnectionFactory
     */
    public ConnectionFactory[] getConnectionFactory()
    {
        int size = _connectionFactoryList.size();
        ConnectionFactory[] mArray = new ConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (ConnectionFactory) _connectionFactoryList.get(index);
        }
        return mArray;
    } //-- ConnectionFactory[] getConnectionFactory() 

    /**
     * Method getConnectionFactoryCount
     */
    public int getConnectionFactoryCount()
    {
        return _connectionFactoryList.size();
    } //-- int getConnectionFactoryCount() 

    /**
     * Method getQueueConnectionFactory
     * 
     * @param index
     */
    public QueueConnectionFactory getQueueConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _queueConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (QueueConnectionFactory) _queueConnectionFactoryList.get(index);
    } //-- QueueConnectionFactory getQueueConnectionFactory(int) 

    /**
     * Method getQueueConnectionFactory
     */
    public QueueConnectionFactory[] getQueueConnectionFactory()
    {
        int size = _queueConnectionFactoryList.size();
        QueueConnectionFactory[] mArray = new QueueConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (QueueConnectionFactory) _queueConnectionFactoryList.get(index);
        }
        return mArray;
    } //-- QueueConnectionFactory[] getQueueConnectionFactory() 

    /**
     * Method getQueueConnectionFactoryCount
     */
    public int getQueueConnectionFactoryCount()
    {
        return _queueConnectionFactoryList.size();
    } //-- int getQueueConnectionFactoryCount() 

    /**
     * Method getTopicConnectionFactory
     * 
     * @param index
     */
    public TopicConnectionFactory getTopicConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _topicConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (TopicConnectionFactory) _topicConnectionFactoryList.get(index);
    } //-- TopicConnectionFactory getTopicConnectionFactory(int) 

    /**
     * Method getTopicConnectionFactory
     */
    public TopicConnectionFactory[] getTopicConnectionFactory()
    {
        int size = _topicConnectionFactoryList.size();
        TopicConnectionFactory[] mArray = new TopicConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (TopicConnectionFactory) _topicConnectionFactoryList.get(index);
        }
        return mArray;
    } //-- TopicConnectionFactory[] getTopicConnectionFactory() 

    /**
     * Method getTopicConnectionFactoryCount
     */
    public int getTopicConnectionFactoryCount()
    {
        return _topicConnectionFactoryList.size();
    } //-- int getTopicConnectionFactoryCount() 

    /**
     * Method getXAConnectionFactory
     * 
     * @param index
     */
    public XAConnectionFactory getXAConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XAConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (XAConnectionFactory) _XAConnectionFactoryList.get(index);
    } //-- XAConnectionFactory getXAConnectionFactory(int) 

    /**
     * Method getXAConnectionFactory
     */
    public XAConnectionFactory[] getXAConnectionFactory()
    {
        int size = _XAConnectionFactoryList.size();
        XAConnectionFactory[] mArray = new XAConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (XAConnectionFactory) _XAConnectionFactoryList.get(index);
        }
        return mArray;
    } //-- XAConnectionFactory[] getXAConnectionFactory() 

    /**
     * Method getXAConnectionFactoryCount
     */
    public int getXAConnectionFactoryCount()
    {
        return _XAConnectionFactoryList.size();
    } //-- int getXAConnectionFactoryCount() 

    /**
     * Method getXAQueueConnectionFactory
     * 
     * @param index
     */
    public XAQueueConnectionFactory getXAQueueConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XAQueueConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (XAQueueConnectionFactory) _XAQueueConnectionFactoryList.get(index);
    } //-- XAQueueConnectionFactory getXAQueueConnectionFactory(int) 

    /**
     * Method getXAQueueConnectionFactory
     */
    public XAQueueConnectionFactory[] getXAQueueConnectionFactory()
    {
        int size = _XAQueueConnectionFactoryList.size();
        XAQueueConnectionFactory[] mArray = new XAQueueConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (XAQueueConnectionFactory) _XAQueueConnectionFactoryList.get(index);
        }
        return mArray;
    } //-- XAQueueConnectionFactory[] getXAQueueConnectionFactory() 

    /**
     * Method getXAQueueConnectionFactoryCount
     */
    public int getXAQueueConnectionFactoryCount()
    {
        return _XAQueueConnectionFactoryList.size();
    } //-- int getXAQueueConnectionFactoryCount() 

    /**
     * Method getXATopicConnectionFactory
     * 
     * @param index
     */
    public XATopicConnectionFactory getXATopicConnectionFactory(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XATopicConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (XATopicConnectionFactory) _XATopicConnectionFactoryList.get(index);
    } //-- XATopicConnectionFactory getXATopicConnectionFactory(int) 

    /**
     * Method getXATopicConnectionFactory
     */
    public XATopicConnectionFactory[] getXATopicConnectionFactory()
    {
        int size = _XATopicConnectionFactoryList.size();
        XATopicConnectionFactory[] mArray = new XATopicConnectionFactory[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (XATopicConnectionFactory) _XATopicConnectionFactoryList.get(index);
        }
        return mArray;
    } //-- XATopicConnectionFactory[] getXATopicConnectionFactory() 

    /**
     * Method getXATopicConnectionFactoryCount
     */
    public int getXATopicConnectionFactoryCount()
    {
        return _XATopicConnectionFactoryList.size();
    } //-- int getXATopicConnectionFactoryCount() 

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
     * Method removeConnectionFactory
     * 
     * @param vConnectionFactory
     */
    public boolean removeConnectionFactory(ConnectionFactory vConnectionFactory)
    {
        boolean removed = _connectionFactoryList.remove(vConnectionFactory);
        return removed;
    } //-- boolean removeConnectionFactory(ConnectionFactory) 

    /**
     * Method removeQueueConnectionFactory
     * 
     * @param vQueueConnectionFactory
     */
    public boolean removeQueueConnectionFactory(QueueConnectionFactory vQueueConnectionFactory)
    {
        boolean removed = _queueConnectionFactoryList.remove(vQueueConnectionFactory);
        return removed;
    } //-- boolean removeQueueConnectionFactory(QueueConnectionFactory) 

    /**
     * Method removeTopicConnectionFactory
     * 
     * @param vTopicConnectionFactory
     */
    public boolean removeTopicConnectionFactory(TopicConnectionFactory vTopicConnectionFactory)
    {
        boolean removed = _topicConnectionFactoryList.remove(vTopicConnectionFactory);
        return removed;
    } //-- boolean removeTopicConnectionFactory(TopicConnectionFactory) 

    /**
     * Method removeXAConnectionFactory
     * 
     * @param vXAConnectionFactory
     */
    public boolean removeXAConnectionFactory(XAConnectionFactory vXAConnectionFactory)
    {
        boolean removed = _XAConnectionFactoryList.remove(vXAConnectionFactory);
        return removed;
    } //-- boolean removeXAConnectionFactory(XAConnectionFactory) 

    /**
     * Method removeXAQueueConnectionFactory
     * 
     * @param vXAQueueConnectionFactory
     */
    public boolean removeXAQueueConnectionFactory(XAQueueConnectionFactory vXAQueueConnectionFactory)
    {
        boolean removed = _XAQueueConnectionFactoryList.remove(vXAQueueConnectionFactory);
        return removed;
    } //-- boolean removeXAQueueConnectionFactory(XAQueueConnectionFactory) 

    /**
     * Method removeXATopicConnectionFactory
     * 
     * @param vXATopicConnectionFactory
     */
    public boolean removeXATopicConnectionFactory(XATopicConnectionFactory vXATopicConnectionFactory)
    {
        boolean removed = _XATopicConnectionFactoryList.remove(vXATopicConnectionFactory);
        return removed;
    } //-- boolean removeXATopicConnectionFactory(XATopicConnectionFactory) 

    /**
     * Method setConnectionFactory
     * 
     * @param index
     * @param vConnectionFactory
     */
    public void setConnectionFactory(int index, ConnectionFactory vConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _connectionFactoryList.set(index, vConnectionFactory);
    } //-- void setConnectionFactory(int, ConnectionFactory) 

    /**
     * Method setConnectionFactory
     * 
     * @param connectionFactoryArray
     */
    public void setConnectionFactory(ConnectionFactory[] connectionFactoryArray)
    {
        //-- copy array
        _connectionFactoryList.clear();
        for (int i = 0; i < connectionFactoryArray.length; i++) {
            _connectionFactoryList.add(connectionFactoryArray[i]);
        }
    } //-- void setConnectionFactory(ConnectionFactory) 

    /**
     * Method setQueueConnectionFactory
     * 
     * @param index
     * @param vQueueConnectionFactory
     */
    public void setQueueConnectionFactory(int index, QueueConnectionFactory vQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _queueConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _queueConnectionFactoryList.set(index, vQueueConnectionFactory);
    } //-- void setQueueConnectionFactory(int, QueueConnectionFactory) 

    /**
     * Method setQueueConnectionFactory
     * 
     * @param queueConnectionFactoryArray
     */
    public void setQueueConnectionFactory(QueueConnectionFactory[] queueConnectionFactoryArray)
    {
        //-- copy array
        _queueConnectionFactoryList.clear();
        for (int i = 0; i < queueConnectionFactoryArray.length; i++) {
            _queueConnectionFactoryList.add(queueConnectionFactoryArray[i]);
        }
    } //-- void setQueueConnectionFactory(QueueConnectionFactory) 

    /**
     * Method setTopicConnectionFactory
     * 
     * @param index
     * @param vTopicConnectionFactory
     */
    public void setTopicConnectionFactory(int index, TopicConnectionFactory vTopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _topicConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _topicConnectionFactoryList.set(index, vTopicConnectionFactory);
    } //-- void setTopicConnectionFactory(int, TopicConnectionFactory) 

    /**
     * Method setTopicConnectionFactory
     * 
     * @param topicConnectionFactoryArray
     */
    public void setTopicConnectionFactory(TopicConnectionFactory[] topicConnectionFactoryArray)
    {
        //-- copy array
        _topicConnectionFactoryList.clear();
        for (int i = 0; i < topicConnectionFactoryArray.length; i++) {
            _topicConnectionFactoryList.add(topicConnectionFactoryArray[i]);
        }
    } //-- void setTopicConnectionFactory(TopicConnectionFactory) 

    /**
     * Method setXAConnectionFactory
     * 
     * @param index
     * @param vXAConnectionFactory
     */
    public void setXAConnectionFactory(int index, XAConnectionFactory vXAConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XAConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _XAConnectionFactoryList.set(index, vXAConnectionFactory);
    } //-- void setXAConnectionFactory(int, XAConnectionFactory) 

    /**
     * Method setXAConnectionFactory
     * 
     * @param XAConnectionFactoryArray
     */
    public void setXAConnectionFactory(XAConnectionFactory[] XAConnectionFactoryArray)
    {
        //-- copy array
        _XAConnectionFactoryList.clear();
        for (int i = 0; i < XAConnectionFactoryArray.length; i++) {
            _XAConnectionFactoryList.add(XAConnectionFactoryArray[i]);
        }
    } //-- void setXAConnectionFactory(XAConnectionFactory) 

    /**
     * Method setXAQueueConnectionFactory
     * 
     * @param index
     * @param vXAQueueConnectionFactory
     */
    public void setXAQueueConnectionFactory(int index, XAQueueConnectionFactory vXAQueueConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XAQueueConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _XAQueueConnectionFactoryList.set(index, vXAQueueConnectionFactory);
    } //-- void setXAQueueConnectionFactory(int, XAQueueConnectionFactory) 

    /**
     * Method setXAQueueConnectionFactory
     * 
     * @param XAQueueConnectionFactoryArray
     */
    public void setXAQueueConnectionFactory(XAQueueConnectionFactory[] XAQueueConnectionFactoryArray)
    {
        //-- copy array
        _XAQueueConnectionFactoryList.clear();
        for (int i = 0; i < XAQueueConnectionFactoryArray.length; i++) {
            _XAQueueConnectionFactoryList.add(XAQueueConnectionFactoryArray[i]);
        }
    } //-- void setXAQueueConnectionFactory(XAQueueConnectionFactory) 

    /**
     * Method setXATopicConnectionFactory
     * 
     * @param index
     * @param vXATopicConnectionFactory
     */
    public void setXATopicConnectionFactory(int index, XATopicConnectionFactory vXATopicConnectionFactory)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _XATopicConnectionFactoryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _XATopicConnectionFactoryList.set(index, vXATopicConnectionFactory);
    } //-- void setXATopicConnectionFactory(int, XATopicConnectionFactory) 

    /**
     * Method setXATopicConnectionFactory
     * 
     * @param XATopicConnectionFactoryArray
     */
    public void setXATopicConnectionFactory(XATopicConnectionFactory[] XATopicConnectionFactoryArray)
    {
        //-- copy array
        _XATopicConnectionFactoryList.clear();
        for (int i = 0; i < XATopicConnectionFactoryArray.length; i++) {
            _XATopicConnectionFactoryList.add(XATopicConnectionFactoryArray[i]);
        }
    } //-- void setXATopicConnectionFactory(XATopicConnectionFactory) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.ConnectionFactories unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.ConnectionFactories) Unmarshaller.unmarshal(org.exolab.jms.config.ConnectionFactories.class, reader);
    } //-- org.exolab.jms.config.ConnectionFactories unmarshal(java.io.Reader) 

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
