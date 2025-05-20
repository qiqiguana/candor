package com.soops.CEN4010.JMCA;

import java.util.*;

/**
 * <p>Title: JMCAAnalyzer </p>
 *
 * <p>Description: implements Analyzer</p>
 *
 * Group2 for CEN4010 at FSU - Fall 2005
 */
public class JMCAAnalyzer implements Analyzer {

    /**
     * Analyze the ASTree and return a formatted string
     *
     * @param root ASTNode
     * @return String
     */
    public String analyze(ASTNode root) {
        if (root == null) {
            output.append("File did not parse correctly");
            return output.toString();
        }
        setHeader();
        //  for each class
        for (ASTNode node : root.list) {
            analyzeClass(node);
        }
        return output.toString();
    }

    /**
     * write the report header to output
     */
    private void setHeader();

    /**
     * top level analyzer for each class <br>
     * calls analyzeMethod for each method
     * @param node ASTNode
     */
    private void analyzeClass(ASTNode node);
}
