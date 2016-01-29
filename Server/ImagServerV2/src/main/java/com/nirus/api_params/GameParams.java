package com.nirus.api_params;

import com.nirus.basics.Player;

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
    private Player player;
}
