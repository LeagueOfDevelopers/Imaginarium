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
        gameOverBind = new HashMap<Room, HashSet<UUID>>();
    }

    public void CreateRoom(HashSet<UUID> playersBind) {
        Room newRoom = new Room(playersBind, roomBind);
    }

    public ResponseForGameUpdate RoomStatus(UUID token) {
        Room room = roomBind.get(token);
        ResponseForGameUpdate response = room.UpdateGame(token);
        if(response.GetAsJSON().get("stage").getAsString() == "GAME_OVER"){
            gameOverBind.get(room).add(token);
            if(gameOverBind.get(room).size() > 5){
                roomBind.remove(room);
                gameOverBind.remove(room);
            }
        }
        return response;
    }

    public ResponseForAChange ChangeRoomStatus(UUID token, RequestForAChange request) {
        Room room = roomBind.get(token);
        return room.ChangeGame(token, request);
    }
    private HashMap<UUID, Room> roomBind;
    private HashMap<Room, HashSet<UUID>> gameOverBind;
}
