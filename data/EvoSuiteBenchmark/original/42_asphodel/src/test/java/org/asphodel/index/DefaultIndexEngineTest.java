package org.asphodel.index;

import org.asphodel.FtrTestUtil;
import org.asphodel.AsphodelServiceLocator;
import org.testng.annotations.Test;

/**
 * @author sunwj
 * @version 0.1
 *          Date: Apr 3, 2007
 *          Time: 12:19:53 PM
 * @since 0.1
 */
public class DefaultIndexEngineTest {

    @Test
    public void testCreateIndexForString() {
        try {
            FtrTestUtil.createIndex();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("when create index..", e);
        }
    }

    @Test
    public void testCreateIndexForHtmlString() {
        FtrTestUtil.init();
        IndexeeContent htmlContent = new HtmlStringContent(FtrTestUtil.HTML_STRING_ENGLISH);

        String repository=FtrTestUtil.DEFAULT_REPOSITORY_IDENTIFER ;
        try {
            IndexEngine indexEngine = AsphodelServiceLocator.getIndexEngine(repository);
            Indexee indexee = new Indexee();
            indexee.setUri("/uri");
            indexee.setOtherFields(null);
            indexee.setIndexeeContent(htmlContent);

            indexEngine.createIndex(indexee);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
