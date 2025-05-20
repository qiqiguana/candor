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

    public Iterator<TableColumn> getColumns(boolean onlyVisible) {
        return (onlyVisible ? tableColumns.iterator() : allTableColumns.iterator());
    }
}
