package com.nirus.services;

import com.google.inject.Singleton;
import com.nirus.api_params.GameParams;
import com.nirus.basics.Room;
import com.nirus.containers.PlayersContainer;
import com.nirus.containers.RoomsContainer;
import com.nirus.interfaces.IRoomManager;
import com.nirus.responses.ResponseGame;

/**
 * Created by ndiezel on 28.01.2016.
 */
@Singleton
public class RoomManager implements IRoomManager {
    public RoomManager(){
        rooms = new RoomsContainer();

    }
    public void createRoom(PlayersContainer players, Integer size, Integer speed) {
        rooms.addRoom(new Room(players, size, speed));
    }

    public ResponseGame getGameStatus(GameParams params) {
        ResponseGame response = rooms
                .getRoomByPlayer(params.getPlayer())
                .gameStatus(params);
        return response;
    }

    public ResponseGame updateGameStatus(GameParams params) {
        ResponseGame response = rooms
                .getRoomByPlayer(params.getPlayer())
                .gameUpdate(params);
        return response;
    }

    public ResponseGame leaveGame(GameParams params) {
        ResponseGame response = rooms
                .getRoomByPlayer(params.getPlayer())
                .leaveGame(params);
        return response;
    }

    public ResponseGame getGameScore(GameParams params) {
        ResponseGame response = rooms
                .getRoomByPlayer(params.getPlayer())
                .gameScore(params);
        return response;
    }

    private RoomsContainer rooms;
}
