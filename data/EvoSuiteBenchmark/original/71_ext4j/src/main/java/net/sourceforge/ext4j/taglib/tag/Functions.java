/**
 * @author luc
 */
package net.sourceforge.ext4j.taglib.tag;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspTagException;

import net.sourceforge.ext4j.taglib.bo.IRequest;



/**
 * @author luc
 *
 */
public class Functions {

	//private static Log mLogger = LogFactory.getLog(Functions.class);
	private static final Map<Integer, String> NUMBERS_WORD = new HashMap<Integer, String>();
	
	private static final String UNDERSCORE = "_";
	private static final String REGEX_UNDERSCORE_ONE_OR_MORE = "_+";
	private static final String REGEX_WHITESPACE_ONE_OR_MORE = "\\s+";
	private static final String[] TO_REPLACE = {"-",":",",","'","!","\"","\\*","\\(","\\)","~","`"};
	private static final String BLURB_SUFFIX = "...";
	private static final String STRING_SPACE = " ";
	private static final String STRING_DOT = ".";
	private static final String STRING_COMMA = ",";
	private static final String STRING_EMPTY = "";
	private static final String STRING_HTML_CR = "<br />";
	//private static final String STRING_CR = System.getProperty("line.separator"); 
	private final static String EOL_LINUX = "\n"; 
	private final static String EOL_WIN32 = "\r\n"; 
	private final static String EOL_MAC_LEGACY = "\n\r";
	public static final String LINK_PREFIX = "webtools.link.";
	//private static final String FILENAME_VAR_REGEX = "\\$\\{filename\\}";
	
	static {
		NUMBERS_WORD.put(0, "zero");
		NUMBERS_WORD.put(1, "one");
		NUMBERS_WORD.put(2, "two");
		NUMBERS_WORD.put(3, "three");
		NUMBERS_WORD.put(4, "four");
		NUMBERS_WORD.put(5, "five");
		NUMBERS_WORD.put(6, "six");
		NUMBERS_WORD.put(7, "seven");
		NUMBERS_WORD.put(8, "eight");
		NUMBERS_WORD.put(9, "nine");
		NUMBERS_WORD.put(10, "ten");
		NUMBERS_WORD.put(11, "eleven");
		NUMBERS_WORD.put(12, "twelve");
		NUMBERS_WORD.put(13, "thirteen");
		NUMBERS_WORD.put(14, "fourteen");
		NUMBERS_WORD.put(15, "fifteen");
		NUMBERS_WORD.put(16, "sixteen");
		NUMBERS_WORD.put(17, "seventeen");
		NUMBERS_WORD.put(18, "eighteen");
		NUMBERS_WORD.put(19, "nineteen");
		fillTens(20, "twenty");
		fillTens(30, "thirty");
		fillTens(40, "fourty");
		fillTens(50, "fifty");
		fillTens(60, "sixty");
		fillTens(70, "seventy");					
		fillTens(80, "eighty");
		fillTens(90, "ninety");
	}
	
	public static int year(int pOffset) {
		Calendar c = Calendar.getInstance();
		int oYear = c.get(Calendar.YEAR);
		return oYear + pOffset;
	}
	
	public static char toUpper(char c) {
		return Character.toUpperCase(c);
	}
	
	public static char forDigit(int pDigit, int pRadix) {
		return Character.forDigit(pDigit, pRadix);
	}
	
	public static int length(Object pCollection) throws JspTagException {
		if (pCollection == null) return 0;
		if (Iterable.class.isAssignableFrom(pCollection.getClass())) {
			Iterable<Object> oIterable = (Iterable<Object>) pCollection;
			int oSize = 0;
			for (Object oKey : oIterable) {
				oSize++;
			}
			return oSize;
		} else {
			return org.apache.taglibs.standard.functions.Functions.length(pCollection);
		}
	}
	
	public static String join(String[] pStrings, String pSep) {
		if (pStrings == null || pStrings.length == 0) return STRING_EMPTY;
		StringBuffer oBuf = new StringBuffer();
		for (int i = 0; i < pStrings.length; i++) {
			if (oBuf.length() > 0) oBuf.append(pSep);
			oBuf.append(pStrings[i]);
		}
		return oBuf.toString();
	}

	public static String seo(String pValue) {
		if (pValue == null) return pValue;
		String oResult = pValue;
		oResult = oResult.replaceAll(REGEX_WHITESPACE_ONE_OR_MORE, UNDERSCORE);
		for (int i = 0; i < TO_REPLACE.length; i++) {
			oResult = oResult.replaceAll(TO_REPLACE[i], UNDERSCORE);
		}		
		oResult = oResult.replaceAll(REGEX_UNDERSCORE_ONE_OR_MORE, UNDERSCORE); // trim "_"s
		return oResult;
	}

	public static long round(double pValue) {
		return Math.round(pValue);
	}
	
