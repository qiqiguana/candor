package framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Reader;
import java.io.PrintStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Allow executing any Class method from command line
 * 
 * @author Laurent BOIS
 * @version $Revision: 1.4 $ $Date: 2004/05/07 19:28:46 $
 */
public class MainClass {

    private boolean        error          = false;
    private static boolean hasConstructor = false, hasInitArgs = false,
                           hasParams = false, showClass = false, callMain = false,
                           isVerbose = true, logOutput = false, isCommandOK = true,
                           showHelp = false, isNew = false, debugOn = true;
    private static int     mIndex         = 0, initargsStartIndex = 0,
                           initargsEndIndex = 0, paramsStartIndex = 0,
                           paramsEndIndex = 0;
    private static Object  initArgs[]     = null, params[] = null;
    private static Class[] cParamSpec     = null, paramSpec = null;
    private static String  fpath          = "";
    private static PrintWriter fwriter = null;

    public static PrintStream stdout = null, stderr = null;

    /**
    * Show class def.
    */
    public MainClass(String className) {
        Object returnValue = null;
        Method m           = null;

        try {
            Class c = Class.forName(className);
            // Dump class name
            print("\nclass name : \t"+c.getName());

            
            if (c == null) {
                throw new Exception("Class is null");
            }

            
            // Dump constructors
            dumpConstructors(c);

            //dump methods
            dumpMethods(c);
            
            // Dump class members
            dumpMembers(c);
            
            

            
        } catch  (Exception e) {
            debug("Oops, exception [" + e.getClass().getName() + "]: ");

            if (e instanceof InvocationTargetException) {
                e = (Exception) ((InvocationTargetException) e)
                    .getTargetException();
            }

            debug("--->" + e);

            error       = true;
            returnValue = e.toString();

            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
        }

    }
    /**
    * Call 'static void main(String[] args)' method 
    */
    
    public MainClass(String className, String methodName,
                          Object[] pparams) {
      Object returnValue = null;
      Class c = null;
      Method[] methods = null;
      Method m = null;
      String[] args = null;

      try {
        c = Class.forName(className);
/*
//dump methods
dumpMethods(c);
// Dump class members
dumpMembers(c);
*/
        methods = c.getDeclaredMethods();            

        for (int i = 0; i < methods.length; i++) {
          if (methods[i].getName().equals(methodName))
            {
              m = methods[i]; break;
            }
        }

        Class[] paramSpec = m.getParameterTypes();
        if (pparams != null) {
        
        args = new String[pparams.length];
        for (int i = 0; i < pparams.length; i++) {
          args[i] = new String((String)pparams[i]);
        }
        } else args = new String[]{null};

        Object[] params = new Object[1];
        params[0] = args;
        print("\n\nInvoking method "+methodName+" from class "+className+"\n");
        returnValue = m.invoke(null, params);
            

      } catch (Exception e) {
            debug("Oops, exception [" + e.getClass().getName() + "]: ");

            if (e instanceof InvocationTargetException) {
                e = (Exception) ((InvocationTargetException) e)
                    .getTargetException();
            }

            debug("--->" + e);

            error       = true;
            returnValue = e.toString();

            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
      }
        
    }

    /** 
    * Call method other than 'static void main(String[] args)'
    * Method must be in the format : method(type argname[, ..., type argname])
    * where type is :
    * java.lang.String
    * java.lang.Integer, int 
    * java.lang.Long, long
    * java.lang.Number
    * java.lang.Character, char
    * java.lang.Short, short
    * java.lang.Byte, byte
    * java.lang.Boolean, boolean
    * java.lang.Float, float
    * java.lang.Double, double
    */
    
