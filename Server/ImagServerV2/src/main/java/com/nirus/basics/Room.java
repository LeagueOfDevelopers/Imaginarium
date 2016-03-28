package com.nirus.basics;

import com.nirus.api_params.GameParams;
import com.nirus.containers.PlayersContainer;
import com.nirus.game.services.CardGame;
import com.nirus.responses.ResponseGame;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class Room {
    public Room(PlayersContainer players, Integer size){
        this.players = players;
        game = new CardGame(size, players);
    }
    public boolean contains(Player player){
        return players.contains(player);
    }
    public ResponseGame gameStatus(GameParams params){
        return game.getStatus(params);
    }
    public ResponseGame gameUpdate(GameParams params){
        return game.setUpdate(params);
    }
    public ResponseGame gameScore(GameParams params){
        return game.getScore(params); }
    public ResponseGame leaveGame(GameParams params){
        return game.leaveGame(params);}
    private CardGame game;
    private PlayersContainer players;
}
