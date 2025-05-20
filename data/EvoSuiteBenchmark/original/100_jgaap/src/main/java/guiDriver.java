import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

class guiDriver {

    Vector<Document> k_docs;
    Vector<DocumentSet> k_docsets;
    Vector<EventSet> k_es;

    Vector<Document> u_docs;
    Vector<DocumentSet> u_docsets;
    Vector<EventSet> u_es;

    Vector<Preprocessor> processEngine;

    guiDriver() {
	k_docs    = new Vector<Document>();
	k_docsets = new Vector<DocumentSet>();
	k_es      = new Vector<EventSet>();
	u_docs    = new Vector<Document>();
	u_docsets = new Vector<DocumentSet>();
	u_es      = new Vector<EventSet>();
    
	processEngine = new Vector<Preprocessor>();
    }

    public void addDocument(String path, String author) {
	Document    d_temp  = new Document(path);
	if (!author.equals("")) {
	    d_temp.setAuthor(author);
	    k_docs.add(d_temp);
	}
	else {
	    u_docs.add(d_temp);
	}

	System.out.println("Known Documents: " + k_docs.size());
	System.out.println("Unknown Documents: " + u_docs.size());
    }

    class preprocessEngineDriver extends Thread{

	JCheckBox [] cz;
	JTabbedPane pane;
	JProgressBar progress;
	
	preprocessEngineDriver(JCheckBox [] box, JTabbedPane tab, JProgressBar prog) {
	    cz = box;
	    pane = tab;
	    progress = prog;
	}
	    
	
	public void run() {
	    pane.setEnabled(false);
	    processEngine.clear();
	    if (cz[0].isSelected())
		processEngine.add(new UnifyCase());
	    if (cz[1].isSelected())
		processEngine.add(new NormalizeWhitespace());
	    if (cz[2].isSelected())
		processEngine.add(new StripHTML());

	    canonicize();
	    pane.setEnabled(true);
	}

	private synchronized void canonicize() {
	    progress.setMinimum(0);
	    progress.setMaximum(u_docs.size() + k_docs.size() + 2 * processEngine.size());
	    progress.setValue(0);
	    int value = 0;
	    u_docsets.clear();
	    k_docsets.clear();
	    
	    for (int i = 0; i < k_docs.size(); i++) {
		for (int j = 0; j < processEngine.size(); j++) {
		    k_docs.elementAt(i).procText = processEngine.elementAt(j).process(k_docs.elementAt(i).procText);
		    progress.setValue(++value);		
		}
		k_docsets.add(new DocumentSet(k_docs.elementAt(i)));
		progress.getValue();
	    }
	    
	    for (int i = 0; i < u_docs.size(); i++) {
		for (int j = 0; j < processEngine.size(); j++) {
		    u_docs.elementAt(i).procText = processEngine.elementAt(j).process(u_docs.elementAt(i).procText);
		    progress.setValue(++value);	
		}
		u_docsets.add(new DocumentSet(u_docs.elementAt(i)));
		progress.getValue();
	    }
	    
	    System.out.println("Canonicization complete: Terminating Worker Thread");
	}
    }

    public preprocessEngineDriver preprocessEngineDriver(JCheckBox [] boxes, JTabbedPane tab, JProgressBar prog) {
	return new preprocessEngineDriver(boxes, tab, prog);
    }


    class createEventSetDriver extends Thread {

	String action;
	JTabbedPane tab;
	JProgressBar prog;

	createEventSetDriver(String action, JTabbedPane tab, JProgressBar prog) {
	    this.action = action;
	    this.tab = tab;
	    this.prog = prog;
	}

	public void run() {
	    tab.setEnabled(false);
	    prog.setMinimum(0);
	    prog.setMaximum(u_docsets.size() + k_docsets.size());
	    prog.setValue(0);
	    int value = 0;
	    u_es.clear();
	    k_es.clear();
	    EventDriver te;
	    if (action.equals("characters"))
		te = new CharacterEventSet();
	    else
		return;  //should probably throw an error instead
	    
	    for (int i = 0; i < k_docsets.size(); i++) {
		EventSet es = te.createEventSet(k_docsets.elementAt(i));
		es.setAuthor(k_docsets.elementAt(0).getDocument(0).getAuthor());
		k_es.add(es);
		prog.setValue(++value);
		prog.getValue();
	    }
	    
	    for (int i = 0; i < u_docsets.size(); i++) {
		u_es.add(te.createEventSet(u_docsets.elementAt(i)));
		prog.setValue(++value);
		prog.getValue();
	    }
	    tab.setEnabled(true);
	}
    }

    public createEventSetDriver createEventSetDriver(String action, JTabbedPane tab, JProgressBar prog) {
	return new createEventSetDriver(action, tab, prog);
    }


    class runStatisticalAnalysisDriver extends Thread {
	
	String results, action;
	JProgressBar prog;
	JTabbedPane pane;

	runStatisticalAnalysisDriver(String action, JTabbedPane pane, JProgressBar prog) {
	    this.action = action;
	    this.prog = prog;
	    this.pane = pane;
	}

	public void run() {
	    pane.setEnabled(false);
	    prog.setIndeterminate(true);
	    prog.setMinimum(0);
	    prog.setMaximum(1);
	    prog.setValue(0);
	    AnalysisDriver ad;
	    results = new String();
	    if (action.equals("crossentropy")) 
		ad = new CrossEntropyDriver();
	    else { 
		ad = new CrossEntropyDriver(); //shouldn't reach this
	    }
	    for (int i = 0; i < u_es.size(); i++) {
		results += u_docsets.elementAt(i).getDocument(0).getFilename() + " - ";
		results += ad.analyze(u_es.elementAt(i), k_es) + "\n";
	    }
	    prog.setIndeterminate(false);
	    prog.setValue(1);
	    prog.getValue();
	    pane.setEnabled(true);
	}

	public synchronized String getResults() {
	    try {
		join();
	    } catch (InterruptedException ie) {
		System.out.println("Analysis Thread Illegally Interrupted");
	    }
	    return results;
	}
    }

    public runStatisticalAnalysisDriver runStatisticalAnalysisDriver(String action, JTabbedPane tab, JProgressBar prog) {
	return new runStatisticalAnalysisDriver(action, tab, prog);
    }

}
