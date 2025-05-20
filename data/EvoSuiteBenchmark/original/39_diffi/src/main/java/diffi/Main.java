/*
 * Main.java
 *
 * Created on 31.10.2007, 14:02:21
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diffi;

import de.beiri22.commandline.CommandLine;
import de.beiri22.commandline.Option;
import de.beiri22.filedp.FileDiffPatch;

/**
 *
 * @author Rico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     long t=System.currentTimeMillis();
     FileDiffPatch.createPatch("C:\\Dokumente und Einstellungen\\Rico\\Desktop\\Wickershain\\Pläne\\ScanImage001.jpg", "C:\\Dokumente und Einstellungen\\Rico\\Desktop\\Wickershain\\Pläne\\ScanImage002.jpg", "C:\\Dokumente und Einstellungen\\Rico\\Desktop\\Wickershain\\Pläne\\patch.p", true);
     System.out.println(System.currentTimeMillis()-t+ "ms");
     Option patch=new Option("", "", true, "+", "");
     Option quell=new Option("", "", true, "", "");  
     Option ziel=new Option("", "", true, "", "");  
     CommandLine cmd=new CommandLine(patch,quell, ziel);
     cmd.parse(args);
     System.out.println("Quell: "+quell.getValue());
     System.out.println("Ziel: "+ziel.getValue());
     System.out.println("Patch: "+patch.getValue());
    }
}