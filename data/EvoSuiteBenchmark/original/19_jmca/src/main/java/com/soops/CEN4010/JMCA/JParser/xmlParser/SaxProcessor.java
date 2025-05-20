package com.soops.CEN4010.JMCA.JParser.xmlParser;

import com.soops.CEN4010.JMCA.ASTNode;
import java.io.InputStream;
import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import java.io.File;
import org.xml.sax.SAXException;
import java.io.PrintWriter;

public class SaxProcessor extends DefaultHandler implements XMLParser {
    State currState = new CompileUnit(this);  // current state
    java.util.Stack<State>  returnState = new java.util.Stack<State>();    // use to store state to return to when more than on is possible
    java.util.Stack<State> parentStateofCurrentClass = new java.util.Stack<State>();
    ASTNode rootNode = null;
    ASTNode currNode = null;
    InputStream inStr = null;

    public State getParentofCurrentClass() {
        if (parentStateofCurrentClass.empty()) return null;
        return parentStateofCurrentClass.peek();
    }

    public void setParentofCurrentClass(State s) {
        if (s == null && !parentStateofCurrentClass.empty()) {
            parentStateofCurrentClass.pop();
        } else {
            parentStateofCurrentClass.push(s);
        }
    }

    public State getReturnState () {
        if (returnState.empty()) return null;
        return returnState.peek();
    }

    public void setReturnState(State s) {
        if (s == null && !returnState.empty() ){
            returnState.pop();
        } else
            returnState.push(s);
    }

    public void setASTrootNode(ASTNode rootNode) {
        this.rootNode = rootNode;
    }

    public void setCurrASTNode(ASTNode node) {
        currNode = node;
    }

    public ASTNode getRootNode() {
        return rootNode;
    }

    public ASTNode getCurrNode() {
        return currNode;
    }

    public State getCurrState() {
        return currState;
    }

    public void setCurrState(State s) {
        currState = s;
    }

   public SaxProcessor(InputStream rdr) {
     inStr =  rdr;
   }

    public SaxProcessor() {
        try {
            inStr = new FileInputStream(new File("test.xml"));
            showFile();
            PrintWriter wtr = new PrintWriter(System.out);
            getRootNode().display(wtr);
            wtr.flush();
        } catch (Exception ie) {
            System.err.println(ie.getMessage());
        }
    }

    public void showFile() {

        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            if (saxParser == null) {
                System.out.println("saxParser = null?");
                System.exit( -1);
            }
            saxParser.parse(inStr, this);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    } // end showFile method

    public void startElement(String namespaceURI,
                             String sName, // simple name (localName)
                             String qName, // qualified name
                             Attributes attrs) throws SAXException {
        // String eName = sName; // element name
        // System.out.println(qName);
       currState.processState(qName);
    }

    public void endElement(String namespaceURI,
                           String sName, // simple name
                           String qName // qualified name
            ) throws SAXException {
        currState.closeState(qName);
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);
        if (!s.trim().equals("")) {
            currState.setTextVal(s);
       }
    }

} // end of class
