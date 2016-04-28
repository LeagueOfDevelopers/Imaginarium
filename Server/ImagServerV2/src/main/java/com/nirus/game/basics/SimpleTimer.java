package com.nirus.game.basics;

/**
 * Created by ndiezel on 22.04.2016.
 */
public class SimpleTimer {
    public void start(){
        startTime = System.currentTimeMillis();
    }
    public void restart(long newFinalTime){
        start();
        if(newFinalTime != 0)
            setFinalTime(newFinalTime);
    }
    public void setFinalTime(long newFinalTime){
        newFinalTime = newFinalTime * 1000;
        finalTime = newFinalTime;
    }
    public boolean hasTimeRunOut(){
        return System.currentTimeMillis() - startTime > finalTime;
    }
    public long getCurrentTime(){
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    public long getRemainingTime(){
        return finalTime / 1000 - getCurrentTime();
    }
    private long startTime;
    private long finalTime;
}
