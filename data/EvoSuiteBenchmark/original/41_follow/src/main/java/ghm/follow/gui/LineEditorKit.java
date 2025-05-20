package ghm.follow.gui;

import javax.swing.text.Element;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class LineEditorKit extends StyledEditorKit implements ViewFactory
{
	/**
	 * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
	 */
	public View create(Element elem)
	{
		return new LineView(elem);
	}

	/**
	 * @see javax.swing.text.DefaultEditorKit#getViewFactory()
	 */
	public ViewFactory getViewFactory()
	{
		return this;
	}
}