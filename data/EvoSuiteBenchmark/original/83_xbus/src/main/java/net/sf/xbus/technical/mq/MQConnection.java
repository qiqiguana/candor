package net.sf.xbus.technical.mq;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>MQConnection</code> manages connections and sessions for messagequeues.
 * <p>
 * 
 * It implements two Design-Patterns:
 * <ol>
 * <li><b>Singleton: </b> An instance of <code>MQConnection</code> is created
 * for every thread. This instance can be fetched with
 * <code>getInstance()</code>.</li>
 * <li><b>Facade: </b> The complexity of creating and managing a connection is
 * capsuled.</li>
 * </ol>
 */
public class MQConnection implements TAResource {
	private static Hashtable mMQConnections = new Hashtable();
	private static final Object classLock = MQConnection.class;

	private QueueConnectionFactory mQueueConnectionFactory = null;
	private Context jndiContext = null;

	private String mQueueSuffix = null;

	private QueueSession mSession = null;
	private QueueConnection mConnection = null;
	private Hashtable mReceivers = new Hashtable();
	private Hashtable mBrowsers = new Hashtable();
	private Hashtable mSenders = new Hashtable();
	private Hashtable mQueues = new Hashtable();
	private boolean mIsOpen = false;

	private XBUSSystem mDeleteQueue = null;
	private String mDeleteMessageId = null;

	/**
	 * The constructor is private, instances of <code>MQConnection</code> can
	 * only be generated via the method <code>getInstance()</code>. Each
	 * instance is put in a <code>Hashtable</code> with the name of the thread
	 * as the key.
	 */
	private MQConnection() throws XException {
		Configuration config = Configuration.getInstance();
		mQueueSuffix = config.getValueOptional("Connection", "MQ",
				"QueueSuffix");
		if (mQueueSuffix == null) {
			mQueueSuffix = "";
		}

		open();
		TAManager taManager = TAManager.getInstance();
		taManager.registerResource(this);

		mMQConnections.put(Thread.currentThread().getName(), this);
	}

