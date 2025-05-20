/* 
Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)

This file is part of Follow (http://follow.sf.net).

Follow is free software; you can redistribute it and/or modify
it under the terms of version 2 of the GNU General Public
License as published by the Free Software Foundation.

Follow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Follow; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ghm.follow.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Extension of {@link java.util.Properties} which allows one to specify property values which are
 * Lists of Strings.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class EnumeratedProperties extends Properties
{

	/**
	 * Returns the List value of the property with the supplied key. Note that one can call
	 * getEnumeratedProperty() for a given key successfully if and only if setEnumeratedProperty()
	 * for that key was called some time beforehand. All members of the list returned will be
	 * Strings.
	 * 
	 * @param key
	 *            lookup of the enumerated property to be retrieved.
	 * @return list containing String values
	 */
	public List<String> getEnumeratedProperty(String key)
	{
		ArrayList<String> values = new ArrayList<String>();
		int i = 0;
		String value;
		while ((value = this.getProperty(key + delimiter + i++)) != null)
		{
			values.add(value);
		}
		return values;
	}

	/**
	 * Assigns the supplied array of String values to the supplied key.
	 * 
	 * @param key
	 *            property lookup
	 * @param values
	 *            values to be associated with the property lookup
	 */
	public void setEnumeratedProperty(String key, List<String> values)
	{
		int i = 0;
		for (; i < values.size(); i++)
		{
			setProperty(key + delimiter + i, values.get(i));
		}
		while (getProperty(key + delimiter + i) != null)
		{
			remove(key + delimiter + i);
			i++;
		}
	}

	/** Delimiter between property name & list member index */
	protected static char delimiter = '.';

}
