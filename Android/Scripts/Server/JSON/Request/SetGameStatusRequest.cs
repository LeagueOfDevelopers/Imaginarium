using UnityEngine;
using System.Collections;
using System;

[Serializable]
public class SetGameStatusRequest
{

    public string token;
    public int card;
    public string text;
    public int stage;

    public SetGameStatusRequest(int card)
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
        this.card = card;
        stage = prefs.getStage();
    }

    public SetGameStatusRequest(CardsJson card)
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
        this.card = card.id;
        stage = prefs.getStage();
    }

    public SetGameStatusRequest(int card, string text)
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
        this.card = card;
        this.text = text;
        stage = prefs.getStage();
    }

    public SetGameStatusRequest(CardsJson card, string text)
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
        this.card = card.id;
        this.text = text;
        stage = prefs.getStage();
    }

    public SetGameStatusRequest()
    {
        Prefs prefs = new Prefs();
        token = prefs.getToken();
    }
}
