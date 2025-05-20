package org.asphodel.search;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.asphodel.FtrTestUtil;
import org.asphodel.FtrException;

import java.util.Collection;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 *        Date: Apr 4, 2007
 *        Time: 11:06:20 AM
 */
public class DefaultSearchEngineTest {
    private static final Log log = LogFactory.getLog(DefaultSearchEngineTest.class);

    @Test
    public void testSeachUsingString() {
        log.debug("--:"+System.getProperty("ittest"));
        SearchEngine searchEngine = new DefaultSearchEngine();
        String queryString = "java";
        try {
            FtrTestUtil.createIndex();
            QueryCommand queryCommand = new QueryCommand(queryString);
            int neverypage=5;
            int npage=1;

            queryCommand.setStartIndex(0);            
            queryCommand.setMaxSize(npage*neverypage);
            FtrSearchResult ftrSearchResult= searchEngine.search(queryCommand);
            
            log.debug("total:"+ftrSearchResult.getTotal());
            Collection <FtrRecord>coll = ftrSearchResult.getFtrRecords();
            if(coll!=null){
                for(FtrRecord ftrRecord:coll){
                  log.debug("uri:"+ftrRecord.getField("uri"));
                }
            }

            assertEquals(1, ftrSearchResult.getTotal());
        } catch (FtrException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
