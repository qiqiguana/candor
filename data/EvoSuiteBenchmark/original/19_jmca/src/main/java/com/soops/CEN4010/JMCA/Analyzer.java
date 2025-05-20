package com.soops.CEN4010.JMCA;
/**
 *
 * <p>Title: Group 2 Project </p>
 *
 * <p>Class: CEN4010 Fall 2005</p>
 *
 * <p>Description: common Analyzer interface used for cohesion testing</p>
 *
 * <p>Instructor Dr. Stoeklin</p>
 *
 * @author Group 2
 */
public interface Analyzer {
    /**
     * Analyze the ASTree and return a formatted string
     * @param root ASTNode
     * @return String
     */
    public String analyze (ASTNode root);
    /**
     * Store the file name parsed for formatted display purposes
     * @param fileName String
     */
    public void setFileName(String fileName);
}
