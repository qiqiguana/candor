package net.sf.xbus.base.journal;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;

/**
 * <code>Journal</code> writes information about the activities of the
 * middleware.
 * <p>
 * 
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>Journal</td>
 * <td>Write</td>
 * <td><i>system.getName()</i>.Send</td>
 * <td>true, if sending data to this system shall be written.</td>
 * </tr>
 * <tr>
 * <td>Journal</td>
 * <td>Write</td>
 * <td><i>system.getName()</i>.Receive</td>
 * <td>true, if receiving data from this system shall be written.</td>
 * </tr>
 * <tr>
 * <td>Journal</td>
 * <td><i>Implementation</i></td>
 * <td><i>Class</i></td>
 * <td>Name of the Java-class that shall do the writing.</td>
 * </tr>
 * <tr>
 * <td>Journal</td>
 * <td><i>MessageLength</i></td>
 * <td><i>Bytes</i></td>
 * <td>Maximum number of bytes for request- and response-message.</td>
 * </tr>
 * </table>
 */
public class Journal
{
	static private boolean mInitialized = false;
	static private String mJournalClass = null;

	private JournalInterface mJournal;

	/**
	 * Writes a middleware-activity. An activity can be:
	 * <ul>
	 * <li>A message has been received and processed.</li>
	 * <li>A message has been send to a neighbor-system.</li>
	 * </ul>
	 * <p>
	 * The {@link net.sf.xbus.base.core.config.Configuration} stores, which
	 * activities are written. All other activities are ignored.
	 * <p>
	 * 
	 * @param type <b>R</b>: message has been received, <b>S</b>: message has
	 *            been send
	 * @param system The name of the system, from which the message has been
	 *            received or where the message has been sent to.
	 * @param message The {@link Message}-object that represents the data of
	 *            the message.
	 * 
	 */
	public void log(char type, XBUSSystem system, Message message)
			throws XException
	{
		if (!mInitialized)
		{
			Journal.initialize();
		}
		
		if (mJournal == null)
		{
			mJournal = (JournalInterface) ReflectionSupport
					.createObject(mJournalClass);
		}

		Configuration config = Configuration.getInstance();

		/* Find out, if this event has to be logged. */
		boolean doIt;
		if (type == 'S')
		{
			doIt = config.getValueAsBooleanOptional(Constants.CHAPTER_SYSTEM,
					system.getName(), "Journal.Send");
		}
		else if (type == 'R')
		{
			doIt = config.getValueAsBooleanOptional(Constants.CHAPTER_SYSTEM,
					system.getName(), "Journal.Receive");
		}
		else
		{
			List params = new Vector();
			params.add(String.valueOf(type));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE, Constants.PACKAGE_BASE_JOURNAL, "2",
					params);
		}
		if (doIt)
		{
			if (!mInitialized)
			{
				initialize();
			}
			mJournal.log(type, system, message);
		}
	}

	/**
	 * Commits the database.
	 */
	public void commit() throws XException
	{
		if (!mInitialized)
		{
			initialize();
		}
		mJournal.commit();
	}

	/**
	 * The given <code>Date</code> is converted to a string. The format of the
	 * string is specified in
	 * {@link net.sf.xbus.base.core.Constants#DATE_FORMAT}.
	 */
	static public String formatDate(java.util.Date in)
	{
		if (in == null)
		{
			return "<null>";
		}

		SimpleDateFormat formatter = Constants.getDateFormat();
		return formatter.format(in);
	}

	/**
	 * Shortens either the request-message or the response-message to a length
	 * specified in the {@link net.sf.xbus.base.core.config.Configuration}.
	 * 
	 * @param message The {@link Message}-object that contains the data of the
	 *            message.
	 * @param system The name of the system, from which the message has been
	 *            received or where the message has been sent to.
	 * @param request true: the request-message will be shortened, false: the
	 *            response-message will be shortened.
	 * @return the shortened message
	 */
	static public String formatText(Message message, XBUSSystem system,
			boolean request) throws XException
	{
		String retString = null;

		Configuration config = Configuration.getInstance();
		int length = config.getValueAsInt("Base", "Journal", "MessageLength");

		/*
		 * When the length should be 0, we must not format anything.
		 */
		if (length == 0)
		{
			return null;
		}

		boolean textMessageFound = false;
		boolean objectMessageFound = false;

		Class[] interfaces = message.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++)
		{
			if (interfaces[i].getName().equals(
					"net.sf.xbus.protocol.TextMessage"))
			{
				textMessageFound = true;
			}
			else if (interfaces[i].getName().equals(
					"net.sf.xbus.protocol.ObjectMessage"))
			{
				objectMessageFound = true;
			}
		}

		if (textMessageFound)
		{
			if (request)
			{
				retString = formatString(((TextMessage) message)
						.getRequestText(system), length);
			}
			else
			{
				retString = formatString(((TextMessage) message)
						.getResponseText(), length);
			}
		}
		else if (objectMessageFound)
		{
			if (request)
			{
				Object obj = ((ObjectMessage) message).getRequestObject(system);
				if (obj != null)
				{
					retString = formatString(obj.toString(), length);
				}
			}
			else
			{
				Object obj = ((ObjectMessage) message).getResponseObject();
				if (obj != null)
				{
					retString = formatString(obj.toString(), length);
				}
			}
		}
		return retString;
	}

	static public void initialize() throws XException
	{
		Configuration config = Configuration.getInstance();

		String journalClassShort = config.getValue("Base", "Journal",
				"Implementation");
		mJournalClass = Configuration.getClass("Journal", journalClassShort);

		mInitialized = true;
	}

	/**
	 * Shortens the input-string.
	 */
	static private String formatString(String in, int length)
	{
		if (in == null)
		{
			return "<null>";
		}

		String tmp;

		if ((length >= 0) && (in.length() > length))
		{
			tmp = in.substring(0, length);
		}
		else
		{
			tmp = in;
		}

		return tmp;
	}

	static public String removeLinefeeds(String text)
	{
		if (text != null)
		{
			text = text.replaceAll("\n", "");
			return text.replaceAll("\r", "");
		}
		else
		{
			return text;
		}
	}
}
