package com.soops.CEN4010.JMCA;

import java.io.Reader;
import com.soops.CEN4010.JMCA.JParser.JavaParser;
import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;
import java.io.FileInputStream;
import java.io.File;
import com.soops.CEN4010.JMCA.JParser.xmlParser.SaxProcessor;
import com.soops.CEN4010.JMCA.JParser.ParseException;

public class JMCAParser implements Parser {

    String intermediateFile = "default.xml";

    /**
     * parse implements parse for Parser
     * precondition is the intermediatFile is set
     * @param rdr Reader
     * @return ASTNode
     */
    public ASTNode parse(Reader rdr);
}
