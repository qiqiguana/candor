package apbs_mem_gui;

/**
 * @author keithc
 */
import java.io.*;
import java.util.Scanner;
import java.text.*;

public class FileEditor {

    /**
     * Extract the energy from a calculation that has summed the component atom energies
     * rather than simply taking the total. (pull_comps.c)
     *
     * @param filename Path to the file written by pull_comps with the energy sum.
     * @return a double array of the total energy of each calculation (up to 6 calculations if there are 3 focus levels).
     */
    public double[] getCompEnergy(String filename) {
        File temp = new File(filename);
        if (!temp.exists())
            return null;
        else {
            double[] finalenergy = new double[6];
            int i = 0;
            try {
                Scanner fScan = new Scanner(new FileInputStream(filename));
                String nextline;
                double D;
                while (fScan.hasNextLine()) {
                    nextline = fScan.nextLine();
                    System.out.println(nextline);
                    if (!nextline.equals("")) {
                        if (nextline.contains("+")) {
                            //if its time 10^positive int
                            D = Double.parseDouble(nextline.substring(0, (nextline.lastIndexOf("+") - 1)));
                            D *= Math.pow(10, Double.parseDouble(nextline.substring(nextline.indexOf("+") + 1)));
                            //  /(temperature*0.008314472); // convert to kT
                            finalenergy[i] = D;
                        } else {
                            //if its time 10^negative int
                            D = Double.parseDouble(nextline.substring(0, (nextline.lastIndexOf("-") - 1)));
                            D *= Math.pow(10, Double.parseDouble(nextline.substring(nextline.lastIndexOf("-"))));
                            //  /(temperature*0.008314472);
                            finalenergy[i] = D;
                        }
                        i++;
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return finalenergy;
        }
    }
}
