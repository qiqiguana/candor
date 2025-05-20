package dk.statsbiblioteket.summa.support.embeddedsolr;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;

import dk.statsbiblioteket.summa.common.configuration.Resolver;

public class KeywordFieldTest {

	String solrHome = "support/solr_home1"; //data-dir (index) will be created here.  
	EmbeddedJettyWithSolrServer server;
	SolrServer solrServer;


	@Before
	public void setUp() throws Exception {
		System.setProperty("basedir", "."); //for logback
		server =  new EmbeddedJettyWithSolrServer(Resolver.getFile(solrHome).toString());
		server.run();
		solrServer = new HttpSolrServer(server.getServerUrl());		
	}

	@Test
	public void testKeywordField() throws Exception {

		//field lma_long is fieldtype:keyword
		//"ABC   DEF_GI J" is mapped to abc def gi j
		String[] files = new String[]{
				"support/solr_test_documents/keywordfieldtest_doc.txt",
		
		};
		SolrServerUnitTestUtil.indexFiles(files);
		SolrQuery query = new SolrQuery("lma_long:\"ABC   DEF_GI J\"");
		QueryResponse response = solrServer.query(query);
	  	assertEquals(1L, response.getResults().getNumFound());

        //must not be found(it is tokenized in 'freetext field')
	  	query = new SolrQuery("lma_long:abc");
		response = solrServer.query(query);
		assertEquals(0L, response.getResults().getNumFound()); 
	  	
		query = new SolrQuery("lma_long:\"abc def gi j\"");
		response = solrServer.query(query);
		assertEquals(1L, response.getResults().getNumFound());				
	
		query = new SolrQuery("lma_long:\"abc\\   def\\ gi\\ j\"");
		response = solrServer.query(query);
		assertEquals(1L, response.getResults().getNumFound());
		

		//Spaces must be kept.
		query = new SolrQuery("lma_long:ABCDEFGIJ");
		response = solrServer.query(query);
		assertEquals(0L, response.getResults().getNumFound());
		
	}

}