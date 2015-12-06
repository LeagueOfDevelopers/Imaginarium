package com.nirus.containers;

import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class ResponseForGameUpdate {
    public ResponseForGameUpdate(String status) {
        _status = status;
        firstStage = new HashSet<Integer>();
    }
    public void SetFirstStage(HashSet<Integer> cards, Boolean head){
        firstStage = cards;
        _head = head;
    }
    public void SetSecondStage(String text, Integer cardID){
        this.text = text;
        this.cardID = cardID;
    }
    public JsonObject GetAsJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("status", _status);
        if(_status == "FIRST_STAGE"){
            response.addProperty("is_head", _head.toString());
            response.addProperty("score", score.toString());
            Iterator<Integer> iterator = firstStage.iterator();
            for(Integer i = 0; iterator.hasNext(); i++){
                response.addProperty("card#"+i.toString(), iterator.next());
            }
        } else if(_status == "SECOND_STAGE"){
            response.addProperty("card", cardID.toString());
            response.addProperty("text", text);
        }
        return response;
    }
    private Integer score = 0;
    private Boolean _head;
    private String _status;
    private HashSet<Integer> firstStage;
    private String text;
    private Integer cardID;
}