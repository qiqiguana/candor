package com.soops.CEN4010.JMCA;

import java.util.*;

/**
 *
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
     * top level analyzer for each class <br>
     * calls analyzeMethod for each method
     * @param node ASTNode
     */
    private void analyzeClass(ASTNode node) {
        // list of class variables
        classList = new HashSet<String>();
        java.util.ArrayList<ASTNode> methodList = new ArrayList<ASTNode>();

        // print class header
        output.append("\nClass: " + node.getIdentity() + "\n\n");
        output.append(
                "Method Name                             Cohesion Level\n");
        output.append(
                "--------------------------------------------------------------\n");

        // save each class variable in classList
        for (ASTNode childNode : node.list) {
            if (childNode.getType().equals("ClassVariableDeclaration")) {
                classList.add(childNode.getIdentity());
            }
        }

        //  do for each method
        for (ASTNode childNode : node.list) {
            if (childNode.getType().equals("MethodDeclaration")) {
                analyzeMethod(childNode);
            }
        }
    } // end method analyzeClass

    /**
     * called for each method
     * @param node ASTNode
     */
    private void analyzeMethod(ASTNode node) {
        output.append(node.getIdentity());

        /**
         * list of all statements
         */
        ArrayList<StatementDS> statementList = new ArrayList<StatementDS>();
        /**
         *analyze all statements and add them to the statementList
         */
        statements(node, statementList, false, false);
        /**
         * HashMap of all modified variables
         */
        HashMap<String, StatementElement> modifiedVariables =
                new HashMap<String, StatementElement>();
        // build semantics for each statement
        for (StatementDS tmpDS : statementList) {
            if (classList.contains(tmpDS.getLVal().getElementName())) {
                tmpDS.getLVal().setType(StatementElement.variableType.
                                        ClassVariable);
            }
            if (tmpDS.getType() != null) {
                if (tmpDS.getType().equals(StatementDS.statementType.Assignment) ||
                    tmpDS.getType().equals(StatementDS.statementType.PostInc) ||
                    tmpDS.getType().equals(StatementDS.statementType.PreInc)) {
                    tmpDS.getLVal().setModified(true);
                    modifiedVariables.put(tmpDS.getLVal().getElementName(),
                                          tmpDS.getLVal());
                }
            }
            // set for selection
            tmpDS.getLVal().setInSelection(tmpDS.isInSelection());
            // set for iteration
            tmpDS.getLVal().setInIteration(tmpDS.isInIteration());
            // add modified variables to the chain of variables affecting this element
            ArrayList<StatementElement> rVals = tmpDS.getRVals();
            for (StatementElement rval : rVals) {
                StatementElement tmpEl = modifiedVariables.get(rval.
                        getElementName());
                if (tmpEl != null &&
                    !tmpEl.getElementName().equals(tmpDS.getLVal().
                        getElementName())) {
                    tmpDS.getLVal().addModifier(tmpEl);
                } else if (!rval.getElementName().equals(tmpDS.getLVal().
                        getElementName())) {
                    tmpDS.getLVal().addModifier(rval);
                }

                if (rval.hasBeenModified()) {
                    tmpDS.getLVal().addModifier(rval);
                    modifiedVariables.put(rval.getElementName(), rval);
                }

            }

        }
        int modifiedClassVariables = 0;
        int classVariableWithModifiers = 0;
        int modifiedLocVariables = 0;
        boolean iterative = false;
        boolean selective = false;
        String cohesionLevel = "";
        boolean firstModifiedClassVariableWithModifers = true;
        ArrayList<String> classVarModifiers = new ArrayList<String>();

        // do for every Statement
        for (StatementElement el : modifiedVariables.values()) {
            if (el.hasBeenModified()) {
                // class variables
                if (el.getType() != null &&
                    el.getType().equals(StatementElement.variableType.
                                        ClassVariable)) {
                    // selection
                    if (el.isInSelection()) {
                        selective = true;
                    }
                    // iteration
                    if (el.isInIteration()) {
                        iterative = true;
                    }
                    if (el.getModifiers().size() > 0) {
                        ++classVariableWithModifiers;
                        if (firstModifiedClassVariableWithModifers) {
                            createArrayListOfModifiers(el, classVarModifiers);
                            firstModifiedClassVariableWithModifers = false;
                        } else {
                            ArrayList<String> tmpList = new ArrayList<String>();
                            createArrayListOfModifiers(el, tmpList);
                            ArrayList<String> unionSet = new ArrayList<String>();
                            // get the union of modifed variables
                            for (String var : classVarModifiers) {
                                for (String tmpEl : tmpList) {
                                    if (var.equals(tmpEl)) {
                                        unionSet.add(var);
                                    }
                                }
                            }
                            classVarModifiers = unionSet;
                        }
                    }
                    ++modifiedClassVariables;
                } else { // end modified class variables
                    ++modifiedLocVariables;
                }

            }
        }
        String cohesionType = "Coincidental";
        if (modifiedClassVariables == 1 && modifiedLocVariables == 0) {
            cohesionType = "Functional";
        } else
        if (modifiedClassVariables == 1 && classVarModifiers.size() > 0) {
            cohesionType = "Sequential";
        } else
        if (modifiedClassVariables > 1 && classVarModifiers.size() > 0) {
            cohesionType = "Communicational";
        } else
        if (modifiedClassVariables == 0) {
            cohesionType = "No Class Level Assignments";
            //  cohesionType = "Coincidental";
        }

        if (iterative) {
            cohesionType = "Iterative";
        }
        if (selective) {
            cohesionType = "Conditional";
        }
        for (int i = 0;
                     i < 60 - node.getIdentity().length() - cohesionType.length();
                     ++i) {
            output.append(" ");
        }
        output.append(cohesionType + "\n");
        // print out a toString dump for development analysis
        if (DEVELOPMENT) {
            for (StatementDS tmpDS : statementList) {
                output.append("     " + tmpDS.toString() + "\n");
            }
        }
    } // end method analyzeMethod

    /**
     * recursive method to create a chain of modifiers to a variable <b>
     * using this method then :   a = b + c;  d = a;  e = d; <br>
     *   e would contain both d and a as modifiers it is dependent on
     * @param stE StatementElement
     * @param list ArrayList
     */
    private void createArrayListOfModifiers(StatementElement stE,
                                            ArrayList<String>
            list) {
        for (StatementElement tmpEl : stE.getModifiers()) {
            list.add(tmpEl.getElementName());
            if (tmpEl.getModifiers().size() > 0) {
                createArrayListOfModifiers(tmpEl, list);
            }
        }
    }

    /**
     * recusive method to analyze statements
     * @param parentNode ASTNode
     * @param statementList ArrayList
     * @param isInSelection boolean
     * @param isInIteration boolean
     */
    private void statements(ASTNode parentNode, ArrayList<StatementDS>
            statementList, boolean isInSelection, boolean isInIteration) {

        for (ASTNode childNode : parentNode.list) {

            if (childNode.getType().equals("Selection")) {
                statements(childNode, statementList, true, isInIteration);
            } else if (childNode.getType().equals("Iteration")) {
                statements(childNode, statementList, isInSelection, true);
            } else if (childNode.getIdentity().equals("StatementExpression")) {
                StatementDS tmpDS = analyzeStatement(childNode);
                tmpDS.setInIteration(isInIteration);
                tmpDS.setInSelection(isInSelection);
                statementList.add(tmpDS);
            }
        }
    }

    /**
     * called for each statement
     * @param node ASTNode
     * @return StatementDS
     */
    private StatementDS analyzeStatement(ASTNode node) {
        StatementDS localDS = new StatementDS();
        int nodeCount = node.list.size();
        int currCtr = 0;
        int nxtCtr = nodeCount > 0 ? 1 : 0;

        // check for preDec ++ or --
        String firstVal = node.list.get(0).getIdentity();
        if (firstVal.equals("++") || firstVal.equals("--")) {
            localDS.setType(StatementDS.statementType.PreInc);
            currCtr++;
        }
        currCtr = getLVal(currCtr, node.list);
        localDS.setLVal(node.list.get(currCtr).getIdentity());
        if (currCtr + 1 == nodeCount) {
            return localDS;
        }

        // have the LVAL and there is a next
        currCtr++;
        if (node.list.get(currCtr).getIdentity().equals("[")) {
            while (!node.list.get(currCtr).getIdentity().equals("]")) {
                currCtr++;
            }
            currCtr++;
        }
        if (currCtr + 1 == nodeCount) {
            return localDS;
        }

        // have the LVAL and there is a next

        String tmpVl = node.list.get(currCtr).getIdentity();
        if (tmpVl.equals("(")) {
            localDS.setType(StatementDS.statementType.MethodCall);
            return localDS;
        }
        if (tmpVl.equals("++") || tmpVl.equals("--")) {
            localDS.setType(StatementDS.statementType.PostInc);
            return localDS;
        }
        if (tmpVl.equals("+=") || tmpVl.equals("=") || tmpVl.equals("-=") ||
            tmpVl.equals("\\=") || tmpVl.equals("*=")) {
            localDS.setType(StatementDS.statementType.Assignment);
            currCtr++;
        }
        getRVals(localDS, currCtr, node.list);
        return localDS;
    }

    /**
     * called from analyzeStatement - gets a list of RVals in assignment statements
     * @param locDS StatementDS
     * @param ctr int
     * @param list ArrayList
     */
    private void getRVals(StatementDS locDS, int ctr, ArrayList<ASTNode> list) {
        boolean isFirstToken = true; // reset to true for each new rval element
        String currVariable = null;
        int nodeCount = list.size();
        String currID = null, nextID = null;
        StatementElement currEl = null;
        while (ctr < nodeCount) {
            currID = list.get(ctr).getIdentity();
            if (ctr + 1 < nodeCount) {
                nextID = list.get(ctr + 1).getIdentity();
            } else {
                nextID = null;
            }
            if (isFirstToken) {
                currEl = new StatementElement();
                isFirstToken = false;
                if (currID.equals("++") || currID.equals("--")) {
                    currEl.setModified(true);
                }
                if (currID.equals("(")) {
                    ctr++;
                    continue;
                }
            }

            if (nextID != null && (nextID.equals("++") || nextID.equals("--"))) {
                currEl.setModified(true);
                if (currEl.getElementName() == null) {
                    currEl.setElementName(currID);
                }
            }
            if (nextID != null && (nextID.equals(")"))) {
                if (currEl.getElementName() == null) {
                    currEl.setElementName(currID);
                }
                ctr++;
                continue;
            }
            if (nextID != null && nextID.equals(".")) {
                ctr += 2;
                continue;
            }
            if (nextID == null || isOperator(nextID)) {
                if (currEl.getElementName() == null) {
                    currEl.setElementName(currID);
                }
                locDS.addRVal(currEl);
                isFirstToken = true; // set for the next element
                ctr++;
                continue;
            }
            if (currID.equals("new")) {
                currEl.setType(StatementElement.variableType.ClassInitiator);
            }
            if (nextID != null && nextID.equals("(")) {
                if (currEl.getType() == null) {
                    currEl.setType(StatementElement.variableType.MethodCall);
                }
                if (currEl.getElementName() == null) {
                    currEl.setElementName(currID);
                }
            }
            // ignore the array subscripts
            if (nextID != null && nextID.equals("[")) {
                if (currEl.getElementName() == null) {
                    currEl.setElementName(currID);
                }
                String tmpVal = list.get(ctr).getIdentity();
                while (!tmpVal.equals("]")) {
                    ctr++;
                    tmpVal = list.get(ctr).getIdentity();
                }
                if (ctr == nodeCount - 1) {
                    locDS.addRVal(currEl);
                }
            }
            ctr++;
        } // end while loop
    }

    /**
     * helper function - checks for delimiters in a equation where delimiters <br>
     * are defined as arithmetic operands such as '+' and '-'
     * @param id String
     * @return boolean
     */
    private boolean isOperator(String id) {
        if (id == null) {
            return false;
        }
        if (id.equals("+") || id.equals("-") || id.equals("\\") ||
            id.equals("*") || id.equals("<<") || id.equals(">>") ||
            id.equals("%")) {
            return true;
        }
        return false;
    }

    /**
     * gets the variable name for the lVal of an expression
     * @param ctr int
     * @param list ArrayList
     * @return int
     */
    private int getLVal(int ctr, ArrayList<ASTNode> list) {
        // do until the LVal is found
        // lval is an identity that does not have a '.' as the next identity
        // ie.   java.util.Type   in this instance Type is the LVal
        int nodeCount = list.size();
        String currID = null, nextID = null;
        while (ctr < nodeCount) {
            currID = list.get(ctr).getIdentity();
            if (ctr + 1 < nodeCount) {
                nextID = list.get(ctr + 1).getIdentity();
            } else {
                return ctr;
            }
            if (!nextID.equals(".")) {
                return ctr;
            }
            ctr += 2;
        }
        return ctr;
    }

    /**
     * write the report header to output
     */
    private void setHeader() {
        output.append("\nFile:  ");
        output.append(fileName);
        output.append("\n");
    }

    /**
     * Store the file name parsed for formatted display purposes
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}


/**
 *  represents a variable in an expression
 */
