import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * @author Shane Santner
 * The Capture class utilizes dvgrab to capture audio and video
 * data from a dv camcorder.  It captures for the amount of time
 * specified by the user.
 *
 * TODO - Is there a way to get the camcorder time from dvgrab?
 *        This would be a more accurate way of determining when
 *        to stop the camcorder.  Also need to handle input, output
 *        and error streams more appropriatly.
 */
public class Capture implements Runnable
{
    /** Creates a default instance of Capture */
    public Capture()
    {}
    
    /** 
     * Creates a new instance of Capture 
     * @param   min         The amount of time in minutes to capture audio/video
     * @param   sec         The amount of time in seconds to capture audio/video
     * @param   DVD_GUI     This is the GUI object used to control the form
     */    
    public Capture(int min, int sec, GUI DVD_GUI)
    {
        m_Minutes = min;
        m_Seconds = sec;     
        m_GUI = DVD_GUI;
    }

    /** Capture Member Variables */
    private int m_Minutes;
    private int m_Seconds;
    private GUI m_GUI;
    private Thread m_Thread;
    private boolean m_Error;
    private String m_BaseErr = "Capture Error - ";
    private String m_dvgrab = "dvgrab --autosplit --size 0 --format raw --opendml" +
                            " --buffers 200 dv/dv_file-";   
    
    /**
     * Used to instantiate a new thread and to perform error checking.
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean init()
    {
        if(m_GUI.menuChkThread.isSelected())
        {
            /* Create and start the new thread */
            m_Thread = new Thread(this);

            /* Increase priority so we don't drop frames */
            m_Thread.setPriority(10);
            m_Thread.start();
            
