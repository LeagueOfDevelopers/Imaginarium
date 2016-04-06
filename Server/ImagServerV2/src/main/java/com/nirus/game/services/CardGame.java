package com.nirus.game.services;

import com.nirus.api_params.GameParams;
import com.nirus.basics.Player;
import com.nirus.game.basics.CardsContainer;
import com.nirus.containers.PlayersContainer;
import com.nirus.containers.ResponsesContainer;
import com.nirus.game.basics.*;
import com.nirus.responses.ResponseGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class CardGame {
    public CardGame(Integer size, PlayersContainer players){
        Integer[] a = new Integer[]{0,0,0,96,75,72,98};
        standardDeck = new Deck(a[size - 1]);
        responses = new ResponsesContainer();
        hands = new PlayersHands();
        scores = new PlayersScores(players);
        gameStage = new Stage();
        playedCards = new PlayedCards();
        fourthStagePlayers = new PlayersContainer();

        for(Player player: players.getHashSet()){
            hands.addHand(player, new CardsContainer());
            players.updatePlayerInstant(player, Instant.now());
        }

        this.players = players;
        headIterator = this.players.getHashSet().iterator();
        initFirstTurn();
        logCurrentState();
    }

    public ResponseGame getStatus(GameParams params){
        updateResponseForPlayer(params.getPlayer());
        players.updatePlayerInstant(params.getPlayer(), Instant.now());
        checkPlayersTiming();
        ResponseGame response = responses.getResponseByPlayer(params.getPlayer());
        updateForNextStage();
        return response;
    }

    public ResponseGame setUpdate(GameParams params){
        ResponseGame response = new ResponseGame();
        if(updateGameSituation(params)){
            response.addField("status", "OK");
        } else{
            response.addField("status", "ERROR");
        }
        return response;
    }

    public ResponseGame getScore(GameParams params){
        ResponseGame responseGame = new ResponseGame();
        Integer i = 0;
        for (Player player : players.getHashSet()){
            responseGame.addField("player#" + i.toString(), player.getId().toString());
            responseGame.addField("score#" + i.toString(), scores.getScoreByPlayer(player).getScore().toString());
            i++;
        }
        return responseGame;
    }

    public ResponseGame leaveGame(GameParams params){
        gameStage.endGame(-3);
        ResponseGame response = new ResponseGame();
        response.addField("status", "OK");
        return response;
    }

    private boolean updateGameSituation(GameParams params){
        if(gameStage.getStage() == 0){
            if(params.getPlayer().equals(currentHead)){
                if(hands.getHandByPlayer(params.getPlayer()).contains(params.getCard())){
                    headsText = params.getText();
                    hands.getHandByPlayer(params.getPlayer()).removeCard(params.getCard());
                    playedCards.chooseCard(params.getPlayer(), params.getCard());
                    return true;
                }
                return false;
            } else{
                return false;
            }
        }
        if(gameStage.getStage() == 1){
            if(!params.getPlayer().equals(currentHead)){
                if(hands.getHandByPlayer(params.getPlayer()).contains(params.getCard())){
                    hands.getHandByPlayer(params.getPlayer()).removeCard(params.getCard());
                    playedCards.chooseCard(params.getPlayer(), params.getCard());
                    return true;
                }
                return false;
            } else{
                return false;
            }
        }
        if(gameStage.getStage() == 2){
            if(!params.getPlayer().equals(currentHead)){
                if(playedCards.containsChosenCard(params.getCard())){
                    playedCards.voteCard(params.getPlayer(), params.getCard());
                    return true;
                }
                return false;
            } else{
                return false;
            }
        }
        if(gameStage.getStage() == 3){
            fourthStagePlayers.addPlayer(params.getPlayer());
            return true;
        }
        return false;
    }
    private void updateForNextStage(){
        if(responses.getAmmountOfRequest().equals(players.size())){
            if(gameStage.getStage() == 0){
                if(playedCards.howMuchChosen() == 1){
                    gameStage.nextStage();
                    initSecondStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 1){
                if(playedCards.howMuchChosen().equals(players.size())){
                    gameStage.nextStage();
                    initThirdStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 2){
                if(playedCards.howMuchVoted() == players.size() - 1){
                    gameStage.nextStage();
                    initFourthStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 3){
                if(fourthStagePlayers.size().equals(players.size())) {
                    gameStage.nextStage();
                    fourthStagePlayers.clear();
                    initFirstTurn();
                    logCurrentState();
                }
            }
        }
    }
    private void updateResponseForPlayer(Player player){
        ResponseGame responseGame = responses.getResponseByPlayerSafe(player);
        if(gameStage.getStage() == 0){
            responseGame.updateField("countOfPlayers", playedCards.howMuchChosen().toString());
            Boolean isDone = !currentHead.equals(player) || playedCards.howMuchChosen() > 0;
            responseGame.updateField("isDone", isDone.toString());
        }
        if(gameStage.getStage() == 1){
            responseGame.updateField("countOfPlayers", playedCards.howMuchChosen().toString());
            Boolean isDone = playedCards.containsChosenCardByPlayer(player);
            responseGame.updateField("isDone", isDone.toString());
        }
        if(gameStage.getStage() == 2){
            responseGame.updateField("countOfPlayers", playedCards.howMuchVoted().toString());
            Boolean isDone = playedCards.containsVotedCard(player);
            if(player.getId().equals(currentHead.getId())){
                isDone = true;
            }
            responseGame.updateField("isDone", isDone.toString());
        }
        if(gameStage.getStage() == 3){
            responseGame.updateField("countOfPlayers", fourthStagePlayers.size().toString());
            Boolean isDone = fourthStagePlayers.contains(player);
            responseGame.updateField("isDone", isDone.toString());
        }
        if(gameStage.getStage() < 0){
            responseGame.updateField("stage", "0");
        }
    }
    private void initFirstTurn(){
        responses.clear();
        playedCards.clear();
        if(!headIterator.hasNext()){
            headIterator = players.getHashSet().iterator();
        }
        currentHead = headIterator.next();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            if(hands.getHandByPlayer(player).size() == 0 && standardDeck.size() == 0){
                response.addField("stage", "0");
                responses.addResponse(response, player);
                gameStage.endGame(-1);
                continue;
            }
            Boolean isHead = currentHead.equals(player);
            isHead = !isHead;
            response.addField("isDone", isHead.toString());
            response.addField("countOfPlayers", "0");
            response.addField("stage", "1");
            response.addField("currentHead",  currentHead.getId().toString());
            //response.addField("score", scores.getScoreByPlayer(player).getScore().toString());
            response.addField("amountOfCards", standardDeck.size().toString());
            CardsContainer cards = hands.getHandByPlayer(player);
            if(cards == null){
                cards = new CardsContainer();
            }
            Integer handSize = cards.getHashSet().size();
            for(int i = 0; i < 6 - handSize; i++){
                Card card = standardDeck.getRandomCard();
                if(card != null){
                    cards.addCard(card);
                }
            }
            Integer i = 0;
            for(Card card : cards.getHashSet()){
                response.addField("card#" + i.toString(), card.getId().toString());
                i++;
            }
            responses.addResponse(response, player);
        }
    }
    private void initSecondStage(){
        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            Boolean isDone = playedCards.containsChosenCardByPlayer(player);
            response.addField("isDone", isDone.toString());
            response.addField("countOfPlayers", "1");
            response.addField("stage", "2");
            response.addField("text", headsText);
            responses.addResponse(response, player);
        }
    }
    private void initThirdStage(){
        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            Boolean isDone = currentHead.equals(player);
            response.addField("isDone", isDone.toString());
            response.addField("countOfPlayers", "1");
            response.addField("stage", "3");
            Integer i = 0;
            for(Card card: playedCards.getChosenCards()){
                if(card.getId().equals(playedCards.getChosenCardByPlayer(player).getId())){
                    continue;
                }
                response.addField("card#" + i.toString(), card.getId().toString());
                i++;
            }
            responses.addResponse(response, player);
        }
    }
    private void initFourthStage(){
        HashMap<Card, Integer> points = calculateScore();

        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            response.addField("isDone", "false");
            response.addField("countOfPlayers", players.size().toString());
            response.addField("stage", "4");
            Integer i = 0;
            for(Card card: points.keySet()){
                Player player1 = playedCards.getPlayerByChosenCard(card);
                response.addField("player#" + i.toString(), player1.getId().toString());
                response.addField("card#" + i.toString(), card.getId().toString());
                response.addField("vote#" + i.toString(), points.get(card).toString());
                i++;
            }
            responses.addResponse(response, player);
        }
    }

    private HashMap<Card, Integer> calculateScore() {
        HashMap<Card, Integer> points = new HashMap<Card, Integer>();
        for(Player player: players.getHashSet()){
            Card chosenCard = playedCards.getChosenCardByPlayer(player);
            Integer result = 0;
            for(Player player1: playedCards.getVotedPlayers()){
                if(playedCards.getVotedCardByPlayer(player1).getId().equals(chosenCard.getId())){
                    result++;
                }
            }
            points.put(chosenCard, result);
        }
        for(Player player: players.getHashSet()){
            Card playerCard = playedCards.getChosenCardByPlayer(player);
            if(player.getId().equals(currentHead.getId())){
                if(points.get(playerCard) == 0){
                    scores.changePlayerScore(currentHead, -2);
                } else if(points.get(playerCard) == players.size() - 1){
                    scores.changePlayerScore(currentHead, -3);
                } else{
                    scores.changePlayerScore(currentHead,
                            points.get(playerCard) * 3);
                }
            } else{
                if(points.get(playedCards.getChosenCardByPlayer(currentHead)) != players.size() - 1){
                    if(playedCards
                            .getVotedCardByPlayer(player)
                            .getId()
                            .equals(playedCards.getChosenCardByPlayer(currentHead).getId())){
                        scores.changePlayerScore(player, 3);
                    }
                    scores.changePlayerScore(player, points.get(playerCard));
                }
            }
        }
        return points;
    }
    private void checkPlayersTiming(){
        for(Player player: players.getHashSet()){
            if(players.getPlayerInstant(player).isBefore(Instant.now().minus(12, ChronoUnit.HOURS))){
                gameStage.endGame(-2);
                break;
            }
        }
    }
    private void logCurrentState(){
        logger.debug("===========================================================================");
        logger.debug("Stage: " + gameStage.getStage().toString());
        logger.debug("Current head: " + currentHead.getId().toString());
        for(Player player: players.getHashSet()){
            logger.debug("Player: " + player.getId().toString());
            String cardsInHand = "";
            for(Card card: hands.getHandByPlayer(player).getHashSet()){
                cardsInHand += card.getId().toString() + " ";
            }
            logger.debug("Cards in hand: " + cardsInHand);
            if(!(playedCards.getChosenCardByPlayer(player) == null)) {
                logger.debug("Chosen Card: " + playedCards.getChosenCardByPlayer(player).getId().toString());
            }
            if(!(playedCards.getVotedCardByPlayer(player) == null)) {
                logger.debug("Voted Card: " + playedCards.getVotedCardByPlayer(player).getId().toString());
            }
            logger.debug("Score: " + scores.getScoreByPlayer(player).getScore().toString());
            logger.debug("Response: " + responses.getResponseByPlayerSafe(player).getResponse());
        }

    }

    private PlayersContainer fourthStagePlayers;

    private Logger logger = LogManager.getLogger(CardGame.class);

    private String headsText;
    private PlayedCards playedCards;
    private Stage gameStage;
    private Iterator<Player> headIterator;
    private Player currentHead;
    private PlayersScores scores;
    private PlayersHands hands;
    private ResponsesContainer responses;
    private Deck standardDeck;
    private PlayersContainer players;
}
