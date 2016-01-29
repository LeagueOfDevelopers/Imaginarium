package com.nirus.services;

import com.nirus.api_params.LobbyParams;
import com.nirus.basics.Player;
import com.nirus.containers.LobbiesContainer;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.responses.ResponseLobby;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class LobbyManager implements ILobbyManager {
    public LobbyManager(){
        lobbies = new LobbiesContainer();
    }
    public ResponseLobby JoinLobby(LobbyParams params) {
        Player newPlayer = params.getPlayer();
        lobbies.getLobbyBySizeForNewPlayer(params.getLobbyMaxSize())
                .addPlayer(newPlayer);
        ResponseLobby response = new ResponseLobby();
        response.addField("token", newPlayer.getId().toString());
        response.addField("countOfPlayers", lobbies.getLobbyByPlayer(newPlayer)
                .size()
                .toString());
        return response;
    }

    public ResponseLobby UpdateLobby(LobbyParams params) {
        Player player = params.getPlayer();
        if(lobbies.contains(player)) {
            if (lobbies.getLobbyByPlayer(player).isItFull()) {
                ResponseLobby response = new ResponseLobby();
                response.addField("token", player.getId().toString());
                response.addField("status", "READY");
                response.addField("countOfPlayers", lobbies.getLobbyByPlayer(player).size().toString());
                return response;
            } else {
                ResponseLobby response = new ResponseLobby();
                response.addField("token", player.getId().toString());
                response.addField("status", "WAITING");
                response.addField("countOfPlayers", lobbies.getLobbyByPlayer(player).size().toString());
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
        return null;
    }
    private LobbiesContainer lobbies;
}
