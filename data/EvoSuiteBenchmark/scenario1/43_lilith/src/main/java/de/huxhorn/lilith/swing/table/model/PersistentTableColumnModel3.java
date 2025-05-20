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

    public int getColumnIndex(Object identifier, boolean onlyVisible) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier is null");
        }
        List<TableColumn> columns = (onlyVisible ? tableColumns : allTableColumns);
        int noColumns = columns.size();
        TableColumn column;
        for (int columnIndex = 0; columnIndex < noColumns; ++columnIndex) {
            column = columns.get(columnIndex);
            if (identifier.equals(column.getIdentifier())) {
                return columnIndex;
            }
        }
        throw new IllegalArgumentException("Identifier not found");
    }
}
