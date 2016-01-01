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
        _roomHandler = roomHandler;
    }

    public ResponseForLobby JoinLobby(Integer size) {
        UUID newPlayerUUID = UUID.randomUUID();
        if(size == 4){
            if(lobbyFor4 == null){
                lobbyFor4 = new Lobby(playersBind, size);
            }
            lobbyFor4.AddPlayer(newPlayerUUID);
            if(lobbyFor4.GetCurrentPlayersCount() >= 4){
                ChangingLobby(size);
            }
        }
        if(size == 5){
            if(lobbyFor5 == null){
                lobbyFor5 = new Lobby(playersBind, size);
            }
            lobbyFor5.AddPlayer(newPlayerUUID);
            if(lobbyFor5.GetCurrentPlayersCount() >= 5){
                ChangingLobby(size);
            }
        }
        if(size == 6){
            if(lobbyFor6 == null){
                lobbyFor6 = new Lobby(playersBind, size);
            }
            lobbyFor6.AddPlayer(newPlayerUUID);
            if(lobbyFor6.GetCurrentPlayersCount() >= 6){
                ChangingLobby(size);
            }
        }
        if(size == 7){
            if(lobbyFor7 == null){
                lobbyFor7 = new Lobby(playersBind, size);
            }
            lobbyFor7.AddPlayer(newPlayerUUID);
            if(lobbyFor7.GetCurrentPlayersCount() >= 7){
                ChangingLobby(size);
            }
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
    private void ChangingLobby(Integer size){
        Lobby oldLobby = new Lobby(playersBind, 0);
        switch (size){
            case 4:
                oldLobby = lobbyFor4;
                lobbyFor4 = new Lobby(playersBind, 4);
                break;
            case 5:
                oldLobby = lobbyFor5;
                lobbyFor5 = new Lobby(playersBind, 5);
                break;
            case 6:
                oldLobby = lobbyFor6;
                lobbyFor6 = new Lobby(playersBind, 6);
                break;
            case 7:
                oldLobby = lobbyFor7;
                lobbyFor7 = new Lobby(playersBind, 7);
                break;
        }
        HashSet<UUID> setOfPlayers = oldLobby.GetPlayersSet();
        _roomHandler.CreateRoom(setOfPlayers);
    }
    private IRoomHandler _roomHandler;
    private HashMap<UUID, Lobby> playersBind;
    private Lobby lobbyFor4;
    private Lobby lobbyFor5;
    private Lobby lobbyFor6;
    private Lobby lobbyFor7;
}
