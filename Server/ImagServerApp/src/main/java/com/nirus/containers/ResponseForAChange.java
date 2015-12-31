package com.nirus.containers;

import com.google.gson.JsonObject;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class ResponseForAChange {
    public ResponseForAChange(String status){
        this.status = status;
    }
    public JsonObject GetAsJson(){
        JsonObject response = new JsonObject();
        response.addProperty("status", status);
        return response;
    }
    private String status;
}
