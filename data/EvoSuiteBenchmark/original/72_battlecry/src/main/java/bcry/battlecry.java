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

/* BATTLECRY
 * The main class of Battlecry. It only initializes the Generator
 * and calls its output methods according to given command line options.
 * If no command line options are given, the GUI is called instead.
 *
 * Command Line Options:
 * -l <filename> Custom layout file
 * -m <filename> Module
 * -i <filename> Print module info and exit
 * -t Text mode (no GUI)
 * -v Verbose mode
 * -h Help
 *
 * Options Array:
 * [0] Verbose Mode
 * [1] Lyrics To Screen (set "true" for command line tool, "false" for GUI and applet)
 */
class battlecry {  
   
   public static void main(String[] args) {

      String demoFile = "src/bcry/bcDemoModule.java";
      String genData = "";
      String genLayout = "";
      boolean[] options = new boolean[2];
      boolean printInfo = false;
      boolean printHelp = false;
      boolean useGUI = true;
      boolean makeDemo = false;
      bcGenerator bc;
      bcVoice voice;
      bcModule module;

      options[0] = false; //Verbose Mode set to Off by default
      options[1] = true;  //Lyrics to Console On

      if (args.length <= 0) {
         genData = "";
         genLayout = "";
      }
      else {
         for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t")) {
               useGUI = false;
            }
            if (args[i].equals("-m")) {
               useGUI = false;
               if (args.length <= i) {
                  System.out.println("Warning: No module file specified. Using default...");
                  genLayout = "";
               }
               else {
                  genData = args[i+1];
                  i++;
               }
            }
            else if (args[i].equals("-l")) {
               useGUI = false;
               if (args.length <= i) {
                  System.out.println("Warning: No layout file specified. Using default...");
                  genLayout = "";
               }
               else {
                  genLayout = args[i+1];
                  i++;
               }
            }
            else if (args[i].equals("--demo")) {
               useGUI = false;
               makeDemo = true;
               if (args.length <= i) {
                  System.out.println("Warning: No demo output file specified. Using default...");
               }
               else {
                  demoFile = args[i+1];
                  i++;
               }
            }
            else if (args[i].equals("-v")) {
               System.out.println("Verbose Mode");
               options[0] = true;
               useGUI = false;
            }
            else if (args[i].equals("-h")) {
               printHelp = true;
               useGUI = false;
            }
            else if (args[i].equals("-i")) {
               useGUI = false;
               if (args.length <= i+1) {
                  System.out.println("Warning: No module file specified. Using default...");
                  genLayout = "";
               }
               else {
                  genData = args[i+1];
                  i++;
               }
               printInfo = true;
            }
         }
      }
      if (printHelp) {
            System.out.println("");
            System.out.println("Battlecry - Command line options");
            System.out.println("");
            System.out.println("--demo <outputfile> Create demo class and write it to the given file."); 
            System.out.println("-t                  Don't use the graphical user interface.");
            System.out.println("                    Not needed if any of the other parameters is called.");
            System.out.println("-l <layoutfile>     Uses layoutfile instead of the layout.dat inside the module");
            System.out.println("-m <module>         Loads a battlecry module and generates the lyrics.");
            System.out.println("                    If parameter is not given, the default module will be used.");
            System.out.println("-i <module>         Displays information about a module");
            System.out.println("-v                  Verbose mode");
            System.out.println("-h                  This help screen");
            System.out.println("");
      }
      else if (!useGUI) {
         voice = new bcVoice(options, null);
         module = new bcModule(genData, genLayout, voice);
         bc = new bcGenerator(module, voice);
         if (printInfo) {
            bc.getModuleInfo();
         }
         else if (makeDemo) {
            module.toDemoClass(demoFile);
         }
         else {
            bc.getLyrics();
         }
      }
      else {
         System.out.println("Battlecry GUI is starting...");
         battlecryGUI g = new battlecryGUI();
      }
   }

}
