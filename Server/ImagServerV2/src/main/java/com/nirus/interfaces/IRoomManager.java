package com.nirus.interfaces;

import com.nirus.api_params.GameParams;
import com.nirus.containers.PlayersContainer;
import com.nirus.responses.ResponseGame;

/**
 * Created by ndiezel on 28.01.2016.
 */
public interface IRoomManager {
    void createRoom(PlayersContainer players, Integer size);
    ResponseGame getGameStatus(GameParams params);
    ResponseGame updateGameStatus(GameParams params);
    ResponseGame getGameScore(GameParams params);
    ResponseGame leaveGame(GameParams params);
}
