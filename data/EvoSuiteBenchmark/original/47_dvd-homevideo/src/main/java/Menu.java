import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FilenameFilter;

import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class creates a background menu for a DVD.  The user
 * can specify a background picture and audio to use for the
 * menu, or a default background can also be used.  The user
 * also has the option of specifying unique titles for each
 * chapter of the DVD.  If nothing is specified then the title
 * of the DVD will be used followed by an underscore and increasing
 * integer for each video clip.
 *
 * TODO - Can I use streams on dvd-menu?  Really need to standardize
 *        how I use streams across the board for all classes.
 */
public class Menu implements Runnable
{
    /**
     * Creates a default instance of Menu 
     */
    public Menu()
    {}
    
    /**
     * Creates a new instance of Menu with the title, text file path and PAL/NTSC format
     * passed as parameters. 
     * @param   title           The title of the DVD
     * @param   TextFilePath    The path to the title file
     * @param   isPAL           Is this a PAL or NTSC DVD
     */
    public Menu(String title, String TextFilePath, boolean isPAL, GUI DVD_GUI)
    {
        strTitle = title;
        strTextFilePath = TextFilePath;
        pal_menu = isPAL;
        m_GUI = DVD_GUI;
    }
    
    /** 
     * Creates a new instance of Menu with the title, text file path, PAL/NTSC format,
     * menu picture path and audio path passed as parameters. 
     * @param   title           The title of the DVD
     * @param   picPath         The path to the background picture of the DVD Menu
     * @param   audioPath       The path to the background audio of the DVD Menu
     * @param   TextFilePath    The path to the title file
     * @param   isPAL           Is this a PAL or NTSC DVD
     */
    public Menu(String title, String picPath, String audioPath, String TextFilePath, boolean isPAL, GUI DVD_GUI)
    {
        strTitle = title;
        strPicPath = picPath;
        strAudioPath = audioPath;
        strTextFilePath = TextFilePath;
        pal_menu = isPAL;        
        m_GUI = DVD_GUI;
    }
    
    /**
     * Menu Member Variables 
     */
    private String strTitle;
    private String strPicPath;
    private String strAudioPath;
    private String strTextFilePath;
    private String dvd_menu = "dvd-menu";
    private String[] titles = new String[50];
    private boolean pal_menu;
    private String baseErr = "Menu Error - ";
    private String[] video_files;
    private GUI m_GUI;
    private Thread m_Thread;
    private boolean m_Error;
    
    /**
     * Used to instantiate a new thread and to perform error checking.
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean init()
    {
        /* Create and start the new thread */
        m_Thread = new Thread(this);
        m_Thread.start();
        try 
        {
            m_Thread.join();
        }
        catch (InterruptedException ex) 
        {
            ex.printStackTrace();
        }
        
        /* 
         * If an error occurred while executing DV_Capture then m_Error will be set
         * to true.  Also check the log file for any evidence of an error.
         */
        return (m_Error || m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/dvd-menu.log"));	
    }
    
    /**
     * Implements the run() method of the Runnable interface.  Makes multi-threading
     * possible.
     */
    public void run()
    {        
        DVDMainMenu();
    }   
    
    /**
     * Creates DVD menu using optional picture and music by calling dvd-menu
     * @param   m_GUI This is the GUI object used to control the form
     * @return  A boolean to determine if an error occurred in the function  
     */
    public void DVDMainMenu()
    {
        /* First we need to extract all of the .vob files that were
         * created using transcode and store these file names in 
         * the video_files array. */
        File ListDir = new File(m_GUI.strOutputDir);
        FilenameFilter filter = new FilenameFilter() 
        {
            boolean ret_val = true;
            public boolean accept(File dir, String name) 
            {
                if(name.endsWith("vob"))
                {
                    if(!name.equals("menu.vob"))
                        ret_val = true;
                    else
                        ret_val = false;
                }
                else
                    ret_val = false;
                return ret_val;
            }
        };
        
        /* This is where the .vob files actually get stored in the array */
        video_files = ListDir.list(filter);
        
        /* Enable the Menu progress portion of the GUI */
	m_GUI.lblAuthor.setEnabled(true);
	m_GUI.lblAuthorProg.setEnabled(true);
	m_GUI.prgAuthor.setEnabled(true);
	m_GUI.prgAuthor.setIndeterminate(true);

        /* Add to the dvd-menu string based on user supplied parameters */
	String menu_options;
	if(strPicPath == null && strAudioPath == null)
	    menu_options = " -c -D -n '" + strTitle + "' -o " + m_GUI.strOutputDir;
	else if(strPicPath.equals("") && strAudioPath != null)
    	    menu_options = " -c -D -n '" + strTitle + "' -o " + m_GUI.strOutputDir + " -a " + strAudioPath;
	else if(strPicPath != null && strAudioPath.equals(""))
	    menu_options = " -c -D -n '" + strTitle + "' -o " + m_GUI.strOutputDir + " -b " + strPicPath;
	else
	    menu_options = " -c -D -n '" + strTitle + "' -o " + m_GUI.strOutputDir + " -a " + strAudioPath + " -b " + strPicPath;

        /* First scenario - the user DID NOT supply a title file */
        int i = 0;
        if(strTextFilePath.equals("") || strTextFilePath == null)
        {
            while(i < video_files.length)
	    {
                /* Add default naming to the dvd-menu since nothing else was specified */
		menu_options += " -f " + m_GUI.strOutputDir + "/" + video_files[i].substring(0,11) + ".vob -t " + strTitle + "_" + (i + 1);
		i++;
	    }    
        }
        /* Otherwise they DID supply a text file, so we need to read from it */
        else
        {
            try 
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(m_GUI.txtTextFile.getText())));
                String line;i = 0;
                
