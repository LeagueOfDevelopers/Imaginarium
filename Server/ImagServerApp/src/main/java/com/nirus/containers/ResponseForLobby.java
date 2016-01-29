package com.nirus.containers;

import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class ResponseForLobby {
    public ResponseForLobby(String status, UUID token, Integer size) {
        _status = status;
        _token = token;
        ammountOfPeople = size;
    }
    public JsonObject GetAsJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("status", GetStatus());
        response.addProperty("token",GetToken().toString());
        response.addProperty("size", ammountOfPeople);
        return response;
    }
    public String GetStatus(){ return _status;}
    public UUID GetToken(){ return _token;}
    private String _status;
    private UUID _token;
    private Integer ammountOfPeople;
}
