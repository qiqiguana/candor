package net.sf.sugar.fspath;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kbishop
 * @version $Id$
 */
public class FSPathResultListImpl extends ArrayList<FSPathResult> implements FSPathResultList {

    /**
     * Creates a new instance of FSPathResultListImpl
     */
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
    public FSPathResultList each(Callback callback) throws IOException;

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
    public FSPathResultModificationList delete() throws IOException, OperationNotPermittedException;

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
    public FSPathResultModificationList copy(String destinationDirPath) throws IOException, OperationNotPermittedException;

    public boolean isListOfFiles();

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
    public FSPathResultModificationList rename(String matchExpression, String replaceExpresion) throws IOException, OperationNotPermittedException;

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
     */
    public FSPathResultModificationList move(String destinationPath) throws IOException, OperationNotPermittedException;
}
