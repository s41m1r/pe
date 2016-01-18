package commons;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonFunctions {
	
	public static boolean isPrime(Number number) {
		long n = number.longValue();
		if(n < 2) 
			return false;
		if(n == 2 || n == 3) 
			return true;
		if(n%2 == 0 || n%3 == 0) 
			return false;
		
		long sqrtN = (long)Math.sqrt(n)+1;
		for(long i = 6L; i <= sqrtN; i += 6) {
			if(n%(i-1) == 0 || n%(i+1) == 0) 
				return false;
		}
		return true;
	}
	
	/**
	 * Sieve of Eratosthenes
	 * @param n
	 * @return
	 */
	public static List<Integer> calcPrimeNumbers(int n) {
	    boolean[] isPrimeNumber = new boolean[n + 1]; //all false by default
	    List<Integer> primes = new ArrayList<Integer>();
	    for (int i = 2; i < n; i++) {
	      isPrimeNumber[i] = true;
	    }
	    for (int i = 2; i < n; i++) {
	      if (isPrimeNumber[i]) {
	        primes.add(i);
	        // now mark the multiple of i as non-prime number
	        for (int j = i; j * i <= n; j++) {
	          isPrimeNumber[i * j] = false;
	        }
	      }

	    }
	    return primes;
	  }
	
	public static <T> Set<Set<T>> generateAllSubsets(Set<T> original) {
        Set<Set<T>> allSubsets = new HashSet<Set<T>>();

        allSubsets.add(new HashSet<T>()); //Add empty set.

        for (T element : original) {
            // Copy subsets so we can iterate over them without ConcurrentModificationException
            Set<Set<T>> tempClone = new HashSet<Set<T>>(allSubsets);

            // All element to all subsets of the current power set.
            for (Set<T> subset : tempClone) {
                Set<T> extended = new HashSet<T>(subset);
                extended.add(element);
                allSubsets.add(extended);
            }
        }
        return allSubsets;
    }
	
	public static Set<BitSet> subsets(int n){
		Set<BitSet> result = new HashSet<BitSet>();
		for(int i=0; i<(2<<(n-1)); i++){
			BitSet bitSet = BitSet.valueOf(new long[]{i});
			result.add(bitSet);
		}
		return result;
	}
	
	public static void main(String[] args) {
		final int n = 14;
		
		Set<Integer> set = new HashSet<Integer>();
		for(Integer i = 0; i<n; i++){
			set.add(i);
		}
		
		long start = System.currentTimeMillis();
		Set<Set<Integer>> set1 = generateAllSubsets(set);
		long mid = System.currentTimeMillis(); 
		Set<BitSet> set2 = subsets(n);
		long end = System.currentTimeMillis();
		System.out.println("Combinations:");
		System.out.println("\tSet 1: "+set1.size()+"\t Set 2:"+set2.size());
		System.out.println();
//		System.out.println("\tSet 1: "+set1+"\n\tSet 2: "+set2);
		System.out.println("Times");
		System.out.println("\tSet 1: "+(mid-start)+"\t Set 2: "+(end-mid));
	}
	
	public static <T> Set<Set<T>> combinations(Set<T> original, int k){
		ArrayList<T> app = new ArrayList<T>(original);
		Set<BitSet> bitSets = subsets(original.size());
		Set<Set<T>> result = new HashSet<Set<T>>();
		for (BitSet bitSet : bitSets) {
			if(bitSet.cardinality()==k){
				Set<T> subset = new HashSet<T>();
				for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i+1)) {
					subset.add(app.get(i));
				}
				result.add(subset);
			}
		}
		return result;
	}
	
}
