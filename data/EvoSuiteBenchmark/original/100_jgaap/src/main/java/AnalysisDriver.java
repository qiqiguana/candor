import java.util.Vector;

interface StatisticalAnalysis {
    
    public String analyze(EventSet unknown, Vector<EventSet> known);

}

abstract class AnalysisDriver implements StatisticalAnalysis {
    
    abstract public String analyze(EventSet unknown, Vector<EventSet> known);

}
