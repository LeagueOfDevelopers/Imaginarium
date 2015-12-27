using UnityEngine;
using System.Collections;

public class FirstStageLogic : MonoBehaviour {
    public GameObject Cards;
    Prefs prefs = new Prefs();
	// Use this for initialization
	void Start () {
        setCards();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    private void setCards() {
        Cards.GetComponent<CardNamer>().SetCards(prefs.getCards());
    }
}
