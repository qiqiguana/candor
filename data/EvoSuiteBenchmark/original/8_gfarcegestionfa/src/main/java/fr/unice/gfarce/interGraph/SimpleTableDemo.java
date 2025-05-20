package fr.unice.gfarce.interGraph;


/*
 * SimpleTableDemo.java requires no other files.
 */

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleTableDemo extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
	String[] columnNames;
	Object[][] data;
	ListSelectionModel listSelectionModel;

	public SimpleTableDemo(String[] columnNames, Object[][] data) {

		this.columnNames = columnNames;
		this.data = data;


		/*
		String[] columnNames = {"First Name",
				"Last Name",
				"Sport",
				"# of Years",
		"Vegetarian"};

		Object[][] data = {
				{"Kathy", "Smith",
					"Snowboarding", new Integer(5), new Boolean(false)},
					{"John", "Doe",
						"Rowing", new Integer(3), new Boolean(true)},
						{"Sue", "Black",
							"Knitting", new Integer(2), new Boolean(false)},
							{"Jane", "White",
								"Speed reading", new Integer(20), new Boolean(true)},
								{"Joe", "Brown",
									"Pool", new Integer(10), new Boolean(false)}
		};
		 */
		final JTable table = new JTable(data, columnNames);
		/*
		listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		table.setSelectionModel(listSelectionModel);
		*/
		table.setPreferredScrollableViewportSize(new Dimension(500, 100));
		table.setFillsViewportHeight(true);

		if (DEBUG) {
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					printDebugData(table);
				}
			});
		}

		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);
	}

	private void printDebugData(JTable table) {
		int numRows = table.getRowCount();
		int numCols = table.getColumnCount();
		javax.swing.table.TableModel model = table.getModel();

		System.out.println("Value of data: ");
		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + model.getValueAt(i, j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	public int getNbrColonne(){
		return columnNames.length;
	}

	public int getNbrLigne(){
		return data.length;
	}

	public Object getValueAt(int ligne, int col) {
		return this.data[ligne][col];
	}
	

	public Object getLigne(Object nom){
		int tailleLigne = this.getNbrColonne();
		int tailleCol = this.getNbrLigne();
		String[] ligne = null;
		for(int j=0; j<tailleCol; j++){
			for(int i=0; i<tailleLigne; i++){
				System.out.println(i+ " "+ j);
				if(this.getValueAt(i, j)==(Object)nom){
					for(int k=0; k<tailleLigne; k++){
						//ligne[k] = (String)this.getValueAt(k, j);
						System.out.println(this.getValueAt(i, k));
					}
					return ligne;
				}
			}
		}

		return ligne;
	}


}
