package com.nirus.threads;

import com.nirus.containers.Room;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by ndiezel on 29.11.2015.
 */
public class Lobby implements Runnable {
    public Lobby(HashMap<Integer, Lobby> playersBind, ArrayBlockingQueue<Integer> playersQueue, ArrayBlockingQueue<Integer> playersCountForI){
        _playersBind = playersBind;
        _playersQueue = playersQueue;
        _playersCount = 0;
        _playersCountForI = playersCountForI;
    }

    public void run() {
        while (_playersCount < 6){
            Integer newPlayer = 0;
            try {
                newPlayer = _playersQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _playersBind.put(newPlayer, this);
            _playersCount++;
            try {
                _playersCountForI.put(_playersCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private HashMap<Integer, Lobby> _playersBind;
    private ArrayBlockingQueue<Integer> _playersQueue;
    private Integer _playersCount;
    private ArrayBlockingQueue<Integer> _playersCountForI;
    private Thread lobbyThread;
}
