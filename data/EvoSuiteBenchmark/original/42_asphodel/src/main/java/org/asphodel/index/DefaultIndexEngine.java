package org.asphodel.index;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.asphodel.parser.ContentParserException;
import org.asphodel.FtrException;
import org.asphodel.FtrConstants;
import org.asphodel.AsphodelServiceLocator;
import org.asphodel.AsphodelConfig;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 *        Date: Apr 3, 2007
 *        Time: 12:25:46 PM
 *        default implementation of the indexEngine. If you are using a IOC,te repositoryDirectory can be configured.
 */
public class DefaultIndexEngine implements IndexEngine {
    private static final Log log = LogFactory.getLog(DefaultIndexEngine.class);
    private String repositoryDirectory = null;

    public DefaultIndexEngine(String repository) {
        if (repository == null) {
            repository = AsphodelConfig.getDefaultRepository();
        }
        this.repositoryDirectory =
                new File(AsphodelConfig.getRepositoryHousePath(), repository).getAbsolutePath();
    }



    public void createIndex(Collection<Indexee> indexees) throws FtrException {

    }

    /**
     * create index using the given content.
     * TODO: save the page in cache with highlighted.
     *
     * @param indexee the target to be indexed
     */
    public void createIndex(Indexee indexee) throws FtrException {
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(FSDirectory.getDirectory(this.repositoryDirectory), this.getAnalyzer());

            Document document = this.composeIndexeeDocument(indexee);

            indexWriter.addDocument(document);

        } catch (ContentParserException cpe) {
            log.error("failed to parse the content", cpe);
            throw new FtrException("failed to parse the content", cpe);
        } catch (IOException e) {
            log.error("when indexing.", e);
            throw new FtrException("failed to execute the index operation", e);
        } finally {
            if (indexWriter != null)
                try {
                    indexWriter.close();
                } catch (IOException ioe) {
                    log.warn("indexWriter closed error:" + ioe.getMessage());
                }
        }
    }

    /**
     * compose the document,which will contain all the filed to be indexed.
     */
    private Document composeIndexeeDocument(Indexee indexee) throws ContentParserException {
        Document document = new Document();

        String content = indexee.getIndexeeContent().getContent();
        String uri = indexee.getUri();
        checkFields(new String[]{uri});

//        document.add(new Field(FtrConstants.FIELD_CONTENT, reader,Field.TermVector.YES));
        document.add(new Field(FtrConstants.FIELD_CONTENT, content, Field.Store.YES, Field.Index.TOKENIZED));
        document.add(new Field(FtrConstants.FIELD_URI, uri, Field.Store.YES, Field.Index.NO));

        document.add(new Field(FtrConstants.FIELD_CACHEDDATE,
                DateTools.timeToString(System.currentTimeMillis(), DateTools.Resolution.DAY),
                Field.Store.YES,
                Field.Index.NO));

        Map otherIndexeeFields = indexee.getOtherFields();
        if (otherIndexeeFields != null) {
            Set set = otherIndexeeFields.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                Object fieldValue = otherIndexeeFields.get(key);

                //todo how to deal with the date field                if(fieldValue instanceof java.util.Date) 
                document.add(new Field(key, (String) fieldValue, Field.Store.YES, Field.Index.UN_TOKENIZED));
            }
        }

        return document;
    }

    private void checkFields(String[] values) throws IllegalArgumentException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null || values[i].equals(""))
                throw new IllegalArgumentException("the value is not allowed null:" + i);
        }
    }

    private Analyzer getAnalyzer() {
        return AsphodelServiceLocator.getAnalyzer();
    }

}
