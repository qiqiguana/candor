/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.ext4j.taglib.tag.PaginationTag;

import org.junit.Test;

import com.tripfilms.os.exttaglib.pagination.datatype.Pagination;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class PaginationTagTest {
	
	@Test
	public void getPaginationFirstPage() throws Exception {
		PaginationTag oTag = newTag(1, 100, 5, 7, "test.do", true);
		
		Pagination oPagination = oTag.getPagination();
		assertNotNull(oPagination);
		
		assertEquals(1, oPagination.getCurrentPage());
		assertEquals(100 / 5, oPagination.getTotalPages());
		
		assertNull(oPagination.getFirstPage());
		assertNull(oPagination.getPreviousPage());
		
		assertNotNull(oPagination.getPages());
		assertNotNull(oPagination.getPages().getPage());
		assertEquals(7, oPagination.getPages().getPage().size());
		
		assertNotNull(oPagination.getNextPage());
		assertMatch(".*from=6&to=10(&|$)", oPagination.getNextPage().getUrl());
		assertNotNull(oPagination.getLastPage());
		assertMatch(".*from=96&to=100(&|$)", oPagination.getLastPage().getUrl());
	}
	
	@Test
	public void getPaginationSecondPage() throws Exception {
		PaginationTag oTag = newTag(9, 100, 5, 7, "test.do", true);
		
		Pagination oPagination = oTag.getPagination();
		assertNotNull(oPagination);
		
		assertEquals(2, oPagination.getCurrentPage());
		assertEquals(100 / 5, oPagination.getTotalPages());
		
		assertNotNull(oPagination.getFirstPage());
		assertMatch(".*from=1&to=5(&|$)", oPagination.getFirstPage().getUrl());
		assertNotNull(oPagination.getPreviousPage());
		assertMatch(".*from=1&to=5(&|$)", oPagination.getPreviousPage().getUrl());
		
		assertNotNull(oPagination.getPages());
		assertNotNull(oPagination.getPages().getPage());
		assertEquals(7, oPagination.getPages().getPage().size());
		
		assertNotNull(oPagination.getNextPage());
		assertMatch(".*from=11&to=15(&|$)", oPagination.getNextPage().getUrl());
		assertNotNull(oPagination.getLastPage());
		assertMatch(".*from=96&to=100(&|$)", oPagination.getLastPage().getUrl());
	}
	
	@Test
	public void getPaginationBeforeLastPage() throws Exception {
		PaginationTag oTag = newTag(94, 100, 5, 7, "test.do", true);
		
		Pagination oPagination = oTag.getPagination();
		assertNotNull(oPagination);
		
		assertEquals(19, oPagination.getCurrentPage());
		assertEquals(100 / 5, oPagination.getTotalPages());
		
		assertNotNull(oPagination.getFirstPage());
		assertMatch(".*from=1&to=5(&|$)", oPagination.getFirstPage().getUrl());
		assertNotNull(oPagination.getPreviousPage());
		assertMatch(".*from=86&to=90(&|$)", oPagination.getPreviousPage().getUrl());
		
		assertNotNull(oPagination.getPages());
		assertNotNull(oPagination.getPages().getPage());
		assertEquals(7, oPagination.getPages().getPage().size());
		
		assertNotNull(oPagination.getNextPage());
		assertMatch(".*from=96&to=100(&|$)", oPagination.getNextPage().getUrl());
		assertNotNull(oPagination.getLastPage());
		assertMatch(".*from=96&to=100(&|$)", oPagination.getLastPage().getUrl());
	}
	
	@Test
	public void getPaginatioLastPage() throws Exception {
		PaginationTag oTag = newTag(98, 100, 5, 7, "test.do", true);
		
		Pagination oPagination = oTag.getPagination();
		assertNotNull(oPagination);
		
		assertEquals(20, oPagination.getCurrentPage());
		assertEquals(100 / 5, oPagination.getTotalPages());
		
		assertNotNull(oPagination.getFirstPage());
		assertMatch(".*from=1&to=5(&|$)", oPagination.getFirstPage().getUrl());
		assertNotNull(oPagination.getPreviousPage());
		assertMatch(".*from=91&to=95(&|$)", oPagination.getPreviousPage().getUrl());
		
		assertNotNull(oPagination.getPages());
		assertNotNull(oPagination.getPages().getPage());
		assertEquals(7, oPagination.getPages().getPage().size());
		
		assertNull(oPagination.getNextPage());
		assertNull(oPagination.getLastPage());
	}
	
	
	
	@Test
	public void getPaginationNoFromToOnFirstPage() throws Exception {
		PaginationTag oTag = newTag(9, 100, 5, 7, "test.do", false);
		Pagination oPagination = oTag.getPagination();
		assertNotNull(oPagination);		
		
		assertNotNull(oPagination.getFirstPage());
		assertMatch("test.do", oPagination.getFirstPage().getUrl());
	}
	
	@Test
	public void renderTag() throws Exception {
		PaginationTag oTag = newTag(9, 100, 5, 7, "test.do", false);
		JspWriterStub oOut = new JspWriterStub();
		oTag.renderTag(oOut);
		assertTrue(oOut.getBuffer().length() > 0);
		//System.out.println("Buffer:\n" + oOut.getBuffer().toString());
		String oExpected = readFromFile("/simple_output_expected.html");
		assertEquals(oExpected.trim(), oOut.getBuffer().toString().trim());
	}
	
	@Test
	public void renderTagPerformance() throws Exception {
		PaginationTag oTag = newTag(9, 100, 5, 7, "test.do", false);		
		JspWriterStub oOut = new JspWriterStub();
		long oStart = System.currentTimeMillis();
		oTag.renderTag(oOut);
		long oWarmup = (System.currentTimeMillis() - oStart);
		
		List<Long> oDurations = new ArrayList<Long>();
		for (int i = 0; i < 100; i++) {
			oStart = System.currentTimeMillis();
			oTag.renderTag(oOut);
			oDurations.add(System.currentTimeMillis() - oStart);
		}
		double oAvg = MathUtils.average(oDurations);
		//System.out.println(String.format("warmup: %s, average: %s", oWarmup, oAvg));
		
		// Ensure we keep performance the same even when creating new tags
		oDurations = new ArrayList<Long>();
		for (int i = 1; i <= 100; i++) {			
			oTag = newTag(i, 100, 5, 7, "test.do", false);
			oStart = System.currentTimeMillis();
			oTag.renderTag(oOut);
			oDurations.add(System.currentTimeMillis() - oStart);
		}
		double oAvgWithCreations = MathUtils.average(oDurations);
		assertTrue(oAvgWithCreations <= oAvg);
		
	}

	private PaginationTag newTag(int pFrom, int pTotal, int pByPage, int pPages, String pURL, boolean pUseFromToOnFirstPage) {
		PaginationTag oTag;
		oTag = new PaginationTag();	
		oTag.setFrom(pFrom);
		oTag.setTotal(pTotal);
		oTag.setByPage(pByPage);
		oTag.setPages(pPages);
		oTag.setUrl(pURL);
		oTag.setUseFromToOnFirstPage(pUseFromToOnFirstPage);
		return oTag;
	}

	private String readFromFile(String pResource) {
		StringBuffer oResult = new StringBuffer();
		byte[] oBuf = new byte[1024];
		int oRead = 0;
		InputStream oIS = null;
		try {
			oIS = this.getClass().getResourceAsStream(pResource);
			while ((oRead = oIS.read(oBuf)) > 0) {
				oResult.append(new String(oBuf, 0, oRead));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oIS != null) try { oIS.close(); } catch (IOException e) {};
		}
		return oResult.toString();
	}

	private void assertMatch(String pRegex, String pString) {
		assertNotNull("Expected not null for " + pRegex, pString);
		assertTrue("Does not match regex. expected:[" + pRegex + "] but got [" + pString + "]", pString.matches(pRegex));
	}

}
