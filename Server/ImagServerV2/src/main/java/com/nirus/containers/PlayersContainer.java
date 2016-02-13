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
        for(Player player1: players){
            if(player1.getId().equals(player.getId())){
                return false;
            }
        }
        return players.add(player);
    }
    public boolean removePlayer(Player player){
        for(Player player1: players){
            if(player.getId().equals(player1.getId())){
                return players.remove(player1);
            }
        }
        return players.remove(player);
    }
    public boolean contains(Player player){
        for(Player player1: players){
            if(player1.equals(player)){
                return true;
            }
        }
        return false;
    }
    public Integer size(){
        return players.size();
    }
    public HashSet<Player> getHashSet(){
        return players;
    }
    public void clear(){
        players.clear();
    }
    private HashSet<Player> players;
}
