package net.sf.xbus.base.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingException;

import net.sf.xbus.base.core.trace.Trace;

import org.xml.sax.SAXParseException;

/**
 * All errors that occur while processing a request are handled by throwing a
 * <code>XException</code>. Because nearly all methods possibly throw a
 * <code>XException</code>, only a few points (eg. the receivers) have to
 * catch and handle it.
 */
public class XException extends Exception
{
	private static Hashtable mExceptionInformation = new Hashtable();
	private String mMessageText = null;

	/**
	 * Constructs a <code>XException</code> and traces the
	 * <code>message</code> and the backtrace.
	 * 
	 * @param location indicates wether this is an internal or an external
	 *            problem
	 * @param layer indicates the layer where the error has occurred
	 * @param Package indicates the package where the error has occurred
	 * @param number to identify the error
	 */
	public XException(String location, String layer, String Package,
			String number)
	{
		traceException(location, layer, Package, number, null);
	}

	/**
	 * Constructs a <code>XException</code> and traces the
	 * <code>message</code> and the backtrace.
	 * 
	 * @param location indicates wether this is an internal or an external
	 *            problem
	 * @param layer indicates the layer where the error has occurred
	 * @param Package indicates the package where the error has occurred
	 * @param number to identify the error
	 * @param params a list of values to be included in the message
	 */
	public XException(String location, String layer, String Package,
			String number, List params)
	{
		traceException(location, layer, Package, number, params);
	}

	/**
	 * Constructs a <code>XException</code> and traces the
	 * <code>Throwable</code> and the backtrace.
	 * 
	 * @param location indicates wether this is an internal or an external
	 *            problem
	 * @param layer indicates the layer where the error has occurred
	 * @param Package indicates the package where the error has occurred
	 * @param number to identify the error
	 * @param t contains the thrown Exception.
	 * @param params list of parameters to be inclueded in the message
	 */
	public XException(String location, String layer, String Package,
			String number, Throwable t, List params)
	{
		super(t.getMessage());

		traceException(location, layer, Package, number, t, params);
	}

	/**
	 * Constructs a <code>XException</code> and traces the
	 * <code>Throwable</code> and the backtrace.
	 * 
	 * @param location indicates wether this is an internal or an external
	 *            problem
	 * @param layer indicates the layer where the error has occurred
	 * @param Package indicates the package where the error has occurred
	 * @param number to identify the error
	 * @param t contains the thrown Exception.
	 */
	public XException(String location, String layer, String Package,
			String number, Throwable t)
	{
		super(t.getMessage());

		traceException(location, layer, Package, number, t, null);
	}

	/**
	 * @return the message string of this instance (which may be null).
	 */
	public String getMessage()
	{
		return mMessageText;
	}

	/**
	 * @return a more detailed information about the last thrown XException.
	 */
	public static String getExceptionInformation()
	{
		return (String) mExceptionInformation.get(Thread.currentThread()
				.getName());
	}

	/**
	 * Sets the information for the current XException
	 * 
	 * @param message the message of this XException
	 * @param t the thrown Exception.
	 */
	public static void setExceptionInformation(String message, Throwable t)
	{
		if (t == null)
		{
			mExceptionInformation
					.put(Thread.currentThread().getName(), message);
		} // if (t == null)
		else
		{
			mExceptionInformation.put(Thread.currentThread().getName(),
					new StringBuffer().append(message).append(
							Constants.LINE_SEPERATOR).append(
							Constants.LINE_SEPERATOR).append(
							getStackTraceAsString(t)).toString());
		}
	}

	/**
	 * Removes information about previous exceptions.
	 */
	public static void clearExceptionInformation()
	{
		mExceptionInformation = new Hashtable();
	}

	/**
	 * Returns the stacktrace of the given <code>Throwable</code> as a string.
	 * 
	 * @param t the thrown Exception.
	 */
	private static String getStackTraceAsString(Throwable t)
	{
		String retString = null;

		try
		{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outStream);
			t.printStackTrace(printStream);

			retString = outStream.toString();
			printStream.close();
			outStream.close();
		}
		catch (IOException e)
		{
			/*
			 * do nothing
			 */
		}

		return retString;
	}

	private void traceException(String Location, String Layer, String Package,
			String Number, List params)
	{
		String basename = "errors";
		String key = Location + "_" + Layer + "_" + Package + "_" + Number;

		MessageHandler msg = MessageHandler.getInstance(basename);
		String messageText = msg.getMessage(key, params);

		if (Trace.isInitialized())
		{
			Trace.error("Exception caught: " + key + " " + messageText, this);
		} // if (Trace.isInitialized())
		else
		{
			System.out.println("Exception caught: " + key + " " + messageText);
			printStackTrace();
		}

		messageText = key + " " + messageText;
		setExceptionInformation(messageText, this);
		mMessageText = messageText;
	}

	private void traceException(String Location, String Layer, String Package,
			String Number, Throwable t, List params)
	{
		/*
		 * When the exception is an XException, it has been traced before
		 */
		if (!this.getClass().equals(t.getClass()))
		{
			String basename = "errors";
			String key = Location + "_" + Layer + "_" + Package + "_" + Number;

			MessageHandler msg = MessageHandler.getInstance(basename);
			String messageText = msg.getMessageOptional(key, params);

			if (messageText == null)
			{
				messageText = t.getMessage();
				if ((messageText == null) && (t.getCause() != null))
				{
					/*
					 * When the Exception has no message, maybe its cause has
					 * one
					 */
					messageText = t.getCause().getMessage();
				}
				if (messageText == null)
				{
					/*
					 * If messageText is still null, the name of the Throwable
					 * class is used
					 */
					messageText = t.getClass().getName();
				}
			} // if (messageText == null)

			if (Trace.isInitialized())
			{
				Trace.error("Exception caught: " + key + " " + messageText, t);
				traceAdditionalInfo(t);
			} // if (Trace.isInitialized())
			else
			{
				System.out.println("Exception caught: " + key + " "
						+ messageText);
				t.printStackTrace();
			}

			messageText = key + " " + messageText;
			setExceptionInformation(messageText, t);
			mMessageText = messageText;
		} // if (!this.getClass().equals(t.getClass()))
		else
		{
			mMessageText = t.getMessage();
		}
	}

	private void traceAdditionalInfo(Throwable t)
	{
		if (t instanceof NamingException)
		{
			Trace.error("Resolved Name	 : "
					+ ((NamingException) t).getResolvedName());
			Trace.error("Resolved Object : "
					+ ((NamingException) t).getResolvedObj());
			Trace.error("Remaining Name  : "
					+ ((NamingException) t).getRemainingName());
			Trace.error("Explanation : "
					+ ((NamingException) t).getExplanation());
		}
		else if (t instanceof SQLException)
		{
			Trace.error("SQLState : " + ((SQLException) t).getSQLState());
			Trace.error("Errorcode: " + ((SQLException) t).getErrorCode());
		}
		else if (t instanceof SAXParseException)
		{
			Trace.error("Parsing error occurred at line "
					+ ((SAXParseException) t).getLineNumber() + ", column "
					+ ((SAXParseException) t).getColumnNumber());
		}
	}
}
