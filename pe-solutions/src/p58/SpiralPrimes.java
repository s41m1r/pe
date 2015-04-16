/**
 * 
 */
package p58;

import java.util.Collection;

import commons.CommonFunctions;

/**
 * @author Saimir Bala
 *
 */
public class SpiralPrimes {

	static int primesFound = 0;
	static int oldLength = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		boolean found = false;
		Spiral spiral = new Spiral();
	
		while(!found){
			oldLength=spiral.getSpiral().size();
			spiral.wrapOneLayer();
			spiral.recomputeDiagonals();
			if(ratioPrimesOverDiagonals(spiral)<=0.1)
				found=true;
			
			System.out.println("length of the spiral = "+spiral.getSideLength()+ 
					" Percentage is "+ratioPrimesOverDiagonals(spiral));
		}
		
		System.out.println("length of the spiral = "+spiral.getSideLength()+ 
				" total num in diag = "+totalNumbersInDiagonals(spiral));

		long end = System.currentTimeMillis();
		System.out.println("Percentage is "+ratioPrimesOverDiagonals(spiral));
		System.out.println("It took "+(double)(end-start)/1000+" seconds.");
	}

	public static int totalNumbersInDiagonals(Spiral spiral){
		int len = spiral.getSideLength();
		if(len == 1)
			return 1;
		if(len%2 == 0)
			return 2*len;
		else 
			return 2*len - 1;
	}

	public static double ratioPrimesOverDiagonals(Spiral spiral){
		Collection<Number> diag1 = spiral.getPrincipalDiagonal();
		Collection<Number> diag2 = spiral.getSecondaryDigonal();
		countPrimes(diag1);
		countPrimes(diag2);
		return (primesFound)/(double)totalNumbersInDiagonals(spiral);
	}

	private static void countPrimes(Collection<Number> diag1) {
//		int count = 0;
		Number[] array = new Number[diag1.size()];
		diag1.toArray(array);
		
		for (int i = array.length-1; i>oldLength; i--) {
			if(CommonFunctions.isPrime(array[i]));
				primesFound++;
		}
	}

}
