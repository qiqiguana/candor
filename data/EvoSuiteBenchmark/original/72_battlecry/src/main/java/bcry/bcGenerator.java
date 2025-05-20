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

/* THE BATTLECRY GENERATOR
 * This class is the main part of Battlecry.
 * It initializes a module and generates the lyrics with its information.
 */
class bcGenerator {

   private final String GRAMMAR_START = "START";
   private final String GRAMMAR_TITLE = "TITLE";
   
   private Random r = new Random();
   private int currentSyllables = 0;
   private String rSeg = "";
   
   private bcModule module;
   private bcVoice voice;

   
   /* CLASS CONSTRUCTOR */
   public bcGenerator(bcModule m, bcVoice v) {
      voice = v;
      module = m;
   }

   // ---------------- LYRICS CREATION METHODS ----------------

   /* parseGrammar
    * Parses the information stores in the grammar properties and
    * generates a random string which then can be parsed by fill() to
    * create a line of lyrics.
    */
   private String parseGrammar(String segment) {

      String output = "";
      try {
         String[] temp;
         
         //Get segment from Properties and look for brackets
         String seg = replaceBrackets(module.getGrammar().getProperty(segment,segment));
            
         //Split between semicolons
         if (seg.indexOf(";") == -1) {
            temp = new String[1];
            temp[0] = seg.substring(1,seg.length()-1);
         }
         else {
            temp = seg.substring(1,seg.length()-1).split(";");
         }

         for (int i = 0; i < temp.length; i++) {
            //LOOK FOR "OR", randomize if found
            if (temp[i].indexOf("#") != -1) {
               String[] temp2 = temp[i].split("#");
               temp[i] = temp2[r.nextInt(temp2.length)];
            }
            //FOUND BRACKETED SUBSEGMENT; change back the AND's and OR's and parse recursively
            if ((temp[i].startsWith("(")) && (temp[i].endsWith(")"))) {
               temp[i] = temp[i].replace('&',';');
               temp[i] = temp[i].replace('|','#');
               temp[i] = "{" + temp[i].substring(1,temp[i].length()-1) + "}";
               output += parseGrammar(temp[i]);
               if (i < temp.length-1) {output += "@";}
            }
            //FOUND OPTIONAL BRACKETED SUBSEGMENT; change back the AND's and OR's and parse recursively
            else if ((temp[i].startsWith("*(")) && (temp[i].endsWith(")"))) {
               if (r.nextInt(2) == 1) {
                 temp[i] = temp[i].replace('&',';');
                 temp[i] = temp[i].replace('|','#');
                 temp[i] = "{" + temp[i].substring(2,temp[i].length()-1) + "}";
                 output += parseGrammar(temp[i]);
                 if (i < temp.length-1) {output += "@";}
               }
            }
            //FOUND STRING
            else if ((temp[i].startsWith("'")) && (temp[i].endsWith("'"))) {
               if (isWord(temp[i].substring(1,temp[i].length()-1))) {
                  currentSyllables += getWordFromLists(temp[i].substring(1,temp[i].length()-1)).getSyllables();
               }
               output += temp[i];
               if (i < temp.length-1) {output += "@";}
               
            }
            //FOUND OPTIONAL STRING
            else if ((temp[i].startsWith("*'")) && (temp[i].endsWith("'"))) {
               if (r.nextInt(2) == 1) {
                 if (isWord(temp[i].substring(2,temp[i].length()-1))) {
                    currentSyllables += getWordFromLists(temp[i].substring(2,temp[i].length()-1)).getSyllables();
                 }
                 output += temp[i].substring(1,temp[i].length());
                 if (i < temp.length-1) {output += "@";}                
               }
            }
            //FOUND SEGMENT; parse recursively
            else if ((temp[i].startsWith("[")) && (temp[i].endsWith("]"))) {
               output += parseGrammar(temp[i].substring(1,temp[i].length()-1));
               if (i < temp.length-1) {output += "@";}
            }
            //FOUND OPTIONAL SEGMENT
            else if ((temp[i].startsWith("*[")) && (temp[i].endsWith("]"))) {
               if (r.nextInt(2) == 1) {
                  output += parseGrammar(temp[i].substring(2,temp[i].length()-1));
                  if (i < temp.length-1) {output += "@";}
               }
            }
            //FOUND OPTIONAL WORD FROM A LIST
            else if (temp[i].startsWith("*")) {
               if (r.nextInt(2) == 1) {
                  output += temp[i].substring(1,temp[i].length());
                  if (i < temp.length-1) {output += "@";}
               }
            }
            //FOUND WORD FROM A LIST
            else {
               output += temp[i].substring(0,temp[i].length());
               if (i < temp.length-1) {output += "@";}
            }
         }
      }
      catch (Exception e) {
         voice.sysout("Error: Missing or illegal expression in grammar.dat - Segment:"+segment);
         voice.sysout(e.toString());
      }
      //cut "@" at the end of string
      if (output.endsWith("@")) {output = output.substring(0,output.length()-1);}
      return output;
   }

