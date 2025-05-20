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


/* BATTLECRY RHYME DICTIONARY MANAGER
 * This class manages the word database that is used to determine rhymes and syllable count
 */
import java.io.*;
import java.util.Hashtable;

class bcDictionary {

   private Hashtable database;
   private bcVoice voice;

   public bcDictionary(String dictfile, bcVoice v) {
      database = new Hashtable();
      voice = v;
      loadDict(dictfile);
   }

   public int getSize() {
      return database.size();
   }
          
   public String getPhonemes(String[] words) {
      String result = "";
      for (int i = 0; i < words.length-1; i++) {
         result += getPhonemes(words[i]) + " ";
      }
      result += getPhonemes(words[words.length-1]);
      return result;
   }
   
   public String getPhonemes(String word) {
      boolean test = false;
      String result = "";
      if (word.indexOf(" ") != -1) {   //if String contains more than one word, process them one by one
         String[] temp = word.split(" ");
         for (int i=0; i<temp.length-1; i++) {
            if (database.get(temp[i].toUpperCase()) != null) {
               result += database.get(temp[i].toUpperCase()).toString()+" ";
            }
         }
         test = true;
         if (database.get(temp[temp.length-1].toUpperCase()) != null) {
               result += database.get(temp[temp.length-1].toUpperCase()).toString();
         }
      }
      else if (database.get(word.toUpperCase()) != null) {
         result = database.get(word.toUpperCase()).toString();
      }
      return result;
   }
   
   /* loadDict
    * Loads the Dictionary from an ASCII file and creates the word database.
    */
   public void loadDict(String filename) {
      voice.verbose("Loading dictionary file, please wait...");
      String line = "";
      int blank = 0;
      try {
         BufferedReader br = new BufferedReader(new FileReader(filename));
         while ((line = br.readLine()) != null) {
            if ((!line.startsWith("##")) && (!line.equals(""))) {
               blank = line.indexOf(" ");
               database.put(line.substring(0,blank), line.substring(blank+2));
            }
         }
         br.close();
      }
      catch (IOException e) {System.out.println(e.toString());}
      voice.verbose("Dictionary ready.");
   }
   
   /* saveDB
    * Writes the database to a file.
    * TODO: Reduce saving time
    */
   public void saveDB(String filename) {
      System.out.print("Saving database...");
      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
         oos.writeObject(database);
         oos.close();
      }
      catch (Exception e) {System.out.println(e.toString());}
      System.out.println("done.");
   }
   
   /* loadDB
    * Loads a database from a file.
    * TODO: Reduce loading time
    */
   public void loadDB(String filename) {
      System.out.print("Loading database...");
      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
         database = (Hashtable)ois.readObject();
         ois.close();
      }
      catch (Exception e) {System.out.println(e.toString());}
      System.out.println("done.");       
   }
     
}
