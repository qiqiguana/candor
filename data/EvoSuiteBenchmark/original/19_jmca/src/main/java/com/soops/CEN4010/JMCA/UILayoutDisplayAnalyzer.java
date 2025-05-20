package com.soops.CEN4010.JMCA;

/**
 *
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
public class UILayoutDisplayAnalyzer implements Analyzer
{
    String filename = null;
    /**
     * store the file name for formatted display purposes
     * @param file String
     */
    public void setFileName(String file)
    {
        filename = file;
    }

    /**
     * no operational implementation of Analyzer
     * @param dummy ASTNode not used for dummy output
     * @return display string
     */
    public String analyze(ASTNode dummy)
    {
        StringBuffer output = new StringBuffer();
        //FAKE OUTPUT FOR PROTOTYPE
        output = new StringBuffer("\nFile:  ");
        output.append(filename);
        output.append("\n");
        output.append("Class: CohesionTest\n\n");
        output.append(
                "Method Name                             Cohesion Level\n");
        output.append(
                "--------------------------------------------------------------\n");
        output.append("deposit                                 Functional\n");
        output.append("withdraw                                Sequential\n");
        output.append(
                "addCD                                   Communicational\n");
        output.append("formDet                                 Iterative\n");
        output.append("checkBookIn                             Conditional\n");
        output.append("readInput                               Coincidental\n");

        return output.toString();
    }
}
