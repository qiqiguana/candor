package org.templateit;

import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;

public class Poi2ItextUtil
{
	private static final Logger logger = Logger.getLogger(Poi2ItextUtil.class);
	private final HSSFWorkbook workbook;
	
	public Poi2ItextUtil(HSSFWorkbook workbook)
	{
		this.workbook = workbook;
	}

	public static Color colorPOI2Itext(HSSFColor poiColor)
	{
		short[] poiRGB = poiColor.getTriplet();
		return new Color(poiRGB[0],poiRGB[1],poiRGB[2]);
	}

	public void copyBackgroundColor(HSSFCell xcell,PdfPCell cell)
	{
		HSSFCellStyle xstyle = xcell.getCellStyle();
		short cx = xstyle.getFillForegroundColor();
		
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor poiColor = palette.getColor(cx);
		if( poiColor != null && poiColor.getIndex()!=HSSFColor.AUTOMATIC.index)
		{
			Color color = colorPOI2Itext(poiColor);
			cell.setBackgroundColor(color);
		}
	}
	
	public void copyCellHorisontalAlignment(HSSFCell xcell,PdfPCell cell)
	{
		HSSFCellStyle xstyle = xcell.getCellStyle();
		int type = xcell.getCellType();
		short alignment = xstyle.getAlignment();
		switch(alignment)
		{
			case      HSSFCellStyle.ALIGN_GENERAL:
				switch (type)
				{
					case HSSFCell.CELL_TYPE_NUMERIC:
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					break;

					case HSSFCell.CELL_TYPE_BLANK:
					case HSSFCell.CELL_TYPE_BOOLEAN:
					case HSSFCell.CELL_TYPE_ERROR:
					case HSSFCell.CELL_TYPE_STRING:
						cell.setHorizontalAlignment(Element.ALIGN_UNDEFINED);
					break;
					
					case HSSFCell.CELL_TYPE_FORMULA:
						HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator(workbook);
						int valType = eval.evaluateFormulaCell(xcell);
						switch (valType)
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							break;
							default: 
								cell.setHorizontalAlignment(Element.ALIGN_UNDEFINED);
						}
					break;
				}
				break;
			case      HSSFCellStyle.ALIGN_LEFT:
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				break;
	    case      HSSFCellStyle.ALIGN_CENTER:
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	break;
	    case      HSSFCellStyle.ALIGN_RIGHT:
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	break;
	    case      HSSFCellStyle.ALIGN_FILL:
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	break;
      case      HSSFCellStyle.ALIGN_JUSTIFY:
				cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
      	break;
	    case      HSSFCellStyle.ALIGN_CENTER_SELECTION:
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	break;
		}
	}

	public void copyCellBorders(HSSFCell xcell, PdfPCell cell)
	{
		HSSFCellStyle xstyle = xcell.getCellStyle();
		int top = xstyle.getBorderTop()!=HSSFCellStyle.BORDER_NONE?Rectangle.TOP:Rectangle.NO_BORDER;
		int bottom = xstyle.getBorderBottom()!=HSSFCellStyle.BORDER_NONE?Rectangle.BOTTOM:Rectangle.NO_BORDER;
		int left = xstyle.getBorderLeft()!=HSSFCellStyle.BORDER_NONE?Rectangle.LEFT:Rectangle.NO_BORDER;
		int right = xstyle.getBorderRight()!=HSSFCellStyle.BORDER_NONE?Rectangle.RIGHT:Rectangle.NO_BORDER;
		cell.setBorder(top|bottom|left|right);
	}
	
	public void resetRightBorder(HSSFCell xcell, PdfPCell cell)
	{
		HSSFCellStyle xstyle = xcell.getCellStyle();
		int right = xstyle.getBorderRight()!=HSSFCellStyle.BORDER_NONE?Rectangle.RIGHT:Rectangle.NO_BORDER;
		cell.setBorder(cell.getBorder()|right);
	}
	
	public Font chooseFont(HSSFFont xfont )
	{
	  int fontFamily = chooseFontFamily(xfont, Font.HELVETICA);
		
		Font font = new Font(fontFamily);
		font.setSize(xfont.getFontHeightInPoints());
		
    int style = xfont.getBoldweight()==HSSFFont.BOLDWEIGHT_BOLD?Font.BOLD:Font.NORMAL;
		style |= xfont.getItalic()?Font.ITALIC:Font.NORMAL;
		style |= xfont.getStrikeout()?Font.STRIKETHRU:Font.NORMAL;
		style |= xfont.getUnderline()==HSSFFont.U_NONE?Font.NORMAL:Font.UNDERLINE;
		font.setStyle(style);
 
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor poiColor = palette.getColor(xfont.getColor());
		
		if( poiColor != null && poiColor.getIndex()!=HSSFColor.AUTOMATIC.index)
		{
			font.setColor(colorPOI2Itext(poiColor));
		}
		
		return font;
	}

	public int chooseFontFamily(HSSFFont font, int defaultFontFamily)
	{
		String fontName = font.getFontName();
		int fontFamily = defaultFontFamily;
		if( "Arial".equals(fontName)) {	fontFamily = Font.HELVETICA;	}
		else if( "Courier".equals(fontName)) {	fontFamily = Font.COURIER;	}
		else if( "Courier New".equals(fontName)) {	fontFamily = Font.COURIER;	}
		else if( "Times New Roman".equals(fontName)) {	fontFamily = Font.TIMES_ROMAN;	}
		return fontFamily;
	}
	public Font chooseFont(short fontIdx )
	{
		return  chooseFont(workbook.getFontAt(fontIdx));
	}

	public int getDefaultCharWidth()
	{
		char defaultChar = '0';
    HSSFFont defaultFont = workbook.getFontAt((short) 0);
    AttributedString str = new AttributedString("" + defaultChar);
    copyAttributes(defaultFont, str, 0, 1);
    FontRenderContext frc = new FontRenderContext(null, true, true);
    TextLayout layout = new TextLayout(str.getIterator(), frc);
    int defaultCharWidth = (int)layout.getAdvance();
		return defaultCharWidth;
	}

	private void copyAttributes(HSSFFont font, AttributedString str, int startIdx, int endIdx) 
	{
    str.addAttribute(TextAttribute.FAMILY, font.getFontName(), startIdx, endIdx);
    str.addAttribute(TextAttribute.SIZE, new Float(font.getFontHeightInPoints()));
    if (font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD) str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, startIdx, endIdx);
    if (font.getItalic() ) str.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, startIdx, endIdx);
    if (font.getUnderline() == HSSFFont.U_SINGLE ) str.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, startIdx, endIdx);
	}
	
}
