/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package apbs_mem_gui;

/**
 *
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
	public String [] getEnergy(String filename) {		//returns [0] energy in kJ/mol, then [1] is in kcal/mol
		File temp = new File(filename);
		String [] energies = new String[3];
		if(!temp.exists())
			return null;
		else {
			NumberFormat formatter = new DecimalFormat("0.####E0");
			double finalenergy = 0;

			try{
				Scanner fScan = new Scanner(new FileInputStream(filename));
				String nextline;
				boolean solvated = true; //tells whether solvated or reference

				while(fScan.hasNextLine()) {
					nextline = fScan.nextLine();
					if(nextline.contains("elec name solvated")) {
						solvated = true;
					} else if(nextline.contains("elec name reference")) {
						solvated = false;
					} else if(nextline.contains("Global net ELEC energy ") && solvated) {	//if found energy and its in solvated
                                            System.out.println(nextline);
						if(nextline.contains("+")) {		//if its time 10^positive int
							finalenergy = toDoublePos(nextline);
						} else {							//if its time 10^negative int
							finalenergy = toDoubleNeg(nextline);
						}
                                        }
				}
			} catch (Exception e1){e1.printStackTrace();}

			energies[0] = formatter.format(finalenergy) + " kJ/mol";
			energies[1] = formatter.format(finalenergy * 0.239) + " kcal/mol";
                        energies[2] = formatter.format(finalenergy * 0.4035) + " kT";
			return energies;
		}
	}

        /**
         * Extract the energy from a calculation that has summed the component atom energies
         * rather than simply taking the total. (pull_comps.c)
         * @param filename Path to the file written by pull_comps with the energy sum.
         * @return a double array of the total energy of each calculation (up to 6 calculations if there are 3 focus levels).
         */
        public double[] getCompEnergy(String filename) {
            File temp = new File(filename);
            if(!temp.exists())
                return null;
            else {
                double[] finalenergy = new double[6];
                int i = 0;
                try{
                    Scanner fScan = new Scanner(new FileInputStream(filename));
                    String nextline;

                    double D;

                    while(fScan.hasNextLine()) {
                        nextline = fScan.nextLine();
                        System.out.println(nextline);
                        if(!nextline.equals("")) {
                            if(nextline.contains("+")) {		//if its time 10^positive int
                                    D = Double.parseDouble( nextline.substring(0,(nextline.lastIndexOf("+") - 1)));
                                    D *= Math.pow( 10, Double.parseDouble(nextline.substring(nextline.indexOf("+") + 1)));
                                    finalenergy[i] = D; //  /(temperature*0.008314472); // convert to kT
                            } else {							//if its time 10^negative int
                                    D = Double.parseDouble(nextline.substring(0,(nextline.lastIndexOf("-") - 1)));
                                    D *= Math.pow( 10, Double.parseDouble(nextline.substring(nextline.lastIndexOf("-"))));
                                    finalenergy[i] = D; //  /(temperature*0.008314472);
                            }
                            i++;
                        }
                    }
                }
                catch (Exception e1){e1.printStackTrace();}

                return finalenergy;
            }
        }

	private double toDoublePos(String theDouble) {
		double D;
                D = Double.parseDouble( theDouble.substring( (theDouble.indexOf("Global net ELEC energy ") + 25), (theDouble.lastIndexOf("+") - 1) ) );
		return ( D *= Math.pow( 10, Double.parseDouble( theDouble.substring( (theDouble.indexOf("+") + 1), theDouble.indexOf(" kJ") ) ) ) );
	}
	private double toDoubleNeg(String theDouble) {
		double D;
		D = Double.parseDouble( theDouble.substring( (theDouble.indexOf("Global net ELEC energy ") + 25), (theDouble.lastIndexOf("-") - 1) ) );
		return ( D *= Math.pow( 10, Double.parseDouble( theDouble.substring( theDouble.lastIndexOf("-"), theDouble.indexOf(" kJ") ) ) ) );
	}
}

