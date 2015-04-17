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
import model.Score;

public class TableDrawPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	Dealer dealer; 
	CardImages cardImages;
	
	public TableDrawPanel() throws IOException {
		// init images
		
		cardImages = new CardImages();
		
		// margin: 10
		// width for 9 hands
		
		setPreferredSize(new Dimension( 10 + 125 * 8 + 25 + cardImages.getWidth() + 10, 
				10 + 150 + cardImages.getHeight() + 10 + 10));
	}
	
	@Override
    public void paint(Graphics g) {
		super.paint(g);
    	
    	if (dealer != null) {
    		// draw the cards
    		
    		Card [] cards = dealer.getTableCards();
    		for (int i = 0; i < 5; i++) {
    			if ( cards[i] != null )
    				g.drawImage( cardImages.getCardImage(cards[i]), 100 + 100*i, 10, null);
    		}
    		
    		Card [][] handCards = dealer.getHandCards();
    		int xpos = 10;
    		
    		for (int i = 0; i<dealer.getHandsTotal(); i++) {
    			for(int j = 0; j < 13; j++){
    			if (handCards[i][j] != null)
    				g.drawImage( cardImages.getCardImage(handCards[i][j]), xpos, 150, null);
    			xpos += 75;
    			}
    		}
    		
    		if (dealer.isScored()) {
    			// draw scores and ranks
    			Font font = new Font("Dialog", Font.BOLD, 12); 
    			g.setFont(font);
    			
        		xpos = 10;
				
    			for (int i = 0; i<dealer.getHandsTotal(); i++) {
    				Score score = dealer.getScoreByHand(i);
    				int rank = dealer.getRankByScore(score);
    				
    				if (rank == 1)
    					g.setColor(Color.RED);
    				else g.setColor(Color.BLACK);
    				
    				String str = Integer.toString(rank) + ", " + score.getPattern().toString().toUpperCase();
    				g.drawString(str, xpos, 275);
    				
    				xpos += 125;
    			}
    		}
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
