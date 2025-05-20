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
 * FSPathResult.java
 *
 * Created on 08 April 2008, 16:21
 *
 */

package net.sf.sugar.fspath;

import java.io.File;
import java.net.URI;
import java.util.Date;

/**
 * This class is a simple wrapper for any of the possible result types
 * that may be returned by an FSPath query.
 *
 *
 * Most FSPath queries are likely to return list of java.io.File object
 * which will be wrapped by an instance of this class.
 *
 *
 * @author kbishop
 * @version $Id$
 */
public class FSPathResult {

    private File resultFile;

    private String resultString;

    private Double resultDouble;

    private Date resultDate;

    private URI resultURI;

    private Boolean resultBoolean;

    /** Creates a new instance of FSPathResult */
    public FSPathResult(File file) {
        this.resultFile = file;
    }

    public FSPathResult(String string) {
        this.resultString = string;
    }

    public FSPathResult(Double resultLong) {
        this.resultDouble = resultLong;
    }

    public FSPathResult(Date date) {
        this.resultDate = date;
    }

    public FSPathResult(URI uri) {
        this.resultURI = uri;
    }

    public FSPathResult(Boolean resultBoolean) {
        this.resultBoolean = resultBoolean;
    }

    public File getFile() {
        return this.resultFile;
    }

    public String getString() {
        return this.resultString;
    }

    public Double getDouble() {
        return this.resultDouble;
    }

    public Date getDate() {
        return this.resultDate;
    }

    public URI getURI() {
        return this.resultURI;
    }

    public Boolean getBoolean() {
        return this.resultBoolean;
    }

	/**
	 *  Returns a String representation of the underlying object which
	 *  this class wraps.
	 *
	 *	The following rules apply : <br/>
	 *  <br/>
	 *  if the underlying object is a java.io.File, then getAbsolutePath() method is called on it.<br/>
	 *  if the underlying object is any of Boolean, Date, Double, String or URI then toString() is called in<br/>
	 *
	 *  @returns String
	 */
    public String toString() {

		if (this.resultFile != null) {
			return this.resultFile.getAbsolutePath();
		}

		if (this.resultString != null) {
			return this.resultString;
		}

		if (this.resultDouble != null) {
			return this.resultDouble.toString();
		}

		if (this.resultBoolean != null) {
			return this.resultBoolean.toString();
		}

		if (this.resultDate != null) {
			return this.resultDate.toString();
		}

		if (this.resultURI != null) {
			return this.resultURI.toString();
		}

		return "FSPathResult : empty";
	}
}