    public MainClass(String className, boolean hasConstructor,
                          Class[] cParamSpec, Object[] initArgs, String methodName,
                          Class[] paramSpec, Object[] params) {

        int    pcount      = 0;
        Object returnValue = null;
        Method m           = null;

        try {
            Class c = Class.forName(className);

            if (c == null) {
                throw new Exception("Class is null");
            }
/*
//dump methods
dumpMethods(c);            
// Dump class members
dumpMembers(c);
*/
            if (!hasConstructor) {

                //pn("static Class : method invoked = "+methodName);
                m = c.getDeclaredMethod(methodName, paramSpec);

                //pn("get method = "+(m==null?"null":"OK")); 
                if (m != null) {
                    print("\n\nInvoking method "+methodName+" from class "+className+"\n");                
                    if (m.getModifiers()
                            == (Modifier.STATIC + Modifier.PUBLIC)) {


                        returnValue = m.invoke(null, params);
                    } else {    // a tester (L.Bois 22-AUG-2002)

                        returnValue =
                            m.invoke(Class.forName(className).newInstance(),
                                     params);
                    }
                } else {
                    throw new Exception("Class.getDeclaredMethod returns null");
                }
            } else {
                
                Constructor constructor = c.getConstructor(cParamSpec);
                Object      instance    = constructor.newInstance(initArgs);

                m           = c.getDeclaredMethod(methodName, paramSpec);
                print("\n\nInvoking method "+methodName+" from class "+className+"\n");                
                returnValue = m.invoke(instance, params);
            }                   // end if (constructors.length)...

            if (returnValue != null) {
                if (returnValue instanceof String) {
                    print("\nreturnValue for "+c.getName()+"."+m.getName()+"(...) : "
                                       + (String) returnValue);
                } else {
                    print("\nreturnValue for "+c.getName()+"."+m.getName()+"(...) : "
                                       + returnValue.toString());
                }
            }
        } catch (Exception e) {
            debug("Oops, exception [" + e.getClass().getName() + "]: ");

            if (e instanceof InvocationTargetException) {
                e = (Exception) ((InvocationTargetException) e)
                    .getTargetException();
            }

            debug("--->" + e);

            error       = true;
            returnValue = e.toString();

            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
        }
    }

    public static void main(String[] args) {
        
        
        MainClass wrap = null;

        checkCommand(args);
        // Silent mode
        if (!isVerbose) debugOn = false;
        // output is a file
        if (logOutput)
          fwriter = openFileWriter(fpath);
        // Help page
        if (showHelp)
        {usage(); System.exit(0);}
        // Class definition
        else if (showClass)
          wrap = new MainClass(args[1]);
        // invoke method  
        else {
          loadArrays(args);
          // case method is 'main'
          if (callMain)
            wrap = new MainClass(args[1], args[mIndex], params);
          // other cases
          else
            wrap = new MainClass(args[1], hasConstructor, cParamSpec, initArgs,
                                  args[mIndex], paramSpec, params);
        }

          // Release resource
          if (logOutput)
            fwriter.close();
    }

    private static void loadArrays(String[] args) {

        int len = args.length;

        try {
            if (hasConstructor) {
                if (hasInitArgs) {
                    cParamSpec =
                        new Class[(initargsEndIndex - initargsStartIndex + 1) / 2];
                    initArgs     =
                        new Object[(initargsEndIndex - initargsStartIndex + 1) / 2];

                    int j = 0;
                    int i = initargsStartIndex;

                    while (i < initargsEndIndex) {
                                          
                        cParamSpec[j] = getDescriptor(args[i]);
                        if (!isPrimitive(args[i])) {
                          initArgs[j] = setArgument(args[i], args[i + 1]);
                        } else {
                          initArgs[j] = getPrimitiveValue(cParamSpec[j], args[i + 1]);
                        }

                        j++;

                        i += 2;
                    }
                }
            }

            if (hasParams) {
            
             if (!args[mIndex].equals("main")) {
                paramSpec =
                    new Class[(paramsEndIndex - paramsStartIndex + 1) / 2];
                params    =
                    new Object[(paramsEndIndex - paramsStartIndex + 1) / 2];
             } else {
             
                params    =
                    new Object[(paramsEndIndex - paramsStartIndex + 1)];
             }

                int k = 0;
                int l = paramsStartIndex;
                if (!args[mIndex].equals("main")) {
                while (l < paramsEndIndex) {
                    
                      paramSpec[k] = getDescriptor(args[l]);

                    if (!isPrimitive(args[l])) {
                      
                        params[k] = setArgument(args[l], args[l + 1]);
                      //else 
                      //  params[k] = setArgument("java.lang.String", args[l]);                      
                    } else {
                        
                          params[k] = getPrimitiveValue(paramSpec[k],
                                                      args[l + 1]);
                    }

                    k++;
                    
                      l += 2;
                    
                }
              } else {
                while (l <= paramsEndIndex) {
                      
                      params[k] = setArgument("java.lang.String", args[l]);                      

                      k++;
                    
                      l += 1;
                    
                }              

              }
            } 
            
            
        } catch (Exception e) {
            pn("Oops, exception [" + e.getClass().getName()
                               + "] (loadArrays) : ");
            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
        }
    }

