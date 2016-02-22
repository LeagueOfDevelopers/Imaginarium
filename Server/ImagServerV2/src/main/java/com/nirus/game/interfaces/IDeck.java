package com.nirus.game.interfaces;

import com.nirus.game.basics.Card;

/**
 * Created by ndiezel on 20.02.2016.
 */
public interface IDeck {
    Card getRandomCard();
    Integer size();
}
