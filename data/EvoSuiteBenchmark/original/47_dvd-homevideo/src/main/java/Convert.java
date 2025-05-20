import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.StringIndexOutOfBoundsException;

import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class is used to transcode the video captured
 * from the digital camcorder into mpeg4, the DVD compatible
 * format.  It explicitly calls the transcode command
 * after meticulously preparing the options that can be passed
 * to transcode.
 *
 * TODO - Break mplex out into its own class.
 *        Figure out how to calculate remaining time even if
 *        video was not captured from the camcorder during the
 *        current session.  
 *        Need to handle input, output and error streams
 *        more appropriatly.
 */
public class Convert implements Runnable
{
    /** 
     * Creates a new instance of Convert based on the video
     * format and aspect ratio specified.
     * @param   format      This specifies either PAL or NTSC
     * @param   aspectRatio This can be either 4:3 or 16:9
     * @param   DVD_GUI     This is the GUI object used to control the form
     */
    public Convert(String format, String aspectRatio, GUI DVD_GUI) 
    {
        if(format.equals("dvd-ntsc"))
            m_fps = 29.97;
        else
            m_fps = 25;
        
        m_GUI = DVD_GUI;
        m_Format = format;
        m_AspectRatio = aspectRatio;
        thread_track = 0;
    }
    
    /** 
     * Overloaded Constructor - accounts for quality being selected 
     * @param   quality     The quality of the video compression
     * @param   format      This specifies either PAL or NTSC
     * @param   aspectRatio This can be either 4:3 or 16:9 
     * @param   DVD_GUI     This is the GUI object used to control the form
     */
    public Convert(int quality, String format, String aspectRatio, GUI DVD_GUI)
    {
        if(format.equals("dvd-ntsc"))
            m_fps = 29.97;
        else
            m_fps = 25;
        
        m_GUI = DVD_GUI;
        m_Quality = quality;
        m_Format = format;
        m_AspectRatio = aspectRatio;
        thread_track = 0;
    }
    
    /**
     * Convert Member Variables 
     */
    private int m_Quality;
    private double m_fps;
    private String m_Format;
    private String m_AspectRatio;
    private String m_flags;
    private String m_bitrate = "8500";
    protected String[] video_files;
    private GUI m_GUI;
    private Thread m_Thread;
    private boolean m_Error;
    private int thread_track;

    private String m_BaseErr = "Transcoding Error - ";
    private String m_transcode = "transcode -i dv/inp -m test1.ac3 -o test1 -w bitr -x dv,dv" +
                               " -F flags -y mpeg2enc,raw -N 0x2000 -b 256 --encode_fields b" +
                               " -E 48000,16,2 -J resample" +
                               " --export_prof format --export_fps frames/s --export_asr aspectRatio" +
                               " -j 0,8,0,8 --print_status 30";   
    /* -i                 input video/audio files from the camcorder
       -m                 output .ac3 audio file
       -o                 output .m2v (mpeg2) video file
       -b                 audio bit rate - 256 should be sufficient
       -w                 maximum bit rate (audio + video) ~8500 kbps
       -x                 import module [video,audio]
       -F                 parameters passed to mpeg2enc
       -E                 audio output samplerate [Hz], bits per sample and channels
       -y                 export module [video,audio] - mpeg2enc and ffmpeg are similar in quality,
                                could use either
       --export_prof      either dvd_ntsc or dvd_pal
       --encode_fields    'b' means to encode bottom up - used for interlacing which is critical when
                                watching the video on television
       --export_asr       '2' gives an aspect ratio of 4:3 and '3' would give an aspect ratio of 16:9
       --export_fps       either 29.97 for NTSC or 25 for PAL
       --print_status     print the status of transcode every 30 frames ~1s for NTSC
       -j 0,8,0,8         clip off 8 pixels from the left and right sides of the screen to fit
                                the television screen correctly */
    
    private String mplex = "mplex -f 8 -V -o inp.vob inp.m2v inp.ac3";
    /* -f                 format - '8' equates to DVD
       -V                 needed to multiplex variable bit-rate video streams correctly
       -o                 output file created from the merged .m2v and .ac3 files */
    
