using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class CardSpriteHandler : MonoBehaviour {

    private int cardID;
    private string texturePath = "Textures\\Game\\Cards\\";

    public void setCard(int id)
    {
        cardID = id;  
        gameObject.GetComponent<Image>().sprite = Resources.Load<Sprite>(texturePath + id);
    }

    public void setCard(CardsJson card)
    {
        cardID = card.id;
        gameObject.GetComponent<Image>().sprite = Resources.Load<Sprite>(texturePath + cardID);
    }

    public int getCard()
    {
        return cardID;
    }
}
