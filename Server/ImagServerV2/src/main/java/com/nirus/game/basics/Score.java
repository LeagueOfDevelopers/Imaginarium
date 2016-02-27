package com.nirus.game.basics;

/**
 * Created by ndiezel on 07.02.2016.
 */
public class Score {
    public Score(Integer score){
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private Integer score;
}
