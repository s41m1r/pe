/**
 * 	The primes 3, 7, 109, and 673, are quite remarkable. 
	By taking any two primes and concatenating them in any order 
	the result will always be prime. 
	For example, taking 7 and 109, both 7109 and 1097 are prime. 
	The sum of these four primes, 792, represents the lowest sum 
	for a set of four primes with this property.
	
	Find the lowest sum for a set of five primes for which 
	any two primes concatenate to produce another prime.
 */
package p60;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import commons.CommonFunctions;
import commons.SieveOfAtkin;

/**
 * @author Saimir Bala
 *
 */
public class PrimePairSets {
	
	final static long SEARCH_SPACE = 10000000; //search space limit 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.print("Generating sieve ... ");
		List<Long> primeList = SieveOfAtkin.primesList(SEARCH_SPACE);
		Long maxPrimeInList = primeList.get(primeList.size()-1);
		System.out.println("Done. Max prime is "+maxPrimeInList);
		Set<Long> primes = new HashSet<Long>(primeList);
		Set<Long> result = new HashSet<Long>();
		List<Long> candidates = getCandidates(primeList, primes, 100);
		System.out.println(candidates);
		result.add(3L);
		result.add(7L);
		result.add(109L);
		result.add(673L);
		int timesFound = 0;
		for (Iterator<Long> it = primeList.iterator(); it.hasNext();) {
			Long x = it.next();
			timesFound = 0;
			if(result.contains(x))
				continue;
			for (Long r : result) {
				Long prepend = Long.valueOf(x+""+r);
				Long append = Long.valueOf(r+""+x);
				if(append > maxPrimeInList || prepend > maxPrimeInList){
					if(CommonFunctions.isPrime(prepend) && CommonFunctions.isPrime(append))
						timesFound++;
				}
				else{
					if(primes.contains(prepend) && primes.contains(append)){
						timesFound++;
					}
				}
			}
			if(timesFound==result.size()){
				result.add(x);
				System.out.println("Found it! It is "+x);
				break;
			}
		}		
		System.out.println(sum(result)+" res="+result);
		System.out.println("Time "+(System.currentTimeMillis()-start));
	}

	private static List<Long> getCandidates(List<Long> primeList, Set<Long> primes, int maxCandidates) {
		Set<Long> candidates = new TreeSet<Long>();
		for (int i = 0; candidates.size()<maxCandidates && i < primeList.size(); i++) {
			for (int j = i+1; candidates.size()<maxCandidates && j < primeList.size(); j++) {
				Long prepend = Long.valueOf(primeList.get(i)+""+primeList.get(j));
				Long append = Long.valueOf(primeList.get(j)+""+primeList.get(i));
				if(primes.contains(prepend) && primes.contains(append)){
					candidates.add(primeList.get(i));
					candidates.add(primeList.get(j));
					candidates.add(prepend);
					candidates.add(append);
				}
			}
		}
		
		return new ArrayList<Long>(candidates);
	}

	private static long sum(Set<Long> result) {
		long sum = 0;
		for (Long integer : result) {
			sum+=integer;
		}
		return sum;
	}

}
