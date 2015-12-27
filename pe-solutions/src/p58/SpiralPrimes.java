/**
 * 
 */
package p58;

import commons.CommonFunctions;

/**
 * @author Saimir Bala
 *
 */
public class SpiralPrimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		double ratioPrimesOverTotal = 1;
		int primes = 0;
		int sideLength = 1;
		
		for(sideLength=1; ratioPrimesOverTotal>.1; sideLength+=2){
			int spiralLength = (sideLength+2)*(sideLength+2);
			for(int i = sideLength*sideLength+1; i<=spiralLength; i++){
				if(isDiagonalMember(sideLength+2, i)){
					if(CommonFunctions.isPrime(i)){
						primes++;
					}
				}
			}
			ratioPrimesOverTotal = primes/(double)totalNumbersInDiagonals(sideLength+2);
		}
		long end = System.currentTimeMillis();
		System.out.println("side length = "+sideLength);
		System.out.println("primes="+primes);
		System.out.println("total nums = "+totalNumbersInDiagonals(sideLength));
		System.out.println("Percentage is "+ratioPrimesOverTotal);
		System.out.println("It took "+(end-start)+" millisecondss.");
	}

	private static boolean isDiagonalMember(int sideLength, int num) {
		return (num-(sideLength-2)*(sideLength-2))%(sideLength-1)==0;
	}

	public static int totalNumbersInDiagonals(int len){
		if(len == 1)
			return 1;
		if(len%2 == 0)
			return 2*len;
		else 
			return 2*len - 1;
	}
}
