/*
 * HSSFDataFormat.java
 *
 * Created on December 18, 2001, 12:42 PM
 */
package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.FormatRecord;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class HSSFDataFormat {

    private static List builtinFormats = createBuiltinFormats();

    private Vector formats = new Vector();

    private Workbook workbook;

    // Flag to see if need to
    private boolean movedBuiltins = false;

    /**
     * Construncts a new data formatter.  It takes a workbook to have
     * access to the workbooks format records.
     * @param workbook the workbook the formats are tied to.
     */
    public HSSFDataFormat(Workbook workbook) {
    }

    private static synchronized List createBuiltinFormats();

    public static List getBuiltinFormats();

    public static short getBuiltinFormat(String format);

    /**
     * Get the format index that matches the given format
     *  string, creating a new format entry if required.
     * Aliases text to the proper format as required.
     * @param format string matching a built in format
     * @return index of format.
     */
    public short getFormat(String format);

    public String getFormat(short index);

    public static String getBuiltinFormat(short index);

    public static int getNumberOfBuiltinBuiltinFormats();
}
