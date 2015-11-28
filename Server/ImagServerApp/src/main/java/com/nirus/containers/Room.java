package com.nirus.containers;

import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
public class Room {
    public Room() {
        _id = new UUID(16, 16);
    }
    public UUID GetID(){ return _id;}
    private UUID _id;
}