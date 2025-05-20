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
 * FSPathResultListImpl.java
 *
 * Created on 08 April 2008, 18:00
 *
 */

package net.sf.sugar.fspath;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kbishop
 * @version $Id$
 */
public class FSPathResultListImpl extends ArrayList<FSPathResult> implements FSPathResultList  {

    /** Creates a new instance of FSPathResultListImpl */
    public FSPathResultListImpl() {
    }

	/**
	 *  A convenience method for defining custom filesystem interaction
	 *  across the whole list of results.
	 *
	 *  This method loops through the results and calls the call(Result result)
	 *  method of the Callback class passed to it for each individual result.
	 *
	 *  @param Callback - a custom implementation of the Callback interface.
	 *  @throws IOException
 	 */
    public FSPathResultList each(Callback callback) throws IOException {
        for (FSPathResult result : this) {
            callback.call(result);
        }
        return this;
    }

    /**
     *  Deletes each file contained in this FSPathResultList.
     *  <br/>
     *  <pre>
     *  ************************************************************************
     *  *               IMPORTANT !!!!!    Use with caution                    *
     *  *   This method makes it extremely easy to trash your filesystem       *
     *  *   Its advised that FSPath queries are tested thouroughly before use  *
     *  *   in order to verify which files would be deleted                    *
     *  *                                                                      *
     *  ************************************************************************
     *  </pre>
     *  @returns FSPathResultModificationListImpl - all successfully deleted files<br/>
     *  will be added as a success, and the failures will be added as failures.
     *
     *  @throws IOException - NOTE this method does not currently thrown an IOException
     *  @throws OperationNotPermittedException - this exception will be thrown if<br/>
     *  The FSPathResult objects contained in this FSPathResultList don't contain<br/>
     *  java.io.File objects<br/>
     *  (i.e the FSPath query was written to return Boolean, String nor numerical results).
     */
    public FSPathResultModificationList delete() throws IOException, OperationNotPermittedException {

        if (! isListOfFiles()) {
            throw new OperationNotPermittedException("Delete is only permitted on FSPathResult objects containing a File object");
        }

        FSPathResultModificationList deletionList = new FSPathResultModificationListImpl();

        for(FSPathResult result : this) {
            try {
                File file = result.getFile();
                boolean success = file.delete();

                if (success) {
                    deletionList.addSuccess(result);
                } else {
                    deletionList.addFailure(result);
                }

            } catch (Exception e) {
                //todo: log this ?
                deletionList.addFailure(result);
            }
        }

        return deletionList;
    }

    /**
     *  This method will copy each file contained in this FSPathResultList to the
     *  destination path supplied.
     *
     *  @param String - the destination path which you would like to copy files to.
     *
     *  @returns FSPathResultModificationListImpl - all successfully copied files
     *  will be added as a success, and the failures will be added as failures.
     *
     *  @param String the absolute or realtive path of the destination Directory
     *  @throws IOException - NOTE this is currently not thrown by this method.
     *  @throws OperationNotPermittedException - this exception is thrown upon
     *  the following conditions :<br/>
     *  <br/>
     *  1)  The FSPathResult objects contained in this FSPathResultList don't contain<br/>
     *      java.io.File objects<br/>
     *      (i.e the FSPath query was written to return Boolean, String nor numerical results).<br/>
     *  2)  The directory denoted by the destination path doesn't exist.<br/>
     *  3)  The destination path doesn't resolve to a directory.<br/>
     *  4)  The destination path isn't writeable.<br/>
     *  5)  The current java process doesn't have sufficient priveledges to<br/>
     *      access the destination path.<br/>
     */
    public FSPathResultModificationList copy(String destinationDirPath)
                                                throws IOException,
                                                OperationNotPermittedException {

        if (! isListOfFiles()) {
            throw new OperationNotPermittedException("Copy is only permitted on FSPathResult objects containing a java.io.File object");
        }

        File destinationDir = new File(destinationDirPath);

        try {
            if (! destinationDir.exists()) {
                throw new OperationNotPermittedException("Unable to copy to a directory that doesn't exist");
            }

            if (! destinationDir.isDirectory()) {
                throw new OperationNotPermittedException("Destination path " + destinationDir.getAbsolutePath() + " does not resolve to a directory");
            }

            if (! destinationDir.canWrite()) {
                throw new OperationNotPermittedException("Desination path " + destinationDir.getAbsolutePath() + " is not writable");
            }

        } catch (SecurityException se) {
            throw new OperationNotPermittedException("The current process does not have sufficent priveledges to access " + destinationDir.getAbsolutePath(), se);
        }

        FSPathResultModificationList results = new FSPathResultModificationListImpl();

        for (FSPathResult result : this) {

            File destinationFile = new File(destinationDir + result.getFile().getName());

            try {

                FileReader inputReader = new FileReader(result.getFile());
                FileWriter outputReader = new FileWriter(destinationFile);

                int charsRead = 0;

                while ((charsRead = inputReader.read()) != -1) {
                    outputReader.write(charsRead);
                }

                inputReader.close();
                outputReader.close();

                results.addSuccess(new FSPathResult(destinationFile));

            } catch (Exception e) {
                results.addFailure(new FSPathResult(destinationFile));
            }
        }

        return results;
    }

