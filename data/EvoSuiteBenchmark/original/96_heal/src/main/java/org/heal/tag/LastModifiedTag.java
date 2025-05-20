package org.heal.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tag to display the modification date of a file.  Based on example
 * from <a href="http://www-106.ibm.com/developerworks/java/library/j-jsp08053.html">http://www-106.ibm.com/developerworks/java/library/j-jsp08053.html</a>
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>) 
 */
public class LastModifiedTag extends TagSupport {
    private String format = "MMM dd yyyy";

    public void setFormat(String format) {
        this.format = format;
    }

    public int doEndTag() {
        try {
            HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
            String path = pageContext.getServletContext().getRealPath(request.getServletPath());
            File file = new File(path);

            DateFormat formatter = new SimpleDateFormat(format);

            pageContext.getOut().print(formatter.format(new Date(file.lastModified())));
        } catch(IOException e) {
        }
        return EVAL_PAGE;
    }
}
