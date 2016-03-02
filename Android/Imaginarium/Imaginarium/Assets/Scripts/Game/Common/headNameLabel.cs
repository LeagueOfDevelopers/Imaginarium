using UnityEngine;
using UnityEngine.UI;

public class headNameLabel : MonoBehaviour {

    Prefs pref = new Prefs();
	
	// Update is called once per frame
	void Update () {
        GetComponent<Text>().text = pref.GetHeadName();
	}
}
