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
        for(Player player1: chosenCard.keySet()){
            if(player.getId().equals(player1.getId())){
                chosenCard.put(player1, card);
                return;
            }
        }
        chosenCard.put(player, card);
    }
    public void voteCard(Player player, Card card){
        for(Player player1: votedCard.keySet()){
            if(player.getId().equals(player1.getId())){
                votedCard.put(player1, card);
                return;
            }
        }
        votedCard.put(player, card);
    }
    public Integer howMuchChosen(){
        return chosenCard.size();
    }
    public Integer howMuchVoted(){
        return votedCard.size();
    }
    public Card getChosenCardByPlayer(Player player){
        for(Player player1: chosenCard.keySet()){
            if(player.getId().equals(player1.getId())){
                return chosenCard.get(player1);
            }
        }
        return null;
    }
    public Card getVotedCardByPlayer(Player player){
        for(Player player1: votedCard.keySet()){
            if(player.getId().equals(player1.getId())){
                return votedCard.get(player1);
            }
        }
        return null;
    }
    public HashSet<Card> getChosenCards(){
        return new HashSet<Card>(chosenCard.values());
    }
    public HashMap<Player, Card> getChosenMap(){ return chosenCard; }
    public HashSet<Player> getVotedPlayers(){
        return new HashSet<Player>(votedCard.keySet());
    }
    public HashMap<Player, Card> getVotedMap(){ return votedCard; }
    public Boolean containsChosenCardByPlayer(Player player){
        for(Player player1: chosenCard.keySet()){
            if(player.getId().equals(player1.getId())){
                return true;
            }
        }
        return false;
    }
    public Boolean containsChosenCard(Card card){
        for(Card card1: chosenCard.values()){
            if(card.getId().equals(card1.getId())){
                return true;
            }
        }
        return false;
    }
    public  Boolean containsVotedCard(Player player){
        for(Player player1: votedCard.keySet()){
            if(player.getId().equals(player1.getId())){
                return true;
            }
        }
        return false;
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
