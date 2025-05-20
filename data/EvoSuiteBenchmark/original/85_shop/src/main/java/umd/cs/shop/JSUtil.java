package umd.cs.shop;
import java.io.*;


public final class JSUtil

{

    static void print(String str)
    {
        System.out.print(str);
    }
    
    static void println(String str)
    {
        System.out.println(str);
    }
    
    static void flagParser(String line)
    {
        if (JSJshopVars.flagParser) {
             JSUtil.flag10(line);
        }
    }
    
     static void flagParser(String line, int i)
    {
        if (JSJshopVars.flagParser) {
             JSUtil.flag10(line, i);
        }
    }
    
     static void flagPlanning(String line)
    {
        if (JSJshopVars.flagPlanning) {
             JSUtil.flag10(line);
        }
    }
    static void flagPlanning(String line, int i)
    {
        if (JSJshopVars.flagPlanning) {
             JSUtil.flag10(line, i);
        }
    }
   /*
   static void verbose10(String line)
    {
        if (JSJshopVars.flagLevel > 0)
            System.out.println(line);
        if (JSJshopVars.flagLevel > 10){
        try{
            System.in.read();
            }
            catch(IOException e)
            {
                System.out.println("Error flag1 : " + e);	
                return;	
            }
        }
      }
      
    */
    
   static void flag(String line){ JSUtil.flag10(line);}

    static void flag10(String line)
    {
        if (JSJshopVars.flagLevel > 0)
            System.out.println(line);
        if (JSJshopVars.flagLevel > 10){
        try{
            System.in.read();
            }
            catch(IOException e)
            {
                System.out.println("Error flag1 : " + e);	
                return;	
            }
        }
      }
      
      static void flag10(String line, int i)
    {
        if (JSJshopVars.flagLevel > i)
            System.out.println(line);
        if (JSJshopVars.flagLevel > 10){
        try{
            System.in.read();
            }
            catch(IOException e)
            {
                System.out.println("Error flag1 : " + e);	
                return;	
            }
        }
      }
      
 static void flag2(String line){}

  static void flag20(String line)
    {
        System.out.println(line);
        try{
            System.in.read();
            }
            catch(IOException e)
            {
                System.out.println("Error flag1 : " + e);	
                return;	
            }
      }
      
     static boolean readToken(StreamTokenizer tokenizer, String name)
     {
        try{
            tokenizer.nextToken();
            if (tokenizer.ttype == tokenizer.TT_EOF){
            System.out.println("Line " +tokenizer.lineno()+" : " +name+": unexpected EOF");	
            return false;
           }
        } catch (Exception e) {
            System.out.println(name+": Error reading control parameters: " + e);
            System.exit(1);
        }
        return true;
     }
     
      static boolean 
         expectTokenType(int type, StreamTokenizer tokenizer, String name)
     {
        if (!JSUtil.readToken(tokenizer,name)){
           return false;
        }
        if (tokenizer.ttype != type){
            System.out.println(name+": "
                       +JSUtil.stringTokenizer(tokenizer)+" expected");
            return false;
      }
      return true;
     }
     
     static String readWord(StreamTokenizer tokenizer, String name)
     // reads everything from the next token on until
     // a white space is encountered.
     // if the returned string is "%%%" means that an error occur
     {
        String w = "";
        String wT = "";
        
       tokenizer.ordinaryChar(JSJshopVars.whiteSpace);
        if (!JSUtil.readToken(tokenizer,name))
             return "%%%";
        while (tokenizer.ttype != JSJshopVars.whiteSpace &&
               tokenizer.ttype != JSJshopVars.rightPar)
        { 
            wT = JSUtil.stringTokenizer(tokenizer);
            if (!wT.equals("%%%")){
                //return "%%%";
                w = w.concat(wT); 
            }
             if (!JSUtil.readToken(tokenizer,name))
                   return "%%%";
        }
        if (tokenizer.ttype == JSJshopVars.rightPar)
              tokenizer.pushBack();
       tokenizer.whitespaceChars(JSJshopVars.whiteSpace, JSJshopVars.whiteSpace);
       // JSUtil.initParseTable(tokenizer);
      //  JSUtil.flag("readWord: "+w+"<");
        return w;
     }
     
