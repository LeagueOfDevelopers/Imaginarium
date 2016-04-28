using UnityEngine;
using UnityEngine.UI;


public class CountOfPlayersLabel : MonoBehaviour {

    Prefs prefs = new Prefs();
	
	// Update is called once per frame
	void Update () {
        GetComponent<Text>().text = prefs.getSize() + " Игроков";
	}
}
