package com.nirus.basics;

import java.util.UUID;

/**
 * Created by ndiezel on 27.01.2016.
 */
public class Player {
    public Player(){
        id = UUID.randomUUID();
    }
    public Player(UUID id){
        this.id = id;
    }
    public UUID getId(){
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Player obj) {
        return this.getId().equals(obj.getId());
    }

    private UUID id;
}
