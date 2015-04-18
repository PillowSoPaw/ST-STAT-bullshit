package model;

public class Card implements Comparable<Card> {
    public static final int TOTAL_CARDS = 52;
    private Rank rank;
    private Suit suit;

    public enum Suit {
        CLUB(0, 'C'), DIAMOND(1, 'D'),
        HEART(2, 'H'), SPADE(3, 'S');

        private final int intValue;
        private final Character symbol;
        private static final Suit [] INT_SUIT_MAP = {SPADE, HEART, DIAMOND, CLUB};

        Suit(int intValue, Character symbol) {
            this.intValue = intValue;
            this.symbol = symbol;
        }

        public int getIntValue() {
            return intValue;
        }

        public Character getSymbol() {
            return symbol;
        }

        static public Suit getSuit(int suit) {
            return INT_SUIT_MAP[suit];
        }
    }

    public enum Rank {
        TWO(2, '2'), THREE(3, '3'), FOUR(4, '4'), FIVE(5, '5'),
        SIX(6, '6'), SEVEN(7, '7'), EIGHT(8, '8'), NINE(9, '9'),
        TEN(10, 'T'), JACK(11, 'J'), QUEEN(12, 'Q'), KING(13, 'K'),
        ACE(14, 'A');

        private final int intValue;
        private final Character symbol;

        Rank(int intValue, Character symbol) {
            this.intValue = intValue;
            this.symbol = symbol;
        }

        public int getIntValue() {
            return intValue;
        }

        public Character getSymbol() {
            return symbol;
        }

        private static final Rank [] INT_RANK_MAP = {null, ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
                EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}; 

        public static Rank getRank(int intValue) {
            return INT_RANK_MAP[intValue];
        }
    }

    //auto generates ace of spades
    public Card() {
        rank = Rank.ACE;
        suit = Suit.SPADE;
    }

    //constructs a card given a rank and a suit
    public Card(int rank, int suit) {
        this.rank = Rank.getRank(rank);
        this.suit = Suit.getSuit(suit);
    }

    //constructs a card given an int from 0-51
    public Card(int intValue) {
        rank = Rank.getRank( intValue%13 + 1 );
        suit = Suit.getSuit( intValue/13 );
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    
    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getIntValue() {
        int rankInt = rank.intValue - 1;

        if (rankInt == 13)
                rankInt = 0;

        return suit.intValue * 13 + rankInt;
    }

    @Override
    public String toString() {
        return rank.getSymbol().toString() + suit.getSymbol();
    }

    @Override
    public int compareTo(Card o) {
        int c = rank.compareTo(o.rank);

        if (c!=0)
            return c;
        else return suit.compareTo(o.suit);
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;

        if (rank == card.rank && suit == card.suit)
           return true;
        else return false;
    }
}
