package umd.cs.shop;
/*HICAP import nrl.aic.hicap.*;*/
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;
import java.net.*;

//The main constructor of this class is JSJshop(String nameFile). This
//constructor will open and parse the <file> named nameFile and solves the 
//problem  stated in it. It assumes that the file contains a single problem
// i.e., "make-problem" and a single domain, i.e., "make-domain". If several
// domains and problems are given it will parse all of them and solve the
// last problem for the last domain.

public final class JSJshop

{
    /*HICAP*//*==== class variables ====*/
    /*HICAP*/public static boolean corbaToHicap = false;
    /*HICAP*/public static JApplet applet;

   /* instance variables */

    private JSPlanningDomain dom;

    private JSPlanningProblem prob;
    
    private JSListPlanningProblem probSet= new JSListPlanningProblem();
    
    private JSPlan sol;
    
    private JSJshopNode tree = null;
    
    private JSPairPlanTSListNodes solution;
    
/*HICAP:    private NeoEditor aNeoEditor;*/
  
    /*====  main ====*/
    public static void main(String[] args)
    {
      if( args.length < 2 ||args.length > 4){
        JSUtil.println("Usage :" );
        JSUtil.println(" java JSJshop <domainDef-file-name> <problemDef-file-name> [number-of-plans]");// [verbose-level]" );
        JSUtil.println("[number-of-plans] can be 'one' or 'all'. The default value is 'one'" );
     //   JSUtil.println("[verbose-level] can be integer from  0 to 10. The default verbose value is 0" );
        return;
      }
      String one="one";
      String all="all";
      int level,i;
      try{ 
       for ( i=2; i< args.length;i++)
        
        if (one.equalsIgnoreCase(args[i]))
           JSJshopVars.allPlans=false;
        else
        if (all.equalsIgnoreCase(args[i]))
           JSJshopVars.allPlans=true;
       else{
          level=Integer.valueOf(args[i]).intValue();
           JSJshopVars.flagLevel=level;
       /*    
            JSUtil.println("Invalid parameter" );
            return;*/
          }    
        }catch (NumberFormatException e) {
          JSUtil.println("Invalid parameter" );
          return;
        }    
        new JSJshop(args[0],args[1]);        



/*HICAP:
      else if( args.length==1 && args[0].indexOf(".shp") >= 0)
      {
        new JSJshop(args[0]);
      }
      else 
      {
        System.err.println("USAGE: java JSJshop [<file-name>.shp]");
      }
*/
    } // main

    /* constructors */

 public
    JSJshop()    {    }


/******** main constructor **********/
   
    public JSJshop(String nameDomainFile , String nameProblemFile)
    {
      
      JSPairPlanTSListNodes pair;
      JSListPairPlanTStateNodes allPlans;
      JSUtil.println("Reading file " + nameDomainFile);
      if (! parserFile(nameDomainFile)) 
        if (JSJshopVars.flagExit)
          System.exit(0);
        else
          return;
      JSUtil.println("Domain file parsed successfully");
      JSUtil.println("Reading file " + nameProblemFile);
      if (! parserFile(nameProblemFile)) 
        if (JSJshopVars.flagExit)
          System.exit(0);
        else
          return;
      
      JSUtil.println("Problem file parsed successfully");
       
      
      for (int k=0; k< probSet.size();k++){
        
        prob= (JSPlanningProblem)probSet.elementAt(k);     
        JSUtil.println("Solving Problem :" + prob.Name());       
        allPlans= dom.solveAll(prob,JSJshopVars.allPlans);
        if (allPlans.isEmpty()) 
         {
           // Return the failing solution to HICAP
            sol = new JSPlan();
            sol.assignFailure();
            solution=new  JSPairPlanTSListNodes ( new JSPairPlanTState(sol,new JSTState()), new Vector() );
            JSUtil.println("0 plans found"); 
        }
        else
        {
           // Return the first solution to HICAP
           solution= (JSPairPlanTSListNodes) allPlans.elementAt(0);
           sol= solution.planS().plan();
                      
           JSUtil.println(allPlans.size() +  " plans found.");
           if (JSJshopVars.flagLevel > 0 ){
                JSUtil.println("********* PLANS *******");
                for (int i=0;i<allPlans.size();i++)
                 { 
                   JSUtil.println( "Plan # " + (i+1));
                   pair= (JSPairPlanTSListNodes )allPlans.elementAt(i);
                   pair.planS().plan().printPlan();//.print();
                   // pair.print();
                 }      
           }
        }
      }
      return;
    }

/*HICAP:   
    public
    JSJshop( String nameFile, NeoEditor aNE, JSTaskAtom pred)
    {
      this.aNeoEditor = aNE;
      setFile( nameFile, pred);
      
    }
*/

    public
    JSJshop( String nameFile, JSTaskAtom pred)
    {
      setFile( nameFile, pred);
      
    }

