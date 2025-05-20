/*
* Copyright (C) 2005  John D. Heintz
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public License
* as published by the Free Software Foundation; either version 2.1
* of the License.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Library General Public License for more details.
*
* John D. Heintz can be reached at: jheintz@pobox.com 
*/
package dash;

/**
 * The Provider interface is used to lookup Obtainable resources.
 * 
 * @author jheintz
 *
 */
public interface Provider {
	
	/**
	 * This is the central interface to support matching findable objects
	 * to requests.
	 * 
	 * @param klazz The type of object that must be returned.
	 * @param key A String key to help identify the returned object. (URN, JNDI, ...)
	 * @param forTarget The object that is Obtaining the returned object
	 * @return The object in question, or null
	 */
	public Object lookup(Class klazz, String key, ComponentProvider forTarget);
}
