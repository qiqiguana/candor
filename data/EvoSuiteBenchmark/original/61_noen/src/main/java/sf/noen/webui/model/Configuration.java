/*
 * Copyright (C) 2009 VTT Technical Research Centre of Finland.
 *
 * This file is part of NOEN framework.
 *
 * NOEN framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2.
 *
 * NOEN framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package sf.noen.webui.model;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Teemu Kanstrén
 */
public class Configuration {
  /** Class to test, should contain JUnit tests/suite. */
  private static String testClassName = null;
  private static Properties properties = null;

  public static synchronized String getTestClassName() {
	if (testClassName == null) {
	  testClassName = property("testclass");
	}
	return testClassName;
  }

  private static synchronized String property(String key) {
	try {
	  if (properties == null) {
		properties = new Properties();
		properties.load(Configuration.class.getClassLoader().getResourceAsStream("noen-testui.properties"));
	  }
	  return properties.getProperty(key);
	} catch (IOException e) {
	  throw new RuntimeException("UNable to load configuration file 'noen-testui.properties'", e);
	}
  }
}
