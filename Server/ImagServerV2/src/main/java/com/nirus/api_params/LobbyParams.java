package com.nirus.api_params;

import com.nirus.basics.Player;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class LobbyParams {
    public LobbyParams(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    public void setLobbyMaxSize(Integer size){
        lobbyMaxSize = size;
    }
    public Integer getLobbyMaxSize(){
        return lobbyMaxSize;
    }
    private Integer lobbyMaxSize;
    private Player player;
}
