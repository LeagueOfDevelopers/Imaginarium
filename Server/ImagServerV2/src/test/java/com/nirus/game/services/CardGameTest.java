package com.nirus.game.services;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirus.api_params.GameParams;
import com.nirus.basics.Player;
import com.nirus.containers.PlayersContainer;
import com.nirus.game.basics.Card;
import com.nirus.game.models.FirstStage;
import com.nirus.game.models.FourthStage;
import com.nirus.game.models.SecondStage;
import com.nirus.game.models.ThirdStage;
import com.nirus.responses.ResponseGame;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by ndiezel on 19.03.2016.
 */
public class CardGameTest {
    @Test
    public void testGameProccessTillGameOver() throws Exception{
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        PlayersContainer playersContainer = new PlayersContainer();
        for(int i = 0; i < 4; i++){
            playersContainer.addPlayer(new Player(UUID.randomUUID()));
        }
        CardGame cardGame = new CardGame(4, playersContainer);

        //start
        while (true){
            //first stage
            HashMap<Player, ResponseGame> responses = new HashMap<Player, ResponseGame>();
            Player currentHead = null;
            cardGame.getStatus(new GameParams((Player) playersContainer.getHashSet().toArray()[0]));
            for(Player player: playersContainer.getHashSet()){
                ResponseGame response = cardGame.getStatus(new GameParams(player));
                responses.put(player, response);
                try{
                    if(player.getId().equals(UUID.fromString(gson.fromJson(response.getResponse(), FirstStage.class).currentHead.token))){
                        currentHead = player;
                    }
                }
                catch (Exception e){}
            }
            if(gson.fromJson(responses.get(playersContainer.getHashSet().toArray()[0]).getResponse(), FirstStage.class).stage == 0){
                break;
            }
            if(currentHead == null){
                fail("No head in first stage.");
            }
            if(gson.fromJson(responses.get(currentHead).getResponse(), FirstStage.class).stage != 1){
                fail("Couldn't change stage");
            }

            GameParams params = new GameParams(currentHead);
            try{
                params.setCard(gson.fromJson(responses.get(currentHead).getResponse(), FirstStage.class).cards[0].id);
            } catch (Exception e){
                fail("dunno");
            }
            params.setText("test");
            cardGame.setUpdate(params);

            //second stage
            HashMap<Player, ResponseGame> responses2 = new HashMap<Player, ResponseGame>();
            cardGame.getStatus(new GameParams(currentHead));
            for(Player player: playersContainer.getHashSet()){
                ResponseGame response = cardGame.getStatus(new GameParams(player));
                responses2.put(player, response);
            }
            if(gson.fromJson(responses2.get(currentHead).getResponse(), SecondStage.class).stage != 2){
                fail("Couldn't change stage");
            }
            for(Player player: playersContainer.getHashSet()){
                GameParams params1 = new GameParams(player);
                params1.setCard(gson.fromJson(responses.get(player).getResponse(), FirstStage.class).cards[0].id);
                cardGame.setUpdate(params1);
            }

            //third stage
            HashMap<Player, ResponseGame> responses3 = new HashMap<Player, ResponseGame>();
            cardGame.getStatus(new GameParams(currentHead));
            for(Player player: playersContainer.getHashSet()){
                ResponseGame response = cardGame.getStatus(new GameParams(player));
                responses3.put(player, response);
            }
            if(gson.fromJson(responses3.get(currentHead).getResponse(), ThirdStage.class).stage != 3){
                fail("Couldn't change stage");
            }
            for(Player player: playersContainer.getHashSet()){
                GameParams gameParams = new GameParams(player);
                gameParams.setCard(gson.fromJson(responses3.get(player).getResponse(), ThirdStage.class).cards[0].id);
                cardGame.setUpdate(gameParams);
            }

            //fourth stage
            cardGame.getStatus(new GameParams(currentHead));
            for(Player player: playersContainer.getHashSet()){
                if(gson.fromJson(cardGame.getStatus(new GameParams(player)).getResponse(), FourthStage.class).stage != 4){
                    fail("Couldn't change stage");
                }
            }
            for(Player player: playersContainer.getHashSet()){
                cardGame.setUpdate(new GameParams(player));
            }
        }
        cardGame.getScore(new GameParams(new Player(UUID.randomUUID())));

    }
}