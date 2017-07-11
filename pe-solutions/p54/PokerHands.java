/**
 * In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the following way:

	High Card: Highest value card.
	One Pair: Two cards of the same value.
	Two Pairs: Two different pairs.
	Three of a Kind: Three cards of the same value.
	Straight: All cards are consecutive values.
	Flush: All cards of the same suit.
	Full House: Three of a kind and a pair.
	Four of a Kind: Four cards of the same value.
	Straight Flush: All cards are consecutive values of same suit.
	Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
	The cards are valued in the order:
	2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace.

	If two players have the same ranked hands then the rank made up of the highest value wins; for example, a pair of eights beats a pair of fives (see example 1 below). But if two ranks tie, for example, both players have a pair of queens, then highest cards in each hand are compared (see example 4 below); if the highest cards tie then the next highest cards are compared, and so on.

	Consider the following five hands dealt to two players:

	Hand	 	Player 1	 				Player 2	 						Winner
	1	 	5H 5C 6S 7S KD Pair of Fives 2C 3S 8S 8D TD	Pair of Eights 			Player 2
	2	 	5D 8C 9S JS AC Highest card Ace 2C 5C 7D 8S QH Highest card Queen 	Player 1
	3	 	2D 9C AS AH AC
	Three Aces
	 	3D 6D 7D TD QD
	Flush with Diamonds
	 	Player 2
	4	 	4D 6S 9H QH QC
	Pair of Queens
	Highest card Nine
	 	3D 6D 7H QD QS
	Pair of Queens
	Highest card Seven
	 	Player 1
	5	 	2H 2D 4C 4D 4S
	Full House
	With Three Fours
	 	3C 3D 3S 9S 9D
	Full House
	with Three Threes
	 	Player 1
	The file, poker.txt, contains one-thousand random hands dealt to two players. Each line of the file contains ten cards (separated by a single space): the first five are Player 1's cards and the last five are Player 2's cards. You can assume that all hands are valid (no invalid characters or repeated cards), each player's hand is in no specific order, and in each hand there is a clear winner.

	How many hands does Player 1 win?
 */
package p54;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Saimir Bala
 *
 */
public class PokerHands {

	private static final Integer HIGH_CARD = 1;
	private static final Integer ONE_PAIR = 2;
	private static final Integer TWO_PAIRS = 3;
	private static final Integer TRIS = 4;
	private static final Integer STRAIGHT = 5;
	private static final Integer FLUSH = 6;
	private static final Integer FULL = 7;
	private static final Integer POKER = 8;
	private static final Integer STRAIGHT_FLUSH = 9;
	private static final Integer ROYAL_FLUSH = 10;

	static Map<String, Integer> numbersMap = new HashMap<String, Integer>();

	private static int deal(SortedSet<String> h1, SortedSet<String> h2) {
		SortedMap<String, Integer> h1countStats = new TreeMap<String, Integer>();
		SortedMap<String, Integer> h1seedStats = new TreeMap<String, Integer>();
		SortedMap<String, Integer> h2countStats = new TreeMap<String, Integer>();
		SortedMap<String, Integer> h2seedStats = new TreeMap<String, Integer>();

		fillInStats(h1, h1countStats, h1seedStats);
		fillInStats(h2, h2countStats, h2seedStats);

		return whoWins(h1, h1countStats, h1seedStats, h2, h2countStats, h2seedStats); 
	}

	private static void fillInStats(Set<String> h1,
			Map<String, Integer> countStats, Map<String, Integer> seedStats) {
		for (Iterator<String> it = h1.iterator(); it.hasNext();) {
			String e = it.next();
			String val = e.substring(0, 1);
			String seed = e.substring(1);
			insertIntoCountMap(countStats, val);
			insertIntoCountMap(seedStats, seed);
		}
	}

