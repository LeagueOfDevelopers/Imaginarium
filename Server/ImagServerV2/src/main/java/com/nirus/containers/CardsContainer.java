package com.nirus.containers;

import com.nirus.game.basics.Card;

import java.util.HashSet;

/**
 * Created by ndiezel on 06.02.2016.
 */
public class CardsContainer {
    public CardsContainer(){
        cards = new HashSet<Card>();
    }
    public boolean addCard(Card card){
        return cards.add(card);
    }
    public HashSet<Card> getHashSet(){
        return cards;
    }
    private HashSet<Card> cards;
}
