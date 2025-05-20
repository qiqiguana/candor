/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package visu.handball.moves.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import visu.handball.moves.Main;

public class AboutDialog extends JFrame {

	private AnimatedImageLabel animatedLogo;
	
	public AboutDialog() {
		super("Über");

		JPanel panel = new JPanel(new BorderLayout());
		setContentPane(panel);

		JPanel imagePanel = new JPanel(new GridLayout(2, 1));

		animatedLogo = new AnimatedImageLabel(Main.class.getResource("images/splash.png"));
		imagePanel.add(animatedLogo);

		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		JLabel label = new JLabel(
				"<html><center>Programmiert von:<br><br>Richard Dörfler <br>Thomas Halm<br><br>© 2006<br>Veröffentlicht als Open-Source</center></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		imagePanel.add(label);

		JButton close = new JButton("Schließen");

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.animatedLogo.stopAnimation();
				AboutDialog.this.dispose();
			}
		});
		textPanel.add(close);

		panel.add(imagePanel, BorderLayout.CENTER);
		panel.add(textPanel, BorderLayout.SOUTH);

		pack();
		setResizable(false);

		Main.locateOnScreenCenter(this);
		
		animatedLogo.startAnimation();
	}

}
