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
    public FSPathResultList each(Callback callback) throws IOException;
}
