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


/* BATTLECRY WORD LIST
 * This class stores bcWord objects which hold data from the word files.
 */
import java.io.*;
import java.util.Hashtable;
import java.util.Random;
import java.util.Enumeration;

class bcWordList {

   private Hashtable list;
   private String filename;
   private Random r = new Random();

   public bcWordList(String f) {
      filename = f;
      list = new Hashtable();
   }

   public int getNumberOfWords() {
      return list.size();
   }

   public bcWord getRandomItem() {
      return this.getItem(r.nextInt(1+list.size()));
    }

   public bcWord getItem(int n) {
      bcWord result = null;
      Enumeration e = list.elements();
      int i = 0;
      while ((e.hasMoreElements()) && (i <= n)) {
         result = (bcWord)e.nextElement();
         if (i == n) {break;}
         i++;
      }
      return result;
   }

   public bcWord getItem(String n) {
      return (bcWord)list.get(n);
   }

   public void addWord(String w, String r) {
      list.put(w, new bcWord(w,r));
   }
   
   public void addWord(bcWord word) {
      list.put(word.getWord(),word);
      
   }

   public String getFileName() {
      return filename;
   }
   
   public void printList() {
      System.out.println("WORD LIST: "+filename);
      for (int i=0; i<getNumberOfWords(); i++) {
         System.out.println(getItem(i).getWord());
      }
   }

}
