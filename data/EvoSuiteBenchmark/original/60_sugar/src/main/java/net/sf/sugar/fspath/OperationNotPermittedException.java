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
 * OperationNotPermittedException.java
 *
 * Created on 08 April 2008, 18:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.sugar.fspath;

/**
 *
 * @author kbishop
 */
public class OperationNotPermittedException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>OperationNotPermittedException</code> without detail message.
     */
    public OperationNotPermittedException() {
    }
    
    
    /**
     * Constructs an instance of <code>OperationNotPermittedException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public OperationNotPermittedException(String msg) {
        super(msg);
    }
    
    public OperationNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }
}
