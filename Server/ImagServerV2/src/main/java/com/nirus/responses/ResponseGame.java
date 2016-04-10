package com.nirus.responses;

import com.google.gson.JsonObject;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class ResponseGame {
    public ResponseGame(){
        response = new JsonObject();
    }
    public void addField(String name, String value){
        response.addProperty(name, value);
    }
    public void updateField(String name, String value){
        response.remove(name);
        response.addProperty(name, value);
    }
    public String getField(String key){ return response.get(key).getAsString();}
    public String getResponse(){
        return response.toString();
    }
    public JsonObject getJson(){ return response; }
    private JsonObject response;
}
