package net.sf.sugar.fspath;

import java.io.File;

/**
 * @author kbishop
 */
public class FSPathFactory {

    /**
     * Creates a new instance of FSPathFactory
     */
    public FSPathFactory() {
    }

    /**
     *  @returns FSPath a new DefaultFSPath instance which uses the current user directory (System.getProperty("user.dir")) to search from.
     */
    public static FSPath newFSPath();

    /**
     *  @returns FSPath a new DefaultFSPath instance which uses the directory provided to search from.
     */
    public static FSPath newFSPath(File file);

    /**
     *  @returns FSPath a new DefaultFSPath instance which uses the directory path String provided to search from.
     */
    public static FSPath newFSPath(String path);
}
