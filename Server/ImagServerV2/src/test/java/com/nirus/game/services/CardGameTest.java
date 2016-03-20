package com.nirus.game.services;

import static org.junit.Assert.*;

import com.nirus.api_params.GameParams;
import com.nirus.basics.Player;
import com.nirus.containers.PlayersContainer;
import com.nirus.game.basics.Card;
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
                    if(player.getId().equals(UUID.fromString(response.getField("currentHead")))){
                        currentHead = player;
                    }
                }
                catch (Exception e){}
            }
            if(responses.get(playersContainer.getHashSet().toArray()[0]).getField("stage").equals("0")){
                break;
            }
            if(currentHead == null){
                fail("No head in first stage.");
            }
            GameParams params = new GameParams(currentHead);
            params.setCard(Integer.parseInt(responses.get(currentHead).getField("card#0")));
            params.setText("test");
            cardGame.setUpdate(params);

            //second stage
            HashMap<Player, ResponseGame> responses2 = new HashMap<Player, ResponseGame>();
            cardGame.getStatus(new GameParams(currentHead));
            for(Player player: playersContainer.getHashSet()){
                ResponseGame response = cardGame.getStatus(new GameParams(player));
                responses2.put(player, response);
            }
            for(Player player: playersContainer.getHashSet()){
                GameParams params1 = new GameParams(player);
                params1.setCard(Integer.parseInt(responses.get(player).getField("card#0")));
                cardGame.setUpdate(params1);
            }

            //third stage
            HashMap<Player, ResponseGame> responses3 = new HashMap<Player, ResponseGame>();
            cardGame.getStatus(new GameParams(currentHead));
            for(Player player: playersContainer.getHashSet()){
                ResponseGame response = cardGame.getStatus(new GameParams(player));
                responses3.put(player, response);
            }
            for(Player player: playersContainer.getHashSet()){
                GameParams gameParams = new GameParams(player);
                gameParams.setCard(Integer.parseInt(responses.get(player).getField("card#0")));
                cardGame.setUpdate(gameParams);
            }

            //fourth stage
            for(Player player: playersContainer.getHashSet()){
                cardGame.getStatus(new GameParams(player));
            }
            for(Player player: playersContainer.getHashSet()){
                cardGame.setUpdate(new GameParams(player));
            }
        }
        cardGame.getScore(new GameParams(new Player(UUID.randomUUID())));

    }
}