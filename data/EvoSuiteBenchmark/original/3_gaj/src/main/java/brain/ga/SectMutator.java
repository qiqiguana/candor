package brain.ga;

import java.util.*;

public class SectMutator implements Mutator
{
        private Random rnd = new Random();
        private GAEnumAllelesSet allelesSet;
        public void setAllelesSet(GAEnumAllelesSet newAllelesSet)
        {
                allelesSet = newAllelesSet;
        }
        public int mutate(Genome genome, double pmut)
        {
                /**@todo: Implement this ga.Mutator method*/
                //throw new java.lang.UnsupportedOperationException("Method mutate() not yet implemented.");
                double p;
                VectorGenome vGenome = (VectorGenome) genome;
                for (int i = 0; i < vGenome.getGenesCount(); i++)
                {
                        p = rnd.nextDouble();
                        if (p <= pmut)
                        {
                                vGenome.setGene(i,allelesSet.allele());
                        }
                }
                return 0;
        }
}