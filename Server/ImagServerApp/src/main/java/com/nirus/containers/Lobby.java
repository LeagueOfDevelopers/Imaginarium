package com.nirus.containers;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 29.11.2015.
 */
public class Lobby {
    public Lobby(HashMap<UUID, Lobby> playersBind){
        _playersBind = playersBind;
        _playersSet = new HashSet<UUID>();
    }
    public void AddPlayer(UUID newPlayer){
        _playersBind.put(newPlayer, this);
        _playersSet.add(newPlayer);
    }
    public Integer GetCurrentPlayersCount(){
        return _playersSet.size();
    }
    public HashSet<UUID> GetPlayersSet(){
        return _playersSet;
    }
    private HashMap<UUID, Lobby> _playersBind;
    private HashSet<UUID> _playersSet;
}