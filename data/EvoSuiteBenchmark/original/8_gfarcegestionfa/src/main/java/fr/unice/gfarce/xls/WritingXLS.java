package fr.unice.gfarce.xls;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

import fr.unice.gfarce.identity.Candidat;
import jxl.*;
import jxl.write.*; 
import jxl.write.biff.RowsExceededException;

public  class WritingXLS {


	public void exportXLS(Candidat[] tab,String output) throws IOException, RowsExceededException, WriteException, ParseException{		
		output += ".xls";
		WritableWorkbook workbook = Workbook.createWorkbook(new File(output));
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		WritableFont arialFont1 = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, true);
		WritableFont arialFont2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, true);
		WritableCellFormat titre = new WritableCellFormat (arialFont1);
		WritableCellFormat data = new WritableCellFormat (arialFont2);		
		String[] s = {"NOM","PRENOM","SEX","EMAIL","TYPE","DATE NAISSANCE","DIPLOME","NATIONALITE"} ;
		Label l;
		for(int i =0;i<s.length;i++){
			l = new Label(i,0,s[i],titre);
			sheet.addCell(l);
		}		
		for(int c=0;c<tab.length;c++){
				sheet.addCell( new Label(0,c+1,tab[c].getNom(),data));
				sheet.addCell( new Label(1,c+1,tab[c].getPrenom(),data));
				sheet.addCell( new Label(2,c+1,tab[c].getSex(),data));
				sheet.addCell( new Label(3,c+1,tab[c].getEmail(),data));
				sheet.addCell( new Label(4,c+1,""+tab[c].getType(),data));
				sheet.addCell( new Label(5,c+1,tab[c].getDateNaissance().get(Calendar.DATE)+"/"+tab[c].getDateNaissance().get(Calendar.MONTH)+"/"+tab[c].getDateNaissance().get(Calendar.YEAR),data));
				sheet.addCell( new Label(6,c+1,tab[c].getDiplome(),data));
				sheet.addCell( new Label(8,c+1,tab[c].getNationalite(),data));
		}
		workbook.write();
		workbook.close(); 

	}

}
