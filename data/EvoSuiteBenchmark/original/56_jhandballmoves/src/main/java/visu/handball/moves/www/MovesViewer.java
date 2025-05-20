/*
 * Created on 21.05.2007
 * Created by Thomas Halm
 * Copyright (C) 2007 Thomas Halm
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
package visu.handball.moves.www;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import visu.handball.moves.Main;
import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;
import visu.handball.moves.views.Field;
import visu.handball.moves.xml.HandballModelReader;

public class MovesViewer extends JApplet implements ListSelectionListener,
		HandballModelListener {

	public final static String MOVES_FOLDER = "movesFolder";

	public final static String MOVES_LIST = "movesList";

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane jSplitPane2 = null;

	private JPanel jPanel = null;

	private JPanel buttonBar = null;

	private Field field = null;

	private JEditorPane jep = null;

	private JList movesList = null;

	private HandballModel handballModel;

	private Map<String, HandballModel> modelMap;

	private JSlider sequenceSelector;

	/**
	 * Konstruktor
	 */
	public MovesViewer() {
		super();
		handballModel = new HandballModel();
		handballModel.addListener(this);
		modelMap = new HashMap<String, HandballModel>();
	}

	/**
	 * This method initializes this
	 */
	public void init() {
		loadHandballModels();
		this.setContentPane(getJContentPane());
		this.setSize(this.getPreferredSize());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getButtonBar(), BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setResizeWeight(1.0);
			jSplitPane.setLeftComponent(getJSplitPane2());
			jSplitPane.setRightComponent(getHandballField());

		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setOneTouchExpandable(true);
			jSplitPane2.setTopComponent(new JScrollPane(getMovesListPanel()));
			jSplitPane2.setBottomComponent(getCommentView());
			jSplitPane2.setMinimumSize(new Dimension(300, 600));
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMovesListPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getMovesList(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonBar() {
		if (buttonBar == null) {
			buttonBar = new JPanel();
			buttonBar.setLayout(new GridLayout(1, 4));
			JButton play = new JButton("Bis zum Ende abspielen");
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (handballModel.getEvents().size() > 0) {
						handballModel.startAnimation(false);
					}
				}
			});
			play.setIcon(Main.createImageIcon("images/play_till_end.gif", "Spielzug bis zum Ende abspielen"));
			buttonBar.add(play);
			JButton playSequence = new JButton("Aktuelle Sequenz abspielen");
			playSequence.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (handballModel.getEvents().size() > 0) {
						handballModel.startAnimation(true);
					}
				}
			});
			playSequence.setIcon(Main.createImageIcon("images/play.gif",
				"Aktuelle Sequenz abspielen"));
			buttonBar.add(playSequence);

			sequenceSelector = new JSlider();
			sequenceSelector.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int seq = sequenceSelector.getValue();
					if (handballModel != null && seq > 0
							&& seq <= handballModel.getHighestSequenceNumber()) {
						if (handballModel.getMoveEvents(seq).size() > 0) {
							handballModel.setActualMoveEvent(handballModel
									.getMoveEvents(seq).get(0));
						}
					}

				}
			});
			buttonBar.add(sequenceSelector);
		}
		return buttonBar;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getHandballField() {
		if (field == null) {
			field = new Field(handballModel, new ColorModel());
			field.setMinimumSize(field.getPreferredSize());
			field.setMinimumSize(field.getPreferredSize());
		}
		return field;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getMovesList() {
		if (movesList == null) {
			DefaultListModel listModel = new DefaultListModel();

			for (Iterator<String> iter = modelMap.keySet().iterator(); iter
					.hasNext();) {
				String model = iter.next();
				listModel.addElement(model);
			}
			movesList = new JList(listModel);
			movesList.addListSelectionListener(this);
			movesList.setPreferredSize(new Dimension(200, 250));
		}
		return movesList;
	}

	private void loadHandballModels() {
		// Nach den angebenen Spielzügen suchen
		String movesFolderParameter = getParameter(MOVES_FOLDER);
		if (movesFolderParameter == null) {
			movesFolderParameter = "moves";
		}

		String list = getParameter(MOVES_LIST);
		StringTokenizer tokenzier = new StringTokenizer(list, ";");
		while (tokenzier.hasMoreTokens()) {
			String token = tokenzier.nextToken();
			try {
				URL move = new URL(getDocumentBase(), movesFolderParameter
						+ "/" + token);
				InputStream stream = move.openStream();
				if (stream != null) {
					HandballModelReader reader = HandballModelReader
							.getInstance(stream);
					HandballModel model = reader.readFromXml();
					if (model.getEvents().size() > 0) {
						modelMap.put(model.getMoveName(), model);
					} else {
						System.err
								.println("Spielzug ["
										+ model.getMoveName()
										+ "] enthält keine Ereignisse - wird ignoriert");
					}
					stream.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		String moveName = (String) movesList.getSelectedValue();
		handballModel.initWithLoadedModel(modelMap.get(moveName));
		handballModel.setState(HandballModel.State.ANIMATION);

		sequenceSelector.setMinimum(1);
		sequenceSelector.setMaximum(handballModel.getHighestSequenceNumber());
		sequenceSelector.setMajorTickSpacing(1);
		sequenceSelector.setPaintLabels(true);
		sequenceSelector.setPaintTicks(true);
		sequenceSelector.setSnapToTicks(true);
		sequenceSelector.setValue(1);

		jep.setText(handballModel.getComment());
	}

	private JPanel getCommentView() {
		JPanel panel = new JPanel(new BorderLayout());

		jep = new JEditorPane();
		jep.setFont(new Font("Arial", Font.PLAIN, 10));
		jep.setBackground(new Color(240, 255, 189));
		JScrollPane textView = new JScrollPane(jep);
		panel.add(textView, BorderLayout.CENTER);
		jep.setCaretPosition(0);
		jep.setEditable(false);
		return panel;
	}

	public void modelChanged() {
		if (handballModel.getState() == HandballModel.State.ANIMATION) {
			// damit Slider nach Animation einer einzelnen Sequenz auch
			// anschließend
			// auf der aktuellen Sequenz steht
			sequenceSelector.setValue(handballModel.getAcutalSequenceNr());
		}

	}
	
	public String[][] getParameterInfo() {
	    String[][] info = {
	      // Parameter Name     Kind of Value   Description
	        {"movesFolder",     "URL",          "a directory where the MovesFiles are stored"},
	        {"movesList",       "String",       "MoveFiles (stored in MoveFolder) which are selectable in the applet. Separated wird a \";\"."}
	    };
	    return info;
	}   

}
