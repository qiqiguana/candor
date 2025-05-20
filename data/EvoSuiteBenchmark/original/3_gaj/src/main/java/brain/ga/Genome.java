package brain.ga;

public class Genome implements Comparable
{
	protected Evaluator evaluator;
	private boolean evaluated = false;
	private double score;

	public Genome()
	{
	//	System.out.println("Constructor Genome");
	}
        public Evaluator getEvaluator()
        {
                return this.evaluator;
        }
	public void initialize()
        {
                //aici vor intra diverse chestiuni di intializare
        }
        public double getScore()
	{
		if (!evaluated)
		{
			score = evaluator.evaluate(this);
			evaluated = true;
		}
		return score;
	}

	public int compareTo (Object theGenome)
	{
		int valRet;
		Genome genome = (Genome) theGenome;
		if (this.getScore() > genome.getScore())
			valRet=1;
		else
			valRet = (this.getScore() == genome.getScore()? 0 : -1);
		return valRet;
	}

}