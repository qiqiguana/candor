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
 * Callback.java
 *
 * Created on 08 April 2008, 17:49
 *
 */

package net.sf.sugar.fspath;

/**
 *  A general purpose interface for implementing a callback class.
 *
 *  Use of callbacks gives deveopers using this api a simple way of extending the
 *  functionality of the api, while preserving the simplicity of the api.
 *
 *  Calbacks are used in the each() method of FSPathResultList, and the onFailure() method
 *  of FSPathResultModificationList.
 *  <br/>
 *  The simplest way to implement this interface is as an anonymous inner class.
 *
 *  For example :
 *  <pre>
 *  result = fspath.query("/dir")
 *                 .each(new Callback() {
 *                         public void call(FSPathResult result) {
 *                         File file = result.getFile();
 *                         System.out.println(file.getName() + " has : "
 *                           + file.listFiles().size() + " children");
 *                         }
 *                        });
 * </pre>
 * This would list the number of children of each directory below the current
 * directory.
 *
 * @author kbishop
 */
public interface Callback {

    /**
     *  This method will be called by any FSPathResultList implementation method
     *  which takes a Callback implementation class as a parameter.
     *
     *  For instance the onFailure method of <code>FSPathResultDeletionList</code> will invoke
     *  this call method once for every failed deletion.
     *
     *  @param FSPathResult an FSPathResult object on which to operate on.
     */
    public void call(FSPathResult result);
}
