/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package apbs_mem_gui;

/**
 *
 * @author Keith Callenberg
 */

public class InFile {
	private String molecule1, molecule2, molecule3, dime, glen[],
            solMethod, proteinDi, solventDi, boundCond, countIon1,
            countIon2, srad, sdens, temp, potential, zmem, lmem, mdie,
            idie, geo1, geo2, geo3, i1charge, i1radius, i1conc, i2charge,
            i2conc, i2radius, center;
	private StringBuffer readMols, focus, all;
	private int maxfocus, calcType;
        private boolean drawpot;
	public void setMaxFocus(int mfocus) {maxfocus = mfocus;}

	public InFile() {
            maxfocus = 0;
            dime = new String("");
            glen = new String[3]; glen[0] = new String("");
            solMethod = new String("");
            proteinDi = new String("");
            solventDi = new String("");
            boundCond = new String("");
            molecule1 = new String("");
            molecule2 = new String("");
            molecule3 = new String("");
            i1charge = new String("");
            i1conc = new String("");
            i1radius = new String("");
            i2charge = new String("");
            i2conc = new String("");
            i2radius = new String("");

            countIon2 = new String("");
            srad      = new String("");
            sdens     = new String("");
            temp      = new String("");
            potential = new String("");
            zmem      = new String("");
            lmem      = new String("");
            mdie      = new String("");
            idie      = new String("");
            geo1      = new String("");
            geo2      = new String("");
            geo3      = new String("");
            center    = new String("");
            calcType  = 0;
            drawpot   = false;

            readMols = new StringBuffer();
            focus = new StringBuffer();
            all = new StringBuffer();
	}

        /* setter methods for the Main class */
	public void setMol1     (String mol1)  {molecule1 = mol1;System.out.println("infile: " + mol1);}
        public void setMol2     (String mol2)  {molecule2 = mol2;}
        public void setMol3     (String mol3)  {molecule3 = mol3;}
	public void setDime     (String newDime)  {dime = newDime;}
	public void setGlen     (String newGlen, int f)  {     glen[f] = newGlen;}
	public void setProteinDi(String newpDi)  {proteinDi = newpDi;}
	public void setSolventDi(String newsDi)  {solventDi = newsDi;}
	public void setSolMethod(String newsol)  {solMethod = newsol;}
	public void setBoundCond(String newbcd)  {boundCond = newbcd;}
        public void setIon1Charge(String newi1charge) {i1charge = newi1charge;}
        public void setIon1Conc(String newi1conc) {i1conc = newi1conc;}
        public void setIon1Radius(String newi1radius) {i1radius = newi1radius;}
        public void setIon2Charge(String newi2charge) {i2charge = newi2charge;}
        public void setIon2Conc(String newi2conc) {i2conc = newi2conc;}
        public void setIon2Radius(String newi2radius) {i2radius = newi2radius;}
	public void setCountIon1(String newci1)  {countIon1 = newci1;}
	public void setCountIon2(String newci2)  {countIon2 = newci2;}
	public void setSrad     (String newrad)  {     srad = newrad;}
	public void setSdens    (String newden)  {    sdens = newden;}
	public void setTemp     (String newtem)  {     temp = newtem;}
        public void setType     (int type)       {    calcType = type;}
        public void setPotential(String pot)    { potential = pot;}
        public void setZmem     (String zm)      { zmem = zm;}
        public void setLmem     (String lm)      { lmem = lm;}
        public void setMdie     (String md)      { mdie = md;}
        public void setIdie     (String id)      { idie = id;}
        public void setGeo1     (String g1)      { geo1 = g1;}
        public void setGeo2     (String g2)      { geo2 = g2;}
        public void setGeo3     (String g3)      { geo3 = g3;}
        public void setDrawPot  (boolean dp)     { drawpot = dp;}
        public void setCenter   (String ctr)     { center = ctr;}

        /* getter methods for the Run class */
        public String getMol1()  {return molecule1;}
        public String getMol2()  {return molecule2;}
        public String getMol3()  {return molecule3;}
	public String getDime()  {return      dime;}
	public String[] getGlen()  {return      glen;}
	public String getProteinDi()  {return proteinDi;}
	public String getSolventDi()  {return solventDi;}
	public String getSolMethod()  {return solMethod;}
	public String getBoundCond()  {return boundCond;}
	public String getCountIon1()  {return i1conc;}
	public String getCountIon2()  {return i2conc;}
	public String getSrad()  { return     srad;}
	public String getSdens()  { return    sdens;}
	public String getTemp()  {  return    temp;}
        public int getType()     {  return   calcType;}
        public String getPotential()   {return  potential;}
        public String getZmem()     {return  zmem;}
        public String getLmem()      {return  lmem;}
        public String getMdie ()      {return  mdie;}
        public String getIdie ()      {return  idie;}
        public String getGeo1()      {return  geo1;}
        public String getGeo2()      {return  geo2;}
        public String getGeo3 ()      {return  geo3;}
        public boolean getDrawPot()     {return  drawpot;}
        public int getMaxfocus()    {return maxfocus;}
        public String getCenter()   {return center;}

