package org.asphodel;

import org.asphodel.index.Indexee;
import org.asphodel.index.IndexEngine;
import org.asphodel.index.PlainTextStringContent;

import java.util.Properties;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 *        Date: Apr 4, 2007
 *        Time: 10:36:33 AM
 *        <p/>
 *        util for test convenience
 */
public class FtrTestUtil {
    /**
     * The big dir to reside all the index repository
     */
    public static final String PATH_REPOSITORY_HOUSE = "/tmp4test/index";
    /**
     * dir contains index files.
     */
    public static final String DEFAULT_REPOSITORY_IDENTIFER = "index0";

    public static final String TEXT_STRING_ENGLISH = "Brian Repko has posted \"Caching for JDK 7,\" a post about the dormant state of JSR-107, the JCache specification. There are a number of programmatic caches out there, he says, but there should be a standard. Cameron Purdy, spec lead for JSR-107, cited a lack of time and need as a reason for the JSR's inactivity; anyone interested in helping Brian revive it?(java) ";
    public static final String HTML_STRING_ENGLISH ="<PRE>\n" +
                "public <A HREF=\"../../java/io/File.html\" title=\"class in java.io\">File</A>[] <B>listFiles</B>(<A HREF=\"../../java/io/FilenameFilter.html\" title=\"interface in java.io\">FilenameFilter</A>&copy;filter)</PRE>";


    public static void init()
    {
        Properties props = new Properties();
        props.setProperty("ftr.repository.house",PATH_REPOSITORY_HOUSE);
        props.setProperty("ftr.repository.default",DEFAULT_REPOSITORY_IDENTIFER);
        AsphodelConfig.populateConfig(props);
    }
    /**
     * create index..
     * Because this operation will be called in several classes,so just extract it.
     */
    public static void createIndex() throws FtrException {
        RepositoryManager repositoryManager = new DefaultRepositoryManager();
        //if if exist,then delete first,and recreate,else,create
        if (!repositoryManager.exist(DEFAULT_REPOSITORY_IDENTIFER)) {
            repositoryManager.createRepository(DEFAULT_REPOSITORY_IDENTIFER);
        } else {
            repositoryManager.deleteRepository(DEFAULT_REPOSITORY_IDENTIFER);
            repositoryManager.createRepository(DEFAULT_REPOSITORY_IDENTIFER);
        }

        IndexEngine indexEngine = AsphodelServiceLocator.getIndexEngine(null);
        Indexee indexee = new Indexee();
        indexee.setUri("uri");
        indexee.setOtherFields(null);
        indexee.setIndexeeContent(new PlainTextStringContent(TEXT_STRING_ENGLISH));

        indexEngine.createIndex(indexee);
    }
}
