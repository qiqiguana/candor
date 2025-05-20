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

    /**
     * Get the format index that matches the given format
     *  string, creating a new format entry if required.
     * Aliases text to the proper format as required.
     *
     * @param format string matching a built in format
     * @return index of format.
     */
    public short getFormat(String format);
}
