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
 * FSPathResultList.java
 *
 * Created on 08 April 2008, 15:34
 *
 */

package net.sf.sugar.fspath;

import java.io.IOException;
import java.util.List;

/**
 *  A decorator class for List which enables the copying, renaming and deletion
 *  of each result contained in the list.
 *
 *  The methods delete, copy, rename will only work on FSPathResult objects which 
 *  contain a java.io.File object, and other orm of result, will cause an
 *  OperationNotPermittedException to be thrown.
 *
 * @author kbishop
 * @version $Id$
 */
public interface FSPathResultList extends List<FSPathResult> {

    public FSPathResultList each(Callback callback) throws IOException;
    
    public FSPathResultModificationList delete() throws IOException, 
                                                 OperationNotPermittedException;
    
    public FSPathResultModificationList copy(String destinationPath) 
                                                 throws 
                                                 IOException, 
                                                 OperationNotPermittedException;
    
    public FSPathResultModificationList move(String destinationPath)
                                                 throws 
                                                 IOException,
                                                 OperationNotPermittedException;
    
    public FSPathResultModificationList rename(String matchExpression, 
                                               String replaceExpresion) 
                                                 throws 
                                                 IOException, 
                                                 OperationNotPermittedException;
    
}
