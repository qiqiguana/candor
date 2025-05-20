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
package visu.handball.moves.model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ColorModel implements Cloneable {

	private Color fieldColor;

	private Color lineColor;

	private Color selectedPlayerColor;

	private Color attackersColor;

	private Color defendersColor;

	private Color hightlightColor;

	private Color moveColor;

	private Color passColor;

	private Color outlineBallColor;

	private Color fillBallColor;

	private Color eventPointOutlineColor;

	private Color eventPointFillColor;

	private Properties properties;

	public final static String FIELD_COLOR_KEY = "fieldDrawer.field";

	public final static String LINE_COLOR_KEY = "fieldDrawer.line";

	public final static String SELECTED_PLAYER_COLOR_KEY = "playerDrawer.selected";

	public final static String ATTACKERS_COLOR_KEY = "playerDrawer.attackers";

	public final static String DEFENDERS_COLOR_KEY = "playerDrawer.defenders";

	public final static String HIGHLIGHT_COLOR_KEY = "drawer.highlight";

	public final static String MOVE_COLOR_KEY = "eventDrawer.move";

	public final static String PASS_COLOR_KEY = "eventDrawer.pass";

	public final static String BALL_OUTLINE_COLOR_KEY = "ballDrawer.outline";

	public final static String BALL_FILL_COLOR_KEY = "ballDrawer.fill";

	public final static String EVENT_POINT_OUTLINE_COLOR_KEY = "eventPointDrawer.outline";

	public final static String EVENT_POINT_FILL_COLOR_KEY = "eventPointDrawer.fill";

	public final static String PROPERTIES_FILENAME = "jHandballMoves.properties";

	private List<ColorModelListener> listeners;

	public ColorModel() {
		properties = new Properties();
		loadProperties();
		initColors();
		listeners = new ArrayList<ColorModelListener>();
	}

	public void addColorModelListener(ColorModelListener listener) {
		listeners.add(listener);
	}

	private void fireModelChanged() {
		for (ColorModelListener listener : listeners) {
			listener.colorModelChanged(this);
		}
	}

	private void initColors() {
		fieldColor = new Color(Integer.valueOf(properties
				.getProperty(FIELD_COLOR_KEY)));
		lineColor = new Color(Integer.valueOf(properties
				.getProperty(LINE_COLOR_KEY)));
		selectedPlayerColor = new Color(Integer.valueOf(properties
				.getProperty(SELECTED_PLAYER_COLOR_KEY)));
		attackersColor = new Color(Integer.valueOf(properties
				.getProperty(ATTACKERS_COLOR_KEY)));
		defendersColor = new Color(Integer.valueOf(properties
				.getProperty(DEFENDERS_COLOR_KEY)));
		hightlightColor = new Color(Integer.valueOf(properties.getProperty(
				HIGHLIGHT_COLOR_KEY, String.valueOf(Color.YELLOW.getRGB()))));
		moveColor = new Color(Integer.valueOf(properties.getProperty(
				MOVE_COLOR_KEY, String.valueOf(Color.BLACK.getRGB()))));
		passColor = new Color(Integer.valueOf(properties.getProperty(
				PASS_COLOR_KEY, String.valueOf(Color.LIGHT_GRAY.getRGB()))));
		fillBallColor = new Color(Integer.valueOf(properties.getProperty(
				BALL_FILL_COLOR_KEY, String.valueOf(Color.BLACK.getRGB()))));
		outlineBallColor = new Color(Integer.valueOf(properties.getProperty(
				BALL_OUTLINE_COLOR_KEY, String.valueOf(Color.WHITE.getRGB()))));
		eventPointFillColor = new Color(Integer.valueOf(properties.getProperty(
				EVENT_POINT_FILL_COLOR_KEY, String.valueOf(Color.MAGENTA
						.getRGB()))));
		eventPointOutlineColor = new Color(Integer.valueOf(properties
				.getProperty(EVENT_POINT_OUTLINE_COLOR_KEY, String
						.valueOf(Color.BLACK.getRGB()))));
	}

	public void saveProperties() {
		syncColorWithProperties();
		
		try {
			properties.store(new FileOutputStream(PROPERTIES_FILENAME),
					"jHandballMoves Settings");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncColorWithProperties() {
		properties.put(FIELD_COLOR_KEY, String.valueOf(fieldColor.getRGB()));
		properties.put(LINE_COLOR_KEY, String.valueOf(lineColor.getRGB()));
		properties.put(SELECTED_PLAYER_COLOR_KEY, String
				.valueOf(selectedPlayerColor.getRGB()));
		properties.put(ATTACKERS_COLOR_KEY, String.valueOf(attackersColor
				.getRGB()));
		properties.put(DEFENDERS_COLOR_KEY, String.valueOf(defendersColor
				.getRGB()));
		properties.put(HIGHLIGHT_COLOR_KEY, String.valueOf(hightlightColor
				.getRGB()));
		properties.put(MOVE_COLOR_KEY, String.valueOf(moveColor.getRGB()));
		properties.put(PASS_COLOR_KEY, String.valueOf(passColor.getRGB()));
		properties.put(BALL_FILL_COLOR_KEY, String.valueOf(fillBallColor
				.getRGB()));
		properties.put(BALL_OUTLINE_COLOR_KEY, String.valueOf(outlineBallColor
				.getRGB()));
		properties.put(EVENT_POINT_FILL_COLOR_KEY, String.valueOf(eventPointFillColor
				.getRGB()));
		properties.put(EVENT_POINT_OUTLINE_COLOR_KEY, String.valueOf(eventPointOutlineColor
				.getRGB()));
	}

	private void loadProperties() {
		try {
		File propertiesFile = new File(PROPERTIES_FILENAME);
		if (!propertiesFile.exists()) {
			getDefaultColors();
		} else {
			try {
				FileInputStream input = new FileInputStream(propertiesFile);
				properties.load(input);
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		} catch (AccessControlException e) {
			//im Applet brauchen keine Datein geladen werden
			getDefaultColors();
		}

	}

	private void getDefaultColors() {
		properties.setProperty(ColorModel.FIELD_COLOR_KEY, String
				.valueOf(Color.BLUE.getRGB()));
		properties.setProperty(ColorModel.LINE_COLOR_KEY, String
				.valueOf(Color.WHITE.getRGB()));
		properties.setProperty(ColorModel.SELECTED_PLAYER_COLOR_KEY, String
				.valueOf(Color.CYAN.getRGB()));
		properties.setProperty(ColorModel.ATTACKERS_COLOR_KEY, String
				.valueOf(Color.GREEN.getRGB()));
		properties.setProperty(ColorModel.DEFENDERS_COLOR_KEY, String
				.valueOf(Color.RED.getRGB()));
		properties.setProperty(ColorModel.HIGHLIGHT_COLOR_KEY, String
				.valueOf(Color.YELLOW.getRGB()));
		properties.setProperty(ColorModel.MOVE_COLOR_KEY, String
				.valueOf(Color.BLACK.getRGB()));
		properties.setProperty(ColorModel.PASS_COLOR_KEY, String
				.valueOf(Color.LIGHT_GRAY.getRGB()));
		properties.setProperty(ColorModel.BALL_FILL_COLOR_KEY, String
				.valueOf(Color.BLACK.getRGB()));
		properties.setProperty(ColorModel.BALL_OUTLINE_COLOR_KEY, String
				.valueOf(Color.WHITE.getRGB()));
		properties.setProperty(ColorModel.EVENT_POINT_FILL_COLOR_KEY, String
				.valueOf(Color.MAGENTA.getRGB()));
		properties.setProperty(ColorModel.EVENT_POINT_OUTLINE_COLOR_KEY, String
				.valueOf(Color.BLACK.getRGB()));
	}

	public Color getFieldColor() {
		return fieldColor;
	}

	public void setFieldColor(Color fieldColor) {
		this.fieldColor = fieldColor;
		fireModelChanged();
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		fireModelChanged();
	}

	public void resetColors() {
		getDefaultColors();
		initColors();
		fireModelChanged();
	}

	public Color getSelectedPlayerColor() {
		return selectedPlayerColor;
	}

	public void setSelectedPlayerColor(Color selectedPlayerColor) {
		this.selectedPlayerColor = selectedPlayerColor;
		fireModelChanged();
	}

	public Color getAttackersColor() {
		return attackersColor;
	}

	public void setAttackersColor(Color attackersColor) {
		this.attackersColor = attackersColor;
		fireModelChanged();
	}

	public Color getDefendersColor() {
		return defendersColor;
	}

	public void setDefendersColor(Color defendersColor) {
		this.defendersColor = defendersColor;
		fireModelChanged();
	}

	public Color getHightlightColor() {
		return hightlightColor;
	}

	public void setHightlightColor(Color hightlightColor) {
		this.hightlightColor = hightlightColor;
		fireModelChanged();
	}

	public Color getMoveColor() {
		return moveColor;
	}

	public void setMoveColor(Color moveColor) {
		this.moveColor = moveColor;
		fireModelChanged();
	}

	public Color getPassColor() {
		return passColor;
	}

	public void setPassColor(Color passColor) {
		this.passColor = passColor;
		fireModelChanged();
	}

	public Color getFillBallColor() {
		return fillBallColor;
	}

	public void setFillBallColor(Color fillBallColor) {
		this.fillBallColor = fillBallColor;
		fireModelChanged();
	}

	public Color getOutlineBallColor() {
		return outlineBallColor;
	}

	public void setOutlineBallColor(Color outlineBallColor) {
		this.outlineBallColor = outlineBallColor;
		fireModelChanged();
	}

	public Color getEventPointFillColor() {
		return eventPointFillColor;
	}

	public void setEventPointFillColor(Color eventPointFillColor) {
		this.eventPointFillColor = eventPointFillColor;
		fireModelChanged();
	}

	public Color getEventPointOutlineColor() {
		return eventPointOutlineColor;
	}

	public void setEventPointOutlineColor(Color eventPointOutlineColor) {
		this.eventPointOutlineColor = eventPointOutlineColor;
		fireModelChanged();
	}

	public Properties getProperties() {
		syncColorWithProperties();
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		initColors();
		fireModelChanged();
	}
	
	

}
