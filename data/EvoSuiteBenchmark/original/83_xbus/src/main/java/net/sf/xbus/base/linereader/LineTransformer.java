package net.sf.xbus.base.linereader;

import net.sf.xbus.base.core.XException;

/**
 * TODO Kommentierung
 */
public interface LineTransformer
{
	/**
	 * @param string
	 * @return ???
	 * @throws XException
	 */
	public String transform(String string) throws XException;

}
