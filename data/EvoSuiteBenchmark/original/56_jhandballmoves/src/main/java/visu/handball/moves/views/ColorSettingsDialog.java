/*
 * Created on 26.01.2007
 * Created by Thomas Halm
 * Copyright (C) 2007  Richard Doerfler, Thomas Halm
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
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import visu.handball.moves.Main;

public class ColorSettingsDialog extends JFrame {

	abstract public class ColoredButton extends JButton implements
			ActionListener {

		public ColoredButton(Color color) {
			setContentAreaFilled(false);
			setOpaque(true);
			setBackground(color);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(ColorSettingsDialog.this,
					"Farben anpassen...", getBackground());
			if (color != null) {
				changeColor(color);
				setBackground(color);
			}
		}

		abstract protected void changeColor(Color color);
	}

	private Properties oldColorProperties;
	
	public ColorSettingsDialog() {
		super("Farben anpassen");
		setIconImage(Main.createImageIcon("images/colors.gif",
				"Change colors...").getImage());

		oldColorProperties = (Properties) Main.getColorModel().getProperties().clone();

		JPanel panel = new JPanel(new BorderLayout());
		setContentPane(panel);

		JPanel settingsPanel = new JPanel(new GridLayout(0, 2));

		settingsPanel.add(new JLabel("Spielfeld:"));

		ColoredButton fieldColorButton = new ColoredButton(Main.getColorModel()
				.getFieldColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setFieldColor(color);
			}
		};
		settingsPanel.add(fieldColorButton);

		settingsPanel.add(new JLabel("Feldlinien:"));
		ColoredButton lineColorButton = new ColoredButton(Main.getColorModel()
				.getLineColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setLineColor(color);
			}
		};
		settingsPanel.add(lineColorButton);
		
		settingsPanel.add(new JLabel("Angreifer:"));
		ColoredButton attackers = new ColoredButton(Main.getColorModel()
				.getAttackersColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setAttackersColor(color);
			}
		};
		settingsPanel.add(attackers);

		settingsPanel.add(new JLabel("Verteidiger:"));
		ColoredButton defenders = new ColoredButton(Main.getColorModel()
				.getDefendersColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setDefendersColor(color);
			}
		};
		settingsPanel.add(defenders);
		
		settingsPanel.add(new JLabel("Selektierter Spieler:"));
		ColoredButton selectedPlayer = new ColoredButton(Main.getColorModel()
				.getSelectedPlayerColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setSelectedPlayerColor(color);
			}
		};
		settingsPanel.add(selectedPlayer);
		
		settingsPanel.add(new JLabel("Hervorheben:"));
		ColoredButton highlight = new ColoredButton(Main.getColorModel()
				.getHightlightColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setHightlightColor(color);
			}
		};
		settingsPanel.add(highlight);
		
		settingsPanel.add(new JLabel("Laufweg:"));
		ColoredButton moveColor = new ColoredButton(Main.getColorModel()
				.getMoveColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setMoveColor(color);
			}
		};
		settingsPanel.add(moveColor);
		
		settingsPanel.add(new JLabel("Passweg:"));
		ColoredButton passColor = new ColoredButton(Main.getColorModel()
				.getPassColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setPassColor(color);
			}
		};
		settingsPanel.add(passColor);
		
		settingsPanel.add(new JLabel("Ball (Umrandung):"));
		ColoredButton ballOutline = new ColoredButton(Main.getColorModel()
				.getOutlineBallColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setOutlineBallColor(color);
			}
		};
		settingsPanel.add(ballOutline);
		
		settingsPanel.add(new JLabel("Ball (Fläche):"));
		ColoredButton ballFill = new ColoredButton(Main.getColorModel()
				.getFillBallColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setFillBallColor(color);
			}
		};
		settingsPanel.add(ballFill);
		
		settingsPanel.add(new JLabel("Wegpunkt (Umrandung):"));
		ColoredButton eventPointOutline = new ColoredButton(Main.getColorModel()
				.getEventPointOutlineColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setEventPointOutlineColor(color);
			}
		};
		settingsPanel.add(eventPointOutline);
		
		settingsPanel.add(new JLabel("Wegpunkt (Fläche):"));
		ColoredButton eventPointFill = new ColoredButton(Main.getColorModel()
				.getEventPointFillColor()) {
			protected void changeColor(Color color) {
				Main.getColorModel().setEventPointFillColor(color);
			}
		};
		settingsPanel.add(eventPointFill);
		
		panel.add(settingsPanel, BorderLayout.CENTER);

		// Button Bar
		panel.add(createButtonBar(), BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(385, 320);
		setVisible(true);
	}

	private JPanel createButtonBar() {
		JPanel buttonBar = new JPanel(new GridLayout(1, 4));

		JButton defaultColors = new JButton("Zurücksetzen");
		defaultColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Colormodel zurücksetzen
				Main.getColorModel().resetColors();
				ColorSettingsDialog.this.dispose();
			}
		});
		buttonBar.add(defaultColors);

		buttonBar.add(new JLabel(""));

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorSettingsDialog.this.dispose();
			}
		});
		buttonBar.add(ok);

		JButton cancel = new JButton("Abbrechen");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Farben aus geklonetem Model wieder herstellen
				Main.getColorModel().setProperties(oldColorProperties);
				ColorSettingsDialog.this.dispose();
			}
		});
		buttonBar.add(cancel);
		
		return buttonBar;
	}
}
