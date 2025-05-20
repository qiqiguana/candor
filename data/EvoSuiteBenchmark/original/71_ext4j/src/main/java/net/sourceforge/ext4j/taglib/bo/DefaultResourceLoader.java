/*
	 * Copyright 2002-2009 the original author or authors.
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */
package net.sourceforge.ext4j.taglib.bo;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Juergen Hoeller
 *
 */
public class DefaultResourceLoader {

		private static final String CLASSPATH_URL_PREFIX = "classpath:";

		/**
		 * Create a new DefaultResourceLoader.
		 * <p>ClassLoader access will happen using the thread context class loader
		 * at the time of this ResourceLoader's initialization.
		 * @see java.lang.Thread#getContextClassLoader()
		 */
		public DefaultResourceLoader() {
		}

		public URL getResource(String location) {
			if (location == null) throw new RuntimeException("Location must not be null");
			if (location.startsWith(CLASSPATH_URL_PREFIX)) {
				String oResourceLocation = location.substring(CLASSPATH_URL_PREFIX.length());
				return this.getClass().getResource(oResourceLocation);
			} else {
				try {
					// Try to parse the location as a URL...
					URL url = new URL(location);
					return url;
				} catch (MalformedURLException ex) {
					throw new RuntimeException(ex);
					// No URL -> resolve as resource path.
					//return getResourceByPath(location);
				}
			}
		}		

}
