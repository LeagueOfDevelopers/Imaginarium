﻿using UnityEngine;
using System;
using System.Collections;

public class Prefs {

    public void setToken(string token)
    {
        PlayerPrefs.SetString("token", token);
    }

    public string getToken() {
        return PlayerPrefs.GetString("token", string.Empty);
    }

    public void setHead(bool isHead)
    {
        PlayerPrefs.SetString("isHead",isHead.ToString());
    }

    public bool getHead()
    {
        return Convert.ToBoolean(PlayerPrefs.GetString("isHead")); 
    }

    public void setText(string text)
    {
        PlayerPrefs.SetString("text", text);
    }

    public string getText()
    {
        return PlayerPrefs.GetString("text", string.Empty);
    }

    public void setCards(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("card"+i.ToString(), cards[i]);
        }
    }

    public int[] getCards()
    {
        int[] cards = new int[6];
        for (int i = 0; i < 6; i++)
        {
            cards[i] = PlayerPrefs.GetInt("card"+i.ToString(), -1);
        }
        return cards;
    }

    public void setChosenCards(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("chosenCard" + i.ToString(), cards[i]);
        }
    }

    public int[] getChosenCards()
    {
        int[] cards = new int[6];
        for (int i = 0; i < 6; i++)
        {
            cards[i] = PlayerPrefs.GetInt("chosenCard" + i.ToString(), -1);
        }
        return cards;
    }

    public void setResultForCards(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("resultForCards" + i.ToString(), cards[i]);
        }
    }

    public int[] getResultForCards()
    {
        int[] cards = new int[6];
        for (int i = 0; i < 6; i++)
        {
            cards[i] = PlayerPrefs.GetInt("resultForCards" + i.ToString(), 0);
        }
        return cards;
    }

    public void setScore(int score)
    {
        PlayerPrefs.SetInt("score", score);
    }

    public int getScore() {
        return PlayerPrefs.GetInt("score", 0);
    }

    public void setOwnCard(int card)
    {
        PlayerPrefs.SetInt("ownCard", card);
    }

    public int getOwnCard()
    {
        return PlayerPrefs.GetInt("ownCard", -1);
    }

    public void setHeadCard(int card)
    {
        PlayerPrefs.SetInt("headCard", card);
    }

    public int getHeadCard()
    {
        return PlayerPrefs.GetInt("headCard", -1);
    }


}
