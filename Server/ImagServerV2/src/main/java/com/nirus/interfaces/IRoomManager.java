package com.nirus.interfaces;

import com.nirus.api_params.GameParams;
import com.nirus.containers.PlayersContainer;
import com.nirus.responses.ResponseGame;

/**
 * Created by ndiezel on 28.01.2016.
 */
public interface IRoomManager {
    public void createRoom(PlayersContainer players);
    public ResponseGame getGameStatus(GameParams params);
    public ResponseGame updateGameStatus(GameParams params);
}
