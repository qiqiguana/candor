package net.sf.xbus.base.deletedMessageStore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XDomSupport;
import net.sf.xbus.base.xml.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>DeletedMessageStore</code> manages the saving of messages before they
 * are deleted in case of an error.
 * <p>
 * It implements a <b>Singleton </b> pattern, where there is one instance per
 * thread.
 */
public class DeletedMessageStore
{
	private static final String SECTION = "DeletedMessageStore";

	private static Hashtable mDeletedMessageStores = new Hashtable();

	private static final Object classLock = DeletedMessageStore.class;

	private Object mMessage = null;

	private XBUSSystem mSystem = null;

	private DeletedMessageStore()
	{
		mDeletedMessageStores.put(Thread.currentThread().getName(), this);
	}

	/**
	 * Returns an instance of the <code>DeletedMessageStore</code>. A new
	 * instance will be created if there is none existing for this thread.
	 * 
	 * @return an instance of the <code>DeletedMessageStore</code>
	 */
	public static DeletedMessageStore getInstance()
	{
		synchronized (classLock)
		{
			DeletedMessageStore dmStore = (DeletedMessageStore) mDeletedMessageStores
					.get(Thread.currentThread().getName());

			if (dmStore == null)
			{
				dmStore = new DeletedMessageStore();
			}
			return dmStore;
		}
	}

	/**
	 * Set the message that shall be saved. The XML file will contain the
	 * <code>String</code> representation of this object.
	 * 
	 * @param message
	 *            the message to be saved
	 */
	public void setMessage(Object message)
	{
		mMessage = message;
	}

	/**
	 * Sets the system that has received the message.
	 * 
	 * @param system
	 *            the receiving message
	 */
	public void setSystem(XBUSSystem system)
	{
		mSystem = system;
	}

	/**
	 * Writes the name of the system, its additional addresses and the message
	 * into a XML file. The name of the directory is read out of the
	 * configuration. The filename is
	 * <code><i>system.getCompleteName()</i>.xml.<i>timestamp</i></code>.
	 * 
	 * @return true if the writing has been successful, false if there has been
	 *         a problem
	 */
	public boolean writeMessage()
	{
		try
		{
			if (getEnabled(mSystem))
			{
				writeFile(buildXML(mMessage, mSystem), mSystem, getDirectory());
			}
			return true;
		}
		catch (XException e)
		{
			Trace.error("Problem while writing to DeletedMessageStore");
			return false;
		}
	}

	/**
	 * Reads the name of all files containing deleted messages.
	 * 
	 * @return an array with the name of all files containing deleted messages
	 * @throws XException
	 *             if any error occures
	 */
	public static String[] getDeletedMessageFilenames() throws XException
	{
		File[] files = new File(getDirectory()).listFiles();

		String[] retArray = new String[files.length];
		for (int i = 0; i < retArray.length; i++)
		{
			retArray[i] = files[i].getName();
		}
		return retArray;
	}

