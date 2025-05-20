/**
 * Copyright 2008 (C) Keith Bishop (bishi@users.sourceforge.net)
 *
 * All rights reserved.
 *
 * This file is part of FSPath.
 *
 * FSPath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSPath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSPath.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * FSPathFactory.java
 *
 * Created on 06 April 2008, 17:04
 *
 *
 */

package net.sf.sugar.fspath;

import java.io.File;

/**
 *
 * @author kbishop
 */
public class FSPathFactory {

    /** Creates a new instance of FSPathFactory */
    public FSPathFactory() {
    }

    /**
     *  @returns FSPath a new DefaultFSPath instance which uses the current user directory (System.getProperty("user.dir")) to search from.
     */
    public static FSPath newFSPath() {
        return new DefaultFSPath(new File(System.getProperty("user.dir")));
    }

	/**
	 *  @returns FSPath a new DefaultFSPath instance which uses the directory provided to search from.
	 */
    public static FSPath newFSPath(File file) {
        return new DefaultFSPath(file);
    }

	/**
	 *  @returns FSPath a new DefaultFSPath instance which uses the directory path String provided to search from.
	 */
    public static FSPath newFSPath(String path) {
        return new DefaultFSPath(new File(path));
    }

}
