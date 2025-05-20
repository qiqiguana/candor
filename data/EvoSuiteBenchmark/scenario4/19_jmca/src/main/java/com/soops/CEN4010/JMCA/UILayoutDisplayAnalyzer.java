package com.soops.CEN4010.JMCA;

/**
 * <p>Title: Group 2 Project </p>
 *
 * <p>Class: CEN4010 Fall 2005</p>
 *
 * <p>Description: Description: UILayoutDisplayAnalyzer implements a dummy Analyzer
 *    use for UI layout  </p>
 *
 * <p>Instructor Dr. Stoeklin</p>
 *
 * @author Group 2
 */
public class UILayoutDisplayAnalyzer implements Analyzer {

    String filename = null;

    /**
     * store the file name for formatted display purposes
     * @param file String
     */
    public void setFileName(String file);

    /**
     * no operational implementation of Analyzer
     * @param dummy ASTNode not used for dummy output
     * @return display string
     */
    public String analyze(ASTNode dummy);
}
