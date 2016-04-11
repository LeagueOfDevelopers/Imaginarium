using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class playerNameLabel : MonoBehaviour {

    Prefs prefs = new Prefs();
	
	// Update is called once per frame
	void Update () {
        GetComponent<Text>().text = prefs.GetOwnName();
	}
}
