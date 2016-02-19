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

        for(Player player1: responses.keySet()){
            if(player.getId().equals(player1.getId())){
                if(!hasItSendRequests(player)){
                    requestsFromPlayers.add(player);
                }
                return responses.get(player1);
            }
        }
        return null;
    }
    public ResponseGame getResponseByPlayerSafe(Player player){
        for(Player player1: responses.keySet()){
            if(player.getId().equals(player1.getId())){
                return responses.get(player1);
            }
        }
        return null;
    }
    private boolean hasItSendRequests(Player player){
        for(Player player1: requestsFromPlayers){
            if(player1.getId().equals(player.getId())){
                return true;
            }
        }
        return false;
    }
    private HashSet<Player> requestsFromPlayers;
    private HashMap<Player, ResponseGame> responses;
}
