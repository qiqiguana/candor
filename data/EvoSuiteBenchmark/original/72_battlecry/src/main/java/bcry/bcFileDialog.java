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

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;


/* Adapted from MFileDialog, written by Florian Turowski for the Maze Invaders Project */
public class bcFileDialog extends Component{

    private String path;
    private File file;
    private Frame frame;
    private JFrame jframe;
    private boolean swing;
    private String ffilter;
    private String recentFile;
    private String fdes;
    private JFileChooser fc;

    /* Constructor
     */
    public bcFileDialog(String f) {
        super();
        ffilter = f;
        recentFile = "data/temp/recent";
        fdes = "";
        path = getLastPath();
        init(path);
    }
    /* Constructor
     * @param f String - Filter
     * @param d String - Description of File Type
     */
    public bcFileDialog(String f,String d) {
        super();
        ffilter = f;
        recentFile = "data/temp/recent";
        fdes = d;
        path = getLastPath();
        init(path);
    }
    private String getLastPath(){
        String path;
        BufferedReader br;
        
        try{ br = new BufferedReader(new FileReader(recentFile));
             path = br.readLine();
             br.close();
             return path;
        }
        catch(FileNotFoundException e){ File f = new File(recentFile);}
        catch(IOException e){}
        return null;
            
    }
    
    private void setLastPath(String p){
        
        BufferedWriter bw;
        
        try{ bw = new BufferedWriter(new FileWriter(recentFile));
             bw.write(p,0,p.length());
             bw.close();
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}
        
    }
    
    private void init(String p){
      if(p == null){
            fc = new JFileChooser();
                fc.setDragEnabled(true);
                MFileFilter filter = new MFileFilter();
            filter.addExtension(ffilter);
                filter.setDescription(fdes);
                fc.addChoosableFileFilter(filter);
                
      }
        else {  fc = new JFileChooser(new File(p));
                fc.setDragEnabled(true);
                MFileFilter filter = new MFileFilter();
                filter.addExtension(ffilter);
                filter.setDescription(fdes);
                fc.addChoosableFileFilter(filter);
         
        }
    }
      

    public File openDialog(){
        
        File f = new File("");
        
        int val = fc.showOpenDialog(null);
        if(val == JFileChooser.APPROVE_OPTION){
                    path = fc.getCurrentDirectory().getAbsolutePath();
                    f = fc.getSelectedFile();
                    //System.out.println(path);
                    setLastPath(path);
                    
        }
        
        return f;
        
    }
    
    public File openSaveDialog(){
        
        File f = new File("");
        
        int val = fc.showSaveDialog(null);
        if(val == JFileChooser.APPROVE_OPTION){
                    path = fc.getCurrentDirectory().getAbsolutePath();
                    f = fc.getSelectedFile();
                    //System.out.println(path);
                    setLastPath(path);
                    
        }
        
        return f;
        
    }
    
    
    
    /*
     * Auxiliary class for filter usage
     */
    class MFileFilter extends javax.swing.filechooser.FileFilter{
          private Hashtable filters = null;
       private String description = null;
       private String fullDescription = null;
       private boolean useExtensionsInDescription = true;
       
       public MFileFilter() {
      this.filters = new Hashtable();
          }
          public MFileFilter(String extension, String description) {
      this();
      if(extension!=null) addExtension(extension);
      if(description!=null) setDescription(description);
          }
          
          public void addExtension(String extension) {
      if(filters == null) {
            filters = new Hashtable(5);
      }
      filters.put(extension.toLowerCase(), this);
      fullDescription = null;
       }
       
       public String getDescription() {
      if(fullDescription == null) {
          if(description == null || isExtensionListInDescription()) {
         fullDescription = description==null ? "(" : description + " (";
         Enumeration extensions = filters.keys();
         if(extensions != null) {
             fullDescription += "." + (String) extensions.nextElement();
             while (extensions.hasMoreElements()) {
            fullDescription += ", ." + (String) extensions.nextElement();
             }
         }
         fullDescription += ")";
          } else {
         fullDescription = description;
          }
      }
      return fullDescription;
           }
           public void setDescription(String description) {
      this.description = description;
      fullDescription = null;
         }
         public boolean isExtensionListInDescription() {
      return useExtensionsInDescription;
         }
         public void setExtensionListInDescription(boolean b) {
      useExtensionsInDescription = b;
      fullDescription = null;
         }
         public String getExtension(File f) {
      if(f != null) {
          String filename = f.getName();
          int i = filename.lastIndexOf('.');
          if(i>0 && i<filename.length()-1) {
         return filename.substring(i+1).toLowerCase();
          };
      }
      return null;
             }
             public boolean accept(File f) {
      if(f != null) {
          if(f.isDirectory()) {
         return true;
          }
          String extension = getExtension(f);
          if(extension != null && filters.get(getExtension(f)) != null) {
         return true;
          };
      }
      return false;
         }
    }    
}
