/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package apbs_mem_gui;
import java.io.*;

/**
 *
 * @author keithc
 */


public class Exec {

    /**
     * Use ProcessBuilder to start a calculation with APBS.
     * @param file The APBS input file containing all necessary parameters.
     */
    public void callAPBS(String file) {

        try {
            ProcessBuilder pb = new ProcessBuilder();
            if(System.getProperty("os.name").startsWith("Windows")) {

                pb = new ProcessBuilder( "apbs.exe", (file + ".in"));
            }
            else {
                     pb = new ProcessBuilder( "apbs", (file + ".in"));
            }

             Process pp = pb.start();
             System.out.println("Starting apbs with input file: " + file + ".in");

                try {
                     PrintWriter out = new PrintWriter(new FileOutputStream(new File(file + ".out")));
                     BufferedReader in =  new BufferedReader (new InputStreamReader(pp.getInputStream())) ;
                     String currentLine = null;
                     while((currentLine = in.readLine()) != null )
                         out.println(currentLine);
                     out.close();
                     in.close();
                }
                catch (Exception eb) {
                    System.out.println(eb);
                }

             int exitVal = pp.waitFor();
             System.out.println("APBS exitValue: " + exitVal);
             //should we return an error and stop the calculation if the APBS exit value is not 0?
        } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
        }
    }

    /**
     * Calls the draw_membrane utility to write new DX maps that include the
     * membrane. 
     *
     * @param file
     * @param zmem
     * @param Lmem
     * @param proteinDi
     * @param solventDi
     * @param membraneDi
     * @param V
     * @param countIon1Con
     * @param geoFactor1
     * @param geoFactor2
     * @param idie
     * @param geoFactor3
     */
    public void callDrawMem(String file, String zmem, String Lmem, String proteinDi, String solventDi, String membraneDi, String idie, String V,
               String countIon1Con, String upperExclusion, String lowerExclusion, String headgroupThickness) {
        try {
                ProcessBuilder pb = new ProcessBuilder();

                String command = "";
                if(System.getProperty("os.name").startsWith("Windows")) {
                     command = "draw_membrane4.exe";
                }
                else {
                    command = "./draw_membrane4";
                }

                pb = new ProcessBuilder(command, (file + ".dx"), zmem, Lmem, proteinDi, solventDi, membraneDi,
                            idie, V, countIon1Con, upperExclusion, lowerExclusion, headgroupThickness);

                System.out.println(command + " " + zmem + " " + Lmem + " " + proteinDi + " " + solventDi + " " + membraneDi + " " + idie + " " + V + " " + countIon1Con + " " + upperExclusion + " " + lowerExclusion + " " + headgroupThickness);

                Process pp = pb.start();

             /* uncomment to print draw_membrane4 output
             BufferedReader in = new BufferedReader(new InputStreamReader(pp.getInputStream()));
             String line;
             System.out.println("DRAWMEM OUTPUT:");
             while ((line = in.readLine()) != null) {
                System.out.println(line);
             }
              */
            int exitVal = pp.waitFor();
            System.out.println(command + " Process exitValue: " + exitVal);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use a ProcessBuilder instance to call the C-based utility total_charge_off
     * to set all charges in a file to zero.
     * @param file1 PQR file that should be set to all zero charges.
     */
    public void callChargeOff(String file1) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if(System.getProperty("os.name").startsWith("Windows")) {
                pb = new ProcessBuilder("total_charge_off_2.exe", file1);
            }
            else
                pb = new ProcessBuilder("./total_charge_off_2", file1);
            Process pp = pb.start();
            int exitVal = pp.waitFor();
            System.out.println("total_charge_off_2 process exit: " + exitVal);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use a ProcessBuilder to run the C-based pull_comps utility for summing
     * the atom component energies from an APBS calculation.
     * @param file1 The APBS output file that contains the component energies.
     */
    public void callPullcomps(String file1) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if(System.getProperty("os.name").startsWith("Windows")) {
                pb = new ProcessBuilder("pull_comps.exe", file1, "comps_temp");
            }
            else
                pb = new ProcessBuilder("./pull_comps", file1, "comps_temp");
            Process pp = pb.start();
            int exitVal = pp.waitFor();
            System.out.println("pull_comps process exit: " + exitVal);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

