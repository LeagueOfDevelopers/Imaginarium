package com.nirus.game_logic;

import com.google.gson.JsonObject;
import com.nirus.containers.RequestForAChange;
import com.nirus.containers.ResponseForAChange;
import com.nirus.containers.ResponseForGameUpdate;

import java.util.*;
import java.lang.Math;

/**
 * Created by ndiezel on 01.12.2015.
 */
public class CardPart {
    public CardPart(HashSet<UUID> players) {
        clientRequests = new ArrayList<UUID>();
        cardsInADeck = new ArrayList<Integer>();
        cardBind = new HashMap<UUID, HashSet<Integer>>();
        gameStage = 0;
        playersList = players;
        InitCards();
        rand = new Random();
        iteratorForHead = playersList.iterator();
        currentHead = iteratorForHead.next();
    }

    public ResponseForGameUpdate GetGameStatus(UUID token){
        if(gameStage == 0){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                HashSet<Integer> cardsForPlayer = FirstStage(token);
                ResponseForGameUpdate response = new ResponseForGameUpdate("FIRST_STAGE");
                response.SetFirstStage(cardsForPlayer, token.equals(currentHead));
                return response;
            } else{
                if(token.equals(currentHead) && clientRequests.size() > 5 && headsCard != null){
                    gameStage = 1;
                    clientRequests.clear();
                }
                return new ResponseForGameUpdate("SAME");
            }
        } else if(gameStage == 1){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                ResponseForGameUpdate response = new ResponseForGameUpdate("SECOND_STAGE");
                response.SetSecondStage(headsText, headsCard);
                return response;
            } else{
                return new ResponseForGameUpdate("SAME");
            }
        }  else{
            return new ResponseForGameUpdate("ERROR");
        }
    }
    public ResponseForAChange UpdateGameSituation(UUID token, RequestForAChange request){
        if(gameStage == 0){
            if(token.equals(currentHead)){
                JsonObject jsonRequest = request.GetJson();
                Integer cardId = jsonRequest.get("card").getAsInt();
                String text = jsonRequest.get("text").getAsString();
                headsCard = cardId;
                headsText = text;
                return new ResponseForAChange("OK");
            } else{
                return new ResponseForAChange("YOU_ARE_NOT_A_HEAD");
            }
        } else if(gameStage == 1){
            if(!token.equals(currentHead)){
                return new ResponseForAChange("OK");
            }else {
                return new ResponseForAChange("YOU_ARE_HEAD");
            }
        } else{
            return new ResponseForAChange("ERROR");
        }
    }
    private void InitCards(){
        cardsInADeck = new ArrayList<Integer>(72);
        for (int i = 0; i < 72; i++) {
            cardsInADeck.add(i);
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
            Integer card = cardsInADeck.get(Math.abs(rand.nextInt()%cardsInADeck.size()));
            setOfCards.add(card);
            cardsInADeck.remove(card);
        }
        return setOfCards;
    }
    private Integer gameStage;
    private Random rand;
    private UUID currentHead;
    private ArrayList<UUID> clientRequests;
    private Iterator<UUID> iteratorForHead;
    private HashSet<UUID> playersList;
    private HashMap<UUID, HashSet<Integer>> cardBind;
    private ArrayList<Integer> cardsInADeck;
    private Integer headsCard = null;
    private String headsText = null;
}
