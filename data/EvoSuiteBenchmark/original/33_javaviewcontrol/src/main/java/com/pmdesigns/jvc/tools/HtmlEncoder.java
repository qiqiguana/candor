package com.pmdesigns.jvc.tools;

/**
 * A simple HTML encoder
 */
public class HtmlEncoder {
	
	public static String encode(String s) {
		int len = s.length();

		// first check if there are any characters that need to be escaped
		boolean found = false;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if ("\"&<>".indexOf(c) != -1 || (0xffff & c) >= 160) {
				found = true;
				break;
			}
		}
		if (!found) return s;
		
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == '"') {
				sb.append("&quot;");
			} else if (c == '&') {
				sb.append("&amp;");
			} else if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else {
				int ci = 0xffff & c;
				if (ci < 160 ) {
					// nothing special only 7 Bit
					sb.append(c);
				} else {
					// Not 7 Bit use the unicode system
					sb.append("&#").append(Integer.toString(ci)).append(';');
				}
			}
		}
		return sb.toString();
	}
}
