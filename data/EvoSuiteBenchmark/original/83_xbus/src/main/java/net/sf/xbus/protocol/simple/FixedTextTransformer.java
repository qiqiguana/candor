/*
 * Created on 20.10.2004
 * 
 */
package net.sf.xbus.protocol.simple;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>FixedTextTransformer</code> serves for sending fix text. The text
 * is written from the configuration.
 * 
 * @author Stephan Düwel
 */
public class FixedTextTransformer implements Transformer
{

	/**
	 * The <code>transform</code> creates a string from the configuration. It
	 * takes any input, ignores it and reads the output string from the
	 * configuration.
	 * 
	 * @see net.sf.xbus.protocol.Transformer#transform(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem,
	 *      net.sf.xbus.protocol.Message)
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		Configuration config = Configuration.getInstance();
		// For the linebreak
		String platform = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				destination.getName(), "Platform");
		// Standard linebreak from the operating system
		String lineBreak = Constants.LINE_SEPERATOR;
		// Using another platform's linebreak in the output?
		if (platform != null)
			lineBreak = Constants.getLineSeperator(platform);

		// For the result.
		StringBuffer buffer = new StringBuffer();
		// Read the string line by line form the configuration.
		String line = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				destination.getName(), "TextLine1");
		int i = 1;
		while (line != null)
		{
			i++;
			line = destination.replaceAllMarkers(line)[0];
			buffer.append(line);
			buffer.append(lineBreak);
			line = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					destination.getName(), "TextLine" + i);
		} // while (line != null)

		return buffer.toString();
	} // transform(Object inObject, XBUSSystem source, XBUSSystem destination,
		// Message destinationMessage)

} // FixedTextTransformer