    public boolean isListOfFiles() {
        return (this.size() > 0 && this.get(0).getFile() != null);
    }

	/**
	 *  Renames each file in the FSPathResultList based on a regex match
	 *  expression and a replace expression.
	 *  <br/>
	 *  This method is designed to enable simple renaming i.e. renaming from<br/>
	 *  "a.txt" to "b.txt" but also complex renaming using regular expressions.<br/>
	 *  <br/>
	 *  Example simple renaming : <br/>
	 *  <pre>fspath.query("/dir[@name='logs']/file[@name='error.log']").rename("error.log", "error.log.1");</pre> <br/>
	 *  This would work fine for a single file but not much use for multiple files.<br/>
	 *  <br/>
	 *  Example complex renaming : <br/>
	 *  <br/>
	 *  Imagine a directory full of files with a format such as : <br/>
	 *  <pre>
	 *  appLog-01_01_2008.log.1
	 *  appLog-01_01_2008.log.2
	 *  ...
	 *  </pre>
	 *  Now imagine that we wanted to rename the files so that they end in .log but they also keep their<br/>
	 *  uniqueness (i.e. the number at the end of the file needs to move to a new position in the filename)<br/>
	 *  <br/>
	 *  The following code expression would work :<br/>
	 *  <pre>fspath.query("dir[@name = 'logs']/file").rename("(.*)\.log\.([0-9]+)", "$1_$2.log");</pre> <br/>
	 *  Here the matchExpression has two capturing groups, one being everything up to the '.log' in the filename, <br/>
	 *  and the other being the number after the ".log." .<br/>
	 *  The replace expression simply specifies that the new file name will have the text in the first capturing group,<br/>
	 *  followed by a "_" then the text in the second capturing group and then ".log".
	 */
    public FSPathResultModificationList rename(String matchExpression,
                                               String replaceExpresion)
                                               throws
                                                IOException,
                                                OperationNotPermittedException {

        if (! isListOfFiles()) {
            throw new OperationNotPermittedException("Copy is only permitted on FSPathResult objects containing a java.io.File object");
        }

        Pattern pattern = Pattern.compile(matchExpression);

        FSPathResultModificationList results = new FSPathResultModificationListImpl();

        for (FSPathResult result : this) {

            File origFile = null;
            File newFile = null;
            try {
                origFile = result.getFile();
                Matcher matcher = pattern.matcher(origFile.getName());

                String newName = matcher.replaceAll(replaceExpresion);
                 newFile = new File(newName);

                origFile.renameTo(newFile);

                results.addSuccess(new FSPathResult(newFile));

            } catch (Exception e) {
                results.addFailure(new FSPathResult(newFile));
            }
        }

        return results;
    }

    /**
     *  Moves each file in the list to the specified desination path.
     *
     *  This method effecively calls copy() and then delete() on itself.
     *  If any file fails to sucessfully copy, then this method 'fails fast'
     *  and returns the results of the copy. This should prevent the situation arising
     *  where the copied files are completely deleted.
     *  If the copy suceeds, then it will attempt to delete the original files.
     *
     *  @param String - the directory path to move the files to.
     *  @throws OperationNotPermittedException - see the comments for <code>copy</code>
     *
     */
    public FSPathResultModificationList move(String destinationPath)
                                                throws
                                                IOException,
                                                OperationNotPermittedException {

        if (! isListOfFiles()) {
            throw new OperationNotPermittedException("move is only permitted on FSPathResult objects containing a java.io.File object");
        }

        FSPathResultModificationList copyResults = this.copy(destinationPath);

        //if we detect a failure then cease what we're doing and return the results so far
        if (copyResults.hasFailures()) {
            return copyResults;
        }

        //if we're happy with the copy, delete the original files,
        //and return the results of the deletion
        return this.delete();

    }

}
