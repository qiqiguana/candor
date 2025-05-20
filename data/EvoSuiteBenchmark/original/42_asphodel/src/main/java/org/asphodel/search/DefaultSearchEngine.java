/**
 * Copyright 2007 Sun Wenju.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 * */
package org.asphodel.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.asphodel.FtrConstants;
import org.asphodel.FtrException;
import org.asphodel.AsphodelServiceLocator;
import org.asphodel.AsphodelConfig;

import java.io.IOException;
import java.io.File;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 *        Date: Apr 3, 2007
 *        Time: 12:25:37 PM
 */
public class DefaultSearchEngine implements SearchEngine {
    private static final Log log = LogFactory.getLog(DefaultSearchEngine.class);


    public FtrSearchResult search(String repository, QueryCommand queryCommand) throws FtrException {
        if (repository == null) {
            repository = AsphodelConfig.getDefaultRepository();
        }
        String queryString = queryCommand.getQueryString();
        IndexSearcher indexSearcher = null;
        FtrSearchResult ftrSearchResult = new FtrSearchResult();
        try {
            indexSearcher = new IndexSearcher(
                    new File(AsphodelConfig.getRepositoryHousePath(), repository).getAbsolutePath());

            Analyzer analyzer = AsphodelServiceLocator.getAnalyzer();
            //using the default field.
            QueryParser parser = new QueryParser(FtrConstants.FIELD_CONTENT, analyzer);

            //todo how to query among multi fields.
            Query query = parser.parse(queryString);

            Hits hits = indexSearcher.search(query);

            ftrSearchResult.setTotal(hits.length());

            int startIndex = queryCommand.getStartIndex();
            int endIndex = Math.min(startIndex + queryCommand.getMaxSize(), hits.length());

            // iterate through the results: and wrap it into more convenient format.
            for (int i = startIndex; i < endIndex; i++) {
                Document hitDoc = hits.doc(i);
                FtrRecord ftrRecord = new FtrRecord(hitDoc, hits.score(i));

//                this.dumpFields(hitDoc.getFields());

                ftrRecord.setBrief(getHighlightedBrief(analyzer, query, hitDoc.getField(FtrConstants.FIELD_CONTENT).stringValue()));
                ftrRecord.setUri(hitDoc.getField(FtrConstants.FIELD_URI).stringValue());

                ftrRecord.setCachedDate(DateTools.stringToDate(hitDoc.getField(FtrConstants.FIELD_CACHEDDATE).stringValue()));
                ftrSearchResult.addFtrRecord(ftrRecord);
            }

        } catch (Exception e) {
            log.error("when search...", e);
            throw new FtrException(e.getMessage(), e);
        } finally {
            try {
                indexSearcher.close();
            } catch (IOException e) {
                log.warn("there are exception when closing the indexSearcher", e);
            }
        }

        return ftrSearchResult;
    }

    /**
     * just search the index repository using the given query string.
     *
     * @param queryCommand: the condition used to retrieve result encapsulated as a command object
     * @return Collection: contains the result which is type of :
     */
    public FtrSearchResult search(QueryCommand queryCommand) throws FtrException {
        return this.search(null, queryCommand);
    }

    private void dumpFields(java.util.List list) {
        for (int i = 0; i < list.size(); i++) {
            Field f = (Field) list.get(i);
            System.out.print(f.name() + ":");
            System.out.println(f.stringValue());
        }
    }

    //@todo move the style to onfig file
    private String getHighlightedBrief(Analyzer analyzer, Query query, String content) throws IOException {

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>"); //
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

        //todo move the length to config files?
        highlighter.setTextFragmenter(new SimpleFragmenter(500));//the length of content
        String result[] = highlighter.getBestFragments(analyzer, "content", content, 1); //获取关键字出现最多的5个位置

        return result[0];
    }
}
