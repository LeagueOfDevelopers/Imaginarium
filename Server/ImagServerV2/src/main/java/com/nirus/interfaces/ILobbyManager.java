package com.nirus.interfaces;

import com.nirus.api_params.LobbyParams;
import com.nirus.responses.ResponseLobby;

/**
 * Created by ndiezel on 26.01.2016.
 */
public interface ILobbyManager {
    ResponseLobby JoinLobby(LobbyParams params);
    ResponseLobby UpdateLobby(LobbyParams params);
    ResponseLobby LeaveLobby(LobbyParams params);
}
