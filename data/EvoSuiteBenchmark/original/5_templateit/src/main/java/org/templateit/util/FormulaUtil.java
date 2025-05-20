package org.templateit.util;

import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.formula.AreaPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RefPtg;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FormulaUtil
{

	public static String offsetRelativeReferences(HSSFWorkbook wb,
			String formula, int roff, int coff)
	{
		Ptg[] ptgs = HSSFFormulaParser.parse(formula, wb);
		offsetRelativePtgs(ptgs, roff, coff);
		String newFormula = HSSFFormulaParser.toFormulaString(wb, ptgs);
		return newFormula;
	}

	private static void offsetRelativePtgs(Ptg[] ptgs, int roff, int coff)
	{
		for (Ptg ptg : ptgs)
		{
			offsetRelativePtg(ptg, roff, coff);
		}
	}

	private static void offsetRelativePtg(Ptg ptg, int roff, int coff)
	{
		if (ptg instanceof RefPtg)
		{
			RefPtg ref = (RefPtg) ptg;
			if (roff != 0 && ref.isRowRelative())
			{
				ref.setRow(ref.getRow() + roff);
			}
			if (coff != 0 && ref.isColRelative())
			{
				ref.setColumn(ref.getColumn() + coff);
			}
		}
		else if (ptg instanceof AreaPtg)
		{
			AreaPtg aptg = (AreaPtg) ptg;
			if (roff != 0)
			{
				if (aptg.isFirstRowRelative())
				{
					aptg.setFirstRow(aptg.getFirstRow() + roff);
				}
				if (aptg.isLastRowRelative())
				{
					aptg.setLastRow(aptg.getLastRow() + roff);
				}
			}
			if (coff != 0)
			{
				if (aptg.isFirstColRelative())
				{
					aptg.setFirstColumn(aptg.getFirstColumn() + coff);
				}
				if (aptg.isLastColRelative())
				{
					aptg.setLastColumn(aptg.getLastColumn() + coff);
				}
			}
		}
	}
}
