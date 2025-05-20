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
 * FSPathResultModificationListImpl.java
 *
 * Created on 08 April 2008, 18:33
 *
 */

package net.sf.sugar.fspath;

import java.util.LinkedList;

/**
 *  
 * @author kbishop
 * @version $Id$
 */
public class FSPathResultModificationListImpl extends LinkedList<FSPathResult> implements FSPathResultModificationList  {
    
    private FSPathResult firstSuccess;
    
    private FSPathResult firstFailure;
    
    /**
     * Creates a new instance of FSPathResultModificationListImpl
     */
    public FSPathResultModificationListImpl() {
    }

    public FSPathResultModificationList onFailure(Callback callback) {
        for(FSPathResult result : this.getFailures()) {
            callback.call(result);
        }
        return this;
    }

    public boolean hasFailures() {
        return (this.firstFailure != null);
    }

    public FSPathResultList getSuccesses() {
        
        FSPathResultList successes = new FSPathResultListImpl();
        
        if(this.firstSuccess != null) {
            int index = this.indexOf(this.firstSuccess);
            successes.addAll(this.subList(0, index + 1));           
        }
        return successes;
    }

    public FSPathResultList getFailures() {
        
        FSPathResultList failures = new FSPathResultListImpl();
        
        if(this.firstFailure != null) {
            int index = this.indexOf(this.firstFailure);
            failures.addAll(this.subList(index, this.size()));           
        }
        return failures;
    }
    
    public void addSuccess(FSPathResult successResult) {
        if(this.firstSuccess == null) {
            this.firstSuccess = successResult;
        }
        this.addFirst(successResult); 
    }
    
    public void addFailure(FSPathResult failureResult) {
        if(this.firstFailure == null) {
            this.firstFailure = failureResult;
        }
        this.addLast(failureResult);
    }
    
}
