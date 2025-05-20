package org.heal.tag;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * A tag to truncate long strings by taking a substring and appending a <code>"..."</code>
 * to the end of them.
 */
public class TruncateStringTag extends TagSupport {
	public static final int MAX_LENGTH = 60;
	public static final String ELLIPSIS = new String("...");

	private String str = new String();
	private int length = MAX_LENGTH;

	public void setString(String str) {
		this.str = str;
	}

	public String getString() {
		return this.str;
	}

	public void setLength(String length) {
		try {
			this.length = Integer.parseInt(length);
		} catch(NumberFormatException e) {
		}
	}

	public String getLength() {
		return String.valueOf(this.length);
	}

	public int doEndTag() {
		String outputStr;
		if(str.length() > (length + ELLIPSIS.length())) {
			outputStr = str.substring(0, length) + ELLIPSIS;
		} else {
			outputStr = str;
		}
		try {
			pageContext.getOut().print(outputStr);
		} catch(IOException e) {
		}
		return EVAL_PAGE;
	}
}
