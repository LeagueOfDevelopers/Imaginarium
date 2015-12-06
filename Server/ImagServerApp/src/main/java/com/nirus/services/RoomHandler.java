package com.nirus.services;

import com.google.inject.Singleton;
import com.nirus.containers.RequestForAChange;
import com.nirus.containers.ResponseForAChange;
import com.nirus.containers.ResponseForGameUpdate;
import com.nirus.game_logic.Room;
import com.nirus.interfaces.IRoomHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 30.11.2015.
 */
@Singleton
public class RoomHandler implements IRoomHandler {
    public RoomHandler() {
        roomBind = new HashMap<UUID, Room>();
    }

    public void CreateRoom(HashSet<UUID> playersBind) {
        Room newRoom = new Room(playersBind, roomBind);
    }

    public ResponseForGameUpdate RoomStatus(UUID token) {
        Room room = roomBind.get(token);
        return room.UpdateGame(token);
    }

    public ResponseForAChange ChangeRoomStatus(UUID token, RequestForAChange request) {
        Room room = roomBind.get(token);
        return room.ChangeGame(token, request);
    }
    private HashMap<UUID, Room> roomBind;
}