	public static String stripHTML(String s) {
		if (s == null) return null;
		return s.replaceAll("\\<[^>]*\\>", STRING_EMPTY);
	}
	
	public static String blurb(String s, int pMaxLength) {
		if (s == null) return null;
		String oResult = stripHTML(s);
		if (oResult.length() < pMaxLength) return oResult;
		StringBuilder oBuf = new StringBuilder();
		String[] oWords = oResult.split(STRING_SPACE);
		int i = 0;
		while (i < oWords.length && oBuf.length() < pMaxLength) {
			oBuf.append(oWords[i]);
			oBuf.append(STRING_SPACE);
			i++;
		}
		oResult = oBuf.toString().trim();
		while (oResult.endsWith(STRING_DOT) || oResult.endsWith(STRING_COMMA)) {
			oResult = oResult.substring(0, oResult.length() - 1);
		}
		//oResult = stripHTML(oResult); // moved above
		return oResult + BLURB_SUFFIX;
	}

	public static String blurbHTML(String s, int pMaxLength) {
		if (s == null) return null;
		String oResult = s.replaceAll("<p>", STRING_SPACE);
		oResult = oResult.replaceAll("</p>", STRING_EMPTY);
		oResult = oResult.replaceAll("<br />", STRING_SPACE);
		oResult = oResult.replaceAll("<br>", STRING_SPACE);
		return blurb(oResult, pMaxLength);
	}
	
	public static String since(Date pFrom) {
		if (pFrom == null)
			return "";
		String oCaption = null;
		long oDiff = Calendar.getInstance().getTime().getTime() - pFrom.getTime();
		long oDaysPassed = oDiff / (1000 * 60 * 60 * 24);
		if (oDaysPassed <= 1) {
			long oMinutesPassed = oDiff / (1000*60);
			if (oMinutesPassed <= 1) {
				oCaption = "1 minute ago";
			} else if (oMinutesPassed <= 5) {
				oCaption = "5 minutes ago";
			} else if (oMinutesPassed <= 30) {
				oCaption = "30 minutes ago";
			} 
			long oHours = oMinutesPassed / 60;
			if (oHours <= 1) {
				oCaption = "1 hour ago";
			} else if (oHours <= 11){
				oCaption = oHours + " hours ago";
			} else {
				oCaption = "today";
			}
		} else if (oDaysPassed > 1 && oDaysPassed <= 2) {
			oCaption = "yesterday";
		} else if (oDaysPassed <= 7) {
			oCaption = "this week";
		} else if (oDaysPassed > 7 && oDaysPassed <= 14) {
			oCaption = "last week";
		} else if (oDaysPassed > 14 && oDaysPassed <= 21) {
			oCaption = "2 weeks ago";
		} else if (oDaysPassed > 21 && oDaysPassed <= 28) {
			oCaption = "3 weeks ago";
		} else if (oDaysPassed > 28 && oDaysPassed <= 60) {
			oCaption = "last month";
		} else if (oDaysPassed > 60 && oDaysPassed <= 365) {
			int oMonths = ((int)oDaysPassed)/30;
			oCaption = oMonths + " months ago";
		} else {
			oCaption = "over a year ago";
		}
		return oCaption;
	}
	
	public static String decimalFormat(double pDouble, String pPattern) {
		DecimalFormat oFormatter = new DecimalFormat(pPattern);
		return oFormatter.format(pDouble);
	}
	
	/**
	 * Format a date with a pattern (using java.text.SimpleDateFormat)
	 * @param pDateTime the date
	 * @param pPattern the pattern
	 * @return the date to display
	 */
	public static String dateFormat(Date pDateTime, String pPattern) {
		if (pDateTime == null)
			return "";
		SimpleDateFormat oFormatter = new SimpleDateFormat (pPattern);
		return oFormatter.format(pDateTime);
	}
	
	/**
	 * Add 's or ' at the end of the text, for example "James'" for "James" or "Daniel's" for "Daniel"
	 * @param pText the text
	 * @return the text with 's or '
	 */
	public static String addS(String pText) {
		if (pText == null || pText.trim().equals(""))
			return "";
		if (pText.charAt(pText.length() - 1) == 's')
			return pText + "'";
		else
			return pText + "'s";
	}
	
	/**
	 * Return the pMaxLength first characters of the string s with "..."
	 * @param s
	 * @param pMaxLength
	 * @return
	 */
	public static String blurbChar(String s, int pMaxLength) {
		if (s == null) return null;
//		if (s.length() < pMaxLength) return s.replaceAll(STRING_CR, " ");
//		return s.substring(0, pMaxLength).replaceAll(STRING_CR, " ") + BLURB_SUFFIX;
		if (s.length() < pMaxLength) return s.replaceAll(EOL_WIN32, " ").replaceAll(EOL_MAC_LEGACY, " ").replaceAll(EOL_LINUX, " ");
		return s.substring(0, pMaxLength).replaceAll(EOL_WIN32, " ").replaceAll(EOL_MAC_LEGACY, " ").replaceAll(EOL_LINUX, " ") + BLURB_SUFFIX;
	}
	
