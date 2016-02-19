package com.nirus.game.basics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class Deck {
    public Deck(Integer size){
        cards = new ArrayList<Card>();
        standardInit(size);
        rand = new Random();
    }
    public Card getRandomCard(){
        if(cards.size() == 0){
            return null;
        }
        Card card = cards.get(Math.abs(rand.nextInt()%cards.size()));
        cards.remove(card);
        return card;
    }

    private void standardInit(Integer size){
        for(Integer i = 0; i < size; i++){
            cards.add(new Card(i));
        }
    }
    private ArrayList<Card> cards;
    private Random rand;
}
