package com.nirus.game.basics;

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
    public boolean removeCard(Card card){
        for(Card card1: cards){
            if(card.getId().equals(card1.getId())){
                return cards.remove(card1);
            }
        }
        return false;
    }
    public boolean contains(Card card){
        for(Card card1: cards){
            if(card.getId().equals(card1.getId())){
                return true;
            }
        }
        return false;
    }
    public HashSet<Card> getHashSet(){
        return cards;
    }
    public Integer size(){
        return cards.size();
    }
    private HashSet<Card> cards;
}