class StatementElement {
    /**
     * marked true if this variable is modifed in the method
     */
    private boolean hasBeenModified = false;
    /**
     * represents the variable name
     */
    private String elementName = null;
    /**
     * set true if variable is within a if statement
     */
    private boolean isInSelection = false;
    /**
     * set true if variable is within an interation block
     */
    private boolean isInIteration = false;
    /**
     * list of elements that have modified this element <br>
     * a recursive descent of elements will get a chain of modifiers
     */
    private ArrayList<StatementElement> modifiers =
            new ArrayList<StatementElement>();
    /**
     * add a variable to the modifier list
     * @param rval StatementElement
     */
    public void addModifier(StatementElement rval) {
        modifiers.add(rval);
    }

    /**
     * return a reference to the modifier list
     * @return ArrayList
     */
    public ArrayList<StatementElement> getModifiers() {
        return modifiers;
    }

    /**
     *  enumeration of types
     */
    public static enum variableType {
        ClassVariable, LocalVariable, MethodCall, ClassInitiator} ;

        /**
         * local variableType represent the type of variable this is
         */
        variableType type = null;

        /**
         * default constructor
         */
        StatementElement() {}

        /**
         *  setter for the variable name
         * @param name String
         */
        StatementElement(String name) {
            setElementName(name);
        }

