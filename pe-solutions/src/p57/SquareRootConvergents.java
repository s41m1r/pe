/**
 * In the first one-thousand expansions, 
 * how many fractions contain a numerator with more digits than denominator?
 */
package p57;

import java.math.BigInteger;

/**
 * @author Saimir Bala
 *
 */
public class SquareRootConvergents {
	final static int EXPANSIONS_LIMIT = 1000;
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		int count = 0;
		BigInteger pPrevPrev = BigInteger.ZERO;
		BigInteger pPrev = BigInteger.ONE;
		for(int i=0;i<EXPANSIONS_LIMIT;i++){
			BigInteger p = pell(pPrev, pPrevPrev);
			BigInteger num = num(pPrev, pPrevPrev);
			pPrevPrev = pPrev;
			pPrev = p;
			String numsString = String.valueOf(num);
			String denString = String.valueOf(p);
			if(numsString.length()>denString.length()){
				count++;
//				System.out.println(num+"/"+p+" i="+i);
			}
		}
		System.out.println(count);
	}
	
	private static BigInteger pell(BigInteger pPrev, BigInteger pPrevPrev) {
		return (BigInteger.valueOf(2).multiply(pPrev)).add(pPrevPrev); 
	}
	
	private static BigInteger num(BigInteger pPrev, BigInteger pPrevPrev){
		return (BigInteger.valueOf(3).multiply(pPrev)).add(pPrevPrev);
	}
}
