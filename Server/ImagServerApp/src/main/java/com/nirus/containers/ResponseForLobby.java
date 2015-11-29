package com.nirus.containers;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class ResponseForLobby {
    public ResponseForLobby(String status, Integer token) {
        _status = status;
        _token = token;
    }
    public String GetStatus(){ return _status;}
    public Integer GetToken(){ return _token;}
    private String _status;
    private Integer _token;
}