        /**
         * getter for the type : see enum variableType
         * @return variableType
         */
        variableType getType() {
            return type;
        }

        /**
         * setter for the variable type
         * @param t variableType
         */
        void setType(variableType t) {
            type = t;
        }

        /**
         * setter for the name
         * @param name String
         */
        void setElementName(String name) {
            elementName = name;
        }

        /**
         * getter for the boolean flag hasBeenModified
         * @return boolean
         */
        boolean hasBeenModified() {
            return hasBeenModified;
        }

        /**
         * getter for the variable name
         * @return String
         */
        String getElementName() {
            return elementName;
        }

        /**
         * setter for the boolean flag has been modified
         * @param val boolean
         */
        void setModified(boolean val) {
            hasBeenModified = val;
        }

        /**
         * override of toString provides information for debugging/development
         * @return String
         */
        public String toString() {
            String tmp = elementName;
            if (hasBeenModified) {
                tmp += " (modified) ";
            }
            if (type != null) {
                tmp += " (" + type.toString() + ") ";
            }
            for (StatementElement element : modifiers) {
                tmp += element.toString();
            }
            return tmp;
        }

        /**
         * getter for the boolean flag isInSelection
         * @return boolean
         */
        boolean isInSelection() {
            return isInSelection;
        }

        /**
         * getter for the boolean flag isInIteration
         * @return boolean
         */
        boolean isInIteration() {
            return isInIteration;
        }

