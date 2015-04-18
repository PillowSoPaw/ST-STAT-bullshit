package model;

import java.util.ArrayList;
import java.util.Random;
import org.apache.log4j.Logger;

public class Deck {
    static Logger logger = Logger.getLogger(Deck.class);
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Random random;
    private boolean useGuShuffle = false;

    public Deck() {
        random = new Random();
        init();
    }

    public Deck(long randomSeed) {
        random = new Random(randomSeed);
        init();
    }

    private void init() {
        clear();

        for (int i=0; i<Card.TOTAL_CARDS; i++) 
                addCard(new Card(i));

        logger.debug("cards: " + toString());
    }
    
    public void shuffle() {
        if (useGuShuffle)
                shuffle1();
        else shuffle2();
        logger.debug("shuffle: " + toString());
    }

    public void shuffle1() {
        for (int i=0; i<cards.size(); i++) {
                int subIdx = random.nextInt(cards.size());
                // switch places of a pair of cards
                swapCards(i, subIdx);
        }
    }

    public void shuffle2() {
        for (int i=cards.size()-1; i>0; i--) {
                int subIdx = random.nextInt(i+1);
                // switch places of a pair of cards
                swapCards(i, subIdx);
        }
    }

    public void swapCards(int idx1, int idx2) {
        if (idx1 == idx2)
                return;

        Card tmp = cards.get(idx1);
        cards.set( idx1, cards.get(idx2) );
        cards.set(idx2, tmp);
    }

    //get and remove a card from the start of the deck
    public Card removeHeadCard() {
        return cards.remove(0);
    }
    
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }
    //get and remove a card from the end of the deck
    public Card removeTailCard() {
        return ArrayListUtils.removeTail(cards);
    }

    //clear all cards from the deck
    public void clear() {
        cards.clear();
    }

    //add a card to a pile
    public void addCard(Card card) {
        cards.add(card);
    }

    //remove a card from a pile
    public void removeCard(Card card) {
        cards.remove(card);
    }

    //add cards from another deck
    public void addDeck(Deck o) {
        cards.addAll(o.cards);
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < cards.size(); i++)
                str += cards.get(i) + " ";

        return str;
    }
}
