package com.soops.CEN4010.JMCA;

/**
 *
 * <p>Title: Group 2 Project </p>
 *
 * <p>Class: CEN4010 Fall 2005</p>
 *
 * <p>Description: Parser Interface </p>
 *
 * <p>Instructor Dr. Stoeklin</p>
 *
 * @author Group 2
 */
public interface Parser
{
    /**
     * parse a file and return the root of an ASTree
     * @param file String
     * @return ASTNode
     */
    ASTNode parse(java.io.Reader rdr);
}