   /* parseLayout
    * Determines the song's structure according to the layout data and
    * generates the lyrics.
    */
   private void parseLayout() {

      String[] temp;
      String[] chorus = null;
      int lines;

      for (int i = 0; i < module.getLayout().size(); i++) {
         if (!(((String)module.getLayout().get(i)).indexOf(":") == -1)) {
            temp = ((String)module.getLayout().get(i)).split(":");
            if (temp[0].equals("lines")) {
               lines = Integer.parseInt(temp[1]);
               voice.sing(makeLines(lines));
            }
            else if (temp[0].equals("verse")) {
               voice.sing(makeChorus(temp[1]));
            }
            else if (temp[0].equals("chorus")) {
               voice.sing("[Chorus]");
               if (chorus == null) {
                  chorus = makeChorus(temp[1]);
               }
               voice.sing(chorus);
            }
            else if (temp[0].equals("bridge")) {
               lines = Integer.parseInt(temp[1]);
               voice.sing(makeBridge(lines));
            }
         }
         else {
            voice.sing(fill(parseGrammar((String)module.getLayout().get(i))));
         }
      }
   }

   /* xLiner
    * Creates two or three random rhyming lines with the same syllable count (more or less)
    * and returns them as an array.
    * "number" can be either 2 or 3.
    */
   private String[] xLiner(int number) { 
      
      //first, make sure we have a valid argument
      if (number <= 2) {number = 2;} else {number = 3;}
      
      String[] result = new String[number];
      String[] lastSegs = new String[number-1];
      String lastWord = "";
      String tempWord = "";      
      int[] tempSyls = new int[number];
      int tempSyl = 0;
      boolean match;
      bcWordList lst = null;
      bcWord current = null;
      
      //first loop: first and second line; second loop: second and third line (if there is one)
      for (int l=0; l<=number-2; l++) {         
         do {
            match = true;
                 
            if (l == 0) {
               //Generate the first line of text, only in the first run of the l-loop
               currentSyllables = 0;
               result[0] = fill(parseGrammar(GRAMMAR_START));
               lastWord = getLastWord(result[0]);
               tempSyls[0] = currentSyllables;
               currentSyllables = 0;
            }
            else {
               lastWord = getLastWord(result[1]);
            }
         
            //Get a second line from parseGrammar and cut off their last word, which is left blank
            result[l+1] = parseGrammar(GRAMMAR_START);
            if (result[l+1].indexOf("@") == -1) {
               //last word is only word
               lastSegs[l] = result[l+1];
               result[l+1] = "";
               tempSyls[l+1] = currentSyllables = 0;
            }
            else {
               //find last word and cut it off
               for (int j = result[l+1].length()-1; j >= 0; j--) {
                  if (result[l+1].charAt(j) == '@') {
                     lastSegs[l] = result[l+1].substring(j+1);
                     result[l+1] = fill(result[l+1].substring(0,j));
                     tempSyls[l+1] = currentSyllables;
                     currentSyllables = 0;
                     break;
                  }
               }
            }        
            
            //finish second line
            if (lastSegs[l].startsWith("'") && lastSegs[l].endsWith("'")) {
               //last word is a string, test if it rhymes with first line
               tempWord = lastSegs[l].substring(1,lastSegs[l].length()-1);
               tempSyl = getWordFromLists(tempWord).getSyllables();
               if ((rhyming(tempWord,lastWord) < 1) ||
                 !(tempSyl+tempSyls[l+1] <= tempSyls[0]+module.getSyllableTolerance()) ||
                 !(tempSyl+tempSyls[l+1] >= tempSyls[0]-module.getSyllableTolerance())) {
                  match = false;
               }
               else {
                  //complete second line
                  result[l+1] += " " + tempWord;
                  tempSyls[l+1] += tempSyl;
               }
            }
            else {
               //create list of possible last words, choose one at random
               lst = module.getWordList(lastSegs[l]);            
               List possibleMatches = new LinkedList();
               for (int j = 0; j < lst.getNumberOfWords(); j++) {
                  current = lst.getItem(j);
                  tempWord = current.getWord();
                  if ((rhyming(tempWord,lastWord) == 1) &&
                      (current.getSyllables()+tempSyls[l+1] <= tempSyls[0]+module.getSyllableTolerance()) &&
                      (current.getSyllables()+tempSyls[l+1] >= tempSyls[0]-module.getSyllableTolerance())) {
                     possibleMatches.add(current);
                  }
               }
               if (possibleMatches.size() == 0) {
                  //no word from the list can make a match
                  match = false;              
               }
               else {
                  //one or more possible matches found, choose one at random
                  current = (bcWord)possibleMatches.get(r.nextInt(possibleMatches.size()));
                  tempWord = current.getWord();
                  result[l+1] += " " + tempWord;
                  tempSyls[l+1] += current.getSyllables();
               }
            }
            if ((module.getSyllableTolerance() == 0) && (!result[l+1].equals("")) && (!metricMatch(result[0], result[l+1]))) {
               //Only test metrum if Syllable Tolerance is zero
               //TODO: Find out why result[l] can be empty string
               match = false;
            }
         } //end do
         while (!match);
      } //end for    
      return result;
   } 

