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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Represents a rectangular template area in the template workbook.
 * Encapsulated accompanying data such as template parameters,  
 * columns permutation information and the original template sheet object.
 *   
 * @author Dmitriy Kumshayev
 */
class StaticTemplate extends Template
{
	private final List<MergeRegion> mergeRegions = new LinkedList<MergeRegion>();
	List<Integer> selectList = null;
	private List<Integer> absSelectList = null;
	private final Region region = new Region();
	private Map<Integer,Map<Integer,Integer>> parameterIndexMap = null;
	
	public StaticTemplate(String name, HSSFSheet sheet)
	{
		super(name, sheet);
	}
	
	public List<MergeRegion> getMergeRegions()
	{
		return mergeRegions;
	}
	
	public void addMergeRegion(MergeRegion mreg)
	{
		mergeRegions.add(mreg);
	}

	public void setSelectList(List<Integer> selectList)
	{
		this.selectList = selectList;

		// calculate parameterIndexMap
		if( selectList!=null)
		{
			// Initialize absolute numbers of the selected columns
			this.absSelectList = new ArrayList<Integer>(selectList.size());
			for (int c = 0; c < width(); c++)
			{
				absSelectList.add(absoluteColumn(c));
			}
			
			parameterIndexMap = new HashMap<Integer, Map<Integer, Integer>>();
			int idx = 1;
			int h = height();
			int w = width();
			for( int r=0; r<h; r++)
			{
				for( int c=0; c<w; c++)
				{
					int col = selectList.get(c);
					Parameter p = getParameter(r, col);
					if( p!=null )
					{
						Map<Integer, Integer> rowIndexMap = parameterIndexMap.get(r);
						if( rowIndexMap==null)
						{
							rowIndexMap = new HashMap<Integer, Integer>();
							parameterIndexMap.put(r, rowIndexMap);
						}
						rowIndexMap.put(c, idx++);
					}
				}
			}
		}
		else
		{
			parameterIndexMap = null;
			absSelectList = null;
		}
	}
	
	@Override
	public int getRowHeight(int row)
	{
		HSSFRow trow = sheet.getRow(absoluteRow(row));
		if( trow != null )
		{
			return trow.getHeight();
		}
		return sheet.getDefaultRowHeight();
	}
	
	/**
	 * @param r - 0-based relative row number within the template
	 * @return
	 */
	private int absoluteRow(int r)
	{
		return start().row()+r;
	}

	@Override
	public boolean isRowBroken(int r)
	{
		return sheet.isRowBroken(absoluteRow(r));
	}
	/**
	 * @param r - 0-based relative column number within the template
	 * @return
	 */
	public int absoluteColumn(int c)
	{
		if( selectList != null )
		{
			return start().column()+selectList.get(c);
		}
		else
		{
			return start().column()+c;
		}
	}

	@Override
	public int getParameterIndex(int r, int c)
	{
		Integer idx = -1;
		if( selectList == null )
		{
			Parameter p = super.getParameter(r, c);
			if( p!=null )
			{
				idx = p.getIndex();
			}
		}
		else
		{
				idx = parameterIndexMap.get(r).get(c);
				if( idx==null)
				{
					idx=-1;
				}
		}
		return idx;
	}
	
	public HSSFCell getCell(int r, int c)
	{
		HSSFRow tRow = sheet.getRow(absoluteRow(r));
		if( tRow != null )
		{
			return tRow.getCell(absoluteColumn(c));
		}
		return null;
	}

	private int absoluteFirstRow()
	{
		return absoluteRow(0);
	}
	
	private int absoluteFirstColumn()
	{
		return absoluteColumn(0);
	}

	private int absoluteLastRow()
	{
		return absoluteRow(height()-1);
	}
	
	private int absoluteLastColumn()
	{
		return absoluteColumn(width()-1);
	}

	@Override
	public Reference absoluteReference(int r, int c)
	{
		Reference ref = new Reference(absoluteRow(r),absoluteColumn(c));
		return ref;
	}
	
	
	@Override
	public String toString()
	{
		return name+","+super.toString();
	}

	
	@Override
	public int height()
	{
		return end().row()-start().row()+1;
	}

	@Override
	public int width()
	{
		if( selectList != null )
		{
			return selectList.size();
		}
		else
		{
			return end().column()-start().column()+1;
		}
	}
	

	/* Wrapping methods to access region */
	public void setStartReference(Reference start)
	{
		region.setStartReference(start);
	}

	public Reference start()
	{
		return region.start();
	}

	public void setEndReference(Reference end)
	{
		region.setEndReference(end);
	}

	public Reference end()
	{
		return region.end();
	}
	
	/**
	 * @return true if this Template contains the reference
	 */
	public boolean contains(Reference reference)
	{
		if( selectList==null)
		{
			return region.contains(reference);
		}
		else
		{
			return absoluteFirstRow() <= reference.row()
			&& reference.row() <= absoluteLastRow()
			&& absoluteFirstColumn() <= reference.column()
			&& reference.column() <= absoluteLastColumn()
			&& (absSelectList!=null?absSelectList.contains(reference.column()):true);
		}
	}
	
	public boolean contains(Region region)
	{
		return region.contains(region);
	}

}
