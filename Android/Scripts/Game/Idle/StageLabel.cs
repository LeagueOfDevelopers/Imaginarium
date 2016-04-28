using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class StageLabel : MonoBehaviour {

    public GameObject HeadNameLabel;
    public GameObject Label;

    private string[] labels = {"Конец игры", "Выберите карту и ассоциацию", "Выберите подходящую карту", "Этап голосования", "Подведение итогов" };
    Prefs prefs = new Prefs();

	
	// Update is called once per frame
	void Update () {
        switch (prefs.getStage())
        {
            case 1:
                Label.SetActive(false);
                HeadNameLabel.SetActive(true);
                Debug.Log(prefs.GetHeadToken() + "TOKEN OF HEAD");
                Player head = new Player(prefs.GetHeadToken(), 0);
                head.OpenPlayerInfo();
                Debug.Log(head.token + "TOKEN OF HEAD");
                Debug.Log(head.Name + "NameOfHead");
                HeadNameLabel.transform.FindChild("Player").GetComponent<PlayerSetter>()
                    .SetPlayer(head, 0);
                break;
            case 2:
            case 3:
            case 4:
            case 0:
                Label.SetActive(true);
                HeadNameLabel.SetActive(false);
                Label.GetComponent<Text>().text = labels[prefs.getStage()];
                break;
            

        }
	}
}
