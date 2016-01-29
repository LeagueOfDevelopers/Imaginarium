package com.nirus.game.services;

import com.nirus.containers.PlayersContainer;
import com.nirus.game.basics.Deck;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class CardGame {
    public CardGame(Integer size, PlayersContainer players){
        standartDeck = new Deck(size);
        this.players = players;
    }

    
    private Deck standartDeck;
    private PlayersContainer players;
}