    private static Object setArgument(String argSpec, String arg) {

        Object instance = null;

        try {
            Class       c           = Class.forName(argSpec);
            Constructor constructor = c.getConstructor(new Class[]{
                Class.forName("java.lang.String") });
            Object[]    cArgs       = new Object[]{ arg };

            instance = constructor.newInstance(cArgs);

            return instance;
        } catch (Exception e) {
            pn("Oops, exception [" + e.getClass().getName()
                               + "]: ");

            if (e instanceof InvocationTargetException) {
                e = (Exception) ((InvocationTargetException) e)
                    .getTargetException();
            }

            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
        }
        return instance;
    }

    private static boolean isPrimitive(String argSpec) throws Exception {

        Class   c         = null;
        boolean primitive = false;

        try {
            c = Class.forName(argSpec);
        } catch (ClassNotFoundException cnfe) {
            if ((argSpec.equals("int")) || (argSpec.equals("boolean"))
                    || (argSpec.equals("float")) || (argSpec.equals("short"))
                    || (argSpec.equals("char")) || (argSpec.equals("byte"))
                    || (argSpec.equals("double"))
                    || (argSpec.equals("long"))) {
                primitive = true;
            } else {
                throw new Exception("[isPrimitive] " + argSpec
                                    + "is unknown");
            }
        }
      return primitive;
    }

    private static Class getDescriptor(String argSpec) throws Exception {

        Class c = null;

        try {
            c = Class.forName(argSpec);
        } catch (ClassNotFoundException cnfe) {
            if (argSpec.equals("int")) {
                c = Integer.TYPE;
            } else if (argSpec.equals("boolean")) {
                c = Boolean.TYPE;
            } else if (argSpec.equals("float")) {
                c = Float.TYPE;
            } else if (argSpec.equals("short")) {
                c = Short.TYPE;
            } else if (argSpec.equals("char")) {
                c = Character.TYPE;
            } else if (argSpec.equals("byte")) {
                c = Byte.TYPE;
            } else if (argSpec.equals("double")) {
                c = Double.TYPE;
            } else if (argSpec.equals("long")) {
                c = Long.TYPE;
            } else {
                throw new Exception("[getDescriptor] " + argSpec
                                    + "is unknown");
            }
        } 
        return c;
    }

