using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class curentStageLabel : MonoBehaviour {

    private string[] stageString = { "Конец игры", "Ход ведущего", "Выбор карты", "Голосование", "Результат" };
    private int curentStage = 1;
    private Prefs prefs = new Prefs();


	// Update is called once per frame
	void Update () {
        curentStage = prefs.getStage();
        GetComponent<Text>().text = stageString[curentStage];
	}


}
