package com.momed.test;

import java.util.Iterator;
import java.util.List;

import com.momed.parser.MParser;
import com.momed.parser.MParserFactory;

public class MCSVParserTest
{

	/**
	 * @param args
	 */
	public static void main(String args[])
	{
		MParser parser = MParserFactory.getParser();
		List csvList = parser
				.parseAsList("C:/Documents and Settings/gowerdh/My Documents/"
						+ "Infocrossing/Migration/sample_data/"
						+ "metadata/clmaltx1.inx@20070430231040");
		for (Iterator iterator = csvList.iterator(); iterator.hasNext();)
		{
			List row = (List) iterator.next();
			for (Iterator iterator2 = row.iterator(); iterator2.hasNext();)
			{
				String column = (String) iterator2.next();
				System.out.print(column);
				if (iterator2.hasNext())
					System.out.print(",");

			}
			System.out.println();
		}
	}

}
