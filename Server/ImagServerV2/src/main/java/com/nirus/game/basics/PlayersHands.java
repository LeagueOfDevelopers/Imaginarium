package com.nirus.game.basics;

import com.nirus.basics.Player;
import com.nirus.containers.CardsContainer;

import java.util.HashMap;

/**
 * Created by ndiezel on 06.02.2016.
 */
public class PlayersHands {
    public PlayersHands(){
        hands = new HashMap<Player, CardsContainer>();
    }
    public void addHand(Player player, CardsContainer cards){
        hands.put(player, cards);
    }
    public Integer sizeOfPlayersHand(Player player){
        return hands.get(player).getHashSet().size();
    }
    public void addCardToHand(Player player, Card card){
        CardsContainer cards = hands.get(player);
        cards.addCard(card);
        hands.put(player, cards);
    }
    public CardsContainer getHandByPlayer(Player player){
        return hands.get(player);
    }
    private HashMap<Player, CardsContainer> hands;
}