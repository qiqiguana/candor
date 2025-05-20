package net.sf.xbus.application;

import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.simple.SimpleTextMessage;

/**
 * <code>PostProcessor</code> is used to call one sender after receiving and
 * processing a message. It is called after the transaction has been committed.
 * An example is to call a program to process an incoming file.
 * <p />
 * The sender used for the postprocessing must not be transacted, since the
 * <code>PostProcessor</code> doesn't use the <code>TAManager</code>!
 */
public class PostProcessor implements Runnable
{
	XBUSSystem mDestination = null;
	Object mCallData = null;

	public static void start(XBUSSystem source, Object callData, String modus)
	{
		try
		{
			Vector postProcessors = new Vector();

			Configuration config = Configuration.getInstance();

			String destination = config.getValueOptional(
					Constants.CHAPTER_SYSTEM, source.getName(),
					"PostProcessor.System");

			if (destination != null)
			{
				String configModus = config.getValueOptional(
						Constants.CHAPTER_SYSTEM, source.getName(),
						"PostProcessor.Modus");
				if (configModus == null)
				{
					configModus = Constants.POSTPROCESSING_PERSYSTEM;
				} // if (configModus == null)
				if (configModus.equals(modus))
				{
					Object[] postProcessor =
					{
							destination,
							Boolean.valueOf(config.getValueAsBoolean(
									Constants.CHAPTER_SYSTEM, source.getName(),
									"PostProcessor.Asynchronous"))};
					postProcessors.add(postProcessor);
				} // if (configModus.equals(modus))
			} // if (destination != null) - a single postprocessor
			else
			{ // Perhaps several postprocessors
				destination = config.getValueOptional(Constants.CHAPTER_SYSTEM,
						source.getName(), "PostProcessor1.System");
				for (int i = 1; destination != null; i++)
				{ // Loop over postprocessors
					String configModus = config.getValueOptional(
							Constants.CHAPTER_SYSTEM, source.getName(),
							"PostProcessor" + i + ".Modus");
					if (configModus == null)
					{
						configModus = Constants.POSTPROCESSING_PERSYSTEM;
					} // if (configModus == null)
					if (configModus.equals(modus))
					{
						Object[] postProcessor =
						{
								destination,
								Boolean.valueOf(config.getValueAsBoolean(
										Constants.CHAPTER_SYSTEM, source
												.getName(), "PostProcessor" + i
												+ ".Asynchronous"))};
						postProcessors.add(postProcessor);
					} // if (configModus.equals(modus))
					destination = config.getValueOptional(
							Constants.CHAPTER_SYSTEM, source.getName(),
							"PostProcessor" + (i + 1) + ".System");
				} // for (int i=1; destination!=null; i++)
			} // else (destination != null)

			for (int i = 0; i < postProcessors.size(); i++)
			{
				destination = (String) ((Object[]) postProcessors.get(i))[0];
				PostProcessor postProc = new PostProcessor(new XBUSSystem(
						destination, source.getAddresses(), config
								.getValueAsBooleanOptional("System",
										destination, "Broadcast")), callData);
				if (((Boolean) ((Object[]) postProcessors.get(i))[1])
						.booleanValue())
				{
					Thread processorThread = new Thread(postProc);
					processorThread.start();
				} // then
				// (((Boolean)((Object[])postProcessors.get(i))[1]).booleanValue())
				else
				{
					postProc.process();
				} // else
				// (((Boolean)((Object[])postProcessors.get(i))[1]).booleanValue())
			} // for (int i=0; i<postProcessors.size()-1; i++)
		} // try
		catch (XException e)
		{
			// XException has already been traced
		} // catch
	} // start(XBUSSystem source, Object callData, String modus)

	private PostProcessor(XBUSSystem destination, Object callData)
	{
		mDestination = destination;
		mCallData = callData;
	}

	/**
	 * @see java.lang.Runnable#run()
	 * 
	 * May only be called from method <code>start</code>.
	 */
	public void run()
	{
		process();
	}

	private void process()
	{
		try
		{
			Trace.info("Starting postprocessor ...");

			SimpleTextMessage message = new SimpleTextMessage(null);
			message.setRequestObject(mCallData, null);
			Adapter.callSender(message, mDestination);

			Trace.info("Postprocessor finished");
		}
		catch (XException e)
		{
			// XException has already been traced
		}
	}
}
