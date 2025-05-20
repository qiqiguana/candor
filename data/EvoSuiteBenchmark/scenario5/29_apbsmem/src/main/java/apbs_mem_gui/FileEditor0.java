package apbs_mem_gui;

/**
 * @author keithc
 */
import java.io.*;
import java.util.Scanner;
import java.text.*;

public class FileEditor {

    /**
     * Extract energy from the final APBS output file.
     *
     * @param filename Path to APBS output file.
     * @return String array of total energies in kilojoules, kilocalories and k_BT
     */
    public String[] getEnergy(String filename) {
        //returns [0] energy in kJ/mol, then [1] is in kcal/mol
        File temp = new File(filename);
        String[] energies = new String[3];
        if (!temp.exists())
            return null;
        else {
            NumberFormat formatter = new DecimalFormat("0.####E0");
            double finalenergy = 0;
            try {
                Scanner fScan = new Scanner(new FileInputStream(filename));
                String nextline;
                //tells whether solvated or reference
                boolean solvated = true;
                while (fScan.hasNextLine()) {
                    nextline = fScan.nextLine();
                    if (nextline.contains("elec name solvated")) {
                        solvated = true;
                    } else if (nextline.contains("elec name reference")) {
                        solvated = false;
                    } else if (nextline.contains("Global net ELEC energy ") && solvated) {
                        //if found energy and its in solvated
                        System.out.println(nextline);
                        if (nextline.contains("+")) {
                            //if its time 10^positive int
                            finalenergy = toDoublePos(nextline);
                        } else {
                            //if its time 10^negative int
                            finalenergy = toDoubleNeg(nextline);
                        }
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            energies[0] = formatter.format(finalenergy) + " kJ/mol";
            energies[1] = formatter.format(finalenergy * 0.239) + " kcal/mol";
            energies[2] = formatter.format(finalenergy * 0.4035) + " kT";
            return energies;
        }
    }

    private double toDoublePos(String theDouble);

    private double toDoubleNeg(String theDouble);
}
