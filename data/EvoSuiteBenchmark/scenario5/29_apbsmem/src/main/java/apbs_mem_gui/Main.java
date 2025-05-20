package apbs_mem_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.builder.*;
import org.jmol.api.JmolViewer;
import org.jmol.api.JmolAdapter;
import org.jmol.popup.JmolPopup;

public class InFile {

    /* setter methods for the Main class */
    public void setMol1(String mol1);

    public void setMol2(String mol2);

    public void setDime(String newDime);

    public void setGlen(String newGlen, int f);

    public void setIon1Charge(String newi1charge);

    public void setIon1Conc(String newi1conc);

    public void setIon1Radius(String newi1radius);

    public void setIon2Charge(String newi2charge);

    public void setIon2Conc(String newi2conc);

    public void setIon2Radius(String newi2radius);

    public void setProteinDi(String newpDi);

    public void setSolventDi(String newsDi);

    public void setSrad(String newrad);

    public void setSdens(String newden);

    public void setTemp(String newtem);

    public void setType(int type);

    public void setPotential(String pot);

    public void setLmem(String lm);

    public void setZmem(String zm);

    public void setMdie(String md);

    public void setIdie(String id);

    public void setGeo1(String g1);

    public void setGeo2(String g2);

    public void setGeo3(String g3);

    public void setDrawPot(boolean dp);

    public void setBoundCond(String newbcd);

    public void setSolMethod(String newsol);

    public void setCenter(String ctr);
}

/**
 * @author Keith Callenberg, Gabriel de Forest
 */
public class Main {

    /**
     * Store the current GUI text field values in the inFile object.
     *
     * @return True if the operation was succesful, false otherwise.
     */
    public boolean SaveData() {
        if (pqrFile1.getText().equals("") || pqrFile1.getText() == null || gridDimx.getText().equals("") || gridDimx.getText() == null || gridDimy.getText().equals("") || gridDimy.getText() == null || gridDimz.getText().equals("") || gridDimz.getText() == null || gridLen1a.getText().equals("") || gridLen1a.getText() == null || gridLen2a.getText().equals("") || gridLen2a.getText() == null || gridLen3a.getText().equals("") || gridLen3a.getText() == null || countIon1Charge.getText().equals("") || countIon1Charge.getText() == null || countIon1Con.getText().equals("") || countIon1Con.getText() == null || countIon1Sz.getText().equals("") || countIon1Sz.getText() == null || countIon2Charge.getText().equals("") || countIon2Charge.getText() == null || countIon2Con.getText().equals("") || countIon2Con.getText() == null || countIon2Sz.getText().equals("") || countIon2Sz.getText() == null || proteinDi.getText().equals("") || proteinDi.getText() == null || solventDi.getText().equals("") || solventDi.getText() == null || srad.getText().equals("") || srad.getText() == null || sdens.getText().equals("") || sdens.getText() == null || temp.getText().equals("") || temp.getText() == null) {
            if (maxfocus > 0 && (gridLen1b.getText().equals("") || gridLen1b.getText() == null || gridLen2b.getText().equals("") || gridLen2b.getText() == null || gridLen3b.getText().equals("") || gridLen3b.getText() == null)) {
                if (maxfocus > 1 && (gridLen1c.getText().equals("") || gridLen1c.getText() == null || gridLen2c.getText().equals("") || gridLen2c.getText() == null || gridLen3c.getText().equals("") || gridLen3c.getText() == null)) {
                    if (file_loaded) {
                        //if you arent opening it and it fails then fail
                        JOptionPane.showMessageDialog(null, "Fill in all values first", "", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
        }
        //otherwise save data
        inFile.setMol1(pqrFile1.getText());
        inFile.setMol2(pqrFile2.getText());
        inFile.setDime(new String(gridDimx.getText() + " " + gridDimy.getText() + " " + gridDimz.getText()));
        inFile.setGlen(new String(gridLen1a.getText() + " " + gridLen2a.getText() + " " + gridLen3a.getText()), 0);
        if (maxfocus > 0) {
            inFile.setGlen(new String(gridLen1b.getText() + " " + gridLen2b.getText() + " " + gridLen3b.getText()), 1);
        }
        if (maxfocus == 2) {
            inFile.setGlen(new String(gridLen1c.getText() + " " + gridLen2c.getText() + " " + gridLen3c.getText()), 2);
        }
        inFile.setIon1Charge(countIon1Charge.getText());
        inFile.setIon1Conc(countIon1Con.getText());
        inFile.setIon1Radius(countIon1Sz.getText());
        inFile.setIon2Charge(countIon2Charge.getText());
        inFile.setIon2Conc(countIon2Con.getText());
        inFile.setIon2Radius(countIon2Sz.getText());
        inFile.setProteinDi(new String(proteinDi.getText()));
        inFile.setSolventDi(new String(solventDi.getText()));
        inFile.setSrad(srad.getText());
        inFile.setSdens(sdens.getText());
        inFile.setTemp(temp.getText());
        inFile.setType(calcTypeCombo.getSelectedIndex());
        inFile.setPotential(potential.getText());
        inFile.setLmem(Lmem.getText());
        inFile.setZmem(zmem.getText());
        inFile.setMdie(membraneDi.getText());
        inFile.setIdie(idie.getText());
        inFile.setGeo1(geoFactor1.getText());
        inFile.setGeo2(geoFactor2.getText());
        inFile.setGeo3(geoFactor3.getText());
        inFile.setDrawPot(drawPot.isSelected());
        if (boundaryCondCombo.getSelectedIndex() == 2) {
            inFile.setBoundCond(new String("mdh"));
        } else if (boundaryCondCombo.getSelectedIndex() == 1) {
            inFile.setBoundCond(new String("sdh"));
        } else if (boundaryCondCombo.getSelectedIndex() == 0) {
            inFile.setBoundCond(new String("zero"));
        }
        if (solMethodCombo.getSelectedIndex() == 1) {
            inFile.setSolMethod(new String("npbe"));
        } else if (solMethodCombo.getSelectedIndex() == 0) {
            inFile.setSolMethod(new String("lpbe"));
        }
        if (centerCombo.getSelectedIndex() == 0) {
            inFile.setCenter(new String("0 0 0"));
        } else if (centerCombo.getSelectedIndex() == 1) {
            inFile.setCenter(new String("mol 1"));
        }
        return true;
    }
}