        /*
         * The toString method is used when writing APBS input files
         * from the variables stored in an InFile object.
         * There are 3 calculation types:
         *  0: Protein solvation
         *  1: Ion solvation
         *  2: Gating charge
         * @param firstcall True if the output should be written for a Dummy calculation
         */
	public String toString(boolean firstcall) {
		all = new StringBuffer(); readMols = new StringBuffer();	//reset all the stringbuffers or it will
		focus = new StringBuffer();	

                all.append("#APBSmem geometric factors\n");
                all.append(getRunParams());

		readMols.append(new String("# READ IN MOLECULES\nread\n"));

                if(calcType == 2 && firstcall)
                    readMols.append("mol pqr \"" + molecule1 + ".n.pqr\"\n");
                else 
                    readMols.append("mol pqr \"" + molecule1 + "\"\n");

                if(!molecule2.equals("")) {
                    if(calcType == 2 && firstcall)
                        readMols.append("mol pqr \"" + molecule2 + ".n.pqr\"\n");
                    else
                        readMols.append("mol pqr \"" + molecule2 + "\"\n");
                }
                
                if(!molecule3.equals(""))
                    readMols.append("mol pqr \"" + molecule3 + "\"\n");


                if(calcType == 0) {
                    //PROTEIN SOLVATION

                    if(!firstcall) {
                            readMols.append(new String("\n# Read Maps"));
                            for(int i = 1; i <= (maxfocus + 1); i++)
                                    readMols.append(new String("\ndiel dx dielx_" + i + "m.dx diely_" + i + "m.dx dielz_" + i + "m.dx"));
                            readMols.append("\n");
                            for(int i = 1; i <= (maxfocus + 1); i++)
                                    readMols.append(new String("\n    kappa dx kappa_" + i + "m.dx"));
                            readMols.append("\n");
                            for(int i = 1; i <= (maxfocus + 1); i++)
                                    readMols.append(new String("\n    charge dx charge_" + i + "m.dx"));
                    }
                    readMols.append(new String("\nend\n\n"));
                    all.append(readMols);

                    
                    if(firstcall) {
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeSection(i, 1, new String("solv" + i), firstcall, false));
                        }
                    }
                    else {
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeSection(i, 1, new String("solv" + i), firstcall, false));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeSection(i, 1, new String("ref" + i), firstcall, true));
                        }
                        all.append("print elecEnergy ref" + Integer.toString(maxfocus) + " - solv" +
                                Integer.toString(maxfocus) + "\nend\n\n");
                    }
                }

                else if(calcType == 1) {
                    //ION SOLVATION

                    if(!firstcall) {
                            readMols.append(new String("\n# Read Maps"));
                            for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                    readMols.append(new String("\ndiel dx dielx_" + i + "m.dx diely_" + i + "m.dx dielz_" + i + "m.dx"));
                            readMols.append("\n");
                            for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                    readMols.append(new String("\n    kappa dx kappa_" + i + "m.dx"));
                            readMols.append("\n");
                            for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                    readMols.append(new String("\n    charge dx charge_" + i + "m.dx"));
                    }
                    readMols.append(new String("\nend\n\n"));
                    all.append(readMols);

                    if(firstcall) {
                        //Write the input file for the "DUMMY" calculation
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 3, new String("protion" + i), firstcall, false, i+1));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 1, new String("prot" + i), firstcall, false, (maxfocus+2)+i));
                        }
                    }
                    else {
                        //SOLV
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 3, new String("protion" + i), firstcall, true, i + 1));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 2, new String("ion" + i), firstcall, false, 0));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 1, new String("prot" + i), firstcall, true, (maxfocus + 2) + i));
                        }
                        all.append("print elecEnergy protion" + Integer.toString(maxfocus) + " - ion" +
                                Integer.toString(maxfocus) + " - prot" + Integer.toString(maxfocus)  + "\nend\n\n");
                    }
                }
		
                else if(calcType == 2) {
                    //GATING CHARGE                    

                    if(firstcall) {
                        //Write the input file for the "DUMMY" calculation
                        readMols.append(new String("\nend\n\n"));
                        all.append(readMols);

                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeSection(i, 1, new String("ref_neut_" + i), firstcall, false));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeIonSection(i, 2, new String("motion_neut_" + i), firstcall, false, (maxfocus+2)+i));
                        }
                    }
                    
                    else {
                        //Write the input fule for the "SOLV" calculation
                        readMols.append(new String("\n# Read Maps"));
                        for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                readMols.append(new String("\n    diel dx dielx_" + i + "m.dx diely_" + i + "m.dx dielz_" + i + "m.dx"));
                        readMols.append("\n");
                        for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                readMols.append(new String("\n    kappa dx kappa_" + i + "m.dx"));
                        readMols.append("\n");
                        for(int i = 1; i <= (maxfocus + 1)*2; i++)
                                readMols.append(new String("\n    charge dx charge_" + i + "m.dx"));
                        readMols.append(new String("\nend\n\n"));
                        all.append(readMols);
                        
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeGatingSection(i, 1, new String("ref_charged" + i), firstcall, true, i+1));
                        }
                        for(int i = 0; i <= maxfocus; i++) {
                            all.append(writeGatingSection(i, 2, new String("motion_charged" + i), firstcall, true, (maxfocus+2)+i));
                        }
                        all.append("print elecEnergy ref_charged" + Integer.toString(maxfocus)  + " - motion_charged" + Integer.toString(maxfocus) + "\nend\n\n");
                    }
                }

		all.append("quit");
		return all.toString();
	}


        private String writeSection(int i, int mol, String calcId, boolean firstcall, boolean solv) {
            StringBuffer section = new StringBuffer();

            section.append("elec name " + calcId + "\n");

            if(firstcall)
                section.append("mg-dummy\n");
            else
                section.append("mg-manual\n");

            section.append("dime " + dime +"\n");
            section.append("glen " + glen[i] + "\n");
            section.append(solMethod + "\n");
            section.append("pdie " + proteinDi + "\n");
            section.append("sdie " + solventDi + "\n");
            section.append("bcfl ");
            if(i == 0)
                section.append("zero\n");
            else
                section.append("focus\n");
            section.append("ion " + i1charge + " " + i1conc + " " + i1radius +"\n");
            section.append("ion " + i2charge + " " + i2conc + " " + i2radius +"\n");
            section.append("gcent " + center + "\n");
            section.append("mol " + mol + "\n");
            section.append("chgm spl2\n");
            section.append("srfm mol\n");
            section.append("srad " + srad + "\n");
            section.append("swin 0.3\n");
            section.append("sdens " + sdens +"\n");
            section.append("temp " + temp + "\n");
            if(!firstcall)
                section.append("calcenergy total\n");
            else
                section.append("calcenergy no\n");
            section.append("calcforce no\n");

            if(firstcall) {
                section.append("write dielx dx dielx_" + Integer.toString(i+1) + "\n");
                section.append("write diely dx diely_" + Integer.toString(i+1) + "\n");
                section.append("write dielz dx dielz_" + Integer.toString(i+1) + "\n");
                section.append("write kappa dx kappa_" + Integer.toString(i+1) + "\n");
                section.append("write charge dx charge_" + Integer.toString(i+1) + "\nend\n\n");
            }
            else if(solv) {
                section.append("usemap diel " + Integer.toString(i+1) + "\n");
                section.append("usemap kappa " + Integer.toString(i+1) + "\n");
                section.append("usemap charge " + Integer.toString(i+1) + "\n");
                if(drawpot)
                    section.append("write pot dx pot_" + Integer.toString(i+1) + "\n");
                section.append("end\n\n");
            }
            else
                    section.append("end\n\n");

            return section.toString();
        }

        private String writeIonSection(int i, int mol, String calcId, boolean firstcall, boolean solv, int loadmap) {
            StringBuffer section = new StringBuffer();

            section.append("elec name " + calcId + "\n");

            if(firstcall)
                section.append("mg-dummy\n");
            else
                section.append("mg-manual\n");

            section.append("dime " + dime +"\n");
            section.append("glen " + glen[i] + "\n");
            section.append(solMethod + "\n");
            section.append("pdie " + proteinDi + "\n");
            section.append("sdie " + solventDi + "\n");
            section.append("bcfl ");
            if(i == 0)
                section.append("zero\n");
            else
                section.append("focus\n");
            section.append("ion " + i1charge + " " + i1conc + " " + i1radius +"\n");
            section.append("ion " + i2charge + " " + i2conc + " " + i2radius +"\n");
            section.append("gcent " + center + "\n");
            section.append("mol " + mol + "\n");
            section.append("chgm spl2\n");
            section.append("srfm mol\n");
            section.append("srad " + srad + "\n");
            section.append("swin 0.3\n");
            section.append("sdens " + sdens +"\n");
            section.append("temp " + temp + "\n");
            section.append("calcenergy total\n");
            section.append("calcforce no\n");

            if(firstcall) {
                section.append("write dielx dx dielx_" + Integer.toString(loadmap) + "\n");
                section.append("write diely dx diely_" + Integer.toString(loadmap) + "\n");
                section.append("write dielz dx dielz_" + Integer.toString(loadmap) + "\n");
                section.append("write kappa dx kappa_" + Integer.toString(loadmap) + "\n");
                section.append("write charge dx charge_" + Integer.toString(loadmap) + "\nend\n\n");
            }
            else if(solv) {		
                section.append("usemap diel " + Integer.toString(loadmap) + "\n");
                section.append("usemap kappa " + Integer.toString(loadmap) + "\n");
                section.append("usemap charge " + Integer.toString(loadmap) + "\n");
                if(drawpot)
                    section.append("write pot dx pot_" + Integer.toString(loadmap) + "\n");
                section.append("end\n\n");
            }
            else
                    section.append("end\n\n");

            return section.toString();
        }

        private String writeGatingSection(int i, int mol, String calcId, boolean firstcall, boolean solv, int loadmap) {
            StringBuffer section = new StringBuffer();

            section.append("elec name " + calcId + "\n");

            if(firstcall)
                section.append("mg-dummy\n");
            else
                section.append("mg-manual\n");

            section.append("dime " + dime +"\n");
            section.append("glen " + glen[i] + "\n");
            section.append(solMethod + "\n");
            section.append("pdie " + proteinDi + "\n");
            section.append("sdie " + solventDi + "\n");
            section.append("bcfl ");
            if(i == 0)
                section.append("mem\n");
            else {
                if (firstcall)
                    section.append("zero\n");
                else
                    section.append("focus\n");
            }
            section.append("ion " + i1charge + " " + i1conc + " " + i1radius +"\n");
            section.append("ion " + i2charge + " " + i2conc + " " + i2radius +"\n");
            section.append("gcent " + center + "\n");
            section.append("mol " + mol + "\n");
            section.append("chgm spl2\n");
            section.append("srfm mol\n");
            section.append("srad " + srad + "\n");
            section.append("swin 0.3\n");
            section.append("sdens " + sdens +"\n");
            section.append("temp " + temp + "\n");

            if(firstcall)
                section.append("calcenergy no\n");
            else
                section.append("calcenergy comps\n");
            section.append("calcforce no\n");

            if(i==0) {
                section.append("zmem " + zmem + "\n");
                section.append("lmem " + lmem + "\n");
                section.append("mdie " + mdie + "\n");
                section.append("memv " + potential + "\n");
            }

            if(firstcall) {
                section.append("write dielx dx dielx_" + Integer.toString(loadmap) + "\n");
                section.append("write diely dx diely_" + Integer.toString(loadmap) + "\n");
                section.append("write dielz dx dielz_" + Integer.toString(loadmap) + "\n");
                section.append("write kappa dx kappa_" + Integer.toString(loadmap) + "\n");
                section.append("write charge dx charge_" + Integer.toString(loadmap) + "\nend\n\n");
            }
            else if(solv) {
                section.append("usemap diel " + Integer.toString(loadmap) + "\n");
                section.append("usemap kappa " + Integer.toString(loadmap) + "\n");
                section.append("usemap charge " + Integer.toString(loadmap) + "\n");
                section.append("write pot dx pot_" + Integer.toString(loadmap) + "\nend\n\n");
            }
            else
                    section.append("end\n\n");

            return section.toString();
        }

        private String getRunParams() {
            StringBuffer section = new StringBuffer();
            section.append("#zmem " + zmem + "\n");
            section.append("#lmem " + lmem + "\n");
            section.append("#mdie " + mdie + "\n");
            section.append("#idie " + idie + "\n");
            section.append("#geo1 " + geo1 + "\n");
            section.append("#geo2 " + geo2 + "\n");
            section.append("#geo3 " + geo3 + "\n\n");
            return section.toString();
        }

}

