/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.gui;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import jigl.image.ImageSequence;
import jigl.image.exceptions.BadImageException;

/**
 * Sequence Canvas is a class that handles all the appropriate functionality of a sequence. It
 * includes animation and a control bar for that animation
 * 
 * TODO: replace currentCanvas field with a function
 */

public class SequenceCanvas extends Canvas implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;

	private List<ImageCanvas> images = null;
	private int displayed = 0;
	/** The current, displayable Canvas */
	public ImageCanvas currentCanvas = null;
	private CloseableFrame control = null;
	private Label frames_per_sec = new Label(" 1 frame/second      ");
	private Label frame_number = new Label("  frame #1  ");
	// private int frameNumber = -1;
	private int delay;
	private Thread animatorThread;
	private boolean frozen = false;
	private int direction = 0;
	private float fps2 = 0;

	Dimension offDimension = null;
	java.awt.Image offImage = null;
	Graphics offGraphics = null;

	/** Creates an empty SequenceCanvas */
	public SequenceCanvas() {
		images = new ArrayList<ImageCanvas>();
	}

	/** Creates a SequenceCanvas from a given sequence */
	public SequenceCanvas(ImageSequence sequence) throws BadImageException {
		images = new ArrayList<ImageCanvas>(0);
		for (int a = 0; a < sequence.number(); a++)
			images.add(new ImageCanvas(sequence.get(a)));
		currentCanvas = new ImageCanvas(sequence.get(0));
	}

	/** Adds an image to the SequenceCanvas */
	public void add(jigl.image.Image image) throws BadImageException {
		images.add(new ImageCanvas(image));
		if (images.size() == 1) {
			//TODO: Should we instantiate yet another ImageCanvas here?
			currentCanvas = new ImageCanvas(image);

		}
	}

	/** Adds an image to the SequenceCanvas at a given <i>index</i> 
	 * @throws BadImageException */
	public void add(jigl.image.Image image, int index) throws BadImageException {
//		images.insertElementAt(image, index);
		images.add(index, new ImageCanvas(image));
	}

	/** Gets the JIGL image at <i>index</i> */
	public jigl.image.Image getImage(int index) {
		ImageCanvas canvas = images.get(index);
		return canvas.getImage();
	}

	/** Gets the java image at <i>index</i> */
	public java.awt.Image getJavaImage(int index) {
		ImageCanvas canvas = images.get(index);
		return canvas.getJavaImage();
	}

	/** Gets the ImageCanvas at <i>index</i> */
	public ImageCanvas getCanvas(int index) {
		return images.get(index);
	}

	/** Sets the JIGL image at <i>index</i> to <i>image</i> */
	public void setImage(jigl.image.Image image, int index) throws BadImageException {
		ImageCanvas canvas = images.get(index);
		canvas.setImage(image);
	}

	/** Sets the java image at <i>index</i> to <i>image</i> */
	public void setImage(java.awt.Image image, int index) throws BadImageException {
		ImageCanvas canvas = images.get(index);
		canvas.setImage(image);
	}

	/** Paints the current image to the screen */
	public void paint(Graphics g) {
		ImageCanvas canvas = images.get(displayed);
		canvas.paint(g);

	}

	/** Paints the next image to the screen */
	public void next() throws BadImageException {
		if (displayed < images.size() - 1)
			displayed = displayed + 1;
		else
			displayed = 0;
		ImageCanvas tcanvas = images.get(displayed);
		currentCanvas.setImage(tcanvas.getJavaImage());

		currentCanvas.update(currentCanvas.getGraphics());
	}

	/** Paints the previous image to the screen */
	public void previous() throws BadImageException {
		if (displayed != 0)
			displayed = displayed - 1;
		else
			displayed = images.size() - 1;
		ImageCanvas tcanvas = images.get(displayed);
		currentCanvas.setImage(tcanvas.getJavaImage());
		currentCanvas.update(currentCanvas.getGraphics());
	}

	/**
	 * Starts animation for this SequenceCanvas and brings up Control frame for the animation at one
	 * frame per second. If <i>addFrame</i> is true then the control frame will be added. Otherwise
	 * it will not be displayed.
	 */
	public void animate(boolean addFrame) {

		control = new CloseableFrame("Animation Controls");
		control.setSize(325, 85);
		control.setBackground(Color.lightGray);

		Font font = new Font("JIGL Font", Font.PLAIN, 10);
		// Font font2=new Font("Button Font", Font.BOLD, 17);
		Panel panel = new Panel();
		panel.setFont(font);
		panel.add(frames_per_sec);
		panel.add(frame_number);

		Button start = new Button();
		// start.setFont(font2);
		start.setLabel("<GO>");

		Button stop = new Button();
		// stop.setFont(font2);
		stop.setLabel("[STOP]");
		Button reverse = new Button();
		// reverse.setFont(font2);
		reverse.setLabel("REV");
		Button forward = new Button();
		// forward.setFont(font2);
		forward.setLabel("FWD");
		Button faster = new Button();
		// faster.setFont(font2);
		faster.setLabel(">>>");
		Button slower = new Button();
		// slower.setFont(font2);
		slower.setLabel("<<<");

		start.setSize(50, 50);
		stop.setSize(50, 50);
		reverse.setSize(75, 50);
		forward.setSize(75, 50);
		slower.setSize(100, 50);
		faster.setSize(100, 50);

		start.addActionListener(this);
		stop.addActionListener(this);
		reverse.addActionListener(this);
		forward.addActionListener(this);
		faster.addActionListener(this);
		slower.addActionListener(this);

		start.setActionCommand("START");
		stop.setActionCommand("STOP");
		reverse.setActionCommand("REV");
		forward.setActionCommand("FWD");
		faster.setActionCommand("FAST");
		slower.setActionCommand("SLOW");

		Panel control2 = new Panel();
		control2.add(reverse);
		control2.add(forward);
		control2.add(start);
		control2.add(stop);
		control2.add(slower);
		control2.add(faster);

		control.add(control2, "North");
		control.add(panel, "South");
		if (addFrame == true)
			control.setVisible(true);
		currentCanvas.update(currentCanvas.getGraphics());
		initAnimator(1);
		start();

	}

	/** Shows the control frame for animation */
	public void showControls() {
		control.setVisible(true);
	}

	/** Hides the control frame for animation */
	public void hideControls() {
		control.setVisible(false);
	}

	/** Performs appropriate function from butttons */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command == "REV") {
			direction = 1;
		}
		if (command == "FWD") {
			direction = 0;
		}
		if (command == "STOP")
			stopAnimation();
		if (command == "START") {
			start();
			run();
		}
		if (command == "FAST") {
			if (fps2 < 1)
				initAnimator(fps2 * 2);
			else
				initAnimator(fps2 + 1);
		}

		if (command == "SLOW") {
			if (fps2 <= 1)
				initAnimator(fps2 / 2);
			else
				initAnimator(fps2 - 1);
		}
	}

	/**
	 * Initializes the Animator with <i>fps</i> being the number of frames per second
	 */
	public void initAnimator(float fps) {
		// float delay2 = 0;
		if (fps == 1)
			frames_per_sec.setText(" 1 frame/second ");
		else
			frames_per_sec.setText(" " + fps + " frames/second");
		delay = (fps > 0) ? (int) (1000 / fps) : 100;
		fps2 = fps;
	}

	/** Starts the animation. Note: this method calls run automatically */
	public void start() {
		if (frozen) {
			// Do nothing. The user has requested that we
			// stop changing the image.
		} else {
			// Start animating!
			if (animatorThread == null) {
				animatorThread = new Thread(this);
				animatorThread.start();
			}
		}
	}

	/** Stops the animation */
	public void stopAnimation() {
		animatorThread = null;
	}

	/** Runs the Animation */
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		// Remember the starting time.
		long startTime = System.currentTimeMillis();

		Thread currentThread = Thread.currentThread();
		while (currentThread == animatorThread) {
			change_display();

			try {
				startTime += delay;

				Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));

			} catch (InterruptedException e) {
			}

		}

	}

	private void change_display() {
		if (direction == 0)
			if (displayed < images.size() - 1)
				displayed = displayed + 1;
			else
				displayed = 0;
		else if (displayed != 0)
			displayed = displayed - 1;
		else
			displayed = images.size() - 1;

		frame_number.setText(" frame #" + displayed);

		ImageCanvas tcanvas = images.get(displayed);

		frame_number.setText(" frame #" + displayed);

		currentCanvas.getGraphics().drawImage(tcanvas.getJavaImage(), 0, 0, currentCanvas);
	}

}
