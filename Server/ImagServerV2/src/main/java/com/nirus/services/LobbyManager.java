package com.nirus.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.LobbyParams;
import com.nirus.basics.Lobby;
import com.nirus.basics.Player;
import com.nirus.containers.LobbiesContainer;
import com.nirus.containers.PlayersContainer;
import com.nirus.game.models.basics.PlayerModel;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomManager;
import com.nirus.models.LobbyModel;
import com.nirus.responses.ResponseLobby;

import java.util.ArrayList;
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
        Lobby lobby = lobbies.getLobbyBySizeAndSpeedForNewPlayer(params.getLobbyMaxSize(), params.getSpeed());
        lobby.addPlayer(newPlayer);
        lobby.getPlayers().updatePlayerInstant(newPlayer, params.getInstant());
        if(lobby.isItFull()){
            roomManager.createRoom(lobby.getPlayers(), lobby.size(), lobby.getSpeed());
        }

        ResponseLobby response = new ResponseLobby();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        LobbyModel lobbyModel = new LobbyModel();
        lobbyModel.token = newPlayer.getId().toString();
        ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
        //response.addField("token", newPlayer.getId().toString());
        //response.addField("countOfPlayers", lobbies.getLobbyByPlayer(newPlayer)
        //        .size()
         //       .toString());
        //Integer i = 0;
        for(Player player: lobbies.getLobbyByPlayer(newPlayer).getPlayers().getHashSet()){
            PlayerModel playerModel = new PlayerModel();
            playerModel.token = player.getId().toString();
            playerModels.add(playerModel);
            //response.addField("token#" + i.toString(), token.getId().toString());
            //i++;
        }
        lobbyModel.players = playerModels.toArray(new PlayerModel[]{});
        response.setResult(gson.toJson(lobbyModel));
        return response;
    }

    public ResponseLobby UpdateLobby(LobbyParams params) {
        Player player = params.getPlayer();
        checkForKick(params);
        if(lobbies.contains(player)) {
            if (lobbies.getLobbyByPlayer(player).isItFull()) {
                ResponseLobby response = new ResponseLobby();
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                LobbyModel lobbyModel = new LobbyModel();
                lobbyModel.token = player.getId().toString();
                lobbyModel.status = "READY";
                ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
                //response.addField("token", token.getId().toString());
                //response.addField("status", "READY");
                //response.addField("countOfPlayers", lobbies.getLobbyByPlayer(token).size().toString());
                //Integer i = 0;
                for(Player player1: lobbies.getLobbyByPlayer(player).getPlayers().getHashSet()){
                    PlayerModel playerModel = new PlayerModel();
                    playerModel.token = player1.getId().toString();
                    playerModels.add(playerModel);
                    //response.addField("token#" + i.toString(), player1.getId().toString());
                    //i++;
                }
                lobbyModel.players = playerModels.toArray(new PlayerModel[]{});
                response.setResult(gson.toJson(lobbyModel));
                return response;
            } else {
                lobbies.getLobbyByPlayer(player).getPlayers().updatePlayerInstant(player, params.getInstant());
                ResponseLobby response = new ResponseLobby();
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                LobbyModel lobbyModel = new LobbyModel();
                lobbyModel.token = player.getId().toString();
                lobbyModel.status = "WAITING";
                ArrayList<PlayerModel> playerModels = new ArrayList<PlayerModel>();
                //response.addField("token", token.getId().toString());
                //response.addField("status", "WAITING");
                //response.addField("countOfPlayers", lobbies.getLobbyByPlayer(token).size().toString());
                //Integer i = 0;
                for(Player player1: lobbies.getLobbyByPlayer(player).getPlayers().getHashSet()){
                    PlayerModel playerModel = new PlayerModel();
                    playerModel.token = player1.getId().toString();
                    playerModels.add(playerModel);
                    //response.addField("token#" + i.toString(), player1.getId().toString());
                    //i++;
                }
                lobbyModel.players = playerModels.toArray(new PlayerModel[]{});
                response.setResult(gson.toJson(lobbyModel));
                return response;
            }
        } else{
            ResponseLobby response = new ResponseLobby();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            LobbyModel lobbyModel = new LobbyModel();
            lobbyModel.token = player.getId().toString();
            lobbyModel.status = "ERROR";
            response.setResult(gson.toJson(lobbyModel));
            //response.addField("token", token.getId().toString());
            //response.addField("status", "ERROR");
            return response;
        }
    }

    public ResponseLobby LeaveLobby(LobbyParams params) {
        Player player = params.getPlayer();
        if(lobbies.contains(player)){
            if(!lobbies.getLobbyByPlayer(player).isItFull()){
                if(lobbies.getLobbyByPlayer(player).removePlayer(player)){
                    ResponseLobby response = new ResponseLobby();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    LobbyModel lobbyModel = new LobbyModel();
                    lobbyModel.status = "OK";
                    response.setResult(gson.toJson(lobbyModel));
                    //response.addField("status", "OK");
                    return response;
                }
            }
        }
        ResponseLobby response = new ResponseLobby();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        LobbyModel lobbyModel = new LobbyModel();
        lobbyModel.status = "ERROR";
        response.setResult(gson.toJson(lobbyModel));
        //response.addField("status", "ERROR");
        return response;
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
