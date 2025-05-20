import javax.swing.*;

public class jgaap {

    private static void createAndShowGUI() {

	JFrame frame = new JFrame("Java Graphical Authorship Attribution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	frame.getContentPane().add(new jgaapGUI());

	frame.pack();
	frame.setVisible(true);
        
       
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