                /* 
                 * Read in the user supplied title file and store
                 * each line in an array.
                 */
                while((line = in.readLine()) != null)
                {
                    titles[i] = line;
                    i++;                    
                }
                           
                /* Determine the number of lines in the text file */
                int num_blanks = 0;
                for(i = 0; i < titles.length; i++)
                {
                    if(titles[i].equals("BLANK"))
                        num_blanks++;
                    if(titles[i].equals("DONE"))
                        break;
                }
                
                /* Error check the user supplied text file */
                int num_lines = i;
                if((num_lines - num_blanks) > 10)
                {
                    m_GUI.MessageBox("Your text file can only have a maximum" +
                            "of 10 titles!  Please edit your text file and rerun" +
                            "dvd-homevideo.",1);
                    
                    /* 
                     * Can't recover from this user error, so make them start over...
                     * or at least run dvd-menu and dvdauthor again
                     */
                    m_Error = true;;
                }                    
                else if(num_lines == video_files.length)
                {
                    i = 0;
                    while(i < video_files.length)
                    {
                        /* Look for the keywords BLANK and DONE */
                        if(!titles[i].equals("BLANK")  && !titles[i].equals("DONE"))
                        {
                            menu_options +=  " -t " + titles[i] + " -f " + m_GUI.strOutputDir + "/" + video_files[i].substring(0,11) + ".vob";
                            i++;
                        } 
                        /* If we find DONE, then quit parsing the text file */
                        else if(titles[i].equals("DONE"))
                            break;
                        else
                        {
                            menu_options += " -f " + m_GUI.strOutputDir + "/" + video_files[i].substring(0,11) + ".vob";
                            i++;
                        }                        
                    }   
                }
                else
                {
                    m_GUI.MessageBox("It appears that your text file is not\n" +
                                       "formatted correctly.  You currently have\n" +
                                       (video_files.length + 1) + " vob files, \n" +
                                       "however your text file is showing\n"
                                       + num_lines + " lines.",1);
                    
                    /* 
                     * Can't recover from this user error, so make them start over...
                     * or at least run dvd-menu and dvdauthor again
                     */
                    m_Error = true;;
                }                 
            } 
            catch(FileNotFoundException ex) 
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox("Can not find " + m_GUI.txtTextFile + "\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }
            catch(IOException ex) 
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(baseErr + "IO Error\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }       
        }
        
        if(pal_menu == true)
            menu_options += " -p"; 
        
        /* 
         * I don't like this but for some reason dvd-menu requires
         * that I redirect the error stream.  It doesn't care where,
         * it just has to be redirected.  Possibly something to do 
         * with lame?
         */
        dvd_menu += (menu_options + " 2>&1");// log/dvd-menu.err <2");
                
        m_GUI.txtAreaOutput.append(dvd_menu + "\n");
        try 
        {
	    String[] dvd_menu_cmd = {"/bin/sh","-c",dvd_menu};
            Process p = Runtime.getRuntime().exec(dvd_menu_cmd, null, new File(m_GUI.strOutputDir));
            
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader err_in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            String line;
            StringTokenizer st;
            /* 
             * Write anything from the input stream for dvd-menu
             * to the txtAreaOutput that the user sees
             */
            while((line = in.readLine()) != null)
            {                
                /* 
                 * Check for the case where the necessary dependencies are not
                 * installed, but the user tries to run dvd-homevideo anyway
                 */
                if(line.equals("/bin/sh: dvd-menu: command not found"))
                {
                    m_GUI.MessageBox("Could not locate dvd-menu in your path." + 
                                       "\nPlease install all necessary dependencies" +
                                       "\nand rerun dvd-homevideo.", 0);
                    /* Close all open streams */
                    in.close();
                    //out.close();
                    err_in.close();
                    //err_out.close();

                    /* Indicate that there was an error */
                    m_Error = true;
                }
                st = new StringTokenizer(line);
                if(st.hasMoreTokens())
                {
                    /* 
                     * Since dvd-menu makes us redirect the error stream to stdin,
                     * we don't want to waste a ton of time in I/O, so ignore lines
                     * that start with 'Frame#'
                     */
                    if(!st.nextToken().equals("Frame#"))
                    {
                        m_GUI.txtAreaOutput.append(line + "\n");
                        m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());            
                    }
                }
                
            }
            /*
             * p.waitFor() throws an InterruptedException
             */
            p.waitFor();        
        
            /* move dvd-menu.log into the log directory */
            p = Runtime.getRuntime().exec("mv dvd-menu.log log/", null, new File(m_GUI.strOutputDir));
            
            /* Delete unnecessary output directory created by dvd-menu */
            File CreateOutDir = new File(m_GUI.strOutputDir + "/dvd_fs");
            if(CreateOutDir.exists() && CreateOutDir.isDirectory())
                CreateOutDir.delete(); 
            
            /* Put in a slight delay to ensure dvd-menu.log closes */
            Thread.sleep(2000);                        
        } 
        catch(IOException ex) 
        {
            SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
            m_GUI.MessageBox(baseErr + "IO Error\n" + ex.toString(), 0);
            ex.printStackTrace();
            m_Error = true;
        }
        catch(InterruptedException ex) 
        {
            SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
            m_GUI.MessageBox(baseErr + "dvd-homevideo thread was interrupted\n" + ex.toString(), 0);
            ex.printStackTrace();
            m_Error = true;
        }            	
    }    
    
    /**
     * Creates the xml file to pass to dvdauthor
     * @param   m_GUI This is the GUI object used to control the form
     */
    public boolean createXML()
    {
	File xmlFile = new File(m_GUI.strOutputDir + "/vmgm.xml");
        File ListDir = new File(m_GUI.strOutputDir);
                
        /* 
         * Need to find all vob files, but don't want to include menu.vob
         * which was created by dvd-menu.
         */
        FilenameFilter filter = new FilenameFilter() 
        {
            boolean ret_val = true;
            public boolean accept(File dir, String name) 
            {
                if(name.endsWith("vob"))
                {
                    if(!name.equals("menu.vob"))
                        ret_val = true;
                    else
                        ret_val = false;
                }
                else
                    ret_val = false;               
                return ret_val;
            }
        };
        video_files = ListDir.list(filter);
        
        /* Write the xml file.  It's not pretty, but it works. */
	int i;
        try 
        {	
            BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
            writer.write("<dvdauthor dest=\"" + m_GUI.strOutputDir + "/DVD/\" jumppad=\"0\">");writer.newLine();
            writer.write("\t<vmgm>");writer.newLine(); 
            writer.write("\t\t<menus>");writer.newLine();
            writer.write("\t\t\t<pgc entry=\"title\" >");writer.newLine();
            writer.write("\t\t\t\t<vob file=\"" + m_GUI.strOutputDir + "/menu.vob\" pause=\"inf\" />");writer.newLine();
            if(titles[0] == null)
            {
                for(i = 1; i <= video_files.length; i++)
                {
                    writer.write("\t\t\t\t<button>jump title " + i + ";</button>");
                    writer.newLine();
                }    
            }
            else
            {
                for(i = 1; i < titles.length; i++)
                {
                    if(!titles[i-1].equals("BLANK")  && !titles[i-1].equals("DONE"))
                    {
                        writer.write("\t\t\t\t<button>jump title " + i + ";</button>");
                        writer.newLine();
                    }
                    else if(titles[i-1].equals("DONE"))
                        break;
                }
            }
            writer.write("\t\t\t\t<post> jump vmgm menu 1; </post>");writer.newLine();
            writer.write("\t\t\t</pgc>");writer.newLine();
            writer.write("\t\t</menus>");writer.newLine();
            writer.write("\t</vmgm>");writer.newLine();
            writer.write("\t<titleset>");writer.newLine();
            writer.write("\t<titles>");writer.newLine();
            for(i = 1; i < video_files.length; i++)
            {
            	writer.write("\t\t<pgc>"); writer.newLine();			
            	writer.write("\t\t\t<vob file=\"" + video_files[i-1] + "\" />"); writer.newLine();
                writer.write("\t\t\t<post>jump title " + (i + 1) + ";</post>"); writer.newLine();
		writer.write("\t\t</pgc>"); writer.newLine();
	    }
            writer.write("\t\t<pgc>");writer.newLine();
            writer.write("\t\t\t<vob file=\"" + video_files[i-1] + "\" />");writer.newLine();
            writer.write("\t\t\t<post>call vmgm menu 1;</post>");writer.newLine();
            writer.write("\t\t</pgc>");writer.newLine();
            writer.write("\t</titles>");writer.newLine();
            writer.write("\t</titleset>");writer.newLine();
            writer.write("</dvdauthor>");writer.newLine();
            writer.close();
        }
        catch(IOException ex) 
        {
            SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
            m_GUI.MessageBox(baseErr + "IO Error\n" + ex.toString(), 0);
            ex.printStackTrace();    
            return true;
        }        
        /* Shouldn't expect to see any errors from this method call. */
        return false;
    }
}
