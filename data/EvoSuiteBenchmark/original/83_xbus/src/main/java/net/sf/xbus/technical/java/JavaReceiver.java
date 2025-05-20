package net.sf.xbus.technical.java;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Receiver;

/**
 * The class <code>JavaReceiver</code> receives a request via a call of its
 * <code>receive</code> method. It creates a <code>Message</code> -object
 * and calls the application-layer.
 */
public class JavaReceiver implements Receiver
{
	/**
	 * Receives a request and returns a response.
	 */
	public Object receive(String system, Object request) throws XException
	{
		if (system == null)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_JAVA, "2");
		}

		String errormessage = null;
		XBUSSystem source = new XBUSSystem(system);

		Trace.info("Receiving data from " + source.getCompleteName());

		Object responseObject = null;

		/*
		 * Initialize transaction manager
		 */
		TAManager taManager = TAManager.getInstance();

		try
		{
			taManager.clearManager();
			taManager.begin();

			Adapter adapter = new Adapter();
			adapter.callApplication(source, request, getType());
			responseObject = adapter.getResponse();
			if (Constants.RC_OK.equals(adapter.getReturncode()))
			{
				taManager.commit();
				PostProcessor.start(source, responseObject,
						Constants.POSTPROCESSING_PERSYSTEM);
				Trace.info("End processing " + source.getCompleteName());
				Trace.info("-----------------------------");
			}
			else
			{
				errormessage = adapter.getErrormessage();
				taManager.rollback();
				NotifyError.notifyError(this, source, errormessage, request,
						null);
				Trace
						.info("Error while processing "
								+ source.getCompleteName());
				Trace.info("-----------------------------");
			}
		}
		catch (Exception t)
		{
			errormessage = t.getMessage();
			NotifyError.notifyError(this, source, errormessage, request, null);
			Trace.info("Error while processing " + source.getCompleteName());
			Trace.info("-----------------------------");
			if (t instanceof XException)
			{
				throw (XException) t;
			}
		}

		taManager.close();

		if (errormessage != null)
		{
			List messages = new Vector(1);
			messages.add(errormessage);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_JAVA, "3", messages);
		}

		return responseObject;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