	private static void init(Map<String, Integer> points) {
		points.put("2",2);
		points.put("3",3);
		points.put("4",4);
		points.put("5",5);
		points.put("6",6);
		points.put("7",7);
		points.put("8",8);
		points.put("9",9);
		points.put("T",10);
		points.put("J",11);
		points.put("Q",12);
		points.put("K",13);
		points.put("A",14);
	}

	/**
	 * @param countStats
	 * @param val
	 */
	private static void insertIntoCountMap(Map<String, Integer> countStats,
			String val) {
		Integer valCount = 1;
		if(countStats.containsKey(val)){
			valCount = countStats.get(val)+1;
		}
		countStats.put(val, valCount);
	}

	private static Integer isPokerOrFull(SortedMap<String, Integer> countStats) {
		int firstK = countStats.get(countStats.firstKey());
		int lastK = countStats.get(countStats.lastKey());
		if(firstK == 4 || lastK == 4)
			return POKER;
		else 
			if(firstK == 3 || lastK == 3) return FULL;
		return 0;
	}

	private static boolean isTris(SortedMap<String, Integer> countStats) {
		Set<String> cards = countStats.keySet();
		for (String c : cards) {
			if(countStats.get(c) == 3)
				return true;
		}		
		return false;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		Scanner scanner = new Scanner(new File("data/p054_poker.txt"));
		SortedSet<String> h1 = new TreeSet<String>(); 
		SortedSet<String> h2 = new TreeSet<String>();
		int winsP1 = 0;
		int i = 0;
		while (scanner.hasNext()) {
			String string = (String) scanner.next();
			if(i<5){
				h1.add(string);
			}
			else
				h2.add(string);

			if(i==9){
				int winner = deal(h1,h2);
				if(winner==1)
					winsP1++;
				h1.clear();
				h2.clear();
				i=0;
				continue;
			}
			i++;
		}
		scanner.close();
		System.out.println("Time = "+(System.currentTimeMillis()-start)+" ms.");
		System.out.println("Player 1 wins "+winsP1+" games.");
	}

	/**
	 * 	1 High Card: Highest value card.
		2 One Pair: Two cards of the same value.
		3 Two Pairs: Two different pairs.
		4 Three of a Kind: Three cards of the same value.
		5 Straight: All cards are consecutive values.
		6 Flush: All cards of the same suit.
		7 Full House: Three of a kind and a pair.
		8 Four of a Kind: Four cards of the same value.
		9 Straight Flush: All cards are consecutive values of same suit.
		10 Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
		The cards are valued in the order:
		2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace.
	 * @param h1
	 * @param h1countStats
	 * @param h1seedStats
	 * @return
	 */
	private static Integer rank(SortedSet<String> hand,
			SortedMap<String, Integer> countStats, SortedMap<String, Integer> seedStats) {
		Integer rank = HIGH_CARD;
		boolean flush = false;
		boolean straight = false;

		if(seedStats.size()==1)//one color
			flush = true;
		
		if(countStats.size()==2){//cards are in partitioned into 2 sets
			return isPokerOrFull(countStats);
		}
		
		if(countStats.size()==3)//two pairs or tris		
			if(isTris(countStats))
				return TRIS;
			else 
				return TWO_PAIRS;

		if(countStats.size()==4)
			return ONE_PAIR;

		if(countStats.size()==5){//only five different
			//check if consecutive
			TreeSet<String> keys = new TreeSet<String>(countStats.keySet());
			boolean consecutive = true;

			init(numbersMap);
			
			ArrayList<Integer> numList = new ArrayList<Integer>();
			Iterator<String> it = keys.iterator();
			
			while (it.hasNext()) {
				numList.add(numbersMap.get(it.next()));
			}
			Object[] nums = numList.toArray();
			Arrays.sort(nums);
			
			int cur = (int) nums[0];
			int last = cur;
			
			for (int i = 1; consecutive && i < nums.length; i++) {
				cur = (int) nums[i];
				if(cur-last!=1)
					consecutive=false;
				last=cur;
			}
			
			if(consecutive){
				straight = true;
			}		

			if(straight&&flush){
				if((int)nums[0]==10)
					return ROYAL_FLUSH;
				return STRAIGHT_FLUSH;//straightflush
			}
			if(straight) return STRAIGHT;

			if(flush) return FLUSH;
		}

		return rank;
	}

