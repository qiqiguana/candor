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
    public String[] getEnergy(String filename);
}
