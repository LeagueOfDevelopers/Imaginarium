using UnityEngine;
using System;
using System.Collections;

[Serializable]
public class GetGameStatusResponce
{

    public string token;
    public int stage;
    public Player[] donePlayers;

    public int amountOfCards;
    public Player currentHead;
    public string text;
    public CardsJson[] cards;

    public string reason;
    public Player leftPlayer;

    public bool isDone()
    {
        if (donePlayers == null)
            return false;

        foreach (Player player in donePlayers)
            if (player.isOwnPlayer()) return true;

        return false;
    }
}

