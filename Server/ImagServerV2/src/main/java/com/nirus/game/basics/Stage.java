package com.nirus.game.basics;

/**
 * Created by ndiezel on 09.02.2016.
 */
public class Stage {
    public Stage(){
        stage = 0;
    }
    public void nextStage(){
        stage++;
        if(stage == 4){
            stage = 0;
        }
    }
    public Integer getStage(){
        return stage;
    }
    Integer stage;
}
