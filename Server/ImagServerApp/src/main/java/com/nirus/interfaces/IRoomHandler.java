package com.nirus.interfaces;

import com.nirus.containers.ResponseForAChange;
import com.nirus.threads.Lobby;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public interface IRoomHandler {
    void CreateRoom(HashSet<UUID> playersBind);
    ResponseForGameUpdate RoomStatus(UUID token);
    ResponseForAChange ChangeRoomStatus(UUID token);

}