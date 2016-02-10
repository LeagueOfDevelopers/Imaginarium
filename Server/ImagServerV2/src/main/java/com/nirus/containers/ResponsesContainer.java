package com.nirus.containers;

import com.nirus.basics.Player;
import com.nirus.responses.ResponseGame;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ndiezel on 06.02.2016.
 */
public class ResponsesContainer {
    public ResponsesContainer(){
        responses = new HashMap<Player, ResponseGame>();
        requestsFromPlayers = new HashSet<Player>();
    }
    public void addResponse(ResponseGame responseGame, Player player){
        responses.put(player, responseGame);
    }
    public void clear(){
        requestsFromPlayers.clear();
        responses.clear();
    }
    public Integer getAmmountOfRequest(){
        return requestsFromPlayers.size();
    }
    public ResponseGame getResponseByPlayer(Player player){
        requestsFromPlayers.add(player);
        return responses.get(player);
    }
    public ResponseGame getResponseByPlayerSafe(Player player){
        return responses.get(player);
    }
    private HashSet<Player> requestsFromPlayers;
    private HashMap<Player, ResponseGame> responses;
}
