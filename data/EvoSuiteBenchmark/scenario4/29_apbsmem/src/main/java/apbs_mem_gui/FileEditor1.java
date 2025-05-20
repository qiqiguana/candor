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
     * @param filename Path to APBS output file.
     * @return String array of total energies in kilojoules, kilocalories and k_BT
     */
    public String[] getEnergy(String filename);

    /**
     * Extract the energy from a calculation that has summed the component atom energies
     * rather than simply taking the total. (pull_comps.c)
     * @param filename Path to the file written by pull_comps with the energy sum.
     * @return a double array of the total energy of each calculation (up to 6 calculations if there are 3 focus levels).
     */
    public double[] getCompEnergy(String filename);

    private double toDoublePos(String theDouble);

    private double toDoubleNeg(String theDouble);
}
