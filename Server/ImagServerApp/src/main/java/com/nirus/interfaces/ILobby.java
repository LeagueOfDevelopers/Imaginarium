package com.nirus.interfaces;

import com.nirus.containers.Player;
import com.nirus.containers.ResponseForLobby;

/**
 * Created by ndiezel on 28.11.2015.
 */
public interface ILobby {
    ResponseForLobby JoinLobby(Player joiningPlayer);
    ResponseForLobby UpdateLobby();
}
