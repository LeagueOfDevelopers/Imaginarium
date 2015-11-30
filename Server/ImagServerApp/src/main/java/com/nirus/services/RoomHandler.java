package com.nirus.services;

import com.nirus.containers.ResponseForAChange;
import com.nirus.interfaces.IRoomHandler;
import com.nirus.interfaces.ResponseForGameUpdate;
import com.nirus.threads.Lobby;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class RoomHandler implements IRoomHandler {
    public void CreateRoom(HashSet<UUID> playersBind) {

    }

    public ResponseForGameUpdate RoomStatus(UUID token) {
        return null;
    }

    public ResponseForAChange ChangeRoomStatus(UUID token) {
        return null;
    }
}
