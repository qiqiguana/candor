/* dcParseArgs - Java library to simplify args[] handling
 * 
 * Copyright (C) 2008 Roland Koller <roland@devcity.de>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package de.devcity.parseargs;

import java.util.List;

import junit.framework.TestCase;
import de.devcity.parseargs.arguments.ParameterArgument;
import de.devcity.parseargs.arguments.StringArgument;
import de.devcity.parseargs.arguments.SwitchArgument;

public class ArgsParserTest extends TestCase {

	/*
	 * a is a short switch, b is a long switch, c a short argument with a parameter (parameter argument), d a long parameter argument,
	 * everything that follows are independent "String" arguments.
	 */
	final String[] sample1 = { "-a", "--bee", "-c", "cccc", "--dee", "ddddd", "ffffff", "ggggggg", "" };

	/* 
	 * a and b are switches, c is a parameter argument using d as it's parameter.
	 */
	final String[] sample2 = { "-abc", "dee" };

	final String[] sample3 = { "aaa", "", "bbb" };

	public void testParseSwitchArgument() {
		ArgsParser ap = new ArgsParser(sample1);

		SwitchArgument res1 = ap.parseSwitchArgument("a");
		assertTrue(res1.isSet());
		assertFalse(res1.isLongKey());

		SwitchArgument res2 = ap.parseSwitchArgument("bee");
		assertTrue(res2.isSet());
		assertTrue(res2.isLongKey());

		SwitchArgument res3 = ap.parseSwitchArgument("x");
		assertFalse(res3.isSet());

		ap.setArgs(sample2);

		SwitchArgument res4 = ap.parseSwitchArgument("a");
		assertTrue(res4.isSet());

		SwitchArgument res5 = ap.parseSwitchArgument("b");
		assertTrue(res5.isSet());
	}

	public void testParseParameterArgument() {
		ArgsParser ap = new ArgsParser(sample1);

		ParameterArgument res1 = ap.parseParameterArgument("c");
		assertEquals("c", res1.getKey());
		assertFalse(res1.isLongKey());
		assertEquals("cccc", res1.getValue());

		ParameterArgument res2 = ap.parseParameterArgument("dee");
		assertEquals("dee", res2.getKey());
		assertTrue(res2.isLongKey());
		assertEquals("ddddd", res2.getValue());

		assertNull(ap.parseParameterArgument("xxx"));
		assertNull(ap.parseParameterArgument("bee"));

		ap.setArgs(sample2);

		assertNull(ap.parseParameterArgument("e"));

		ParameterArgument res3 = ap.parseParameterArgument("c");
		assertEquals("c", res3.getKey());
		assertFalse(res3.isLongKey());
		assertEquals("dee", res3.getValue());

	}

	public void testParseStringArgument() {
		ArgsParser ap = new ArgsParser(sample3);
		List<StringArgument> res = ap.parseStringArgument();
		assertEquals(3, res.size());
		assertEquals(sample3[0], res.get(0).getValue());
		assertEquals(sample3[1], res.get(1).getValue());
		assertEquals(sample3[2], res.get(2).getValue());
	}

	/*
	 * A complete test in correct order.
	 * (Param args --> Switch args --> String args) 
	 */

	public void testSuite() {
		// sample1: -a --bee -c cccc --dee ddddd ffffff ggggggg ""

		ArgsParser ap = new ArgsParser(sample1);
		assertEquals(sample1.length, ap.getArgsCount());
		assertEquals(sample1.length, ap.getArgsLeftCount());

		ParameterArgument resC = ap.parseParameterArgument("c");
		ParameterArgument resD = ap.parseParameterArgument("dee");
		assertEquals(5, ap.getArgsLeftCount());

		SwitchArgument resA = ap.parseSwitchArgument("a");
		SwitchArgument resB = ap.parseSwitchArgument("bee");
		assertEquals(3, ap.getArgsLeftCount());

		List<StringArgument> resString = ap.parseStringArgument();
		assertEquals(0, ap.getArgsLeftCount());

		assertEquals(sample1.length, ap.getArgsCount());

		assertEquals(sample1[3], resC.getValue());
		assertEquals(sample1[5], resD.getValue());
		assertTrue(resA.isSet());
		assertTrue(resB.isSet());
		assertEquals(3, resString.size());
	}
}
