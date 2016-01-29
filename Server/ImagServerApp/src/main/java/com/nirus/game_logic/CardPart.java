package com.nirus.game_logic;

import com.google.gson.JsonObject;
import com.nirus.containers.Card;
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
        cardsInADeck = new ArrayList<Card>();
        ReInit();
        scoresMap = new HashMap<UUID, Integer>();
        playersList = players;
        for (UUID player:
                playersList) {
            scoresMap.put(player, 0);
            cardBind.put(player, new HashSet<Card>());
        }
        gameStage = 0;

        rand = new Random();
        iteratorForHead = playersList.iterator();
        currentHead = iteratorForHead.next();
        switch (players.size()){
            case 4:
                ammountOfCards = 96;
                break;
            case 5:
                ammountOfCards = 75;
                break;
            case 6:
                ammountOfCards = 72;
                break;
            case 7:
                ammountOfCards = 98;
                break;
        }
        InitCards();
    }

    public ResponseForGameUpdate GetGameStatus(UUID token){
        if(gameStage == 0){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                HashSet<Card> cardsForPlayer = FirstStage(token, gameCircle == 0);
                if(cardsForPlayer.size() == 0){
                    return new ResponseForGameUpdate("GAME_OVER");
                }
                ResponseForGameUpdate response = new ResponseForGameUpdate("OK");
                response.SetStage(gameStage + 1);
                response.SetFirstStage(cardsForPlayer, token.equals(currentHead), scoresMap.get(token));
                return response;
            } else{
                if(clientRequests.size() > (playersList.size() - 1) && headsCard != null){
                    gameStage = 1;
                    clientRequests.clear();
                }
                return new ResponseForGameUpdate("SAME");
            }
        } else if(gameStage == 1){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                ResponseForGameUpdate response = new ResponseForGameUpdate("OK");
                response.SetStage(gameStage + 1);
                response.SetSecondStage(headsText);
                return response;
            } else{
                if(chosenCards.size()>(playersList.size() - 1)){
                    gameStage = 2;
                    clientRequests.clear();
                }
                return new ResponseForGameUpdate("SAME");
            }
        }  else if(gameStage == 2){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                ResponseForGameUpdate response = new ResponseForGameUpdate("OK");
                response.SetStage(gameStage + 1);
                HashSet<Card> cards = new HashSet<Card>(chosenCards.values());
                response.SetThirdStage(cards);
                return response;
            } else {
                if(votedCards.size()>(playersList.size() - 2)){
                    CalculateScore();
                    gameStage = 3;
                    clientRequests.clear();
                }
                return new ResponseForGameUpdate("SAME");
            }
        }else if(gameStage == 3){
            if(!clientRequests.contains(token)){
                clientRequests.add(token);
                ResponseForGameUpdate response = new ResponseForGameUpdate("OK");
                response.SetStage(gameStage + 1);
                response.SetFourthStage(scorePerCard, headsCard);
                return response;
            } else {
                if(clientRequests.size() > (playersList.size() - 1)){
                    gameStage = 0;
                    clientRequests.clear();
                    currentHead = iteratorForHead.next();
                    gameCircle++;
                    scorePerCard.clear();
                    headsText = null;
                    ReInit();
                }
                return new ResponseForGameUpdate("SAME");
            }
        } else{
            return new ResponseForGameUpdate("ERROR");
        }
    }
    public ResponseForAChange UpdateGameSituation(UUID token, RequestForAChange request){
        if(gameStage == 0){
            if(token.equals(currentHead)){
                JsonObject jsonRequest = request.GetJson();
                Card cardId = new Card(jsonRequest.get("card").getAsInt());
                String text = jsonRequest.get("text").getAsString();
                headsCard = cardId;
                chosenCards.put(token, cardId);
                headsText = text;
                Integer value = scorePerCard.containsKey(cardId)? scorePerCard.get(cardId):0;
                scorePerCard.put(cardId, value);
                return new ResponseForAChange("OK");
            } else{
                return new ResponseForAChange("YOU_ARE_NOT_A_HEAD");
            }
        } else if(gameStage == 1){
            if(!token.equals(currentHead)){
                Card chosenCard = new Card(request.GetJson().get("card").getAsInt());
                cardBind.get(token).remove(chosenCard);
                chosenCards.put(token, chosenCard);
                Integer value = scorePerCard.containsKey(chosenCard)? scorePerCard.get(chosenCard):0;
                scorePerCard.put(chosenCard, value);
                return new ResponseForAChange("OK");
            }else {
                return new ResponseForAChange("YOU_ARE_HEAD");
            }
        } else if(gameStage == 2){
            if(!token.equals(currentHead)){
                Card chosenCard = new Card(request.GetJson().get("card").getAsInt());
                votedCards.put(token, chosenCard);
                Integer value = scorePerCard.containsKey(chosenCard)? scorePerCard.get(chosenCard):0;
                scorePerCard.put(chosenCard, value + 1);
                if(chosenCard.equals(headsCard)){
                    likeHead++;
                }
                return new ResponseForAChange("OK");
            }else{
                return new ResponseForAChange("YOU_ARE_HEAD");
            }
        } else{
            return new ResponseForAChange("ERROR");
        }
    }
    private void InitCards(){
        cardsInADeck = new ArrayList<Card>(ammountOfCards);
        for (int i = 0; i < ammountOfCards; i++) {
            cardsInADeck.add(new Card(i));
        }
    }
    private HashSet<Card> FirstStage(UUID token, boolean howMuchCards){
        HashSet<Card> newSetOfCards;
        if(howMuchCards) {
             newSetOfCards = GiveCards(6);
        }else{
            newSetOfCards = GiveCards(1);
        }
        ArrayList<Card> temp = new ArrayList<Card>(cardBind.get(token));
        temp.addAll(newSetOfCards);
        newSetOfCards = new HashSet<Card>(temp);
        cardBind.put(token, newSetOfCards);
        return newSetOfCards;
    }
    private HashSet<Card> GiveCards(Integer howMuch){
        HashSet<Card> setOfCards = new HashSet<Card>(howMuch);
        if(cardsInADeck.size() == 0){
            return new HashSet<Card>();
        }
        for(int i = 0; i < howMuch; i++){
            Card card = cardsInADeck.get(Math.abs(rand.nextInt()%cardsInADeck.size()));
            setOfCards.add(card);
            cardsInADeck.remove(card);
        }
        return setOfCards;
    }
    private void ReInit(){
        chosenCards = new HashMap<UUID, Card>();
        votedCards = new HashMap<UUID, Card>();
        cardBind = new HashMap<UUID, HashSet<Card>>();
        scorePerCard = new HashMap<Card, Integer>();
        likeHead = 0;
    }
    private void CalculateScore() {
        for(UUID player : playersList){
            Integer count = 0;
            Card chosenCard = chosenCards.get(player);
            for(UUID pl: playersList){
                if(!pl.equals(currentHead)) {
                    if (votedCards.get(pl).equals(chosenCard)) {
                        count++;
                    }
                }
            }
            if(player.equals(currentHead)){
                if(likeHead > 0 && likeHead !=(playersList.size() - 1)){
                    scoresMap.put(player, scoresMap.get(player) + count + 3);
                } else if(likeHead == 0){
                    scoresMap.put(player, scoresMap.get(player) - 2);
                } else{
                    scoresMap.put(player, scoresMap.get(player) - 3);
                }
            } else{
                if(votedCards.get(player) == headsCard && likeHead != (playersList.size() - 1)){
                    scoresMap.put(player, scoresMap.get(player) + 3);
                }
                scoresMap.put(player, scoresMap.get(player) + count);
            }
        }
    }
    private Integer gameStage;
    private Random rand;
    private UUID currentHead;
    private Integer gameCircle = 0;
    private Integer likeHead = 0;
    private ArrayList<UUID> clientRequests;
    private Iterator<UUID> iteratorForHead;
    private HashSet<UUID> playersList;
    private HashMap<UUID, Integer> scoresMap;
    private HashMap<Card, Integer> scorePerCard;
    private HashMap<UUID, Card> chosenCards;
    private HashMap<UUID, Card> votedCards;
    private HashMap<UUID, HashSet<Card>> cardBind;
    private ArrayList<Card> cardsInADeck;
    private Integer ammountOfCards;
    private Card headsCard = null;
    private String headsText = null;
}
