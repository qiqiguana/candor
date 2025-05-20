package net.sf.xbus.base.core.trace;

import java.util.Date;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * The <code>Formatter</code> ensures, that every line in the trace-protocol
 * has an equal format.
 */
public class Formatter
{
	/**
	 * Formats a trace-line:
	 * <p>
	 * <code>   Timestamp | Priority | Threadname | Message</code>
	 * 
	 * @param message the message to be traced
	 * @param priority the priority after which the messages should be traced
	 * @return String - formatted trace-line
	 * @exception XException if any error occurs
	 */
	public static String format(String message, int priority) throws XException
	{
		String priorityname;
		String datetime = Constants.getDateFormat().format(new Date());

		switch (priority)
		{
			case 0 :
				priorityname = "always";
				break;
			case 1 :
				priorityname = "error ";
				break;
			case 2 :
				priorityname = "warn  ";
				break;
			case 3 :
				priorityname = "info  ";
				break;
			case 4 :
				priorityname = "debug ";
				break;
			default :
				priorityname = "NA    ";
		}

		Configuration config = Configuration.getInstance();
		int maxLength = config.getValueAsInt("Base", "Trace", "MaxLength");

		if ((maxLength >= 0) && (message.length() > maxLength))
		{
			message = message.substring(0, maxLength);
		}

		StringBuffer buf = new StringBuffer().append(datetime).append(" | ")
				.append(priorityname).append(" | ").append(
						Thread.currentThread().getName()).append(" | ").append(
						message);

		return buf.toString();

	}
}
