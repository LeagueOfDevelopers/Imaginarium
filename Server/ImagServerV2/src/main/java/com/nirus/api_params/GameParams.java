package com.nirus.api_params;

import com.nirus.basics.Player;
import com.nirus.game.basics.Card;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class GameParams {
    public GameParams(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Integer id) {
        this.card = new Card(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    private Player player;
}
