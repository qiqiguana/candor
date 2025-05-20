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
// Copyright 2003 Ken Harris <kbh7@cornell.edu>
//

package corina.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import corina.core.App;
import corina.util.StringUtils;

/**
   Provide localization strings.

   <p>Java's ResourceBundles are intended to do this, but they don't
   provide the level of support that most other libraries do.</p>

   <p>For example, suppose you have a "Copy" menuitem.
<pre>
   JMenuItem copy = new JMenuItem("Copy");
</pre>

   Of course, you should internationalize this.  But you want to
   change the text, the mnemonic, and the keyboard accelerator.  If
   you only had ResourceBundle to work with, you might end up with 3
   lines of translation for every word in your program.  Plus, you'd
   have to know the name of the I18n file you used, each time you
   wanted to use it.  Most other libraries (like Powerplant on Mac and
   Win95 resources) let you put them all in one line, which makes the
   translator's job much easier.  That's what this class does.</p>

   <p>Now, all you have to say is:
<pre>
   copy = &amp;Copy [accel C]
</pre>

   <p>There are several things going on here:</p>

   <ul>

     <li>"copy" is the <i>key</i>.  When you want to refer to this
     entry, you'll ask for it by this name.  The user never sees this.

     <li>"Copy" (without the &amp;) is the <i>text</i> of this key.
     This is what users will see in menuitems, buttons, and so forth.

     <li>"C" (the thing right after the &amp;) is the <i>mnemonic</i>.
     On most platforms (all except Mac), this letter is underlined,
     and users can jump to it by pressing this letter, or Alt and this
     letter (depending on platform and context).

     <li>"accel C" is the <i>keystroke</i>.  The user can press this
     at any time to invoke this command.  You can use the modifiers
     "shift", "control", "alt", and "meta", or the special modifier
     "accel".  "accel" is automatically turned into "control" on PCs
     (Windows and Linux), and "meta" (Java's term for "command") on
     Macs.  You should normally use the generic "accel" modifier.  You
     can also combine modifiers, like "shift accel S" (the standard
     keystroke for "Save As...").  The class javax.swing.KeyStroke
     takes these strings as input: you can say
<pre>
   KeyStroke.getKeyStroke(I18n.getKeyStroke("copy"))
</pre>
     to get a Swing KeyStroke object.

   </ul>

   <p>This is convenient, but it gets even better: normally, you don't
   even have to mess with keystrokes and mnemonics.  You can simply
   use the Builder factory to do the dirty work for you:

<pre>
   JMenuItem copy = Builder.makeMenuItem("copy");
</pre>

   Of course, if you just want the text (for making a printout, for
   example), you should still use I18n.getText().</p>

   <p>Not all keys must have a keystroke, or a mnemonic.  Note that
   those methods can return nulls.</p>

   <p>These methods get their values from the resource bundle called
   "TextBundle".  That is, the file is called
   "TextBundle.properties", or some variant, like
   "TextBundle_de_DE.properties".</p>

   <h2>Left to do</h2>
   <ul>
     <li>The way getMnemonic() is set up, "Save &as..." will
         still show it as "S&ave as...".  Bad, but not fatal.

     <li>Add way to escape [/]/&amp; in text; also, add markers so
         "this text is mnemonic/text/keystroke" and "this text is
	 just a string" are separate(?)

     <li>in getKeyStroke(), why not use
         Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() instead of
	 (?:) for command/control choice?
   </ul>

   @see java.util.ResourceBundle

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: I18n.java,v 1.4 2005/01/24 03:09:34 aaron Exp $
*/
public class I18n {

    private I18n() {
	// don't instantiate me
    }

    /**
       Get the text for this key.  The text has no special control
       characters in it, and can be presented to the user.

       <p>For example, if the localization file has the line
       <code>copy = &amp;Copy [accel C]</code>, the string "Copy" is
       returned.</p>

       @param key the key to look up in the localization file
       @return the text
    */
    public static String getText(String key) {
	String value = msg.getString(key);

	StringBuffer buf = new StringBuffer();

	int n = value.length();
	boolean ignore = false;
	for (int i=0; i<n; i++) {
	    char c = value.charAt(i);
	    switch (c) {
	    case '&': continue;
	    case '[': ignore = true; break;
	    case ']': ignore = false; break;
	    default:
		if (!ignore)
		    buf.append(c);
	    }
	}

	return buf.toString().trim();
    }

    /**
       Get the keystroke string for this key.  This string can be
       passed directly to the Keystroke.getKeyStroke() method.

       <p>For example, if the localization file has the line
       <code>copy = &amp;Copy [accel C]</code>, the string "control C"
       is returned (or on the Mac, "meta C").</p>

       <p>If the string has no [keystroke] listed, null is
       returned.</p>

       @param key the key to look up in the localization file
       @return the keystroke string
    */
    public static String getKeyStroke(String key) {
	String value = msg.getString(key);

	int left = value.indexOf('[');
	int right = value.indexOf(']');

	if (left==-1 || right==-1)
	    return null;

	String stroke = value.substring(left+1, right).trim();

	// accel = command (in java-ese: "meta") on mac, control on pc
	String accel = (App.platform.isMac() ? "meta" : "control");
	stroke = StringUtils.substitute(stroke, "accel", accel);

	return stroke;
    }

    /**
       Get the mnemonic character this key.

       <p>For example, if the localization file has the line
       <code>copy = &amp;Copy [accel C]</code>, the character "C" is
       returned.</p>

       <p>If the string has no &amp;mnemonic listed, null is
       returned.</p>

       @param key the key to look up in the localization file
       @return the mnemonic character
    */
    public static Character getMnemonic(String key) {
	String value = msg.getString(key);

	int amp = value.indexOf('&');

	if (amp==-1 || amp==value.length()-1)
	    return null;

	return new Character(Character.toUpperCase(value.charAt(amp+1)));
    }

    // the resource bundle to use
    private final static ResourceBundle msg;
    
    static {
      ResourceBundle bundle;
      try {
        bundle = ResourceBundle.getBundle("Translations/TextBundle");
      } catch (MissingResourceException mre) {
        try {
          bundle = ResourceBundle.getBundle("TextBundle");
        } catch (MissingResourceException mre2) {
          mre2.printStackTrace();
          bundle = new DefaultResourceBundle();
        }
      }
      msg = bundle;
    }
}
