package com.nirus.interfaces;

import com.nirus.containers.ResponseForLobby;

import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public interface ILobbyManager {
    ResponseForLobby JoinLobby(Integer size);
    ResponseForLobby UpdateLobby(UUID token);
}