     static void printTokenizer(StreamTokenizer tokenizer)
     {
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER){
                             System.out.print(new 
                                     Integer((int) tokenizer.nval).toString()+" ");
                     }
                     if (tokenizer.ttype == JSJshopVars.leftPar){
                          System.out.print("( ");
                     }
                    if (tokenizer.ttype == JSJshopVars.rightPar){
                          System.out.print(") ");
                     }
                    if (tokenizer.ttype == JSJshopVars.colon){
                          System.out.print(": ");
                     }
                     if (tokenizer.ttype == JSJshopVars.dot){
                          System.out.print(". ");
                     }
                     if (tokenizer.ttype == JSJshopVars.semicolon){
                          System.out.print("; ");
                     }
                     if (tokenizer.ttype == JSJshopVars.apostrophe){
                          System.out.print("' ");
                     }
                     if (tokenizer.ttype == JSJshopVars.exclamation){
                          System.out.print("! ");
                     }
                     if (tokenizer.ttype == JSJshopVars.interrogation){
                          System.out.print("? ");
                     }
                     if (tokenizer.ttype == JSJshopVars.percent){
                          System.out.print("% ");
                     }
                     if (tokenizer.ttype == JSJshopVars.minus){
                          System.out.print("- ");
                     }
                     if (tokenizer.ttype == JSJshopVars.lessT){
                          System.out.print("< ");
                     }
                     if (tokenizer.ttype == JSJshopVars.equalT){
                          System.out.print("= ");
                     }
                     if (tokenizer.ttype == JSJshopVars.greaterT){
                          System.out.print("> ");
                     }
                     if (tokenizer.ttype == JSJshopVars.plus){
                          System.out.print("+ ");
                     }
                     if (tokenizer.ttype == JSJshopVars.coma){
                          System.out.print(", ");
                     }
                     if (tokenizer.ttype == JSJshopVars.astherisk){
                          System.out.print("* ");
                     }
                     if (tokenizer.ttype == JSJshopVars.slash)
                         System.out.print("/ ");
                    if (tokenizer.ttype == JSJshopVars.backquote)
                           System.out.print("` ");
                     if (tokenizer.ttype == JSJshopVars.rightBrac){
                          System.out.print("] ");
                     }
                     if (tokenizer.ttype == JSJshopVars.leftBrac){
                          System.out.print("[ ");
                     }
                     if (tokenizer.ttype == JSJshopVars.verticalL){
                          System.out.print("| ");
                     }
                     if (tokenizer.ttype == tokenizer.TT_WORD){
                          System.out.print(tokenizer.sval+ " ");
                     }
                     JSUtil.flag("");//flag
     }
     
     static String stringTokenizer(StreamTokenizer tokenizer)
     {
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
                             return new 
                                     Integer((int) tokenizer.nval).toString();
        if (tokenizer.ttype == JSJshopVars.leftPar)
                          return "(";
        if (tokenizer.ttype == JSJshopVars.rightPar)
                          return ")";
        if (tokenizer.ttype == JSJshopVars.colon)
                          return ":";
        if (tokenizer.ttype == JSJshopVars.semicolon)
                          return ";";
        if (tokenizer.ttype == JSJshopVars.dot)
                          return ".";
        if (tokenizer.ttype == JSJshopVars.apostrophe)
                          return "'";
        if (tokenizer.ttype == JSJshopVars.exclamation)
                          return "!";
        if (tokenizer.ttype == JSJshopVars.interrogation)
                          return "?";
        if (tokenizer.ttype == JSJshopVars.percent)
                          return "%";
        if (tokenizer.ttype == JSJshopVars.minus)
                          return "-";
        if (tokenizer.ttype == JSJshopVars.lessT)
                          return "<";
        if (tokenizer.ttype == JSJshopVars.equalT)
                          return "=";
        if (tokenizer.ttype == JSJshopVars.greaterT)
                          return ">";
        if (tokenizer.ttype == JSJshopVars.plus)
                          return "+";
        if (tokenizer.ttype == JSJshopVars.coma)
                          return ",";
        if (tokenizer.ttype == JSJshopVars.astherisk)
                          return "*";
        if (tokenizer.ttype == JSJshopVars.slash)
                          return "/";
        if (tokenizer.ttype == JSJshopVars.backquote)
                          return "`";
        if (tokenizer.ttype == JSJshopVars.rightBrac)
                          return "]";
        if (tokenizer.ttype == JSJshopVars.leftBrac)
                          return "[";
        if (tokenizer.ttype == JSJshopVars.verticalL)
                          return "|";
        if (tokenizer.ttype == tokenizer.TT_WORD)
                          return tokenizer.sval;
       if (tokenizer.ttype == JSJshopVars.whiteSpace)
                          JSUtil.flag("the tokenizer is SPACE!");                  
        JSUtil.flag("JSUtil>>stringTokenizer is returning %%%");
        return "%%%";
     }
     
      static void initParseTable(StreamTokenizer tokenizer)
    { 
              tokenizer.ordinaryChar(JSJshopVars.leftPar);
              tokenizer.ordinaryChar(JSJshopVars.rightPar);
              tokenizer.ordinaryChar(JSJshopVars.colon);
              tokenizer.ordinaryChar(JSJshopVars.apostrophe);
              tokenizer.ordinaryChar(JSJshopVars.exclamation);
              tokenizer.ordinaryChar(JSJshopVars.interrogation);
              tokenizer.ordinaryChar(JSJshopVars.percent);
              tokenizer.ordinaryChar(JSJshopVars.minus);
              tokenizer.ordinaryChar(JSJshopVars.equalT);
              tokenizer.ordinaryChar(JSJshopVars.greaterT);
              tokenizer.ordinaryChar(JSJshopVars.lessT);
              tokenizer.ordinaryChar(JSJshopVars.coma);
             tokenizer.ordinaryChar(JSJshopVars.dot);
              tokenizer.ordinaryChar(JSJshopVars.astherisk);
              tokenizer.ordinaryChar(JSJshopVars.rightBrac);
              tokenizer.ordinaryChar(JSJshopVars.leftBrac);
              tokenizer.ordinaryChar(JSJshopVars.verticalL);
              tokenizer.ordinaryChar(JSJshopVars.plus);
              tokenizer.ordinaryChar(JSJshopVars.backquote);
              tokenizer.ordinaryChar(JSJshopVars.slash);
              
              tokenizer.commentChar(JSJshopVars.semicolon);
             
    }
 }
