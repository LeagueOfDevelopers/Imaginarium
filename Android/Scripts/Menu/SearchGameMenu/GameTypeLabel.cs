using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class GameTypeLabel : MonoBehaviour {

    

	// Use this for initialization
	void Start () {
        Prefs prefs = new Prefs();
        if (prefs.GetIsFastGame())
            GetComponent<Text>().text = "Быстрая игра";
        else
            GetComponent<Text>().text = "Медленная игра";
	}
	
	
}