    public JSJshopNode getTree()
    {
        return tree;
    }

    public JSPairPlanTSListNodes getSolution()
    {
      return solution;
    }

    public JSListLogicalAtoms getAddList()
    {
      if(tree==null) return null;
      return solution.planS().tState().addList();
    }

    public JSListLogicalAtoms getDeleteList()
    {
      if(tree==null) return null;
      return solution.planS().tState().deleteList();      
    }

    /*==== solves planning problem for input file ====*/
    public JSPairPlanTSListNodes setFile( String nameFile,
                                          JSTaskAtom pred )
    {
        JSPairPlanTSListNodes pair;
        Vector listNodes = new Vector();
         
        JSJshopVars.VarCounter = 0;
        
        //JSUtil.flag2(nameFile+" will be parsered");
        parserFile(nameFile);
        
        /*HICAP: prob.assignState(aNeoEditor.translateState());*/
        /*HICAP: prob.makeTask(pred);*/
        
        JSUtil.flag("File parsed");
        dom.print();
        JSUtil.flag("<- domain");
        prob.print();
        JSUtil.flag("<- problem");
        
        JSJshopVars.allPlans = false;
        JSListPairPlanTStateNodes listPairs = dom.solveAll(prob,JSJshopVars.allPlans);
        if (listPairs.size()==0)
              return null;
        else
            pair = (JSPairPlanTSListNodes) listPairs.elementAt(0);
        
        JSUtil.flag("**After planning");
       
        sol = pair.planS().plan();
        if (sol.isFailure())
        {
            JSUtil.print("*NO* SOLUTION FOUND"); 
	        return null;
        }
        else
        {
            JSUtil.print("SOLUTION FOUND");
            pair.planS().tState().print();
            JSUtil.println("***************FINAL SOLUTION*******");
            sol.print();
                     
            JSUtil.println("********* list-tree FINAL SOLUTION*******");

    
            JSTaskAtom ta;
            Vector listT;
            JSJshopNode node;
            
	    listNodes = pair.listNodes();
            for (int i = 0; i < listNodes.size();i++)
            {
                node = (JSJshopNode) listNodes.elementAt(i);
                node.print2();
            }
        // we assume that there is only a single task
        // at the top level. If more than 1 task
        // must be reduced at the top level, listNodes
        // contain the list of trees (one for each task).
        // Whereas this does not affect SHOP, HICAP assumes
        // that it receives a single task at the top level
        //tree.print();
        //listNodes = pair.listNodes();
	    //pair.print();
            //JSJshopNode node;
            node = (JSJshopNode) listNodes.elementAt(listNodes.size()-1);
                  // last element must be the root
            listNodes.removeElement(node);
            tree = new JSJshopNode(node,listNodes);
   JSUtil.println("********* tree FINAL SOLUTION*******");

	    tree.print();
            solution = pair;
	    return pair;
        } // solution found
    } // setFile
    
    
   
    
    public void testParser()
    {
          try {
              
              FileReader fr = new FileReader("farp.shp");
              StreamTokenizer tokenizer = new StreamTokenizer(fr);
              JSUtil.initParseTable(tokenizer);
              while (tokenizer.nextToken() != StreamTokenizer.TT_EOF){
                     if (tokenizer.ttype == StreamTokenizer.TT_NUMBER){
                             System.err.print(new 
                                     Double(tokenizer.nval).toString()+" ");
                     }
                     if (tokenizer.ttype == JSJshopVars.leftPar){
                          System.err.print("( ");
                     }
                    if (tokenizer.ttype == JSJshopVars.rightPar){
                          System.err.print(") ");
                     }
                    if (tokenizer.ttype == JSJshopVars.colon){
                          System.err.print(": ");
                     }
                     if (tokenizer.ttype == JSJshopVars.semicolon){
                          System.err.print("; ");
                     }
                     if (tokenizer.ttype == JSJshopVars.apostrophe){
                          System.err.print("' ");
                     }
                     if (tokenizer.ttype == JSJshopVars.exclamation){
                          System.err.print("! ");
                     }
                     if (tokenizer.ttype == JSJshopVars.interrogation){
                          System.err.print("? ");
                     }
                     if (tokenizer.ttype == JSJshopVars.minus){
                          System.err.print("- ");
                     }
                     if (tokenizer.ttype == JSJshopVars.lessT){
                          System.err.print("< ");
                     }
                     if (tokenizer.ttype == JSJshopVars.equalT){
                          System.err.print("= ");
                     }
                     if (tokenizer.ttype == JSJshopVars.greaterT){
                          System.err.print("> ");
                     }
                     if (tokenizer.ttype == JSJshopVars.plus){
                          System.err.print("+ ");
                     }
                    
                     if (tokenizer.ttype == JSJshopVars.backquote){
                          System.err.print("` ");
                     }
                     
                     if (tokenizer.ttype == JSJshopVars.slash){
                          System.err.print("/ ");
                     }
                     if (tokenizer.ttype == JSJshopVars.coma){
                          System.err.print(", ");
                     }
                     if (tokenizer.ttype == JSJshopVars.astherisk){
                          System.err.print("* ");
                     }
                     if (tokenizer.ttype == JSJshopVars.rightBrac){
                          System.err.print("] ");
                     }
                     if (tokenizer.ttype == JSJshopVars.leftBrac){
                          System.err.print("[ ");
                     }
                     if (tokenizer.ttype == JSJshopVars.verticalL){
                          System.err.print("| ");
                     }
                     if (tokenizer.ttype == tokenizer.TT_WORD){
                          System.err.print(tokenizer.sval+ " ");
                     }
                      
              }
              fr.close();
              //JSUtil.flag("End of parsing");
              
           } catch (Exception e) {
            JSUtil.println("Error reading control parameters: " + e);
            System.exit(1);
        }
        
    }
    
