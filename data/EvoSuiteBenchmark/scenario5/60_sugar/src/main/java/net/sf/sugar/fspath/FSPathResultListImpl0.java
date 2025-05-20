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
     *  A convenience method for defining custom filesystem interaction
     *  across the whole list of results.
     *
     *  This method loops through the results and calls the call(Result result)
     *  method of the Callback class passed to it for each individual result.
     *
     * @param Callback - a custom implementation of the Callback interface.
     * @throws IOException
     */
    public FSPathResultList each(Callback callback) throws IOException {
        for (FSPathResult result : this) {
            callback.call(result);
        }
        return this;
    }
}

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
     * @param FSPathResult an FSPathResult object on which to operate on.
     */
    public void call(FSPathResult result);
}
