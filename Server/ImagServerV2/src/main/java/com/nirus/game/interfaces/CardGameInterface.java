package com.nirus.game.interfaces;

import com.nirus.api_params.GameParams;
import com.nirus.responses.ResponseGame;

/**
 * Created by ndiezel on 22.04.2016.
 */
public interface CardGameInterface {
    ResponseGame getStatus(GameParams params);
    ResponseGame setUpdate(GameParams params);
    ResponseGame getScore(GameParams params);
    ResponseGame leaveGame(GameParams params);
}
