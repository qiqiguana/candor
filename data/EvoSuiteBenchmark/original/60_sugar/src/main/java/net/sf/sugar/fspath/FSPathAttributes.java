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
 * FSPathAttributes.java
 *
 * Created on 18 September 2006, 01:18
 *
 * 
 */

package net.sf.sugar.fspath;

/**
 *  An enum of the attribute names to specify for a specific file.
 *  These are just taken from java.io.File
 *
 *  @author keith
 */
public enum FSPathAttributes {

    canRead(),
    canWrite(),
    exists(),
    absolutePath(),
    canonicalPath(),
    name(),
    parent(),
    path(),
    isAbsolute(),
    isDirectory(),
    isFile(),
    isHidden(),
    lastModified(),
    length(),
    file(), //name of a file tag
    dir();


}
