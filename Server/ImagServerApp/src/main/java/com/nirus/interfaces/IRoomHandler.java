package com.nirus.interfaces;

import java.util.Map;

/**
 * Created by ndiezel on 28.11.2015.
 */
public interface IRoomHandler {

    Map<String, String> LeaveLobby(Map<String, String> request);

    Map<String, String> MakeTurn(Map<String, String> request);
    Map<String, String> UpdateGame(Map<String, String> request);

}