package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import view.TableDrawPanel;

public class CardImages {
    BufferedImage [] cardImages;

    public CardImages() throws IOException {
        loadImages();
    }

    private void loadImages() throws IOException {
        cardImages = new BufferedImage[Card.TOTAL_CARDS];

        for (int i = 0; i < Card.TOTAL_CARDS; i++) {
            Card card = new Card(i);			
            Card.Suit suit = card.getSuit();
            String suffix = suit == Card.Suit.SPADE ? "spades" :
                            suit == Card.Suit.HEART ? "hearts" : 
                            suit == Card.Suit.DIAMOND ? "diamonds" : "clubs";
            int rank = card.getRank().getIntValue();

            String rankStr;
            if (rank == 14)
                rankStr = "a";
            else if (rank == 11)
                rankStr = "j";
            else if (rank == 12)
                rankStr = "q";
            else if (rank == 13)
                rankStr = "k";
            else rankStr = Integer.toString(rank);

            String fileName;
            fileName="images/" + suffix + "-" + rankStr + "-75.png";		
            URL fileUrl = TableDrawPanel.class.getClassLoader().getResource(fileName);

            cardImages[card.getIntValue()] = ImageIO.read(fileUrl);
        }
    }

    public BufferedImage getCardImage(Card card) {
        return cardImages[card.getIntValue()];
    }

    public int getWidth() {
        return cardImages[0].getWidth();
    }

    public int getHeight() {
        return cardImages[0].getHeight();
    }
}
