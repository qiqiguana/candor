package net.sf.xbus.technical.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.ASCIITokenizer;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverThreadManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The class <code>HTTPReceiver</code> handles requests coming via a
 * HTTP-connection.
 * <p />
 * Depending on the <code>System_xxx_Receiver</code> entry in the
 * configuration, it acts as a normal <code>HTTPReceiver</code>, receiving
 * and returning strings, as a <code>HTTPStreamReceiver</code>, accepting an
 * <code>InputStream</code> as the request or as a
 * <code>HTTPParameterReceiver</code>, accepting a query string and returning
 * the result as a string.
 * <p />
 * The last part of the URL is used as the name of the system.
 */

public class HTTPReceiver implements Receiver
{
	private static final String HTTP_RECEIVER = "HTTPReceiver";

	private static final String HTTP_STREAM_RECEIVER = "HTTPStreamReceiver";

	private static final String HTTP_PARAMETER_RECEIVER = "HTTPParameterReceiver";

	private static Hashtable mAmountErrors = new Hashtable();

	private XBUSSystem mSource = null;

	private String mReceiverType = null;

	/**
	 * Handles the HTTP-request. In case of an error while processing the
	 * messages, it returns a
	 * <code>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</code>.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		try
		{
			Trace.info("Receiving data from " + req.getRequestURL().toString());

			/*
			 * Do some initialization in the beginning
			 */
			initialize(req);

			/*
			 * Check if the HTTPReceiver is stopped
			 */
			if (ReceiverThreadManager.getInstance().isHTTPReceiverStopped(
					mSource.getName()))
			{
				try
				{
					res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				}
				catch (IOException e1)
				{
					Trace.error(e1);
				}
				return;
			}

