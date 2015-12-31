package com.nirus.containers;

/**
 * Created by ndiezel on 07.12.2015.
 */
public class Card {
    public Card(Integer id) {
        this.id = id;
    }
    public Integer GetId(){
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Card obj) {
        return id.equals(obj.GetId());
    }

    private Integer id;
}
