/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package apbs_mem_gui;

import jahuwaldt.plot.PlotWindow;
import javax.swing.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import org.jmol.api.JmolViewer;
/**
 *
 * @author Keith Callenberg
 */
public class Run implements Runnable{

    Exec exec = new Exec();
    FileEditor file = new FileEditor();
    InFile in;
    File ofile;
    PrintWriter outFile;
    JmolViewer viewer;
    Main p;
    JProgressBar pb;
    boolean dp, preview;
    String pc;

    public Run(Main parent, InFile i, File of, JmolViewer view, JProgressBar pBar, boolean drawp, String potc, boolean prev) {
        p = parent;
        in = i;
        ofile = of;
        viewer = view;
        pb = pBar;
        dp = drawp;
        pc = potc;
        preview = prev;
    }

    /**
     * Method for running an APBSmem calculation.
     */
    public void run() {
        Double isovalue = Double.valueOf(in.getMdie()) + 0.001;
        if(preview) {
            pb.setValue(10);

            int ttype = in.getType();
            int tfocus = in.getMaxfocus();

            in.setType(0); // we set all types to prot solv when doing a quick preview
            in.setMaxFocus(0);


            p.SaveToFile(true);

            exec.callAPBS(ofile.getPath() + ".dummy");
            pb.setValue(50);

            exec.callDrawMem(new String("dielx_1"), in.getZmem(), in.getLmem(), in.getProteinDi(), in.getSolventDi(), in.getMdie(), in.getIdie(), "0",
                in.getCountIon1(), in.getGeo1(), in.getGeo2(), in.getGeo3());

            pb.setValue(90);
            viewer.evalString("isosurface delete");
            if (fileExists("dielx_1m.dx"))
                viewer.evalString("isosurface " + p.getIsocontour() + " \"dielx_1m.dx\"; color isoSurface white translucent");
            else
                JOptionPane.showMessageDialog(null, "Error: an error occurred while writing the modified dielectric maps.", "Error", JOptionPane.ERROR_MESSAGE);

            //revert to previous calculation type
            in.setType(ttype);
            in.setMaxFocus(tfocus);
            pb.setValue(0);
        }
        else {
            // A regular calculation -- Not preview mode
            pb.setValue(1);
            int mf = in.getMaxfocus();
            SaveToFile(true);
            if (in.getType() == 2) {
                //GATING CHARGE CALCULATION
                double tempPot = Double.parseDouble(in.getPotential());

                double[] memv = new double[4];
                double[] memvkt = new double[4];
                //double half = Math.floor((tempPot/2)+0.5);
                double half = tempPot/2;
                if(tempPot > 0) {
                    memv[0] = -(tempPot);
                    memv[1] = -half;
                    //memv[2] = 0;
                    memv[2] = half;
                    memv[3] = tempPot;
                }
                else {
                    memv[0] = tempPot;
                    memv[1] = half;
                    //memv[2] = 0;
                    memv[2] = -half;
                    memv[3] = -(tempPot);
                }
                pb.setValue(5);

                double[] finalenergy = new double[memv.length];
                double[] tempenergies = new double[6];
                Double ktconv = Double.parseDouble(in.getTemp()) * 0.008314472; //conversion from kJ to kBT
                for(int j=0; j < memv.length; j++) {

                    exec.callAPBS(ofile.getPath() + ".dummy");		//now run apbs

                    for (int i = 0; i <= (mf+1)*2; i++) {
                        //create dielectric maps
                        exec.callDrawMem(new String("dielx_" + Integer.toString(i+1)), in.getZmem(), in.getLmem(), in.getProteinDi(), in.getSolventDi(), in.getMdie(), in.getIdie(), Double.toString(memv[j]),
                            in.getCountIon1(), in.getGeo1(), in.getGeo2(), in.getGeo3());
                    }


                    in.setPotential(Double.toString(memv[j]));
                    //save all data to file for second (solv) run
                    SaveToFile(false);
                    exec.callAPBS(ofile.getPath() + ".solv");	//run apbs again
                    pb.setValue(10+(j*10));
                    exec.callPullcomps(ofile.getPath() + ".solv.out"); //pull the atom component energies and add them together for a total

                    tempenergies = file.getCompEnergy(ofile.getParentFile().getParent() + "/comps_temp");

                    memvkt[j] = memv[j] * 25.69;
                    p.log("Memv " + Double.toString(memvkt[j]) + " mV");

                    for(int k=0; k<(mf+1)*2;k++) {
                        p.log(Integer.toString(k) + ": " + tempenergies[k] + " kJ, " + tempenergies[k] / (ktconv) + " kT");
                    }

                    //we multiply by 2 since APBS divides by 2 to account for double counting for explicit charges
                    finalenergy[j] = 2 * (tempenergies[mf] - tempenergies[((mf+1)*2)-1]);
                    p.log("Total 2*(file1-file2): " + Double.toString(finalenergy[j]) + " kJ, " + (finalenergy[j] / (ktconv)) + " kT\n");

                }
                pb.setValue(60);
                PlotWindow pw = new PlotWindow("Gating Charge Results");
                pw.setSize(500, 300);
                pw.setLocation(50,50);
                pw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pw.setData(memvkt, finalenergy);
                pw.drawPlot("Gating Charge Results", "Applied voltage [mV]", "Energy [kT]");
                pw.show();
                pw.saveImage(ofile.getParent() + "/gating_charge_plot.jpg");

                NumberFormat ftr = new DecimalFormat("0.####E0");
                double slope = ((finalenergy[memv.length-1] / (ktconv)) - (finalenergy[0] / (ktconv))) / 4;// (memv[3] - memv[0]);
                pb.setValue(70);
                JOptionPane.showMessageDialog(null, "The slope is " + ftr.format(slope) + " e.\n Output has been saved as\n " + ofile.getName() + "/" + ofile.getName() + ".solv.out.", "Calculation results", JOptionPane.PLAIN_MESSAGE);
                p.log("Slope: " + slope + " e");

                pb.setValue(0);
            }

            else {
                exec.callAPBS(ofile.getPath() + ".dummy");		//now run apbs
                pb.setValue(6);

                if (!fileExists("dielx_1.dx")){
                    JOptionPane.showMessageDialog(null, "Error: an error occurred while writing the first dielectric maps.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //protein solvation calculation type
                if (in.getType() == 0) {
                    for (int i = 0; i <= mf; i++) {
                        //create dielectric maps
                        exec.callDrawMem(new String("dielx_" + Integer.toString(i+1)), in.getZmem(), in.getLmem(),
                            in.getProteinDi(), in.getSolventDi(), in.getMdie(), in.getIdie(), "0",
                            in.getCountIon1(), in.getGeo1(), in.getGeo2(), in.getGeo3());
                    }
                }
                //ion solvation calculation
                else {
                    for (int i = 0; i <= (mf+1)*2; i++) {
                        //create dielectric maps
                        exec.callDrawMem(new String("dielx_" + Integer.toString(i+1)), in.getZmem(), in.getLmem(),
                            in.getProteinDi(), in.getSolventDi(), in.getMdie(), in.getIdie(), "0",
                            in.getCountIon1(), in.getGeo1(), in.getGeo2(), in.getGeo3());
                    }
                }

                if (!fileExists("dielx_1m.dx")){
                    JOptionPane.showMessageDialog(null, "Error: an error occurred while writing the modified dielectric maps.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pb.setValue(50);

                SaveToFile(false);			//save all data to file for second run
                exec.callAPBS(ofile.getPath() + ".solv");	//run apbs again


                pb.setValue(80);
                String[] theEnergy = file.getEnergy(ofile.getPath() + ".solv.out");


                if (theEnergy[0] != null) {
                    JOptionPane.showMessageDialog(null, "ELEC energy result: \n" + theEnergy[0] + " = " + theEnergy[1] + " = " + theEnergy[2] + ".\n Output has been saved as " + ofile.getName() + ".solv.out.", "Global net ELEC energy", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Energy not found!", "Global net ELEC energy", JOptionPane.PLAIN_MESSAGE);
                }
                pb.setValue(0);
            }
            viewer.evalString("isosurface delete;isosurface " + p.getIsocontour() + " \"dielz_" + (mf + 1) + "m.dx\"; color isosurface white translucent");
            if (dp) {
                 viewer.evalString("isosurface potpos " + pc + " \"pot_1.dx\"; color isoSurface red translucent");
                 viewer.evalString("isosurface potneg -" + pc + " \"pot_1.dx\"; color isoSurface blue translucent");
            }
            // In the future, an option to save DX files would be great, but for now, let's leave this alone by default since they are so large
            //p.copyFiles(new File(".").getPath(), ofile.getParent(), ".dx");
        }
    }

    /**
     * This function writes the parameters stored in an inFile object to a file.
     * @param firstcall True if the calculation is a first "dummy" calculation for writing the DX files, otherwise false.
     */
    public void SaveToFile(boolean firstcall) //saves to file
    {
        if (ofile != null) {
            try {
                if(firstcall) {
                    outFile = new PrintWriter(new FileOutputStream(ofile.getPath() + ".dummy.in"));
                }
		else {
                    outFile = new PrintWriter(new FileOutputStream(ofile.getPath() + ".solv.in"));
                }
            } catch (Exception e1) {
                System.out.print(e1.toString());
            }

            String tempIn = in.toString(firstcall);         //store data in temporary string
            String[] tempInSplit = tempIn.split("\n");		//split data by each new line
            for (int i = 0; i < tempInSplit.length; i++) {
                outFile.println(tempInSplit[i]);		//print data one line at a time
            }
            outFile.close();

        }
    }

    public boolean fileExists(String f) {
            File check = new File(f);
            return check.exists();
    }



}
