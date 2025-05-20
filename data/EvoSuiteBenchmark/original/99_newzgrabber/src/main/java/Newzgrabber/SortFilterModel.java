package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;

public class SortFilterModel extends AbstractTableModel
	implements MouseListener
{  

   private static boolean AscSort = true;
   private JTable table;
   private boolean FORCESORT = false;
   private int SORTCOLUMN;
   private int TIEBREAKER = 3; // secondary sort default is message id

   public SortFilterModel(TableModel m)
   {  
      model = m;
      AscSort = true;
      this.setRowsColumns();
   }

   
   public void sortTable(int column)
   {
    if(table == null) return;
    FORCESORT = true;
    SORTCOLUMN = column;
    Date d = new Date();
    MouseEvent me = new MouseEvent(table,this.hashCode(),d.getTime(),
	0,0,0,1,false);
    this.mouseClicked(me);
   }

   public void setSort(boolean b)
   {
    AscSort = b;
   }

   private void setRowsColumns()
   {
      rows = new Row[model.getRowCount()];
      for (int i = 0; i < rows.length; i++)
      {  rows[i] = new Row();
         rows[i].index = i;
      }
   }

   public void sort(int c)
   {  sortColumn = c;
      Arrays.sort(rows);
      fireTableDataChanged();
   }

   public void updateTable()
   {
    this.setRowsColumns();
    fireTableDataChanged();
   }

   public void setTable(JTable t)
   {
    table = t;
    table.getTableHeader().addMouseListener(this);
   }

   public void mousePressed(MouseEvent me) {}
   public void mouseEntered(MouseEvent me) {}
   public void mouseExited(MouseEvent me) {}
   public void mouseReleased(MouseEvent me) {}

   public void mouseClicked(MouseEvent event)
   {
    if(table != null)
    {
     int tableColumn = 3;
     if(FORCESORT)
     {
      tableColumn = SORTCOLUMN;
     }
     else
     {
      tableColumn = table.columnAtPoint(event.getPoint());
     }
     int modelColumn = table.convertColumnIndexToModel(tableColumn);
     this.setRowsColumns();
     this.sort(modelColumn);  
     AscSort = !AscSort; 
     FORCESORT = false;
    }
   }
/*
   public void addMouseListener(final JTable table)
   {  
	final SortFilterModel tmpmodel = this;
	table.getTableHeader().addMouseListener(new MouseAdapter()
         {  public void mouseClicked(MouseEvent event)
            {  // check for double click

               // find column of click and
//		System.out.println("Mouse clicked on table");
               int tableColumn
                  = table.columnAtPoint(event.getPoint());

               // translate to table model index and sort
               int modelColumn
                  = table.convertColumnIndexToModel(tableColumn);
		tmpmodel.setRowsColumns();
               tmpmodel.sort(modelColumn);
		SortFilterModel.AscSort = !SortFilterModel.AscSort;
            }
         });
   }
*/

   /* compute the moved row for the three methods that access
      model elements
   */

   public Object getValueAt(int r, int c)
   {  
	Object o = null;
	try
	{
		o = model.getValueAt(rows[r].index,c); 
	}
  	catch(Exception e)
	{
		o = null;
	}
	return o;
   }

   public boolean isCellEditable(int r, int c)
   {  return model.isCellEditable(rows[r].index, c);
   }

   public void setValueAt(Object aValue, int r, int c)
   {  model.setValueAt(aValue, rows[r].index, c);
   }

   /* delegate all remaining methods to the model
   */

   public int getRowCount()
   {  return model.getRowCount();
   }

   public int getColumnCount()
   {  return model.getColumnCount();
   }

   public String getColumnName(int c)
   {  return model.getColumnName(c);
   }

   public Class getColumnClass(int c)
   {  return model.getColumnClass(c);
   }

   /* this inner class holds the index of the model row
      Rows are compared by looking at the model row entries
      in the sort column
   */

   private class Row implements Comparable
   {  public int index;
      public int compareTo(Object other)
      {  Row otherRow = (Row)other;
         Object a = model.getValueAt(index, sortColumn);
         Object b = model.getValueAt(otherRow.index, sortColumn);
         if (a instanceof Comparable) {
		if(SortFilterModel.AscSort) {
			if(((Comparable)a).compareTo(b) == 0) {
				Object tmpa = model.getValueAt(index,
					TIEBREAKER);
				Object tmpb = model.getValueAt(
					otherRow.index,TIEBREAKER);
				return ((Comparable)tmpa).compareTo(tmpb);
			} else {
				return ((Comparable)a).compareTo(b);
			}
		} else {
			if(((Comparable)b).compareTo(a) == 0) {
				Object tmpa = model.getValueAt(index,
					TIEBREAKER);
				Object tmpb = model.getValueAt(
					otherRow.index,TIEBREAKER);
				return ((Comparable)tmpb).compareTo(tmpa);
			} else {
				return ((Comparable)b).compareTo(a);
			}
		}
         } else {
		if(SortFilterModel.AscSort)
			return index - otherRow.index;
		else
			return otherRow.index - index;
	}
      }
   }

   private TableModel model;
   private int sortColumn;
   private Row[] rows;
}