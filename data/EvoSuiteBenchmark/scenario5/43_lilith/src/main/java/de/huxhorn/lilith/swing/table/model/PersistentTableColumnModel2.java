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

    /**
     * Returns an <code>Enumeration</code> of all the columns in the model.
     *
     * @param onlyVisible if set all invisible columns will be missing from the enumeration.
     * @return an <code>Enumeration</code> of the columns in the model
     */
    public Iterator<TableColumn> getColumns(boolean onlyVisible) {
        return (onlyVisible ? tableColumns.iterator() : allTableColumns.iterator());
    }
}
