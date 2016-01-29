package com.nirus.interfaces;

import com.nirus.api_params.LobbyParams;
import com.nirus.responses.ResponseLobby;

/**
 * Created by ndiezel on 26.01.2016.
 */
public interface ILobbyManager {
    public ResponseLobby JoinLobby(LobbyParams params);
    public ResponseLobby UpdateLobby(LobbyParams params);
    public ResponseLobby LeaveLobby(LobbyParams params);
}