	/**
	 * Opens the JMS-QueueSession and JMS-QueueConnection for sending and
	 * receiving messages.
	 */
	public void open() throws XException {
		if (!mIsOpen) {
			jndiContext = null;

			if (mQueueConnectionFactory == null) {
				Configuration config = Configuration.getInstance();
				mQueueConnectionFactory = (QueueConnectionFactory) jndiLookup(config
						.getValue("Connection", "MQ", "QueueConnectionFactory"));
			}

			try {
				mConnection = mQueueConnectionFactory.createQueueConnection();
				mSession = mConnection.createQueueSession(true,
						Session.AUTO_ACKNOWLEDGE);
				mConnection.start();
			} catch (JMSException e) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", e);
			}

			mIsOpen = true;
		}
	}

	/**
	 * Delivers an instance of <code>MQConnection</code>.
	 * <p>
	 * 
	 * If it is the first call for the actual thread, a new
	 * <code>MQConnection</code> -object gets created. Subsequent calls in this
	 * thread will deliver the object, that has been created by the first call.
	 */
	public static MQConnection getInstance() throws XException {
		synchronized (classLock) {
			MQConnection mqConnection = (MQConnection) mMQConnections
					.get(Thread.currentThread().getName());

			if (mqConnection == null) {
				mqConnection = new MQConnection();
			}

			return mqConnection;
		}
	}

	/**
	 * Commits all actions on the queues associated with this
	 * <code>MQConnection</code> (normally all queues of the current thread).
	 */
	public void commit() throws XException {
		try {
			mSession.commit();
		} catch (JMSException e) {
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		} finally {
			mDeleteMessageId = null;
			mDeleteQueue = null;
		}
	}

	/**
	 * Performs a rollback for all actions on the queues associated with this
	 * <code>MQConnection</code> (normally all queues of the current thread).
	 */
	public void rollback() throws XException {
		if (mIsOpen) {
			try {
				mSession.rollback();
				if (mDeleteQueue != null) {
					/*
					 * The first queue entry shall be deleted after writing it
					 * to the DeletedMessageStore, because the OnError
					 * resolution is Delete
					 */
					QueueReceiver receiver = getReceiver(mDeleteQueue);
					Message message = receiver.receiveNoWait();
					if (message.getJMSMessageID().equals(mDeleteMessageId)
							&& DeletedMessageStore.getInstance().writeMessage()) {
						mSession.commit();
					} else {
						mSession.rollback();
					}
				}
			} catch (JMSException e) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", e);
			} finally {
				mDeleteMessageId = null;
				mDeleteQueue = null;
			}
		}
	}

	/**
	 * Closes the connection.
	 */
	public void close() throws XException {
		try {
			QueueReceiver receiver;
			for (Enumeration e = mReceivers.elements(); e.hasMoreElements();) {
				receiver = (QueueReceiver) e.nextElement();
				receiver.close();
			}
			mReceivers.clear();

			QueueSender sender;
			for (Enumeration e = mSenders.elements(); e.hasMoreElements();) {
				sender = (QueueSender) e.nextElement();
				sender.close();
			}
			mSenders.clear();

			QueueBrowser browser;
			for (Enumeration e = mBrowsers.elements(); e.hasMoreElements();) {
				browser = (QueueBrowser) e.nextElement();
				browser.close();
			}
			mBrowsers.clear();

			mSession.close();
			mConnection.close();

			mIsOpen = false;
			jndiContext = null;
		} catch (JMSException e) {
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		}
	}

	/**
	 * Initializes the MQConnection.
	 */
	public void initialize() {
		mIsOpen = false;
		mReceivers.clear();
	}

	/**
	 * Creates and returns a JMS- <code>QueueSender</code> for the given queue.
	 */
	public QueueSender getSender(XBUSSystem system) throws XException {
		QueueSender sender = null;

		String pyhsQueuename = getPhysQueuename(system);
		Queue queue = getQueue(pyhsQueuename);

		try {
			if ((sender = (QueueSender) mSenders.get(pyhsQueuename)) == null) {
				sender = mSession.createSender(queue);
				sender.setDeliveryMode(DeliveryMode.PERSISTENT);
				mSenders.put(pyhsQueuename, sender);
			}
		} catch (JMSException e) {
			Trace
					.warn("Connection may be gone, trying MQConnection.getSender again ...");
			mIsOpen = false;
			open();
			try {
				if ((sender = (QueueSender) mSenders.get(pyhsQueuename)) == null) {
					sender = mSession.createSender(queue);
					sender.setDeliveryMode(DeliveryMode.PERSISTENT);
					mSenders.put(pyhsQueuename, sender);
				}
			} catch (JMSException exc) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", exc);
			}
		}
		return sender;
	}

	/**
	 * Creates and returns a JMS- <code>QueueReceiver</code> for the given
	 * queue.
	 * 
	 * @param system
	 *            name of an interface
	 * 
	 * @return JMS QueueReceiver
	 */
	public QueueReceiver getReceiver(XBUSSystem system) throws XException {
		QueueReceiver receiver = null;

		String pyhsQueuename = getPhysQueuename(system);
		Queue queue = getQueue(pyhsQueuename);
		try {
			if ((receiver = (QueueReceiver) mReceivers.get(pyhsQueuename)) == null) {
				receiver = mSession.createReceiver(queue);
				mReceivers.put(pyhsQueuename, receiver);
			}
		} catch (JMSException e) {
			Trace
					.warn("Connection may be gone, trying MQConnection.getReceiver again ...");
			mIsOpen = false;
			open();
			try {
				if ((receiver = (QueueReceiver) mReceivers.get(pyhsQueuename)) == null) {
					receiver = mSession.createReceiver(queue);
					mReceivers.put(pyhsQueuename, receiver);
				}
			} catch (JMSException exc) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", exc);
			}
		}
		return receiver;
	}

	/**
	 * Creates and returns a JMS- <code>QueueBrowser</code> for the given queue.
	 */
	public QueueBrowser getBrowser(XBUSSystem system) throws XException {
		QueueBrowser browser = null;

		String pyhsQueuename = getPhysQueuename(system);
		Queue queue = getQueue(pyhsQueuename);
		try {
			if ((browser = (QueueBrowser) mBrowsers.get(pyhsQueuename)) == null) {
				browser = mSession.createBrowser(queue);
				mBrowsers.put(pyhsQueuename, browser);
			}
		} catch (JMSException e) {
			Trace
					.warn("Connection may be gone, trying MQConnection.getBrowser again ...");
			mIsOpen = false;
			open();
			try {
				if ((browser = (QueueBrowser) mBrowsers.get(pyhsQueuename)) == null) {
					browser = mSession.createBrowser(queue);
					mBrowsers.put(pyhsQueuename, browser);
				}
			} catch (JMSException exc) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", exc);
			}
		}
		return browser;
	}

	/**
	 * Creates and returns a JMS- <code>TextMessage</code>.
	 */
	public TextMessage createTextMessage() throws XException {
		TextMessage message = null;

		try {
			message = mSession.createTextMessage();
			message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		}
		return message;
	}

	protected void setDeleteInformation(XBUSSystem source, String messageId) {
		mDeleteQueue = source;
		mDeleteMessageId = messageId;
	}

	static public void clear() {
		mMQConnections.clear();
	}

	/**
	 * Creates a JNDI InitialContext object if none exists yet. Then looks up
	 * the string argument and returns the associated object.
	 * 
	 * @param name
	 *            the name of the object to be looked up
	 * 
	 * @return the object bound to <code>name</code>
	 * @throws net.sf.xbus.base.core.XException
	 */
	private Object jndiLookup(String name) throws XException {
		Object obj = null;

		if (jndiContext == null) {
			jndiContext = createContext();
		}

		try {
			obj = jndiContext.lookup(name);
		} catch (NamingException e) {
			Trace
					.warn("Connection maybe gone, trying MQConnection.jndiContext again...");
			try {
				jndiContext = createContext();
				obj = jndiContext.lookup(name);
			} catch (NamingException exc) {
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MQ, "0", e);
			}
		}
		return obj;
	}

	/**
	 * Reads the name of the message queue from the configuration.
	 * 
	 * @param system
	 *            the name of the sender/receiver
	 * 
	 * @return the name of the message queue
	 */
	public String getPhysQueuename(XBUSSystem system) throws XException {
		Configuration config = Configuration.getInstance();
		String physQueuename = config.getValue(Constants.CHAPTER_SYSTEM, system
				.getName(), "Queuename");

		physQueuename = system.replaceAllMarkers(physQueuename)[0];
		return new StringBuffer(physQueuename).append(mQueueSuffix).toString();
	}

	private Context createContext() throws XException {
		try {
			Hashtable env = new Hashtable();

			Configuration config = Configuration.getInstance();
			String factory = config.getValue("Connection", "MQ",
					"ContextFactory");
			env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
			String url = config.getValueOptional("Connection", "MQ", "URL");
			if (url != null) {
				env.put(Context.PROVIDER_URL, url);
			}
			String user = config.getValueOptional("Connection", "MQ", "User");
			if (user != null) {
				env.put(Context.SECURITY_PRINCIPAL, user);
			}
			String password = config.getValueOptional("Connection", "MQ",
					"Password");
			if (password != null) {
				env.put(Context.SECURITY_CREDENTIALS, password);
			}
			String referral = config.getValueOptional("Connection", "MQ",
					"Referral");
			if (referral != null) {
				env.put(Context.REFERRAL, referral);
			}
			String authorative = config.getValueOptional("Connection", "MQ",
					"Authorative");
			if (authorative != null) {
				env.put(Context.AUTHORITATIVE, authorative);
			}
			String batchSize = config.getValueOptional("Connection", "MQ",
					"BatchSize");
			if (batchSize != null) {
				env.put(Context.BATCHSIZE, batchSize);
			}
			String language = config.getValueOptional("Connection", "MQ",
					"Language");
			if (language != null) {
				env.put(Context.LANGUAGE, language);
			}
			String dnsUrl = config.getValueOptional("Connection", "MQ",
					"DNS_URL");
			if (dnsUrl != null) {
				env.put(Context.DNS_URL, dnsUrl);
			}
			String objectFactories = config.getValueOptional("Connection",
					"MQ", "ObjectFactories");
			if (objectFactories != null) {
				env.put(Context.OBJECT_FACTORIES, objectFactories);
			}
			String securityProtocol = config.getValueOptional("Connection",
					"MQ", "SecurityProtocol");
			if (securityProtocol != null) {
				env.put(Context.SECURITY_PROTOCOL, securityProtocol);
			}
			String securityAuthentication = config.getValueOptional(
					"Connection", "MQ", "SecurityAuthentication");
			if (securityAuthentication != null) {
				env
						.put(Context.SECURITY_AUTHENTICATION,
								securityAuthentication);
			}
			String stateFactories = config.getValueOptional("Connection", "MQ",
					"StateFactories");
			if (stateFactories != null) {
				env.put(Context.STATE_FACTORIES, stateFactories);
			}
			String urlPkgPrefixes = config.getValueOptional("Connection", "MQ",
					"URLPkgPrefixes");
			if (urlPkgPrefixes != null) {
				env.put(Context.URL_PKG_PREFIXES, urlPkgPrefixes);
			}
			String port = config.getValueOptional("Connection", "MQ", "Port");
			if (port != null) {
				env.put("javax.naming.factory.port", port);
			}
			String host = config.getValueOptional("Connection", "MQ", "Host");
			if (host != null) {
				env.put("javax.naming.factory.host", host);
			}
			return new InitialContext(env);
		} catch (NamingException e) {
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		}
	}

	private Queue getQueue(String physQueuename) throws XException {
		Queue queue = (Queue) mQueues.get(physQueuename);
		if (queue == null) {
			try {
				if (Configuration.getInstance().getValue("Connection", "MQ",
						"ContextFactory").contains("activemq")) {
					/*
					 * Workaround for ActiveMQ dynamic queues
					 */
					queue = (Queue) jndiLookup("dynamicQueues/" + physQueuename);
				} else {
					queue = (Queue) jndiLookup(physQueuename);
				}
			} catch (XException e1) {
				try {
					Trace.error("Creating queue " + physQueuename);
					queue = mSession.createQueue(physQueuename);
					mQueues.put(physQueuename, queue);
				} catch (JMSException e2) {
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_MQ, "0", e2);
				}
			}
		}
		return queue;
	}
}
