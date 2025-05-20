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
     *
     * @returns FSPathResultModificationListImpl - all successfully deleted files<br/>
     *  will be added as a success, and the failures will be added as failures.
     * @throws IOException - NOTE this method does not currently thrown an IOException
     * @throws OperationNotPermittedException - this exception will be thrown if<br/>
     *  The FSPathResult objects contained in this FSPathResultList don't contain<br/>
     *  java.io.File objects<br/>
     *  (i.e the FSPath query was written to return Boolean, String nor numerical results).
     */
    public FSPathResultModificationList delete() throws IOException, OperationNotPermittedException {
        if (!isListOfFiles()) {
            throw new OperationNotPermittedException("Delete is only permitted on FSPathResult objects containing a File object");
        }
        FSPathResultModificationList deletionList = new FSPathResultModificationListImpl();
        for (FSPathResult result : this) {
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
}
