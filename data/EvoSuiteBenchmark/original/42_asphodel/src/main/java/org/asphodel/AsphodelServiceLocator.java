
package org.asphodel;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.asphodel.index.IndexEngine;
import org.asphodel.index.DefaultIndexEngine;
import org.asphodel.search.SearchEngine;
import org.asphodel.search.DefaultSearchEngine;
//import org.asphodel.service.AsphodelTimeService;
//import org.asphodel.service.DefaultTimeServiceImpl;

/**
 * @author : Sun Wenju
 *         Date: Feb 6, 2008 3:34:06 PM
 * todo refactor the hardcode to config files.
 */
public class AsphodelServiceLocator {
    public static Analyzer getAnalyzer()
    {
        return new CJKAnalyzer();
    }

    public static IndexEngine getIndexEngine(String repositoryDirectory)
    {
        return new DefaultIndexEngine(repositoryDirectory);
    }

    public static SearchEngine getSearchEngine()
    {
        return new DefaultSearchEngine();
    }
    public static RepositoryManager getRepositoryManager()
    {
        return new DefaultRepositoryManager();
    }

//    public static AsphodelTimeService getTimeService()
//    {
//        return new DefaultTimeServiceImpl();
//    }
}
