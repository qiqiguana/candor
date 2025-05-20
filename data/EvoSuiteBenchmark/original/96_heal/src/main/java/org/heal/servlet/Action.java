package org.heal.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    /**
     * @return <code>true</code> if this servlet action requires authentication,
     *      <code>false</code> otherwise.
     */
    public boolean actionRequiresLogin();

}
