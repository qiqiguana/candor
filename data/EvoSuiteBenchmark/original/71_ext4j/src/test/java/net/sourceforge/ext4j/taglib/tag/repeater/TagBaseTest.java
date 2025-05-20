/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author luc
 *
 */
public class TagBaseTest {
	
	protected MockServletContext mMockServletContext;
	protected WebApplicationContext mMockWebApplicationContext;
	protected MockPageContext mMockPageContext;
	
	
	@Before
	public void setup() throws Exception {
		mMockServletContext = new MockServletContext();
		// Create the mock Spring Context so that we can mock out the calls to getBean in the custom tag
        // Then add the Spring Context to the Servlet Context
        mMockWebApplicationContext = createMock(WebApplicationContext.class);
        mMockServletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                                        mMockWebApplicationContext);

        // Create the MockPageContext passing in the mock servlet context created above
        mMockPageContext = new MockPageContext(mMockServletContext);

        // Create the mock service object using it's interface
        //mockService = createMock(SomeServiceInterface.class);

        // Create an instance of the custom tag we want to test
        // set it's PageContext to the MockPageContext we created above
        //someCustomTag = new SomeCustomTag();
        //someCustomTag.setPageContext(mockPageContext);

        // Whenever you make a call to the doStartTag() method on the custom tag it calls getServletContext()
        // on the WebApplicationContext.  So to avoid having to put this expect statement in every test
        // I've included it in the setUp()
        expect(mMockWebApplicationContext.getServletContext()).andReturn(mMockServletContext).anyTimes();
        
	}
	
	protected List<MyModel> newData(String... pNames) {
		List<MyModel> oData = new ArrayList<MyModel>();
		for (String n : pNames) {
			oData.add(new MyModel(n));
		}
		return oData;
	}
	
	protected String getOutput() throws UnsupportedEncodingException {
		String oOutput = ((MockHttpServletResponse) mMockPageContext.getResponse()).getContentAsString();
		return oOutput;
	}
	
	protected void doAfterBodyAndAssertEquals(IterationTag pTag, int pExpected) throws JspException {
		assertEquals(pExpected, pTag.doAfterBody());
	}

	protected void doStartTagAnsAssertEquals(Tag pTag, int pExpected) throws JspException {
		assertEquals(pExpected, pTag.doStartTag());
	}
	
	protected void replayAllMocks() {
		replay(mMockWebApplicationContext);
	}
	
	protected void verifyAllMocks() {
		verify(mMockWebApplicationContext);
	}


}
