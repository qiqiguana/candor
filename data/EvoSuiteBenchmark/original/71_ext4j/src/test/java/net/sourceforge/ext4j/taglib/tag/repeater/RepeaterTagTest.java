/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import net.sourceforge.ext4j.taglib.tag.JspWriterStub;

import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author luc
 *
 */
public class RepeaterTagTest extends TagBaseTest {
	
	@Test
	public void doStartTag() throws Exception {
		replayAllMocks();
		
		RepeaterTag oRepeaterTag = new RepeaterTag();
		oRepeaterTag.setPageContext(mMockPageContext);
		doStartTagAnsAssertEquals(oRepeaterTag, TagSupport.SKIP_BODY);
		List<MyModel> oData = newData("Luc","Tony");
		oRepeaterTag.setItems(oData);
		doStartTagAnsAssertEquals(oRepeaterTag, TagSupport.EVAL_BODY_INCLUDE);
		
		verifyAllMocks();
	}
	
	
	@Test
	public void iteration() throws Exception {
		replayAllMocks();
		
		RepeaterTag oRepeaterTag = new RepeaterTag();
		oRepeaterTag.setPageContext(mMockPageContext);
		List<MyModel> oData = newData("Luc","Jerome","Tony");
		oRepeaterTag.setItems(oData);
		Evaluator oEvaluator = new Evaluator();
		
		doStartTagAnsAssertEquals(oRepeaterTag, TagSupport.EVAL_BODY_INCLUDE);
		assertEquals("Luc", oEvaluator.evaluate("test", "${item.name}", String.class, null, mMockPageContext));
		assertEquals(0, oEvaluator.evaluate("test", "${container.index}", Integer.class, null, mMockPageContext));
		assertEquals(true, oEvaluator.evaluate("test", "${container.first}", Boolean.class, null, mMockPageContext));
		assertEquals(false, oEvaluator.evaluate("test", "${container.last}", Boolean.class, null, mMockPageContext));
		
		doAfterBodyAndAssertEquals(oRepeaterTag, TagSupport.EVAL_BODY_AGAIN);
		assertEquals("Jerome", oEvaluator.evaluate("test", "${item.name}", String.class, null, mMockPageContext));
		assertEquals(1, oEvaluator.evaluate("test", "${container.index}", Integer.class, null, mMockPageContext));
		assertEquals(false, oEvaluator.evaluate("test", "${container.first}", Boolean.class, null, mMockPageContext));
		assertEquals(false, oEvaluator.evaluate("test", "${container.last}", Boolean.class, null, mMockPageContext));
		
		doAfterBodyAndAssertEquals(oRepeaterTag, TagSupport.EVAL_BODY_AGAIN);
		assertEquals("Tony", oEvaluator.evaluate("test", "${item.name}", String.class, null, mMockPageContext));
		assertEquals(2, oEvaluator.evaluate("test", "${container.index}", Integer.class, null, mMockPageContext));
		assertEquals(false, oEvaluator.evaluate("test", "${container.first}", Boolean.class, null, mMockPageContext));
		assertEquals(true, oEvaluator.evaluate("test", "${container.last}", Boolean.class, null, mMockPageContext));
		
		doAfterBodyAndAssertEquals(oRepeaterTag, TagSupport.SKIP_BODY);
		
		verifyAllMocks();
	}

	private void processTag(Tag pTag) throws JspException {
		int oEval = pTag.doStartTag();
		System.out.println("doStatTag: " + oEval);
		//oEval = pTag.doAfterBody();
		//System.out.println("doAfterBody: " + oEval);
		oEval = pTag.doEndTag();
		System.out.println("doEndTag: " + oEval);
	}

}
