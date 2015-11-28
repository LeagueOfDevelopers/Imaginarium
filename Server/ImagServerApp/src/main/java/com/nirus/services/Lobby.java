package com.nirus.services;

import com.nirus.containers.Player;
import com.nirus.containers.ResponseForLobby;
import com.nirus.containers.Room;
import com.nirus.interfaces.ILobby;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class Lobby implements ILobby {
    public Lobby() {
        players = new ArrayList<Player>();
        howManyTimesRequested = 0;
    }

    public ResponseForLobby JoinLobby(Player joiningPlayer) {
        players.add(joiningPlayer);
        return getResponseForLobby();
    }

    public ResponseForLobby UpdateLobby() {
        return getResponseForLobby();
    }

    private ResponseForLobby getResponseForLobby() {
        if(IsLobbyFull()){
            howManyTimesRequested++;
            if(howManyTimesRequested >= 6){
                players.clear();
                howManyTimesRequested = 0;
            }
            return new ResponseForLobby(
                    "JOINING",
                    new Room());
        }
        else{
            return new ResponseForLobby(
                    "WAITING",
                    null);
        }
    }

    private boolean IsLobbyFull(){
        return players.size() >= 6;
    }
    private ArrayList<Player> players;
    private int howManyTimesRequested;
}
