using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class PlayerLeaveNameLabel : MonoBehaviour {

	// Use this for initialization
	void Start () {
        Prefs prefs = new Prefs();

        string reason = prefs.GetEndReason();

        switch (reason)
        {
            case "OUT_OF_CARDS":
                GetComponent<Text>().text = "Карты закончились!";
                break;
            case "RUN_OUT_OF_TIME":
                GetComponent<Text>().text = "Вышло время!";
                break;

            case "PLAYER_LEFT":
                GetComponent<Text>().text = prefs.GetPlayerLeaveName() + " покинул игру!";
                break;
        }
        
      
    }
	
}
