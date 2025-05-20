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
 * Commands.java
 *
 * Created on 24 April 2008, 22:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.sugar.fspath.cli;

/**
 *
 * @author kbishop
 */
public enum Commands {
    
    EXIT("exit", "exits FSPathExplorer"),
    CD(  "cd",   "changes the root directory to the directory specified"),
    PWD( "pwd",  "prints the current working directory"),
    HELP("help", "prints this message")
    ;
    
    private final String commandText;
    private final String description;
    
    Commands(String commandText, String description) {
        this.commandText = commandText;
        this.description = description;
    }
    
    public String getCommandText() {
        return this.commandText;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    /**
     *  
     */
    public static boolean isCommand(String value) {
        for(Commands c : Commands.values()) {
            if(c.getCommandText().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
