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

    public void setCards(CardArray cards)
    {
        PlayerPrefs.SetString("PlayerOwnCards", JsonUtility.ToJson(cards));
        
    }

    public CardArray getCards()
    {        
        return JsonUtility.FromJson<CardArray>(PlayerPrefs.GetString("PlayerOwnCards"));
    }

    public void setChosenCards(CardArray cards)
    {
        PlayerPrefs.SetString("ChosenCards", JsonUtility.ToJson(cards));
        
    }

    public CardArray getChosenCards()
    {
        return JsonUtility.FromJson<CardArray>(PlayerPrefs.GetString("ChosenCards"));
    }


    public void setScore(int score)
    {
        PlayerPrefs.SetInt("score", score);
    }

    public int getScore() {
        return PlayerPrefs.GetInt("score", 0);
    }

    public void setOwnCard(CardsJson card)
    {
        PlayerPrefs.SetString("ownCard", JsonUtility.ToJson(card));
    }

    public CardsJson getOwnCard()
    {
        return JsonUtility.FromJson<CardsJson>(PlayerPrefs.GetString("ownCard"));
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

    public void SetPlayerColor(string token, int colorNum)
    {
        PlayerPrefs.SetInt("playerColor" + token, colorNum);
    }

    public int GetPlayerColor(string token)
    {
        return PlayerPrefs.GetInt("playerColor" + token, 0);
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

    public void SetPositionInScoreboard(int position)
    {
        PlayerPrefs.SetInt("positionInScoreboard", position);
    }

    public int GetPositionInScoreboard()
    {
        return PlayerPrefs.GetInt("positionInScoreboard", 1);
    }

    public void SetIsFastGame(bool value)
    {
        if (value)
            PlayerPrefs.SetInt("isFastGame", 1);
        else
            PlayerPrefs.SetInt("isFastGame", 0);
    }

    public bool GetIsFastGame()
    {
        if (PlayerPrefs.GetInt("isFastGame") == 1)
            return true;
        return false;
    }

    public void SetAmountOfCards(int amount)
    {
        PlayerPrefs.SetInt("amountOfCards", amount);
    }

    public int GetAmountOfCards()
    {
        return PlayerPrefs.GetInt("amountOfCards",0);
    }

    public string GetPlayerLeaveToken()
    {
        return PlayerPrefs.GetString("playerLeaveToken");
    }

    public string GetPlayerLeaveName()
    {
        string token = GetPlayerLeaveToken();
        return GetPlayerName(token);
    }

    public void SetPlayerLeaveToken(string token)
    {
        PlayerPrefs.SetString("playerLeaveToken", token);
    }

    public void SetEndReason(string reason)
    {
        PlayerPrefs.SetString("EndReason", reason);
    }

    public string GetEndReason()
    {
        return PlayerPrefs.GetString("EndReason", "OUT_OF_CARDS");

    }

    public void SetPlayerScore(string token, int score)
    {
        PlayerPrefs.SetInt("ScorePlayer" + token, score);
    }

    public int GetPlayerScore(string token)
    {
        return PlayerPrefs.GetInt("ScorePlayer" + token, 0);
    }

    public void SetPlayerNewScore(string token, int score)
    {
        Debug.Log(token.ToString() + " " + score);
        int oldScore = GetPlayerScore(token);
        PlayerPrefs.SetInt("DeltaScorePlayer" + token, score-oldScore);
        SetPlayerScore(token, score);
    }

    public int GetPlayerDeltaScore(string token)
    {
        return PlayerPrefs.GetInt("DeltaScorePlayer" + token, 0);
    }
}
