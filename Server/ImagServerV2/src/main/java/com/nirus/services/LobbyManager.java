package com.nirus.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.GameParams;
import com.nirus.api_params.LobbyParams;
import com.nirus.basics.Lobby;
import com.nirus.basics.Player;
import com.nirus.containers.LobbiesContainer;
import com.nirus.containers.PlayersContainer;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomManager;
import com.nirus.responses.ResponseLobby;

import java.util.HashSet;

/**
 * Created by ndiezel on 27.01.2016.
 */
@Singleton
public class LobbyManager implements ILobbyManager {
    @Inject
    public LobbyManager(IRoomManager roomManager){
        lobbies = new LobbiesContainer();
        this.roomManager = roomManager;
    }
    public ResponseLobby JoinLobby(LobbyParams params) {
        Player newPlayer = params.getPlayer();
        Lobby lobby = lobbies.getLobbyBySizeForNewPlayer(params.getLobbyMaxSize());
        lobby.addPlayer(newPlayer);
        lobby.getPlayers().updatePlayerInstant(newPlayer, params.getInstant());
        if(lobby.isItFull()){
            roomManager.createRoom(lobby.getPlayers(), lobby.size());
        }
        ResponseLobby response = new ResponseLobby();
        response.addField("token", newPlayer.getId().toString());
        response.addField("countOfPlayers", lobbies.getLobbyByPlayer(newPlayer)
                .size()
                .toString());
        Integer i = 0;
        for(Player player: lobbies.getLobbyByPlayer(newPlayer).getPlayers().getHashSet()){
            response.addField("player#" + i.toString(), player.getId().toString());
            i++;
        }
        return response;
    }

    public ResponseLobby UpdateLobby(LobbyParams params) {
        Player player = params.getPlayer();
        checkForKick(params);
        if(lobbies.contains(player)) {
            if (lobbies.getLobbyByPlayer(player).isItFull()) {
                ResponseLobby response = new ResponseLobby();
                response.addField("token", player.getId().toString());
                response.addField("status", "READY");
                response.addField("countOfPlayers", lobbies.getLobbyByPlayer(player).size().toString());
                Integer i = 0;
                for(Player player1: lobbies.getLobbyByPlayer(player).getPlayers().getHashSet()){
                    response.addField("player#" + i.toString(), player1.getId().toString());
                    i++;
                }
                return response;
            } else {
                lobbies.getLobbyByPlayer(player).getPlayers().updatePlayerInstant(player, params.getInstant());
                ResponseLobby response = new ResponseLobby();
                response.addField("token", player.getId().toString());
                response.addField("status", "WAITING");
                response.addField("countOfPlayers", lobbies.getLobbyByPlayer(player).size().toString());
                Integer i = 0;
                for(Player player1: lobbies.getLobbyByPlayer(player).getPlayers().getHashSet()){
                    response.addField("player#" + i.toString(), player1.getId().toString());
                    i++;
                }
                return response;
            }
        } else{
            ResponseLobby response = new ResponseLobby();
            response.addField("token", player.getId().toString());
            response.addField("status", "ERROR");
            return response;
        }
    }

    public ResponseLobby LeaveLobby(LobbyParams params) {
        Player player = params.getPlayer();
        if(lobbies.contains(player)){
            if(!lobbies.getLobbyByPlayer(player).isItFull()){
                if(lobbies.getLobbyByPlayer(player).removePlayer(player)){
                    ResponseLobby response = new ResponseLobby();
                    response.addField("status", "OK");
                    return response;
                } else {
                    ResponseLobby response = new ResponseLobby();
                    response.addField("status", "ERROR");
                    return response;
                }
            } else {
                ResponseLobby response = new ResponseLobby();
                response.addField("status", "ERROR");
                return response;
            }
        } else{
            ResponseLobby response = new ResponseLobby();
            response.addField("status", "ERROR");
            return response;
        }
    }
    private void checkForKick(LobbyParams params){
        Lobby lobby = lobbies.getLobbyByPlayer(params.getPlayer());
        if(lobby == null || lobby.isItFull()){
            return;
        }
        PlayersContainer players = lobby.getPlayers();
        HashSet<Player> playersSet = new HashSet<Player>(players.getHashSet());
        for(Player player: playersSet){
            if(players.getPlayerInstant(player).plusSeconds(30).isBefore(params.getInstant())){
                players.removePlayer(player);
            }
        }
    }
    private LobbiesContainer lobbies;
    private IRoomManager roomManager;
}
