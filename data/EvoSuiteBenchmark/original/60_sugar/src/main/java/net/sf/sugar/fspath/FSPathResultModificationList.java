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
 * FSPathResultModificationList.java
 *
 * Created on 08 April 2008, 17:42
 *
 */

package net.sf.sugar.fspath;

import java.util.List;

/**
 *	This class is returned by the copy(), rename(), move() and delete() methods of FSPathResultList.
 *
 * @author kbishop
 */
public interface FSPathResultModificationList extends List<FSPathResult> {

    /**
     *  This is a convenience method for the developer to define some custom action in the event of a failed modification to the filesystem.
     *
     */
    public FSPathResultModificationList onFailure(Callback callback);

	/**
	 *  If a modification to the filesystem succeeds then the correspondng FSPathResult objects are marked as successes.
	 */
    public FSPathResultList getSuccesses();

	/**
	 *  If a modification to the filesystem has failed then the correspondng FSPathResult objects are marked as failures.
	 */
    public FSPathResultList getFailures();

	/**
	 *  Returns true if this list has any failed modifications.
	 */
    public boolean hasFailures();

    public void addSuccess(FSPathResult successResult);

    public void addFailure(FSPathResult failureResult);
}
