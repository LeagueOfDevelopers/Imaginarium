package com.nirus.containers;

import com.nirus.basics.Lobby;
import com.nirus.basics.Player;

import java.util.ArrayList;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class LobbiesContainer {
    public LobbiesContainer(){
        lobbies = new ArrayList<Lobby>();
    }
    public void addLobby(Lobby lobby){
        lobbies.add(lobby);
    }
    public Lobby getLobbyByPlayer(Player player){
        for (Lobby lobby:
             lobbies) {
            if (lobby.contains(player)){
                return lobby;
            }
        }
        return null;
    }
    public Lobby getLobbyBySizeAndSpeedForNewPlayer(Integer size, Integer speed){
        for (Lobby lobby:
                lobbies) {
            if (lobby.maxSize().equals(size) && lobby.getSpeed().equals(speed) && !lobby.isItFull()){
                return lobby;
            }
        }
        Lobby newLobby = new Lobby(size, speed);
        addLobby(newLobby);
        return newLobby;
    }
    public boolean contains(Player player){
        for (Lobby lobby:
             lobbies) {
            if(lobby.contains(player)){
                return true;
            }
        }
        return false;
    }
    private ArrayList<Lobby> lobbies;
}
