/*
 * HelloNekoMUDTest.java
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit 4.5 test cases for HelloNekoMUD 
 * @author patrick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:xml/nekomud-hello.xml"})
public class HelloNekoMUDTest
{
	private ByteArrayOutputStream byteOut;
	private PrintStream captureOut;
	private PrintStream stdOut;
	
	@Autowired
	private MessageWriter messageWriter;

	@Before
	public void onSetUp() throws Exception {
		stdOut = System.out;
		byteOut = new ByteArrayOutputStream();
		captureOut = new PrintStream(byteOut);
		System.setOut(captureOut);
	}

	@After
	public void onTearDown() throws Exception {
		System.setOut(stdOut);
	}

	@Test
	public void testAlwaysSucceed() {
		assertTrue(true);
	}
	
	@Test
	public void testMainNull()
	{
		HelloNekoMUD.main(null);
		String output = new String(byteOut.toByteArray());
		assertEquals(output, messageWriter.getMessage());
	}

	@Test
	public void testMainEmpty()
	{
		String[] args = new String[0];
		HelloNekoMUD.main(args);
		String output = new String(byteOut.toByteArray());
		assertEquals(output, messageWriter.getMessage());
	}

	@Test
	public void testMainArgs()
	{
		String[] args = { "Hello, JUnit!" };
		HelloNekoMUD.main(args);
		String output = new String(byteOut.toByteArray());
		assertEquals(output, messageWriter.getMessage());
	}
}
