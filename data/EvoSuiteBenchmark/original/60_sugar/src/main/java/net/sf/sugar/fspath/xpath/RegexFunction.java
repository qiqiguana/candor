/**
 * Copyright 2008 (C) Keith Bishop (bishi@users.sourceforge.net)
 *
 * All rights reserved.
 *
 * This file is part of FSPath.
 *
 * FSPath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSPath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSPath.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * RegexFunction.java
 *
 * Created on 23 April 2008, 22:30
 *
 */

package net.sf.sugar.fspath.xpath;


import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.xml.xpath.XPathFunctionException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *	This class enables the use of regular expressions in FSPath queries.
 *  For instance :
 *  <pre>
 *  //file[fs:matches('[0-9]{6}-[\w]{4}\.log')]
 *  </pre>
 *  would match files of the pattern nnnnnn-xxxx.log where n is a numerical character and x is a word character.
 *
 * @author kbishop
 * @version $Id$
 */
public class RegexFunction implements javax.xml.xpath.XPathFunction {

    private Pattern mostRecentPattern;

    private String mostRecentExpression;

    /** Creates a new instance of RegexFunction */
    public RegexFunction() {

    }

    /**
     *
     */
    public Object evaluate(List args) throws XPathFunctionException {

        String nodeValue = this.getNodeValue(args);
        Pattern pattern = this.getPattern(args);

        Boolean isMatch = new Boolean(pattern.matcher(nodeValue).matches());

        return isMatch;
    }

    protected String getNodeValue(List args) throws XPathFunctionException {

        Object o = args.get(0); //we know there will be two args
        String nodeValue = "";

        if (o instanceof String) { nodeValue = (String)o; }
        else if (o instanceof Boolean) { nodeValue = o.toString(); }
        else if (o instanceof Double) { nodeValue = o.toString(); }
        else if (o instanceof NodeList) {

            nodeValue = ((Node)((NodeList)o).item(0)).getTextContent();

        } else {
            throw new XPathFunctionException("Unable to evaluate fs:match() function, could not convert argument type");
        }

        return nodeValue;
    }

    protected Pattern getPattern(List args) throws XPathFunctionException {
        try {
            String expression = (String)args.get(1);

            //For situations where the expression is being applied to a large number of nodes
            //it's desrable to only compile the pattern once.
            if (this.mostRecentExpression == null || !(expression.equals(this.mostRecentExpression))) {

                this.mostRecentExpression = expression;
                Pattern pattern = Pattern.compile(this.mostRecentExpression);
                this.mostRecentPattern = pattern;
            }

            return this.mostRecentPattern;

        } catch (ClassCastException cce) {
            throw new XPathFunctionException("Unable to evaluate fs:match() function, the second parameter must evaluate to a String type");

        } catch (PatternSyntaxException pse) {
            throw new XPathFunctionException("Unable to evaluate fs:match() function, the second parameter is not a valid regex : " + pse.getMessage());
        }
    }

}