	/**
	 * Reads a deleted message and makes a new attempt to send it to its
	 * destinations.
	 * 
	 * @param filename
	 *            the name of a file containing a deleted message
	 * @return a message of success or failure
	 */
	public static String resendDeletedMessage(String filename)
	{
		StringBuffer retString = new StringBuffer();
		TAManager taManager = null;

		Trace.info("Resending message from " + filename);

		/*
		 * Reading and parsing the deleted message
		 */
		try
		{
			Document messageDom = XMLHelper.parseXML(readFile(getDirectory()
					+ Constants.FILE_SEPERATOR + filename), null, null);
			XBUSSystem source = new XBUSSystem(messageDom.getFirstChild()
					.getNodeName());
			NodeList addresses = messageDom.getElementsByTagName("Address");
			Node address = null;
			String attrName = null;
			String addressValue = null;
			for (int i = 0; i < addresses.getLength(); i++)
			{
				address = addresses.item(i);
				if ((attrName = XMLHelper.getAttribute(address, "name")) == null)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_BASE,
							Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "1");
				}
				if ((addressValue = XMLHelper.getNodeText(address)) == null)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_BASE,
							Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "2");
				}
				source.setAddress(attrName, addressValue);
			}

			NodeList data = messageDom.getElementsByTagName("Data");
			String request = null;
			if (data.getLength() > 0)
			{
				Node messageNode = messageDom.getElementsByTagName("Data")
						.item(0);
				Node dataNode = null;
				if ((dataNode = messageNode.getFirstChild()) != null)
				{
					request = dataNode.getNodeValue();
				}
			}
			if (request == null)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_BASE,
						Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "3");
			}

			/*
			 * Resending the message
			 */
			taManager = TAManager.getInstance();
			taManager.clearManager();
			taManager.begin();

			Adapter adapter = new Adapter();
			adapter.callApplication(source, request, Constants.TYPE_TEXT);
			Object responseObject = adapter.getResponse();
			if (Constants.RC_OK.equals(adapter.getReturncode()))
			{
				taManager.commit();
				PostProcessor.start(source, responseObject,
						Constants.POSTPROCESSING_PERSYSTEM);
				Trace.info("End resending " + source.getCompleteName());
				Trace.info("-----------------------------");
				retString.append("Message from ").append(
						source.getCompleteName()).append(
						" successfully resent!");

				File messageFile = new File(getDirectory()
						+ Constants.FILE_SEPERATOR + filename);
				if (messageFile.delete())
				{
					retString.append("<br>");
					retString.append("File ").append(filename).append(
							" has been deleted.");
				}
				else
				{
					retString.append("<br />");
					retString.append("File ").append(filename).append(
							" cannot be deleted.");
				}
			}
			else
			{
				taManager.rollback();
				Trace.info("Error while resending " + source.getCompleteName());
				Trace.info("-----------------------------");
				retString.append("Error while resending ").append(
						source.getCompleteName()).append("<br>").append(
						"Reason: ").append(adapter.getErrormessage()).append(
						"<br>").append("File ").append(filename).append(
						" has not been deleted.");
			}
		}
		catch (Exception t)
		{
			Trace.info("Error while resending " + filename);
			Trace.info("-----------------------------");
			retString.append("Error while resending ").append(filename).append(
					"<br>").append("Reason: ").append(t.getMessage()).append(
					"<br>").append("File ").append(filename).append(
					" has not been deleted.");
		}
		finally
		{
			if (taManager != null)
			{
				taManager.close();
			}
		}

		return retString.toString();

	}

	private static boolean getEnabled(XBUSSystem system) throws XException
	{
		Configuration config = Configuration.getInstance();
		return config.getValueAsBooleanOptional(Constants.CHAPTER_BASE,
				SECTION, "Enabled")
				|| config.getValueAsBooleanOptional(Constants.CHAPTER_SYSTEM,
						system.getName(), "UseDeletedMessageStore");
	}

	/**
	 * Reads the name of the directory of the deleted messages out of the
	 * configuration.
	 * 
	 * @return the name of the directory containing the deleted messages
	 * @throws XException
	 *             if any error occures
	 */
	public static String getDirectory() throws XException
	{
		return Configuration.getInstance().getValue(Constants.CHAPTER_BASE,
				SECTION, "Directory");
	}

	private void writeFile(String message, XBUSSystem system, String directory)
			throws XException
	{
		String fileName = new StringBuffer(directory).append(
				Constants.FILE_SEPERATOR).append(system.getCompleteName())
				.append(Constants.getDateAsString()).append(".xml").toString();

		BufferedWriter buffOut = null;
		try
		{
			buffOut = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), Constants.getXMLEncoding()));
			buffOut.write(message);
			buffOut.close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_BASE,
					Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "0", e);
		}
		finally
		{
			if (buffOut != null)
			{
				try
				{
					buffOut.close();
				}
				catch (IOException e1)
				{
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_BASE,
							Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "0", e1);
				}
			}
		}
	}

	private String buildXML(Object message, XBUSSystem system)
			throws XException
	{
		Document doc = XMLHelper.getDocumentBuilder("Default", null)
				.newDocument();

		Element root = doc.createElement(XDomSupport.makeTagNameValid(system
				.getName()));
		doc.appendChild(root);

		Hashtable addresses = system.getAddresses();
		if (!addresses.isEmpty())
		{
			Element addressesNode = doc.createElement("Addresses");
			String address = null;
			Element addressNode = null;
			for (Enumeration keys = addresses.keys(); keys.hasMoreElements();)
			{
				address = (String) keys.nextElement();
				addressNode = doc.createElement("Address");
				addressNode.setAttribute("name", address);
				addressNode.appendChild(doc.createTextNode((String) addresses
						.get(address)));
				addressesNode.appendChild(addressNode);
			}
			root.appendChild(addressesNode);
		}

		Element data = doc.createElement("Data");
		data.appendChild(doc.createCDATASection(message.toString()));
		root.appendChild(data);

		return XMLHelper.serializeXML(doc, null);
	}

	/**
	 * Reads a file.
	 * 
	 * @param filename
	 *            the name of the file including the directory
	 * @return the content of the file
	 */
	static private String readFile(String filename) throws XException
	{
		StringBuffer retBuffer = new StringBuffer();
		File sourceFile = new File(filename);
		String zeile;
		BufferedReader buffReader = null;

		try
		{
			buffReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(sourceFile)));

			if ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(zeile);
			} // if((zeile = buffReader.readLine()) != null)

			while ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(Constants.LINE_SEPERATOR);
				retBuffer.append(zeile);
			} // while ((zeile = buffReader.readLine()) != null)
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_BASE,
					Constants.PACKAGE_BASE_DELETEDMESSAGESTORE, "0", e);
		}
		finally
		{
			if (buffReader != null)
			{
				try
				{
					buffReader.close();
				}
				catch (IOException e1)
				{
					// do nothing
				}
			}
		}

		return retBuffer.toString();
	}

}
