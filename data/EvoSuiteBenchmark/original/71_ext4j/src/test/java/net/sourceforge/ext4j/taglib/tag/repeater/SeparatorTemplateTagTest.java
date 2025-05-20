/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;

/**
 * @author luc
 *
 */
public class SeparatorTemplateTagTest extends TagBaseTest {

	@Test
	public void render() throws Exception {
		replayAllMocks();
		
		RepeaterTag oLoopTag = new RepeaterTag();
		oLoopTag.setPageContext(mMockPageContext);
		List<MyModel> oData = newData("Luc","Jerome","Tony");
		oLoopTag.setItems(oData);
		
		SeparatorTemplateTag oTag = new SeparatorTemplateTag();
		oTag.setPageContext(mMockPageContext);
		oTag.setParent(oLoopTag);
		
		oLoopTag.doStartTag();
		doStartTagAnsAssertEquals(oTag, TagSupport.EVAL_BODY_INCLUDE);
		oLoopTag.doAfterBody();
		doStartTagAnsAssertEquals(oTag, TagSupport.EVAL_BODY_INCLUDE);
		oLoopTag.doAfterBody();
		doStartTagAnsAssertEquals(oTag, TagSupport.SKIP_BODY);
		
		
		verifyAllMocks();
	}
}
