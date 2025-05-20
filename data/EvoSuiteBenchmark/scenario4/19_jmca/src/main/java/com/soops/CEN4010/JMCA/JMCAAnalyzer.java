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

    private String fileName = "";

    private StringBuilder output = new StringBuilder();

    private java.util.HashSet<String> classList = null;

    private static final boolean DEVELOPMENT = false;

    /**
     * Analyze the ASTree and return a formatted string
     * @param root ASTNode
     * @return String
     */
    public String analyze(ASTNode root);

    /**
     * top level analyzer for each class <br>
     * calls analyzeMethod for each method
     * @param node ASTNode
     */
    private void analyzeClass(ASTNode node);

    /**
     * called for each method
     * @param node ASTNode
     */
    private void analyzeMethod(ASTNode node);

    /**
     * recursive method to create a chain of modifiers to a variable <b>
     * using this method then :   a = b + c;  d = a;  e = d; <br>
     *   e would contain both d and a as modifiers it is dependent on
     * @param stE StatementElement
     * @param list ArrayList
     */
    private void createArrayListOfModifiers(StatementElement stE, ArrayList<String> list);

    /**
     * recusive method to analyze statements
     * @param parentNode ASTNode
     * @param statementList ArrayList
     * @param isInSelection boolean
     * @param isInIteration boolean
     */
    private void statements(ASTNode parentNode, ArrayList<StatementDS> statementList, boolean isInSelection, boolean isInIteration);

    /**
     * called for each statement
     * @param node ASTNode
     * @return StatementDS
     */
    private StatementDS analyzeStatement(ASTNode node);

    /**
     * called from analyzeStatement - gets a list of RVals in assignment statements
     * @param locDS StatementDS
     * @param ctr int
     * @param list ArrayList
     */
    private void getRVals(StatementDS locDS, int ctr, ArrayList<ASTNode> list);

    /**
     * helper function - checks for delimiters in a equation where delimiters <br>
     * are defined as arithmetic operands such as '+' and '-'
     * @param id String
     * @return boolean
     */
    private boolean isOperator(String id);

    /**
     * gets the variable name for the lVal of an expression
     * @param ctr int
     * @param list ArrayList
     * @return int
     */
    private int getLVal(int ctr, ArrayList<ASTNode> list);

    /**
     * write the report header to output
     */
    private void setHeader();

    /**
     * Store the file name parsed for formatted display purposes
     * @param fileName String
     */
    public void setFileName(String fileName);
}
