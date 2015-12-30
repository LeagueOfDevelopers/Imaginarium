using UnityEngine;
using System.Collections;

public class CardNamer : MonoBehaviour {

    public void SetCards(int[] cardsInHand)
    {
        int i = 0;
        foreach(int card in cardsInHand)
        {
            Debug.Log(card);
            GameObject child = transform.FindChild("Card" + i).gameObject;
            child.GetComponent<CardSpriteHandler>().setCard(card);
            i++;
        }
    }
}