        /**
         * setter for isInSelection
         * @param val boolean
         */
        void setInSelection(boolean val) {
            isInSelection = val;
        }

        /**
         * setter for isInIteration
         * @param val boolean
         */
        void setInIteration(boolean val) {
            isInIteration = val;
        }
    } // end class


    /**
     *   represents an expression statement <br>
     *   has Statment Elements  : one lVal and 0 . . . n rVals
     */
    class StatementDS {
        private StatementElement lVal = null;
        private ArrayList<StatementElement> rValList = new ArrayList<
                StatementElement>();
        public static enum statementType {
            PreInc, PostInc, Assignment,
            MethodCall} ;

            private statementType type = null;
            private boolean isInSelection = false;
            private boolean isInIteration = false;


            statementType getType() {
                return type;
            }

            /**
             * add a rVal to the list
             * @param rv StatementElement
             */
            void addRVal(StatementElement rv) {
                rValList.add(rv);
            }

            /**
             * set statement type: see  enum statementType
             * @param t statementType
             */
            void setType(statementType t) {
                type = t;
            }

            /**
             * get a reference to the list of rVals
             * @return ArrayList
             */
            ArrayList<StatementElement> getRVals() {
                return rValList;
            }

            /**
             * override of toString contains information for debugging
             * @return String
             */
            public String toString() {
                String tmp = lVal.toString();
                tmp += " : " + type.toString();
                if (this.isInIteration()) {
                    tmp += " (Iteration) ";
                }
                if (this.isInSelection()) {
                    tmp += " (Selection) ";
                }
                for (StatementElement rVal : rValList) {
                    tmp += " := " + rVal.toString();
                }
                return tmp;
            }

            /**
             * setter for the LVal
             * @param rv StatementElement
             */
            void setLVal(StatementElement rv) {
                lVal = rv;
            }

            /**
             * setter for the LVal
             * @param id String
             */
            void setLVal(String id) {
                lVal = new StatementElement(id);
            }

            /**
             * getter for the LVal
             * @return StatementElement
             */
            StatementElement getLVal() {
                return lVal;
            }

            /**
             * getter for the boolean flag isInSelection
             * @return boolean
             */
            boolean isInSelection() {
                return isInSelection;
            }

            /**
             * getter for the boolean flag isInIteration
             * @return boolean
             */
            boolean isInIteration() {
                return isInIteration;
            }

            /**
             * setter for isInSelection
             * @param val boolean
             */
            void setInSelection(boolean val) {
                isInSelection = val;
            }

            /**
             * setter for isInIteration
             * @param val boolean
             */
            void setInIteration(boolean val) {
                isInIteration = val;
            }

        }
