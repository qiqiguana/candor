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

package dash.providerFactory;

import dash.ComponentProvider;
import dash.Provider;


/**
 * @author jheintz
 *
 */
public class DefaultProvider implements Provider {


	/**
	 * Strategy:
	 * 1. Check ProviderFactory.threadLocalProvider
	 * 2. Check ComponentProvider.lookup
	 *  (TODO insert checks for classpath, jndi, ... lookups) 
	 * 3. Return null
	 *
	 * @see binding.provider.Provider#lookup(java.lang.Class, java.lang.String, binding.provider.ComponentProvider)
	 */
	public Object lookup(Class klazz, String key, ComponentProvider forTarget) {
		Object result=null;

		Provider threadLocalProvider = ProviderFactory.threadLocalProvider.get();
		if (threadLocalProvider != null) {
			result = threadLocalProvider.lookup(klazz, key, forTarget);
		}

		if (result == null) {
			result = forTarget.lookup(klazz, key, forTarget);
		}

		return result;
	}

}