    /**
     * Used to instantiate a new thread and to perform error checking.
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean init()
    {
        if(m_GUI.menuChkThread.isSelected())
        {
            m_Thread = new Thread(this);
            m_Thread.start();
            try 
            {
                m_Thread.join();
            }
            catch (InterruptedException ex) 
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);    
                m_GUI.MessageBox(m_BaseErr + "Could not join Convert.java thread\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;                    
            } 
        }
        else
            Transcode();        
        
        /* 
         * Check transcode.log and mplex.log for any sign of an error.
         * If either file contains an error then return an error.  Also
         * check m_Error to see if it was set while executing the 
         * Transcode method.
         */
	return (m_Error || 
                m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/transcode.log") ||
                m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/mplex.log")); 
    }
    
    /**
     * Implements the run() method of the Runnable interface.  Makes multi-threading
     * possible.
     */
    public void run()
    {
        Transcode();
    }       

    /**
     * Encodes dv files to mpeg using transcode, then uses mplex to combine
     * the .ac3 audio and .m2v video files into a DVD compatible .vob file
     */
    public void Transcode()
    {
        /* 
         * Get a list of all of the .dv files captured from the camcorder 
         * and stored in the dv directory
         */
	File ListDir = new File(m_GUI.strOutputDir + "/dv/");
       
        /* High quality, 80 min /DVD - slower encoding, classic MPEG2 */
        if(m_Quality == 0) 
	    m_flags="'8, -c -q 2 -4 1 -2 1 -H -K file=matrix.txt -R 2'"; 
        /* High quality, 80 min DVD */    
        else if(m_Quality == 1)
	    m_flags="'8, -c -q 4 -4 2 -2 2 -K file=matrix.txt -R 1'"; 
        /* OK quality, 120 min DVD -  slower encoding, classic MPEG2 */    
        else   
	    m_flags="'8, -c -q 6 -4 3 -2 3 -N 0.5 -E -10 -K tmpgenc -R 0'"; 
	        
        /* Perform some String manipulation */
	m_transcode = m_transcode.replaceAll("flags",m_flags);
	m_transcode = m_transcode.replaceAll("bitr",m_bitrate);
        m_transcode = m_transcode.replaceAll("format",m_Format);
        m_transcode = m_transcode.replaceAll("aspectRatio",m_AspectRatio);
        m_transcode = m_transcode.replaceAll("frames/s",String.valueOf(m_fps));       
        
        /* 
         * Write out matrix.txt which is a quantization table passed to 
         * mpeg2enc from transcode
         */
	matrix();
        
        /* Enable the GUI widgets for the conversion */
	m_GUI.lblConvert.setEnabled(true);
	m_GUI.prgConvert.setEnabled(true);
	m_GUI.lblConvertProg.setEnabled(true);

        /* 
         * Need to create doubles and ints to track the progess
         * of transcode.  The idea is that if we know the total
         * amount of capture time, and we also know the number of 
         * frames processed by transcode, then we can display a 
         * relatively accurate estimate of the amount of time left
         * to transcode.  Since this is the most time consuming part
         * of dvd-homevideo, I thought it was important.
         */
        double cummulative_time = 0;
        double current_time = 0;
        double[] average_fps = new double[5];
        average_fps[4] = -1;
        int hour, min, sec;
        int total_time = (((Integer)m_GUI.spnMinutes.getValue()).intValue() * 60) + ((Integer)m_GUI.spnSeconds.getValue()).intValue();
        
        StringTokenizer st, time;   
 	while(thread_track < ListDir.list().length)
	{
            video_files = ListDir.list();
            try 
            {
                /* Insert the appropriate parameters into transcode */
                if(thread_track == 0)
                {
                    m_transcode = m_transcode.replaceAll("inp",video_files[thread_track]);
                    m_transcode = m_transcode.replaceAll("test1",video_files[thread_track].substring(0,11));
                    mplex = mplex.replaceAll("inp",video_files[thread_track].substring(0,11));
                }
                else
                {
                    m_transcode = m_transcode.replaceAll(video_files[thread_track-1],video_files[thread_track]);
                    m_transcode = m_transcode.replaceAll(video_files[thread_track-1].substring(0,11), video_files[thread_track].substring(0,11));
                    mplex = mplex.replaceAll(video_files[thread_track-1].substring(0,11),video_files[thread_track].substring(0,11));
                }
            } 
            catch(StringIndexOutOfBoundsException ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);    
                m_GUI.MessageBox(m_BaseErr + "Are there non-standard (not .dv)" +
                                   "\nfiles in the dv directory?\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;                      
            }
            
            try
            {
                /* 
                 * transcode the .avi files from the camcorder into two separate 
                 * streams, one for audio and the other for video
                 */
                String[] transcode_cmd = {"/bin/sh","-c", m_transcode};
                Process p = Runtime.getRuntime().exec(transcode_cmd, null, new File(m_GUI.strOutputDir));
                                                                       
                /* Create an input reader, an error reader and an output writer */
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader err_in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                
                BufferedWriter out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/transcode.log"));
                BufferedWriter err_out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/transcode.err"));
                
                /* Write out the actual transcode command to the log file */
                out.write(m_transcode); out.newLine();
                
                String line;
                /* 
                 * Check for the case where the necessary dependencies are not
                 * installed, but the user tries to run dvd-homevideo anyway
                 */
                if(err_in.ready())
                {
                    line = err_in.readLine();
                    if(line.equals("/bin/sh: transcode: command not found"))
                    {
                        m_GUI.MessageBox("Could not locate transcode in your path." + 
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
                
                int k = 0;                                      
                while((line = in.readLine()) != null)
                {
                    st = new StringTokenizer(line);
                    if(st.hasMoreTokens())
                    {
                        if(st.nextToken().equals("encoding"))
                        {
                            st.nextToken(); st.nextToken(); 
                            average_fps[k%5] = Double.valueOf(st.nextToken()).doubleValue(); 
                            st.nextToken(); st.nextToken();
                                  
                            time = new StringTokenizer(st.nextToken(), ":,");
                            if(time.hasMoreTokens())
                            {
                                /* Calculate the estimated time left for transcode */
                                hour = Integer.valueOf(time.nextToken()).intValue();
                                min = Integer.valueOf(time.nextToken()).intValue();
                                sec = Integer.valueOf(time.nextToken()).intValue();
                                current_time = (hour * 3600) + (min * 60) + sec + cummulative_time;
                                m_GUI.prgConvert.setValue((int)((current_time / total_time) * 100));
                                m_GUI.lblConvertProg.setText(String.valueOf((int)((current_time / total_time) * 100)) + "%");
                                      
                                /*
                                 * Display to the user an approximation of the conversion time
                                 * left on the prgConvert progress bar using the tool tip (on mouse hover)
                                 */
                                double fps_sum = 0;
                                int time_remaining;
                                /* If the array has been filled... */
                                if(average_fps[4] != -1)
                                {
                                    for(int j = 0; j < 5; j++)
                                    {
                                        fps_sum += average_fps[j];
                                    }
                                    
                                    /* Average the fps readings from transcode */
                                    fps_sum /= 5;
                                    
                                    /* Added (int)m_fps to account for PAL (25 fps) and NTSC (29.97 fps) */
                                    time_remaining = (int)(((total_time - current_time) * (int)m_fps) / fps_sum);

                                    int captureTipHour = (time_remaining / 3600);
                                    int captureTipMinute = (time_remaining - 3600 * captureTipHour) / 60;

                                    if((captureTipHour == 0) && (captureTipMinute < 5))
                                        m_GUI.prgConvert.setToolTipText("Less than 5 minutes remaining");
                                    else if(captureTipMinute < 10)
                                        m_GUI.prgConvert.setToolTipText(captureTipHour + ":0" + 
                                                                          captureTipMinute + " Remaining");
                                    else
                                        m_GUI.prgConvert.setToolTipText(captureTipHour + ":" + captureTipMinute +
                                                                          " Remaining");
                                }
                                     
                                m_GUI.txtAreaOutput.append(line + "\n");
                                m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());
                                out.write(line); out.newLine();    
                            }
                            else
                            {
                                m_GUI.txtAreaOutput.append(line + "\n");
                                m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());
                                out.write(line); out.newLine();    
                            }
                         k++;    
                        }
                    }  
                }
                      
                while((line = err_in.readLine()) != null)
                {
                    m_GUI.txtAreaOutput.append(line + "\n");
                    m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());                            
                    out.write(line); out.newLine();                                                          
                }
                                                
                /* Close all open streams */
                in.close();
                out.close();
                err_in.close();
                err_out.close();
                
                /*
                 * p.waitFor() throws an InterruptedException
                 */
                
                /* This now runs as a thread...need to fix this */
                //p.waitFor();
                
                
                /* Timekeeping */
                cummulative_time = current_time;
                                
/*******************************************************
 * This is the separation between transcode and mplex. *
 * Need to break this out into a separate class.       * 
 *******************************************************/                

                /* 
                 * Combine the separate streams together into a .vob (DVD) file 
                 */                        
                String[] mplex_cmd = {"/bin/sh", "-c", mplex};
                p = Runtime.getRuntime().exec(mplex_cmd, null, new File(m_GUI.strOutputDir));

                /* Create an input reader, an error reader and an output writer */
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                err_in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                
                out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/mplex.log"));
                err_out = new BufferedWriter(new FileWriter(m_GUI.strOutputDir + "/log/mplex.err"));
                         
                /* 
                 * Check for the case where the necessary dependencies are not
                 * installed, but the user tries to run dvd-homevideo anyway
                 */
                if(err_in.ready())
                {
                    line = err_in.readLine();
                    if(line.equals("/bin/sh: mplex: command not found"))
                    {
                        m_GUI.MessageBox("Could not locate mplex in your path." + 
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
                
                while((line = err_in.readLine()) != null)
                {                    
                    m_GUI.txtAreaOutput.append(line + "\n");
                    m_GUI.txtAreaOutput.setCaretPosition(m_GUI.txtAreaOutput.getDocument().getLength());
                    out.write(line); out.newLine();    
                }
                /* Close all open streams */
                in.close();
                out.close();
                err_in.close();
                err_out.close();
                
                /*
                 * p.waitFor() throws an InterruptedException
                 */
                
                /* This now runs as a thread...need to fix this */
                //p.waitFor();
                
                /* Put in a slight delay to ensure transcode.log and mplex.log closes */
                Thread.sleep(2000);        
            }            
            catch(IOException ex)
            {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);    
                m_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;                      
            }
//            catch(InterruptedException ex)
//            {
//                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
//                m_GUI.MessageBox(m_BaseErr + "dvd-homevideo thread was interrupted\n" + ex.toString(), 0);
//                ex.printStackTrace();
//                m_Error = true;
//            }
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
            thread_track++;
	}
        /* Disable GUI widgets for the conversion */
        m_GUI.prgConvert.setValue(100);
        m_GUI.lblConvertProg.setText("100%");
                                      
	m_GUI.prgConvert.setEnabled(false);
	m_GUI.lblConvert.setEnabled(false);
	m_GUI.lblConvertProg.setEnabled(false);       
    }
    
    /**
     * Outputs a text file to be used by mpeg2enc for encoding
     * @param   m_GUI This is the GUI object used to control the form
     */
    public void matrix()
    {       
        try 
        {
            FileWriter fw = new FileWriter(m_GUI.strOutputDir + "/" + "matrix.txt");
            BufferedWriter wr = new BufferedWriter(fw);
            wr.write("# High resolution INTRA table\n");
            wr.write("8,16,18,20,24,25,26,30\n");
            wr.write("16,16,20,23,25,26,30,30\n");
            wr.write("18,20,22,24,26,28,29,31\n");
            wr.write("20,21,23,24,26,28,31,31\n");
            wr.write("21,23,24,25,28,30,30,33\n");
            wr.write("23,24,25,28,30,30,33,36\n");
            wr.write("24,25,26,29,29,31,34,38\n");
            wr.write("25,26,28,29,31,34,38,42\n");
            wr.write("# TMPEGEnc NON-INTRA table\n");
            wr.write("16,17,18,19,20,21,22,23\n");
            wr.write("17,18,19,20,21,22,23,24\n");
            wr.write("18,19,20,21,22,23,24,25\n");
            wr.write("19,20,21,22,23,24,26,27\n");
            wr.write("20,21,22,23,25,26,27,28\n");
            wr.write("21,22,23,24,26,27,28,30\n");
            wr.write("22,23,24,26,27,28,30,31\n");
            wr.write("23,24,25,27,28,30,31,33\n");

            wr.close();
            fw.close();
        } 
        catch(IOException ex)
        {
            SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);    
            m_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
            ex.printStackTrace();
            System.exit(1);                      
        }
    }
}
