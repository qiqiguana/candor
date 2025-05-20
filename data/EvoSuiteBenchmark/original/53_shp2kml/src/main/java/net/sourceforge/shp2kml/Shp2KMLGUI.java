package net.sourceforge.shp2kml;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileFilter;

public class Shp2KMLGUI extends JFrame implements Runnable {
	private static final long serialVersionUID = -6154094250620419998L;
	private 	JFileChooser jfile = new JFileChooser();
	
	public boolean setGui()
	{
		boolean retval = false;
		JProgressBar jpbar = new JProgressBar();
		jfile.setApproveButtonText("Convert to KML");
		jfile.setFileFilter(new ShpFileFilter());
		jfile.setFileHidingEnabled(true);
		
		JButton setKML = new JButton("Select Shapefile & Begin Conversion");
		setKML.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				jfile.showOpenDialog(getParent());
				Converter.convertShp("file://"+jfile.getSelectedFile().getAbsolutePath());
				
			}
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		this.getContentPane().add(setKML,BorderLayout.NORTH);
		this.getContentPane().add(jpbar,BorderLayout.SOUTH);
		
		this.setTitle("Shp2KML");
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		retval = true;
		return retval;
	}

	public void run() {
		this.setGui();
	}

	
		
	
	
	public class ShpFileFilter extends FileFilter{

	public boolean accept(File arg0) {
		boolean retval = false;
		if(arg0.isDirectory() || arg0.getName().endsWith(".shp"))
		{
			retval = true;
		}
		return retval;
	}

	public String getDescription() {
		return "ESRI Shapefile";
	}
	
}

}
