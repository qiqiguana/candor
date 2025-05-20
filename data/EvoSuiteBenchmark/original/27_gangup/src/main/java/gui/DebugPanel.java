/* $Id: DebugPanel.java,v 1.5 2004/05/04 14:14:34 njursten Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.5 $
 *
 */

package gui;

import module.*;
import static module.MessageFactory.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The DebugPanel may be used to view and send messages.
 *
 */
public class DebugPanel extends JPanel implements ActionListener {

    private DefaultTableModel dump;
    private JTextField body;
    private JTextField topic;
    private Kernel kernel;

    /**
     * Creates a new Instance of the DebugPanel class.
     * @param kernel the associated kernel instance.
     */
    public DebugPanel(Kernel kernel) {
	this.kernel = kernel;
	createComponents();
    }
    
    /**
     * Appends the specified message to the received messages table.
     * @param msg the message to append to the received messages table.
     */
    public void append(Message msg) {
	try {
	    dump.addRow(new Object[] 
		{ new Long(msg.getID()),
		  new Long(msg.getSID()),
		  msg.getHeader(), 
		  msg.getSender(),
		  msg.getRecipient(), 
		  new Integer(msg.getState()),
		  new Long(msg.getTTL()),
		  new Long(msg.getTimeStamp()),
		  msg.getBody()
		} );
	} catch (NullPointerException e) {
	    System.err.println("DebugPanel.append(): *** WARNING *** [NPE] " +
			       "caught null pointer exception.");
	    e.printStackTrace(System.err);
	} catch (Exception e) {
	    System.err.println("DebugPanel.append(): *** WARNING *** [X] " +
			       "caught unknown exception.");
	    e.printStackTrace(System.err);
	}
    }

    /**
     * This method is invoked when the Inject button is clicked.
     * @param ev the ActionEvent triggered when button was clicked.
     */
    public void actionPerformed(ActionEvent ev) {
	Message m = null;
	try {
	    m = createMessage(topic.getText().toUpperCase(), body.getText());
	    m.setSender("SnoopyModule");
	    m.sendTo(kernel);
	} catch (MessageDeliveryException e) {
	    e.printStackTrace(System.err);
	}
    }

    /**
     * Create and layout the components of this panel.
     *
     */
    private void createComponents() {
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints cons = new GridBagConstraints();

	JLabel topicLabel = new JLabel("Topic: ");
	JLabel bodyLabel  = new JLabel("Data: ");
	JLabel debugLabel = new JLabel("Debug: ");
	JButton button0 = new JButton("Inject");

	topic = new JTextField();
	body = new JTextField();
	dump = new DefaultTableModel(
	    new String[] {"id","sid","topic","src","dst",
			  "state","ttl","time","data"}, 0);

	JTable table = new JTable(dump);
	table.setDefaultRenderer(Object.class, new ToolTipRenderer());
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

	JScrollPane scroll1 = new JScrollPane(table);

	button0.setActionCommand("send");
	button0.addActionListener(this);
	
	setLayout(layout);

	cons.weightx = 0;
	cons.weighty = 0;
	cons.fill = GridBagConstraints.BOTH;
	layout.setConstraints(topicLabel, cons);
	add(topicLabel);

	cons.weightx = 1;
	cons.weighty = 0;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(topic, cons);
	add(topic);

	cons.weightx = 0;
	cons.weighty = 0;
	cons.gridwidth = 1;
	layout.setConstraints(bodyLabel, cons);
	add(bodyLabel);

	cons.weightx = 1;
	cons.weighty = 0;
	cons.gridwidth = 1;
	layout.setConstraints(body, cons);
	add(body);

	cons.weightx = 0;
	cons.weighty = 0;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(button0, cons);
	add(button0);
/*
	cons.weightx = 1;
	cons.weighty = 0;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(debugLabel, cons);
*/
	cons.weightx = 1;
	cons.weighty = 1;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(scroll1, cons);
	add(scroll1);
    }
}
