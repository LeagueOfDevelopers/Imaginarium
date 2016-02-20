using UnityEngine;
using System.Collections;

public class ErrorHandler : MonoBehaviour {
    private GUIStyle style = new GUIStyle();
	// Use this for initialization
	void Start () {
        style.fontSize = 20;
        style.normal.textColor = Color.red;
	}

    void OnGUI() {
        GUI.Label(new Rect(0, 0,500,100), "Ошибка соединения с сервером",style);
    }

	// Update is called once per frame
	void Update () {
	
	}
}
