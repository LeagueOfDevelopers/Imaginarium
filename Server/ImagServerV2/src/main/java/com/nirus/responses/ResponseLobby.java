package com.nirus.responses;

import com.google.gson.JsonObject;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class ResponseLobby {
    public ResponseLobby(){
        response = new JsonObject();
    }
    public void addField(String name, String value){
        response.addProperty(name, value);
    }
    public String getResponse(){
        return response.getAsString();
    }
    private JsonObject response;
}
