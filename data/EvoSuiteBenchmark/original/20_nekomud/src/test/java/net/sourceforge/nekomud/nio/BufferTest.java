/*
 * BufferTest.java
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

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * BufferTest investigates some properties of Buffer and ByteBuffer from the
 * java.nio package.
 * @author pmeade
 */
public class BufferTest
{
	public static final int BUFFER_SIZE = 1024;
	public static final String HELLO_MESSAGE = "Hello, world!\n";
	
	private ByteBuffer byteBuffer;

	@Before
	public void setUp() throws Exception {
		byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	}

	@After
	public void tearDown() throws Exception {
		byteBuffer = null;
	}

	@Test
	public void testAlwaysSucceed() {
		assertTrue(true);
	}
	
	@Test
	public void testBuffer000() {
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
	}

	@Test
	public void testBuffer001() {
		byteBuffer.clear();
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
		byteBuffer.put(HELLO_MESSAGE.getBytes());
		assertEquals(14, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE-14, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
	}

	@Test
	public void testBuffer002() {
		byteBuffer.clear();
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
		for(int i=0; i<(BUFFER_SIZE / HELLO_MESSAGE.length()); i++) {
			byteBuffer.put(HELLO_MESSAGE.getBytes());
			assertEquals(HELLO_MESSAGE.length() * (i+1), byteBuffer.position());
			assertEquals(BUFFER_SIZE, byteBuffer.limit());
			assertEquals(BUFFER_SIZE, byteBuffer.capacity());
			assertEquals(BUFFER_SIZE-(HELLO_MESSAGE.length() * (i+1)), byteBuffer.remaining());
			assertTrue(byteBuffer.hasRemaining());
		}
		try {
			byteBuffer.put("Hello, world!\n".getBytes());
			fail("BufferOverflowException expected");
		} catch(BufferOverflowException e) {
			// expected
		}
		assertEquals(BUFFER_SIZE - (BUFFER_SIZE % HELLO_MESSAGE.length()), byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals((BUFFER_SIZE % HELLO_MESSAGE.length()), byteBuffer.remaining());
		if((BUFFER_SIZE % HELLO_MESSAGE.length()) != 0) {
			assertTrue(byteBuffer.hasRemaining());
		} else {
			assertFalse(byteBuffer.hasRemaining());
		}
	}

	@Test
	public void testBuffer003() {
		byteBuffer.clear();
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
		byteBuffer.put(HELLO_MESSAGE.getBytes());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE-HELLO_MESSAGE.length(), byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
		byteBuffer.flip();
		assertEquals(0, byteBuffer.position());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
		byte[] byteArray = new byte[byteBuffer.remaining()];
		byteBuffer.get(byteArray);
		String message = new String(byteArray);
		assertEquals(HELLO_MESSAGE, message);
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.position());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(0, byteBuffer.remaining());
		assertFalse(byteBuffer.hasRemaining());
	}

	@Test
	public void testBuffer004() {
		byteBuffer.clear();
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		byteBuffer.put(HELLO_MESSAGE.getBytes());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE-HELLO_MESSAGE.length(), byteBuffer.remaining());
		byteBuffer.flip();
		assertEquals(0, byteBuffer.position());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.remaining());
		byte[] byteArray = new byte[byteBuffer.remaining()];
		byteBuffer.get(byteArray);
		String message = new String(byteArray);
		assertEquals(HELLO_MESSAGE, message);
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.position());
		assertEquals(HELLO_MESSAGE.length(), byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(0, byteBuffer.remaining());
		assertFalse(byteBuffer.hasRemaining());
		byteBuffer.clear();
		assertEquals(0, byteBuffer.position());
		assertEquals(BUFFER_SIZE, byteBuffer.limit());
		assertEquals(BUFFER_SIZE, byteBuffer.capacity());
		assertEquals(BUFFER_SIZE, byteBuffer.remaining());
		assertTrue(byteBuffer.hasRemaining());
	}
}
