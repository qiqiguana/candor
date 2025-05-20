package corina.graph;

/*
 * Density graph: graphable wrapper, essentially.
 */

import java.util.List;
import corina.Year;

public class DensityGraph implements Graphable {
	private List data;
	private Year start;
	private String gname;
	
	public DensityGraph(List data, Year start, String name) {		
		this.data = data;
		this.start = start;
		this.gname = name;
	}
	
    public List getData() {
        return data;
    }
    
    public Year getStart() {
        return start;
    }
    
    public float getScale() {
        return 1.0f;
    }
    
    public String toString() {
        return gname;
    }
}
