package com.nirus.game_logic;

import com.nirus.containers.RequestForAChange;
import com.nirus.containers.ResponseForAChange;
import com.nirus.containers.ResponseForGameUpdate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class Room {
    public Room(HashSet<UUID> players, HashMap<UUID, Room> roomBind) {
        _players = players;
        for (UUID player: _players) {
            roomBind.put(player, this);
        }
        game = new CardPart(players);
    }
    public ResponseForGameUpdate UpdateGame(UUID token){ return game.GetGameStatus(token);}
    public ResponseForAChange ChangeGame(UUID token,
                                         RequestForAChange requestForAChange)
    {
        return game.UpdateGameSituation(token, requestForAChange);
    }
    private CardPart game;
    private HashSet<UUID> _players;
}