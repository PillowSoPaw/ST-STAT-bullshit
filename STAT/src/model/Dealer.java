package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;

import org.apache.commons.lang3.ArrayUtils;

public class Dealer extends Observable {
	private Deck deck;
	private int handsTotal;
        private Player[] players = new Player[4];
        private Card[] discardPile;
	private DealerState dealerState = DealerState.CLEAR; 
	private boolean scored = false;
	private ArrayList<Score> scoreList = new ArrayList<Score>();
	private HashMap<Integer, Score> handToScoreMap = new HashMap<Integer, Score>();
	private HashMap<Score, Integer> scoreToHandMap = new HashMap<Score, Integer>();
	private HashMap<Score, Integer> scoreToRankMap = new HashMap<Score, Integer>();
	private HashMap<Score, Boolean> scoreToTiedMap = new HashMap<Score, Boolean>();
	
	int currCards = 13;

	enum DealerState {
		CLEAR,
		PREFLOP,
		FLOP,
		TURN,
		RIVER;
	}
	
	public Dealer() {
                players[0] = new Player();
                players[1] = new Player();
                players[2] = new Player();
                players[3] = new Player();
		deck = new Deck();
		setHandsTotal(1);
	}
	
	public Dealer(long randSeed) {
		// default deck
		deck = new Deck(randSeed);
		// the number of hands to be dealt
		setHandsTotal(1);
	}	

	/**
	 * performs one step of the card dealing process
	 */
	public void deal() {
		switch (dealerState) {
		
		case CLEAR:
			newDeal();
			break;
		}
	}
	
	private void newDeal() {
		deck.shuffle();
		dealHands();
		dealerState = DealerState.PREFLOP;
		setChanged();
	}
	
	private void dealHands() {
            int i = 0;
		while (!deck.isEmpty()) {
                    if(i == 4)
                        i = 0;
                    players[i].addCardToHand(deck.removeTailCard());
                    i++;
		}
                System.out.println("ALL CARDS DEALT");
                
                for(i = 0; i < players.length; i++){
                System.out.println("Player: " + i);
                players[i].displayEntireHand();
                }
		setChanged();
	}
	
        public Player[] getAllPlayers()
        {
            return this.players;
        }
        
	public void addCard(Player player, Card card)
        {
		player.addCardToHand(deck.removeTailCard());
	}
	
	public void removeCard(Card card, Player player)
        {	
		player.removeCardFromHand(card);
	}
        
	public void clear() {
		for (int i = 0; i < getHandsTotal(); i++) {
		//	Arrays.fill(handCards[i], null);
		}

		//Arrays.fill(tableCards, null);
		
		dealerState = DealerState.CLEAR;
		scored = false;
		
		setChanged();
	}
	
	@Override
	public String toString() {	
		String str = "";
		
		//for (int i=0; i<5; i++) {
		//	if (tableCards[i]!=null)
		//		str += tableCards[i].toString() + ' ';
		//}
		
		String NL = System.getProperty("line.separator");
		str += NL;
		
		
	//			str += (i+1) + ": " + handCards[0].toString() + ' ';
	//			str += handCards[1].toString() + "  ";
			
		
		
		return str;
	}
	
	public void setHandsTotal(int hands) {
		this.handsTotal = hands;
		//handCards = new Card[52];
	}
	
	public boolean isCompleted() {
		return dealerState == DealerState.RIVER;
	}
	
	public void scoreHands() {
		scoreList.clear();
		Evaluator evaluator = new Evaluator();
		for (int i=0; i<getHandsTotal(); i++) {
			
			Card [] cards = getCombinedCards(i);

			Score score = evaluator.evalulate(cards);
			scoreList.add( score );
			
			handToScoreMap.put(i, score);
			scoreToHandMap.put(score, i);
		}
		
		// sort the scores
		Collections.sort(scoreList);
		
		// rank the scores
		
		boolean equalPrevious = false;
		boolean equalNext = false;
		int equalRank = 1;
		Score score;
		Score scoreNext;
		
		for (int i=0; i<getHandsTotal(); i++) {
			
			int rank = i+1;
			score = scoreList.get(getHandsTotal()-i-1);
			
			equalPrevious = equalNext;
			
			// compare with next score to detect tie
			if (getHandsTotal()-i-1 != 0) {
				
				scoreNext = scoreList.get(getHandsTotal()-i-2);
				
				if (score.equals(scoreNext)) {
					// tied with next hand

					// set equal rank, if the starting rank of the tie
					if (!equalPrevious)
						equalRank = rank;

					equalNext = true;
					
				} else {
					equalNext = false;
					
				}
				
			} else
				equalNext = false;
			
			if (equalPrevious)	{
				// mark from previous hand
				rank = equalRank;
			}
			
			scoreToRankMap.put(score, rank);
			scoreToTiedMap.put(score, equalPrevious || equalNext);
		}
		
		scored = true;
		setChanged();
	}
	
	private Card [] getCombinedCards(int hand) {
		Card [] cards = new Card[7];
		
		//for (int j=0; j<5; j++) 
		//	cards[j] = getTableCards()[j];
			
//		for (int j=0; j<13; j++) 
//			cards[5+j] = getHandCards()[j];
		
		return cards;
	}
	
	////////////////////////////////
	// getters and setters
	
	public Deck getDeck() {
		return deck;
	}
        
        public Player getHuman()
        {
            return players[0];
        }
        
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getHandsTotal() {
		return handsTotal;
	}

	public Card[] getDiscardPile() {
		return discardPile;
	}

	public void setDiscardPile(Card[] handCards) {
		this.discardPile = handCards;
	}
        
        public void giveDiscardPileToPlayer(Player player)
        {
            for(int i = 0; i < this.discardPile.length; i++){
            player.addCardToHand(this.discardPile[i]);
            }
            ArrayUtils.removeAll(this.discardPile);
            
        }
//	public Card[] getTableCards() {
//		return tableCards;
//	}
//
//	public void setTableCards(Card[] tableCards) {
//		this.tableCards = tableCards;
//	}

	public DealerState getDealerState() {
		return dealerState;
	}

	// scoreList is sorted according to natural order
	public ArrayList<Score> getScoreList() {
		return scoreList;
	}
	
	public Score getScoreByHand(int hand) {
		return handToScoreMap.get(hand);
	}
	
	public int getHandByScore(Score score) {
		return scoreToHandMap.get(score);
	}
	
	public int getRankByScore(Score score) {
		return scoreToRankMap.get(score);
	}
	
	public boolean isTied(Score score) {
		return scoreToTiedMap.get(score);
	}
	
	public boolean isScored() {
		return scored;
	}

	/////////////////////////////////////////////////
	// test
	
	public static void main(String[] args) {
		Dealer dealer = new Dealer();
		
		dealer.deal();
		
		//System.out.println(dealer.toString());
		
		// evaluate and rank each hand *remove this shit after the final product*
		//dealer.scoreHands();
		
//		ArrayList<Score> scoreList = dealer.getScoreList();
//
//		for (int i=0; i<dealer.getHandsTotal(); i++) {
//			
//			Score score = scoreList.get(dealer.getHandsTotal()-i-1);
//			
//			int hand = dealer.getHandByScore(score);
//			int rank = dealer.getRankByScore(score);
//			boolean isTied = dealer.isTied(score);
//			
//			System.out.println("rank: " + rank + ", hand: " + (hand+1) + 
//					", score: " + score.toString() + ( isTied? " (tied)" : "") );
//		}	
	}
}
