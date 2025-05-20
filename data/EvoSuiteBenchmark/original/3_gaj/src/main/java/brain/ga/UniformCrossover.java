package brain.ga;

import java.util.*;

public class UniformCrossover implements SexualCrossover
{
        private Random rnd;
        public UniformCrossover()
        {
                rnd = new Random();
        }
        public Genome cross(Genome mom, Genome dad)
        {
                VectorGenome vSon;
                Vector sonGenes = new Vector();
                VectorGenome vMom = (VectorGenome) mom;
                VectorGenome vDad = (VectorGenome) dad;
                for (int i = 0; i < vMom.getGenesCount(); i++)
                {
                     sonGenes.addElement(rnd.nextBoolean()?vMom.getGene(i):vDad.getGene(i));
                }
                vSon = new VectorGenome(sonGenes, vMom.getEvaluator());
                return (Genome) vSon;

        }
}