   /* makeBridge
    * Creates a bunch of rhyming TwoLiners according to the given chorus length in the layout
    * file. Odd line numbers will be increased by 1.
    */
   private String[] makeBridge(int lines) {

      String[] result = new String[lines];
      String[] temp;
      if (lines % 2 != 0) {lines += 1;}
      int i;
      for (i = 0; i < lines; i++) {
         temp = xLiner(2);
         result[i] = temp[0];
         i++;
         result[i] = temp[1];
      }
      return result;
   }

   /* makeChorus
    * It generates a bunch of rhyming lines according to a given scheme,
    * for example "aabb" or "ababcdcd".
    * Letters a-e can be used; each letter must not appear more than three times.
    * If you use complex schemes, make sure you have enough rhyming words!
    */
   private String[] makeChorus(String scheme) {

      String[] result = new String[scheme.length()];
      String[][] schemes = new String[5][3];
      scheme = scheme.toLowerCase();
      int[] count = new int[5];
      boolean rhyming;
      
      for (int i = 0; i < 5; i++) {
         schemes[i] = new String[3];
         for (int j = 0; j < 3; j++) {schemes[i][j] = "";}
         count[i] = 0;
      }
      
      //Count letters a-e
      for (int i = 0; i < scheme.length(); i++) {
         if (scheme.substring(i).startsWith("a")) {count[0]++;}
         else if (scheme.substring(i).startsWith("b")) {count[1]++;}
         else if (scheme.substring(i).startsWith("c")) {count[2]++;}
         else if (scheme.substring(i).startsWith("d")) {count[3]++;}
         else if (scheme.substring(i).startsWith("e")) {count[4]++;}
      }
      
      //Start building a scheme
      do {
         rhyming = false;
         for (int i = 0; i < 5; i++) {
            if (count[i] == 1) {
               schemes[i][0] = fill(parseGrammar(GRAMMAR_START));
            }
            else if (count[i] >= 2) {
               schemes[i] = xLiner(count[i]);
            }
            for (int j = 0; j <= i; j++) {
               if ((i != j) && 
                  ((rhyming(getLastWord(schemes[i][0]),schemes[j][0]) == 1) || 
                  (rhyming(getLastWord(schemes[i][0]),schemes[j][0]) == -2))) {
                  rhyming = true;
               }
            }
         }       
         voice.progressBarDot(".");
      }
      while (rhyming);
      
      //Good scheme found, now arrange it
      for (int i = 0; i < 5; i++) {count[i] = 0;}
      for (int i = 0; i < scheme.length(); i++) {
         if (scheme.substring(i).startsWith("a")) {result[i] = schemes[0][count[0]]; count[0]++;}
         else if (scheme.substring(i).startsWith("b")) {result[i] = schemes[1][count[1]]; count[1]++;}
         else if (scheme.substring(i).startsWith("c")) {result[i] = schemes[2][count[2]]; count[2]++;}
         else if (scheme.substring(i).startsWith("d")) {result[i] = schemes[3][count[3]]; count[3]++;}
         else if (scheme.substring(i).startsWith("e")) {result[i] = schemes[4][count[4]]; count[4]++;}
      }
      return result;
   }



