package brain.ga;
import java.util.*;
public class VectorGenome extends Genome
{
	private Vector genes;
	public VectorGenome()
	{
		//System.out.println("Constructor Portofolio");
                this.genes = new Vector();
	}

	public VectorGenome(Vector genes, Evaluator evaluator)
	{
		this.evaluator = evaluator;
		this.genes = genes;
		//System.out.println("Constructor Portfolio 2!");
                //System.out.println(genes);
	}
	public int getGenesCount()
	{
		return genes.size();
	}
	public void setGene(int i, Object gene)
	{
		genes.set(i,gene);
	}
	public Object getGene(int i)
	{
		return genes.get(i);
	}
}

