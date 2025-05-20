/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;

/**
 * @author luc
 *
 */
public class ItemTemplateTagTest extends TagBaseTest {
	
	@Test
	public void render() throws Exception {
		
		RepeaterTag oLoopTag = new RepeaterTag();
		ItemTemplateTag oItemTemplateTag = new ItemTemplateTag();
		oItemTemplateTag.setPageContext(mMockPageContext);
		oItemTemplateTag.setParent(oLoopTag);
		
		doStartTagAnsAssertEquals(oItemTemplateTag, TagSupport.EVAL_BODY_INCLUDE);
		/*
		List<MyModel> oData = new ArrayList<MyModel>();
		oData.add(new MyModel("Luc"));
		oLoopTag.setItems(oData);
		
		doStartTagAnsAssertEquals(oItemTemplateTag, TagSupport.EVAL_BODY_INCLUDE);
		*/
	}

}
