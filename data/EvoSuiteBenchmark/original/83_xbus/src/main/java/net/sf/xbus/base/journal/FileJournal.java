package net.sf.xbus.base.journal;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;

/**
 * <code>FileJournal</code> writes information about the activities of the
 * middleware into a file.
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
 * <td>FileJournal</td>
 * <td>Filename</td>
 * <td>Filename of the Journal. It is located under %XBUS_HOME%/log.</td>
 * </tr>
 * </table>
 */
public class FileJournal implements JournalInterface
{
	private static final String DATEFORMAT = "yyyyMMdd";

	/**
	 * Writes a middleware-activity into a file. An activity can be:
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
		try
		{
			String date = new SimpleDateFormat(DATEFORMAT).format(new Date());

			StringBuffer mFilename = new StringBuffer().append(
					Constants.XBUS_HOME).append(Constants.FILE_SEPERATOR)
					.append("log").append(Constants.FILE_SEPERATOR).append(
							"journal").append("_").append(date).append(".log");

			PrintWriter printer = new PrintWriter(new FileWriter(mFilename
					.toString(), true));
			printer.println(format(type, system, message));
			printer.close();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE, Constants.PACKAGE_BASE_JOURNAL, "0",
					e);
		}
	}

	/**
	 * Committing files is not necessary.
	 */
	public void commit()
	{
		/*
		 * do nothing
		 */
	}

	/**
	 * @param type <b>R</b>: message has been received, <b>S</b>: message has
	 *            been send
	 * @param system The name of the system, from which the message has been
	 *            received or where the message has been sent to.
	 * @param message The {@link Message}-object that represents the data of
	 *            the message.
	 * 
	 */
	private String format(char type, XBUSSystem system, Message message)
			throws XException
	{
		String requestMessage = Journal.formatText(message, system, true);
		requestMessage = Journal.removeLinefeeds(requestMessage);
		String responseMessage = Journal.formatText(message, system, false);
		responseMessage = Journal.removeLinefeeds(responseMessage);

		String requestTimestamp = Journal.formatDate(message
				.getRequestTimestamp());
		String responseTimestamp = Journal.formatDate(message
				.getResponseTimestamp());

		StringBuffer buf = new StringBuffer();

		buf.append(type);
		buf.append(" | ");
		buf.append(system.getCompleteName());
		buf.append(" | ");
		buf.append(message.getFunction());
		buf.append(" | ");
		buf.append(message.getId());
		buf.append(" | ");
		buf.append(message.getReturncode());
		buf.append(" | ");
		buf.append(requestTimestamp);
		buf.append(" | ");
		buf.append(requestMessage);
		buf.append(" | ");
		buf.append(responseTimestamp);
		buf.append(" | ");
		buf.append(responseMessage);
		buf.append(" | ");
		buf.append(message.getErrorcode());
		buf.append(" | ");
		buf.append(message.getErrortext());

		return new String(buf);
	}
}
