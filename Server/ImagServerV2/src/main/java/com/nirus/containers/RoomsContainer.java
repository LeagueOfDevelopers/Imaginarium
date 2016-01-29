package com.nirus.containers;

import com.nirus.basics.Player;
import com.nirus.basics.Room;

import java.util.HashSet;

/**
 * Created by ndiezel on 28.01.2016.
 */
public class RoomsContainer {
    public RoomsContainer(){
        rooms = new HashSet<Room>();
    }
    public boolean addRoom(Room room){
        return rooms.add(room);
    }
    public Room getRoomByPlayer(Player player){
        for (Room room:
             rooms) {
            if(room.contains(player)){
                return room;
            }
        }
        return null;
    }
    private HashSet<Room> rooms;
}
