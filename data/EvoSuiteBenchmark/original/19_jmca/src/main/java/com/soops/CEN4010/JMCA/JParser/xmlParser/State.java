package com.soops.CEN4010.JMCA.JParser.xmlParser;


import java.util.*;
import java.io.*;
import com.soops.CEN4010.JMCA.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

interface XMLParser {
    public void setASTrootNode(ASTNode rootNode);

    public void setCurrState(State s);

    public void setReturnState(State s);

    public State getReturnState();

    public void setCurrASTNode(ASTNode node);

    public ASTNode getRootNode();

    public ASTNode getCurrNode();

    public State getCurrState();

    public State getParentofCurrentClass();

    public void setParentofCurrentClass(State s);
}

public abstract class State {
    XMLParser parent = null;
    String textVal = null;
    public State(XMLParser p) {
        parent = p;
    }

    abstract void processState(String token);

    void setTextVal(String val) {
        textVal = val;
    }

    abstract void closeState(String token);
}


class CompileUnit extends State {
    void processState(String token) {
        if (token.equals("ClassOrInterfaceDeclaration")) {
            parent.setCurrState(new ClassOrInterfaceDeclaration(parent));
            parent.setParentofCurrentClass(this);
        } else if (token.equals("CompilationUnit")) {
            parent.setASTrootNode(new ASTNode("CompileUnit", "RootNode", null));
            parent.setCurrASTNode(parent.getRootNode());
        }
    }

    CompileUnit(XMLParser p) {
        super(p);
    }

    void closeState(String token) {}
}


class ClassOrInterfaceDeclaration extends State {

    void processState(String token) {
        if (token.equals("ClassOrInterfaceBody")) {
            parent.setCurrState(new ClassOrInterfaceBody(parent));
        }
    }

    void closeState(String token) {
        if (token.equals("identifier") && !textVal.equals("class")) {
            ASTNode tmpNode = new ASTNode("ClassDeclaration",
                                          "class-" + textVal,
                                          parent.getCurrNode());
            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);
            parent.setCurrState(new ClassOrInterfaceBody(parent));
        } else if (token.equals("ClassOrInterfaceDeclaration")) {
          //  parent.setCurrState(new CompileUnit(parent));
            parent.setCurrState(parent.getParentofCurrentClass());
            parent.setParentofCurrentClass(null);
            parent.setCurrASTNode(parent.getCurrNode().getParent());
        }

    }

    ClassOrInterfaceDeclaration(XMLParser p) {
        super(p);
    }
}


class ClassOrInterfaceBody extends State {

    ClassOrInterfaceBody(XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("ClassOrInterfaceBodyDeclaration")) {
            parent.setCurrState(new ClassOrInterfaceBodyDeclaration(parent));
        }
    }

    void closeState(String token) {
        if (token.equals("ClassOrInterfaceBody")) {
            parent.setCurrState(new ClassOrInterfaceDeclaration(parent));
        }
    }

}


class ClassOrInterfaceBodyDeclaration extends State {

    ClassOrInterfaceBodyDeclaration(XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("VariableDeclaratorId")) {
            parent.setCurrState(new VariableDeclarator(parent));
        } else if (token.equals("MethodDeclarator")
                   || token.equals("ConstructorDeclaration")) {
            parent.setCurrState(new MethodDeclarator(parent));
        } else if (token.equals("ClassOrInterfaceDeclaration")) {
            State tmpState = new ClassOrInterfaceDeclaration(parent);
            parent.setCurrState(tmpState);
            parent.setParentofCurrentClass(this);
        }
    }

    void closeState(String token) {
        if (token.equals("ClassOrInterfaceBodyDeclaration")) {
            parent.setCurrState(new ClassOrInterfaceBody(parent));
        }
    }

}


class VariableDeclarator extends State {

    VariableDeclarator(XMLParser p) {
        super(p);
    }

    void processState(String token) {
    }

    void closeState(String token) {
        if (token.equals("identifier")) {
            parent.getCurrNode().addChild(new ASTNode(
                    "ClassVariableDeclaration", textVal, parent.getCurrNode()));
            parent.setCurrState(new ClassOrInterfaceBodyDeclaration(parent));
        } //else if (token.equals("ClassOrInterfaceBodyDeclaration")) {
        //  parent.setCurrState(new ClassOrInterfaceBody(parent));
        //  }
    }
}


class MethodDeclarator extends State {
    boolean oneTime = false;
    MethodDeclarator(XMLParser p) {
        super(p);
    }

    void processState(String token) {
        //  if (token.equals("Block")) {
        //      parent.setCurrState(new Block(parent));
        //  } else
        if (token.equals("FormalParameters")) {
            parent.setCurrState(new Parameters(parent));
        } else if (token.equals("Statement") ||
            token.equals("ExplicitConstructorInvocation") ||
            token.equals("BlockStatement")) {
            parent.setCurrState(new Statement(parent));
        }
    }

    void closeState(String token) {
        if (token.equals("identifier") && !oneTime) {
            ASTNode tmpNode = new ASTNode(
                    "MethodDeclaration", textVal, parent.getCurrNode());
            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);
            oneTime = true;
        } else if (token.equals("MethodDeclaration")
                   || token.equals("ConstructorDeclaration")) {
            parent.setCurrState(new ClassOrInterfaceBodyDeclaration(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
        } else if (token.equals("MethodDeclarator")) {
            oneTime = true;
        }
    }
}



class Parameters extends State {

    Parameters
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("VariableDeclaratorId")) {
            parent.setCurrState(new Parameter(parent));
        }
    }

    void closeState(String token) {
        if (token.equals("FormalParameters")) {
            parent.setCurrState(new MethodDeclarator(parent));
        }
    }
}

