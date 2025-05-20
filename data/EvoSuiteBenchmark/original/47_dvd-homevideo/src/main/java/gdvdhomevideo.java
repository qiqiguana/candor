import java.awt.Color;

/**
 * @author Shane Santner
 * This is the main class for dvd-homevideo.  This class
 * controls the creation and instantiation of objects,
 * and generally controls the flow of the entire program.
 */
public class gdvdhomevideo 
{    
    /** Creates a new instance of gdvdhomevideo */
    public gdvdhomevideo() 
    {}
    
    /**
     * @param   args    The command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        /* Define and Create Objects */
        GUI DVD_GUI = new GUI();
        Capture DVD_Capture;
        Convert DVD_Convert;
        Menu DVD_Menu;
        Author DVD_Author;
        Burn DVD_Burn;
        
        /* Create Color objects for txtStatus */
        Color green = new Color(0,255,0);
        Color red = new Color(255,0,0);
        Color grey = new Color(153,153,153);
        
        /* Display the GUI */
        DVD_GUI.setVisible(true);
        
        /*
         * Wait for user to press Start button, then check the user interface for 
         * errors.  If no errors then begin instantiating objects and start
         * executing.
         */
        begin:
        while(true)
        {
            /*
             * Reset values to zero percent and disable progress
             * information every single time in the code that we 
             * continue to the label begin.
             */
            DVD_GUI.prgCapture.setValue(0); DVD_GUI.prgCapture.setEnabled(false);
            DVD_GUI.prgConvert.setValue(0); DVD_GUI.prgConvert.setEnabled(false);
            DVD_GUI.prgAuthor.setValue(0);  DVD_GUI.prgAuthor.setEnabled(false);

            DVD_GUI.lblCaptureProg.setText("0%"); DVD_GUI.lblCaptureProg.setEnabled(false);
            DVD_GUI.lblConvertProg.setText("0%"); DVD_GUI.lblConvertProg.setEnabled(false);
            DVD_GUI.lblAuthorProg.setText("0%");  DVD_GUI.lblAuthorProg.setEnabled(false);
                                          
            DVD_GUI.prgAuthor.setIndeterminate(false);
                                                  
            DVD_GUI.lblCapture.setEnabled(false);
            DVD_GUI.lblConvert.setEnabled(false);
            DVD_GUI.lblAuthor.setEnabled(false);           
            
            /* Wait for the user to press the Start button */
            while(DVD_GUI.blnBegin == false)
            {
                Thread.sleep(20);
            }
            
                            
            DVD_GUI.UpdateStatus(grey, "Status");

            /* Instantiate Capture Object */
            DVD_Capture = new Capture(((Integer)DVD_GUI.spnMinutes.getValue()).intValue(), ((Integer)DVD_GUI.spnSeconds.getValue()).intValue(), DVD_GUI);

            /* Instantiate Convert Object */
            String aspectRatio, format;                
            boolean usePAL = false;
            if(DVD_GUI.rdNTSC.isSelected())
                format = "dvd-ntsc";

            else 
                /* This is necessary for transcode to process pal data correctly */
                format = "dvd-pal --dv_yuy2_mode";                                

            /* Check which aspect ratio is selected */
            if(DVD_GUI.rd4_3.isSelected())
                aspectRatio = "2";
            else
                aspectRatio = "3";

           /* Check the quality that has been chosen */
            if(DVD_GUI.rdSuper.isSelected())
                DVD_Convert = new Convert(0, format, aspectRatio, DVD_GUI);
            else if(DVD_GUI.rdGood.isSelected())
                DVD_Convert = new Convert(1, format, aspectRatio, DVD_GUI);
            else 
                DVD_Convert = new Convert(2, format, aspectRatio, DVD_GUI);

            /* Instantiate Menu Object */
            if(DVD_GUI.rdPAL.isSelected())
                usePAL = true;
            if(DVD_GUI.chkMenu.isSelected())
                DVD_Menu = new Menu(DVD_GUI.txtTitle.getText(), DVD_GUI.txtPicture.getText(), 
                                    DVD_GUI.txtAudio.getText(), DVD_GUI.txtTextFile.getText(),
                                    usePAL, DVD_GUI);
            else
                DVD_Menu = new Menu(DVD_GUI.txtTitle.getText(), DVD_GUI.txtTextFile.getText(),
                                    usePAL, DVD_GUI);

            /* Instantiate Author Object */
            DVD_Author = new Author();

            /* Instantiate Burn Object */
            if(DVD_GUI.chkBurn.isSelected())
                DVD_Burn = new Burn(true);
            else
                DVD_Burn = new Burn(false);

            /* Let's Begin! */
            /* Have we already captured from the camcorder? */
            if(!DVD_GUI.menuRd_IgnoreCap.isSelected() &&
               !DVD_GUI.menuRd_IgnoreCapConv.isSelected() &&
               !DVD_GUI.menuRd_IgnoreCapConvMenu.isSelected())
            {                    
                if(DVD_Capture.init())
                {
                    DVD_GUI.UpdateStatus(red, "FAIL");
                    continue begin;
                }   
          
                /* 
                * If we are indeed capturing video from the camcorder, then since we
                * are also multi-threading we need to add a slight delay before
                * transcode starts converting the video.
                */
               Thread.sleep(6500);
            }
            /* Have we already run transcode/mplex? */
            if(!DVD_GUI.menuRd_IgnoreCapConv.isSelected() &&
               !DVD_GUI.menuRd_IgnoreCapConvMenu.isSelected())
            {                    
                if(DVD_Convert.init())
                {                       
                    DVD_GUI.UpdateStatus(red, "FAIL");
                    continue begin;
                }    
            }
            /* Have we already run dvd-menu? */
            if(!DVD_GUI.menuRd_IgnoreCapConvMenu.isSelected())
            {
                if(DVD_Menu.init())
                {
                    DVD_GUI.UpdateStatus(red, "FAIL");
                    continue begin;
                }   
                if(DVD_Menu.createXML())
                {
                    DVD_GUI.UpdateStatus(red, "FAIL");
                    continue begin;
                }   
            } 
            /* Run dvdauthor and check for errors */
            if(DVD_Author.CreateDVDFileStructure(DVD_GUI))
            {
                DVD_GUI.UpdateStatus(red, "FAIL");
                continue begin;
            }   
            /* Run growisofs and check for errors */
            if(DVD_Burn.BurnToDVD(DVD_GUI))
            {
                DVD_GUI.UpdateStatus(red, "FAIL");
                continue begin;
            }   
            /* 
             * If we have made it this far, then lets allow the 
             * user to test the DVD by clicking on the Play button.
             */
            DVD_GUI.btnPlay.setEnabled(true);
            DVD_GUI.MessageBox("Your DVD has successfully completed!", 1);

            DVD_GUI.UpdateStatus(green, "PASS");
            continue begin;                                          
        }        
    }       
}
