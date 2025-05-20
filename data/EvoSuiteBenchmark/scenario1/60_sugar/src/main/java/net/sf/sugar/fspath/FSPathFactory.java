package net.sf.sugar.fspath;

import java.io.File;

/**
 * @author kbishop
 */
public class FSPathFactory {

    public static FSPath newFSPath() {
        return new DefaultFSPath(new File(System.getProperty("user.dir")));
    }
}
