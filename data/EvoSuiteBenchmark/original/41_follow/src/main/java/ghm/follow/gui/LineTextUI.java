package ghm.follow.gui;

import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;

/**
 * UI implementation that highlights the line where the caret is found.
 * 
 * @author Carl Hall
 */
public class LineTextUI extends BasicTextAreaUI
{
	private transient Logger log = Logger.getLogger(LineTextUI.class.getName());
	int selectedIndex = -1;
	private JTextComponent comp;

	public View create(Element elem)
	{
		return new LineView(elem);
	}

	public void installUI(JComponent c)
	{
		comp = (JTextComponent) c;
		// install listener if we should highlight the current line

		// if (SyntaxSupport.getInstance().getShouldHighlightCurrentLine()) {
		comp.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent e)
			{
				Document doc = comp.getDocument();
				Element map = doc.getDefaultRootElement();
				int index = map.getElementIndex(e.getDot());

				if (selectedIndex > -1)
				{
					try
					{
						// unhighlight previous Selected line
						Element previous = map.getElement(selectedIndex);
						if (previous != null)
						{
							Rectangle rec = comp.modelToView(previous.getStartOffset());
							if (rec != null)
							{
								rec.width = comp.getWidth();
								comp.repaint(rec);
							}
						}
					}
					catch (BadLocationException e1)
					{
						log.log(Level.SEVERE, "BadLocationException in LineTextUI", e1);
					}
				}
				if (comp.getSelectionStart() == comp.getSelectionEnd())
				{
					selectedIndex = index;
				}
				else
				{
					selectedIndex = -1;
				}

				// highlight current
				Element selected = map.getElement(index);
				damageRange(comp, selected.getStartOffset(), selected.getEndOffset() - 1);
			}
		});
		super.installUI(c);
	}

	public int getSelectedIndex()
	{
		return selectedIndex;
	}
}