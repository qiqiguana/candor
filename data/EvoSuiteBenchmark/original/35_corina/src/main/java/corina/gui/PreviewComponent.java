// 
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import corina.Preview;
import corina.core.App;

/**
   A component that displays a Preview.  It displays the title in bold
   text, and then lists each of the items below that, indented and
   preceded by a bullet.

   @see corina.Preview
   @see corina.Previewable

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: PreviewComponent.java,v 1.2 2005/01/24 03:09:35 aaron Exp $
*/
public class PreviewComponent extends JPanel {

    // u2022 is normal bullet, u2023 is an arrow > bullet (which looks
    // cool, but i don't need it)
    // -- java on windows and linux doesn't even support \u2022.
    // so use a hyphen there.
    private static final String BULLET = (App.platform.isMac() ? "\u2023" : "-");
    // FIXME: check Font.canDisplay()?

    /** Given a Preview object, create a component which displays it.
	@param p the Preview to display */
    public PreviewComponent(Preview p) {
	setLayout(new BorderLayout());

	// make a label, bold, with the title.
	JLabel title = new JLabel(p.getTitle());
	title.setFont(title.getFont().deriveFont(Font.BOLD));
	add(title, BorderLayout.NORTH);

	// go west, young man.
	add(Box.createHorizontalStrut(20), BorderLayout.WEST);

	// make entries for other items.
	// WARNING: if items change, things get screwy.  make copy first?
	JPanel center = new JPanel();
	center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
	List items = p.getItems();
	for (int i=0; i<items.size(); i++) {
	    String text = (String) items.get(i);
	    JLabel label = new JLabel(BULLET + " " + text);
	    center.add(label);
	}
	add(center, BorderLayout.CENTER);
    }
}
