using UnityEngine;
using System.Collections;

public class CardNamer : MonoBehaviour {

    public void SetCards(int[] cardsInHand)
    {
        if (cardsInHand.Length != 6) return;

        for (int i = 0; i < 6; i++)
        {
            Debug.Log(cardsInHand[i]);
            GameObject child = transform.FindChild("Card" + i).gameObject;
            child.GetComponent<CardSpriteHandler>().setCard(cardsInHand[i]);
        }
    }
}
