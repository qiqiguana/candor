/*
 * ConfigurationTest.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:xml/nekomud-config.xml"})
public class ConfigurationTest
{
	@Autowired private ApplicationContext applicationContext;
	private Configuration configuration;
	
	@Before
	public void setUp() throws Exception {
		configuration = (Configuration)applicationContext.getBean("configuration");
	}

	@Test
	public void testAlwaysSucceed() {
		assertTrue(true);
	}
	
	@Test
	public void testGetPort() {
		assertEquals(7777, configuration.getPort());
	}

	@Test
	public void testSetPort() {
		int oldPort = configuration.getPort();
		assertEquals(7777, configuration.getPort());
		configuration.setPort(1234);
		assertEquals(1234, configuration.getPort());
		configuration.setPort(oldPort);
		assertEquals(7777, configuration.getPort());
	}
}
