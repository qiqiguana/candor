/*
 * SelectorTest.java
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

package net.sourceforge.nekomud.nio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:xml/nekomud-config.xml","file:xml/nekomud-nio.xml"})
public class SelectorTest
{
	@Autowired private ApplicationContext applicationContext;
	private Selector selector;
	
	@Before
	public void setUp() throws Exception {
		selector = (Selector)applicationContext.getBean("selector");
	}

	@After
	public void tearDown() throws Exception {
		selector.close();
	}
	
	@Test
	public void testAlwaysSucceed() {
		assertTrue(true);
	}
	
	@Test
	public void testNegativeSelectTimeout() {
		try {
			selector.select(-10L);
			fail("Expected IllegalArgumentException");
		} catch(IllegalArgumentException e) {
			// expected
		} catch(IOException e) {
			fail("IOException: " + e.getMessage());
		}
	}
	
	@Test
	public void testKeysEmpty001() {
		assertTrue(selector.keys().isEmpty());
	}
	
	@Test
	public void testKeys002() throws Exception {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		InetAddress lh = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(lh, 7777);
		ssc.socket().bind(isa);
		SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
		
		assertFalse(selector.keys().isEmpty());
		assertEquals(1, selector.keys().size());
		for(SelectionKey selectionKey : selector.keys()) {
			assertEquals(acceptKey, selectionKey);
		}
	}
	
	@Test
	public void testKeysEmpty002() {
		assertTrue(selector.keys().isEmpty());
	}
}
