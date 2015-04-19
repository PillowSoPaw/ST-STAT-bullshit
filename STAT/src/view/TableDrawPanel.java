package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Card;
import model.CardImages;
import model.Dealer;
import model.Player;
import model.Score;

public class TableDrawPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	Dealer dealer; 
	CardImages cardImages;
	Player player;
	//used for telling the paint() what to do once repaint is called.
	int command;
	//the current cards the player has
	//int currCards = 13;
	
	public TableDrawPanel() throws IOException {
		// init images
		
		cardImages = new CardImages();
		
		// margin: 10
		// width for 9 hands
		
		setPreferredSize(new Dimension(990, 373));
	}
	Card [] handCards;
	String cardToRemove;
	int xpos = 10;
	@Override
    public void paint(Graphics g) {
		super.paint(g);
    	
    	if (dealer != null) {
    		// draw the cards
    		player = dealer.getHuman();
                //this function displays the 5 cards for flop, river, etc. (FOR POKER)
    		//Card [] cards = player.getHand();
//    		for (int i = 0; i < 5; i++) {
//    			if ( cards[i] != null )
//    				g.drawImage( cardImages.getCardImage(cards[i]), 100 + 100*i, 10, null);
//    		}
    		
    		handCards = player.getHand();
    	
    			for(int j = 0; j < handCards.length; j++){
    			if (handCards[j] != null)
    				g.drawImage( cardImages.getCardImage(handCards[j]), xpos, 150, null);
    			xpos += 25;
    			}
    		
    		xpos = 10;
    	}
    	
    	if(command == 1){
    		//note that my method refreshes the whole hand instead of adding the card.
                player = dealer.getHuman();
    		handCards = player.getHand();
    			for(int j = 0; j < handCards.length; j++){
    			if (handCards[j] != null)
    				g.drawImage( cardImages.getCardImage(handCards[j]), xpos, 150, null);
    			xpos += 25;
    			}
    		
    		xpos = 10;
    	}
    	
    	if(command == 2){
    		//handCards = dealer.getHandCards();
                player = dealer.getHuman();
        	handCards = player.getHand();
			for(int j = 0; j < handCards.length; j++){
			if (handCards[j] != null)
				g.drawImage( cardImages.getCardImage(handCards[j]), xpos, 150, null);
			xpos += 25;
			}
		
		xpos = 10;
    	}
    	
    }

	@Override
	public void update(Observable o, Object arg) {
		dealer = (Dealer) o;
		repaint();
	}
	
	public Dealer getDealer() {
		return dealer;
	}
}
