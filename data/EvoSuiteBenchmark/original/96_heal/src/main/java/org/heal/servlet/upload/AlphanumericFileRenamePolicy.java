package org.heal.servlet.upload;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.io.File;

/**
 * A FileRenamePolicy that strips non-alphanumeric characters.
 */
public class AlphanumericFileRenamePolicy extends DefaultFileRenamePolicy {
    /**
     * Eliminates non-alphanumeric characters from filenames.  Exceptions
     * include <code>' '</code>, <code>'.'</code> and <code>'_'</code>
     *
     * @param original Original file reference.
     * @return File reference stripped of non-alphanumeric characters.
     */
    public File rename(File original) {
        File ret = new File(stripSpecialCharacters(original.getPath()));
        return super.rename(ret);
    }

    String stripSpecialCharacters(String filename) {
        // Removes all non-alphanumeric characters (except for '.' and ' ')
        return filename.substring(0, filename.lastIndexOf(File.separator) + 1) +
                filename.substring(filename.lastIndexOf(File.separator) + 1).replaceAll("[^\\w\\. ]", "");
    }
}
