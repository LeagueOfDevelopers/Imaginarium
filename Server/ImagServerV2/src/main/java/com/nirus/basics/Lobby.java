package com.nirus.basics;

import com.nirus.containers.PlayersContainer;

import java.util.HashSet;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class Lobby {
    public Lobby(Integer maxSize, Integer speed){
        players = new PlayersContainer();
        this.maxSize = maxSize;
        this.speed = speed;
    }
    public boolean addPlayer(Player player){
        return players.addPlayer(player);
}
    public boolean removePlayer(Player player){
        return players.removePlayer(player);
    }
    public PlayersContainer getPlayers(){ return players;}
    public boolean contains(Player player){
        return players.contains(player);
    }
    public boolean isItFull(){
        return maxSize.equals(players.size());
    }
    public Integer size(){
        return players.size();
    }
    public Integer getSpeed(){
        return speed;
    }
    public Integer maxSize(){
        return maxSize;
    }
    private PlayersContainer players;
    private Integer maxSize;
    private Integer speed;
}
