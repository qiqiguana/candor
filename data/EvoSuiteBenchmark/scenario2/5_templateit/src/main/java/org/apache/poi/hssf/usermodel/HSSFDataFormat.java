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
    public short getFormat(String format) {
        ListIterator i;
        int ind;
        if (format.toUpperCase().equals("TEXT"))
            format = "@";
        if (!movedBuiltins) {
            i = builtinFormats.listIterator();
            while (i.hasNext()) {
                ind = i.nextIndex();
                if (formats.size() < ind + 1) {
                    formats.setSize(ind + 1);
                }
                formats.set(ind, i.next());
            }
            movedBuiltins = true;
        }
        i = formats.listIterator();
        while (i.hasNext()) {
            ind = i.nextIndex();
            if (format.equals(i.next()))
                return (short) ind;
        }
        ind = workbook.getFormat(format, true);
        if (formats.size() <= ind)
            formats.setSize(ind + 1);
        formats.set(ind, format);
        return (short) ind;
    }
}
