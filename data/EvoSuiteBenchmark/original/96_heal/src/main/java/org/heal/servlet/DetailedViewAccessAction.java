package org.heal.servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import org.heal.module.user.UserBean;

/**
 * Comments here!
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>) 
 */
public class DetailedViewAccessAction extends DelimitedFileWriterAction {
    public boolean actionRequiresLogin() { return false; }

    /**
     * @return The name of the file to write the delimited data to.  This file
     *      need not include path information -- it will show up in the /private/logs/
     *      folder as specified in {@link org.heal.servlet.DelimitedFileWriterAction}.
     */
    public String getLogFilename() {
        return "detailedViewAccess.txt";
    }

    /**
     * @return A <code>String</code> representation of the page to forward
     *      to upon a successful form submission.
     */
    public String getSuccessPage() {
        return null;
    }

    public List getData(HttpServletRequest request) {
        List ret = new ArrayList();
        UserBean user = (UserBean)request.getSession().getAttribute("validUser");
        if(user!=null){
          ret.add(user.getUserId());
        }
        ret.add(request.getParameter("metadataId"));
        ret.add(request.getSession().getId());
        ret.add(""+new java.util.Date());        
        
        //ret.add(getFormParameter(request, "lastName"));
        return ret;
    }

}
