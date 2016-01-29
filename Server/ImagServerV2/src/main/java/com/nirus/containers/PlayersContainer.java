package com.nirus.containers;

import com.nirus.basics.Player;

import java.util.HashSet;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class PlayersContainer {
    public PlayersContainer(){
        players = new HashSet<Player>();
    }
    public boolean addPlayer(Player player){
        return players.add(player);
    }
    public boolean removePlayer(Player player){
        return players.remove(player);
    }
    public boolean contains(Player player){
        return players.contains(player);
    }
    public Integer size(){
        return players.size();
    }
    public HashSet<Player> getHashSet(){
        return players;
    }
    private HashSet<Player> players;
}
