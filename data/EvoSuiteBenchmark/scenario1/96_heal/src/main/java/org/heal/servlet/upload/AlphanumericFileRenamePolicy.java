package org.heal.servlet.upload;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;

/**
 * A FileRenamePolicy that strips non-alphanumeric characters.
 */
public class AlphanumericFileRenamePolicy extends DefaultFileRenamePolicy {

    public File rename(File original) {
        File ret = new File(stripSpecialCharacters(original.getPath()));
        return super.rename(ret);
    }
}
