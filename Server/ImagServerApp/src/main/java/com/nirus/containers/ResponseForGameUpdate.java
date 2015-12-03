package com.nirus.containers;

import com.google.gson.JsonObject;

import java.util.HashSet;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class ResponseForGameUpdate {
    public ResponseForGameUpdate(String status) {
        _status = status;
        firstStage = new HashSet<Integer>();
    }
    public String GetStatus(){ return _status;}
    public HashSet<Integer> GetCards(){ return  firstStage;}
    public void SetFirstStage(HashSet<Integer> cards){ firstStage = cards;}
    public void SetSecondStage(Boolean head){ _head = head;}
    public JsonObject GetAsJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("status", _status);
        if(_status == "FIRST_STAGE"){
            Integer[] cards = (Integer[]) firstStage.toArray();
            for(Integer i = 0; i < cards.length; i++){
                response.addProperty("card#"+i.toString(), cards[i]);
            }
        }
        if(_status == "SECOND_STAGE"){
            response.addProperty("head", _head.toString());
        }
        return response;
    }
    private Boolean _head;
    private String _status;
    private HashSet<Integer> firstStage;
}
