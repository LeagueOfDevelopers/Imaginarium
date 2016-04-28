package com.nirus.game.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirus.api_params.GameParams;
import com.nirus.basics.Player;
import com.nirus.containers.PlayersContainer;
import com.nirus.containers.ResponsesContainer;
import com.nirus.game.basics.*;
import com.nirus.game.interfaces.CardGameInterface;
import com.nirus.game.models.*;
import com.nirus.game.models.basics.CardModel;
import com.nirus.game.models.basics.PlayerModel;
import com.nirus.responses.ResponseGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ndiezel on 22.04.2016.
 */
public class FastCardGame implements CardGameInterface {
    public FastCardGame(Integer size, PlayersContainer players){
        Integer[] a = new Integer[]{0,0,0,36, 42, 48,54};
        standardDeck = new Deck(a[size - 1]);
        responses = new ResponsesContainer();
        hands = new PlayersHands();
        scores = new PlayersScores(players);
        gameStage = new Stage();
        playedCards = new PlayedCards();
        fourthStagePlayers = new PlayersContainer();
        timer = new SimpleTimer();

        builder.setPrettyPrinting();

        for(Player player: players.getHashSet()){
            hands.addHand(player, new CardsContainer());
            players.updatePlayerInstant(player, Instant.now());
        }

        this.players = players;
        headIterator = this.players.getHashSet().iterator();
        builder.setPrettyPrinting();
        initFirstTurn(null);
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
            response.setResult("{\"status\":\"OK\"}");
        } else{
            response.setResult("{\"status\":\"ERROR\"}");
        }
        return response;
    }

    public ResponseGame getScore(GameParams params){
        ResponseGame responseGame = new ResponseGame();
        ScoreModel scoreModel = new ScoreModel();
        ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
        //Integer i = 0;
        for (Player player : players.getHashSet()){
            PlayerModel playerModel = new PlayerModel();
            playerModel.token = player.getId().toString();
            playerModel.score = scores.getScoreByPlayer(player).getScore();
            playerModels.add(playerModel);
            //responseGame.addField("token#" + i.toString(), token.getId().toString());
            //responseGame.addField("score#" + i.toString(), scores.getScoreByPlayer(token).getScore().toString());
            //i++;
        }
        scoreModel.players = playerModels.toArray(new PlayerModel[]{});
        responseGame.setResult(gson.toJson(scoreModel));
        return responseGame;
    }

    public ResponseGame leaveGame(GameParams params){
        gameStage.endGame(-3);
        leavingPlayer = params.getPlayer();
        ResponseGame response = new ResponseGame();
        response.setResult("{\"status\":\"OK\"}");
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
                if(playedCards.getChosenMap().size() == 1){
                    gameStage.nextStage();
                    initSecondStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 1){
                if(playedCards.getChosenMap().size() == players.size()){
                    gameStage.nextStage();
                    initThirdStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 2){
                if(playedCards.getVotedMap().size() == players.size() - 1){
                    gameStage.nextStage();
                    initFourthStage();
                    logCurrentState();
                }
            }else if(gameStage.getStage() == 3){
                if(fourthStagePlayers.size().equals(players.size())) {
                    gameStage.nextStage();
                    fourthStagePlayers.clear();
                    initFirstTurn(null);
                    logCurrentState();
                }
            }
        }
    }
    private void updateResponseForPlayer(Player player){
        ResponseGame responseGame = responses.getResponseByPlayerSafe(player);
        if(gameStage.getStage() == 0){
            FirstStage firstStage;
            firstStage = gson.fromJson(responseGame.getResponse(), FirstStage.class);
            firstStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: playedCards.getChosenMap().keySet()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            firstStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            responseGame.setResult(gson.toJson(firstStage));
        }
        if(gameStage.getStage() == 1){
            SecondStage secondStage;
            secondStage = gson.fromJson(responseGame.getResponse(), SecondStage.class);
            secondStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: playedCards.getChosenMap().keySet()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            secondStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            responseGame.setResult(gson.toJson(secondStage));
        }
        if(gameStage.getStage() == 2){
            ThirdStage thirdStage;
            thirdStage = gson.fromJson(responseGame.getResponse(), ThirdStage.class);
            thirdStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: playedCards.getVotedPlayers()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            thirdStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            responseGame.setResult(gson.toJson(thirdStage));
        }
        if(gameStage.getStage() == 3){
            FourthStage fourthStage;
            fourthStage = gson.fromJson(responseGame.getResponse(), FourthStage.class);
            fourthStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: fourthStagePlayers.getHashSet()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            fourthStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            responseGame.setResult(gson.toJson(fourthStage));
        }
        if(gameStage.getStage() < 0){
            GameOverModel gameOverModel = new GameOverModel();
            gameOverModel.stage = 0;
            if(gameStage.getStage() == -1){
                gameOverModel.reason = "OUT_OF_CARDS";
            }
            if(gameStage.getStage() == -2){
                gameOverModel.reason = "RUN_OUT_OF_TIME";
            }
            if(gameStage.getStage() == -3){
                gameOverModel.reason = "PLAYER_LEFT";
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = leavingPlayer.getId().toString();
                gameOverModel.leftPlayer = playerModel;
            }
            responseGame.setResult(gson.toJson(gameOverModel));
        }
    }
    private void initFirstTurn(PlayersContainer playersThatFuckedUp){
        timer.setFinalTime(90);
        timer.start();
        responses.clear();
        playedCards.clear();
        if(!headIterator.hasNext()){
            headIterator = players.getHashSet().iterator();
        }
        currentHead = headIterator.next();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            FirstStage firstStage = new FirstStage();
            if(hands.getHandByPlayer(player).size() == 0 && standardDeck.size() == 0){
                firstStage.stage = 0;
                response.setResult(gson.toJson(firstStage));
                responses.addResponse(response, player);
                gameStage.endGame(-1);
                continue;
            }
            firstStage.stage = 1;
            firstStage.remainingTime = timer.getRemainingTime();
            if(playersThatFuckedUp != null){
                ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
                for(Player player1: playersThatFuckedUp.getHashSet()){
                    PlayerModel playerModel = new PlayerModel();
                    playerModel.token = player1.getId().toString();
                    playerModels.add(playerModel);
                }
                firstStage.playersThatFuckedUp = playerModels.toArray(new PlayerModel[]{});
            }
            PlayerModel currentH = new PlayerModel();
            currentH.token = currentHead.getId().toString();
            firstStage.currentHead = currentH;
            CardsContainer cards = hands.getHandByPlayer(player);
            if(cards == null){
                cards = new CardsContainer();
            }
            Integer handSize = cards.getHashSet().size();
            ArrayList<CardModel> cardModels = new ArrayList<CardModel>();
            for(int i = 0; i < 6 - handSize; i++){
                Card card = standardDeck.getRandomCard();
                if(card != null){
                    cards.addCard(card);
                }
            }
            for(Card card: cards.getHashSet()){
                CardModel cardModel = new CardModel();
                cardModel.id = card.getId();
                //PlayerModel playerModel = new PlayerModel();
                //playerModel.token = player.getId().toString();
                //cardModel.owner = playerModel;
                cardModels.add(cardModel);
            }
            firstStage.cards = cardModels.toArray(new CardModel[]{});
            response.setResult(gson.toJson(firstStage));
            responses.addResponse(response, player);
        }
        for(Player player: players.getHashSet()){
            FirstStage firstStage;
            firstStage = gson.fromJson(responses.getResponseByPlayerSafe(player).getResponse(), FirstStage.class);
            firstStage.amountOfCards = standardDeck.size();
            ResponseGame responseGame = new ResponseGame();
            responseGame.setResult(gson.toJson(firstStage));
            responses.addResponse(responseGame, player);
        }
    }
    private void initSecondStage(){
        timer.restart(60);
        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            SecondStage secondStage = new SecondStage();
            secondStage.stage = 2;
            secondStage.text = headsText;
            secondStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: playedCards.getChosenMap().keySet()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            secondStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            response.setResult(gson.toJson(secondStage));
            responses.addResponse(response, player);
        }
    }
    private void initThirdStage(){
        timer.restart(45);
        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            ThirdStage thirdStage = new ThirdStage();
            thirdStage.stage = 3;
            thirdStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: playedCards.getVotedPlayers()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            thirdStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            ArrayList<CardModel> cardModels = new ArrayList<CardModel>();
            for(Card card: playedCards.getChosenCards()){
                if(card.getId().equals(playedCards.getChosenCardByPlayer(player).getId())){
                    continue;
                }
                CardModel cardModel = new CardModel();
                cardModel.id = card.getId();
                cardModels.add(cardModel);
            }
            thirdStage.cards = cardModels.toArray(new CardModel[]{});
            response.setResult(gson.toJson(thirdStage));
            responses.addResponse(response, player);
        }
    }
    private void initFourthStage(){
        timer.restart(30);
        HashMap<Card, Integer> points = calculateScore();

        responses.clear();
        for(Player player: players.getHashSet()){
            ResponseGame response = new ResponseGame();
            FourthStage fourthStage = new FourthStage();
            fourthStage.stage = 4;
            fourthStage.remainingTime = timer.getRemainingTime();
            ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
            for(Player player1: fourthStagePlayers.getHashSet()){
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                playerModels.add(playerModel);
            }
            fourthStage.donePlayers = playerModels.toArray(new PlayerModel[]{});
            ArrayList<CardModel> cardModels = new ArrayList<CardModel>();
            for(Card card: points.keySet()){
                Player player1 = playedCards.getPlayerByChosenCard(card);
                CardModel cardModel = new CardModel();
                PlayerModel playerModel = new PlayerModel();
                playerModel.token = player1.getId().toString();
                cardModel.id = card.getId();
                cardModel.owner = playerModel;
                ArrayList<PlayerModel> playerModels1 = new ArrayList<PlayerModel>();
                for(Player player2: playedCards.getVotedPlayers()){
                    if(playedCards.getVotedCardByPlayer(player2).getId().equals(card.getId())){
                        PlayerModel playerModel1 = new PlayerModel();
                        playerModel1.token = player2.getId().toString();
                        playerModels1.add(playerModel1);
                    }
                }
                cardModel.voters = playerModels1.toArray(new PlayerModel[]{});
                cardModels.add(cardModel);
            }
            fourthStage.cards = cardModels.toArray(new CardModel[]{});
            response.setResult(gson.toJson(fourthStage));

            //response.addField("isDone", "false");
            //response.addField("countOfPlayers", players.size().toString());
            //response.addField("stage", "4");
            //Integer i = 0;
            //for(Card card: points.keySet()){
            //    Player player1 = playedCards.getPlayerByChosenCard(card);
            //    response.addField("token#" + i.toString(), player1.getId().toString());
            //    response.addField("card#" + i.toString(), card.getId().toString());
            //    response.addField("vote#" + i.toString(), points.get(card).toString());
            //    i++;
            //}
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
            if(players.getPlayerInstant(player).isBefore(Instant.now().minus(12, ChronoUnit.HOURS))) {
                gameStage.endGame(-2);
                break;
            }
        }
        if(timer.hasTimeRunOut()){
            if(gameStage.getStage() == 0){
                if(playedCards.getChosenMap().size() < 1){
                    scores.changePlayerScore(currentHead, -3);
                    PlayersContainer playersThatFuckedUp = new PlayersContainer();
                    playersThatFuckedUp.addPlayer(currentHead);
                    initFirstTurn(playersThatFuckedUp);
                }
            }
            if(gameStage.getStage() == 1){
                boolean playersFailed = false;
                PlayersContainer playersThatFuckedUp = new PlayersContainer();
                for(Player player: players.getHashSet()){
                    if(playedCards.getChosenCardByPlayer(player) == null){
                        playersThatFuckedUp.addPlayer(player);
                        scores.changePlayerScore(player, -3);
                        playersFailed = true;
                    }
                }
                if(playersFailed){
                    for(Player player: players.getHashSet()){
                        if(playedCards.getChosenCardByPlayer(player) != null){
                            hands.addCardToHand(player, playedCards.getChosenCardByPlayer(player));
                        }
                    }
                    initFirstTurn(playersThatFuckedUp);
                }
            }
            if(gameStage.getStage() == 2){
                boolean playersFailed = false;
                PlayersContainer playersThatFuckedUp = new PlayersContainer();
                for(Player player: players.getHashSet()){
                    if(playedCards.getVotedCardByPlayer(player) == null && !player.getId().equals(currentHead.getId())){
                        playersThatFuckedUp.addPlayer(player);
                        scores.changePlayerScore(player, -3);
                        playersFailed = true;
                    }
                }
                if(playersFailed){
                    initFirstTurn(playersThatFuckedUp);
                }
            }
            if(gameStage.getStage() == 3){
                initFirstTurn(null);
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

    private SimpleTimer timer;
    private String headsText;
    private PlayedCards playedCards;
    private Stage gameStage;
    private Iterator<Player> headIterator;
    private Player currentHead;
    private Player leavingPlayer;
    private PlayersScores scores;
    private PlayersHands hands;
    private ResponsesContainer responses;
    private Deck standardDeck;
    private PlayersContainer players;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
}
