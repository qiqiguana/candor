/*
*   PlotWindow  -- A Swing frame for displaying a plot panel.
*
*   Copyright (C) 2000-2002 by Joseph A. Huwaldt <jhuwaldt@knology.net>.
*   All rights reserved.
*   
*   This library is free software; you can redistribute it and/or
*   modify it under the terms of the GNU Library General Public
*   License as published by the Free Software Foundation; either
*   version 2 of the License, or (at your option) any later version.
*   
*   This library is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*   Library General Public License for more details.
**/
package jahuwaldt.plot;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;


/**
*  <p> A simple Swing frame that can be used to display a plot panel.
*  </p>
*
*  <p>  Modified by:  Joseph A. Huwaldt  </p>
*
*  @author  Joseph A. Huwaldt   Date:  September 22, 2000
*  @version December 12, 2000
**/
public class PlotWindow extends JFrame {

	double[] xArr, yArr;

	//-------------------------------------------------------------------------
	/**
	*  Creates a plot window that displays the specified plot panel.
	*
	*  @param  name   The title to show in the window's title bar.
	*  @param  plot   The plot panel to be displayed in this window.
	**/
	public PlotWindow(String name) {
		super(name);
                xArr = new double[30];
                yArr = new double[30];
	}

	//-------------------------------------------------------------------------

        public void setData(double[] x, double[] y) {

            //xArr[0] = (double) 0;
            for(int i=0;i<x.length;i++){
                xArr[i] = x[i];
            }
            //yArr[0] = (double) 0;
            for(int i=0;i<y.length;i++){
                yArr[i] = y[i];
            }
        }


	/**
	*  A simple method to test this PlotWindow
	**/
	public static void main(String[] args) {
		PlotWindow window = new PlotWindow("Gating Charge Results");
		window.setSize(500, 300);
		window.setLocation(50,50);
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.show();
	}

        public void drawPlot(String title, String xaxis, String yaxis) {
            Plot2D aPlot = new SimplePlotXY(xArr, yArr, title, xaxis, yaxis,
		null, null, new CircleSymbol());

            //PlotAxis xAxis = aPlot.getHorizontalAxis();

            PlotPanel panel = new PlotPanel(aPlot);
            panel.setBackground( Color.white );

            
            getContentPane().add(panel);
        }

        public void saveImage(String path) {
            try{
                //Get the screen size
                Rectangle rectangle = new Rectangle(this.getBounds());
                Robot robot = new Robot();
                BufferedImage image = robot.createScreenCapture(rectangle);

                //Save the screenshot as a png
                File file = new File(path);
                ImageIO.write(image, "jpg", file);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

}


