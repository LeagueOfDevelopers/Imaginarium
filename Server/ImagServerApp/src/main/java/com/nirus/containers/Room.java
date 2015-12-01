package com.nirus.containers;

import com.nirus.game_logic.CardPart;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class Room {
    public Room(HashSet<UUID> players, HashMap<UUID, Room> roomBind) {
        _players = players;
        _roomBind = roomBind;
        for (UUID player: _players) {
            _roomBind.put(player, this);
        }
        game = new CardPart();
    }
    public ResponseForGameUpdate UpdateGame(UUID token){
        return game.GetGameStatus(token);
    }
    private CardPart game;
    private HashSet<UUID> _players;
    private HashMap<UUID, Room> _roomBind;
}