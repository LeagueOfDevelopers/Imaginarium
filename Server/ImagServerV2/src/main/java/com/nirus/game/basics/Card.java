package com.nirus.game.basics;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class Card {
    public Card(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public boolean equals(Card obj) {
        return this.getId().equals(obj.getId());
    }

    private Integer id;
}
