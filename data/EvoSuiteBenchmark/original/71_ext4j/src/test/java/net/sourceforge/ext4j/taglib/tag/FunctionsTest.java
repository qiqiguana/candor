/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.ext4j.taglib.tag.Functions;

import org.junit.Test;

/**
 * @author luc
 *
 */
public class FunctionsTest {
	
	private static class MyIterable implements Iterable<Integer> {
		private List<Integer> mInts = new ArrayList<Integer>();
		public MyIterable(int pSize) {
			for (int i = 0; i < pSize; i++) {
				mInts.add(i);
			}
		}
		public Iterator<Integer> iterator() {
			return mInts.iterator();
		}
	}

	@Test
	public void capFirstLetters() throws Exception {
		String[][] oTestCases = new String[][] {
				{"HELLO MY FRIEND!", "Hello My Friend!"},
				{"ACADEMIC PACIFIC RIM CHARTER SCHOOL", "Academic Pacific Rim Charter School"}
		};
		Collection<String[]> oTests = Arrays.asList(oTestCases);
		for (String[] t : oTests) {
			String oInput = t[0];
			String oExpected = t[1];
			String oActual = Functions.capFirstLetters(oInput);
			assertEquals(oExpected, oActual);
		}
	}
	
	@Test
	public void decimalFormat() throws Exception {
		assertEquals("75.01", Functions.decimalFormat(75.0123, "##.##"));
	}
	
	@Test
	public void length() throws Exception {
		assertEquals(10, Functions.length(new MyIterable(10)));
	}
	
	@Test
	public void blurb() throws Exception {
		String oExpected = "Could you just go ahead and do that please...";
		String oText = "Could you just go ahead and do that please A.S.A.P?";
		assertEquals(oExpected, Functions.blurb(oText, "Could you just go ahead and do that ple".length()));		
	}
	
	@Test
	public void year() throws Exception {
		int oCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
		assertEquals(oCurrentYear, Functions.year(0));
		assertEquals(oCurrentYear + 2, Functions.year(2));
		assertEquals(oCurrentYear - 3, Functions.year(-3));
	}
	
	@Test
	public void ordinal() throws Exception {
		assertEquals("1st", Functions.ordinal(1));
		assertEquals("2nd", Functions.ordinal(2));
		assertEquals("3rd", Functions.ordinal(3));
		assertEquals("4th", Functions.ordinal(4));
		assertEquals("5th", Functions.ordinal(5));
		assertEquals("10th", Functions.ordinal(10));
		assertEquals("11th", Functions.ordinal(11));
		assertEquals("12th", Functions.ordinal(12));
		assertEquals("13th", Functions.ordinal(13));
		assertEquals("14th", Functions.ordinal(14));
		assertEquals("15th", Functions.ordinal(15));
		assertEquals("21st", Functions.ordinal(21));
		assertEquals("22nd", Functions.ordinal(22));
		assertEquals("23rd", Functions.ordinal(23));
		assertEquals("24th", Functions.ordinal(24));
		assertEquals("101st", Functions.ordinal(101));
		assertEquals("102nd", Functions.ordinal(102));
		assertEquals("103rd", Functions.ordinal(103));
		assertEquals("104th", Functions.ordinal(104));
	}
}
