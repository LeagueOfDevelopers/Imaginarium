package com.nirus.containers;

import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class ResponseForLobby {
    public ResponseForLobby(String status, UUID token) {
        _status = status;
        _token = token;
    }
    public String GetStatus(){ return _status;}
    public UUID GetToken(){ return _token;}
    private String _status;
    private UUID _token;
}
