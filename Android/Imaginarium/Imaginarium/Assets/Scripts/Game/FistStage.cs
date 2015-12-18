using UnityEngine;
using System.Collections;

public class FistStage : MonoBehaviour {

    public GameObject Cards;
    public float DeltaCardMove=0 ;

    Vector3 destination;
    Vector3 startPosition;
    int currentCard = 0;

	// Use this for initialization
	void Start () {
        startPosition = Cards.transform.position;
        destination = startPosition;
        DeltaCardMove = Cards.transform.GetChild(0).position.x - Cards.transform.GetChild(1).position.x;
    }
	
	// Update is called once per frame
	void Update () {
        if (Input.touchCount > 0)
        {
            MoveCards();
            NormalizeCurrentCard();
        }
        destination.x = startPosition.x + currentCard * DeltaCardMove;
        Cards.transform.position = Vector3.Lerp(Cards.transform.position, destination, Time.deltaTime*10);
	}


    private void MoveCards()
    {
        if (Input.GetTouch(0).deltaPosition.x > 50)
        {
            currentCard--;
        }
        if (Input.GetTouch(0).deltaPosition.x < -50)
        {
            currentCard++;
        }
    }

    private void NormalizeCurrentCard()
    {
        if (currentCard < 0) currentCard = 0;
        if (currentCard > 5) currentCard = 5;
    }

    public void cardPlus() {
        currentCard++;
    }

    public void cardMinus()
    {
        currentCard--;
    }

}
