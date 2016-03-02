using UnityEngine;
using System;
using System.Collections.Generic;

public class Prefs {

    public void setToken(string token)
    {
        PlayerPrefs.SetString("token", token);
    }

    public string getToken() {
        return PlayerPrefs.GetString("token", string.Empty);
    }

    public void setSize(int size)
    {
        PlayerPrefs.SetInt("size", size);
    }

    public int getSize()
    {
        return PlayerPrefs.GetInt("size", 6);
    }

    public void setHead(int isHead)
    {
        PlayerPrefs.SetInt("isHeadInt", isHead);
    }

    public bool getHead()
    {
        int isHead = PlayerPrefs.GetInt("isHeadInt",0);
        if (isHead == 0)
            return false;
        return true; 
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
        for (int i = 0; i < 5; i++)
        {
            PlayerPrefs.SetInt("chosenCard" + i.ToString(), cards[i]);
        }
    }

    public int[] getChosenCards()
    {
        int[] cards = new int[5];
        for (int i = 0; i < 5; i++)
        {
            cards[i] = PlayerPrefs.GetInt("chosenCard" + i.ToString(), -1);
        }
        return cards;
    }

    public void setVoteForCards(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("resultForCards" + i.ToString(), cards[i]);
        }
    }

    public int[] getVoteForCards()
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

    public void setStage(int stage) {
        PlayerPrefs.SetInt("stage", stage);
    }

    public int getStage()
    {
        return PlayerPrefs.GetInt("stage", 0);
    }

    public void setIsHead(int head)
    {
        PlayerPrefs.SetInt("isHead", head);
    }

    public bool getIsHead() {
        if (PlayerPrefs.GetInt("isHead", 0) == 0)
            return false;
        return true;
    }

    public void setIsStageComplete(bool complete)
    {
        if(complete)
            PlayerPrefs.SetInt("isStageComplete", 1);
        else
            PlayerPrefs.SetInt("isStageComplete", 0);
    }

    public bool getIsStageComplete()
    {
        if (PlayerPrefs.GetInt("isStageComplete", 0) == 0)
            return false;
        return true;
    }

    public void setCardsForVote(int[] cards)
    {
        for (int i = 0; i < 6; i++)
        {
            PlayerPrefs.SetInt("cardsForVote" + i.ToString(), cards[i]);
        }
    }

    public int[] getCardsForVote()
    {
        int[] cards = new int[6];
        for (int i = 0; i < 6; i++)
        {
            cards[i] = PlayerPrefs.GetInt("cardsForVote" + i.ToString(), 0);
        }
        return cards;
    }

    public void deleteAll() {
        PlayerPrefs.DeleteAll();
    }

    public void SetPlayerName(string token, string name)
    {
        PlayerPrefs.SetString("playerName" + token, name);
    }

    public string GetPlayerName(string token)
    {
        return PlayerPrefs.GetString("playerName" + token, "NoNameHere!");
    }

    public string GetOwnName()
    {
        Prefs pref = new Prefs();
        return PlayerPrefs.GetString("playerName" + pref.getToken() , "NoNameHere!");
    }

    public void SetHeadToken(string token)
    {
        PlayerPrefs.SetString("headToken", token);
    }

    public string GetHeadToken()
    {
        return PlayerPrefs.GetString("headToken", "NoTokenHere!!!!!!!");
    }

    public string GetHeadName()
    {
        Prefs pref = new Prefs();
        return pref.GetPlayerName(pref.GetHeadToken());
    }

}