class Parameter extends State {

    Parameter
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {

    }

    void closeState(String token) {
        if (token.equals("VariableDeclaratorId")) {
            parent.setCurrState(new Parameters(parent));
        } else if (token.equals("identifier")) {
            parent.getCurrNode().addChild(new ASTNode(
                    "MethodArgument", textVal, parent.getCurrNode()));
        }

    }
}


class LocalVar extends State {
    boolean varDec = false;
    LocalVar(XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("VariableDeclaratorId")) {
            varDec = true;
        }
    }

    void closeState(String token) {
        if (token.equals("LocalVariableDeclaration")) {
            parent.setCurrState(new Statement(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
        } else if (token.equals("identifier") && varDec) {
            parent.getCurrNode().addChild(new ASTNode(
                    "LocalVar", textVal, parent.getCurrNode()));
        }

    }
}


class TryStatement extends State {
    boolean DONE = false;
    TryStatement
            (XMLParser p) {
        super(p);
        parent.setReturnState(this);
    }

    void processState(String token) {
        if (token.equals("Statement") && !DONE) {
            State tmpState = new Statement(parent);
            parent.setCurrState(tmpState);
        }
    }

    void closeState(String token) {
        if (token.equals("TryStatement")) {
            parent.setCurrState(new Statement(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
            parent.setReturnState(null);
        } else if (token.equals("Block")) {
            DONE = true;
        }
    }
}


class IfStatement extends State {
    IfStatement
            (XMLParser p) {
        super(p);
        parent.setReturnState(this);
    }

    void processState(String token) {
        if (token.equals("Statement")) {
            State tmpState = new Statement(parent);
            parent.setCurrState(tmpState);
        }
    }

    void closeState(String token) {
        if (token.equals("IfStatement")) {
            parent.setCurrState(new Statement(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
            parent.setReturnState(null);
        }
    }
}


class IterationStatement extends State {
    IterationStatement
            (XMLParser p) {
        super(p);
        parent.setReturnState(this);
    }

    void processState(String token) {
        if (token.equals("Statement")) {
            State tmpState = new Statement(parent);
            parent.setCurrState(tmpState);
        }
    }

    void closeState(String token) {
        if (token.equals("ForStatement") ||
                token.equals("DoStatement") ||
                    token.equals("WhileStatement")) {
            parent.setCurrState(new Statement(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
            parent.setReturnState(null);
        }
    }
}

class Statement extends State {

    Statement
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("StatementExpression") ) {
            parent.setCurrState(new StatementExpression(parent));
            ASTNode tmpNode = new ASTNode("Statement",
                                          "StatementExpression",
                                          parent.getCurrNode());
            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);
        } else  if (token.equals("ReturnStatement") ) {
            parent.setCurrState(new StatementExpression(parent));
            ASTNode tmpNode = new ASTNode("Statement",
                                          "ReturnStatement",
                                          parent.getCurrNode());
            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);


        } else if (token.equals("LocalVariableDeclaration")) {
            parent.setCurrState(new LocalVar(parent));
                    ASTNode tmpNode = new ASTNode("LocalVarDeclaration",
                                                "LocVar",
                                             parent.getCurrNode());

                parent.getCurrNode().addChild(tmpNode);
                  parent.setCurrASTNode(tmpNode);
        } else if (token.equals("TryStatement")) {
            parent.setCurrState(new TryStatement(parent));
            ASTNode tmpNode = new ASTNode("TryBlock",
                                          "TryBlock",
                                          parent.getCurrNode());

            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);
        } else if (token.equals("IfStatement")) {
            parent.setCurrState(new IfStatement(parent));
            ASTNode tmpNode = new ASTNode("Selection",
                                          "IfStatement",
                                          parent.getCurrNode());

            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);

        } else    if (token.equals("ForStatement") ||
          token.equals("DoStatement") ||
              token.equals("WhileStatement")) {
            parent.setCurrState(new IterationStatement(parent));
            ASTNode tmpNode = new ASTNode("Iteration",
                                          token,
                                          parent.getCurrNode());

            parent.getCurrNode().addChild(tmpNode);
            parent.setCurrASTNode(tmpNode);
        }

    }

    void closeState(String token) {
        if (token.equals("Statement") ||
            token.equals("ExplicitConstructorInvocation")) {
            if (parent.getReturnState() != null) {
                parent.setCurrState(parent.getReturnState());
            } else {
                parent.setCurrState(new MethodDeclarator(parent));
            }
        }
    }
}


class StatementExpression extends State {
    int count = 0;
    StatementExpression
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {
        if (token.equals("StatementExpression")) {
            count ++;
        }
    }

    void closeState(String token) {
        if (token.equals("StatementExpression") && count == 0 ||
                token.equals("ReturnStatement") ) {
            parent.setCurrState(new Statement(parent));
            parent.setCurrASTNode(parent.getCurrNode().getParent());
        } else if (token.equals("StatementExpression") && count > 0 ) {
            count --;
        }
        else if (token.equals("identifier") ) {
            ASTNode tmp = new ASTNode(
                    "StatementExpressionElement", textVal, parent.getCurrNode());
            parent.getCurrNode().addChild(tmp);
        }

    }
}


class Selection extends State {

    Selection
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {
    }

    void closeState(String token) {

    }
}


class PrimaryExpression extends State {

    PrimaryExpression
            (XMLParser p) {
        super(p);
    }

    void processState(String token) {
    }

    void closeState(String token) {

    }
}