   /* makeLines
    * Creates a bunch of (non-rhyming) random lines.
    */
   private String[] makeLines(int lines) {

      String[] result = new String[lines];
      for (int i = 0; i < lines; i++) {
         currentSyllables = 0;
         result[i] = fill(parseGrammar(GRAMMAR_START));
      }
      return result;
   }
   

   // ---------------- AUXILIARY METHODS ----------------

   /* fill()
    * Fills the string generated by parseGrammar with words.
    */
   private String fill(String seg) {
      String result = "";
      if (seg.indexOf("@") == -1) {
         if (seg.startsWith("'") && seg.endsWith("'")) {
            result = seg.substring(1,seg.length()-1);
         }
         else {
            result = getRandomWord(seg);
         }
      }
      else {
         String[] segs = seg.split("@");
         for (int i=0; i<segs.length; i++) {
            if (segs[i].startsWith("'") && segs[i].endsWith("'")) {
               result += segs[i].substring(1,segs[i].length()-1);
            }
            else {
               if (!segs[i].equals("")) {result += getRandomWord(segs[i]);}
            }
            if (i<segs.length-1) {result += " ";}  
         }
      }
      return result;
   }

   /* getRandomWord
    * Returns a random word from the given word list.
    * Warning: Used by parseGrammar only; might f**k up the syllable count if used elsewhere!
    */
   private String getRandomWord(String listName) {

      String result = "[LIST NOT FOUND]";
      for (int i = 0; i < module.getWordLists().size(); i++) {
         if (((bcWordList)module.getWordLists().get(i)).getFileName().equals(listName)) {
            result = ((bcWordList)module.getWordLists().get(i)).getRandomItem().getWord();
            currentSyllables += getWordFromLists(result).getSyllables();
            break;
         }
      }
      if (result.equals("[LIST NOT FOUND]")) {voice.sysout("Error: Word list '"+listName+"' not found, check data files!");}
      return result;
   }
   
   /* getWordFromLists
    * Returns a given word from any of the word lists.
    * This returns a bcWord object, not just a string!
    */
   private bcWord getWordFromLists(String word) {      
      bcWord result = null;
      for (int i = 0; i < module.getWordLists().size(); i++) {
         if (((bcWordList)module.getWordLists().get(i)).getItem(word) != null) {
            result = ((bcWordList)module.getWordLists().get(i)).getItem(word);
            break;
         }
      }
      if (result == null) voice.sysout("Warning: Word not listed: '"+word+"'");
      return result;
   }

   /* getLast Word
    * Analyzes a line of text and returns its last word
    * This is needed to find out if two lines are rhyming.
    */
   private String getLastWord(String line) {
      String result = line;
      if (line.indexOf(" ") > 0) {
         String[] temp = line.split(" ");
         int i = 0;
         do {i++; result = temp[temp.length-i];}
            while (((result.equals("!")) || (result.equals(""))) && (i >= temp.length));
      }
      return result;
   }

   /* rhyming
    * Checks if two lines rhyme
    * returns 0 if they don't rhyme, 1 if they do; -1 if last words are not found in list or empty, -2 if they are equal
    */
   private int rhyming(String lineA, String lineB) {

      //TODO: Don't rhyme equal words set in word lists
      int result = 0;
      lineA = getLastWord(lineA.toLowerCase());
      lineB = getLastWord(lineB.toLowerCase());
      if ((lineA.equals("")) || (lineB.equals(""))) {
         result = -1;
      }
      else if (lineA.equals(lineB)) {
         result = -2;
      }
      else if (rhymingAux(lineA,lineB)) {
         result = 1;
      }
      else {
         result = 0;   
      }
      return result;
   }
   
   /* rhymingAux
    * sub-method of rhyming, used only there
    */
   private boolean rhymingAux(String wordA, String wordB) {
      boolean last = false;
      bcWord A = getWordFromLists(wordA);
      bcWord B = getWordFromLists(wordB);
      if ((A.getSyllables() == 1) || (B.getSyllables() == 1)) {
         last = true;
      }
      //replace some phonemes that are commonly accepted as rhyming
      wordA = A.getRhymeKey(last).replaceAll("AE","EH");
      wordB = B.getRhymeKey(last).replaceAll("AE","EH");
      return wordA.equals(wordB);
      
   }
   
