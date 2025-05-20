/*
JMCAView.java

Contains classes JMCAView, JMCAFileFilter (inner)

CEN4010
FALL 2005
GROUP2

Curt Hayashida, Scott Springer, Ian Cole

Revision History:
1) 10/20/05 - CH - Original creation for GUI prototype - GUI.java and UI.java
2) 10/24/05 - IC - Integration with controller class, first pass
				     1) New name - JMCAFrame.java
					 2) Move UI into file as Inner Class - JMCAPanel
					 3) Add JMCAFileFilter as Inner Class
					 4) Set display text frame to courier
					 5) Rework Frame sizing and controls for smaller screens
					 6) Removed code in actionPerformed that was recreating
					 		display and scroller
					 7) Added controller and wired in basic functions
					 		including setting filename and getting output
					 8) Selecting a new file clears the display
					 9) Made display read-only on creation
3) 10/30/05 - IC - Convert to true MVC (Model-View-Controller) per the grading notes
					 1) Changed from JMCAFrame has a JMCAController to
					 		JMCAController has a JMCAFrame
					 2) Rewired actions to MVC pattern
					 3) Renamed to JMCAView.java (Should be more obvious that we are using MVC
					 4) Removing Panel as inner class with all actions
							since its awkward in the controller to do
								JMCAFrame.JMCAPanel.methodCall(), and
								a few other minor annoyances.
					 5) Also changing so that JMCAView "has a" JFrame, instead of "is a" JFrame



Compilation (from project directory)
javac -d . *.java

Execution (from top-level project directory)
java com.soops.CEN4010.JMCA.JMCAController


*/
package com.soops.CEN4010.JMCA;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;



public class JMCAView
{

private JFrame 			frame;
private JPanel 			panel;

private JLabel 			label1;
private JTextField 		InputFile;
private JTextArea 		display;
private JButton 		analyzeButton;
private JButton 		selectFileButton;
private JScrollPane 	scroller;
private JFileChooser 	chooseFile;

public JMCAView()
    {
	frame = new JFrame("Java Method Cohesion Analyzer");
	panel = new JPanel();
	panel.setLayout(null);

    frame.setLayout(new GridLayout() );
    frame.add(panel);
        frame.setBounds(10, 10, 400, 400);
    frame.setSize(500, 500);



    label1 = new JLabel("File to be analyzed:");
    label1.setBounds(20, 20, 200, 20);
	panel.add(label1);

    InputFile = new JTextField();
    InputFile.setBounds(20,50, 350, 20);
    panel.add(InputFile);

	selectFileButton = new JButton("Select File");
	selectFileButton.setBounds(375, 50, 98, 20);
	panel.add(selectFileButton);

	analyzeButton = new JButton("Analyze");
	analyzeButton.setBounds(200, 100, 80, 20);
	panel.add(analyzeButton);

	display = new JTextArea();
	display.setBounds(20, 150, 450, 300);
	display.setFont (new Font("Courier", Font.PLAIN, 12));
	display.setEditable(false);
	scroller = new JScrollPane(display);
	scroller.setBounds(20, 150, 450, 300);
	panel.add(scroller);


	//shutdown application on window close
	frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});


    frame.setVisible(true);

    } // end JMCAFrame constructor



//Sets the text in the Display text control
public void updateDisplay(String input)
{
	display.setText(input);
}

//Launches a JFileChooser
public void chooseFile()
{
	chooseFile = new JFileChooser();
	chooseFile.addChoosableFileFilter(new JMCAFileFilter());
	chooseFile.showOpenDialog(frame);

	try
	{
		String fullPath = chooseFile.getSelectedFile().getCanonicalPath();
		InputFile.setText(fullPath);
		display.setText("");
	}
	catch (IOException ioe)
	{
		//do something
	}

}

//Called by the controller when filename is needed
public String getFilename()
{
	return InputFile.getText();
}

//Since this is MVC, the controller will be wiring the action
public void setAnalyzeListener( ActionListener al)
{
	analyzeButton.addActionListener (al);
}

//Since this is MVC, the controller will be wiring the action
public void setSelectFileListener( ActionListener al)
{
	selectFileButton.addActionListener (al);
}



// JMCAFileFilter restricts the file browser to displaying .java files
// See this link for details:
// http://java.sun.com/docs/books/tutorial/uiswing/components/filechooser.html#filters
// Some code below taken from that tutorial

public class JMCAFileFilter extends FileFilter {

    //Accept all directories and .java files.
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

		//get the file extension
		//then check the extension for our allowed filetype

	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');

	    if (i > 0 &&  i < s.length() - 1)
	    {
	        ext = s.substring(i+1).toLowerCase();
	    }


        if (ext != null) {
            if (ext.equals("java") || ext.equals("txt")) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription()
    {
        return "Java Class Files";
    }
} //end innerclass JMCAFileFilter

} // end JMCAView class


