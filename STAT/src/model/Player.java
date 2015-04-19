/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Seaver
 */
public class Player {
    private Card[] handCards;
    private boolean isWinner;
    private int index;
    public int handSize;
    private boolean isTurn;
    
    public Player(){
        isWinner = false;
        handCards = new Card[52];
        index = 0;
        handSize = 0;
    }
    
    public void addCardToHand(Card card)
    {
        handCards = ArrayUtils.add(handCards, index, card);
        index++;
        handSize++;
        System.out.println(card.toString() + " added!");
    }
    
    public void removeCardFromHand(Card card)
    {
    	
    	for(int i = 0; i<handSize; i++){
    		if(handCards[i].toString().equals(card.toString())){
    			handCards = ArrayUtils.remove(handCards,i);
    	        handSize--;
    	        index--;
    		}
    	}
        
    }
    
    public boolean isHandEmpty()
    {
        return handCards.length == 0;
    }
    
    public boolean isWinner()
    {
        return isWinner;
    }
    
    public Card[] getHand()
    {
        return this.handCards;
    }
    
    public int handSize()
    {
        return handSize;
    }
    
    public void displayEntireHand()
    {
        if(!isHandEmpty()){
        for(int i = 0; i < handSize; i++)
            System.out.println(handCards[i].toString());
        }else
        {
            System.out.println("HAND EMPTY");
        }
    }
    
    public Card getCardByIndex(int i)
    {
        return this.handCards[i];
    }
    
    public void makeWin()
    {
        this.isWinner = true;
    }
    
    public void makeLose()
    {
        this.isWinner = false;
    }
}
