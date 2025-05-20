/* Battlecry V0.1
 * Copyright (C) 2003  Marek "Wansti" Moeckel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package bcry;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.util.List.*;


/* BATTLECRY LIVE
 * Battlecry Live is yet another interface to the Battlecry Generator -
 * It is a simple applet that lets you use Battlecry directly on a web page.
 */
public class battlecryLive extends Applet implements ActionListener {
  
  private java.util.List lyrics = new LinkedList();
  private boolean[] options = new boolean[2];
  private bcGenerator bc;
  private bcVoice voice;
  private bcModule mod;
  private Button genBtn;
  private TextArea output;
  
  public void init() {
     options[0] = false; //Verbose Mode Off
     options[1] = false; //Lyrics to Console Off
     voice = new bcVoice(options, null);
     mod = new bcModule("DEMO","",voice);
     bc = new bcGenerator(mod,voice);
             
     setLayout(new BorderLayout()); 
     genBtn = new Button("  Generate!  ");
     output = new TextArea();
     output.setBackground(new Color(155, 155, 200));
     output.setEditable(false);
     add("Center",output);
     add("North",genBtn);
     genBtn.addActionListener(this);
     setBackground(new Color(100, 100, 210));
     welcome();
  }
  
  public void actionPerformed(ActionEvent event) { 
     if (event.getSource() == genBtn) {  
       genBtn.setLabel("Generating, please wait...");
       generate();
       genBtn.setLabel("Again!");
     }
  }

  private void welcome() {
   output.setText(""+'\n');
   output.append(""+'\n');
   output.append(""+'\n');
   output.append("Welcome to Battlecry Live!"+'\n');
   output.append("Click the button to generate lyrics."+'\n');
   output.append("This may take a few seconds, please be patient..."+'\n');
   output.append(""+'\n');
   output.append(""+'\n');
   output.append(""+'\n');
   output.append(""+'\n');
   output.append("Written by Marek \"Wansti\" Moeckel"+'\n');
   output.append("http://battlecry.wansti.de"+'\n');
  }
  
  private void generate() {
    lyrics = bc.getLyrics();
    output.setText("");     
    for (int i = 0; i < lyrics.size(); i++) {
      output.append((String)lyrics.get(i)+'\n');
    }
  }
  
}
