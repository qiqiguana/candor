/* 
 Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)

 This file is part of Follow (http://follow.sf.net).

 Follow is free software; you can redistribute it and/or modify
 it under the terms of version 2 of the GNU General Public
 License as published by the Free Software Foundation.

 Follow is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Follow; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ghm.follow.io;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * Implementation of {@link OutputDestination} which appends Strings to a {@link JTextComponent}.
 * 
 * @see OutputDestination
 * @see JTextCompnent
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public class JTextComponentDestination extends FilterableOutputDestination
{
	private static final Logger LOG = Logger.getLogger(JTextComponentDestination.class.getName());
	protected JTextComponent comp;
	protected boolean autoPositionCaret;

	/**
	 * Construct a new JTextCompnentDestination.
	 * 
	 * @param jTextPane
	 *            text will be appended to this text area
	 * @param autoPositionCaret
	 *            if true, caret will be automatically moved to the bottom of the text area when
	 *            text is appended
	 */
	public JTextComponentDestination(JTextComponent comp, boolean autoPositionCaret)
	{
		this.comp = comp;
		this.autoPositionCaret = autoPositionCaret;
	}

	public JTextComponent getJTextComponent()
	{
		return comp;
	}

	public void setJTextComponent(JTextComponent comp)
	{
		this.comp = comp;
	}

	/**
	 * Add a filtered view to this destination. Filtered views show only a subset of the total
	 * output based on filter conditions.
	 * 
	 * @since 1.8.0
	 */
	public void addFilteredView()
	{

	}

	/**
	 * Remove a filtered view
	 * 
	 * @since 1.8.0
	 */
	public void removeFilteredView()
	{

	}

	/**
	 * @return whether caret will be automatically moved to the bottom of the text area when text is
	 *         appended
	 */
	public boolean autoPositionCaret()
	{
		return autoPositionCaret;
	}

	/**
	 * @param autoPositionCaret
	 *            if true, caret will be automatically moved to the bottom of the text area when
	 *            text is appended
	 */
	public void setAutoPositionCaret(boolean autoPositionCaret)
	{
		this.autoPositionCaret = autoPositionCaret;
	}

	public void handlePrint(String s)
	{
		try
		{
			comp.getDocument().insertString(comp.getDocument().getLength(), s, null);
			if (autoPositionCaret)
			{
				comp.setCaretPosition(comp.getDocument().getLength());
			}
		}
		catch (BadLocationException e)
		{
			// just ignore, nothing we can do
			LOG.log(Level.SEVERE, "BadLocationException in JTextComponentDestination", e);
		}
	}

	public void clear()
	{
		comp.setText("");
		if (autoPositionCaret)
		{
			comp.setCaretPosition(0);
		}
	}
}