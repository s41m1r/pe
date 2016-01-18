/**
 * 

By replacing the 1st digit of the 2-digit number *3, 
it turns out that six of the nine possible values: 
13, 23, 43, 53, 73, and 83, are all prime.

By replacing the 3rd and 4th digits of 56**3 with the same digit, 
this 5-digit number is the first example having seven primes among 
the ten generated numbers, yielding the family: 
56003, 56113, 56333, 56443, 56663, 56773, and 56993. 
Consequently 56003, being the first member of this family, 
is the smallest prime with this property.

Find the smallest prime which, 
by replacing part of the number (not necessarily adjacent digits) 
with the same digit, is part of an eight prime value family.

 */
package p51;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import commons.CommonFunctions;
import commons.SieveOfAtkin;

/**
 * @author Saimir Bala
 *
 */
public class PrimeDigitReplacements {
	final static int SEARCH_SPACE = 1000000; //search space limit 
	private static final int FAMILY_SIZE = 8;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Set<Long> primes = new HashSet<Long>(SieveOfAtkin.primesList(SEARCH_SPACE));
		boolean found = false;
		List<Long> family = new ArrayList<Long>();
		Iterator<Long> it = primes.iterator();		
		while (!found && it.hasNext()) {
			Long p = it.next();
			int count = 0;
			int digits = (int) (Math.log10(p)+1);
			Set<BitSet> possibleReplacements = CommonFunctions.subsets(digits);
			for (Iterator<BitSet> it2 = possibleReplacements.iterator(); !found && it2
					.hasNext();) {
				family = new ArrayList<Long>();
				BitSet replacement = it2.next();
				count=0;
				if(replacement.cardinality()!=0 && replacement.cardinality()!=digits){
					//do max 10 replacements and check
					int i = 0;
					if(replacement.get(0))
						i++;
					for(; i<10; i++){
						Long num = replace(p, replacement, i);
						if(primes.contains(num)){
							count++;
							family.add(num);
						}
					}
					if(count==FAMILY_SIZE){
						found = true;
					}
				}
			}
		}
		System.out.println("Family="+family+"\nsize="+family.size());
		System.out.println("Time "+(System.currentTimeMillis()-start));
	}
	/**
	 * 
	 * @param p number to replace
	 * @param replacement what parts (digits) of the number to replace
	 * @param v what value for replacement (0 ... 9)
	 * @return the replaced number
	 */
	private static Long replace(Long p, BitSet replacement, int i) {
		String numString = p.toString();
		char[] res = new char[numString.length()];
		int setBitIdx = replacement.nextSetBit(0);
		for (int j = 0; j < res.length; j++) {
			if(j<setBitIdx || setBitIdx==-1)
				res[j] = numString.charAt(j);
			else{
				if(j==setBitIdx){
					res[j] = (""+i).charAt(0);
					setBitIdx = replacement.nextSetBit(setBitIdx+1);
				}
			}
		}
		return Long.valueOf(String.valueOf(res));
	}

}
