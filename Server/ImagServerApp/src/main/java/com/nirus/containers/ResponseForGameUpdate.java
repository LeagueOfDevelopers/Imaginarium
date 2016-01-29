package com.nirus.containers;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by ndiezel on 30.11.2015.
 */
public class ResponseForGameUpdate {
    public ResponseForGameUpdate(String status) {
        _status = status;
        cards = new HashSet<Card>();
    }
    public void SetFirstStage(HashSet<Card> cards, Boolean head, Integer score){
        this.cards = cards;
        _head = head;
        this.score = score;
    }
    public void SetStage(Integer stage){ this.stage = stage;}
    public void SetSecondStage(String text){
        this.text = text;
    }
    public void SetThirdStage(HashSet<Card> cards){
        this.cards = cards;
    }
    public void SetFourthStage(HashMap<Card, Integer> cardScores, Card card){
        cardHead = card;
        this.cardScores = cardScores;
    }
    public JsonObject GetAsJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("status", _status);
        if(_status == "OK"){
            if(stage == 1){
                response.addProperty("stage", stage);
                response.addProperty("is_head", _head.toString());
                response.addProperty("score", score.toString());
                Iterator<Card> iterator = cards.iterator();
                for(Integer i = 0; iterator.hasNext(); i++){
                    response.addProperty("card#"+i.toString(), iterator.next().GetId());
                }
            }
            if (stage == 2){
                response.addProperty("stage", stage);
                response.addProperty("text", text);
            }
            if(stage == 3){
                response.addProperty("stage", stage);
                Iterator<Card> iterator = cards.iterator();
                for(Integer i = 0; iterator.hasNext(); i++){
                    response.addProperty("card#"+i.toString(), iterator.next().GetId());
                }
            }
            if(stage == 4){
                response.addProperty("stage", stage);
                HashSet<Card> cards = new HashSet<Card>(cardScores.keySet());
                Integer i = 0;
                response.addProperty("cardOfHead", cardHead.GetId());
                for(Card card : cards){
                    response.addProperty("card#" + i.toString(), card.GetId());
                    response.addProperty("vote#" + i.toString(), cardScores.get(card));
                    i++;
                }
            }
        }
        return response;
    }
    private Integer score = 0;
    public Integer stage;
    private Boolean _head;
    private String _status;
    private HashSet<Card> cards;
    private Card cardHead;
    private HashMap<Card, Integer> cardScores;
    private String text;
}