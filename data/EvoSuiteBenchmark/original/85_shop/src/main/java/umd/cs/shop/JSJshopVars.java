package umd.cs.shop;
public final class JSJshopVars

{

     /* class variables */

    

    static char LastCharRead;
    static int VarCounter;
    public static String errorMsg = null;
    static boolean flagParser = false;// if true flags will appear
                                      // when parsing the file
    static boolean flagPlanning = false;// if true flags will appear
                                      // when planning the file
    static int flagLevel =0; // the higher the more verbose the output
    static boolean flagExit=true;
    static boolean allPlans=false;
    /* Table for the parser */
    
    static int leftPar = 0x0028;  //(
    static int rightPar = 0x0029; //)
    static int apostrophe = 0x0027; //'
    static int colon = 0x003A;      //:
    static int dot = 0x002E;      //.
    static int semicolon = 0x003B; //;
    static int exclamation = 0x0021; //!
    static int interrogation = 0x003F; //?
    static int minus = 0x002D; //-
    static int equalT = 0x003D; //=
    static int greaterT = 0x003E; //>
    static int lessT = 0x003C; //<
    static int coma = 0x002C; //,
    static int astherisk = 0x002A; //*
    static int rightBrac = 0x005D; //[
    static int leftBrac = 0x005B;//]
    static int verticalL = 0x007C; //|
    static int plus = 0x002B; //+
    static int whiteSpace = 0x0020;//
    static int percent = 0x0025;//  
    static int backquote = 96;//  
    static int slash = 47;//  
    
    //Any addition/change of a character implies a change
    // in the following methods in JSUtil:
    // stringTokenizer/1, initParseTable/1 and printTokenizer/1

    static void SetAllPlans(boolean val){
    
         allPlans=val;
    }
    
    
    static void SetFlagExit(boolean val){
    
         flagExit=val;
    }
    
    static void SetFlagLevel(int val){
    
         flagLevel=val;
    }
    
    

}
