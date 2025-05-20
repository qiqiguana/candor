/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.tag.rt.core.ForEachTag;

/**
 * Extends ForEachTag and set var and varStatus property to predefined constants.
 * Port of ASP.NET Control "Repeater" element.
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class RepeaterTag extends ForEachTag {
	
	private static final long serialVersionUID = -9066909361412491431L;
	public static final String VARSTATUS_NAME = "container";
	public static final String VAR_NAME = "item";

	@Override
	protected void prepare() throws JspTagException {
		super.prepare();
		setVar(VAR_NAME);
		setVarStatus(VARSTATUS_NAME);
	}

}
