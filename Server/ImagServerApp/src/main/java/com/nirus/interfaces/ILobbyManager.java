package com.nirus.interfaces;

import com.nirus.containers.Player;
import com.nirus.containers.ResponseForLobby;

/**
 * Created by ndiezel on 28.11.2015.
 */
public interface ILobbyManager {
    ResponseForLobby JoinLobby() throws InterruptedException;
    ResponseForLobby UpdateLobby();
}
