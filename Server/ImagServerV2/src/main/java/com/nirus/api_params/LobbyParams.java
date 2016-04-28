package com.nirus.api_params;

import com.nirus.basics.Player;

import java.time.Instant;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class LobbyParams {
    public LobbyParams(Player player)
    {
        this.player = player;
        instant = Instant.now();
    }
    public Instant getInstant(){ return instant;}
    public Player getPlayer(){
        return player;
    }
    public void setLobbyMaxSize(Integer size){
        lobbyMaxSize = size;
    }
    public Integer getLobbyMaxSize(){
        return lobbyMaxSize;
    }
    public Integer getSpeed() {
        return speed;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    private Integer lobbyMaxSize;
    private Player player;
    private Instant instant;
    private Integer speed;
}
