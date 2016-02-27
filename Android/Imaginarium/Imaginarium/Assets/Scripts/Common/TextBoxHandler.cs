using UnityEngine;
using UnityEngine.UI;

public class TextBoxHandler : MonoBehaviour {

	// Use this for initialization
	void Start () {
        Prefs prefs = new Prefs();
       GetComponent<InputField>().text = prefs.getText();
    }
	
	// Update is called once per frame
	void Update () {
	
	}
}
