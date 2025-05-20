package net.sf.xbus.application;

import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.simple.EmptyMessage;

/**
 * The <code>PreProcessor</code> is started before a system will be processed
 * by a <code>SingleReceiver</code> resp. at the beginning of the
 * <code>ReceiverService</code>, depending on the <code>Configuration</code>.
 */
public class PreProcessor
{
	static public final String RECEIVER_SERVICE = "ReceiverService";

	/**
	 * Reads the <code>Configuration</code> and calls the preprocessor system
	 * if it is configured.
	 * 
	 * @param system name of the system when called by a
	 *            <code>ReceiverSingle</code> or <code>RECEIVER_SERVICE</code>
	 *            when called by the <code>ReceiverService</code>
	 * @throws XException if something goes wrong
	 */
	static public void process(String system) throws XException
	{
		String preProcessor = null;
		Vector preProcessors = null;
		Configuration config = Configuration.getInstance();
		if (RECEIVER_SERVICE.equals(system))
		{
			preProcessor = config.getValueOptional("Base", "ReceiverService",
					"PreProcessor");
			if (preProcessor != null)
			{
				preProcessors = new Vector();
				preProcessors.add(preProcessor);
			} // then (preProcessor != null): a single pre-processor
			else
			{
				preProcessor = config.getValueOptional("Base",
						"ReceiverService", "PreProcessor1");
				if (preProcessor != null)
				{
					preProcessors = new Vector();
					for (int i = 2; preProcessor != null; i++)
					{
						preProcessors.add(preProcessor);
						preProcessor = config.getValueOptional("Base",
								"ReceiverService", "PreProcessor" + i);
					} // for (int i=1; preProcessor!=null; i++)
				} // if (preProcessor != null): some multiple pre-processors
			} // else (preProcessor != null): no single pre-processors
		} // then (RECEIVER_SERVICE.equals(system))
		else
		{
			preProcessor = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					system, "PreProcessor");
			if (preProcessor != null)
			{
				preProcessors = new Vector();
				preProcessors.add(preProcessor);
			} // then (preProcessor != null): a single pre-processor
			else
			{
				preProcessor = config.getValueOptional(
						Constants.CHAPTER_SYSTEM, system, "PreProcessor1");
				if (preProcessor != null)
				{
					preProcessors = new Vector();
					for (int i = 2; preProcessor != null; i++)
					{
						preProcessors.add(preProcessor);
						preProcessor = config.getValueOptional(
								Constants.CHAPTER_SYSTEM, system,
								"PreProcessor" + i);
					} // for (int i=1; preProcessor!=null; i++)
				} // if (preProcessor != null): some multiple pre-processors
			} // else (preProcessor != null): no single pre-processors
		} // else (RECEIVER_SERVICE.equals(system))

		if (preProcessors != null)
		{
			for (int i = 0; i < preProcessors.size(); i++)
			{
				Trace.info("Starting preprocessor "
						+ (String) preProcessors.elementAt(i) + " ...");
				XBUSSystem preProcessorSystem = new XBUSSystem(
						(String) preProcessors.elementAt(i));
				Adapter.callSender(new EmptyMessage(preProcessorSystem),
						preProcessorSystem);
				Trace.info("PreProcessor "
						+ (String) preProcessors.elementAt(i) + " finished");
			} // for (int i=0; i<preProcessors.size(); i++)
		} // if (preProcessors != null)
	} // process(String system)

} // PreProcessor
