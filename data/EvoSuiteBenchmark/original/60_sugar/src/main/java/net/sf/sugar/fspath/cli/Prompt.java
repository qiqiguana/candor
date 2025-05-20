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
 * Prompt.java
 *
 * Created on 24 April 2008, 22:15
 *
 */

package net.sf.sugar.fspath.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author kbishop
 * @version $Id$
 */
class Prompt {
    
    private String promptText;
    private BufferedReader in;
    private PrintStream out;
    private PrintStream err;
    
    /** Creates a new instance of Prompt */
    public Prompt(InputStream in, PrintStream out, PrintStream err) {
        this.out = out;
        this.err = err;
        this.in = new BufferedReader(new InputStreamReader(in));
    }
    
    public String readLine() throws IOException {
        out.print(promptText);
        out.flush();
        return in.readLine();
    }
    
    public String getPromptText() {
        return promptText;
    }
    
    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }
    
    public void close() {
        try {
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace(err);
        }
    }
    
}
