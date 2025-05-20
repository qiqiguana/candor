package net.sf.sugar.fspath;

import java.io.File;

/**
 * @author kbishop
 */
public class FSPathFactory {

    /**
     * @returns FSPath a new DefaultFSPath instance which uses the current user directory (System.getProperty("user.dir")) to search from.
     */
    public static FSPath newFSPath() {
        return new DefaultFSPath(new File(System.getProperty("user.dir")));
    }
}
