package p59;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class XORDecryption {
	
	final static int ALPHABET_START = 97;
	final static int ALPHABET_END = 122;
	final static double PRECISION = .9;

	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		Integer[] message = readMessage("data/p059_cipher.txt");
		Set<String> englishWords = readWordList("data/words.txt");
		ArrayList<Integer[]> keys = generateKeys();
		ArrayList<String> candidates = new ArrayList<String>();
		String k = "";
		long asciiValuesSum = 0;
		for (Integer[] key : keys) {
			Integer[] repeatedKey = repeatKey(key, message.length);
			Integer[] res = XOR(repeatedKey,message);
			boolean candidate = true;;
			String msg = "";
			for (int i = 0; i < res.length; i++) {
				int ascii = res[i].intValue();
				if(ascii<32 || ascii>126){
					candidate = false;
					break;//useless to go on
				}
				char ch = (char) ascii;
				msg+=ch;
			}
			if(candidate){
				String[] tokens = msg.split("[\\p{Punct}\\s]+");
				double precision = 0.0;
				for (String s : tokens) {
					if(englishWords.contains(s.toLowerCase())){
						precision+=1;
					}
				}
				precision=precision/tokens.length;
				if(precision>PRECISION){
					candidates.add(msg);
					k += ""+((char)key[0].intValue())+((char)key[1].intValue())+((char)key[2].intValue());
					break;//already found
				}
			}
		}
		
		if(candidates.size()==1){
			String msg = candidates.get(0);
			for (int i = 0; i < msg.length(); i++) {
				asciiValuesSum+=msg.charAt(i);
			}
		}
//		System.out.println("k="+k+", ascii sum = "+asciiValuesSum);
		System.out.println((k.equals("")));
		System.out.println("Time="+(System.currentTimeMillis()-start));
		
		//range 32 - 126; maybe enough: 97 - 122
	}

	private static Set<String> readWordList(String string) throws FileNotFoundException {
		Set<String> words = new HashSet<String>();
		Scanner scanner = new Scanner(new File(string));
		while (scanner.hasNext()) {
			String word = scanner.next();
			words.add(word.toLowerCase());
		}
		scanner.close();
		return words;
	}

	private static Integer[] XOR(Integer[] repeatedKey, Integer[] message) {
		Integer[] result = new Integer[message.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = repeatedKey[i] ^ message[i];
		}
		return result;
	}

	private static Integer[] repeatKey(Integer[] key, int length) {
		Integer[] repeatedKey = new Integer[length];
		for (int i = 0; i < repeatedKey.length; i++) {
			repeatedKey[i] = key[i%key.length];
		}
		return repeatedKey;
	}

	private static ArrayList<Integer[]> generateKeys() {
		ArrayList<Integer[]> res = new ArrayList<Integer[]>();
		for(int i=ALPHABET_START; i<=ALPHABET_END; i++){
			for (int j = ALPHABET_START; j <= ALPHABET_END; j++) {
				for (int k = ALPHABET_START; k <= ALPHABET_END; k++) {
					Integer[] key = {i, j, k};
					res.add(key);
				}
			}
		}
		return res;
	}

	private static Integer[] readMessage(String string) throws FileNotFoundException {
		ArrayList<Integer> asciiChars = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new File(string));
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			asciiChars.add(Integer.parseInt(scanner.next().trim()));
		}
		scanner.close();
		Integer[] msg = new Integer[asciiChars.size()];
		msg = asciiChars.toArray(msg);
		return msg;
	}

}
