package brain.ga;

//The allele set class is a container for the different values that a gene may assume.

//If you call the allele member function with no argument,
//the allele set picks randomly from the alleles it contains
//and returns one of them
import java.util.*;
public class GAEnumAllelesSet
{
	private Random rnd = new Random();
        private Vector alleles;
	public GAEnumAllelesSet()
	{

	}
	public Object allele()
	{
                return alleles.get(rnd.nextInt(alleles.size()));
	}
	public Object allele(int i)
	{
		return alleles.get(i);
	}
        public void setAlleles(Vector newAlleles)
        {
                alleles = newAlleles;
        }
        public int size()
        {
                return alleles.size();
        }

}