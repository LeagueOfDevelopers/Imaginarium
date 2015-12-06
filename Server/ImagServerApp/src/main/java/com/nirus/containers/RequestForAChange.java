package com.nirus.containers;

import com.google.gson.JsonObject;

/**
 * Created by ndiezel on 05.12.2015.
 */
public class RequestForAChange {
    public RequestForAChange(JsonObject json) {
        jsonObject = json;
    }
    public JsonObject GetJson(){ return jsonObject;}
    JsonObject jsonObject;
}