	/**
	 *  Break up a too big word on several lines
	 * @param s
	 * @param pMaxLength
	 * @return
	 */
	public static String cutWordCR(String s, int pMaxLength) {
		if (s == null) return null;
		if (s.length() < pMaxLength) return s;
		String oString = s;
		String oReturn = "";
		do {
			oReturn = oReturn + oString.substring(0, pMaxLength) + STRING_HTML_CR;
			oString = oString.substring(pMaxLength, oString.length());
		} while (oString.length() > pMaxLength);
		if (oString.trim().length() == 0) {
			oReturn = oReturn.substring(oReturn.length() - STRING_HTML_CR.length(), oReturn.length());
		}	else {
			oReturn = oReturn + oString;
		}
		return oReturn;
	}

	/**
	 * Break up the too big words in the given text
	 * @param pText
	 * @param pMaxLength
	 * @return
	 */
	public static String cutWordCRText(String pText, int pMaxLength) {
		if (pText == null) return null;
		if (pText.length() < pMaxLength) return pText;
		String oReturn = "";
		int c = 0;
		for (int i = 0; i < pText.length(); i++) {
			if (pText.charAt(i) == ' ')
				c = 0;
			else if (c >= pMaxLength) {
				oReturn = oReturn + STRING_HTML_CR;
				c = 0;
			}
			else				
				c++;
			oReturn = oReturn + pText.charAt(i);
		}
		return oReturn;
	}

	/**
	 * Break up the too big words in the given text
	 * @param pText
	 * @param pMaxLength
	 * @return
	 */
	public static String replaceCRWithBR(String pText) {
		if (pText == null) return null;
		return pText.replaceAll("\r\n|\n", "<br />");
	}
	
	/**
	 * Convert a date in milliseconds
	 * @param pDateTime the date
	 * @return the milliseconds
	 */
	public static long dateToMilliseconds(Date pDateTime) {
		if (pDateTime == null)
			return 0;
		
		Calendar c = Calendar.getInstance();
		c.setTime(pDateTime);
		return c.getTimeInMillis();
	}
	
	/**
	 * Order the words of a String 
	 * @param s
	 * @param separator the words separator
	 * @return
	 */
	public static String orderWords(String s, String pSeparator) {
		if (s == null || s.trim().length() == 0 || s.equals(pSeparator)) return null;
		String[] oWords = s.split(pSeparator);
		Arrays.sort(oWords);		
		StringBuffer oBuf = new StringBuffer();
		for (int i = 0; i < oWords.length; i++) {
			oBuf.append(oWords[i]);
			if ((i+1) < oWords.length) oBuf.append(pSeparator);
		}
		return oBuf.toString();
	}
	
	public static String capFirstLetters(String s) {
		if (s == null || s.trim().length() == 0) return s;
		String oLowerCase = s.toLowerCase();
		StringTokenizer oTokenizer = new StringTokenizer(oLowerCase, STRING_SPACE);
		StringBuffer oBuf = new StringBuffer();
		String oToken = null;
		while (oTokenizer.hasMoreTokens())  {
			oToken = oTokenizer.nextToken();
			oBuf.append(oToken.substring(0, 1).toUpperCase() + oToken.substring(1));
			if (oTokenizer.hasMoreTokens()) oBuf.append(STRING_SPACE);
		}
		return oBuf.toString();
	}
	
	private static void fillTens(int pTen, String pTenWord) {
		NUMBERS_WORD.put(pTen, pTenWord);
		for (int i = 1; i < 10; i++) {
			NUMBERS_WORD.put(pTen + i, pTenWord + "-" + NUMBERS_WORD.get(i));
		}
	}
	
	public static String numberInWord(int pValue) throws Exception {
		String oWord = NUMBERS_WORD.get(pValue);
		if (oWord == null) oWord = Integer.toString(pValue);
		return oWord;
	}
	
	public static String ordinal(int pInteger) {
		int oDigit = getLastDigit(pInteger);
		if ((pInteger > 20 || pInteger < 10) && oDigit < 4) {
			switch (oDigit) {
				case 1:
					return pInteger + "st";
				case 2:
					return pInteger + "nd";
				case 3:
					return pInteger + "rd";
			}
		}
		return pInteger + "th";
	}
	
	private static int getLastDigit(int pInteger) {
		String s = Integer.toString(pInteger);
		return Integer.parseInt(s.substring(s.length() - 1));
	}
	
	public static IRequest removeParam(String pName, IRequest pRequest) throws Exception {
		return pRequest.removeParam(pName);
	}
	
	public static IRequest addParam(String pName, String pValue, IRequest pRequest) throws Exception {
		return pRequest.addParam(pName, pValue);
	}
	
	public static IRequest setParam(String pName, String pValue, IRequest pRequest) throws Exception {
		return pRequest.setParam(pName, pValue);
	}
}
