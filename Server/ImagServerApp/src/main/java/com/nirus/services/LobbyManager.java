package com.nirus.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.Lobby;
import com.nirus.containers.ResponseForLobby;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomHandler;
import org.apache.logging.log4j.Logger;

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
            return new ResponseForLobby("WAITING", newPlayerUUID, lobbyFor4.GetCurrentPlayersCount());
        }
        if(size == 5){
            if(lobbyFor5 == null){
                lobbyFor5 = new Lobby(playersBind, size);
            }
            lobbyFor5.AddPlayer(newPlayerUUID);
            if(lobbyFor5.GetCurrentPlayersCount() >= 5){
                ChangingLobby(size);
            }
            return new ResponseForLobby("WAITING", newPlayerUUID, lobbyFor5.GetCurrentPlayersCount());
        }
        if(size == 6){
            if(lobbyFor6 == null){
                lobbyFor6 = new Lobby(playersBind, size);
            }
            lobbyFor6.AddPlayer(newPlayerUUID);
            if(lobbyFor6.GetCurrentPlayersCount() >= 6){
                ChangingLobby(size);
            }
            return new ResponseForLobby("WAITING", newPlayerUUID, lobbyFor6.GetCurrentPlayersCount());
        }
        if(size == 7){
            if(lobbyFor7 == null){
                lobbyFor7 = new Lobby(playersBind, size);
            }
            lobbyFor7.AddPlayer(newPlayerUUID);
            if(lobbyFor7.GetCurrentPlayersCount() >= 7){
                ChangingLobby(size);
            }
            return new ResponseForLobby("WAITING", newPlayerUUID, lobbyFor7.GetCurrentPlayersCount());
        }else {
            return new ResponseForLobby("ERROR_ON_JOIN", newPlayerUUID, 0);
        }
    }

    public ResponseForLobby UpdateLobby(UUID token) {
        if(playersBind.containsKey(token)){
            Lobby currentRoom = playersBind.get(token);
            if(currentRoom.GetCurrentPlayersCount() < currentRoom.GetBaseCount()){
                return new ResponseForLobby("WAITING", token, currentRoom.GetCurrentPlayersCount());
            }
            else{
                return new ResponseForLobby("READY_FOR_GAME", token, currentRoom.GetCurrentPlayersCount());
            }
        }
        else {
            return new ResponseForLobby("ERROR_IN_UPDATE", token, 0);
        }
    }
    private void ChangingLobby(Integer size){
        HashSet<UUID> setOfPlayers;
        switch (size){
            case 4:
                setOfPlayers = lobbyFor4.GetPlayersSet();
                lobbyFor4 = new Lobby(playersBind, 4);

                break;
            case 5:

                setOfPlayers = lobbyFor5.GetPlayersSet();
                lobbyFor5 = new Lobby(playersBind, 5);
                break;
            case 6:

                setOfPlayers = lobbyFor6.GetPlayersSet();
                lobbyFor6 = new Lobby(playersBind, 6);
                break;
            case 7:
                setOfPlayers = lobbyFor7.GetPlayersSet();
                lobbyFor7 = new Lobby(playersBind, 7);
                break;
            default:
                setOfPlayers = new HashSet<UUID>();
        }
        _roomHandler.CreateRoom(setOfPlayers);
    }
    private IRoomHandler _roomHandler;
    private HashMap<UUID, Lobby> playersBind;
    private Lobby lobbyFor4;
    private Lobby lobbyFor5;
    private Lobby lobbyFor6;
    private Lobby lobbyFor7;
}