			/*
			 * Test if the connection must be encrypted.
			 */
			Configuration config = Configuration.getInstance();
			boolean forceSSL = config.getValueAsBooleanOptional(
					Constants.CHAPTER_SYSTEM, mSource.getName(), "ForceSSL");
			if ((!req.isSecure()) && (forceSSL))
			{
				Trace.error("System " + mSource.getName()
						+ " accepts only SSL connections");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				Trace.info("Error while processing "
						+ mSource.getCompleteName());
				Trace.info("----------------------------");
				return;
			}
		}
		catch (XException e)
		{
			handleError(res, e, null, e.getMessage());
			Trace.info("Error while processing " + mSource.getCompleteName());
			Trace.info("----------------------------");
			return;
		}

		/*
		 * Initialize transaction manager
		 */
		TAManager taManager = TAManager.getInstance();
		try
		{
			taManager.clearManager();
			taManager.begin();
		}
		catch (XException e)
		{
			handleError(res, e, null, e.getMessage());
			Trace.info("Error while processing " + mSource.getCompleteName());
			Trace.info("----------------------------");
			return;
		}

		/*
		 * Process the message, either an InputStream or a textual message
		 */
		try
		{
			if (HTTP_RECEIVER.equals(mReceiverType))
			{
				processTextMessage(req, res);
			}
			else if (HTTP_STREAM_RECEIVER.equals(mReceiverType))
			{
				processStreamMessage(req, res);
			}
			else if (HTTP_PARAMETER_RECEIVER.equals(mReceiverType))
			{
				processParameterMessage(req, res);
			}
			Trace.info("End processing " + mSource.getCompleteName());
			Trace.info("----------------------------");
		}
		catch (Exception e)
		{
			handleError(res, e, null, e.getMessage());
			Trace.info("Error while processing " + mSource.getCompleteName());
			Trace.info("----------------------------");
		}
		catch (Error e)
		{
			handleError(res, e, null, e.getMessage());
			Trace.info("Error while processing " + mSource.getCompleteName());
			Trace.info("----------------------------");

			/*
			 * Errors must be passed through, the servlet engine has to process
			 * them too.
			 */
			throw e;
		}

		taManager.close();
	}

	private void initialize(HttpServletRequest req) throws XException
	{
		Configuration config = Configuration.getInstance();

		/*
		 * Get the name of the source from the last part of the URL.
		 */
		String servletName = null;
		ASCIITokenizer tokenizer = new ASCIITokenizer(req.getRequestURL()
				.toString(), "/");
		while (tokenizer.hasMoreTokens())
		{
			servletName = tokenizer.nextToken();
		}
		mSource = new XBUSSystem(servletName);

		/*
		 * Check, it this is a normal HTTPReceiver or a HTTPLineReaderReceiver
		 */
		String receiverType = config.getValue(Constants.CHAPTER_SYSTEM, mSource
				.getName(), "Receiver");

		if ((HTTP_RECEIVER.equals(receiverType))
				|| (HTTP_STREAM_RECEIVER.equals(receiverType))
				|| (HTTP_PARAMETER_RECEIVER.equals(receiverType)))
		{
			mReceiverType = receiverType;
		}
		else
		{
			Vector params = new Vector(1);
			params.add(receiverType);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "2", params);
		}
	}

	/**
	 * Processes textual messages and give back a response.
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @throws XException
	 *             if an error occurs
	 */
	private void processTextMessage(HttpServletRequest req,
			HttpServletResponse res) throws XException
	{
		// Ohne die beiden nächsten Abfragen hat
		// Tomcat 3.3a und Tomcat 4.0.2 ein Problem
		// mit dem nachfolgenden Reader.
		String contentType = req.getContentType();
		int contentLength = req.getContentLength();

		StringBuffer textString = null;
		if (contentLength > 0)
		{
			textString = new StringBuffer(contentLength);
		}
		else
		{
			textString = new StringBuffer();
		}
		String line;

		try
		{
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
			{
				textString.append(line);
				textString.append(Constants.LINE_SEPERATOR);
			}
			String requestText = textString.toString();

			Adapter adapter = new Adapter();
			adapter.callApplication(mSource, requestText, getType());
			String responseText = (String) adapter.getResponse();

			if (Constants.RC_OK.equals(adapter.getReturncode()))
			{
				TAManager.getInstance().commit();

				initializeAmountErrors();

				PostProcessor.start(mSource, responseText,
						Constants.POSTPROCESSING_PERSYSTEM);

				res.setStatus(HttpServletResponse.SC_OK);
				if ((responseText != null) && (responseText.length() > 0))
				{
					res.setContentType(contentType);
					PrintWriter writer = res.getWriter();
					writer.print(responseText);
					writer.close();
				}
			}
			else
			{
				TAManager.getInstance().rollback();
				handleError(res, null, requestText, adapter.getErrormessage());
			}
		}
		catch (Exception e)
		{
			if (e instanceof XException)
			{
				throw (XException) e;
			}
			else
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
			}
		}
	}

	/**
	 * Processes an <code>InputStream</code>, no response is given back.
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @throws XException
	 *             if an error occurs
	 */
	private void processStreamMessage(HttpServletRequest req,
			HttpServletResponse res) throws XException
	{
		// Ohne die beiden nächsten Abfragen hat
		// Tomcat 3.3a und Tomcat 4.0.2 ein Problem
		// mit dem nachfolgenden Reader.
		// String contentType = req.getContentType();
		// int contentLength = req.getContentLength();

		InputStream inStream = null;
		try
		{
			inStream = new BufferedInputStream(req.getInputStream());
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
		}
		Adapter adapter = new Adapter();
		adapter.callApplication(mSource, inStream, getType());

		if (Constants.RC_OK.equals(adapter.getReturncode()))
		{
			TAManager.getInstance().commit();

			initializeAmountErrors();

			PostProcessor.start(mSource, adapter.getResponse(),
					Constants.POSTPROCESSING_PERSYSTEM);

			res.setStatus(HttpServletResponse.SC_OK);
		}
		else
		{
			TAManager.getInstance().rollback();
			handleError(res, null, null, adapter.getErrormessage());
		}
	}

	/**
	 * Processes parameter messages and gives back a response.
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @throws XException
	 *             if an error occurs
	 */
	private void processParameterMessage(HttpServletRequest req,
			HttpServletResponse res) throws XException
	{
		try
		{
			String contentType = req.getContentType();

			Map parameters = req.getParameterMap();
			Document requestDoc = XMLHelper.getDocumentBuilder("Default",
					mSource.getName()).newDocument();

			if ((parameters != null) && (!parameters.isEmpty()))
			{
				/*
				 * Patch sent by Raffael Niedermüller to accept an URLParameter
				 * named WILDCARD as $WILDCARD$ in configuration
				 */
				Object obj = parameters.get(XBUSSystem.FILENAME_WILDCARD_XML);
				if (obj == null)
				{
					obj = parameters.get(XBUSSystem.FILENAME_WILDCARD);
				}
				if (obj != null)
				{
					String[] values = (String[]) obj;
					mSource.setAddress(XBUSSystem.FILENAME_WILDCARD, values[0]);
				}

				Element rootNode = requestDoc.createElement(mSource.getName());
				requestDoc.appendChild(rootNode);

				SortedSet paramSet = new TreeSet(parameters.keySet());
				String key = null;
				Node tmpNode = null;
				String[] values = null;
				for (Iterator it = paramSet.iterator(); it.hasNext();)
				{
					key = (String) it.next();
					values = (String[]) parameters.get(key);
					if ((XBUSSystem.FILENAME_WILDCARD.equals(key))
							|| (XBUSSystem.FILENAME_WILDCARD_XML.equals(key)))
					{
						rootNode.setAttribute(XBUSSystem.FILENAME_WILDCARD_XML,
								values[0]);
					}
					else
					{
						values = (String[]) parameters.get(key);
						for (int i = 0; i < values.length; i++)
						{
							tmpNode = requestDoc.createElement(key);
							tmpNode.appendChild(requestDoc
									.createTextNode(values[i]));
							rootNode.appendChild(tmpNode);
						}
					}
				}
			}

			Adapter adapter = new Adapter();
			adapter.callApplication(mSource, requestDoc, getType());

			Object response = adapter.getResponse();
			String responseText = null;
			if (response != null)
			{
				responseText = XMLHelper
						.serializeXML((Document) response, null);
			}

			if (Constants.RC_OK.equals(adapter.getReturncode()))
			{
				TAManager.getInstance().commit();

				initializeAmountErrors();

				PostProcessor.start(mSource, null,
						Constants.POSTPROCESSING_PERSYSTEM);

				res.setStatus(HttpServletResponse.SC_OK);
				if ((responseText != null) && (responseText.length() > 0))
				{
					res.setContentType(contentType);
					PrintWriter writer = res.getWriter();
					writer.print(responseText);
					writer.close();
				}
			}
			else
			{
				TAManager.getInstance().rollback();
				handleError(res, null, requestDoc, adapter.getErrormessage());
			}
		}
		catch (Exception e)
		{
			if (e instanceof XException)
			{
				throw (XException) e;
			}
			else
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
			}
		}
	}

	private void handleError(HttpServletResponse res, Throwable t,
			Object request, Object responseText)
	{
		incrementAmountErrors();
		String message = null;
		if (t != null)
		{
			message = t.getMessage();
		}
		NotifyError.notifyError(this, mSource, message, request, null);
		Trace.error("Error caught while processing doPost");
		// res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		try
		{
			if (responseText != null)
			{
				res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						responseText.toString());
			}
			else
			{
				res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		catch (IOException e)
		{
			Trace.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Receiver#getType()
	 */
	public String getType()
	{
		if ((HTTP_STREAM_RECEIVER.equals(mReceiverType))
				|| (HTTP_PARAMETER_RECEIVER.equals(mReceiverType)))
		{
			return Constants.TYPE_OBJECT;
		}
		else
		{
			return Constants.TYPE_TEXT;
		}
	}

	private void initializeAmountErrors()
	{
		mAmountErrors.remove(mSource.getName());
	}

	private void incrementAmountErrors()
	{
		Integer amountErrors = (Integer) mAmountErrors.get(mSource.getName());
		if (amountErrors == null)
		{
			amountErrors = new Integer(1);
		}
		else
		{
			amountErrors = new Integer(amountErrors.intValue() + 1);
		}
		mAmountErrors.put(mSource.getName(), amountErrors);

		checkStop();
	}

	private void checkStop()
	{
		int stopAfterErrors = ReceiverThreadManager.getStopAfterErrors(mSource
				.getName(), "HTTPReceiver");
		int amountErrors = 0;
		Integer amountErrorsInt = (Integer) mAmountErrors
				.get(mSource.getName());
		if (amountErrorsInt != null)
		{
			amountErrors = amountErrorsInt.intValue();
		}
		if (!ReceiverThreadManager.getInstance().isHTTPReceiverStopped(
				mSource.getName())
				&& (stopAfterErrors > 0) && (amountErrors >= stopAfterErrors))
		{
			String message = "Stopping HTTPReceiver " + mSource.getName()
					+ " because of maximum amount of errors!";
			Trace.always(message);
			NotifyError.notifyError(this, mSource, message, null, null);
			try
			{
				ReceiverThreadManager.getInstance().demandStopReceiverThread(
						mSource.getName());
			}
			catch (XException e)
			{
				// do nothing
			}
		}
	}

	public static void initializeAmountErrorsCompletely()
	{
		mAmountErrors.clear();
	}

}
