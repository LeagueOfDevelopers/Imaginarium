package com.nirus.containers;

import java.util.UUID;
/**
 * Created by ndiezel on 28.11.2015.
 */
public class Player {
    public Player(String name) {
        _name = name;
        _id = new UUID(16, 16);
    }
    public String GetName(){ return _name;}
    public UUID GetUUID(){ return _id;}
    public boolean equals(String name, UUID uuid){
        return /*_id.equals(uuid)&&*/(_name == name);}
    private String _name;
    private UUID _id;
}
