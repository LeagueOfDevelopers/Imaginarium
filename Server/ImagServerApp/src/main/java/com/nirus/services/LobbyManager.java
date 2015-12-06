package com.nirus.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.Lobby;
import com.nirus.containers.ResponseForLobby;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
@Singleton
public class LobbyManager implements ILobbyManager {
    @Inject
    public LobbyManager(IRoomHandler roomHandler) {
        playersBind = new HashMap<UUID, Lobby>();
        lobby = new Lobby(playersBind);
        _roomHandler = roomHandler;
    }

    public ResponseForLobby JoinLobby() {
        UUID newPlayerUUID = UUID.randomUUID();
        lobby.AddPlayer(newPlayerUUID);
        if(lobby.GetCurrentPlayersCount() >= 6){
            ChangingLobby();
        }
        return new ResponseForLobby("WAITING", newPlayerUUID);
    }

    public ResponseForLobby UpdateLobby(UUID token) {
        if(playersBind.containsKey(token)){
            if(playersBind.get(token).GetCurrentPlayersCount() < 6){
                return new ResponseForLobby("WAITING", token);
            }
            else{
                return new ResponseForLobby("READY_FOR_GAME", token);
            }
        }
        else {
            return new ResponseForLobby("ERROR_IN_UPDATE", token);
        }
    }
    private void ChangingLobby(){
        Lobby oldLobby = lobby;
        lobby = new Lobby(playersBind);
        HashSet<UUID> setOfPlayers = oldLobby.GetPlayersSet();
        _roomHandler.CreateRoom(setOfPlayers);
    }
    private IRoomHandler _roomHandler;
    private HashMap<UUID, Lobby> playersBind;
    private Lobby lobby;
}
