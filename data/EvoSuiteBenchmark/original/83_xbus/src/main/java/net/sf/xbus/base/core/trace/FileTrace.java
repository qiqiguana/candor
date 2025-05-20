package net.sf.xbus.base.core.trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.config.Configuration;

/**
 * This implementation of <code>TraceTarget</code> writes the trace to a file.
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
 * <td>Base</td>
 * <td>FileTrace</td>
 * <td>Filename</td>
 * <td>Name of the file, where the trace is written.</td>
 * </tr>
 * </table>
 */
public class FileTrace implements TraceTarget
{
	private String mFilename;

	/**
	 * The constructor builds the name of the trace-file.
	 * <p>
	 * <code>%XBUS_HOME%/log/Name_from_Configuration</code>
	 * <p>
	 * <b>Remark:</b> Programs that use the <code>FileTrace</code> must be
	 * started with: <code>java -Dxbus.home="%XBUS_HOME%"</code>
	 */
	public FileTrace()
	{
		try
		{
			Configuration config = Configuration.getInstance();
			String filename = config.getValue("Base", "Trace", "Filename");

			mFilename = Constants.XBUS_HOME + Constants.FILE_SEPERATOR + "log"
					+ Constants.FILE_SEPERATOR + filename;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Writes the <code>message</code>, the <code>priority</code> and if
	 * given the backtrace of the <code>Throwable</code> to the file.
	 * <p>
	 * It uses the class {@link net.sf.xbus.base.core.trace.Formatter}.
	 * 
	 * @param priority the priority after which the messages should be traced
	 * @param message the message to be traced
	 * @param t the stack trace of the Throwable
	 */
	public void trace(int priority, Object message, Throwable t)
	{
		if (message == null)
		{
			message = "<null>";
		}
		try
		{
			PrintWriter printer = new PrintWriter(new FileWriter(mFilename,
					true));
			printer.println(Formatter.format(message.toString(), priority));
			if (t != null)
			{
				t.printStackTrace(printer);
			}
			printer.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Returns all trace messages as one large string.
	 * 
	 * @return String - all trace messages
	 */
	public String getTrace()
	{
		StringBuffer retString = new StringBuffer();
		String zeile;

		try
		{
			BufferedReader instream = new BufferedReader(new FileReader(
					new File(mFilename)));
			while ((zeile = instream.readLine()) != null)
			{
				retString.append(zeile);
				retString.append(Constants.LINE_SEPERATOR);
			}
			instream.close();

		}
		catch (IOException e)
		{
			return e.getMessage();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return e.getMessage();
		}
		return retString.toString();
	}
}
