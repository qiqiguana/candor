package net.sf.xbus.base.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.strings.XStringSupport;
import net.sf.xbus.base.core.trace.Trace;

/**
 * A <code>Message</code> is selected from a properties file.
 * <p>
 * The message result is a String.<br>
 * The clear regulation of the message is reached by a key. The key is described
 * in * {@link net.sf.xbus.base.core.XException XException} documentation page.
 * </p>
 * 
 * @author Lars Messner
 */
public class MessageHandler
{

	private static Hashtable mInstances = new Hashtable();

	private static final Object classLock = Configuration.class;

	private Hashtable mMessages = null;

	/**
	 * On the basis of the key get the message of the properties file.
	 * 
	 * @param key contain char and/or int for the definitly designation of the
	 *            message
	 * @param params contain a list of the inspected parameters
	 * @return messageText contain the message from the properties file
	 */
	public String getMessage(String key, List params)
	{
		String messageText = null;
		messageText = getMessageOptional(key, params);

		if (messageText == null)
		{
			messageText = "Key: " + key + " not found in message file";
			Trace.error(messageText);
		} // if (messageText == null)

		return messageText;
	}

	/**
	 * On the basis of the key get the message of the properties file.
	 * 
	 * @param key contain char and/or int for the definitly designation of the
	 *            message
	 * @param params contain a list of the inspected parameters
	 * @return messageText contain the message from the properties file
	 */
	public String getMessageOptional(String key, List params)
	{

		String messageText = null;

		if (mMessages == null)
		{
			return null;
		} // if (mMessages == null)

		// gets the message of the key
		messageText = (String) mMessages.get(key);
		if (messageText == null)
		{
			return null;
		}

		int counter = 1;
		if (params != null)
		{
			String paramText = null;
			Object paramObject = null;
			// if there is a type of "$x$"
			for (Iterator it = params.iterator(); it.hasNext();)
			{
				paramObject = it.next();
				if (paramObject != null)
				{
					paramText = paramObject.toString();
				}
				else
				{
					paramText = "<null>";
				}
				String paramCounter = "$" + counter + "$";
				if (messageText.indexOf(paramCounter) >= 0)
				{
					messageText = XStringSupport.replaceAll(messageText,
							paramCounter, paramText);
					counter++;
				} // if (messageText.indexOf(paramCounter) >= 0)
			} // for (Iterator it = params.iterator(); it.hasNext();)
		} // if (params != null)
		return messageText;
	}

	/**
	 * Returns a named instance of the <code>Message</code>.
	 * <p>
	 * The first call creates a new <code>Message</code> and reads all
	 * entries. Following calls will return the object, that has been created by
	 * the first call.
	 * 
	 * @param basename the basename of the properties file.
	 * @return MessageHandler - instance of<code>MessageHandler</code>
	 */
	public static MessageHandler getInstance(String basename)
	{
		synchronized (classLock)
		{
			MessageHandler instance = (MessageHandler) mInstances.get(basename);
			if (instance == null)
			{
				instance = new MessageHandler(basename);
				mInstances.put(basename, instance);
			} // if (instance == null)
			return instance;
		}
	}

	/**
	 * The constructor is private. Instances of <code>Message</code> can only
	 * be accessed via the method <code>getInstance()</code>.
	 * 
	 * @param basename the name of the properties.
	 */
	private MessageHandler(String basename)
	{
		mMessages = new Hashtable();

		addMessages(basename, Constants.XBUS_ETC);
		addMessages(basename, Constants.XBUS_PLUGIN_ETC);
	}

	private void addMessages(String basename, String dir)
	{
		// the language for the properties file is english
		Locale locale = Locale.ENGLISH;
		String prefix = basename;
		String postfix = "_" + locale.toString() + ".properties";

		File dirFile = new File(dir);
		String[] messagesFiles = dirFile.list(new MessagesFilter(prefix,
				postfix));

		for (int i = 0; (messagesFiles != null) && (i < messagesFiles.length); i++)
		{
			FileInputStream instream;
			Properties newProps = new Properties();

			try
			{
				instream = new FileInputStream(dir + messagesFiles[i]);
				newProps.load(instream);
				instream.close();
			}
			catch (java.io.FileNotFoundException e)
			{
				System.out.println("Cannot find messagefile");
				System.exit(1);
			}
			catch (java.io.IOException e)
			{
				System.out.println("Cannot find messagefile");
				System.exit(1);
			}

			/*
			 * Move all elements of currently loaded properties to the complete
			 * set
			 */
			String key = null;
			for (Enumeration keys = newProps.keys(); keys.hasMoreElements();)
			{
				key = (String) keys.nextElement();

				mMessages.put(key, newProps.get(key));
			}
		}
	}

	/**
	 * The internal class <code>MessagesFilter</code> checks wether files are
	 * wanted Messages or not.
	 */
	static private class MessagesFilter implements FilenameFilter
	{
		private String mPrefix = null;
		private String mPostfix = null;

		public MessagesFilter(String prefix, String postfix)
		{
			mPrefix = prefix;
			mPostfix = postfix;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String filename)
		{
			if ((filename.startsWith(mPrefix)) && (filename.endsWith(mPostfix)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

	}
}
