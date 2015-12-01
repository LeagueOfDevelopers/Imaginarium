package com.nirus.game_logic;

import com.nirus.containers.ResponseForGameUpdate;

import java.util.*;

/**
 * Created by ndiezel on 01.12.2015.
 */
public class CardPart {
    public CardPart() {
        clientRequests = new HashSet<UUID>();
        cardsInADeck = new ArrayList<Integer>();
        gameStage = 0;
        InitCards();
    }

    public ResponseForGameUpdate GetGameStatus(UUID token){
        if(gameStage == 0){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                HashSet<Integer> cardsForPlayer = FirstStage();
                if(clientRequests.size() > 5){
                    gameStage = 1;
                }
                return new ResponseForGameUpdate("FIRST_STAGE", cardsForPlayer);
            } else{
                return new ResponseForGameUpdate("SAME", new HashSet<Integer>());
            }
        } else if(gameStage == 1){
            return new ResponseForGameUpdate("SECOND_STAGE", new HashSet<Integer>());
        } else{
            return null;
        }
    }
    private void InitCards(){
        cardsInADeck = new ArrayList<Integer>(72);
        for (int i = 0; i < cardsInADeck.size(); i++) {
            cardsInADeck.set(i, i);
        }
    }
    private HashSet<Integer> FirstStage(){
        return GiveCards(6);
    }
    private HashSet<Integer> GiveCards(Integer howMuch){
        HashSet<Integer> setOfCards = new HashSet<Integer>(howMuch);
        for(int i = 0; i < howMuch; i++){
            Random rand = new Random();
            Integer card = cardsInADeck.get(rand.nextInt()%cardsInADeck.size());
            setOfCards.add(card);
            cardsInADeck.remove(card);
        }
        return setOfCards;
    }
    private Integer gameStage;
    private HashSet<UUID> clientRequests;
    private ArrayList<Integer> cardsInADeck;
}
