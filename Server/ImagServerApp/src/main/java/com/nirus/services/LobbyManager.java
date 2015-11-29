package com.nirus.services;

import com.nirus.containers.Player;
import com.nirus.containers.ResponseForLobby;
import com.nirus.containers.Room;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.threads.Lobby;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class LobbyManager implements ILobbyManager {
    public LobbyManager() {
        playersQueue = new ArrayBlockingQueue<Integer>(100);
        playersBind = new HashMap<Integer, Lobby>();
        playersCountQueue = new ArrayBlockingQueue<Integer>(4);
        currentCount = 0;
        lobby = new Lobby(playersBind, playersQueue, playersCountQueue);
        lobbyThread = new Thread(lobby);
    }

    public ResponseForLobby JoinLobby() throws InterruptedException {
        Random random = new Random();
        Integer newPlayerToken = random.nextInt();
        playersQueue.put(newPlayerToken);
        currentCount = playersCountQueue.take();
        if(currentCount >= 6){
            lobby = new Lobby(playersBind, playersQueue, playersCountQueue);
            lobbyThread = new Thread(lobby);
        }
        return new ResponseForLobby(currentCount.toString(), newPlayerToken);
    }

    public ResponseForLobby UpdateLobby() {
        return new ResponseForLobby("Not done yet", 0);
    }
    private ArrayBlockingQueue<Integer> playersQueue;
    private ArrayBlockingQueue<Integer> playersCountQueue;
    private HashMap<Integer, Lobby> playersBind;
    private Thread lobbyThread;
    private Integer currentCount;
    private Lobby lobby;
}
