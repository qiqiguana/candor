package bible.servlet;

import javax.servlet.http.HttpServletRequest;

import bible.util.*;

public class ServletUtil {
    public final static int UNKNOWN = -10;
    public static String GetUsername(HttpServletRequest request) {
        String name = request.getRemoteUser();
        if (name == null) {
            name = "";
        }
        return name;
    }

    public static int GetIntParameter(HttpServletRequest request, String name) {
        return GetIntParameter(request.getParameter(name));
    }

    public static int[] GetIntParameters(HttpServletRequest request, String name) {
        return GetIntParameters(request.getParameterValues(name));
    }

    public static String GetStringParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    public static int[] GetIntParameters(String[] parameters) {
        if ((parameters != null) && (parameters.length > 0) 
            && !((parameters.length == 1) && (parameters[0].length() == 0))) {//not only one blank parameter
            return Util.ToIntArray(parameters);
        } else {
            return new int[0];
        }
    }

    public static int GetIntParameter(String parameter) {
        if ((parameter != null) && (parameter.length() > 0)) {
            return Integer.parseInt(parameter);
        } else {
            return UNKNOWN;
        }
    }
}
