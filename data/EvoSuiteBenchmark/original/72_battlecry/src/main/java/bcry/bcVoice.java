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

import java.util.*;
import java.util.List.*;

/* THE VOICE OF BATTLECRY
 * This class handles all output, either to the standard output or an instance
 * of a frontend class.
 */
class bcVoice {

   private battlecryGUI gui = null;
   private List lyrics;
   private boolean verbose = false;
   private boolean lyricsToScreen = true;

   /* CLASS CONSTRUCTOR */
   public bcVoice(boolean[] options, battlecryGUI g) {
      lyrics = new LinkedList();
      gui = g;
      verbose = options[0];
      lyricsToScreen = options[1];
      verbose("Voice initialized.");
   }

   // ---------------- OUTPUT METHODS ----------------
      
   /* sing
    * This method displays strings or string arrays on the screen, followed by an empty line.
    * As the name indicates, it is used for lyrics, while sysout is used for system messages.
    */
   public void sing(String[] text) {
      for (int i = 0; i < text.length; i++) {
         if (lyricsToScreen) {System.out.println(text[i]);}
         else {lyrics.add(text[i]);}
      }
      if (lyricsToScreen) {System.out.println("");}
      else {lyrics.add("");}
   }

   public void sing(String text) {
      if (lyricsToScreen) {System.out.println(text);}
      else {lyrics.add(text);}
   }

   /* sysout
    * This method displays strings or string arrays on the screen, or on the GUI,
    * depending on the value of the lyricsToScreen variable.
    * You must pass a valid GUI to the class constructor for this to work;
    * a "valid GUI" is any class that has a public method called "updateLog(String t)".
    */
   public void sysout(String[] text) {
      for (int i = 0; i < text.length; i++) {
         if (lyricsToScreen || (gui == null)) {System.out.println(text[i]);}
         else {gui.updateLog(text[i]);}
      }
      if (lyricsToScreen) {System.out.println("");}
   }

   public void sysout(String text) {
      if (lyricsToScreen || (gui == null)) {System.out.println(text);}
      else {
         gui.updateLog(text+'\n');
      }
   }

   /* verbose
    * Prints a message on the screen if verbose switch is set.
    * Used for system messages that should only appear in verbose mode.
    */
   public void verbose(String text) {
      if (verbose) {sysout("Info: "+text);}
   }
   
   /* verboseLineFeed
    * Prints an empty line on the text console when in verbose mode
    * (Used to separate verbose messages from actual lyrics.)
    */
   public void verboseLineFeed() {
      if (verbose && lyricsToScreen) {sysout("");}
   }

   /* progressBarStart
    * Prints some text to start a progress bar with.
    */
   public void progressBarStart(String text) {
      progressBarDot("Info: "+text);
   }
   
   /* progressBarDot
    * Prints a progress bar dot.
    */
   public void progressBarDot(String text) {
      if (verbose && !lyricsToScreen && !(gui == null)) {gui.updateLog(text);}
   }
   
   /* progressBarEnd
    * Prints a text that marks the end of a progress bar.
    */
   public void progressBarEnd(String text) {
      if (verbose) {sysout(text);}
   }

   /* getLyrics
    * [self-explanatory]
    */
   public List getLyrics() {
      return lyrics;
   }
   
   /* resetLyrics
    * resets the lyrics string list to prepare for a new set of lyrics.
    */
   public void resetLyrics() {
      lyrics = new LinkedList();
   }
}
