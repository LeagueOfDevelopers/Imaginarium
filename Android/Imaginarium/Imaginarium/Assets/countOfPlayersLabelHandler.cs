using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class countOfPlayersLabelHandler : MonoBehaviour {
    public GameObject gameScriptObject;
    private GeneralGameScript gameScript;

    void Start() {
        gameScript = gameScriptObject.GetComponent<GeneralGameScript>();
    }
	void Update () {
        GetComponent<Text>().text = "Игроков сделавших свой ход: " + gameScript.getCountOfPlayers().ToString();
	}
}