            /* Need to fix this */
            return false;
        }
        else
        {
            DV_Capture();
            return (m_Error | m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/dvgrab.log"));	
        }        
        
        /* 
         * If an error occurred while executing DV_Capture then m_Error will be set
         * to true.  Also check the log file for any evidence of an error.
         */        
    }
    
    /**
     * Implements the run() method of the Runnable interface.  Makes multi-threading
     * possible.
     */
    public void run()
    {        
        DV_Capture();
    }   
       
    /**
     * Captures audio and video from a dv camcorder by calling dvgrab
     */
    public void DV_Capture() 
    {
        /* local variables */
	long start, current, end;
	int diff, total;
        
        /* Create output directory */
	File CreateOutDir = new File(m_GUI.strOutputDir);
		
        /* 
         * Check and see if the directory already exists.
         * Give the user an option to delete the directory
         * and procede.
         */
        if(CreateOutDir.exists() && CreateOutDir.isDirectory())
        {
            int response = m_GUI.MessageBox("The Output Directory " + m_GUI.strOutputDir + " already exists!" +
                               "\nWould you like to delete it?");
            if(response == JOptionPane.YES_OPTION)
            {
                try 
                {
                    /* Put all of the necessary parameters into a String array */
                    String rm = "rm -fr " + m_GUI.strOutputDir;
                    String[] rm_cmd = {"/bin/sh","-c",rm};

                    /* Execute the rm utility to recursively delete the directory */
                    Process remove_dir = Runtime.getRuntime().exec(rm_cmd, null);
                    remove_dir.waitFor();
                }
                catch (IOException ex) 
                {
                    SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                    m_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
                    ex.printStackTrace();
                }
                catch (InterruptedException ex) 
                {
                    SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                    m_GUI.MessageBox(m_BaseErr + "dvd-homevideo thread was interrupted\n" + ex.toString(), 0);
                    ex.printStackTrace();
                }
            }
            else
            {
                m_GUI.MessageBox("Please choose a different name for your" +
                                   "\ntitle or move the directory to another location.", JOptionPane.WARNING_MESSAGE);
                m_GUI.blnBegin = false;  
                m_Error = true;
            }            
        }
        /* 
         * Check and see if a file with that name already exists.
         * Give the user an option to delete the directory
         * and procede.
         */
	else if(CreateOutDir.exists() && !CreateOutDir.isDirectory())
        {
            int response = m_GUI.MessageBox("There exists a file with the Output directory name" +
                                              "\nat this location!  Would you like to delete it?");
            if(response == JOptionPane.YES_OPTION)
            {
                try 
                {
                    /* Put all of the necessary parameters into a String array */
                    String rm = "rm -fr " + m_GUI.strOutputDir;
                    String[] rm_cmd = {"/bin/sh","-c",rm};

                    /* Execute the rm utility to recursively delete the directory */
                    Process remove_dir = Runtime.getRuntime().exec(rm_cmd, null);
                    remove_dir.waitFor();
                }
                catch (IOException ex) 
                {
                    SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                    m_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
                    ex.printStackTrace();
                }
                catch (InterruptedException ex) 
                {
                    SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                    m_GUI.MessageBox(m_BaseErr + "dvd-homevideo thread was interrupted\n" + ex.toString(), 0);
                    ex.printStackTrace();
                }
            }
            else
            {
                m_GUI.MessageBox("Please choose a different name for your" +
                                   "\ntitle or move the file to another location.", JOptionPane.WARNING_MESSAGE);
                m_GUI.blnBegin = false;    
            }            
        }
        
        /* If blnBegin is still true, then continue */
	if(m_GUI.blnBegin == true)
	{
            CreateOutDir.mkdir();
            CreateOutDir = new File(m_GUI.strOutputDir + "/DVD");
            CreateOutDir.mkdir();
            CreateOutDir = new File(m_GUI.strOutputDir + "/log");
            CreateOutDir.mkdir();
            CreateOutDir = new File(m_GUI.strOutputDir + "/dv");
            CreateOutDir.mkdir();
			
            try
            {
                /* Put all of the necessary parameters into a String array */
                String[] dvgrab_cmd = {"/bin/sh","-c",m_dvgrab};
                
                /* Execute the dvgrab program to capture video from the camcorder */
                Process p = Runtime.getRuntime().exec(dvgrab_cmd, null, new File(m_GUI.strOutputDir));             
                
                /* Create an input reader, an error reader and an output writer */
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader err_in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                
                BufferedWriter out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/dvgrab.log"));
                BufferedWriter err_out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/dvgrab.err"));
              
                String line;
                /* 
                 * Check for the case where the necessary dependencies are not
                 * installed, but the user tries to run dvd-homevideo anyway
                 */
                if(err_in.ready())
                {
                    line = err_in.readLine();
                    if(line.equals("/bin/sh: dvgrab: command not found"))
                    {
                        m_GUI.MessageBox("Could not locate dvgrab in your path." + 
                                           "\nPlease install all necessary dependencies" +
                                           "\nand rerun dvd-homevideo.", 0);
                        /* Close all open streams */
                        in.close();
                        out.close();
                        err_in.close();
                        err_out.close();

                        /* Indicate that there was an error */
                        m_Error = true;
                    }
                    else
                    {
                        m_GUI.txtAreaOutput.append(line + "\n");
                        out.write(line);  out.newLine();
                    }
                }        
                
                /* Set the GUI feedback widgets to enabled */
                m_GUI.lblCapture.setEnabled(true);
		m_GUI.prgCapture.setEnabled(true);
		m_GUI.lblCaptureProg.setEnabled(true);

                /* Do some time calculations - this will be used for the progress bar*/
		start = System.currentTimeMillis();
                total = m_Minutes * 60 * 1000 + m_Seconds * 1000 + 5000;
		end = start + total;
		current = System.currentTimeMillis();
		diff = (int)(current - start);
		      
		int track = 0;
                //String line;
                
                /* Stay in the while loop until we have reached the user specified amount of time */
		while((diff / total) < 1)
                {	
                    /* Do some GUI stuff */
                    m_GUI.prgCapture.setValue((int)(((double)(diff)/total)*100));
		    m_GUI.lblCaptureProg.setText(Integer.toString(((int)(((double)(diff)/total)*100))) + "%");
                    current = System.currentTimeMillis();
		    diff = (int)(current - start);
                    line = in.readLine();  /* I don't know why but this is critical!!!!! */
                    
                    /* Check for anything written to the error stream */
                    if(err_in.ready())
                    {
                        line = err_in.readLine();
                        /* Look for this specific error code - indicates that the cable is not connected/loose */
                        
                        if(line.equals("Error: no camera exists"))
                        {                            
                            /* Give feedback to the user */
                            m_GUI.MessageBox("It appears that your camcorder is not " +
                                               "connected to your computer!", 0);    
                                                        
                            /* Put all of the necessary parameters into a String array */
                            String rm = "rm -fr " + m_GUI.strOutputDir;
                            String[] rm_cmd = {"/bin/sh","-c",rm};
                
                            /* Execute the rm utility to recursively delete the directory */
                            Process remove_dir = Runtime.getRuntime().exec(rm_cmd, null);
                            remove_dir.waitFor();
                                            
                            /* Close all open streams */
                            in.close();
                            out.close();
                            err_in.close();
                            err_out.close();
                            
                            /* Indicate that there was an error */
                            m_Error = true;
                        }
                                    
                        /* Output information to the GUI text box */
                        m_GUI.txtAreaOutput.append(line + "\n");
			m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());
                        out.write(line); out.newLine();    
                    }
                }
                /* 
                 * close() throws an IOException
                 */
                in.close();
                out.close();
                err_in.close();
                err_out.close();
                
                /* kill dvgrab process */
                /* Since this now runs as a thread do I need to modify this? */
		p.destroy();
                         
                /* Final GUI stuff */
                m_GUI.prgCapture.setValue(100);
                m_GUI.prgCapture.setValue(100);
                m_GUI.lblCaptureProg.setText("100%");
                m_GUI.prgCapture.setEnabled(false);
                m_GUI.lblCapture.setEnabled(false);
                m_GUI.lblCaptureProg.setEnabled(false);        

                /* Put in a slight delay to ensure dvgrab.log closes */
                Thread.sleep(2000);        
                
            }
            catch(IOException ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }
            catch(NullPointerException ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(m_BaseErr + "Error executing Runtime.getRuntime().exec()\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }
            catch(IllegalArgumentException ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(m_BaseErr + "Illegal argument sent to Runtime.getRuntime().exec()\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }
            catch(Exception ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(m_BaseErr + "Unknown Error occurred\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;            
            }
	}
    }
}
