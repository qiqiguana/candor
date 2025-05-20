package brain.ga;

import java.util.*;

public class Population
{
	public List genoms=new ArrayList();
	protected Selector selector;
	protected Evaluator evaluator;
	protected int genomeSize = 4;
	public Population()
	{
		System.out.println("Constructor Population 1");
	}

        public void initialize(GAEnumAllelesSet allelesSet)
        {
        }
	public Genome selectNextGenome()
	{
		return selector.select(this);
	}
	public void setSelector(Selector newSelector)
	{
		selector = newSelector;
	}

	public Selector getSelector()
	{
		return selector;
	}

	public void setEvaluator(Evaluator newEvaluator)
	{
		evaluator = newEvaluator;
	}

	public Evaluator getEvaluator()
	{
		return evaluator;
	}

	public Genome get(int i)
	{
		return (Genome)genoms.get(i-1);
	}
	public int getSize()
	{
		return genoms.size();
	}
	public void sort()
	{
		Collections.sort(genoms);
                System.out.println("acilea ma sortez");
                for (int i = 0; i < this.genoms.size(); i++)
                    {System.out.print("Portofoliu " + i + " ");
                     Genome g = (Genome)this.genoms.get(i);
                     System.out.println(g + " Score " + g.getScore());
                        }
	}

	public void setGenomeSize(int newGenomeSize)
	{
		genomeSize = newGenomeSize;
	}

	public int getGenomeSize()
	{
		return genomeSize;
	}

}