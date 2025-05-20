package net.sf.xbus.base.bytearraylist;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * @author Fleckenstein
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class ByteArrayConverterFactory
{
	static public ByteArrayConverter getConverter(String system)
			throws XException
	{
		Configuration config = Configuration.getInstance();
		String as400name = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				system, "AS400");

		if (as400name != null)
		{
			return new ByteArrayConverterAS400(system);
		}
		else
		{
			return new ByteArrayConverterFile(system);
		}
	}

	/**
	 * Acts as a container for the information of one
	 */
	class Field
	{
		public String name = null;
		public String value = null;
		public int length = 0;
	}

}