    public boolean parserFile(String libraryFile)

    {
       String libraryDirectory = "."; 
       
       try{
        FileReader fr = new FileReader(libraryFile);
        StreamTokenizer tokenizer = new StreamTokenizer(fr);
        tokenizer.lowerCaseMode(true);
        JSUtil.initParseTable(tokenizer);  
        if(fr==null){
            JSUtil.println("Can not open file : " + libraryFile);
            return false;
        }    
        while (tokenizer.nextToken() != tokenizer.TT_EOF)
            processToken(tokenizer);
        fr.close(); 
       }
       catch(IOException e){
            System.out.println("Error in readFile() : " + e);
            return false;	
	   }
	   catch(JSParserError parserError){
            System.out.println("Error in parsing file");
            return false;	
	   }
	   return true;
   }         

  public BufferedReader getBufferedReader( String dir, String file)
  {
    return  getBufferedReader( dir, file);
  }

  public BufferedReader getBufferedReader( String dir, String file,
					   JApplet applet)
  {
    if( file == null) return null;
    BufferedReader br = null;
    FileInputStream libraryFileInputStream = null;
    InputStream conn = null;
    String line;
    try 
    {
	  if(applet != null)
	  {
	    URL url = getAppletURL( file, applet);
	    if( url==null) 
	    {
	      System.err.println("Util.getBufferedReader() error: cannot get URL");
	      return null;
	    }
	    else
	    {
	      conn = url.openStream();
	      if(conn==null)
	      {
            System.err.println("Util.getBufferedReader() error: cannot open URL");
            return null;
	      }
	    }
      } // is applet
      else  // is application
      {
        libraryFileInputStream  = 
          new FileInputStream(dir + File.separator +  file);
      }
    }
    catch(IOException e)
    {
      System.err.println("Error 1 in Util.getBufferedReader : " + e);
      return null;
    }

    if(applet!=null)
    {
	   br = 
	       new BufferedReader(new InputStreamReader( conn));
       }
      else  // application
      {
        try 
        {
          br = 
            new BufferedReader(new InputStreamReader(libraryFileInputStream, 
				     System.getProperty("file.encoding"))); 
        } catch(UnsupportedEncodingException e)
        {
          System.err.println("Error 2 in Util.getBufferedReader : " + e);
          return null;
        } 
      }
    return br;
  } // getBufferedReader
                  
  public URL getAppletURL( String file, JApplet applet)
  {
    try
    { return(new URL(applet.getCodeBase() + file)); }
    catch( MalformedURLException e){ return null; }
  }

public void processToken(StreamTokenizer tokenizer)
{ 
    if (tokenizer.ttype == JSJshopVars.leftPar)
    {
        if (!JSUtil.expectTokenType(tokenizer.TT_WORD,tokenizer,
                     "Expected 'defdomain or defproblem' "))
              throw new JSParserError(); //return;
             
        tokenizer.pushBack();
        
        String w = JSUtil.readWord(tokenizer,"JSJshop>>processToken");
        if (w.equals("%%%"))
             throw new JSParserError(); //return;
        
        if (w.equalsIgnoreCase("defdomain")){
                dom = new JSPlanningDomain(tokenizer);
                return;
        }
        else{ 
            if (w.equalsIgnoreCase("defproblem")){
                    prob = new JSPlanningProblem(tokenizer);
                    probSet.addElement(prob);
                    return;
            }
        }
       
       System.err.println("Line : " + tokenizer.lineno()+" Expecting defdomain or defproblem");	  
        throw new JSParserError(); //return;
      }
      else{
        System.err.println("Line : " + tokenizer.lineno()+" Expected '('");
        throw new JSParserError(); //return;
      }
  }
 
 public JSPlanningDomain dom()
 {
    return dom;
 }
 
 public JSPlanningProblem prob()
 {
    return prob;
 }
 
 public JSPlan sol()
 {
    return sol;
 }
         
 public JSJshopNode tree()
 {
    return tree;
 }
  


}

            

                    

                    

    

