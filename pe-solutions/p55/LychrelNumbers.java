/**
 * 
 */
package p55;

import java.math.BigInteger;

/**
 * 
 * 
 * @author Saimir Bala
 *
 */
public class LychrelNumbers {
	
	final static long THRESHOLD = 10000; //how many lychrel nums are there below 10K?
	final static long LYCHREL_MAX_ITERATIONS = 50; //after this we assume the number is Lychrel's
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long count = 0;
		for(long i=THRESHOLD; i>0; i--){
			if(isLychrel(i))
				count++;
		}
		System.out.println("There are "+count+" Lychrel numbers under "+THRESHOLD);
		System.out.println("Time="+(System.currentTimeMillis()-start));
	}
	
	/**
	 * A number is Lychrel's if it becomes palindrome in less than fifty iterations
	 * @param i
	 * @return true
	 */
	private static boolean isLychrel(long i) {
		BigInteger n=BigInteger.valueOf(i);
		for(long j=0; j<LYCHREL_MAX_ITERATIONS; j++){
			BigInteger sum = n.add(reverse(n));
			if(isPalindromic(sum))
				return false;
			n=sum;
		}
		return true;
	}

	private static boolean isPalindromic(BigInteger sum) {
		String s = String.valueOf(sum);
		char[] chars = s.toCharArray();
		int i=0;
		int j=0;
		for(i=0,j=s.length()-1; i<=s.length()/2;i++,j--){
			if(chars[i] != chars[j])
				return false;
		}
		return true;
	}

	private static BigInteger reverse(BigInteger n) {
		String s = String.valueOf(n);
		String reverse = "";
		for(int i=s.length()-1; i>=0;i--)
			reverse += s.charAt(i); 
		return new BigInteger(reverse);
	}

}
