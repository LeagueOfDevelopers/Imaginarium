package com.nirus.containers;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class ResponseForLobby {
    public ResponseForLobby(String status, Room room) {
        _status = status;
        _room = null;
    }
    public String GetStatus(){ return _status;}
    public Room GetRoom(){ return _room;}
    private String _status;
    private Room _room;
}
