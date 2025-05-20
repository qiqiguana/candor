/*
 * Copyright(C) 2008-2009 Dmitriy Kumshayev. <dq@mail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.templateit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PdfWriter
{
	private static final Logger logger = Logger.getLogger(PdfWriter.class);
	
	private final HSSFWorkbook workbook;
	private final Poi2ItextUtil poi2ITextUtil;

	private Document doc = null; 
	private HSSFSheet sheet = null;
	private MergeData mergeData = null;
	private int sheetIdx = 0;
	private int lastRow = -1;
	private int lastCol = -1;

	public PdfWriter(InputStream workbookStream) throws IOException
	{
		this(new HSSFWorkbook(workbookStream));
	}

	public PdfWriter(HSSFWorkbook workbook)
	{
		this.workbook = workbook;
		this.poi2ITextUtil = new Poi2ItextUtil(workbook);
		initialize();
	}

	private void initialize()
	{
		doc = null;
		sheet = null;
		mergeData = null;
		sheetIdx = 0;
		lastRow = -1;
		lastCol = -1;
	}
	
	public void writePdf(OutputStream out) throws DocumentException, IOException
	{
			doc = new Document(PageSize.LETTER, 50, 50, 50, 50);
			com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(doc, out);
			writer.setUserunit(1);
			doc.open();
			
			for (sheetIdx = 0; sheetIdx < workbook.getNumberOfSheets(); sheetIdx++)
			{
				sheet = workbook.getSheetAt(sheetIdx);
				logger.debug("===> SHEET "+workbook.getSheetName(sheetIdx)+" <===");
				writeSheet();
			}
			
			doc.close();
			out.flush();
	}
	
	private void writeSheet()
			throws BadElementException, DocumentException
	{
		mergeData = new MergeData(sheet);
		lastRow = sheet.getLastRowNum();
		lastCol = calculateLastColumn(sheet, lastRow);
		float[] widths = getColumnWidths(sheet, lastCol);

		if( widths!= null && widths.length>0)
		{
			writeSheetToPdfPTable(widths);
		}
		else
		{
			logger.warn("Empty sheet: "+workbook.getSheetAt(sheetIdx));
		}
	}
	
	private void writeSheetToPdfPTable(float[] widths) throws DocumentException
	{
		debugPrintWidths(widths);
		PdfPTable table = new PdfPTable(widths.length);
		
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setTotalWidth(widths);
		table.setLockedWidth(true);

		for (int r = 0; r <= lastRow; r++)
		{
			logger.trace("---- row " + r);
			HSSFRow row = sheet.getRow(r);
			float rowHeight = getRowHeight(row,sheet.getDefaultRowHeightInPoints());
			
			for (int c = 0; c <= lastCol; c++)
			{
				int newCol = c;
				
				logger.trace("     >---- col " + c);
				
				PdfPCell cell = null;
				if (row != null)
				{
					HSSFCell xcell = row.getCell(c);
					if (xcell != null)
					{
						Object value = getFormattedValue(r, c, xcell);
						int colspan = calculateColspan(r,c);
						HSSFCell rightColspanCell = null;

						if( colspan > 1)
						{
							newCol += colspan-1;
							rightColspanCell = row.getCell(c+colspan-1);
						}
						cell = createPdfPCell(value,r, c, xcell, rightColspanCell, rowHeight, colspan);
						logger.debug("     >cell(" + r + "," + c + ")="+value + (cell.getColspan()>1?",  colspan="+cell.getColspan():""));
					}
				}
				
				if( cell == null )
				{
					cell = new PdfPCell(new Phrase(""));
					cell.setBorder(Rectangle.NO_BORDER);
					logger.debug("     >cell(" + r + "," + c + ")=<empty>" + (cell.getColspan()>1?",  colspan="+cell.getColspan():""));
				}
				table.addCell(cell);
				c = newCol;
			}

			logger.debug("" + rowHeight);
		}
		doc.add(table);
	}
	private PdfPCell createPdfPCell(Object value, int r, int c, HSSFCell xcell, HSSFCell rightColspanCell, float rowHeight, int colspan)
	{
		HSSFCellStyle xstyle = xcell.getCellStyle();
		HSSFFont xfont = xstyle.getFont(workbook);
		Font font = poi2ITextUtil.chooseFont(xfont);
		
		Phrase p;
		if( value instanceof HSSFRichTextString)
		{
			p = createRichTextPhrase(font, (HSSFRichTextString)value);
		}
		else
		{
			p = new Phrase(value.toString(),font);
		}

		PdfPCell cell = new PdfPCell(p);
		cell.setUseDescender(true);
		cell.setFixedHeight(rowHeight);
		cell.setPadding(1f);
		poi2ITextUtil.copyBackgroundColor(xcell, cell);
		poi2ITextUtil.copyCellHorisontalAlignment(xcell, cell);
		poi2ITextUtil.copyCellBorders(xcell, cell);
		
		if( colspan > 1 )
		{
			cell.setColspan(colspan);
			if(rightColspanCell!=null)
			{
				poi2ITextUtil.resetRightBorder(rightColspanCell, cell);
			}
		}
		return cell;
	}
	
	private Phrase createRichTextPhrase(Font font, HSSFRichTextString rts)
	{
		Phrase p;
		String text = rts.toString();
		if( text.length()>0)
		{
			int nr = rts.numFormattingRuns();
			if( nr > 0)
			{
				p = new Phrase();
				int pos = 0;
				int nextPos = rts.getIndexOfFormattingRun(0);
				p.add(new Chunk(text.substring(pos,nextPos),font));
				
				for (int i = 0; i <nr ; i++)
				{
					pos = nextPos;
					nextPos = i+1<nr?rts.getIndexOfFormattingRun(i+1):text.length();
					font = poi2ITextUtil.chooseFont(rts.getFontOfFormattingRun(i));
					p.add(new Chunk(text.substring(pos,nextPos), font));
				}
			}
			else
			{
				p = new Phrase(text,font);
			}
		}
		else
		{
			p = new Phrase("",font);
		}
		return p;
	}
	
	private int calculateColspan(int r, int c)
	{
		int colspan = 1;
		CellRangeAddress merge = mergeData.getMergeRegionAt(r, c);
		if( merge!=null )
		{
			colspan = merge.getLastColumn()-merge.getFirstColumn()+1; 
		}
		return colspan;
	}
	
	private static float getRowHeight(HSSFRow row, float defaultRowHeight)
	{
		float rowHeight = defaultRowHeight;
		if( row != null )
		{
			if( row.getHeightInPoints() != -1 )
			{
				rowHeight = (float)row.getHeightInPoints(); 
			}
		}
		logger.debug("     >row height = "+rowHeight);
		return rowHeight;
	}
	
	private static void debugPrintWidths(float[] widths)
	{
		if( logger.isTraceEnabled() )
		{
			List<Float> lw = new LinkedList<Float>();
			for (int j = 0; j < widths.length; j++)
			{
				lw.add(widths[j]);
			}
			logger.trace("Set widths: "+lw);
		}
	}

	/**
	 * @param sheet
	 * @param lastRow 0-based number of the last row
	 * @return
	 */
	private static int calculateLastColumn(HSSFSheet sheet, int lastRow)
	{
		int lastCol = 0;
		for (int r = 0; r < lastRow; r++)
		{
			HSSFRow row = sheet.getRow(r);
			if( row != null )
			{
				lastCol = Math.max(lastCol, row.getLastCellNum());
			}
		}
		return lastCol-1;
	}

	/**
	 * @param sheet
	 * @param lastCol - 0-based number of the last column
	 * @return
	 */
	private float[] getColumnWidths(HSSFSheet sheet, int lastCol)
	{
		int defaultCharWidth = poi2ITextUtil.getDefaultCharWidth();
    logger.debug("defaultCharWidth="+defaultCharWidth);
    
		float []widths = new float[lastCol+1];
		for (int j = 0; j < widths.length; j++)
		{
			widths[j]=defaultCharWidth*sheet.getColumnWidth(j)/256f;
		}
		return widths;
	}
	
	private Object getFormattedValue(int r, int c, HSSFCell cell)
	{
		Object value = "";
		try
		{
			int type = cell.getCellType();
			switch (type)
			{
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getRichStringCellValue();
				break;

				case HSSFCell.CELL_TYPE_FORMULA:
					HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator(workbook);
					value = new HSSFDataFormatter().formatCellValue(cell,eval);
				break;

				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = ""+cell.getBooleanCellValue();
				break;
				
				case HSSFCell.CELL_TYPE_NUMERIC:
					value = new HSSFDataFormatter().formatCellValue(cell);
				break;
			}
			
		}
		catch (Exception e)
		{
			logger.debug("Cannot get a text value from cell ("+r+","+c+")");
		}
		return value;
	}
	
	public class MergeData
	{
		private final HSSFSheet sheet;
		private final Map<Integer, Map<Integer, CellRangeAddress>> mergeRegions;
		
		public MergeData(HSSFSheet sheet)
		{
			this.sheet = sheet;
			this.mergeRegions = collectMergeData();
		}
		
		public Map<Integer, Map<Integer, CellRangeAddress>>  collectMergeData()
		{
			Map<Integer,Map<Integer, CellRangeAddress>> mergeRegions = new HashMap<Integer, Map<Integer,CellRangeAddress>>();
			
			for (int j = 0; j < sheet.getNumMergedRegions(); j++)
			{
				CellRangeAddress merge = sheet.getMergedRegion(j);
				int r = merge.getFirstRow();
				int c = merge.getFirstColumn();
				
				Map<Integer, CellRangeAddress> rowMergeRegions = mergeRegions.get(r);
				if( rowMergeRegions==null)
				{
					rowMergeRegions = new HashMap<Integer, CellRangeAddress>();
					mergeRegions.put(r, rowMergeRegions);
				}
				rowMergeRegions.put(c, merge);
			}
			return mergeRegions;
		}
		
		public CellRangeAddress getMergeRegionAt(int r, int c)
		{
			return mergeRegions.get(r)==null? null : mergeRegions.get(r).get(c);
		}
		
	}
	
	
}