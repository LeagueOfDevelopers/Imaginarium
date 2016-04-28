using UnityEngine;
using System.Collections;

public class CardNamer : MonoBehaviour {

    public void SetCards(CardArray cardsInHand)
    {
        int i = 1;
        foreach(CardsJson card in cardsInHand.cards)
        { 
            GameObject child = transform.FindChild("Card" + i).gameObject;
            child.SetActive(true);
            child.GetComponent<CardSpriteHandler>().setCard(card.id);
            i++;
        }
        if (i <= 6)
        {
            for (int k = i; k <= 6; k++)
            {
                transform.FindChild("Card" + i).gameObject.SetActive(false);    //Нужны тесты на нулреф
            }
        }
    }

    public void ChosenCardTogle(int chosenCardIndex)
    {
        transform.FindChild("Card" + (chosenCardIndex+1).ToString()).gameObject.SetActive(false);
    }
}
