package org.javathena.core.utiles;

import junit.framework.TestCase;

public class FunctionsTest extends TestCase
{

	public static void testIntToByteTab()
	{
		byte[] aByteTab = new byte[4];
		int toParse = 24726;
		Functions.intToByteTab(toParse, 0, 4, aByteTab);
		
		assertEquals((byte)150, aByteTab[0]);
		assertEquals((byte)96, aByteTab[1]);
		assertEquals(0, aByteTab[2]);
		assertEquals(0, aByteTab[3]);

	}
	public static void testCharSexToInt()
	{
		assertEquals(2,Functions.charSexToInt('S'));
		assertEquals(1,Functions.charSexToInt('M'));
		assertEquals(0,Functions.charSexToInt('F'));
	}
}
