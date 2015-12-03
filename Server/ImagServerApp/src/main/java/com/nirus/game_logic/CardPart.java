package com.nirus.game_logic;

import com.nirus.containers.ResponseForGameUpdate;

import java.util.*;

/**
 * Created by ndiezel on 01.12.2015.
 */
public class CardPart {
    public CardPart(HashSet<UUID> players) {
        clientRequests = new ArrayList<UUID>();
        cardsInADeck = new ArrayList<Integer>();
        gameStage = 0;
        playersList = players;
        InitCards();
        rand = new Random();
    }

    public ResponseForGameUpdate GetGameStatus(UUID token){
        if(gameStage == 0){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                HashSet<Integer> cardsForPlayer = FirstStage(token);
                if(clientRequests.size() > 5){
                    gameStage = 1;
                    currentHead = clientRequests.get(rand.nextInt()%6);
                    clientRequests.clear();
                }
                ResponseForGameUpdate response = new ResponseForGameUpdate("FIRST_STAGE");
                response.SetFirstStage(cardsForPlayer);
                return response;
            } else{
                return new ResponseForGameUpdate("SAME");
            }
        } else if(gameStage == 1){
            return new ResponseForGameUpdate("SECOND_STAGE");
        } else if(gameStage == 2) {
            return new ResponseForGameUpdate("THIRD_STAGE");
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
    private HashSet<Integer> FirstStage(UUID token){
        HashSet<Integer> newSetOfCards = GiveCards(6);
        cardBind.put(token, newSetOfCards);
        return newSetOfCards;
    }
    private HashSet<Integer> GiveCards(Integer howMuch){
        HashSet<Integer> setOfCards = new HashSet<Integer>(howMuch);
        for(int i = 0; i < howMuch; i++){
            Integer card = cardsInADeck.get(rand.nextInt()%cardsInADeck.size());
            setOfCards.add(card);
            cardsInADeck.remove(card);
        }
        return setOfCards;
    }
    private Integer gameStage;
    private Random rand;
    private UUID currentHead;
    private ArrayList<UUID> clientRequests;
    private HashSet<UUID> playersList;
    private HashMap<UUID, HashSet<Integer>> cardBind;
    private ArrayList<Integer> cardsInADeck;
}