	/**
	 * 
	 * @param h1 
	 * @param h1countStats
	 * @param h1seedStats
	 * @param h2 
	 * @param h2countStats
	 * @param h2seedStats
	 * @return 0 none, 1 player one, 2 player two
	 */
	private static int whoWins(SortedSet<String> h1, SortedMap<String, Integer> h1countStats,
			SortedMap<String, Integer> h1seedStats,
			SortedSet<String> h2, SortedMap<String, Integer> h2countStats, SortedMap<String, Integer> h2seedStats) {

		int winner = 0;

		int rankH1 = rank(h1, h1countStats, h1seedStats);
		int rankH2 = rank(h2,h2countStats,h2seedStats);

		if(rankH1>rankH2)
			return 1;

		else 
			if(rankH2>rankH1)
				return 2;

		//both hands same rank
		if(rankH1==ONE_PAIR || rankH1==TWO_PAIRS || rankH1==TRIS || rankH1==FULL || rankH1==POKER){
			winner=highestValueRank(h1countStats,h2countStats);
		}
		
		if(rankH1==FLUSH || rankH1==STRAIGHT || rankH1 == STRAIGHT_FLUSH || rankH1==ROYAL_FLUSH){
			winner=highestSum(h1,h2);
		}
		
		if(winner==0)
			return highestCard(h1countStats,h2countStats);
		
		return winner;
	}
	private static int highestSum(SortedSet<String> h1, SortedSet<String> h2) {
		Iterator<String> it = h1.iterator();
		int sumH1 = 0;
		int sumH2 = 0;
		while (it.hasNext()) {
			String s = it.next();
			sumH1+=numbersMap.get(s);
		}
		it = h2.iterator();
		while (it.hasNext()) {
			String s = it.next();
			sumH2+=numbersMap.get(s);
		}
		
		if(sumH1>sumH2)
			return 1;
		if(sumH2>sumH1)
			return 2;
		return 0;
	}

	/**
	 * 
	 * @param h1countStats
	 * @param h2countStats
	 * @return 0 tie, 1 if h1 has higher value, 2 otherwise
	 */
	private static int highestValueRank(
			SortedMap<String, Integer> h1countStats,
			SortedMap<String, Integer> h2countStats) {
		int valH1 = 0;
		int valH2 = 0;
		Set<String> keySet = h1countStats.keySet();
		for (String s : keySet) {
			int n = h1countStats.get(s);
			if(n>1)
				valH1+=Math.pow(numbersMap.get(s),n);
		}

		keySet = h2countStats.keySet();
		for (String s : keySet) {
			int n = h2countStats.get(s);
			if(n>1)
				valH2+=Math.pow(numbersMap.get(s),n);
		}
		if(valH1>valH2)
			return 1;
		if(valH2>valH1)
			return 2;
		return 0;
	}

	/**
	 * 
	 * @param h1countStats
	 * @param h2countStats
	 * @return 1 if h1, 2 if h2
	 */
	private static int highestCard(SortedMap<String, Integer> h1countStats, SortedMap<String, Integer> h2countStats) {
		int max1 = 0;
		int max2 = 0;
		Set<String> keys = h1countStats.keySet();
		for (String s : keys) {
			int n = numbersMap.get(s);
			if(n>max1)
				max1 = n;
		}
		keys = h2countStats.keySet();
		for (String s : keys) {
			int n = numbersMap.get(s);
			if(n>max2)
				max2 = n;
		}
		return (max1>max2)? 1:2;
	}
}
