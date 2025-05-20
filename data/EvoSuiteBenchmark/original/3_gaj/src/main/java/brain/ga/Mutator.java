package brain.ga;

public interface Mutator
{
	public int mutate(Genome genome, double pmut);

        public void setAllelesSet(GAEnumAllelesSet newAllelesSet);
}
