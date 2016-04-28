using UnityEngine;
using System;
using System.Collections;

[Serializable]
public class CardArray{
    public CardsJson[] cards;

    public CardArray()
    { }

    public CardArray(CardsJson[] cards)
    {
        this.cards = cards;
    }
	
}
