/**
 * @Credits to http://compoasso.free.fr/primelistweb/page/prime/atkin_en.php 
 */
package commons;

import java.util.ArrayList;
import java.util.List;

public class SieveOfAtkin {
	
	public static List<Long> primesList(long MAX) {
		final long SQRT_MAX = (long) Math.sqrt(MAX) + 1;
		final int MEMORY_SIZE = (int) (MAX >> 4);
		byte[] array = new byte[MEMORY_SIZE];
		
		List<Long> primes = new ArrayList<Long>();

		// Find prime
		int[] sequence = { 2, 4 };
		int index = 0;
		long k1 = 0, k = 0;

		double xUpper = Math.sqrt(MAX / 4) + 1;
		long x = 1;
		long y = 0;

		while (x < xUpper) {
			index = 0;
			k1 = 4 * x * x;
			y = 1;
			if (x % 3 == 0) {
				while (true) {
					k = k1 + y * y;
					if (k >= MAX) {
						break;
					}
					toggleBit(k, array);
					y += sequence[(++index & 1)];
				}
			} else {
				while (true) {
					k = k1 + y * y;
					if (k >= MAX) {
						break;
					}
					toggleBit(k, array);
					y += 2;
				}
			}
			x++;
		}

		xUpper = Math.sqrt(MAX / 3) + 1;
		x = 1;
		y = 0;

		while (x < xUpper) {
			index = 1;
			k1 = 3 * x * x;
			y = 2;
			while (true) {
				k = k1 + y * y;
				if (k >= MAX) {
					break;
				}
				toggleBit(k, array);
				y += sequence[(++index & 1)];
			}
			x += 2;
		}

		xUpper = SQRT_MAX;
		x = 1;
		y = 0;

		while (x < xUpper) {
			k1 = 3 * x * x;
			if ((x & 1) == 0) {
				y = 1;
				index = 0;
			} else {
				y = 2;
				index = 1;
			}
			while (y < x) {
				k = k1 - y * y;
				if (k < MAX) {
					toggleBit(k, array);
				}
				y += sequence[(++index & 1)];
			}
			x++;
		}
		
		setBit(2, array);
		setBit(3, array);
		for (int n = 5; n <= SQRT_MAX; n += 2) {
			if (getBit(n, array)) {
				int n2 = n * n;
				for (k = n2; k < MAX; k += (2 * n2)) {
					unSetBit(k, array);
				}
			}
		}

		// Put primes into result list
		primes.add(2L);
		for (long i = 3; i < MAX; i += 2) {
			if (getBit(i, array)) {
				primes.add(i);
			}
		}
		return primes;
	}

	private static boolean getBit(long i, byte[] array) {
		byte block = array[(int) (i >> 4)];
		byte mask = (byte) (1 << ((i >> 1) & 7));

		return ((block & mask) != 0);
	}

	private static void setBit(long i, byte[] array) {
		int index = (int) (i >> 4);
		byte block = array[index];
		byte mask = (byte) (1 << ((i >> 1) & 7));

		array[index] = (byte) (block | mask);
	}

	private static void unSetBit(long i, byte[] array) {
		int index = (int) (i >> 4);
		byte block = array[index];
		byte mask = (byte) (1 << ((i >> 1) & 7));

		array[index] = (byte) (block & ~mask);
	}

	private static void toggleBit(long i, byte[] array) {
		int index = (int) (i >> 4);
		byte block = array[index];
		byte mask = (byte) (1 << ((i >> 1) & 7));

		array[index] = (byte) (block ^ mask);
	}
}