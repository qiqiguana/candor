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
public class ProviderFactory {
	private static Provider _globalProvider;
	public static final ThreadLocal<Provider> threadLocalProvider = new ThreadLocal<Provider>();

	private static class ProviderBuilder {
		public static Provider _buildProvider = new DefaultProvider();
	}

	public static Provider getProvider() {
		if (_globalProvider == null) {
			return ProviderBuilder._buildProvider;
		} else {
			return _globalProvider;
		}
	}

	public static synchronized void setProvider(Provider defaultProvider) {
		if (_globalProvider != null)
			throw new IllegalArgumentException("Provider is already set!");

		_globalProvider = defaultProvider;
	}
	

	public static Object lookup(Class ofType, String key, ComponentProvider forTarget) {
		Provider provider = ProviderFactory.getProvider();
		Object result = provider.lookup(ofType, key, forTarget);
		
		return result;
	}
}
