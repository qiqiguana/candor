package de.huxhorn.lilith.swing.table.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * This class is based on code and ideas from Stephen Kelvin (mail at StephenKelvin.de) and Thomas Darimont.
 */
public class PersistentTableColumnModel extends DefaultTableColumnModel {

    protected List<TableColumn> allTableColumns = new ArrayList<TableColumn>();

    /**
     * Creates an extended table column model.
     */
    public PersistentTableColumnModel() {
    }

    /**
     * Should be implemented by subclasses if needed.
     */
    protected void initColumns();

    // listeners will receive columnAdded()/columnRemoved() event
    public void setColumnVisible(TableColumn column, boolean visible);

    /**
     * Makes all columns in this model visible
     */
    public void setAllColumnsVisible();

    /**
     * Checks wether the specified column is currently visible.
     *
     * @param aColumn column to check
     * @return visibility of specified column (false if there is no such column at all. [It's not visible, right?])
     */
    public boolean isColumnVisible(TableColumn aColumn);

    /**
     * Append <code>column</code> to the right of exisiting columns.
     * Posts <code>columnAdded</code> event.
     *
     * @param column The column to be added
     * @throws IllegalArgumentException if <code>column</code> is <code>null</code>
     * @see #removeColumn
     */
    @Override
    public void addColumn(TableColumn column);

    /**
     * Removes <code>column</code> from this column model.
     * Posts <code>columnRemoved</code> event.
     * Will do nothing if the column is not in this model.
     *
     * @param column the column to be added
     * @see #addColumn
     */
    @Override
    public void removeColumn(TableColumn column);

    /**
     * Moves the column from <code>oldIndex</code> to <code>newIndex</code>.
     * Posts  <code>columnMoved</code> event.
     * Will not move any columns if <code>oldIndex</code> equals <code>newIndex</code>.
     *
     * @throws IllegalArgumentException if either <code>oldIndex</code> or
     *                                  <code>newIndex</code>
     *                                  are not in [0, getColumnCount() - 1]
     * @param	oldIndex			index of column to be moved
     * @param	newIndex			new index of the column
     */
    @Override
    public void moveColumn(int oldIndex, int newIndex);

    /**
     * Returns the total number of columns in this model.
     *
     * @param onlyVisible if set only visible columns will be counted
     * @return the number of columns in the <code>tableColumns</code> array
     * @see	#getColumns
     */
    public int getColumnCount(boolean onlyVisible);

    /**
     * Returns an <code>Enumeration</code> of all the columns in the model.
     *
     * @param onlyVisible if set all invisible columns will be missing from the enumeration.
     * @return an <code>Enumeration</code> of the columns in the model
     */
    public Iterator<TableColumn> getColumns(boolean onlyVisible);

    /**
     * Returns the position of the first column whose identifier equals <code>identifier</code>.
     * Position is the the index in all visible columns if <code>onlyVisible</code> is true or
     * else the index in all columns.
     *
     * @return the index of the first column whose identifier
     *         equals <code>identifier</code>
     * @throws IllegalArgumentException if <code>identifier</code>
     *                                  is <code>null</code>, or if no
     *                                  <code>TableColumn</code> has this
     *                                  <code>identifier</code>
     * @param	identifier the identifier object to search for
     * @param	onlyVisible if set searches only visible columns
     * @see		#getColumn
     */
    public int getColumnIndex(Object identifier, boolean onlyVisible);

    public List<TableColumnLayoutInfo> getColumnLayoutInfos();

    public static class TableColumnLayoutInfo implements Serializable {

        private String columnName;

        private int width;

        private boolean visible;

        public TableColumnLayoutInfo() {
        }

        public TableColumnLayoutInfo(String columnName, int width, boolean visible) {
            this.columnName = columnName;
            this.width = width;
            this.visible = visible;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        @Override
        public String toString() {
            return "TableColumnLayoutInfo[columnName=" + columnName + ", width=" + width + ", visible=" + visible + "]";
        }
    }
}
