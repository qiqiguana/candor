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

package net.sf.sugar.fspath.cli;
/*
 * FSPathExplorer.java
 *
 * Created on 04 April 2008, 16:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.sugar.fspath.FSPath;
import net.sf.sugar.fspath.FSPathAttributes;
import net.sf.sugar.fspath.FSPathFactory;
import net.sf.sugar.fspath.FSPathResult;
import net.sf.sugar.fspath.FSPathResultList;


/**
 *  This is a simple command line tool for interacting with the filesystem
 *
 * @author kbishop
 */
public class FSPathExplorer {
   
    private File rootPath;
    
    private String fsPathQuery = "";
    
    private FSPath fsPath;
    
    private Prompt prompt;
    
    private PrintStream out;
    
    private PrintStream err;
    
    private enum Opt {
        path, p, find, f
    }
    
    /** Creates a new instance of FSPathExplorer */
    public FSPathExplorer() {
        this.out = System.out;
        this.err = System.err;
        this.prompt = new Prompt(System.in, out, err);
        this.prompt.setPromptText("fspath>");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //checkArgs(args);
        
        showWelcome();
        
        FSPathExplorer explorer = new FSPathExplorer();
        
        explorer.configureExplorer(args);
        
        explorer.createFSPath();
        
        explorer.start();

    }
    
//    protected static void checkArgs(String[] args) {
//        if(args.length < 1) {
//            showUsage();
//            System.exit(1);
//        }
//    }
    
    
//    protected static void showUsage() {
//        
//        StringBuffer usage = new StringBuffer("usage : java -jar FSPathExplorer [options] <-f|-find> <FSPath_query>");
//        usage.append("\n\n\t Options : ")
//               .append("\n\t\t -path <root_path> : the relative or absolute path of the directory to start from.")
//               .append("\n\t\t -p <root_path> :  see -path.")
//               .append("\n\n\t Example FSPath queries :")
//               .append("\n\t\t //dir[@name = 'var']/dir[@name = 'www']/dir[@name = 'htdocs']/file[contains(name, '.html')]");
//        
//    }
    
    protected static void showWelcome() {
        
        StringBuffer welcome = 
            new StringBuffer("\n+-------------------------------------------------------+");
              welcome.append("\n|                                                       |")
                     .append("\n| FSPathExplorer                                        |")
                     .append("\n|                                                       |")
                     .append("\n+-------------------------------------------------------+\n")
                     .append("\nType 'help' for commands...\n")
                     .append("\nScanning filesystem metadata...\n")
                     .append("\n+-------------------------------------------------------+")
                     .append("\n| PLEASE NOTE running FSPathExplorer from the top level |")
                     .append("\n| of your filesystem is not advised                     |")
                     .append("\n+-------------------------------------------------------+");
        
        System.out.println(welcome);
    }
    
    protected static void showHelp() {
        
        StringBuffer help = new StringBuffer("FSPathExplorer help :");
        help.append("\n\nEnter an FSPath query to search the filesystem")
            .append("\n\nExamples :")
            .append("\n\n1) //dir[@name = 'foo'] \n   finds any directory under the current directory named 'foo'")
            .append("\n\n2) //dir[@name = 'src']//file[@length > 10000] \n   finds any file which is nested anywhere below a 'src' directory")
            .append("\n   with a file size greater than 10000 bytes")
            .append("\n\n3) //dir[fs:matches(@name, '^[.].*')] \n   finds all directories in the directory sructure which begin with a '.'");
            
        help.append("\n\nAttributes : (see javadoc for java.io.File for more info)")
            .append("\n");
        
        for (FSPathAttributes a : FSPathAttributes.values()) {
            if (!(a.name().equals("dir") || a.name().equals("file"))) {
                help.append("\n").append("@").append(a);
            }
        }
        
        help.append("\n\nCommands :\n");
        
        for (Commands c : Commands.values()) {
            help.append("\n").append(c.getCommandText()).append("\t: ").append(c.getDescription());
        }
        
        System.out.println(help.append("\n"));
    }
    
    /**
     *  Assumes args is not null or 0 length.
     */
    protected void configureExplorer(String[] args) {
              
        Map<Opt, Integer> arguments = new HashMap<Opt, Integer>();
        
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-" + Opt.p.toString()) || args[i].equals("-" + Opt.path.toString())) {
                arguments.put(Opt.path, i);
            }
            
            if (args[i].equals("-" + Opt.f.toString()) || args[i].equals("-" + Opt.find.toString())){              
                arguments.put(Opt.find, i);
            }
        }
        
        Integer pathIndex = arguments.get(Opt.path);
        
        if (pathIndex != null) {
            this.rootPath = new File(args[pathIndex.intValue() + 1]);
        }  
        
        Integer queryIndex = arguments.get(Opt.find);
        
        if (queryIndex != null) {
            for(int i = (queryIndex + 1); i < args.length; i++) {
                this.fsPathQuery += args[i];
            }
        } 
        
    }
    
    protected void createFSPath() {
        if (this.rootPath != null) {
            this.fsPath = FSPathFactory.newFSPath(this.rootPath);
        } else {
            this.fsPath = FSPathFactory.newFSPath();
        }

    }
    
    protected void start() {
        
        boolean running = true;
        
        while (running) {  

            try {
                String currentExpression = this.prompt.readLine();
                
                String[] expressionWords = currentExpression.split(" ");
                
                if (expressionWords[0] != null && Commands.isCommand(expressionWords[0])) {

                    if (expressionWords[0].equals(Commands.EXIT.getCommandText())) {
                        this.prompt.close();
                        out.println("exiting.....");
                        break;
                    }
                    
                    if (expressionWords[0].equals(Commands.PWD.getCommandText())) {
                        out.println("Current directory : " + this.fsPath.getRootDirectory().getAbsolutePath());
                    }
                    
                    if (expressionWords[0].equals(Commands.CD.getCommandText())) {
                        try {
                            File newDir = new File(expressionWords[1]);
                            if (newDir.exists() 
                                && newDir.isDirectory()
                                && newDir.canRead()) {
                                
                                this.fsPath = FSPathFactory.newFSPath(newDir);
                                
                                out.println("changed to : " + newDir.getAbsolutePath());
                            }
                        } catch (Throwable t) {
                            out.println("unable to change to " + expressionWords[1] 
                                    + " please make sure that the directory exitst and a is readable");
                        }
                    }
                    
                    if (expressionWords[0].equals(Commands.HELP.getCommandText())) {
                        showHelp();
                    }
                    
                } else {
                    //assume its an FSPath query and try to execute it
                    this.fsPathQuery = currentExpression;
                    
                    FSPathResultList results = this.fsPath.query(this.fsPathQuery);

                    for (FSPathResult result : results) {
                        out.println(result.getFile().getPath());
                    }
                }
          
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
}
