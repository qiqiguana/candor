package brain.ga;

public class GAAlgorithm
{
	public GAAlgorithm()
	{
	}
	protected Selector selector;
	protected Evaluator evaluator;
        protected SexualCrossover crossoverOperator;
	protected Population population;
        protected Mutator mutator;
        protected GAEnumAllelesSet allelesSet;

        public void evolve()
	{
		initialize();
		while (!done())
		{
			step();
		}
	}
	// must be overriden
	protected void initialize()
	{
	}
	//must be overriden
	protected void step()
	{
	}
	//must be overriden
	protected boolean done()
	{
		return true;
	}

        public void setSelector(Selector newSelector)
	{
		selector = newSelector;
                population.setSelector(selector);
	}

	public Selector getSelector()
	{
		return selector;
	}
        public void setMutator(Mutator newMutator)
        {
                mutator = newMutator;
        }
        public Mutator getMutator()
        {
                return mutator;
        }
	public void setEvaluator(Evaluator newEvaluator)
	{
		evaluator = newEvaluator;
                population.setEvaluator(evaluator);
	}

	public Evaluator getEvaluator()
	{
		return evaluator;
	}

        public void setCrossoverOperator (SexualCrossover newCrossoverOperator)
        {
                crossoverOperator = newCrossoverOperator;
        }

        public SexualCrossover getCrossoverOperator()
        {
                return crossoverOperator;
        }

}
