package com.nirus.game.basics;

import com.nirus.basics.Player;
import com.nirus.containers.PlayersContainer;

import java.util.HashMap;

/**
 * Created by ndiezel on 06.02.2016.
 */
public class PlayersScores {
    public PlayersScores(PlayersContainer players){
        scores = new HashMap<Player, Score>();
        for(Player player: players.getHashSet()){
            scores.put(player, new Score(0));
        }
    }
    public void changePlayerScore(Player player, Integer value){
        for(Player player1: scores.keySet()){
            if(player.getId().equals(player1.getId())){
                scores.get(player1).setScore(scores.get(player1).getScore() + value);
            }
        }
    }
    public Score getScoreByPlayer(Player player){
        return scores.get(player);
    }
    private HashMap<Player, Score> scores;
}
