package com.nirus.containers;

import com.google.gson.JsonObject;
import com.google.inject.Inject;

import java.util.HashSet;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class ResponseForGameUpdate {
    public ResponseForGameUpdate(String status, HashSet<Integer> cards) {
        _status = status;
        firstStage = cards;
    }
    public String GetStatus(){ return _status;}
    public HashSet<Integer> GetCards(){ return  firstStage;}
    public JsonObject GetAsJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("status", _status);
        Integer[] cards = (Integer[]) firstStage.toArray();
        for(int i = 0; i < cards.length; i++){
            response.addProperty("card", cards[i]);
        }
        return response;
    }
    private String _status;
    private HashSet<Integer> firstStage;
}
