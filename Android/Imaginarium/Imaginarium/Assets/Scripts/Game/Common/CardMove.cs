using UnityEngine;
using System.Collections;

public class CardMove : MonoBehaviour {

    public GameObject Cards;
    public int CountOfCards = 6;

    Vector3 destination;
    float deltaCardMove = 0;
    float limitMove;
    Vector3 startPosition;
    int currentCard = 0;

	// Use this for initialization
	void Start () {
        startPosition = Cards.transform.position;
        destination = startPosition;
        deltaCardMove = Cards.transform.GetChild(0).position.x - Cards.transform.GetChild(1).position.x;
        limitMove = Mathf.Abs(deltaCardMove / 2);
    }
	
	// Update is called once per frame
	void Update () {
        if (Input.touchCount > 0)
        {
            MoveCards();
        }
        else
        {
            destination.x = startPosition.x + currentCard * deltaCardMove;
            Cards.transform.position = Vector3.Lerp(Cards.transform.position, destination, Time.deltaTime * 10);
        }
	}


    private void MoveCards()
    {
        float delta = Input.GetTouch(0).deltaPosition.x*3;
        float curentPositionX = Cards.transform.position.x;
        if (alowedMovement(delta, curentPositionX))
        {
            Cards.transform.position = new Vector3(curentPositionX + delta, startPosition.y, startPosition.z);
            currentCard = Mathf.RoundToInt(Mathf.Abs((Cards.transform.position.x-startPosition.x) / deltaCardMove));
        }
    }

    private bool alowedMovement(float delta, float current) {
        float destination = delta + current;
        return (destination < startPosition.x) &&
            (destination > startPosition.x + deltaCardMove * (CountOfCards-1));
    }

    public int GetCurrentCard() {
        return currentCard;
    }

}
