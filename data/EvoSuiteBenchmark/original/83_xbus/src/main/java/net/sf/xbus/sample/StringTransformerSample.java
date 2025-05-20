package net.sf.xbus.sample;

import net.sf.xbus.base.linereader.LineTransformer;

/**
 * TODO Kommentierung
 */
public class StringTransformerSample implements LineTransformer
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.base.core.strings.StringTransformer#transform(java.lang.String)
	 */
	public String transform(String string)
	{
		return new JavaWorker().reverse(string);
	}

}
