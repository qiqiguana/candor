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
         public ASTNode parse ( Reader rdr) {
             JavaParser parser = new JavaParser(rdr);
             Writer wtr = null;
             try {
                 parser.CompilationUnit();
                 wtr = new FileWriter(new File(intermediateFile));
                 parser.dump(wtr);
                 System.out.println(
                         "Java Parser Version 1.1:  Java program parsed successfully.");
                 wtr.close();
                 SaxProcessor saxP = new SaxProcessor(new FileInputStream(new File(
                         intermediateFile)));
                 saxP.showFile();
                 return saxP.getRootNode();
             } catch (ParseException e) {
                 System.out.println(e.getMessage());
                 System.out.println(
                         "Java Parser Version 1.1:  Encountered errors during parse.");
                 return null;
             } catch (IOException ie) {
                 System.out.println("IO Error from parse : " + ie.getMessage());
                 return null;
             } catch (Error ex) {
                 return null;
             }

             finally {
                 try {
                     if (wtr != null)
                     wtr.close();
                 } catch (IOException ie) {
                     System.err.println("Error while closing intermediate file " +
                                        intermediateFile);
                     System.err.println(ie.getMessage());
                 }
             }

  //           return null;

    }
}
