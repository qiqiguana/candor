package com.imsmart.parser;

import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;

public class MParserFactory
{
    private MLog logger = MLog.getInstance();
    
	public static MParser getParser()
	{
		String fileType = MProperties.getInstance().getPropertyValue(
				MProperties.METADATA_FILE_TYPE);
		if (fileType.equalsIgnoreCase("CSV"))
		{
			MCSVParser parser = new MCSVParser();
			String seperator = MProperties.getInstance().getPropertyValue(
					MProperties.METADATA_SEPERATOR);
			parser.setSeparator(seperator);
			return parser;
		}
		else
		{
			return null;
		}

	}
}
