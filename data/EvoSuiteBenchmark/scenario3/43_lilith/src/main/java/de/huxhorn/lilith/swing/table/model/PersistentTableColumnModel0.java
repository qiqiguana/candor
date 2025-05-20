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
     * Checks wether the specified column is currently visible.
     *
     * @param aColumn column to check
     * @return visibility of specified column (false if there is no such column at all. [It's not visible, right?])
     */
    public boolean isColumnVisible(TableColumn aColumn);
}
