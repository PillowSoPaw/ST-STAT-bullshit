package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import model.Card;
import model.Card.Rank;
import model.Dealer;
import model.Player;

import org.apache.log4j.Logger;

import javax.swing.JLabel;

public class DealPanel extends javax.swing.JPanel {

	static Logger logger = Logger.getLogger(DealPanel.class); 
	Player[] players;
        Dealer dealer;
        Card[] discardPile;
        Rank currRank;
        ArrayList<Rank> playedRanks = new ArrayList<>();
        Random nRand;
        JLabel lblRank = new JLabel("Rank");
        int currDiscardCount = 0;
        
    /** Creates new form dealPanel 
     * @throws IOException */
    public DealPanel() throws IOException {
        initComponents();
        
        ////////////////////
        // custom init
        
        // a default dealer
        dealer = new Dealer();
        dealer.addObserver(drawPanel);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() throws IOException {

        drawPanel = new TableDrawPanel();
        handsComboBox = new javax.swing.JComboBox();
        btnAddCard = new javax.swing.JButton();

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );

        handsComboBox.setModel(new DefaultComboBoxModel(new String[] {"SPADE ", "HEART ", "DIAMOND ", "CLUB"}));
        handsComboBox.setSelectedIndex(0);

        btnAddCard.setText("Call Bluff!");
        btnAddCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					dealButtonActionPerformed(evt);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        JComboBox rankComboBox = new JComboBox();
        rankComboBox.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "A"}));
        
        JButton btnRemoveCard = new JButton("Put Down Card");
        btnRemoveCard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println(rankComboBox.getSelectedIndex()+"hai"+handsComboBox.getSelectedIndex());
        		Card tempcard = new Card(rankComboBox.getSelectedIndex()+2, handsComboBox.getSelectedIndex());
        		dealer.removeCard(tempcard,players[0]);
        		
        		drawPanel.command =2;
        		//drawPanel.currCards--;
            	repaint();
        	}
        });
        
        JLabel lblCurrentRank = new JLabel("Current rank: ");
        
       
        
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblCurrentRank)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(lblRank)
        			.addPreferredGap(ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
        			.addComponent(rankComboBox, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(handsComboBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnRemoveCard, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnAddCard, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        		.addComponent(drawPanel, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(drawPanel, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(btnAddCard)
        					.addComponent(btnRemoveCard)
        					.addComponent(handsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(rankComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(lblCurrentRank)
        					.addComponent(lblRank)))
        			.addContainerGap())
        );
        this.setLayout(layout);
    }// </editor-fold>

    private void dealButtonActionPerformed(java.awt.event.ActionEvent evt) throws InterruptedException {
        drawPanel.command = 1;
    	repaint();
    }
    
    // Variables declaration - do not modify
    private javax.swing.JButton btnAddCard;
    private TableDrawPanel drawPanel;
    private javax.swing.JComboBox handsComboBox;
    // End of variables declaration

    int dealState = 0;
    
    public String suitConvert(String suit){
    	switch(suit){
    	case "Heart":
    		return "H";
    	case "Spade":
    		return "S";
    	case "Club":
    		return "C";
    	default:
    		return "D";
    	}
    }
    
    public String rankConvert(String rank){
    	switch(rank){
    	case "Jack":
    		return "J";
    	case "Queen":
    		return "Q";
    	case "King":
    		return "K";
    	default:
    		return rank;
    	}
    }
    
 
    /**
     * deal once
     */
    public void deal() {
    	if (dealer.isCompleted() && !dealer.isScored()) {
    		//dealer.scoreHands();
    	} else {
    		dealer.deal();
    	}
    	
    	dealer.notifyObservers();
    	logger.debug("deal: " + dealer.toString());
    }
    
        public void getPlayers()
        {
            this.players = dealer.getAllPlayers();
        }
        
        public Rank getCurrPlayRank()
        {
            nRand = new Random();
            if(playedRanks.size() == 13)
            {
                playedRanks.clear();
            }
            
            Rank tempRank;
            do{
            int randomValue = nRand.nextInt(65536) % 14;
            System.out.println(randomValue);
            tempRank = Rank.getRank(randomValue);
            }while(playedRanks.contains(tempRank));
            playedRanks.add(tempRank);
           // System.out.println(tempRank.toString());
            lblRank.setText(tempRank.toString());
            return tempRank;
        }
        
        public void playRound()
        {
            
        }
        
        public void startGame()
        {
            
            int i = 0;
            while(!players[0].isWinner() && !players[1].isWinner() && !players[2].isWinner() && !players[3].isWinner())
            {
                currRank = getCurrPlayRank();
                break;
            }
           
        }
        
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
		dealer.addObserver(drawPanel);
	}
    
	public Dealer getDealer() {
		return dealer;
	}

	////////////////////////////////
	// test
	
	public static void main(String[] args) throws InterruptedException, IOException {
		final JFrame jFrame = new JFrame();;
		final DealPanel dealPanel = new DealPanel();
		
		jFrame.getContentPane().add(dealPanel);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				jFrame.pack();
//				jFrame.setVisible(true);
//			}
//		});
		
		// animation 
		// deal, flop, turn, river, score
                jFrame.pack();
                jFrame.setVisible(true);
                
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					dealPanel.deal();
                                        dealPanel.getPlayers();
                                        dealPanel.startGame();
                                        
				}
			});	
	}
}
