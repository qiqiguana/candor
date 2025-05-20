package org.heal.tag;

import org.heal.util.DateTools;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

/**
 * A tag to display dates in a standard format, as well as allowing the
 * option of specifying a custom format.
 */
public class FormatDateTag extends TagSupport {
	private String format = DateTools.DEFAULT_DATE_FORMAT;
	private Date date = new Date();

	public void setFormat(String format) {
		this.format = format;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int doEndTag() {
		try {
        	pageContext.getOut().print((null != date ? DateTools.format(date, format) : new String()));
		} catch(IOException e) {
		}
		return EVAL_PAGE;
	}
}
