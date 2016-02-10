package com.nirus.game.basics;

import com.nirus.basics.Player;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ndiezel on 09.02.2016.
 */
public class PlayedCards {
    public PlayedCards(){
        chosenCard = new HashMap<Player, Card>();
        votedCard = new HashMap<Player, Card>();
    }
    public void chooseCard(Player player, Card card){
        chosenCard.put(player, card);
    }
    public void voteCard(Player player, Card card){
        votedCard.put(player, card);
    }
    public Integer howMuchChosen(){
        return chosenCard.size();
    }
    public Integer howMuchVoted(){
        return votedCard.size();
    }
    public Card getChosenCardByPlayer(Player player){
        return chosenCard.get(player);
    }
    public Card getVotedCardByPlayer(Player player){
        return votedCard.get(player);
    }
    public HashSet<Card> getChosenCards(){
        return new HashSet<Card>(chosenCard.values());
    }
    public HashSet<Card> getVotedCards(){
        return new HashSet<Card>(votedCard.values());
    }
    public Boolean containsChosenCard(Player player){
        return chosenCard.containsKey(player);
    }
    public  Boolean containsVotedCard(Player player){
        return votedCard.containsKey(player);
    }
    public Integer haveWeLost(Player headPlayer){
        Card card = getChosenCardByPlayer(headPlayer);
        Integer temp = 0;
        for(Card card1: votedCard.values()){
            if(card.equals(card1)){
                temp++;
            }
        }
        return temp;
    }
    public void clear(){
        chosenCard.clear();
        votedCard.clear();
    }
    private HashMap<Player, Card> chosenCard;
    private HashMap<Player, Card> votedCard;
}