    private static Object getPrimitiveValue(Class argSpec, String arg) {

        Class  c           = null;
        Method m           = null;
        Object returnValue = null;

        try {
            if (argSpec == Integer.TYPE) {
                c = Integer.class;
            } else if (argSpec == Boolean.TYPE) {
                c = Boolean.class;
            } else if (argSpec == Float.TYPE) {
                c = Float.class;
            } else if (argSpec == Short.TYPE) {
                c = Short.class;
            } else if (argSpec == Character.TYPE) {
                c = Character.class;
            } else if (argSpec == Byte.TYPE) {
                c = Byte.class;
            } else if (argSpec == Double.TYPE) {
                c = Double.class;
            } else if (argSpec == Long.TYPE) {
                c = Long.class;
            }

            m = c.getDeclaredMethod(argSpec.getName() + "Value", null);

            // Instantiate 
            Constructor constructor = c.getConstructor(new Class[]{
                Class.forName("java.lang.String") });
            Object[]    cArgs       = new Object[]{ arg };
            Object      instance    = constructor.newInstance(cArgs);

            // Invoke method
            returnValue = m.invoke(instance, null);
        } catch (Exception e) {
            pn("Oops, exception [" + e.getClass().getName()
                               + "]: ");

            if (e instanceof InvocationTargetException) {
                e = (Exception) ((InvocationTargetException) e)
                    .getTargetException();
            }

            if (isVerbose) e.printStackTrace(System.out);
            else print(e.toString());
        } 
        return returnValue;
    }
    /**
    * validate command and get its input arguments
    */
    private static void checkCommand(String[] args) {

        if ((args.length == 0) || (args.length < 3)
                || ((args.length > 0)
                    && (!args[0].equals("-class") || args[1].startsWith("-")                        
                        || args[2].equals("-initargs")
                        || args[2].equals("-params")))
                || (args[0].equals("-help") && args.length > 1)) {
            isCommandOK = false;
        } else {
            if (args[0].equals("-help")) {
              showHelp = true;
            } else if (args[2].equals("-help")) {

                showClass = true;

                if (args.length > 3) {
                            checkVerbose(args, 3);
                            if (isVerbose)
                            checkOutputMode(args, 3);
                            else  checkOutputMode(args, 4);                            
                } 
            } else if (args[2].equals("-constructor")) {
                hasConstructor = true;

                if (args[3].equals("-initargs")) {
                    hasInitArgs = true;

                    for (int i = 4; i < args.length; i++) {
                        if ((i == 4) && (args[i].startsWith("-"))) {
                            isCommandOK = false;

                            break;
                        } else if ((i == 4) && (!args[i].startsWith("-"))) {
                            initargsStartIndex = i;
                        } else if (args[i].startsWith("-method")) {
                            initargsEndIndex = i - 1;

                            // Check method name
                            if (args.length == i + 1) {
                                isCommandOK = false;

                                break;
                            } else {
                                if (args[i + 1].startsWith("-")) {
                                    isCommandOK = false;

                                    break;
                                } else {
                                    mIndex = i + 1;

                                    if (args.length > i + 2) {
                                        if (!args[i + 2].equals("-params")) {
                                            checkVerbose(args, i+2);
                                            if (isVerbose)
                                              checkOutputMode(args, i+2);
                                            else {  
                                              if (args.length > i+3) {
                                                checkOutputMode(args, i+3);                            
                                            }
                                            }

                                            break;
                                        } else {
                                            hasParams        = true;
                                            paramsStartIndex = i + 3;
                                            if ((i+4)==args.length)
                                              isCommandOK = false;                                            
                                            paramsEndIndex = getParamEndIndex(args, i+3);
                                            if (paramsStartIndex
                                                == paramsEndIndex) 
                                                isCommandOK = false;                                        
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if ((initargsStartIndex == 0) || (initargsEndIndex == 0)
                            || (initargsStartIndex == initargsEndIndex)) {
                        isCommandOK = false;
                    }
                } else if (args[3].equals("-method")) {
                    if (args[4].startsWith("-")) {
                        isCommandOK = false;
                    } else {
                        mIndex = 4;
                        if (args[4].equals("main"))
                          callMain = true;

                        if (args.length > 5) {
                            if (args[5].equals("-params")) {
                                if (args.length == 6) {
                                    isCommandOK = false;
                                } else {
                                    hasParams        = true;
                                    paramsStartIndex = 6;
                                    if (7==args.length)
                                          isCommandOK = false;                                    
                                    paramsEndIndex = getParamEndIndex(args, 6);
                                    if (paramsStartIndex
                                                == paramsEndIndex)
                                            isCommandOK = false;                                       
                                    
                                }
                            } else {
                                    checkVerbose(args, 5);
                                    if (isVerbose)
                                      checkOutputMode(args, 5);
                                    else {  
                                    if (args.length > 6) {
                                      checkOutputMode(args, 6);                            
                                    }
                                    }
                            }
                        }
                    }
                }
            } else {
                if (!args[2].startsWith("-")) {
                    isCommandOK = false;
                } else {
                    if (args[2].equals("-method")) {
                        if (args[3].startsWith("-")) {
                            isCommandOK = false;
                        } else {
                            mIndex = 3;
                            if (args[3].equals("main"))
                            callMain = true;
                            if (args.length > 4) {
                                if (!args[4].equals("-params")) {
                                  
                                    checkVerbose(args, 4);
                                    if (isVerbose)
                                      checkOutputMode(args, 4);
                                    else {  
                                    if (args.length > 5) {
                                      checkOutputMode(args, 5);                            
                                    }
                                    }
                                   
                                } else {
                                    if (args.length == 5) {
                                        isCommandOK = false;
                                    } else {
                                        hasParams        = true;
                                        paramsStartIndex = 5;
                                        if (6==args.length)
                                          isCommandOK = false;
                                        paramsEndIndex = getParamEndIndex(args, 5);
                                        if (paramsStartIndex
                                                == paramsEndIndex) 
                                            isCommandOK = false;                                        

                                    }
                                }
                            } 

                            }
                        //}
                      
                    } else {
                        isCommandOK = false;
                    }
                }
            }
        }

        if (!isCommandOK) {
            usage();
            System.exit(-1);
        }        
    }

    private static void usage() {
            pn(
                "Usage:\n" + "======\n"
                + "Show this Help screen\n"
                + "============================\n" 
                + "> java -jar nspcommon.jar -help\n"
                + "\nCall the method 'main' from a class\n"                
                + "Show definition from a class\n"
                + "============================\n" 
                + "> java -jar nspcommon.jar -class <class name> -help  [-silent] [-file [-n] <my/path/to/file>]\n"
                + "\nCall the method 'main' from a class\n"
                + "============================\n"
                + "> java -jar nspcommon.jar -class <class name>  \n"
                + "  -method main [-params <param value 1> ... <param value n>] [-silent] [-file [-n] <my/path/to/file>]\n\n"
                + "Example :\n" + "=========\n"
                + "1) Call method 'main' from class with params \n"                
                + "    > java -jar nspcommon.jar -class my.package.myClass \n"
                + "    -method main -params Value1 Value2 Value3\n"                
                + "2) Call method 'main' from class without params \n"                
                + "    > java -jar nspcommon.jar -class my.package.myClass \n"
                + "    -method main \n"                
                + "\nCall other methods than 'main' from a class\n"
                + "============================\n"
                + "> java -jar nspcommon.jar -class <class name> [[-constructor] [-initargs <constructor init arg type 1> <constructor init arg value 1> ... <constructor init arg type n> <constructor init arg value n>]] \n"
                + "  -method <method to invoke> [-params <param type 1> <param value 1> ... <param type n> <param value n>]\n"
                +"   [-silent]  [-file [-n] <my/path/to/file>]\n\n"
                + "Example :\n" + "=========\n"
                + "1) Call method from class with constructor \n"
                + "  a. Constructor & method have params\n"
                + "    > java -jar nspcommon.jar -class my.package.myClass -constructor \n"
                + "    -initargs java.lang.String Value1 my.Object Value2 \n"
                + "    -method myMethod -params java.lang.String Message1 java.lang.Integer 3\n"
                + "  b. Constructor & method haven't any params\n"
                + "    > java -jar nspcommon.jar -class my.package.myClass -constructor \n"
                + "    -method myMethod\n"
                + "2) Call method from class without constructor\n"
                + "  a. Method has params\n"
                + "    > java -jar nspcommon.jar -class my.package.myClass "
                + "    -method myMethod -params java.lang.String \"Hello from myMethod\" java.lang.Integer 3\n"
                + "  b. Method hasn't any params\n"
                + "    > java -jar nspcommon.jar -class my.package.myClass "
                + "    -method myMethod\n"
                + "  c. Silent mode with output logged to file\n"
                + "    > java -jar nspcommon.jar -class my.package.myClass \n"
                + "    -method myMethod -silent -file \"D:\\my\\path\\myfile.log\" \n\n"
                + "     NOTA : If -file argument value must design an output file\n"+
                  "             Use -n option to create a new file. Without -n, you append lines to the existing file");                
    }

    private static int getParamEndIndex(String[] args, int start) {
      int end = start;
      
      for (int i = start; i < args.length; i++) {
        if (!args[i].startsWith("-"))
          end = i;
        else {
          checkVerbose(args, i);
          if (isVerbose)
          checkOutputMode(args, i);
          else  checkOutputMode(args, i+1);
          break;
        }
      }

      return end;
    }

    private static void checkVerbose(String[] args, int start) {
     if (args.length < start+1) isCommandOK = false;
     else {
      if (args[start].equals("-silent"))
        isVerbose = false;
     }
    }

    private static void checkOutputMode(String[] args, int start) {
     if (args.length < start+1) isCommandOK = false;
     else {
      if (args[start].equals("-file"))
        {
            if (args.length == start+1) isCommandOK = false;
            else {
                    logOutput = true;
                    if (args[start+1].equals("-n"))
                    {  isNew = true;
                        if (args.length == start+2) isCommandOK = false;
                        else {
                                fpath = args[start+2];
                                if (args.length > start+3) isCommandOK = false;
                              }
                    } else { 
                    fpath = args[start+1];

                    if (args.length > start+2) isCommandOK = false;
                    }
                 }
        } else isCommandOK = false;
      }
    }

    private void setSilent() {
      debugOn = false;
    }
    
    /** 
    * util methods
    */
    
    private void debug(String str) {

        if (debugOn) {
            print("[MainClass] " + str);
        }
    }

    private void dumpConstructors(Class c) {

    
            Constructor[] constructors = c.getDeclaredConstructors();

            if (constructors.length > 0) {
              print("\nConstructor(s) for "+c.getName()+"\n"+
                                 "===================");
              completeBar(c.getName());
            }
        
            for (int i=0; i < constructors.length; i++) {
              print(Modifier.toString(constructors[i].getModifiers())+" "+
              constructors[i].getName()+"(");

                //get Param Types from constructor
                Class[] params = constructors[i].getParameterTypes();
                for (int j = 0; j < params.length; j++) {
                  if (j==0)
                    print(params[j].getName());
                  else print(", "+params[j].getName());
                }

              print(") { ... }\n");
            }
    }

    private void dumpMethods(Class c) {
      Method[] methods = c.getDeclaredMethods();
      
      if (methods.length > 0) {
        print("\nMethod(s) for "+c.getName()+"\n"+
                           "==============");
        completeBar(c.getName());
        }
        
            for (int i=0; i < methods.length; i++) {
              print(Modifier.toString(methods[i].getModifiers())+" "+
              methods[i].getReturnType().getName()+" "+
              methods[i].getName()+"(");

                //get Param Types from constructor
                Class[] params = methods[i].getParameterTypes();
                for (int j = 0; j < params.length; j++) {
                  if (j==0)
                    print(params[j].getName());
                  else print(", "+params[j].getName());
                }
                print(") ");
                // dump Exceptions
                Class[] excepts = methods[i].getExceptionTypes();

                if (excepts.length > 0) {
                  print("throws ");

                  for (int k=0; k < excepts.length; k++) {
                    if (k==0)
                      print(excepts[k].getName());
                    else
                      print(", "+excepts[k].getName());                    
                  }
                }

              print("{ ... }\n");
            }                       
    }

    private void dumpMembers(Class c) {
      Class[] members = c.getDeclaredClasses();     
      if (members.length > 0) {
        print("\nClass member(s) for "+c.getName()+"\n"+
                             "====================");  
        completeBar(c.getName());
      }

      for (int i = 0; i < members.length; i++) {
            // Dump class name
            pn("member class name : \t"+members[i].getName());      

            dumpConstructors(members[i]);
            dumpMethods(members[i]);
      }
           
      
    }

    private void completeBar(String str) {
      for (int i = 0; i < str.length(); i++) {
        print("=");
      }
      print("\n");
    }

    private static void print(String str) {
      if (logOutput)
        w(str);
      else p(str);
    }
    
    private static void p(String str) {
      System.out.print(str);
    }
    private static void pn(String str) {
      System.out.println(str);
    }
  /**
  * File output util methods
  */

  private static void w(String str) {
    copy(new StringReader(str), fwriter);
  }
  
  private static  PrintWriter openFileWriter(String fname)
    throws RuntimeException
  {
    PrintWriter out = null;
  
   if (debugOn) 
      pn("\nopening filewriter for "+fname);
    out = new PrintWriter(openFileOutputStream(fname));

    try 
      {
      System.out.println(fname.substring(0, fname.lastIndexOf(File.separator))+"Redirect.out");
        stdout = new PrintStream (new FileOutputStream
              (fname, true));
      }
      catch (Exception e)
      {
      // Sigh.  Couldn't open the file.
        System.out.println ("\nRedirect:  Unable to open output file!");
        return (out);
      }

  // Create new output stream for the standard error output.
      
      try
      {
        stderr = new PrintStream (new FileOutputStream
              (fname, true));
      }
      catch (Exception e)
      {
      // Sigh.  Couldn't open the file.
          System.out.println ("\nRedirect:  Unable to open error file!");
          return (out);
      }
      System.setOut ( stdout );
      System.setErr ( stderr );
      
    return out;
  }
  
  private static FileOutputStream openFileOutputStream(String fname)
    throws RuntimeException
  {
    FileOutputStream file;
    try {
    if (isNew)
      file = new FileOutputStream(fname);
    else
      file = new FileOutputStream(fname, true);    
    } catch (IOException ee) {
      try {
        new File(new File(fname).getParent()).mkdirs();
        file = new FileOutputStream(fname);
      } catch (IOException ex) {
        throw new RuntimeException("Can't open output file " + fname);
      }
    }
    return file;
  }  

  private static void copy(Reader in, Writer out) throws RuntimeException {
    char[] buffer = new char[8*1024] ;
    
    try {
    for ( int n = in.read(buffer) ; n > 0 ; n = in.read(buffer) ) {
      out.write(buffer, 0, n);
    }
    out.flush();
    } catch (IOException ex) {
        throw new RuntimeException("Error while copying "+ex.getMessage());
    }    
  }     
}



 
/**
* Old release : replaced by L. Bois 22-AUG-2002

import com.acnielsen.nsp.common.util.FileUtils;

public class MainClass 
{

  public static final String DEFAULT_TASK = "mergeProperties";


  public static void main(String[] args)
  {
    if (args==null) return;

    String task = DEFAULT_TASK;

    if (args.length>1)
    {
      if ("mergeProperties".equalsIgnoreCase(args[0]))
      {
        if (args.length<3)
        {
          pn("usage: java -jar nspcommon.jar mergeProperties <inputDirectory> <outputFile>");
        }
        else
        {
          String inputDirectory = args[1];
          String outputFile = args[2];
          FileUtils.mergeDirectoryFiles(inputDirectory, outputFile);
        }
      }
    }
    
  }
*/
  