   /* metricMatch()
    * Checks if two lines of text have the same metric scheme
    * TODO: implement properly to make it less restrictive
    */
   private boolean metricMatch(String lineA, String lineB) {
      boolean result = true;
      int count = 0;
      char A;
      char B;
      lineA = metricCode(lineA);
      lineB = metricCode(lineB);
      if (lineA.length() == lineB.length()) {
         for (int i = 0; i < lineA.length(); i++) {           
            A = lineA.charAt(i);
            B = lineB.charAt(i);
            if (((A=='0') && (B=='1')) || ((A=='1') && (B=='0'))) {
               result = false;
            }
            else {
               if (  ((A=='O') && ((B=='1') || (B=='I')))
                  || ((A=='I') && ((B=='0') || (B=='O')))
                  || ((A=='0') &&  (B=='I'))
                  || ((A=='1') &&  (B=='O'))) {
                  count++;
               }
            }
         }
         if (count > module.getMetricTolerance()) result = false;
      }
      else {result = false;}
      return result;  
   }
   
   /* metricCode()
    * returns the metric code of one or more words
    */
   private String metricCode(String line) {
      String metrum = "";
      if (line.indexOf(" ") == -1) {
         metrum += getWordFromLists(line).getMetricCode();
      }
      else {
         String[] temp = line.split(" ");
         for (int j = 0; j < temp.length; j++) {
            metrum += getWordFromLists(temp[j]).getMetricCode();
         }
      }
      return metrum;
   }

   /* replaceBrackets
    * Used by parseGrammar to handle brackets in grammatical segments
    * This method replaces AND's and OR's inside brackets so they won't get split
    * I know it's a little hackish, I'll think of a better way to do it.
    */
   private String replaceBrackets(String segment) {
      if ((segment.indexOf("(") != -1) && (segment.indexOf(")") != -1)) {
         String brk1 = segment.substring(0,segment.indexOf("("));
         String brk2 = segment.substring(segment.indexOf("("),segment.indexOf(")"));
         String brk3 = segment.substring(segment.indexOf(")")+1,segment.length());
         brk2 = brk2.replace(';','&');
         brk2 = brk2.replace('#','|');
         segment = brk1+brk2+")"+replaceBrackets(brk3);
      }
      return segment;
   }
   
   /* isWord
    * determines if a string is a valid word without commas, brackets, etc
    */
   private boolean isWord(String word) {
      return ((word.indexOf(",") == -1) && 
              (word.indexOf(";") == -1) && 
              (word.indexOf(".") == -1) && 
              (word.indexOf("(") == -1) &&
              (word.indexOf(")") == -1) && 
              (word.indexOf("'") == -1) && 
              (word.indexOf("*") == -1));
   }
   
   // ---------------- PUBLIC METHODS ----------------


   /* getLyrics
    * Creates a random set of lyrics as defined by the data file and
    * returns them as a List.
    */
   public List getLyrics() {
      if ((module != null) && (module.isInitialized())) {
         voice.progressBarStart("Generating, please wait...");
         voice.resetLyrics();
         voice.verboseLineFeed();
         voice.sing("*******************************************");
         voice.sing("*             "+fill(parseGrammar(GRAMMAR_TITLE))+"             *");
         voice.sing("*******************************************");
         parseLayout();
         voice.sing("*******************************************");
         voice.progressBarEnd("done.");
      }
      else {
         voice.sysout("Error: Battlecry has not been initialized.");
      }
      return voice.getLyrics();     
   }

   /* getModuleInfo
    * Prints the information from the module's info.dat file.
    */
   public void getModuleInfo() {
      if ((module != null) && (module.isInitialized())) {
         voice.sysout("Module Name: "+module.getInfo().getProperty("NAME"));
         voice.sysout("Author: "+module.getInfo().getProperty("AUTHOR"));
         voice.sysout("Version: "+module.getInfo().getProperty("VERSION"));
         voice.sysout("Comment: "+module.getInfo().getProperty("COMMENT"));
      }
      else {
         voice.sysout("Error: Battlecry has not been initialized.");
      }
   }
}
