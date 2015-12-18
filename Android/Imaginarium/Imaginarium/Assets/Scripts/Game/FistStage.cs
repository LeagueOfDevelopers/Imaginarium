using UnityEngine;
using System.Collections;

public class FistStage : MonoBehaviour {

    public GameObject Cards;
    public float DeltaCardMove=-270;

    Vector3 destination;
    int currentCard = 0;

	// Use this for initialization
	void Start () {
        destination = Cards.transform.position;
	}
	
	// Update is called once per frame
	void Update () {
        if (Input.touchCount > 0)
        {
            MoveCards();
            NormalizeCurrentCard();
        }
        destination.x = currentCard * DeltaCardMove;
        Vector3 delta = (destination - transform.position) * Time.deltaTime;
        transform.Translate(delta);
	}


    private void MoveCards()
    {
        if (Input.GetTouch(0).deltaPosition.x > 100)
        {
            currentCard--;
        }
        if (Input.GetTouch(0).deltaPosition.x < 100)
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
